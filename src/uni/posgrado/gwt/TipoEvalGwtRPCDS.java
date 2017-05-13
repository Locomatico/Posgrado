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

import uni.posgrado.shared.model.TipoEval;

import java.util.List;

public class TipoEvalGwtRPCDS extends GwtRpcDataSource {

	public TipoEvalGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idTipoEval", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 100, true);
        
        addField (field);
        field = new DataSourceTextField ("codigo", "Código", 10, true);
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
	    final TipoEvalGwtRPCDSServiceAsync service = GWT.create (TipoEvalGwtRPCDSService.class);
		final TipoEval testRec = new TipoEval ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<TipoEval>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<TipoEval> result) {
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
	    TipoEval testRec = new TipoEval ();
	    copyValues (rec, testRec);
	    TipoEvalGwtRPCDSServiceAsync service = GWT.create (TipoEvalGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<TipoEval> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (TipoEval result) {
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
	    TipoEval testRec = new TipoEval ();
	    copyValues (rec, testRec);
	    TipoEvalGwtRPCDSServiceAsync service = GWT.create (TipoEvalGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<TipoEval> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (TipoEval result) {
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
	    TipoEval testRec = new TipoEval ();
	    copyValues (rec, testRec);
	    TipoEvalGwtRPCDSServiceAsync service = GWT.create (TipoEvalGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, TipoEval to) {
	    to.setIdTipoEval (from.getAttributeAsInt ("idTipoEval"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase().trim());
	    if(from.getAttributeAsString ("codigo") != null)
	    	to.setCodigo (from.getAttributeAsString ("codigo").toUpperCase().trim());
	}
	
	static void copyValuesFilter (ListGridRecord from, TipoEval to) {
		to.setIdTipoEval (from.getAttributeAsInt ("idTipoEval"));
	    to.setNombre (from.getAttributeAsString ("nombre"));
    	to.setCodigo (from.getAttributeAsString ("codigo"));
	}
	
	private static void copyValues (TipoEval from, ListGridRecord to) {
	    to.setAttribute ("idTipoEval", from.getIdTipoEval());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("codigo", from.getCodigo());
	}
}