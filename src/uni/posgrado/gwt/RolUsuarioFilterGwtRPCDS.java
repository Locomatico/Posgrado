package uni.posgrado.gwt;

import java.util.ArrayList;
import java.util.List;

import uni.posgrado.shared.model.RolUsuario;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntEnumField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class RolUsuarioFilterGwtRPCDS extends GwtRpcDataSource {
	
	public RolUsuarioFilterGwtRPCDS () {	    
		DataSourceField field;
        field = new DataSourceIntegerField ("idUsuario", "idUsuario");
        addField (field);
        field = new DataSourceIntEnumField("usuarios", "usuarios");
        addField(field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final RolUsuarioGwtRPCDSServiceAsync service = GWT.create (RolUsuarioGwtRPCDSService.class);
		final RolUsuario testRec = new RolUsuario ();
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

	}
	
	@Override
	protected void executeUpdate (final String requestId, final DSRequest request, final DSResponse response) {
		
	}
	
	@Override
	protected void executeRemove (final String requestId, final DSRequest request, final DSResponse response) {
		
	}
	
	
	static void copyValues (ListGridRecord from, RolUsuario to) {
		/*if(from.getAttribute ("idUsuario") != null) {
			RolUsuarioPK pe = new RolUsuarioPK();
			pe.setIdUsuario (from.getAttributeAsInt ("idUsuario"));
			to.setId(pe);
		}*/
		if(from.getAttribute("usuarios") != null) {
			ArrayList<Integer> usuarios = new ArrayList<Integer>();
			int[] records = from.getAttributeAsIntArray("usuarios");
			for (int record: records) {
				usuarios.add(record);
			}			
			to.setUsuarios(usuarios);			
		}		
	}
	
	private static void copyValues (RolUsuario from, ListGridRecord to) {
		to.setAttribute ("idRol", from.getId().getIdRol());
	}

}