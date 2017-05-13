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
import uni.posgrado.shared.model.Programa;
import uni.posgrado.shared.model.Unidad;
import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;

import java.util.Date;
import java.util.List;



public class ProgramaGwtRPCDS extends GwtRpcDataSource {

	public boolean returnVal = false;
	
	public ProgramaGwtRPCDS (Boolean hidden) {
		
	    DataSourceField field;
        field = new DataSourceIntegerField ("idPrograma", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceIntegerField ("idPeriodo", "Periodo");
        field.setRequired (false);
        field.setCanEdit(false);
        field.setHidden(true);    
        
        addField (field);        
        field = new DataSourceTextField ("codigo", "Codigo", 6, true);        
        /*CustomValidator cv1 = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				// TODO Auto-generated method stub
				if(value != null && !value.equals("")) {
					ProgramaGwtRPCDSServiceAsync service = GWT.create (ProgramaGwtRPCDSService.class);
					int id = (this.getRecord().getAttribute("idPrograma") != null)?this.getRecord().getAttributeAsInt("idPrograma"):0;
					final CodigoUtil codigo = new CodigoUtil();
					codigo.setEstado(false);
				    service.valida_codigo(id, value.toString(), new AsyncCallback<Boolean>() {						
						@Override
						public void onSuccess(Boolean result) {
							// TODO Auto-generated method stub
							codigo.setEstado(result);
							System.out.println("hice la consulta");
						}						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub							
						}
					});
				    Timer timer = new Timer()
			        {
			            @Override
			            public void run()
			            {
			            	System.out.println("entre al timer");
			            }
			        };
			        timer.schedule(5000);
			        System.out.println("ya paso el timer");
			        return codigo.getEstado();
					return true;
					
				}
				return false;
			}        	
        };
        field.setValidators(cv1);*/
        addField (field);        
        field = new DataSourceTextField ("codigoSunedu", "Codigo SUNEDU", 10, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 250, true);
        addField (field);
        field = new DataSourceTextField ("unidadCodigo", "Cod. dependencia", 20, true);
        field.setCanEdit(false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("unidadNombre", "unidad", 250, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("unidad", "Dependencia", 10, true);
        field.setDisplayField("unidadNombre");
        field.setEditorProperties(new ListGridSelect("Dependencias", 200, 300, "nombre", new UnidadGwtRPCDS(true), "nombre", "idUnidad", true));
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceIntegerField ("duracion", "Duracion", 2, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceDateField ("fechaInicio", "Inicio", 20, true);
        field.setHidden(hidden);    
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
        
        field = new DataSourceTextField ("formacionNombre", "formacion", 250, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("formacion", "Formacion", 10, true);
        field.setHidden(hidden);
        field.setDisplayField("formacionNombre");
        field.setEditorProperties(new ListGridSelect("Formacion", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPFOR"), "nomTabla", "codTabla", true));
        addField (field);
        field = new DataSourceTextField ("metodologiaNombre", "metodologia", 250, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("metodologia", "Metodologia", 10, true);
        field.setHidden(hidden);
        field.setDisplayField("metodologiaNombre");
        field.setEditorProperties(new ListGridSelect("Metodologia", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMET"), "nomTabla", "codTabla", true));
        addField (field);
        field = new DataSourceTextField ("modalidadNombre", "modalidad", 250, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("modalidad", "Modalidad", 10, true);
        field.setHidden(hidden);
        field.setDisplayField("modalidadNombre");
        field.setEditorProperties(new ListGridSelect("Modalidad", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMOD"), "nomTabla", "codTabla", true));
        addField (field);
        field = new DataSourceTextField ("tipoPeriodoNombre", "tipoPeriodo", 250, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipoPeriodo", "Tipo de Periodo", 10, true);
        field.setHidden(hidden);
        field.setDisplayField("tipoPeriodoNombre");
        field.setEditorProperties(new ListGridSelect("Tipo de Periodo", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPPER"), "nomTabla", "codTabla", true));
        addField (field);        
        field = new DataSourceTextField ("grado", "Grado", 250, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("titulo", "Titulo", 250, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceBooleanField ("estado", "Estado", 10, false);
        field.setHidden(hidden);
        addField (field);        
        field = new DataSourceTextField ("descripcion", "Descripcion", 1000, false);
        field.setHidden(hidden);
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final ProgramaGwtRPCDSServiceAsync service = GWT.create (ProgramaGwtRPCDSService.class);
		final Programa testRec = new Programa ();
		ListGridRecord rec = new ListGridRecord (request.getCriteria().getJsObj());
		copyValues(rec, testRec, true);
	    service.fetch_total(testRec, new AsyncCallback<Integer> () {
	        public void onFailure (Throwable caught) {
	        	response.setStatus (RPCResponse.STATUS_FAILURE); 
	            response.setAttribute("data", caught.getMessage());
	            processResponse (requestId, response);
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Programa>> () {
			            public void onFailure (Throwable caught) {
			            	response.setStatus (RPCResponse.STATUS_FAILURE); 
				            response.setAttribute("data", caught.getMessage());
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Programa> result) {
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
	    final Programa testRec = new Programa ();
	    copyValues (rec, testRec, false);
	    final ProgramaGwtRPCDSServiceAsync service = GWT.create (ProgramaGwtRPCDSService.class);
	    service.valida_codigo(0, testRec.getCodigo(), new AsyncCallback<Boolean>() {			
			@Override
			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub
				if(result) {
					service.add (testRec, new AsyncCallback<Programa> () {
				        public void onFailure (Throwable caught) {
				        	response.setStatus (RPCResponse.STATUS_FAILURE); 
				            response.setAttribute("data", caught.getMessage());
				            processResponse (requestId, response);
				        }
				        public void onSuccess (Programa result) {
				            ListGridRecord[] list = new ListGridRecord[1];
				            ListGridRecord newRec = new ListGridRecord ();
				            copyValues (result, newRec);
				            list[0] = newRec;
				            response.setData (list);
				            processResponse (requestId, response);
				        }
				    });
				} else {
					response.setStatus (RPCResponse.STATUS_VALIDATION_ERROR); 
		            response.setAttribute("data", "Código no disponible");
		            processResponse (requestId, response);
		            /*SC.warn("Error", "Código no disponible");*/
				}				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				response.setStatus (RPCResponse.STATUS_FAILURE); 
	            response.setAttribute("data", caught.getMessage());
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
	    final Programa testRec = new Programa ();
	    copyValues (rec, testRec, false);
	    final ProgramaGwtRPCDSServiceAsync service = GWT.create (ProgramaGwtRPCDSService.class);
	    
	    service.valida_codigo(testRec.getIdPrograma(), testRec.getCodigo(), new AsyncCallback<Boolean>() {			
			@Override
			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub
				if(result) {
					service.update (testRec, new AsyncCallback<Programa> () {
				        public void onFailure (Throwable caught) {
				            response.setStatus (RPCResponse.STATUS_FAILURE); 
				            response.setAttribute("data", caught.getMessage());
				            processResponse (requestId, response);
				        }
				        public void onSuccess (Programa result) {
				            ListGridRecord[] list = new ListGridRecord[1];
				            ListGridRecord updRec = new ListGridRecord ();
				            copyValues (result, updRec);
				            list[0] = updRec;
				            response.setData (list);
				            processResponse (requestId, response);
				        }
				    });
				} else {
					response.setStatus (RPCResponse.STATUS_VALIDATION_ERROR); 
		            response.setAttribute("data", "Código no disponible");
		            processResponse (requestId, response);
		            //SC.warn("Error", "Código no disponible");
				}			
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				response.setStatus (RPCResponse.STATUS_FAILURE);
				response.setAttribute("data", caught.getMessage());
	            processResponse (requestId, response);
			}
		});	
	}
	
	@Override
	protected void executeRemove (final String requestId, final DSRequest request, final DSResponse response) {
	    // Retrieve record which should be removed.
	    JavaScriptObject data = request.getData ();
	    final ListGridRecord rec = new ListGridRecord (data);
	    Programa testRec = new Programa ();
	    copyValues (rec, testRec, false);
	    ProgramaGwtRPCDSServiceAsync service = GWT.create (ProgramaGwtRPCDSService.class);
	    service.remove (testRec, new AsyncCallback<Object> () {
	        public void onFailure (Throwable caught) {
	        	response.setStatus (RPCResponse.STATUS_FAILURE); 
	            response.setAttribute("data", caught.getMessage());
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
	
	static void copyValues (ListGridRecord from, Programa to, Boolean is_filter) {
	    to.setIdPrograma (from.getAttributeAsInt ("idPrograma"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase().trim());
	    if(from.getAttributeAsString ("codigo") != null)
	    	to.setCodigo (from.getAttributeAsString ("codigo").toUpperCase().trim());
	    if(from.getAttributeAsString ("codigoSunedu") != null)
	    	to.setCodigoSunedu (from.getAttributeAsString ("codigoSunedu").toUpperCase().trim());
	    if(from.getAttributeAsString ("descripcion") != null)
	    	to.setDescripcion (from.getAttributeAsString ("descripcion").trim());
	    if(from.getAttribute ("duracion") != null)
	    	to.setDuracion (Integer.parseInt(from.getAttribute ("duracion").trim()));
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
	    if(from.getAttributeAsString ("grado") != null)
	    	to.setGrado (from.getAttributeAsString ("grado").toUpperCase().trim());
	    if(from.getAttributeAsString ("titulo") != null)
	    	to.setTitulo (from.getAttributeAsString ("titulo").toUpperCase().trim());

	    if(from.getAttribute ("unidad") != null || from.getAttributeAsString ("unidadNombre") != null || from.getAttributeAsString ("unidadCodigo") != null) {
			Unidad unidad = new Unidad();			
			if(from.getAttribute ("unidad") != null) {
				if(is_filter)
					unidad.setNombre (from.getAttributeAsString ("unidad"));
				else
					unidad.setIdUnidad (from.getAttributeAsInt ("unidad"));
			}				
			if(from.getAttributeAsString ("unidadNombre") != null)
				unidad.setNombre(from.getAttributeAsString ("unidadNombre"));			
			if(from.getAttributeAsString ("unidadCodigo") != null)
				unidad.setCodigo(from.getAttributeAsString ("unidadCodigo"));
			to.setUnidad(unidad);
		}
	    if(from.getAttribute ("formacion") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPFOR");
			var_pk.setCodTabla(from.getAttributeAsString ("formacion"));
			var.setId(var_pk);
			to.setFormacion(var);
		}
	    if(from.getAttribute ("modalidad") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPMOD");
			var_pk.setCodTabla(from.getAttributeAsString ("modalidad"));
			var.setId(var_pk);
			to.setModalidad(var);
		}
	    if(from.getAttribute ("metodologia") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPMET");
			var_pk.setCodTabla(from.getAttributeAsString ("metodologia"));
			var.setId(var_pk);
			to.setMetodologia(var);
		}
	    if(from.getAttribute ("tipoPeriodo") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPPER");
			var_pk.setCodTabla(from.getAttributeAsString ("tipoPeriodo"));
			var.setId(var_pk);
			to.setTipoPeriodo(var);
		}
	    
	    if(from.getAttribute ("idPeriodo") != null) {
    		to.setIdPeriodo(from.getAttributeAsInt("idPeriodo"));
    	}
	}	
	
	private static void copyValues (Programa from, ListGridRecord to) {
	    to.setAttribute ("idPrograma", from.getIdPrograma());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("codigo", from.getCodigo());
	    to.setAttribute ("codigoSunedu", from.getCodigoSunedu());
	    to.setAttribute ("descripcion", from.getDescripcion());
	    to.setAttribute ("duracion", from.getDuracion());
	    to.setAttribute ("estado", from.getEstado());
	    to.setAttribute ("grado", from.getGrado());
	    to.setAttribute ("titulo", from.getTitulo());
	    to.setAttribute ("fechaInicio", from.getFechaInicio());
	    to.setAttribute ("fechaFin", from.getFechaFin());	    
	    if( from.getUnidad() != null) {
	    	to.setAttribute ("unidad", from.getUnidad().getIdUnidad());
		    to.setAttribute ("unidadCodigo", from.getUnidad().getCodigo());
		    to.setAttribute ("unidadNombre", from.getUnidad().getNombre());
	    }
	    if( from.getFormacion() != null) {
	    	to.setAttribute ("formacion", from.getFormacion().getId().getCodTabla());
		    to.setAttribute ("formacionNombre", from.getFormacion().getNomTabla());
	    }
	    if( from.getMetodologia() != null) {
	    	to.setAttribute ("metodologia", from.getMetodologia().getId().getCodTabla());
		    to.setAttribute ("metodologiaNombre", from.getMetodologia().getNomTabla());
	    }
	    if( from.getModalidad() != null) {
	    	to.setAttribute ("modalidad", from.getModalidad().getId().getCodTabla());
		    to.setAttribute ("modalidadNombre", from.getModalidad().getNomTabla());
	    }
	    if( from.getTipoPeriodo() != null) {
	    	to.setAttribute ("tipoPeriodo", from.getTipoPeriodo().getId().getCodTabla());
		    to.setAttribute ("tipoPeriodoNombre", from.getTipoPeriodo().getNomTabla());
	    }
	    
	    
	}
}