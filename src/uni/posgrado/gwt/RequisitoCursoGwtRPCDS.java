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

import uni.posgrado.shared.model.RequisitoCurso;

import java.util.List;

public class RequisitoCursoGwtRPCDS extends GwtRpcDataSource {

	public RequisitoCursoGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idRequisitoCurso", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("grupo", "Grupo", 150, true);
        addField (field);
        
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final RequisitoCursoGwtRPCDSServiceAsync service = GWT.create (RequisitoCursoGwtRPCDSService.class);
		final RequisitoCurso testRec = new RequisitoCurso ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<RequisitoCurso>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<RequisitoCurso> result) {
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
	    RequisitoCurso testRec = new RequisitoCurso ();
	    copyValues (rec, testRec);
	    RequisitoCursoGwtRPCDSServiceAsync service = GWT.create (RequisitoCursoGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<RequisitoCurso> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (RequisitoCurso result) {
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
	    RequisitoCurso testRec = new RequisitoCurso ();
	    copyValues (rec, testRec);
	    RequisitoCursoGwtRPCDSServiceAsync service = GWT.create (RequisitoCursoGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<RequisitoCurso> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (RequisitoCurso result) {
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
	    RequisitoCurso testRec = new RequisitoCurso ();
	    copyValues (rec, testRec);
	    RequisitoCursoGwtRPCDSServiceAsync service = GWT.create (RequisitoCursoGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, RequisitoCurso to) {
	    to.setIdRequisitoCurso (from.getAttributeAsInt ("idRequisitoCurso"));
	    if(from.getAttributeAsString ("grupo") != null)
	    	to.setGrupo (from.getAttributeAsString ("grupo").toUpperCase());
	    
	}	
	
	private static void copyValues (RequisitoCurso from, ListGridRecord to) {
	    to.setAttribute ("idRequisitoCurso", from.getIdRequisitoCurso());
	    to.setAttribute ("grupo", from.getGrupo());
	}
}