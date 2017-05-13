package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.shared.model.Curso;
import uni.posgrado.shared.model.Regulacion;
import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;

import java.math.BigDecimal;
import java.util.List;

public class CursoGwtRPCDS extends GwtRpcDataSource {

	public CursoGwtRPCDS (Boolean hidden) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idCurso", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("codigo", "Código", 150, true);
        addField (field);
        field = new DataSourceTextField ("descripcion", "Nombre", 250, true);
        addField (field);
        field = new DataSourceIntegerField ("credito", "Créditos", 10, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("modalidadNombre", "modalidad", 100, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("modalidad", "Modalidad", 10, true);
        field.setHidden(hidden);
        field.setDisplayField("modalidadNombre");
        field.setEditorProperties(new ListGridSelect("Modalidad", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMOD"), "nomTabla", "codTabla", true));
        addField (field);        
        field = new DataSourceTextField ("tipoCursoNombre", "tipoCurso", 100, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipoCurso", "Tipo de curso", 10, true);
        field.setHidden(hidden);
        field.setDisplayField("tipoCursoNombre");
        field.setEditorProperties(new ListGridSelect("Tipo de curso", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPCUR"), "nomTabla", "codTabla", true));
        addField (field);        
        field = new DataSourceTextField ("tipoNotaNombre", "tipNota", 100, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipoNota", "Tipo de nota", 10, true);
        field.setHidden(hidden);
        field.setDisplayField("tipoNotaNombre");
        field.setEditorProperties(new ListGridSelect("Tipo de nota", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPNOT"), "nomTabla", "codTabla", true));
        addField (field);
        field = new DataSourceTextField ("regulacionNombre", "regulacion", 250, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("regulacion", "Regulación", 10, true);
        field.setHidden(hidden);
        field.setDisplayField("regulacionNombre");
        field.setEditorProperties(new ListGridSelect("Regulación", 200, 300, "nombre", new RegulacionGwtRPCDS(true), "nombre", "idRegulacion", true));
        addField (field);
        field = new DataSourceFloatField ("inasistencia", "Inasistencias", 10, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceFloatField ("inasistenciaJustificada", "I. Justificada", 10, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceFloatField ("inasistenciaInjustificada", "I. Injustificada", 10, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceFloatField ("retiro", "Retiro", 10, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceIntegerField ("notaMaxima", "Nota Máxima", 10, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceIntegerField ("notaMinAprobatoria", "Nota Min. Aprob.", 10, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceIntegerField ("notaMinima", "Nota Mínima", 10, true);
        field.setHidden(hidden);
        addField (field);        
        field = new DataSourceDateField ("fechaInicio", "Inicio", 20, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceDateField ("fechaFin", "Fin", 20, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceBooleanField ("estado", "Estado", 10, false);
        field.setHidden(hidden);
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final CursoGwtRPCDSServiceAsync service = GWT.create (CursoGwtRPCDSService.class);
		final Curso testRec = new Curso ();
		ListGridRecord rec = new ListGridRecord (request.getCriteria().getJsObj());
		copyValues(rec, testRec, true);
	    service.fetch_total(testRec, new AsyncCallback<Integer> () {
	        public void onFailure (Throwable caught) {
	        }
			public void onSuccess(final Integer totalRows) {
				// TODO Auto-generated method stub				
				int startRow = 0;
				int endRow = 50;
				if(request.getStartRow() != null && request.getStartRow() > 0)
					startRow = request.getStartRow();
				if(request.getEndRow() != null)
					endRow = request.getEndRow();
		        
		        response.setTotalRows(totalRows);  
		        response.setStartRow(startRow); 
	
		        endRow = Math.min(endRow, totalRows);
		        response.setEndRow(endRow); 
		        
		        if(totalRows > 0) {
			        String sortBy = request.getAttribute("sortBy");			        
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Curso>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Curso> result) {
			                ListGridRecord[] list = new ListGridRecord[result.size ()];
			                for (int i = 0; i < list.length; i++) {
			                    ListGridRecord record = new ListGridRecord ();
			                    copyValues (result.get (i), record);
			                    list[i] = record;
			                }
			                response.setData (list);
			                response.setTotalRows(totalRows);		                
			                processResponse (requestId, response);
			            }
			        });
		        } else {
					response.setData (new ListGridRecord[0]);
	                response.setTotalRows(totalRows);              
	                processResponse (requestId, response);
				}
			}
		});		
	}
	
	@Override
	protected void executeAdd (final String requestId, final DSRequest request, final DSResponse response) {
	    // Retrieve record which should be added.
	    JavaScriptObject data = request.getData ();
	    ListGridRecord rec = new ListGridRecord (data);
	    Curso testRec = new Curso ();
	    copyValues (rec, testRec, false);
	    CursoGwtRPCDSServiceAsync service = GWT.create (CursoGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Curso> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Curso result) {
	            ListGridRecord[] list = new ListGridRecord[1];
	            ListGridRecord newRec = new ListGridRecord ();
	            copyValues (result, newRec);
	            list[0] = newRec;
	            response.setData (list);
	            processResponse (requestId, response);
	        }
	    });
	}
	
	@Override
	protected void executeUpdate (final String requestId, final DSRequest request, final DSResponse response) {
	    // Retrieve record which should be updated.
	    JavaScriptObject data = request.getData ();
	    ListGridRecord rec = new ListGridRecord (data);
	    // Find grid
	    ListGrid grid = (ListGrid) Canvas.getById (request.getComponentId ());
	    // Get record with old and new values combined
	    int index = grid.getRecordIndex (rec);
	    rec = (ListGridRecord) grid.getEditedRecord (index);
	    Curso testRec = new Curso ();
	    copyValues (rec, testRec, false);
	    CursoGwtRPCDSServiceAsync service = GWT.create (CursoGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Curso> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Curso result) {
	            ListGridRecord[] list = new ListGridRecord[1];
	            ListGridRecord updRec = new ListGridRecord ();
	            copyValues (result, updRec);
	            list[0] = updRec;
	            response.setData (list);
	            processResponse (requestId, response);
	        }
	    });
	}
	
	@Override
	protected void executeRemove (final String requestId, final DSRequest request, final DSResponse response) {
	    // Retrieve record which should be removed.
	    JavaScriptObject data = request.getData ();
	    final ListGridRecord rec = new ListGridRecord (data);
	    Curso testRec = new Curso ();
	    copyValues (rec, testRec, false);
	    CursoGwtRPCDSServiceAsync service = GWT.create (CursoGwtRPCDSService.class);
	    service.remove (testRec, new AsyncCallback<Object> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Object result) {
	            ListGridRecord[] list = new ListGridRecord[1];
	            // We do not receive removed record from server.
	            // Return record from request.
	            list[0] = rec;
	            response.setData (list);
	            processResponse (requestId, response);
	        }
	    });
	}
	
	static void copyValues (ListGridRecord from, Curso to, Boolean is_filter) {
	    to.setIdCurso (from.getAttributeAsInt ("idCurso"));
	    if(from.getAttributeAsString ("descripcion") != null)
	    	to.setDescripcion (from.getAttributeAsString ("descripcion"));
	    if(from.getAttributeAsString ("codigo") != null)
	    	to.setCodigo (from.getAttributeAsString ("codigo").toUpperCase());
	    if(from.getAttribute ("credito") != null)
	    	to.setCredito (Integer.parseInt(from.getAttribute ("credito")));
	    if(from.getAttribute("estado") != null) {
	    	to.setEstado (from.getAttributeAsBoolean ("estado"));
	    }
	    if(from.getAttributeAsString ("fechaInicio") != null)
	    	to.setFechaInicio (from.getAttributeAsDate ("fechaInicio"));
	    if(from.getAttributeAsString ("fechaFin") != null)
	    	to.setFechaFin (from.getAttributeAsDate ("fechaFin"));
	    if(from.getAttribute ("modalidad") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPMOD");
			var_pk.setCodTabla(from.getAttributeAsString ("modalidad"));
			var.setId(var_pk);
			to.setModalidad(var);
		}
	    if(from.getAttribute ("regulacion") != null || from.getAttributeAsString ("regulacionNombre") != null) {
	    	Regulacion regulacion = new Regulacion();
			if(from.getAttribute ("regulacion") != null) {
				if(is_filter)
					regulacion.setNombre (from.getAttributeAsString ("regulacion"));
				else
					regulacion.setIdRegulacion (from.getAttributeAsInt ("regulacion"));
			}				
			if(from.getAttributeAsString ("regulacionNombre") != null)
				regulacion.setNombre(from.getAttributeAsString ("regulacionNombre"));
			to.setRegulacion(regulacion);
		}
	    if(from.getAttributeAsString ("inasistenciaInjustificada") != null)
	    	to.setInasistenciaInjustificada (new BigDecimal(from.getAttribute ("inasistenciaInjustificada").toString()));
    	if(from.getAttributeAsString ("inasistenciaJustificada") != null)
	    	to.setInasistenciaJustificada (new BigDecimal(from.getAttribute ("inasistenciaJustificada").toString()));
    	if(from.getAttributeAsString ("inasistencia") != null)
	    	to.setInasistencia (new BigDecimal(from.getAttribute ("inasistencia").toString()));    	
    	if(from.getAttribute ("notaMaxima") != null)
	    	to.setNotaMaxima (Integer.parseInt(from.getAttribute ("notaMaxima")));
    	if(from.getAttribute ("notaMinima") != null)
	    	to.setNotaMinima (Integer.parseInt(from.getAttribute ("notaMinima")));
    	if(from.getAttribute ("notaMinAprobatoria") != null)
	    	to.setNotaMinAprobatoria (Integer.parseInt(from.getAttribute ("notaMinAprobatoria")));
    	if(from.getAttributeAsString ("retiro") != null)
	    	to.setRetiro (new BigDecimal(from.getAttribute ("retiro").toString()));
    	if(from.getAttribute ("tipoCurso") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPCUR");
			var_pk.setCodTabla(from.getAttributeAsString ("tipoCurso"));
			var.setId(var_pk);
			to.setTipoCurso(var);
		}
    	if(from.getAttribute ("tipoNota") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPNOT");
			var_pk.setCodTabla(from.getAttributeAsString ("tipoNota"));
			var.setId(var_pk);
			to.setTipoNota(var);
		}

	}	
	
	private static void copyValues (Curso from, ListGridRecord to) {
	    to.setAttribute ("idCurso", from.getIdCurso());
	    to.setAttribute ("codigo", from.getCodigo());
	    to.setAttribute ("credito", from.getCredito());
	    to.setAttribute ("descripcion", from.getDescripcion());
	    to.setAttribute ("estado", from.getEstado());
	    to.setAttribute ("fechaInicio", from.getFechaInicio());
	    to.setAttribute ("fechaFin", from.getFechaFin());
	    if( from.getModalidad() != null) {
	    	to.setAttribute ("modalidad", from.getModalidad().getId().getCodTabla());
		    to.setAttribute ("modalidadNombre", from.getModalidad().getNomTabla());
	    }
	    if( from.getRegulacion() != null) {
	    	to.setAttribute ("regulacion", from.getRegulacion().getIdRegulacion());
		    to.setAttribute ("regulacionNombre", from.getRegulacion().getNombre());
	    }
	    to.setAttribute ("inasistenciaInjustificada", from.getInasistenciaInjustificada());
	    to.setAttribute ("inasistenciaJustificada", from.getInasistenciaJustificada());
	    to.setAttribute ("inasistencia", from.getInasistencia());
	    to.setAttribute ("notaMaxima", from.getNotaMaxima());
	    to.setAttribute ("notaMinima", from.getNotaMinima());
	    to.setAttribute ("notaMinAprobatoria", from.getNotaMinAprobatoria());
	    to.setAttribute ("retiro", from.getRetiro());
	    if( from.getTipoCurso() != null) {
	    	to.setAttribute ("tipoCurso", from.getTipoCurso().getId().getCodTabla());
		    to.setAttribute ("tipoCursoNombre", from.getTipoCurso().getNomTabla());
	    }
	    if( from.getTipoNota() != null) {
	    	to.setAttribute ("tipoNota", from.getTipoNota().getId().getCodTabla());
		    to.setAttribute ("tipoNotaNombre", from.getTipoNota().getNomTabla());
	    }
	}
}