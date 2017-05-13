package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.shared.model.Seccion;
import uni.posgrado.shared.model.SeccionHorario;

import java.util.List;

public class SeccionHorarioGwtRPCDS extends GwtRpcDataSource {

	public SeccionHorarioGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("id", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceDateField ("fechaInicio", "Hora Inicial");
        field.setRequired (true);
        addField (field);
        field = new DataSourceDateField ("fechaFin", "Hora Final");
        field.setRequired (true);
        addField (field);
        field = new DataSourceEnumField ("periodo", "Periodo");
        field.setRequired (true);
        field.setValueMap("Semanal", "Cada 2 semanas", "Cada 3 semanas", "Cada 4 semanas", "Cada 5 semanas");
        addField (field);
        field = new DataSourceIntegerField ("idSeccion", "Seccion", 10, true);
        //field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("seccion", "Seccion", 10, true);
        addField (field);
        field = new DataSourceTextField ("curso", "Curso", 10, true);
        addField (field);
        
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final SeccionHorarioGwtRPCDSServiceAsync service = GWT.create (SeccionHorarioGwtRPCDSService.class);
		final SeccionHorario testRec = new SeccionHorario ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<SeccionHorario>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<SeccionHorario> result) {
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
	    SeccionHorario testRec = new SeccionHorario ();
	    copyValues (rec, testRec);
	    SeccionHorarioGwtRPCDSServiceAsync service = GWT.create (SeccionHorarioGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<SeccionHorario> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (SeccionHorario result) {
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
	    SeccionHorario testRec = new SeccionHorario ();
	    copyValues (rec, testRec);
	    SeccionHorarioGwtRPCDSServiceAsync service = GWT.create (SeccionHorarioGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<SeccionHorario> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (SeccionHorario result) {
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
	    SeccionHorario testRec = new SeccionHorario ();
	    copyValues (rec, testRec);
	    SeccionHorarioGwtRPCDSServiceAsync service = GWT.create (SeccionHorarioGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, SeccionHorario to) {
	    to.setId (from.getAttributeAsInt ("id"));	    
	    if(from.getAttribute("fechaFin") != null)
	    	to.setFechaFin(from.getAttributeAsDate("fechaFin"));
	    if(from.getAttribute("fechaInicio") != null)
	    	to.setFechaInicio(from.getAttributeAsDate ("fechaInicio"));	    
	    if(from.getAttribute("idSeccion") != null) {
	    	Seccion seccion = new Seccion();
	    	seccion.setIdSeccion(from.getAttributeAsInt ("idSeccion"));
	    	to.setSeccion(seccion);
	    }	    	
	}	
	
	private static void copyValues (SeccionHorario from, ListGridRecord to) {
	    to.setAttribute ("id", from.getId());
	    to.setAttribute ("fechaFin", from.getFechaFin());
	    to.setAttribute ("fechaInicio", from.getFechaInicio());
	    if(from.getSeccion() != null) {
	    	to.setAttribute ("idSeccion", from.getSeccion().getIdSeccion());
	    	to.setAttribute ("seccion", from.getSeccion().getNombre());
	    	if(from.getSeccion().getMalla() != null)
	    		to.setAttribute ("curso", from.getSeccion().getMalla().getDescripcion());
	    }	    
	}
}