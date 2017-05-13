package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.shared.model.Modalidad;
import uni.posgrado.shared.model.Requisito;
import uni.posgrado.shared.model.Programa;

import java.util.List;

public class RequisitoGwtRPCDS extends GwtRpcDataSource {

	public RequisitoGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idRequisito", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 200, true);
        //field.setRequired (true);
        //field.setAttribute("characterCasing", CharacterCasing.UPPER);
        addField (field);
        
        /*field = new DataSourceTextField ("unidCodigoUnidadAcademica", "Cod. Programa");
        field.setRequired (true);
        addField (field);
        field = new DataSourceTextField ("unidCodigoUnidadAcademicaNombre", "Programa");
        field.setRequired (false);
        field.setCanEdit(false);
        addField (field);
        */
        field = new DataSourceTextField ("programaCodigo", "programa", 100, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("programa", "Cod. Programa", 10, true);
        field.setDisplayField("programaCodigo");
        field.setEditorProperties(new ListGridSelect("Programas", 200, 200, null, new ProgramaGwtRPCDS(false), "nombre", "idPrograma", false));
        addField (field);
        field = new DataSourceTextField ("programaNombre", "Programa", 150, true);
        field.setDisplayField("programaNombre");
        addField (field);
        
        field = new DataSourceTextField ("modNombre", "Modalidad");
        field.setRequired (true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("modalidad", "Modalidad");
        field.setRequired (true);
        field.setDisplayField("modNombre");
        field.setEditorProperties(new ListGridSelect("Modalidades", 200, 200, null, new ModalidadGwtRPCDS(false, true), "nombre", "idModalidad", false));
        addField (field);
        
        field = new DataSourceTextField ("descripcion", "Descripción");
        field.setRequired (false);
        field.setCanFilter(false);
        addField (field);
        field = new DataSourceBooleanField ("estado", "Est.");
        field.setRequired (false); 
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {
	    // These can be used as parameters to create paging.
	    /*
		request.getStartRow ();
	    request.getEndRow ();        
	    request.getSortBy ();
	    */
	    final RequisitoGwtRPCDSServiceAsync service = GWT.create (RequisitoGwtRPCDSService.class);
		final Requisito testRec = new Requisito ();
		ListGridRecord rec = new ListGridRecord (request.getCriteria().getJsObj());
		copyValuesFilter(rec, testRec, false);
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Requisito>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Requisito> result) {
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
	    Requisito testRec = new Requisito ();
	    copyValues (rec, testRec, false);
	    RequisitoGwtRPCDSServiceAsync service = GWT.create (RequisitoGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Requisito> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Requisito result) {
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
	    Requisito testRec = new Requisito ();
	    copyValues (rec, testRec, false);
	    RequisitoGwtRPCDSServiceAsync service = GWT.create (RequisitoGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Requisito> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Requisito result) {
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
	    Requisito testRec = new Requisito ();
	    copyValues (rec, testRec, false);
	    RequisitoGwtRPCDSServiceAsync service = GWT.create (RequisitoGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Requisito to, Boolean is_filter) {
	    to.setIdRequisito (from.getAttributeAsInt ("idRequisito"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase().trim());
	    to.setDescripcion(from.getAttributeAsString("descripcion"));
    	to.setEstado (from.getAttributeAsBoolean ("estado"));
    	
    	if(from.getAttribute ("modalidad") != null) {
			Modalidad m = new Modalidad();
			m.setIdModalidad(from.getAttributeAsInt ("modalidad"));
			to.setModalidad(m);
		}
    	
    	if(from.getAttribute ("programa") != null || from.getAttributeAsString ("programaCodigo") != null || from.getAttributeAsString ("programaNombre") != null) {
	    	Programa programa = new Programa();
			if(from.getAttribute ("programa") != null) {
				if(is_filter)
					programa.setCodigo(from.getAttributeAsString ("programa"));
				else
					programa.setIdPrograma (from.getAttributeAsInt ("programa"));
			}				
			if(from.getAttributeAsString ("programaCodigo") != null)
				programa.setCodigo(from.getAttributeAsString ("programaCodigo"));
			if(from.getAttributeAsString ("programaNombre") != null)
				programa.setNombre(from.getAttributeAsString ("programaNombre"));
			to.setPrograma(programa);
		}
	}
	
	static void copyValuesFilter (ListGridRecord from, Requisito to, Boolean is_filter) {
	    to.setIdRequisito (from.getAttributeAsInt ("idRequisito"));
	    to.setNombre (from.getAttributeAsString ("nombre"));	    
	    if(from.getAttribute("estado") != null) {
	    	to.setEstado (from.getAttributeAsBoolean ("estado"));
	    }
	    
	    if(from.getAttribute ("modalidad") != null) {
			Modalidad m = new Modalidad();
			m.setIdModalidad(from.getAttributeAsInt ("modalidad"));
			to.setModalidad(m);
		}
	    
	    if(from.getAttribute ("programa") != null || from.getAttributeAsString ("programaCodigo") != null || from.getAttributeAsString ("programaNombre") != null) {
	    	Programa programa = new Programa();
			if(from.getAttribute ("programa") != null) {
				if(!is_filter)
					programa.setCodigo(from.getAttributeAsString ("programa"));
				else
					programa.setIdPrograma (from.getAttributeAsInt ("programa"));
			}				
			if(from.getAttributeAsString ("programaCodigo") != null)
				programa.setCodigo(from.getAttributeAsString ("programaCodigo"));
			if(from.getAttributeAsString ("programaNombre") != null)
				programa.setNombre(from.getAttributeAsString ("programaNombre"));
			to.setPrograma(programa);
		}
	}
	
	private static void copyValues (Requisito from, ListGridRecord to) {
	    to.setAttribute ("idRequisito", from.getIdRequisito());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("estado", from.getEstado());    
	    to.setAttribute("descripcion", from.getDescripcion());
	    
	    if( from.getModalidad() != null) {
			to.setAttribute("modNombre", from.getModalidad().getNombre());
			to.setAttribute("modalidad", from.getModalidad().getIdModalidad());
		}
	    if( from.getPrograma() != null) {
	    	to.setAttribute ("programa", from.getPrograma().getIdPrograma());
		    to.setAttribute ("programaCodigo", from.getPrograma().getCodigo());
		    to.setAttribute ("programaNombre", from.getPrograma().getNombre());
	    }
	}
}