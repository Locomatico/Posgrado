package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.shared.model.Periodo;

import java.util.List;

public class PeriodoGwtRPCDS extends GwtRpcDataSource {

	public PeriodoGwtRPCDS (Boolean hidden) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idPeriodo", "ID", 15,true);
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("codigo", "Codigo", 150, true);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 150, true);
        addField (field);
        field = new DataSourceDateField ("fechaInicio", "Fecha Inicio");
        field.setRequired (true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceDateField ("fechaFin", "Fecha Fin");
        field.setRequired (true);
        field.setHidden(hidden);
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final PeriodoGwtRPCDSServiceAsync service = GWT.create (PeriodoGwtRPCDSService.class);
		final Periodo testRec = new Periodo ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Periodo>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Periodo> result) {
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
	    Periodo testRec = new Periodo ();
	    copyValues (rec, testRec);
	    PeriodoGwtRPCDSServiceAsync service = GWT.create (PeriodoGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Periodo> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Periodo result) {
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
	    Periodo testRec = new Periodo ();
	    copyValues (rec, testRec);
	    PeriodoGwtRPCDSServiceAsync service = GWT.create (PeriodoGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Periodo> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Periodo result) {
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
	    Periodo testRec = new Periodo ();
	    copyValues (rec, testRec);
	    PeriodoGwtRPCDSServiceAsync service = GWT.create (PeriodoGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Periodo to) {
	    to.setIdPeriodo (from.getAttributeAsInt ("idPeriodo"));
	    if(from.getAttributeAsString ("codigo") != null)
	    	to.setCodigo(from.getAttributeAsString ("codigo").toUpperCase());
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase());
	    to.setFechaInicio(from.getAttributeAsDate ("fechaInicio"));
	    to.setFechaFin(from.getAttributeAsDate("fechaFin"));
	}
	
	static void copyValuesFilter (ListGridRecord from, Periodo to) {
	    to.setIdPeriodo (from.getAttributeAsInt ("idPeriodo"));
	    to.setCodigo(from.getAttributeAsString ("codigo"));
	    to.setNombre (from.getAttributeAsString ("nombre"));
	    to.setFechaInicio(from.getAttributeAsDate ("fechaInicio"));
	    to.setFechaFin(from.getAttributeAsDate("fechaFin"));
	    /*if(from.getAttribute ("idPersona") != null) {
    		to.setUsuarioCreacion(from.getAttributeAsInt("idUsuario"));
    	}*/
	}
	
	private static void copyValues (Periodo from, ListGridRecord to) {
	    to.setAttribute ("idPeriodo", from.getIdPeriodo());
	    to.setAttribute ("codigo", from.getCodigo());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("fechaInicio", from.getFechaInicio());
	    to.setAttribute ("fechaFin", from.getFechaFin());
	}
}