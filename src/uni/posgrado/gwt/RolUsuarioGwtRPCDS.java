package uni.posgrado.gwt;

import java.util.List;

import uni.posgrado.shared.model.Rol;
import uni.posgrado.shared.model.RolUsuario;
import uni.posgrado.shared.model.RolUsuarioPK;
import uni.posgrado.shared.model.Usuario;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class RolUsuarioGwtRPCDS extends GwtRpcDataSource {
	
	public RolUsuarioGwtRPCDS () {
        DataSourceField field;
        field = new DataSourceIntegerField ("idUsuario", "idUsuario");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (true);
        addField (field);
        field = new DataSourceIntegerField ("idRol", "idRol");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (true);
        addField (field);
        field = new DataSourceTextField ("nombre", "Rol");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceBooleanField ("habilitado", "Habilitado");
        field.setRequired (false);        
        addField (field);
        field = new DataSourceBooleanField ("mantenimiento", "Mantenimiento");
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
	    final RolUsuarioGwtRPCDSServiceAsync service = GWT.create (RolUsuarioGwtRPCDSService.class);
		final RolUsuario testRec = new RolUsuario ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<RolUsuario>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<RolUsuario> result) {
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
	    RolUsuario testRec = new RolUsuario ();
	    copyValues (rec, testRec);
	    RolUsuarioGwtRPCDSServiceAsync service = GWT.create (RolUsuarioGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<RolUsuario> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (RolUsuario result) {
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
	    RolUsuario testRec = new RolUsuario ();
	    copyValues (rec, testRec);
	    RolUsuarioGwtRPCDSServiceAsync service = GWT.create (RolUsuarioGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<RolUsuario> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (RolUsuario result) {
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
	    RolUsuario testRec = new RolUsuario ();
	    copyValues (rec, testRec);
	    RolUsuarioGwtRPCDSServiceAsync service = GWT.create (RolUsuarioGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, RolUsuario to) {
		if(from.getAttribute ("idUsuario") != null && from.getAttribute ("idRol") != null) {
			RolUsuarioPK pe = new RolUsuarioPK();
			pe.setIdUsuario (from.getAttributeAsInt ("idUsuario"));
			pe.setIdRol (from.getAttributeAsInt ("idRol"));
			to.setId(pe);
			Rol rol = new Rol();
			rol.setIdRol(from.getAttributeAsInt ("idRol"));
			to.setRol(rol);
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(from.getAttributeAsInt ("idUsuario"));
			to.setUsuario(usuario);
		}
		to.setHabilitado(from.getAttributeAsBoolean ("habilitado"));
		to.setMantenimiento(from.getAttributeAsBoolean ("mantenimiento"));
	}
	
	static void copyValuesFilter (ListGridRecord from, RolUsuario to) {
		if(from.getAttribute ("idUsuario") != null || from.getAttribute ("idRol") != null) {
			RolUsuarioPK pe = new RolUsuarioPK();
			if(from.getAttribute ("idUsuario") != null)
				pe.setIdUsuario (from.getAttributeAsInt ("idUsuario"));
			if(from.getAttribute ("idRol") != null)
				pe.setIdRol (from.getAttributeAsInt ("idRol"));
			to.setId(pe);
		}
		if(from.getAttributeAsString ("nombre") != null) {
			Rol rol = new Rol();
			rol.setNombre(from.getAttributeAsString ("nombre"));
			to.setRol(rol);
		}
		if(from.getAttribute("habilitado") != null) 
			to.setHabilitado(from.getAttributeAsBoolean ("habilitado"));
		if(from.getAttribute("mantenimiento") != null) 
			to.setMantenimiento(from.getAttributeAsBoolean ("mantenimiento"));
	}
	
	private static void copyValues (RolUsuario from, ListGridRecord to) {
		to.setAttribute ("idUsuario", from.getId().getIdUsuario());
		to.setAttribute ("idRol", from.getId().getIdRol());
		to.setAttribute ("nombre", from.getRol().getNombre());	
		to.setAttribute ("habilitado", from.getHabilitado());
		to.setAttribute ("mantenimiento", from.getMantenimiento());
	}

}