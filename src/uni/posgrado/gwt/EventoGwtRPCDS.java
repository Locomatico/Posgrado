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
//import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.shared.model.Evento;

import java.util.List;

public class EventoGwtRPCDS extends GwtRpcDataSource {

	public EventoGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("id", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 150, true);
        addField (field);
        field = new DataSourceTextField ("tipo", "Tipo", 10, false);
        addField (field);
        field = new DataSourceTextField ("descripcion", "Nombre", 1000, false);
        addField (field);
        field = new DataSourceDateField ("inicio", "Inicio");
        field.setRequired (true);
        addField (field);
        field = new DataSourceDateField ("fin", "Fin");
        field.setRequired (true);
        addField (field);
        field = new DataSourceBooleanField ("estado", "Estado");
        field.setRequired (false);
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final EventoGwtRPCDSServiceAsync service = GWT.create (EventoGwtRPCDSService.class);
		final Evento testRec = new Evento ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Evento>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Evento> result) {
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
	    Evento testRec = new Evento ();
	    copyValues (rec, testRec);
	    EventoGwtRPCDSServiceAsync service = GWT.create (EventoGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Evento> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Evento result) {
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
	    //ListGrid grid = (ListGrid) Canvas.getById (request.getComponentId ());
	    // Get record with old and new values combined
	    //int index = grid.getRecordIndex (rec);
	   // rec = (ListGridRecord) grid.getEditedRecord (index);
	    Evento testRec = new Evento ();
	    copyValues (rec, testRec);
	    EventoGwtRPCDSServiceAsync service = GWT.create (EventoGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Evento> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Evento result) {
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
	    //System.out.println("la data => " + JSON.encode(data));
	    Evento testRec = new Evento ();
	    copyValues (rec, testRec);
	    EventoGwtRPCDSServiceAsync service = GWT.create (EventoGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Evento to) {
	    to.setId (from.getAttributeAsInt ("id"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase());
	    if(from.getAttributeAsString ("tipo") != null)
	    	to.setTipo (from.getAttributeAsString ("tipo").toUpperCase());
	    if(from.getAttribute("estado") != null) {
	    	to.setEstado (from.getAttributeAsBoolean ("estado"));  
	    }
	    if(from.getAttribute("descripcion") != null)
	    	to.setDescripcion (from.getAttributeAsString ("descripcion"));
	    if(from.getAttribute("fin") != null)
	    	to.setFin(from.getAttributeAsDate("fin"));
	    if(from.getAttribute("inicio") != null)
	    	to.setInicio(from.getAttributeAsDate ("inicio"));
	}	
	
	private static void copyValues (Evento from, ListGridRecord to) {
	    to.setAttribute ("id", from.getId());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("tipo", from.getTipo());
	    to.setAttribute ("descripcion", from.getDescripcion());
	    to.setAttribute ("estado", from.getEstado());	
	    to.setAttribute ("fin", from.getFin());
	    to.setAttribute ("inicio", from.getInicio()); 
	}
}