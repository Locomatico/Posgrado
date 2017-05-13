package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.shared.model.Convocatoria;
import uni.posgrado.shared.model.ConvocatoriaDetalle;
import uni.posgrado.shared.model.Modalidad;
import uni.posgrado.shared.model.Periodo;
import uni.posgrado.shared.model.Programa;

import java.util.List;

public class ConvocDetGwtRPCDS extends GwtRpcDataSource {
	
	
	public ConvocDetGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idConvocDet", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        /*field = new DataSourceTextField ("periodoCodigo", "Periodo");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);*/
        field = new DataSourceTextField ("periodoCodigo", "Periodo", 10, false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("convocNombre", "Convocatoria");
        field.setRequired (true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("convocatoria", "Convocatoria");
        field.setRequired (true);
        field.setDisplayField("convocNombre");
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceIntegerField ("idConvocatoria", "Id Convoc.");
        field.setRequired (false);
        field.setCanEdit(false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("programaCodigo", "Cod. Programa", 100, false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("programaNombre", "programa", 100, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("programa", "Programa", 10, true);
        field.setDisplayField("programaNombre");
        field.setEditorProperties(new ListGridSelect("Programas", 200, 300, "nombre", new ProgramaGwtRPCDS(true), "nombre", "idPrograma", true));
        addField (field);
        field = new DataSourceTextField ("modalidadNombre", "Modalidad");
        field.setRequired (true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("modalidad", "Modalidad");
        field.setRequired (true);
        field.setDisplayField("modalidadNombre");
        addField (field);

        field = new DataSourceDateField ("fecInicioInscrip", "Inicio Inscrip.");
        field.setRequired (true);
        addField (field);
        field = new DataSourceDateField ("fecFinInscrip", "Fin Inscrip.");
        field.setRequired (true);
        addField (field);
        field = new DataSourceDateField ("fecInicioPreinscrip", "Inicio Pre-Inscrip. Web");
        field.setRequired (true);
        addField (field);
        field = new DataSourceDateField ("fecFinPreinscrip", "Fin Pre-Inscrip. Web");
        field.setRequired (true);
        addField (field);
        field = new DataSourceDateField ("fechaEvaluacion", "Fecha Eval.");
        field.setRequired (false);
        addField (field);
        field = new DataSourceDateField ("fechaCaducidad", "Fecha Caduc.");
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("cartaAsignacion", "Carta Asig.");
        field.setRequired (false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("vacantesTotales", "Vacantes");
        field.setRequired (true);
        addField (field);
        field = new DataSourceBooleanField ("estado", "Estado");
        field.setRequired (false);        
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {
	    // These can be used as parameters to create paging.
	    /*
		request.getStartRow ();
	    request.getEndRow ();        
	    request.getSortBy ();
	    */   	
	    final ConvocDetGwtRPCDSServiceAsync service = GWT.create (ConvocDetGwtRPCDSService.class);	    
		final ConvocatoriaDetalle testRec = new ConvocatoriaDetalle ();   
		ListGridRecord rec = new ListGridRecord (request.getCriteria().getJsObj());
		copyValuesFilter(rec, testRec);
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<ConvocatoriaDetalle>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<ConvocatoriaDetalle> result) {
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
	    ConvocatoriaDetalle testRec = new ConvocatoriaDetalle ();
	    copyValues (rec, testRec);
	    ConvocDetGwtRPCDSServiceAsync service = GWT.create (ConvocDetGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<ConvocatoriaDetalle> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (ConvocatoriaDetalle result) {
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
	    ConvocatoriaDetalle testRec = new ConvocatoriaDetalle ();
	    copyValues (rec, testRec);
	    ConvocDetGwtRPCDSServiceAsync service = GWT.create (ConvocDetGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<ConvocatoriaDetalle> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (ConvocatoriaDetalle result) {
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
	    ConvocatoriaDetalle testRec = new ConvocatoriaDetalle ();
	    copyValues (rec, testRec);
	    ConvocDetGwtRPCDSServiceAsync service = GWT.create (ConvocDetGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, ConvocatoriaDetalle to) {
	    to.setIdConvocDet (from.getAttributeAsInt ("idConvocDet"));
	    to.setCartaAsignacion (from.getAttributeAsString ("cartaAsignacion"));
    	to.setEstado (from.getAttributeAsBoolean ("estado"));
    	to.setEstadoPublicacion (from.getAttributeAsBoolean ("estadoPublicacion"));

    	to.setFecFinInscrip (from.getAttributeAsDate ("fecFinInscrip"));
    	to.setFecFinPreinscrip (from.getAttributeAsDate ("fecFinPreinscrip"));
    	to.setFecInicioInscrip (from.getAttributeAsDate ("fecInicioInscrip"));
    	to.setFecInicioPreinscrip (from.getAttributeAsDate ("fecInicioPreinscrip"));
    	to.setFechaEvaluacion (from.getAttributeAsDate ("fechaEvaluacion"));
    	to.setFechaCaducidad (from.getAttributeAsDate ("fechaCaducidad")); 
    	
    	if(from.getAttribute ("convocatoria") != null) {
			Convocatoria c = new Convocatoria();
			c.setIdConvocatoria (from.getAttributeAsInt ("convocatoria"));
			to.setConvocatoria(c);
		}    	
    	if(from.getAttribute ("modalidad") != null) {
			Modalidad m = new Modalidad();
			m.setIdModalidad (from.getAttributeAsInt ("modalidad"));
			to.setModalidad(m);
		}    	
    	if(from.getAttribute ("programa") != null || from.getAttribute ("programaNombre") != null || from.getAttribute ("programaCodigo") != null) {
    		Programa programa = new Programa();
    		
    		if(from.getAttribute ("programa") != null)
    			programa.setIdPrograma (from.getAttributeAsInt ("programa"));
    		else
    			programa.setIdPrograma (null);
    		
    		if(from.getAttribute ("programaNombre") != null)
    			programa.setNombre(from.getAttributeAsString ("programaNombre"));
    		
    		if(from.getAttribute ("programaCodigo") != null)
    			programa.setCodigo(from.getAttributeAsString ("programaCodigo"));
    		
			to.setPrograma(programa);
		}

		if(from.getAttribute ("vacantesTotales") != null) {
			try {	    		
				to.setVacantesTotales (Integer.parseInt(from.getAttribute ("vacantesTotales").toString()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	static void copyValuesFilter (ListGridRecord from, ConvocatoriaDetalle to) {
		to.setIdConvocDet (from.getAttributeAsInt ("idConvocDet"));
	    to.setCartaAsignacion (from.getAttributeAsString ("cartaAsignacion"));
    	to.setFecFinInscrip (from.getAttributeAsDate ("fecFinInscrip"));
    	to.setFecFinPreinscrip (from.getAttributeAsDate ("fecFinPreinscrip"));
    	to.setFecInicioInscrip (from.getAttributeAsDate ("fecInicioInscrip"));
    	to.setFecInicioPreinscrip (from.getAttributeAsDate ("fecInicioPreinscrip"));
    	to.setFechaEvaluacion (from.getAttributeAsDate ("fechaEvaluacion"));
    	to.setFechaCaducidad (from.getAttributeAsDate ("fechaCaducidad")); 
    	
    	if(from.getAttribute ("idConvocatoria") != null || from.getAttribute ("convocatoria") != null || from.getAttribute ("periodoCodigo") != null) {
    		Convocatoria c = new Convocatoria();
        	if(from.getAttribute ("idConvocatoria") != null) {
        		c.setIdConvocatoria (from.getAttributeAsInt ("idConvocatoria"));
        	}
        	if(from.getAttribute ("convocatoria") != null) {
        		c.setIdConvocatoria (null);
        		if(from.getAttribute ("convocatoria") != null) {
        			c.setNombre(from.getAttributeAsString ("convocatoria"));
        		}
        	}
        	if(from.getAttribute ("periodoCodigo") != null) {
        		Periodo periodo = new Periodo();
        		periodo.setCodigo(from.getAttributeAsString ("periodoCodigo"));
        		c.setPeriodo(periodo);
        	}
        	to.setConvocatoria(c);
    	}
    	
    	/*if(from.getAttribute ("idPersona") != null) {
    		to.setIdPersona(from.getAttributeAsInt("idPersona"));
    	}*/
		 	
    	if(from.getAttribute ("modalidad") != null) {
			Modalidad m = new Modalidad();
			m.setIdModalidad (from.getAttributeAsInt ("modalidad"));
			to.setModalidad(m);
		}
    	
    	if(from.getAttribute ("programa") != null || from.getAttribute ("programaNombre") != null || from.getAttribute ("programaCodigo") != null) {
    		Programa programa = new Programa();
    		
    		if(from.getAttribute ("programa") != null)
    			programa.setNombre (from.getAttributeAsString ("programa"));
    		
    		if(from.getAttribute ("programaNombre") != null)
    			programa.setNombre(from.getAttributeAsString ("programaNombre"));
    		
    		if(from.getAttribute ("programaCodigo") != null)
    			programa.setCodigo(from.getAttributeAsString ("programaCodigo"));
    		
			to.setPrograma(programa);
		}		

		if(from.getAttribute ("vacantesTotales") != null) {
			try {	    		
				to.setVacantesTotales (Integer.parseInt(from.getAttribute ("vacantesTotales").toString()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	    if(from.getAttribute("estado") != null) {
	    	to.setEstado (from.getAttributeAsBoolean ("estado"));  
	    }	
	    if(from.getAttribute("estadoPublicacion") != null) {
	    	to.setEstadoPublicacion (from.getAttributeAsBoolean ("estadoPublicacion"));  
	    }
	}
	
	private static void copyValues (ConvocatoriaDetalle from, ListGridRecord to) {
	    to.setAttribute ("idConvocDet", from.getIdConvocDet());
	    to.setAttribute ("cartaAsignacion", from.getCartaAsignacion());
	    to.setAttribute ("estado", from.getEstado());
	    to.setAttribute ("estadoPublicacion", from.getEstadoPublicacion());
	    to.setAttribute ("fecFinInscrip", from.getFecFinInscrip());
	    to.setAttribute ("fecFinPreinscrip", from.getFecFinPreinscrip());
	    to.setAttribute ("fecInicioInscrip", from.getFecInicioInscrip());
	    to.setAttribute ("fecInicioPreinscrip", from.getFecInicioPreinscrip());
	    to.setAttribute ("fechaEvaluacion", from.getFechaEvaluacion());
	    to.setAttribute ("fechaCaducidad", from.getFechaCaducidad());
	    to.setAttribute ("vacantesTotales", from.getVacantesTotales());
	    
	    if( from.getConvocatoria() != null) {
			to.setAttribute("convocNombre", from.getConvocatoria().getNombre());
			to.setAttribute("convocatoria", from.getConvocatoria().getIdConvocatoria());
			if(from.getConvocatoria().getPeriodo() != null)
				to.setAttribute("periodoCodigo", from.getConvocatoria().getPeriodo().getCodigo());
		}
	    if( from.getModalidad() != null) {
			to.setAttribute("modalidadNombre", from.getModalidad().getNombre());
			to.setAttribute("modalidad", from.getModalidad().getIdModalidad());
		}
	    if(from.getPrograma() != null) {
	    	to.setAttribute("programa", from.getPrograma().getIdPrograma());
	    	to.setAttribute("programaNombre", from.getPrograma().getNombre());
	    	to.setAttribute("programaCodigo", from.getPrograma().getCodigo());
	    }
	}	
}