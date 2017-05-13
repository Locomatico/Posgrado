package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;

import java.util.List;

public class VariableGwtRPCDS extends GwtRpcDataSource {
	

	public VariableGwtRPCDS () {
	    DataSourceField field;
        field = new DataSourceTextField ("tipTabla", "Tipo", 6, true);
        field.setPrimaryKey (true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipTablaCopy", "Tipo", 6, true);
        addField (field);
        field = new DataSourceTextField ("codTabla", "Código", 6, true);
        field.setPrimaryKey (true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("codTablaCopy", "Código", 6, true);
        addField (field);
        field = new DataSourceTextField ("nomTabla", "Descripción", 250, true);
        addField (field);
        field = new DataSourceTextField ("codAuxiliar1", "Auxiliar 1", 250, false);
        addField (field); 
        field = new DataSourceTextField ("codAuxiliar2", "Auxiliar 2", 1000, false);
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {
	    // These can be used as parameters to create paging.
  	
	    final VariableGwtRPCDSServiceAsync service = GWT.create (VariableGwtRPCDSService.class);	    
		final Variable testRec = new Variable ();
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
		        	service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Variable>> () {
		        		public void onFailure (Throwable caught) {
		        			response.setStatus (RPCResponse.STATUS_FAILURE);
		        			processResponse (requestId, response);
		        		}
		        		public void onSuccess (List<Variable> result) {
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
	    Variable testRec = new Variable ();
	    copyValuesAdd (rec, testRec);
	    testRec.setUserAdd(new Float(Cookies.getCookie("id")));
	    VariableGwtRPCDSServiceAsync service = GWT.create (VariableGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Variable> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Variable result) {
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
	    Variable testRec = new Variable ();
	    copyValues (rec, testRec);
	    testRec.setUserEdit(new Float(Cookies.getCookie("id")));
	    VariableGwtRPCDSServiceAsync service = GWT.create (VariableGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Variable> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Variable result) {
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
	    Variable testRec = new Variable ();
	    copyValues (rec, testRec);
	    testRec.setUserDel(new Float(Cookies.getCookie("id")));
	    VariableGwtRPCDSServiceAsync service = GWT.create (VariableGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Variable to) {
	    to.setCodAuxiliar1 (from.getAttributeAsString ("codAuxiliar1"));
    	to.setCodAuxiliar2 (from.getAttributeAsString ("codAuxiliar2"));    	
    	to.setNomTabla (from.getAttributeAsString ("nomTabla"));    	
	    to.setUserAdd(from.getAttributeAsFloat("userAdd"));
	    to.setEstado('1');	    
	    if(from.getAttributeAsString ("codTabla") != null && from.getAttributeAsString ("tipTabla") != null) {
	    	VariablePK var_pk = new VariablePK();
	    	var_pk.setCodTabla (from.getAttributeAsString ("codTabla").toUpperCase());
	    	var_pk.setTipTabla(from.getAttributeAsString ("tipTabla").toUpperCase());
    		to.setId(var_pk);
	    }
	}
	
	static void copyValuesAdd (ListGridRecord from, Variable to) {
	    to.setCodAuxiliar1 (from.getAttributeAsString ("codAuxiliar1"));
    	to.setCodAuxiliar2 (from.getAttributeAsString ("codAuxiliar2"));
    	VariablePK var_pk = new VariablePK();
    	if(from.getAttributeAsString ("codTablaCopy") != null)
    		var_pk.setCodTabla (from.getAttributeAsString ("codTablaCopy").toUpperCase());
    	to.setNomTabla (from.getAttributeAsString ("nomTabla"));
    	if(from.getAttributeAsString ("tipTablaCopy") != null)
    		var_pk.setTipTabla(from.getAttributeAsString ("tipTablaCopy").toUpperCase());
	    to.setUserAdd(from.getAttributeAsFloat("userAdd"));
	    to.setEstado('1');	    
    	to.setId(var_pk);
	}
	
	static void copyValuesFilter (ListGridRecord from, Variable to) {
	    to.setCodAuxiliar1 (from.getAttributeAsString ("codAuxiliar1"));
    	to.setCodAuxiliar2 (from.getAttributeAsString ("codAuxiliar2"));
    	VariablePK var_pk = new VariablePK();
    	var_pk.setCodTabla (from.getAttributeAsString ("codTablaCopy"));
    	to.setNomTabla (from.getAttributeAsString ("nomTabla"));
    	var_pk.setTipTabla(from.getAttributeAsString ("tipTablaCopy"));
	    to.setUserAdd(from.getAttributeAsFloat("userAdd"));
	    to.setEstado('1');
	    to.setId(var_pk);
	}
	
	private static void copyValues (Variable from, ListGridRecord to) {
	    to.setAttribute ("codAuxiliar1", from.getCodAuxiliar1());
	    to.setAttribute ("codAuxiliar2", from.getCodAuxiliar2());
	    to.setAttribute ("codTabla", from.getId().getCodTabla());
	    to.setAttribute ("codTablaCopy", from.getId().getCodTabla());
	    to.setAttribute ("nomTabla", from.getNomTabla());	
	    to.setAttribute ("tipTabla", from.getId().getTipTabla());
	    to.setAttribute ("tipTablaCopy", from.getId().getTipTabla());
	    to.setAttribute("userAdd", from.getUserAdd());
	}	
	
}