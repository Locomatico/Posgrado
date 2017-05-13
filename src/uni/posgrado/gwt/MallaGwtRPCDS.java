package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.CalendarUtil;
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
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.shared.model.Ciclo;
import uni.posgrado.shared.model.Curso;
import uni.posgrado.shared.model.Malla;
import uni.posgrado.shared.model.PlanEstudio;
import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class MallaGwtRPCDS extends GwtRpcDataSource {

	public MallaGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idMalla", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("cursoCodigo", "cursoCodigo", 10, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("curso", "Código", 10, true);
        field.setDisplayField("cursoCodigo");
        field.setEditorProperties(new ListGridSelect("Cursos", 200, 300, "codigo", new CursoGwtRPCDS(true), "codigo", "idCurso", true));
        addField (field);
        
        field = new DataSourceIntegerField ("planEstudio", "id Plan", 10, false);
        //field.setHidden(true);
        field.setCanEdit(false);
        field.setCanView(false);
        addField (field);
        
        field = new DataSourceTextField ("descripcion", "Nombre", 250, true);
        addField (field);
        
        field = new DataSourceIntegerField ("cicloNumero", "ciclo", 10, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("ciclo", "Ciclo", 10, true);
        field.setDisplayField("cicloNumero");
        field.setEditorProperties(new ListGridSelect("Ciclo", 200, 200, "numero", new CicloGwtRPCDS(true), "numero", "idCiclo", true));
        addField (field);
        
        field = new DataSourceIntegerField ("credito", "Créditos", 10, true);
        addField (field);
        field = new DataSourceTextField ("metodologiaNombre", "metodologia", 100, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("metodologia", "Metodología", 10, true);
        field.setDisplayField("metodologiaNombre");
        field.setEditorProperties(new ListGridSelect("Metodología", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMET"), "nomTabla", "codTabla", true));
        addField (field);
        field = new DataSourceTextField ("tipoCursoNombre", "tipoCurso", 100, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipoCurso", "Tipo de curso", 10, true);
        field.setDisplayField("tipoCursoNombre");
        field.setEditorProperties(new ListGridSelect("Tipo de curso", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPCUR"), "nomTabla", "codTabla", true));
        addField (field);        
        field = new DataSourceTextField ("tipoNotaNombre", "tipNota", 100, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipoNota", "Tipo de nota", 10, true);
        field.setDisplayField("tipoNotaNombre");
        field.setEditorProperties(new ListGridSelect("Tipo de nota", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPNOT"), "nomTabla", "codTabla", true));
        addField (field);
        field = new DataSourceIntegerField ("horaAsesoria", "Horas de asesoria", 10, false);
        addField (field);
        field = new DataSourceIntegerField ("horaPractica", "Horas de práctica", 10, true);
        addField (field);
        field = new DataSourceIntegerField ("horaTeoria", "Horas de teoría", 10, true);
        addField (field);
        field = new DataSourceFloatField ("inasistencia", "Inasistencias", 10, true);
        addField (field);
        field = new DataSourceFloatField ("inasistenciaJustificada", "I. Justificada", 10, true);
        addField (field);
        field = new DataSourceFloatField ("inasistenciaInjustificada", "I. Injustificada", 10, true);
        addField (field);
        field = new DataSourceFloatField ("retiro", "Retiro(%)", 10, true);
        addField (field);
        field = new DataSourceIntegerField ("notaMaxima", "Nota Máxima", 10, true);
        addField (field);
        field = new DataSourceIntegerField ("notaMinAprobatoria", "Nota Min. Aprob.", 10, true);
        addField (field);
        field = new DataSourceIntegerField ("notaMinima", "Nota Mínima", 10, true);
        addField (field);        
        
        field = new DataSourceDateField ("fechaInicio", "Inicio", 20, true);
        CustomValidator cv = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				// TODO Auto-generated method stub
				if(value != null && !value.equals("")) {
					if(this.getRecord().getAttribute("fechaFin") != null && !this.getRecord().getAttribute("fechaFin").isEmpty()) {
						Date date_ini = (Date) this.getRecord().getAttributeAsDate("fechaFin");
                  		if (date_ini.after((Date) value)){
                			return true;
                		} else
                			return false;
					}
				}
				return true;
			}        	
        };
        //cv.setValidateOnChange(true);
        field.setValidators(cv);
        DateItem DateInicio = new DateItem();
        DateInicio.setEndDate(new Date());
        Date iniDate = new Date();
        CalendarUtil.addMonthsToDate(iniDate, -120);
        DateInicio.setStartDate(iniDate);
        field.setEditorProperties(DateInicio); 
        addField (field);
        
        
        field = new DataSourceDateField ("fechaFin", "Fin", 20, true);
        DateItem DateFin = new DateItem();
        DateFin.setStartDate(new Date());
        Date dueDate = new Date();
        CalendarUtil.addMonthsToDate(dueDate, 120);
        DateFin.setEndDate(dueDate);
        field.setEditorProperties(DateFin);        
        cv = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				// TODO Auto-generated method stub
				if(value != null && !value.equals("")) {
					if(this.getRecord().getAttribute("fechaInicio") != null && !this.getRecord().getAttribute("fechaInicio").isEmpty()) {
						Date date_ini = (Date) this.getRecord().getAttributeAsDate("fechaInicio");
                  		if (date_ini.before((Date) value)){
                			return true;
                		} else
                			return false;
					}
				}
				return true;
			}        	
        };
        //cv.setValidateOnChange(true);
        field.setValidators(cv);
        addField (field);
        
        field = new DataSourceBooleanField ("electivo", "Electivo", 10, false);
        addField (field);
        field = new DataSourceBooleanField ("estado", "Estado", 10, false);
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final MallaGwtRPCDSServiceAsync service = GWT.create (MallaGwtRPCDSService.class);
		final Malla testRec = new Malla ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Malla>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Malla> result) {
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
	    Malla testRec = new Malla ();
	    copyValues (rec, testRec, false);
	    MallaGwtRPCDSServiceAsync service = GWT.create (MallaGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Malla> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Malla result) {
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
	    Malla testRec = new Malla ();
	    copyValues (rec, testRec, false);
	    MallaGwtRPCDSServiceAsync service = GWT.create (MallaGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Malla> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Malla result) {
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
	    Malla testRec = new Malla ();
	    copyValues (rec, testRec, false);
	    MallaGwtRPCDSServiceAsync service = GWT.create (MallaGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Malla to, Boolean is_filter) {
	    to.setIdMalla (from.getAttributeAsInt ("idMalla"));
	    if(from.getAttributeAsString ("descripcion") != null)
	    	to.setDescripcion (from.getAttributeAsString ("descripcion").toUpperCase().trim());
	    if(from.getAttribute ("credito") != null)
	    	to.setCredito (Integer.parseInt(from.getAttribute ("credito").trim()));
	    if(is_filter){
	    	if(from.getAttribute("electivo") != null) 
	    	to.setElectivo (from.getAttributeAsBoolean ("electivo"));
	    }else{	    
	    	to.setElectivo (from.getAttributeAsBoolean ("electivo"));	    	
	    }
	    if(is_filter){
	    	if(from.getAttribute("estado") != null) 
	    	to.setEstado (from.getAttributeAsBoolean ("estado"));
	    }else{	    
	    	to.setEstado (from.getAttributeAsBoolean ("estado"));	    	
	    }
	    if(from.getAttributeAsString ("fechaInicio") != null)
	    	to.setFechaInicio (from.getAttributeAsDate ("fechaInicio"));
	    if(from.getAttributeAsString ("fechaFin") != null)
	    	to.setFechaFin (from.getAttributeAsDate ("fechaFin"));
	    if(from.getAttribute ("horaAsesoria") != null)
	    	to.setHoraAsesoria (Integer.parseInt(from.getAttribute ("horaAsesoria").trim()));
	    if(from.getAttribute ("horaPractica") != null)
	    	to.setHoraPractica (Integer.parseInt(from.getAttribute ("horaPractica").trim()));
	    if(from.getAttribute ("horaTeoria") != null)
	    	to.setHoraTeoria (Integer.parseInt(from.getAttribute ("horaTeoria").trim()));
	    	Curso curso = new Curso();
			
	    	if(from.getAttribute ("curso") != null){
	    		if(is_filter)
	    			curso.setCodigo(from.getAttributeAsString ("curso"));
	    		else
	    			curso.setIdCurso (from.getAttributeAsInt ("curso"));
	    		to.setCurso(curso);
	    	}
				
			
		
	    if(from.getAttribute ("ciclo") != null || from.getAttribute ("cicloNumero") != null || from.getAttribute ("planEstudio") != null) {
	    	Ciclo ciclo = new Ciclo();
			if(from.getAttribute ("ciclo") != null)
				ciclo.setIdCiclo (from.getAttributeAsInt ("ciclo"));
			if(from.getAttribute ("cicloNumero") != null)
				ciclo.setNumero(Integer.parseInt(from.getAttribute ("cicloNumero")));
			if(from.getAttribute ("planEstudio") != null){
				PlanEstudio plan = new PlanEstudio();
				plan.setIdPlanEstudio(Integer.parseInt(from.getAttribute ("planEstudio")));
				ciclo.setPlanEstudio(plan);
			}
			to.setCiclo(ciclo);
		}	    
	    if(from.getAttributeAsString ("inasistenciaInjustificada") != null)
	    	to.setInasistenciaInjustificada (new BigDecimal(from.getAttribute ("inasistenciaInjustificada").toString().trim()));
    	if(from.getAttributeAsString ("inasistenciaJustificada") != null)
	    	to.setInasistenciaJustificada (new BigDecimal(from.getAttribute ("inasistenciaJustificada").toString().trim()));
    	if(from.getAttributeAsString ("inasistencia") != null)
	    	to.setInasistencia (new BigDecimal(from.getAttribute ("inasistencia").toString().trim()));
    	if(from.getAttribute ("metodologia") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPMET");
			var_pk.setCodTabla(from.getAttributeAsString ("metodologia"));
			var.setId(var_pk);
			to.setMetodologia(var);
		}
    	if(from.getAttribute ("notaMaxima") != null)
	    	to.setNotaMaxima (Integer.parseInt(from.getAttribute ("notaMaxima").trim()));
    	if(from.getAttribute ("notaMinima") != null)
	    	to.setNotaMinima (Integer.parseInt(from.getAttribute ("notaMinima").trim()));
    	if(from.getAttribute ("notaMinAprobatoria") != null)
	    	to.setNotaMinAprobatoria (Integer.parseInt(from.getAttribute ("notaMinAprobatoria").trim()));
    	if(from.getAttributeAsString ("retiro") != null)
	    	to.setRetiro (new BigDecimal(from.getAttribute ("retiro").toString().trim()));
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
	
	private static void copyValues (Malla from, ListGridRecord to) {
	    to.setAttribute ("idMalla", from.getIdMalla());
	    to.setAttribute ("credito", from.getCredito());
	    to.setAttribute ("descripcion", from.getDescripcion());
	    to.setAttribute ("electivo", from.getElectivo());
	    to.setAttribute ("estado", from.getEstado());
	    to.setAttribute ("fechaInicio", from.getFechaInicio());
	    to.setAttribute ("fechaFin", from.getFechaFin());
	    to.setAttribute ("horaAsesoria", from.getHoraAsesoria());
	    to.setAttribute ("horaPractica", from.getHoraPractica());
	    to.setAttribute ("horaTeoria", from.getHoraTeoria());
	    if( from.getCurso() != null) {
	    	to.setAttribute ("curso", from.getCurso().getIdCurso());
		    to.setAttribute ("cursoCodigo", from.getCurso().getCodigo());
	    }
	    to.setAttribute ("inasistenciaInjustificada", from.getInasistenciaInjustificada());
	    to.setAttribute ("inasistenciaJustificada", from.getInasistenciaJustificada());
	    to.setAttribute ("inasistencia", from.getInasistencia());
	    to.setAttribute ("notaMaxima", from.getNotaMaxima());
	    to.setAttribute ("notaMinima", from.getNotaMinima());
	    to.setAttribute ("notaMinAprobatoria", from.getNotaMinAprobatoria());
	    to.setAttribute ("retiro", from.getRetiro());
	    if( from.getMetodologia() != null) {
	    	to.setAttribute ("metodologia", from.getMetodologia().getId().getCodTabla());
		    to.setAttribute ("metodologiaNombre", from.getMetodologia().getNomTabla());
	    }
	    if( from.getTipoCurso() != null) {
	    	to.setAttribute ("tipoCurso", from.getTipoCurso().getId().getCodTabla());
		    to.setAttribute ("tipoCursoNombre", from.getTipoCurso().getNomTabla());
	    }
	    if( from.getCiclo() != null) {
	    	to.setAttribute ("cicloNumero", from.getCiclo().getNumero());
		    to.setAttribute ("ciclo", from.getCiclo().getIdCiclo());
		    if(from.getCiclo().getPlanEstudio()!= null){
		    	to.setAttribute("planEstudio", from.getCiclo().getPlanEstudio().getIdPlanEstudio());
		    	
		    }
	    }
	    if( from.getTipoNota() != null) {
	    	to.setAttribute ("tipoNota", from.getTipoNota().getId().getCodTabla());
		    to.setAttribute ("tipoNotaNombre", from.getTipoNota().getNomTabla());
	    }	    
	}
}