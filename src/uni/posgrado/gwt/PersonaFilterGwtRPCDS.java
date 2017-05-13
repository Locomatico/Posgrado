package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.shared.model.Persona;

import java.util.List;

public class PersonaFilterGwtRPCDS extends GwtRpcDataSource {
	
	public PersonaFilterGwtRPCDS () {	 
	    DataSourceField field;        
        field = new DataSourceTextField ("email", "Email");
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {
  	
	    final PersonaGwtRPCDSServiceAsync service = GWT.create (PersonaGwtRPCDSService.class);
		final Persona testRec = new Persona ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Persona>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Persona> result) {
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
	   
	}
	
	@Override
	protected void executeUpdate (final String requestId, final DSRequest request, final DSResponse response) {
	   
	}
	
	@Override
	protected void executeRemove (final String requestId, final DSRequest request, final DSResponse response) {
	    
	}
	
	static void copyValues (ListGridRecord from, Persona to) {
	    if(from.getAttributeAsString ("email") != null)
	    	to.setCorreoInstitucion(from.getAttributeAsString ("email").toUpperCase().trim());
	}
	
	static void copyValuesFilter (ListGridRecord from, Persona to) {
	    to.setCorreoInstitucion(from.getAttributeAsString ("email"));
	}
	
	private static void copyValues (Persona from, ListGridRecord to) {
		to.setAttribute ("idPersona", from.getIdPersona());
	    to.setAttribute ("email", from.getCorreoInstitucion());
	}	
}