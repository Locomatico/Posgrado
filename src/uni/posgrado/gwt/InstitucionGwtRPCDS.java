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

import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.shared.model.Institucion;
import uni.posgrado.shared.model.Persona;

import java.util.List;

public class InstitucionGwtRPCDS extends GwtRpcDataSource {

	public InstitucionGwtRPCDS (Boolean hidden) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idInstitucion", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 150, true);
        addField (field);  
        field = new DataSourceTextField ("nombreCompleto", "persona", 250, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("persona", "Nombre", 10, false);
        field.setDisplayField("nombreCompleto");
        field.setHidden(hidden);
        field.setEditorProperties(new ListGridSelect("Personas", 200, 250, "nombreCompleto", new PersonaGwtRPCDS(true), "nombreCompleto", "idPersona", false));
        addField (field);
        
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final InstitucionGwtRPCDSServiceAsync service = GWT.create (InstitucionGwtRPCDSService.class);
		final Institucion testRec = new Institucion ();
		ListGridRecord rec = new ListGridRecord (request.getCriteria().getJsObj());
		copyValues(rec, testRec, true);
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Institucion>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Institucion> result) {
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
	    Institucion testRec = new Institucion ();
	    copyValues (rec, testRec, false);
	    InstitucionGwtRPCDSServiceAsync service = GWT.create (InstitucionGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Institucion> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Institucion result) {
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
	    Institucion testRec = new Institucion ();
	    copyValues (rec, testRec, false);
	    InstitucionGwtRPCDSServiceAsync service = GWT.create (InstitucionGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Institucion> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Institucion result) {
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
	    Institucion testRec = new Institucion ();
	    copyValues (rec, testRec, false);
	    InstitucionGwtRPCDSServiceAsync service = GWT.create (InstitucionGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Institucion to, Boolean is_filter) {
	    to.setIdInstitucion (from.getAttributeAsInt ("idInstitucion"));	    
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase());
	    
	    if(from.getAttribute ("persona") != null) {
	    	Persona per = new Persona();	    	
		    if(is_filter)
		    	per.setNombreCompleto(from.getAttributeAsString ("persona"));
		    else
		    	per.setIdPersona(from.getAttributeAsInt ("persona"));
		    to.setPersona(per);
	    }
	    
	}
	
	private static void copyValues (Institucion from, ListGridRecord to) {
	    to.setAttribute ("idInstitucion", from.getIdInstitucion());
	    to.setAttribute ("nombre", from.getNombre());
	    if(from.getPersona() != null) {
	    	to.setAttribute ("persona", from.getPersona().getIdPersona());
	    	to.setAttribute ("nombreCompleto", from.getPersona().getNombreCompleto());
	    }	
	}
}