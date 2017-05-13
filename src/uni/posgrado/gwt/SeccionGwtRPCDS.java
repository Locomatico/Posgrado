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
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.shared.model.Malla;
import uni.posgrado.shared.model.Seccion;

import java.util.List;

public class SeccionGwtRPCDS extends GwtRpcDataSource {

	public SeccionGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idSeccion", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("codigo", "Codigo");
	    field.setRequired (true);	  
	    addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 150, true);
        addField (field);
        field = new DataSourceIntegerField ("cupo", "Cupos", 10, true);
        addField (field);
        field = new DataSourceIntegerField ("maximoCupo", "Cupo maximo", 10, true);
        addField (field);
        field = new DataSourceIntegerField ("totalHoraPractica", "Horas de practica", 10, true);
        addField (field);
        field = new DataSourceIntegerField ("totalHoraTeorica", "Horas de teoria", 10, true);
        addField (field);
        field = new DataSourceIntegerField ("malla", "malla", 10, true);
        addField (field);
        field = new DataSourceDateField ("fechaInicio", "Inicio", 20, false);
        addField (field);
        field = new DataSourceDateField ("fechaFin", "Fin", 20, false);
        addField (field);
        field = new DataSourceTextField ("observacion", "Observacion", 1000, false);
        addField (field);
        field = new DataSourceBooleanField ("estado", "Estado", 10, false);
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final SeccionGwtRPCDSServiceAsync service = GWT.create (SeccionGwtRPCDSService.class);
		final Seccion testRec = new Seccion ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Seccion>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Seccion> result) {
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
	    Seccion testRec = new Seccion ();
	    copyValues (rec, testRec);
	    SeccionGwtRPCDSServiceAsync service = GWT.create (SeccionGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Seccion> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Seccion result) {
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
	    Seccion testRec = new Seccion ();
	    copyValues (rec, testRec);
	    SeccionGwtRPCDSServiceAsync service = GWT.create (SeccionGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Seccion> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Seccion result) {
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
	    Seccion testRec = new Seccion ();
	    copyValues (rec, testRec);
	    SeccionGwtRPCDSServiceAsync service = GWT.create (SeccionGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Seccion to) {
	    to.setIdSeccion (from.getAttributeAsInt ("idSeccion"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase());
	    if(from.getAttributeAsString ("codigo") != null)
	    	to.setCodigo (from.getAttributeAsString ("codigo").toUpperCase());
	    if(from.getAttribute ("cupo") != null)
	    	to.setCupo (Integer.parseInt(from.getAttribute ("cupo")));
	    if(from.getAttribute("estado") != null) {
	    	to.setEstado (from.getAttributeAsBoolean ("estado"));
	    }
	    if(from.getAttributeAsString ("fechaInicio") != null)
	    	to.setFechaInicio (from.getAttributeAsDate ("fechaInicio"));
	    if(from.getAttributeAsString ("fechaFin") != null)
	    	to.setFechaFin (from.getAttributeAsDate ("fechaFin"));
	    if(from.getAttribute ("malla") != null) {
	    	Malla malla = new Malla();
			if(from.getAttribute ("malla") != null)
				malla.setIdMalla (from.getAttributeAsInt ("malla"));
			to.setMalla(malla);
		}
	    if(from.getAttribute ("maximoCupo") != null)
	    	to.setMaximoCupo (Integer.parseInt(from.getAttribute ("maximoCupo")));
	    if(from.getAttributeAsString ("observacion") != null)
	    	to.setObservacion (from.getAttributeAsString ("observacion"));
	    if(from.getAttribute ("totalHoraPractica") != null)
	    	to.setTotalHoraPractica (Long.parseLong(from.getAttribute ("totalHoraPractica")));
	    if(from.getAttribute ("totalHoraTeorica") != null)
	    	to.setTotalHoraTeorica (Long.parseLong(from.getAttribute ("totalHoraTeorica")));	    
	}	
	
	private static void copyValues (Seccion from, ListGridRecord to) {
	    to.setAttribute ("idSeccion", from.getIdSeccion());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("codigo", from.getCodigo());
	    to.setAttribute ("cupo", from.getCupo());
	    to.setAttribute ("estado", from.getEstado());
	    to.setAttribute ("fechaInicio", from.getFechaInicio());
	    to.setAttribute ("fechaFin", from.getFechaFin());
	    to.setAttribute ("maximoCupo", from.getMaximoCupo());
	    to.setAttribute ("observacion", from.getObservacion());
	    to.setAttribute ("totalHoraPractica", from.getTotalHoraPractica());
	    to.setAttribute ("totalHoraTeorica", from.getTotalHoraTeorica());
	    if( from.getMalla() != null) {
	    	to.setAttribute ("malla", from.getMalla().getIdMalla());
	    }
	}
}