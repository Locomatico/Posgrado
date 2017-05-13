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
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.shared.model.PlanEstudio;
import uni.posgrado.shared.model.Programa;
import uni.posgrado.shared.model.Regulacion;
import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;

import java.util.Date;
import java.util.List;

public class PlanEstudioGwtRPCDS extends GwtRpcDataSource {

	public PlanEstudioGwtRPCDS (Boolean hidden) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idPlanEstudio", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("codigo", "Código", 6, true);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 250, true);
        addField (field);
        field = new DataSourceTextField ("programaNombre", "programa", 250, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("programa", "Programa", 10, true);
        field.setDisplayField("programaNombre");
        field.setEditorProperties(new ListGridSelect("Programas", 300, 300, "nombre", new ProgramaGwtRPCDS(true), "nombre", "idPrograma", true));
        addField (field);
        field = new DataSourceTextField ("regulacionNombre", "regulacion", 250, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("regulacion", "Regulación", 10, true);
        field.setDisplayField("regulacionNombre");
        field.setEditorProperties(new ListGridSelect("Regulación", 300, 300, "nombre", new RegulacionGwtRPCDS(true), "nombre", "idRegulacion", true));
        addField (field);        
        field = new DataSourceIntegerField ("creditos", "Créditos", 2, true);
        addField (field);
        field = new DataSourceIntegerField ("creditosElectivos", "Créditos electivos", 2, true);
        addField (field);
        field = new DataSourceIntegerField ("creditosObligatorios", "Créditos obligatorios", 2, true);
        addField (field);
        field = new DataSourceTextField ("formacionNombre", "formacion", 250, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("formacion", "Formación", 10, true);
        field.setDisplayField("formacionNombre");
        field.setEditorProperties(new ListGridSelect("Formación", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPFOR"), "nomTabla", "codTabla", true));
        addField (field);
        field = new DataSourceTextField ("metodologiaNombre", "metodologia", 250, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("metodologia", "Metodología", 10, true);
        field.setDisplayField("metodologiaNombre");
        field.setEditorProperties(new ListGridSelect("Metodología", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMET"), "nomTabla", "codTabla", true));
        addField (field);
        field = new DataSourceTextField ("jornadaEstudioNombre", "jornadaEstudio", 250, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("jornadaEstudio", "Jornada de estudios", 10, true);
        field.setDisplayField("jornadaEstudioNombre");
        field.setEditorProperties(new ListGridSelect("Jornada de estudios", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPJOR"), "nomTabla", "codTabla", true));
        addField (field);
        field = new DataSourceIntegerField ("totalCiclo", "Ciclos", 2, true);
        addField (field);
        field = new DataSourceTextField ("grado", "Grado", 250, false);
        addField (field);
        field = new DataSourceDateField ("vigenciaInicio", "Inicio", 20, false);
        field.setHidden(hidden);
        CustomValidator cv = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				// TODO Auto-generated method stub
				if(value != null && !value.equals("")) {
					if(this.getRecord().getAttribute("vigenciaFin") != null && !this.getRecord().getAttribute("vigenciaFin").isEmpty()) {
						Date date_ini = (Date) this.getRecord().getAttributeAsDate("vigenciaFin");
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
        
        field = new DataSourceDateField ("vigenciaFin", "Fin", 20, false);
        field.setHidden(hidden);
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
					if(this.getRecord().getAttribute("vigenciaInicio") != null && !this.getRecord().getAttribute("vigenciaInicio").isEmpty()) {
						Date date_ini = (Date) this.getRecord().getAttributeAsDate("vigenciaInicio");
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
        
        field = new DataSourceBooleanField ("estado", "Estado", 10, false);
        addField (field); 
        field = new DataSourceTextField ("descripcion", "Descripción", 1000, false);
        addField (field);
        
       
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final PlanEstudioGwtRPCDSServiceAsync service = GWT.create (PlanEstudioGwtRPCDSService.class);
		final PlanEstudio testRec = new PlanEstudio ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<PlanEstudio>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<PlanEstudio> result) {
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
	    PlanEstudio testRec = new PlanEstudio ();
	    copyValues (rec, testRec, false);
	    PlanEstudioGwtRPCDSServiceAsync service = GWT.create (PlanEstudioGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<PlanEstudio> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (PlanEstudio result) {
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
	    PlanEstudio testRec = new PlanEstudio ();
	    copyValues (rec, testRec, false);
	    PlanEstudioGwtRPCDSServiceAsync service = GWT.create (PlanEstudioGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<PlanEstudio> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (PlanEstudio result) {
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
	    PlanEstudio testRec = new PlanEstudio ();
	    copyValues (rec, testRec, false);
	    PlanEstudioGwtRPCDSServiceAsync service = GWT.create (PlanEstudioGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, PlanEstudio to, Boolean is_filter) {
	    to.setIdPlanEstudio (from.getAttributeAsInt ("idPlanEstudio"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase().trim());
	    if(from.getAttributeAsString ("codigo") != null)
	    	to.setCodigo (from.getAttributeAsString ("codigo").toUpperCase().trim());
	    if(from.getAttribute ("creditos") != null)
	    	to.setCreditos (Integer.parseInt(from.getAttribute ("creditos").trim()));
	    if(from.getAttribute ("creditosElectivos") != null)
	    	to.setCreditosElectivos (Integer.parseInt(from.getAttribute ("creditosElectivos").trim()));
	    if(from.getAttribute ("creditosObligatorios") != null)
	    	to.setCreditosObligatorios (Integer.parseInt(from.getAttribute ("creditosObligatorios").trim()));
	    if(is_filter){
	    	if(from.getAttribute("estado") != null) 
	    	to.setEstado (from.getAttributeAsBoolean ("estado"));
	    }else{	    
	    	to.setEstado (from.getAttributeAsBoolean ("estado"));	    	
	    }
	    if(from.getAttributeAsString ("descripcion") != null)
	    	to.setDescripcion (from.getAttributeAsString ("descripcion").toUpperCase().trim());
	    if(from.getAttribute ("formacion") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPFOR");
			var_pk.setCodTabla(from.getAttributeAsString ("formacion"));
			var.setId(var_pk);
			to.setFormacion(var);
		}
	    if(from.getAttribute ("metodologia") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPMET");
			var_pk.setCodTabla(from.getAttributeAsString ("metodologia"));
			var.setId(var_pk);
			to.setMetodologia(var);
		}
	    if(from.getAttribute ("jornadaEstudio") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPJOR");
			var_pk.setCodTabla(from.getAttributeAsString ("jornadaEstudio"));
			var.setId(var_pk);
			to.setJornadaEstudio(var);
		}
	    if(from.getAttributeAsString ("grado") != null)
	    	to.setGrado (from.getAttributeAsString ("grado").toUpperCase().trim());
	    if(from.getAttribute ("programa") != null || from.getAttributeAsString ("programaNombre") != null) {
	    	Programa programa = new Programa();
			if(from.getAttribute ("programa") != null) {
				if(is_filter)
					programa.setNombre(from.getAttributeAsString ("programa"));
				else
					programa.setIdPrograma (from.getAttributeAsInt ("programa"));
			}				
			if(from.getAttributeAsString ("programaNombre") != null)
				programa.setNombre(from.getAttributeAsString ("programaNombre"));
			to.setPrograma(programa);
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
	    if(from.getAttribute ("totalCiclo") != null)
	    	to.setTotalCiclo (Integer.parseInt(from.getAttribute ("totalCiclo").trim()));
	    if(from.getAttributeAsString ("vigenciaInicio") != null)
	    	to.setVigenciaInicio (from.getAttributeAsDate ("vigenciaInicio"));
	    if(from.getAttributeAsString ("vigenciaFin") != null)
	    	to.setVigenciaFin (from.getAttributeAsDate ("vigenciaFin"));
	}	
	
	private static void copyValues (PlanEstudio from, ListGridRecord to) {
	    to.setAttribute ("idPlanEstudio", from.getIdPlanEstudio());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("codigo", from.getCodigo());
	    to.setAttribute ("creditos", from.getCreditos());
	    to.setAttribute ("creditosElectivos", from.getCreditosElectivos());
	    to.setAttribute ("creditosObligatorios", from.getCreditosObligatorios());
	    to.setAttribute ("descripcion", from.getDescripcion());
	    to.setAttribute ("estado", from.getEstado());
	    if( from.getFormacion() != null) {
	    	to.setAttribute ("formacion", from.getFormacion().getId().getCodTabla());
		    to.setAttribute ("formacionNombre", from.getFormacion().getNomTabla());
	    }
	    if( from.getMetodologia() != null) {
	    	to.setAttribute ("metodologia", from.getMetodologia().getId().getCodTabla());
		    to.setAttribute ("metodologiaNombre", from.getMetodologia().getNomTabla());
	    }
	    if( from.getJornadaEstudio() != null) {
	    	to.setAttribute ("jornadaEstudio", from.getJornadaEstudio().getId().getCodTabla());
		    to.setAttribute ("jornadaEstudioNombre", from.getJornadaEstudio().getNomTabla());
	    }
	    to.setAttribute ("grado", from.getGrado());
	    if( from.getPrograma() != null) {
	    	to.setAttribute ("programa", from.getPrograma().getIdPrograma());
		    to.setAttribute ("programaNombre", from.getPrograma().getNombre());
	    }
	    if( from.getRegulacion() != null) {
	    	to.setAttribute ("regulacion", from.getRegulacion().getIdRegulacion());
		    to.setAttribute ("regulacionNombre", from.getRegulacion().getNombre());
	    }
	    to.setAttribute ("totalCiclo", from.getTotalCiclo());
	    to.setAttribute ("vigenciaInicio", from.getVigenciaInicio());
	    to.setAttribute ("vigenciaFin", from.getVigenciaFin());	    
	}
}