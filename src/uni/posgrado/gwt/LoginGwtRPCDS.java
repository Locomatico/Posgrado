package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourcePasswordField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.grid.ListGridRecord;


public class LoginGwtRPCDS extends GwtRpcDataSource {

	public LoginGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceTextField ("username", "Username");
        field.setRequired (true);
        addField (field); 
        field = new DataSourcePasswordField ("password", "Contraseña");
        field.setRequired (true);
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	}
	
	@Override
	protected void executeAdd (final String requestId, final DSRequest request, final DSResponse response) {
	    // Retrieve record which should be added.
	    JavaScriptObject data = request.getData ();
	    ListGridRecord login = new ListGridRecord (data);
	    
	    LoginGwtRPCDSServiceAsync service = GWT.create (LoginGwtRPCDSService.class);
	    service.login (login.getAttributeAsString ("username"), login.getAttributeAsString ("password"), new AsyncCallback<Boolean> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Boolean result) {
	        	response.setAttribute("is_login", result);
	            processResponse (requestId, response);
	        }
	    });
	}	

	@Override
	protected void executeUpdate(String requestId, DSRequest request,
			DSResponse response) {
		// TODO Auto-generated method stub		
	}

	@Override
	protected void executeRemove(String requestId, DSRequest request,
			DSResponse response) {
		// TODO Auto-generated method stub		
	}	
}