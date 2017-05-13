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
import uni.posgrado.shared.model.Seccion;
import uni.posgrado.shared.model.SeccionDocente;
import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;
import uni.posgrado.shared.model.VinculoDocente;

import java.util.List;

public class SeccionDocenteGwtRPCDS extends GwtRpcDataSource {

	public SeccionDocenteGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idSeccionDocente", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        field = new DataSourceIntegerField ("vinculoDocente", "Docente", 10, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idSeccion", "Seccion", 10, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("docente", "Docente", 150, false);
        field.setCanEdit(false);
        addField (field);
        
        field = new DataSourceTextField ("rolDocNombre", "Rol Doc", 250, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("rolDoc", "Rol Docente", 10, true);
        field.setDisplayField("rolDocNombre");
        field.setEditorProperties(new ListGridSelect("Rol Docente", 200, 200, "codTabla", new VariableFormGwtRPCDS("ROLDOC"), "nomTabla", "codTabla", true));
        addField (field);
        
        field = new DataSourceDateField ("fechaInicio", "Inicio", 20, false);
        addField (field);
        field = new DataSourceDateField ("fechaFin", "Fin", 20, false);
        addField (field);
        field = new DataSourceTextField ("observacion", "Observacion", 1000, false);
        addField (field);
        field = new DataSourceBooleanField ("estado", "Estado", 10, false);
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final SeccionDocenteGwtRPCDSServiceAsync service = GWT.create (SeccionDocenteGwtRPCDSService.class);
		final SeccionDocente testRec = new SeccionDocente ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<SeccionDocente>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<SeccionDocente> result) {
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
	    SeccionDocente testRec = new SeccionDocente ();
	    copyValues (rec, testRec, false);
	    SeccionDocenteGwtRPCDSServiceAsync service = GWT.create (SeccionDocenteGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<SeccionDocente> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (SeccionDocente result) {
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
	    SeccionDocente testRec = new SeccionDocente ();
	    copyValues (rec, testRec, false);
	    SeccionDocenteGwtRPCDSServiceAsync service = GWT.create (SeccionDocenteGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<SeccionDocente> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (SeccionDocente result) {
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
	    SeccionDocente testRec = new SeccionDocente ();
	    copyValues (rec, testRec, false);
	    SeccionDocenteGwtRPCDSServiceAsync service = GWT.create (SeccionDocenteGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, SeccionDocente to, Boolean is_filter) {
	    to.setIdSeccionDocente (from.getAttributeAsInt ("idSeccionDocente"));
	    
	    if(from.getAttribute ("idSeccion") != null) {
			 Seccion s = new Seccion();			
			if(from.getAttribute ("idSeccion") != null) {
				s.setIdSeccion (from.getAttributeAsInt ("idSeccion"));
			}
			to.setSeccion(s);
		}
	    
	    if(from.getAttribute("estado") != null) {
	    	to.setEstado (from.getAttributeAsBoolean ("estado"));
	    }
	    if(from.getAttributeAsString ("fechaInicio") != null)
	    	to.setFechaInicio (from.getAttributeAsDate ("fechaInicio"));
	    if(from.getAttributeAsString ("fechaFin") != null)
	    	to.setFechaFin (from.getAttributeAsDate ("fechaFin"));
	    
	    if(from.getAttribute ("vinculoDocente") != null) {
			VinculoDocente vd = new VinculoDocente();			
			if(from.getAttribute ("vinculoDocente") != null) {
				if(is_filter)
					vd.setIdVinculoDocente (from.getAttributeAsInt ("vinculoDocente"));
				else
					vd.setIdVinculoDocente (from.getAttributeAsInt ("vinculoDocente"));
			}
			to.setVinculoDocente(vd);
		}
	    
	    if(from.getAttribute ("rolDoc") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("ROLDOC");
			var_pk.setCodTabla(from.getAttributeAsString ("rolDoc"));
			var.setId(var_pk);
			to.setRolDocente(var);
		}
	    
	    if(from.getAttributeAsString ("observacion") != null)
	    	to.setObservacion (from.getAttributeAsString ("observacion"));
	}	
	
	private static void copyValues (SeccionDocente from, ListGridRecord to) {
	    to.setAttribute ("idSeccionDocente", from.getIdSeccionDocente());
	    to.setAttribute ("observacion", from.getObservacion());
	    to.setAttribute ("estado", from.getEstado());
	    to.setAttribute ("fechaInicio", from.getFechaInicio());
	    to.setAttribute ("fechaFin", from.getFechaFin());
	    if(from.getSeccion() != null) {
	    	to.setAttribute ("idSeccion", from.getSeccion().getIdSeccion());	
	    }
	    if(from.getVinculoDocente() != null) {
	    	to.setAttribute ("vinculoDocente", from.getVinculoDocente().getIdVinculoDocente());
	    	if(from.getVinculoDocente().getUsuario() != null && from.getVinculoDocente().getUsuario().getPersona() != null) {
	    		to.setAttribute ("docente", from.getVinculoDocente().getUsuario().getPersona().getNombreCompleto());
	    	}
	    }
	    if( from.getRolDocente() != null) {
	    	to.setAttribute ("rolDoc", from.getRolDocente().getId().getCodTabla());
		    to.setAttribute ("rolDocNombre", from.getRolDocente().getNomTabla());
	    }
	}
}