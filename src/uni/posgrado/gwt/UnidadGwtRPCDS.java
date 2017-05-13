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
import uni.posgrado.shared.model.Unidad;

import java.util.List;


public class UnidadGwtRPCDS extends GwtRpcDataSource {

	public UnidadGwtRPCDS (Boolean hidden) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idUnidad", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("codigo", "Código", 150, true);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 150, true);
        addField (field);
        field = new DataSourceTextField ("abreviatura", "Abreviatura", 50, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("instNombre", "Inst Nombre");
        field.setRequired (true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idInstitucion", "Institución");
        field.setRequired (true);
        field.setDisplayField("instNombre");
        field.setHidden(hidden);
        field.setEditorProperties(new ListGridSelect("Instituciones", 200, 200, "nombre", new InstitucionGwtRPCDS(true), "nombre", "idInstitucion", true));
        addField (field);
        field = new DataSourceTextField ("direccion", "Dirección", 200, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("telefono", "Teléfono", 200, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("anexo", "Anexo", 200, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("email", "Email", 200, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("website", "Website", 200, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("descripcion", "Descripción", 1000, false);
        field.setHidden(hidden);
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final UnidadGwtRPCDSServiceAsync service = GWT.create (UnidadGwtRPCDSService.class);
		final Unidad testRec = new Unidad ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Unidad>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Unidad> result) {
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
	    Unidad testRec = new Unidad ();
	    copyValues (rec, testRec);
	    UnidadGwtRPCDSServiceAsync service = GWT.create (UnidadGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Unidad> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Unidad result) {
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
	    Unidad testRec = new Unidad ();
	    copyValues (rec, testRec);
	    UnidadGwtRPCDSServiceAsync service = GWT.create (UnidadGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Unidad> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Unidad result) {
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
	    Unidad testRec = new Unidad ();
	    copyValues (rec, testRec);
	    UnidadGwtRPCDSServiceAsync service = GWT.create (UnidadGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Unidad to) {
	    to.setIdUnidad (from.getAttributeAsInt ("idUnidad"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre"));
	    if(from.getAttributeAsString ("codigo") != null)
	    	to.setCodigo (from.getAttributeAsString ("codigo").toUpperCase());
	    if(from.getAttributeAsString ("abreviatura") != null)
	    	to.setAbreviatura (from.getAttributeAsString ("abreviatura"));
	    if(from.getAttributeAsString ("descripcion") != null)
	    	to.setDescripcion (from.getAttributeAsString ("descripcion"));
	    if(from.getAttributeAsString ("direccion") != null)
	    	to.setDireccion (from.getAttributeAsString ("direccion"));
	    if(from.getAttributeAsString ("telefono") != null)
	    	to.setTelefono (from.getAttributeAsString ("telefono"));
	    if(from.getAttributeAsString ("anexo") != null)
	    	to.setAnexo (from.getAttributeAsString ("anexo"));
	    if(from.getAttributeAsString ("email") != null)
	    	to.setEmail (from.getAttributeAsString ("email"));
	    if(from.getAttributeAsString ("website") != null)
	    	to.setWebsite (from.getAttributeAsString ("website"));
	    
	    if(from.getAttribute ("idInstitucion") != null || from.getAttributeAsString ("instNombre") != null) {
			Institucion inst = new Institucion();
			if(from.getAttribute ("idInstitucion") != null)
				inst.setIdInstitucion (from.getAttributeAsInt ("idInstitucion"));
			if(from.getAttributeAsString ("instNombre") != null)
				inst.setNombre(from.getAttributeAsString ("instNombre"));
			to.setInstitucion(inst);
		} 
	}	
	
	private static void copyValues (Unidad from, ListGridRecord to) {
	    to.setAttribute ("idUnidad", from.getIdUnidad());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("codigo", from.getCodigo());
	    to.setAttribute ("abreviatura", from.getAbreviatura());
	    to.setAttribute ("descripcion", from.getDescripcion());
	    to.setAttribute ("direccion", from.getDireccion());
	    to.setAttribute ("telefono", from.getTelefono());
	    to.setAttribute ("anexo", from.getAnexo());
	    to.setAttribute ("email", from.getEmail());
	    to.setAttribute ("website", from.getWebsite());
	    to.setAttribute ("telefono", from.getTelefono());
	    to.setAttribute ("idInstitucion", from.getInstitucion().getIdInstitucion());
	    to.setAttribute ("instNombre", from.getInstitucion().getNombre());
	}
}