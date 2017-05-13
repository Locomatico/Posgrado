package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.shared.model.Opcion;

import java.util.List;

public class OpcionGwtRPCDS extends GwtRpcDataSource {

	public OpcionGwtRPCDS () {
	    DataSourceField field;
	    field = new DataSourceIntegerField ("idOpcion", "ID");
	    field.setPrimaryKey (true);	     
	    // AutoIncrement on server.
	    field.setRequired (false);
	    field.setHidden(true);
	    addField (field);
	    field = new DataSourceTextField ("idRol", "ID Rol");
	    field.setRequired (false);
	    field.setHidden(true);
	    addField (field);
	    field = new DataSourceTextField ("icon", "Icon");
	    field.setRequired (true);
	    field.setType(FieldType.IMAGE);
	    field.setCanFilter(false);	    
	    addField (field);
	    field = new DataSourceTextField ("codigo", "Codigo");
	    field.setRequired (true);	  
	    addField (field);
	    field = new DataSourceTextField ("nombre", "Nombre");
	    field.setRequired (true);	    
	    addField (field);
	    field = new DataSourceTextField ("entorno", "Grupo");
	    field.setRequired (true);	    
	    field.setHidden(true);	
	    addField (field);
	}   
	
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {
	    // These can be used as parameters to create paging. 	
		
	    final OpcionGwtRPCDSServiceAsync service = GWT.create (OpcionGwtRPCDSService.class);
	    ListGridRecord rec = new ListGridRecord (request.getCriteria().getJsObj());
		final Opcion testRec = new Opcion ();
		testRec.setIdRol(new String(Cookies.getCookie("roles")));
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Opcion>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Opcion> result) {
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
	
	public static void copyValues (ListGridRecord from, Opcion to) {
		to.setIdOpcion (from.getAttributeAsInt ("idOpcion"));
		 to.setNombre (from.getAttributeAsString ("nombre"));
		 to.setCodigo (from.getAttributeAsString ("codigo"));
		 to.setEntorno (from.getAttributeAsString("entorno"));
	    if(from.getAttribute ("idRol") != null) {
	    	to.setIdRol(from.getAttributeAsString("idRol"));
	    }
	}
	
	private static void copyValues (Opcion from, ListGridRecord to) {
	    to.setAttribute ("idOpcion", from.getIdOpcion());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("codigo", from.getCodigo());
	    to.setAttribute ("entorno", from.getEntorno());
	    to.setAttribute("icon", "icons/headerIcon.png");
	}


	@Override
	protected void executeAdd(String requestId, DSRequest request,
			DSResponse response) {
		// TODO Auto-generated method stub
		
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

