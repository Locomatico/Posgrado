package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.shared.model.Modalidad;

import java.util.List;

public class ModalidadGwtRPCDS extends GwtRpcDataSource {
	
	private static Boolean isAllView = false;

	public ModalidadGwtRPCDS (Boolean state, Boolean isAll) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idModalidad", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 150, true);
        addField (field);
        field = new DataSourceTextField ("modNombreShort", "Nombre Corto", 50, false);
        if(!state)
        	field.setHidden(true);
        //field.setRequired (true);
       // field.setAttribute("characterCasing", CharacterCasing.UPPER);
        addField (field);
        field = new DataSourceTextField ("descripcion", "Descripción");
        field.setRequired (false);
        field.setCanFilter(false);
        if(!state)
        	field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("modParticipantes", "Participantes");
        field.setRequired (false);
        field.setCanFilter(false);
        if(!state)
        	field.setHidden(true);
        addField (field);
        
        field = new DataSourceTextField ("modNombrePadre", "Modalidad Padre Nombre");
        field.setRequired (false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idModPadre", "Modalidad Padre");
        field.setRequired (false);
        field.setDisplayField("modNombrePadre");
        if(!state)
        	field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("peso", "Orden");
        field.setRequired (true);
        if(!state)
        	field.setHidden(true);
        addField (field);   
        isAllView = isAll;
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {
	    // These can be used as parameters to create paging.
	    /*
		request.getStartRow ();
	    request.getEndRow ();        
	    request.getSortBy ();
	    */   	
	    final ModalidadGwtRPCDSServiceAsync service = GWT.create (ModalidadGwtRPCDSService.class);	    
		final Modalidad testRec = new Modalidad ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Modalidad>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Modalidad> result) {
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
	    Modalidad testRec = new Modalidad ();
	    copyValues (rec, testRec);
	    ModalidadGwtRPCDSServiceAsync service = GWT.create (ModalidadGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Modalidad> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Modalidad result) {
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
	    Modalidad testRec = new Modalidad ();
	    copyValues (rec, testRec);
	    ModalidadGwtRPCDSServiceAsync service = GWT.create (ModalidadGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Modalidad> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Modalidad result) {
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
	    Modalidad testRec = new Modalidad ();
	    copyValues (rec, testRec);
	    ModalidadGwtRPCDSServiceAsync service = GWT.create (ModalidadGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Modalidad to) {
	    to.setIdModalidad (from.getAttributeAsInt ("idModalidad"));
	    to.setDescripcion (from.getAttributeAsString ("descripcion"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase());
	    if(from.getAttributeAsString ("modNombreShort") != null)
	    	to.setModNombreShort (from.getAttributeAsString ("modNombreShort").toUpperCase());
	    to.setModParticipantes(from.getAttributeAsString("modParticipantes"));
	    to.setPeso(from.getAttributeAsInt("peso"));
	    if(from.getAttribute ("idModPadre") != null && from.getAttributeAsInt ("idModPadre") != from.getAttributeAsInt ("idModalidad")) {
			Modalidad m = new Modalidad();
			m.setIdModalidad (from.getAttributeAsInt ("idModPadre"));
			to.setIdModPadre(m);
		}
	    if(isAllView == true)
	    	to.setEsTodos(1);
	    
	}
	
	static void copyValuesFilter (ListGridRecord from, Modalidad to) {
		to.setIdModalidad (from.getAttributeAsInt ("idModalidad"));
	    to.setDescripcion (from.getAttributeAsString ("descripcion"));
    	to.setNombre (from.getAttributeAsString ("nombre"));
    	to.setModNombreShort (from.getAttributeAsString ("modNombreShort"));
	    to.setModParticipantes(from.getAttributeAsString("modParticipantes"));
	    if(from.getAttribute ("peso") != null) {
	    	try {	    		
	    		to.setPeso (Integer.parseInt(from.getAttribute ("peso").toString()));
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }			
	    if(from.getAttribute ("idModPadre") != null) {
			Modalidad m = new Modalidad();
			m.setNombre (from.getAttributeAsString ("idModPadre"));
			to.setIdModPadre(m);
		}
	    if(isAllView == true)
	    	to.setEsTodos(1);
	}
	
	private static void copyValues (Modalidad from, ListGridRecord to) {
	    to.setAttribute ("idModalidad", from.getIdModalidad());
	    to.setAttribute ("descripcion", from.getDescripcion());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("modNombreShort", from.getModNombreShort());
	    to.setAttribute("modParticipantes", from.getModParticipantes());
	    to.setAttribute("peso", from.getPeso());
	    if( from.getIdModPadre() != null) {
			to.setAttribute("modNombrePadre", from.getIdModPadre().getNombre());
			to.setAttribute("idModPadre", from.getIdModPadre().getIdModalidad());
		}
	}	
}