package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
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
import uni.posgrado.shared.model.PlanEstudio;
import uni.posgrado.shared.model.Unidad;
import uni.posgrado.shared.model.Modalidad;
import uni.posgrado.shared.model.Persona;
import uni.posgrado.shared.model.EstadoPost;
import uni.posgrado.shared.model.Postulacion;
import uni.posgrado.shared.model.Programa;
import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class PostulacionGwtRPCDS extends GwtRpcDataSource {

	public PostulacionGwtRPCDS (Boolean state) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("id", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        field = new DataSourceIntegerField ("idPersona", "Persona");
        field.setRequired (true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("codigo", "Cód. Postulante", 9, true);
        addField (field);
        field = new DataSourceTextField ("numeroDocumento", "Identificación");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("personaNombre", "Postulante");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("periodoCodigo", "Periodo");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("modalidadNombre", "Modalidad");
        field.setRequired (false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idModalidad", "Modalidad");
        field.setRequired (false);
        field.setCanEdit(false);
        field.setDisplayField("modalidadNombre");
        addField (field);
        field = new DataSourceTextField ("convocNombre", "Convocatoria");
        field.setRequired (false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idConvocatoria", "Convocatoria");
        field.setRequired (false);
        field.setCanEdit(false);
        field.setDisplayField("convocNombre");
        addField (field);        
        field = new DataSourceTextField ("unidadCodigo", "Unidad");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("programaCodigo", "Cod. Programa");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("programaNombre", "Programa");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceIntegerField ("idConvocDet", "Convoc. Detalle");
        field.setRequired (true);
        field.setHidden(true);
        addField (field);   
        field = new DataSourceDateTimeField ("fecha", "Fecha");
        field.setRequired (true);
        field.setCanEdit(false);
        addField (field);        
        field = new DataSourceTextField ("estadoNombre", "Estado");
        field.setRequired (true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idEstadoActual", "Estado");
        field.setRequired (true);
        field.setDisplayField("estadoNombre");
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceFloatField ("calificacion", "Calificación");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);        
        field = new DataSourceTextField ("planNombre", "Plan");
        field.setRequired (false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idPlanEstudio", "Plan Estudio");
        field.setRequired (false);
        if(state)
        	field.setCanEdit(false);
        field.setDisplayField("planNombre");
        addField (field);
        field = new DataSourceTextField ("numFile", "Expediente", 50, false);
        if(state)
        	field.setCanEdit(false);
        addField (field);       
        field = new DataSourceTextField ("observacion", "Observacion");
        field.setRequired (false);
        if(state)
        	field.setCanEdit(false);
        addField (field);
        field = new DataSourceBooleanField ("menorEdad", "Menor edad");
        field.setRequired (false);
        field.setCanEdit(false);
        if(!state)
        	field.setHidden(true);
        addField (field);
        
        field = new DataSourceTextField ("tipoDocNombre", "Nombre TIPDOC", 100, true);
        field.setCanEdit(false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipoDoc", "Tipo identif.", 6, true);
        field.setCanEdit(false);
        field.setDisplayField("tipoDocNombre");
        addField (field);
        field = new DataSourceTextField ("email", "Email", 50, true);
        field.setCanEdit(false);
        addField (field);

	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {
	    // These can be used as parameters to create paging.
 	
	    final PostulacionGwtRPCDSServiceAsync service = GWT.create (PostulacionGwtRPCDSService.class);	    
		final Postulacion testRec = new Postulacion ();		
		
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Postulacion>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Postulacion> result) {
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
	    final Postulacion testRec = new Postulacion ();
	    copyValuesAdd (rec, testRec);
	    PostulacionGwtRPCDSServiceAsync service = GWT.create (PostulacionGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Postulacion> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Postulacion result) {
	        	//Integer id = result.getIdPostulacion();
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
	    Postulacion testRec = new Postulacion ();
	    copyValues (rec, testRec);
	    PostulacionGwtRPCDSServiceAsync service = GWT.create (PostulacionGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Postulacion> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Postulacion result) {
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
	    Postulacion testRec = new Postulacion ();
	    copyValues (rec, testRec);
	    PostulacionGwtRPCDSServiceAsync service = GWT.create (PostulacionGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Postulacion to) {
	    to.setId (from.getAttributeAsInt ("id"));
	    if(from.getAttribute ("idPersona") != null) {
    		Persona p = new Persona();
    		p.setIdPersona(from.getAttributeAsInt ("idPersona"));
    		to.setPersona(p);
    	} 
	    if(from.getAttributeAsString ("codigo") != null)
	    	to.setCodigo (from.getAttributeAsString ("codigo").toUpperCase().trim());
	    
	    if(from.getAttribute ("idPlanEstudio") != null) {
    		PlanEstudio pe = new PlanEstudio();
    		pe.setIdPlanEstudio(from.getAttributeAsInt ("idPlanEstudio"));
    		to.setPlanEstudio(pe);
	    }
	    
	    if(from.getAttribute ("calificacion") != null)
	    	to.setCalificacion (from.getAttributeAsFloat ("calificacion"));
    	
    	to.setObservacion (from.getAttributeAsString ("observacion"));
    	
    	if(from.getAttributeAsString ("numFile") != null)
    		to.setNumFile (from.getAttributeAsString ("numFile").toUpperCase().trim());    	
   	
    	if(from.getAttribute ("idConvocDet") != null) {
			ConvocatoriaDetalle cd = new ConvocatoriaDetalle();
			cd.setIdConvocDet (from.getAttributeAsInt ("idConvocDet"));
			to.setConvocDet(cd);
		}
    	   	
    	if(from.getAttribute ("idEstadoActual") != null) {
			EstadoPost ep = new EstadoPost();
			ep.setIdPosEstado (from.getAttributeAsInt ("idEstadoActual"));
			to.setEstadoActual(ep);
		}    	
	    
	    to.setEstado('1');	    
	    to.setFecha(new Timestamp(from.getAttributeAsDate ("fecha").getTime()));	    
	    to.setMenorEdad (from.getAttributeAsBoolean ("menorEdad"));
	}
	
	static void copyValuesAdd (ListGridRecord from, Postulacion to) {
	    to.setId (from.getAttributeAsInt ("id"));
	    
	    if(from.getAttribute ("idPlanEstudio") != null) {
    		PlanEstudio pe = new PlanEstudio();
    		pe.setIdPlanEstudio(from.getAttributeAsInt ("idPlanEstudio"));
    		to.setPlanEstudio(pe);
	    }
	    
	    if(from.getAttribute ("calificacion") != null)
	    	to.setCalificacion (from.getAttributeAsFloat ("calificacion"));
	    
	    if(from.getAttributeAsString ("codigo") != null)
	    	to.setCodigo (from.getAttributeAsString ("codigo").toUpperCase().trim());
    	
	    to.setObservacion (from.getAttributeAsString ("observacion"));
    	
	    if(from.getAttributeAsString ("numFile") != null)
    		to.setNumFile (from.getAttributeAsString ("numFile").toUpperCase().trim());
    	
	    if(from.getAttribute ("idConvocDet") != null) {
			ConvocatoriaDetalle cd = new ConvocatoriaDetalle();
			cd.setIdConvocDet (from.getAttributeAsInt ("idConvocDet"));
			to.setConvocDet(cd);
		}
	    if(from.getAttribute ("idPersona") != null) {
    		Persona p = new Persona();
    		p.setIdPersona(from.getAttributeAsInt ("idPersona"));
    		to.setPersona(p);
    	}   	
    	java.util.Date date = new java.util.Date();
    	EstadoPost ep = new EstadoPost();
    	ep.setIdPosEstado(1);
    	to.setEstadoActual(ep);
	    to.setEstado('1');
	    to.setFecha(new Timestamp(date.getTime()));
	    to.setMenorEdad (from.getAttributeAsBoolean ("menorEdad"));
	}
	
	static void copyValuesFilter (ListGridRecord from, Postulacion to) {
		
		to.setId (from.getAttributeAsInt ("id"));
	    
	    if(from.getAttribute ("calificacion") != null) {
	    	try {
	    		String value = from.getAttributeAsString ("calificacion");
	    		HashMap<String, String> filters = new HashMap<String, String>();
	    		String field = "";
	    		if(value.startsWith(">=")) {
	    			filters.put("calificacion", ">=");
	    			field = value.substring(2, value.length());
	    		} else if (value.startsWith(">")) {
	    			filters.put("calificacion", ">");
	    			field = value.substring(1, value.length());
	    		} else if (value.startsWith("<=")) {
	    			filters.put("calificacion", "<=");
	    			field = value.substring(2, value.length());
	    		} else if (value.startsWith("<")) {
	    			filters.put("calificacion", "<");
	    			field = value.substring(1, value.length());
	    		} else if (value.startsWith("==")) {
	    			filters.put("calificacion", "==");
	    			field = value.substring(2, value.length());
	    		} else if (value.startsWith("=")) {
	    			filters.put("calificacion", "==");
	    			field = value.substring(1, value.length());
	    		} else {
	    			filters.put("calificacion", "==");
	    			field = value;
	    		}
	    		to.setFilters(filters);
	    		to.setCalificacion (Float.parseFloat(field));
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }	    
    	to.setCodigo (from.getAttributeAsString ("codigo"));
    	to.setObservacion (from.getAttributeAsString ("observacion"));
    	to.setNumFile (from.getAttributeAsString ("numFile"));
    	to.setFecha(from.getAttributeAsDate("fecha"));
    	
    	
    	if(from.getAttribute ("idEstadoActual") != null) {
			EstadoPost ep = new EstadoPost();
			ep.setIdPosEstado (from.getAttributeAsInt ("idEstadoActual"));
			to.setEstadoActual(ep);
		}
    	
    	if(from.getAttribute ("idPlanEstudio") != null || from.getAttribute ("planNombre") != null) {
			PlanEstudio plan = new PlanEstudio();
			if(from.getAttribute ("idPlanEstudio") != null)
				plan.setIdPlanEstudio(from.getAttributeAsInt ("idPlanEstudio"));
			if(from.getAttribute ("planNombre") != null)
				plan.setNombre(from.getAttributeAsString ("planNombre"));
			to.setPlanEstudio(plan);
		}
    	
    	if(from.getAttribute ("idConvocatoria") != null || from.getAttribute ("periodoCodigo") != null || from.getAttribute ("idModalidad") != null || from.getAttribute ("programaCodigo") != null || from.getAttribute ("programaNombre") != null || from.getAttribute ("unidadCodigo") != null) {
	    	ConvocatoriaDetalle cd = new ConvocatoriaDetalle();
	    	cd.setIdConvocDet (null);
	    	if(from.getAttribute ("idConvocatoria") != null || from.getAttribute ("periodoCodigo") != null) {
				Convocatoria c = new Convocatoria();
				c.setIdConvocatoria (null);
				if(from.getAttribute ("idConvocatoria") != null)
					c.setNombre(from.getAttributeAsString ("idConvocatoria"));
				/*if(from.getAttribute ("periodoCodigo") != null)
					c.setPeriodoCodigoPeriodo(from.getAttributeAsString ("periodoCodigo"));*/
	    		cd.setConvocatoria(c);
			}
	    	if(from.getAttribute ("idModalidad") != null) {
	    		Modalidad m = new Modalidad();
				m.setIdModalidad (from.getAttributeAsInt ("idModalidad"));
				cd.setModalidad(m);
			}	    	   	
	    	if(from.getAttribute ("programaCodigo") != null || from.getAttribute ("programaNombre") != null || from.getAttribute ("unidadCodigo") != null) {
	    		Programa programa = new Programa();
	    		if(from.getAttribute ("programaCodigo") != null)
	    			programa.setCodigo (from.getAttributeAsString ("programaCodigo"));
	    		else
	    			programa.setCodigo (null);
	    		if(from.getAttribute ("programaNombre") != null)
	    			programa.setNombre(from.getAttributeAsString ("programaNombre"));
	    		if(from.getAttribute ("unidadCodigo") != null) {
	    			Unidad unidad = new Unidad();
	    			unidad.setIdUnidad(0);
	    			unidad.setCodigo(from.getAttributeAsString ("unidadCodigo"));
	    			programa.setUnidad(unidad);
	    		}
				cd.setPrograma(programa);
			}	    	
	    	to.setConvocDet(cd);
    	} 
    	if(from.getAttribute ("idPersona") != null || from.getAttribute ("personaNombre") != null || from.getAttribute ("numeroDocumento") != null || from.getAttribute ("tipoDoc") != null || from.getAttribute ("email") != null) {
    		Persona p = new Persona();
    		p.setIdPersona(null);
    		if(from.getAttribute ("idPersona") != null)
    			p.setIdPersona(from.getAttributeAsInt ("idPersona"));
    		if(from.getAttribute ("personaNombre") != null)
    			p.setNombreCompleto(from.getAttributeAsString ("personaNombre"));
    		if(from.getAttribute ("numeroDocumento") != null)
    			p.setNumeroDocumento(from.getAttributeAsString ("numeroDocumento"));
    		if(from.getAttribute ("tipoDoc") != null) {
    			Variable var1 = new Variable();
    			VariablePK var_pk = new VariablePK();
    			var_pk.setTipTabla("TIPIDE");
    			var_pk.setCodTabla(from.getAttributeAsString ("tipoDoc"));
    			var1.setId(var_pk);
    			p.setTipoDocumento(var1);
    		}
    		if(from.getAttributeAsString ("email") != null)
    	    	p.setCorreoInstitucion(from.getAttributeAsString ("email").toUpperCase().trim());
    		
    		to.setPersona(p);
    	}
	    to.setEstado('1');
	}
	
	private static void copyValues (Postulacion from, ListGridRecord to) {
	    to.setAttribute ("id", from.getId());
	    
	    if(from.getPlanEstudio() != null) {
	    	to.setAttribute ("planNombre", from.getPlanEstudio().getNombre());
	    	to.setAttribute ("idPlanEstudio", from.getPlanEstudio().getIdPlanEstudio());
	    }
	    if(from.getPersona() != null) {
	    	to.setAttribute ("idPersona", from.getPersona().getIdPersona());
	    	to.setAttribute ("personaNombre", from.getPersona().getNombreCompleto());
	    	to.setAttribute ("numeroDocumento", from.getPersona().getNumeroDocumento());
	    }	    	
	    if( from.getConvocDet() != null) {
	    	to.setAttribute ("idConvocDet", from.getConvocDet().getIdConvocDet());
	    	if(from.getConvocDet().getConvocatoria() != null) {
	    		to.setAttribute("convocNombre", from.getConvocDet().getConvocatoria().getNombre());				
				to.setAttribute("idConvocatoria", from.getConvocDet().getConvocatoria().getIdConvocatoria());
				if(from.getConvocDet().getConvocatoria().getPeriodo() != null) {
					to.setAttribute("periodoCodigo", from.getConvocDet().getConvocatoria().getPeriodo().getCodigo());
				}
	    	}
	    	if(from.getConvocDet().getModalidad() != null) {
	    		to.setAttribute("modalidadNombre", from.getConvocDet().getModalidad().getNombre());
				to.setAttribute("idModalidad", from.getConvocDet().getModalidad().getIdModalidad());
	    	}		
	    	if(from.getConvocDet().getPrograma() != null) {
	    		to.setAttribute("programaCodigo", from.getConvocDet().getPrograma().getCodigo());
	    		to.setAttribute("programaNombre", from.getConvocDet().getPrograma().getNombre());
	    		to.setAttribute("unidadCodigo", from.getConvocDet().getPrograma().getUnidad().getCodigo());
	    	}
		}
	    if( from.getEstadoActual() != null) {
			to.setAttribute("estadoNombre", from.getEstadoActual().getNombre());
			to.setAttribute("idEstadoPos", from.getEstadoActual().getIdPosEstado());
		}    	
	    to.setAttribute ("calificacion", from.getCalificacion());
	    to.setAttribute ("codigo", from.getCodigo());
	    to.setAttribute ("fecha", from.getFecha());
	    to.setAttribute ("observacion", from.getObservacion());
	    to.setAttribute ("numFile", from.getNumFile());
	    to.setAttribute("estado", from.getEstado());
	    
	    if( from.getPersona().getTipoDocumento() != null) {
			to.setAttribute("tipoDocNombre", from.getPersona().getTipoDocumento().getNomTabla());
			to.setAttribute("tipoDoc", from.getPersona().getTipoDocumento().getId().getCodTabla());
		}	    
	    to.setAttribute ("email", from.getPersona().getCorreoInstitucion());	        
	}	
}