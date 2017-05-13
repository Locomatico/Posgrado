package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.shared.model.EstadoPost;

import java.util.List;

public class EstadoPostGwtRPCDS extends GwtRpcDataSource {

	public EstadoPostGwtRPCDS (Boolean type) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idPosEstado", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("codigo", "Código");
        field.setRequired (true);
        if(!type)
        	field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre");
        field.setRequired (true);
        addField (field);  
        field = new DataSourceTextField ("descripcion", "Descripción");
        field.setRequired (true);
        if(!type)
        	field.setHidden(true);
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
	    final EstadoPostGwtRPCDSServiceAsync service = GWT.create (EstadoPostGwtRPCDSService.class);	    
		final EstadoPost testRec = new EstadoPost ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<EstadoPost>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<EstadoPost> result) {
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
	    EstadoPost testRec = new EstadoPost ();
	    copyValues (rec, testRec);
	    EstadoPostGwtRPCDSServiceAsync service = GWT.create (EstadoPostGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<EstadoPost> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (EstadoPost result) {
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
	    EstadoPost testRec = new EstadoPost ();
	    copyValues (rec, testRec);
	    EstadoPostGwtRPCDSServiceAsync service = GWT.create (EstadoPostGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<EstadoPost> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (EstadoPost result) {
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
	    EstadoPost testRec = new EstadoPost ();
	    copyValues (rec, testRec);
	    EstadoPostGwtRPCDSServiceAsync service = GWT.create (EstadoPostGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, EstadoPost to) {
	    to.setIdPosEstado (from.getAttributeAsInt ("idPosEstado"));
	    to.setCodigo (from.getAttributeAsString ("codigo"));
    	to.setDescripcion(from.getAttributeAsString ("descripcion"));
    	to.setNombre (from.getAttributeAsString ("nombre"));
	}
	
	static void copyValuesFilter (ListGridRecord from, EstadoPost to) {
		to.setIdPosEstado (from.getAttributeAsInt ("idPosEstado"));
	    to.setCodigo (from.getAttributeAsString ("Codigo"));
    	to.setDescripcion(from.getAttributeAsString ("descripcion"));
    	to.setNombre (from.getAttributeAsString ("nombre"));
	}
	
	private static void copyValues (EstadoPost from, ListGridRecord to) {
	    to.setAttribute ("idPosEstado", from.getIdPosEstado());
	    to.setAttribute ("codigo", from.getCodigo());
	    to.setAttribute ("descripcion", from.getDescripcion());	    
	    to.setAttribute("nombre", from.getNombre());
	}	
}