package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.shared.model.Convocatoria;
import uni.posgrado.shared.model.ConvocatoriaDetalle;
import uni.posgrado.shared.model.Evaluacion;
import uni.posgrado.shared.model.Local;
import uni.posgrado.shared.model.Modalidad;
import uni.posgrado.shared.model.Periodo;
import uni.posgrado.shared.model.TipoEval;
import uni.posgrado.shared.model.Programa;

import java.math.BigDecimal;
import java.util.List;

public class EvaluacionGwtRPCDS extends GwtRpcDataSource {

	public EvaluacionGwtRPCDS () {
	    DataSourceField field;
        field = new DataSourceIntegerField ("id", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("postulantePeriodo", "Periodo");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("postulanteCodPrograma", "Cod. Programa");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("postulantePrograma", "Programa");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);               
        field = new DataSourceTextField ("postulanteModalidad", "Modalidad");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 100, true);
        //field.setRequired (true);
        //field.setAttribute("characterCasing", CharacterCasing.UPPER);
        addField (field);
        field = new DataSourceTextField ("codigo", "Código", 10, false);
        //field.setRequired (false);
        //field.setAttribute("characterCasing", CharacterCasing.UPPER);
        addField (field);
        field = new DataSourceIntegerField ("idConvocDet", "Det. Convoc.");
        field.setRequired (true);
        field.setHidden (true);
        addField (field);
        field = new DataSourceFloatField ("peso", "Peso (%)");
        field.setRequired (true);
        addField (field);
        field = new DataSourceDateTimeField ("fecha", "Fecha");
        field.setRequired (true);
        addField (field);
        field = new DataSourceFloatField ("factorAjuste", "Factor ajuste");
        field.setRequired (false);
        addField (field);
        field = new DataSourceFloatField ("notaMin", "Nota mín.");
        field.setRequired (false);
        addField (field);
        field = new DataSourceFloatField ("notaMax", "Nota máx.");
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("localNombre", "Local Nombre");
        field.setRequired (true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idLocal", "Local");
        field.setRequired (true);
        field.setDisplayField("localNombre");
        addField (field);
        field = new DataSourceTextField ("tipoEvalNombre", "Tipo Eval Nombre");
        field.setRequired (true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idTipoEval", "Tipo Eval.");
        field.setRequired (true);
        field.setDisplayField("tipoEvalNombre");
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
	    final EvaluacionGwtRPCDSServiceAsync service = GWT.create (EvaluacionGwtRPCDSService.class);
		final Evaluacion testRec = new Evaluacion ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Evaluacion>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Evaluacion> result) {
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
	    Evaluacion testRec = new Evaluacion ();
	    copyValues (rec, testRec);
	    EvaluacionGwtRPCDSServiceAsync service = GWT.create (EvaluacionGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Evaluacion> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Evaluacion result) {
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
	    Evaluacion testRec = new Evaluacion ();
	    copyValues (rec, testRec);
	    EvaluacionGwtRPCDSServiceAsync service = GWT.create (EvaluacionGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Evaluacion> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Evaluacion result) {
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
	    Evaluacion testRec = new Evaluacion ();
	    copyValues (rec, testRec);
	    EvaluacionGwtRPCDSServiceAsync service = GWT.create (EvaluacionGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Evaluacion to) {
	    to.setId (from.getAttributeAsInt ("id"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase());
	    if(from.getAttributeAsString ("codigo") != null )
	    	to.setCodigo (from.getAttributeAsString ("codigo").toUpperCase());
	    to.setFecha (from.getAttributeAsDate ("fecha"));
	    if(from.getAttributeAsString ("factorAjuste") != null)
	    	to.setFactorAjuste (new BigDecimal(from.getAttributeAsString ("factorAjuste")));
	    if(from.getAttributeAsString ("notaMax") != null)
	    	to.setNotaMax (new BigDecimal(from.getAttributeAsString ("notaMax")));
	    if(from.getAttributeAsString ("notaMin") != null)
	    	to.setNotaMin (new BigDecimal(from.getAttributeAsString ("notaMin")));
	    if(from.getAttributeAsString ("peso") != null)
	    	to.setPeso (new BigDecimal(from.getAttributeAsString ("peso")));
	    if(from.getAttribute ("idLocal") != null) {
			Local loc = new Local();
			loc.setIdLocal (from.getAttributeAsInt ("idLocal"));
			to.setLocal(loc);
		}
	    if(from.getAttribute ("idTipoEval") != null) {
			TipoEval teval = new TipoEval();
			teval.setIdTipoEval (from.getAttributeAsInt ("idTipoEval"));
			to.setTipoEval(teval);
		}
	    if(from.getAttribute ("idConvocDet") != null) {
	    	ConvocatoriaDetalle cd = new ConvocatoriaDetalle();
	    	cd.setIdConvocDet(from.getAttributeAsInt ("idConvocDet"));
	    	to.setConvocDet (cd);
	    }
	}
	
	static void copyValuesFilter (ListGridRecord from, Evaluacion to) {
		to.setId (from.getAttributeAsInt ("id"));
	    to.setNombre (from.getAttributeAsString ("nombre"));
	    to.setCodigo (from.getAttributeAsString ("codigo"));
	    to.setFecha (from.getAttributeAsDate ("fecha"));
	    if(from.getAttributeAsString ("factorAjuste") != null) {
	    	try {
	    		to.setFactorAjuste (new BigDecimal(from.getAttributeAsString ("factorAjuste")));
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }	    	
	    if(from.getAttributeAsString ("notaMax") != null) {
	    	try {
	    		to.setNotaMax (new BigDecimal(from.getAttributeAsString ("notaMax")));
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }	    	
	    if(from.getAttributeAsString ("notaMin") != null) {
	    	try {
	    		to.setNotaMin (new BigDecimal(from.getAttributeAsString ("notaMin")));
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }	    	
	    if(from.getAttribute ("peso") != null) {
	    	try {	    		
	    		to.setPeso (new BigDecimal(from.getAttribute ("peso").toString()));
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }
	    if(from.getAttribute ("idLocal") != null) {
	    	Local loc = new Local();
			loc.setNombre(from.getAttributeAsString ("idLocal"));
			to.setLocal(loc);
		}
	    if(from.getAttribute ("idTipoEval") != null) {
			TipoEval teval = new TipoEval();
			teval.setIdTipoEval (from.getAttributeAsInt ("idTipoEval"));
			to.setTipoEval(teval);
		}
	    if(from.getAttribute ("idConvocDet") != null || from.getAttribute ("postulanteCodPrograma") != null || from.getAttribute ("postulantePrograma") != null || from.getAttribute ("postulantePeriodo") != null || from.getAttribute ("postulanteModalidad") != null) {
	    	ConvocatoriaDetalle cd = new ConvocatoriaDetalle();
	    	if(from.getAttribute ("idConvocDet") != null)
	    		cd.setIdConvocDet(from.getAttributeAsInt ("idConvocDet"));
	    	if(from.getAttribute ("postulanteCodPrograma") != null || from.getAttribute ("postulantePrograma") != null) {
	    		Programa programa = new Programa();
	    		if(from.getAttribute ("postulanteCodPrograma") != null)
	    			programa.setCodigo(from.getAttributeAsString ("postulanteCodPrograma"));
	    		if(from.getAttribute ("postulantePrograma") != null)
	    			programa.setNombre(from.getAttributeAsString ("postulantePrograma"));
	    		cd.setPrograma(programa);
	    		
	    	}
	    	if(from.getAttribute ("postulanteModalidad") != null) {
	    		Modalidad mod = new Modalidad();
	    		mod.setIdModalidad(from.getAttributeAsInt ("postulanteModalidad"));
	    		cd.setModalidad(mod);
	    	}
	    	if(from.getAttribute ("postulantePeriodo") != null) {
	    		Convocatoria convoc = new Convocatoria();
	    		Periodo periodo = new Periodo();
	    		periodo.setCodigo(from.getAttributeAsString ("postulantePeriodo"));
	    		convoc.setPeriodo(periodo);
	    		cd.setConvocatoria(convoc);
	    	}
	    	to.setConvocDet (cd);
	    }	    
	}
	
	private static void copyValues (Evaluacion from, ListGridRecord to) {
	    to.setAttribute ("id", from.getId());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("codigo", from.getCodigo());    
	    to.setAttribute("fecha", from.getFecha());
	    to.setAttribute("factorAjuste", from.getFactorAjuste());
	    to.setAttribute("notaMax", from.getNotaMax());
	    to.setAttribute("notaMin", from.getNotaMin());
	    to.setAttribute("peso", from.getPeso());	    
	    if( from.getLocal() != null) {
			to.setAttribute("localNombre", from.getLocal().getNombre());
			to.setAttribute("idLocal", from.getLocal().getIdLocal());
		}
	    if( from.getTipoEval() != null) {
			to.setAttribute("tipoEvalNombre", from.getTipoEval().getNombre());
			to.setAttribute("idTipoEval", from.getTipoEval().getIdTipoEval());
		}
	    if(from.getConvocDet() != null) {
	    	to.setAttribute("idConvocDet", from.getConvocDet().getIdConvocDet());
	    	if(from.getConvocDet().getPrograma() != null) {
	    		to.setAttribute("postulanteCodPrograma", from.getConvocDet().getPrograma().getCodigo());
		 	    to.setAttribute("postulantePrograma", from.getConvocDet().getPrograma().getNombre());
	    	}	    	
	    	if(from.getConvocDet().getConvocatoria() != null && from.getConvocDet().getConvocatoria().getPeriodo() != null)
	    		to.setAttribute("postulantePeriodo", from.getConvocDet().getConvocatoria().getPeriodo().getCodigo());
	    	if(from.getConvocDet().getModalidad() != null)
	    		to.setAttribute("postulanteModalidad", from.getConvocDet().getModalidad().getNombre());
	    }	   
	}
}