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



import uni.posgrado.shared.model.Seccion;
import uni.posgrado.shared.model.SeccionFecha;

import java.util.List;

public class SeccionFechaGwtRPCDS extends GwtRpcDataSource {

	public SeccionFechaGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idSeccionFecha", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        
        field = new DataSourceIntegerField ("seccion", "seccion", 10, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceDateField ("fecInicio", "Inicio", 20, false);
        addField (field);
        field = new DataSourceDateField ("fecFin", "Fin", 20, false);
        addField (field);
        field = new DataSourceDateField ("fecCierre", "Cierre", 20, false);
        addField (field);
        field = new DataSourceTextField ("observacion", "Observación", 1000, false);
        addField (field);
        field = new DataSourceBooleanField ("estado", "Estado", 10, false);
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final SeccionFechaGwtRPCDSServiceAsync service = GWT.create (SeccionFechaGwtRPCDSService.class);
		final SeccionFecha testRec = new SeccionFecha ();
		ListGridRecord rec = new ListGridRecord (request.getCriteria().getJsObj());
		copyValues(rec, testRec);
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<SeccionFecha>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<SeccionFecha> result) {
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
	    SeccionFecha testRec = new SeccionFecha ();
	    copyValues (rec, testRec);
	    SeccionFechaGwtRPCDSServiceAsync service = GWT.create (SeccionFechaGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<SeccionFecha> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (SeccionFecha result) {
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
	    SeccionFecha testRec = new SeccionFecha ();
	    copyValues (rec, testRec);
	    SeccionFechaGwtRPCDSServiceAsync service = GWT.create (SeccionFechaGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<SeccionFecha> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (SeccionFecha result) {
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
	    SeccionFecha testRec = new SeccionFecha ();
	    copyValues (rec, testRec);
	    SeccionFechaGwtRPCDSServiceAsync service = GWT.create (SeccionFechaGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, SeccionFecha to) {
	    to.setIdSeccionFecha (from.getAttributeAsInt ("idSeccionFecha"));
	    
	    if(from.getAttribute("estado") != null) {
	    	to.setEstado (from.getAttributeAsBoolean ("estado"));
	    }
	    if(from.getAttributeAsString ("fecInicio") != null)
	    	to.setFecInicio (from.getAttributeAsDate ("fecInicio"));
	    if(from.getAttributeAsString ("fecFin") != null)
	    	to.setFecFin (from.getAttributeAsDate ("fecFin"));
	    if(from.getAttributeAsString ("fecCierre") != null)
	    	to.setFecCierre (from.getAttributeAsDate ("fecCierre"));
	    if(from.getAttribute ("seccion") != null) {
	    	Seccion seccion = new Seccion();
			if(from.getAttribute ("seccion") != null)
				seccion.setIdSeccion (from.getAttributeAsInt ("seccion"));
			to.setSeccion(seccion);
		}
	    if(from.getAttributeAsString ("observacion") != null)
	    	to.setObservacion (from.getAttributeAsString ("observacion"));	    	    
	}	
	
	private static void copyValues (SeccionFecha from, ListGridRecord to) {
	    to.setAttribute ("idSeccionFecha", from.getIdSeccionFecha());	    
	    to.setAttribute ("estado", from.getEstado());
	    to.setAttribute ("fecInicio", from.getFecInicio());
	    to.setAttribute ("fecFin", from.getFecFin());
	    to.setAttribute ("fecCierre", from.getFecCierre());	    
	    to.setAttribute ("observacion", from.getObservacion());	    
	    if( from.getSeccion() != null) {
	    	to.setAttribute ("seccion", from.getSeccion().getIdSeccion());
	    }
	}
}