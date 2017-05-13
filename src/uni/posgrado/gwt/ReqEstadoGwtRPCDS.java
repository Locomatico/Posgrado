package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Cookies;
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

import uni.posgrado.shared.model.EstadoPost;
import uni.posgrado.shared.model.ReqEstado;
import uni.posgrado.shared.model.Requisito;

import java.util.List;

public class ReqEstadoGwtRPCDS extends GwtRpcDataSource {
	public ReqEstadoGwtRPCDS () {	    
	    DataSourceField field;
	    field = new DataSourceIntegerField ("idReqEstado", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        field = new DataSourceIntegerField ("requisito", "ID Requisito");
        field.setHidden(true);
        field.setCanEdit(false);
        // AutoIncrement on server.
        field.setRequired (true);
        addField (field);
        field = new DataSourceTextField ("estNombre", "Estado Nombre");
        field.setRequired (false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("estadoPost", "Estado");
        field.setHidden(false);
        // AutoIncrement on server.
        field.setRequired (true);
        field.setCanEdit(false);
        field.setDisplayField("estNombre");
        addField (field);
        field = new DataSourceBooleanField ("requerido", "Requerido");
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
	    final ReqEstadoGwtRPCDSServiceAsync service = GWT.create (ReqEstadoGwtRPCDSService.class);
		final ReqEstado testRec = new ReqEstado ();
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
		        
		        String sortBy = request.getAttribute("sortBy");
		        
		        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<ReqEstado>> () {
		            public void onFailure (Throwable caught) {
		                response.setStatus (RPCResponse.STATUS_FAILURE);
		                processResponse (requestId, response);
		            }
		            public void onSuccess (List<ReqEstado> result) {
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
			}
		});		
	}
	
	@Override
	protected void executeAdd (final String requestId, final DSRequest request, final DSResponse response) {
	    // Retrieve record which should be added.
	    JavaScriptObject data = request.getData ();
	    ListGridRecord rec = new ListGridRecord (data);
	    ReqEstado testRec = new ReqEstado ();
	    copyValues (rec, testRec);
	    //testRec.setUserEdit(new Integer(Cookies.getCookie("id")));
	    ReqEstadoGwtRPCDSServiceAsync service = GWT.create (ReqEstadoGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<ReqEstado> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (ReqEstado result) {
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
	    ReqEstado testRec = new ReqEstado ();
	    copyValues (rec, testRec);
	    //testRec.setUserEdit(new Integer(Cookies.getCookie("id")));
	    ReqEstadoGwtRPCDSServiceAsync service = GWT.create (ReqEstadoGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<ReqEstado> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (ReqEstado result) {
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
	    ReqEstado testRec = new ReqEstado ();
	    copyValues (rec, testRec);
	    ReqEstadoGwtRPCDSServiceAsync service = GWT.create (ReqEstadoGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, ReqEstado to) {
		to.setIdReqEstado(from.getAttributeAsInt ("idReqEstado"));
	    if(from.getAttribute ("requisito") != null) {
	    	Requisito re = new Requisito();
			re.setIdRequisito (from.getAttributeAsInt ("requisito"));
			to.setRequisito(re);
		}
	    if(from.getAttribute ("estadoPost") != null) {
	    	EstadoPost pe = new EstadoPost();
			pe.setIdPosEstado (from.getAttributeAsInt ("estadoPost"));
			to.setEstadoPost(pe);
		}
	    to.setRequerido(from.getAttributeAsBoolean ("requerido"));
	}
	
	static void copyValuesFilter (ListGridRecord from, ReqEstado to) {
		to.setIdReqEstado(from.getAttributeAsInt ("idReqEstado"));
		if(from.getAttribute ("requisito") != null) {
	    	Requisito re = new Requisito();
			re.setIdRequisito (from.getAttributeAsInt ("requisito"));
			to.setRequisito(re);
		}
	    if(from.getAttribute ("estadoPost") != null) {
	    	EstadoPost pe = new EstadoPost();
			pe.setIdPosEstado (from.getAttributeAsInt ("idPosEstadoPosEstado"));
			to.setEstadoPost(pe);
		}
	    if(from.getAttribute("requerido") != null) {
	    	to.setRequerido(from.getAttributeAsBoolean ("requerido"));
	    }
	}
	
	private static void copyValues (ReqEstado from, ListGridRecord to) {
		to.setAttribute("idReqEstado", from.getIdReqEstado());
		to.setAttribute ("requisito", from.getRequisito().getIdRequisito());
	    to.setAttribute ("estadoPost", from.getEstadoPost().getIdPosEstado());
	    to.setAttribute ("requerido", from.getRequerido());
	    to.setAttribute ("estNombre", from.getEstadoPost().getNombre());
	}
}
