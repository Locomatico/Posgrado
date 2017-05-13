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
import uni.posgrado.shared.model.Persona;
import uni.posgrado.shared.model.Programa;
import uni.posgrado.shared.model.Usuario;
import uni.posgrado.shared.model.VinculoDocente;

import java.util.List;

public class VinculoDocenteGwtRPCDS extends GwtRpcDataSource {

	public VinculoDocenteGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idVinculoDocente", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("numeroDocumento", "Num. documento", 20, false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("usuarioNombre", "usuario", 250, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("usuario", "Nombre", 10, true);
        field.setDisplayField("usuarioNombre");
        field.setEditorProperties(new ListGridSelect("Usuario", 300, 300, "idUsuario", new UsuarioGwtRPCDS(true), "persona", "idUsuario", true));
        addField (field);
        field = new DataSourceTextField ("programaCodigo", "Código Prog.", 150, false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("programaNombre", "programa", 100, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("programa", "Programa", 10, true);
        field.setDisplayField("programaNombre");
        field.setEditorProperties(new ListGridSelect("Programas", 200, 300, "nombre", new ProgramaGwtRPCDS(true), "nombre", "idPrograma", true));
        addField (field);
        
        field = new DataSourceIntegerField("idSeccion", "Seccion");
        field.setRequired (false);
        field.setCanEdit(false);
        field.setHidden(true);
        addField (field);       
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final VinculoDocenteGwtRPCDSServiceAsync service = GWT.create (VinculoDocenteGwtRPCDSService.class);
		final VinculoDocente testRec = new VinculoDocente ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<VinculoDocente>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<VinculoDocente> result) {
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
	    VinculoDocente testRec = new VinculoDocente ();
	    copyValues (rec, testRec);
	    VinculoDocenteGwtRPCDSServiceAsync service = GWT.create (VinculoDocenteGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<VinculoDocente> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (VinculoDocente result) {
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
	    VinculoDocente testRec = new VinculoDocente ();
	    copyValues (rec, testRec);
	    VinculoDocenteGwtRPCDSServiceAsync service = GWT.create (VinculoDocenteGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<VinculoDocente> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (VinculoDocente result) {
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
	    VinculoDocente testRec = new VinculoDocente ();
	    copyValues (rec, testRec);
	    VinculoDocenteGwtRPCDSServiceAsync service = GWT.create (VinculoDocenteGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, VinculoDocente to) {
	    to.setIdVinculoDocente (from.getAttributeAsInt ("idVinculoDocente"));
	    if(from.getAttribute ("programa") != null || from.getAttributeAsString ("programaNombre") != null || from.getAttributeAsString ("programaCodigo") != null) {
	    	Programa programa = new Programa();
			if(from.getAttribute ("programa") != null) {
					programa.setIdPrograma (from.getAttributeAsInt ("programa"));
			}				
			if(from.getAttributeAsString ("programaNombre") != null)
				programa.setNombre(from.getAttributeAsString ("programaNombre"));
			if(from.getAttributeAsString ("programaCodigo") != null)
				programa.setCodigo(from.getAttributeAsString ("programaCodigo"));
			to.setPrograma(programa);
		}
	    if(from.getAttribute ("usuario") != null || from.getAttributeAsString ("usuarioNombre") != null || from.getAttributeAsString ("numeroDocumento") != null) {
	    	Usuario usuario = new Usuario();
			if(from.getAttribute ("usuario") != null) {
				usuario.setIdUsuario (from.getAttributeAsInt ("usuario"));
			}				
			if(from.getAttributeAsString ("numeroDocumento") != null) {
		    	Persona per = new Persona();
		    	if(from.getAttributeAsString ("numeroDocumento") != null)
		    		per.setNumeroDocumento (from.getAttributeAsString ("numeroDocumento"));			   
		    	usuario.setPersona(per);
		    }
			to.setUsuario(usuario);
		}
	    if(from.getAttribute ("idSeccion") != null) {
    		to.setIdSeccion(from.getAttributeAsInt("idSeccion"));
    	}

	}	
	
	private static void copyValues (VinculoDocente from, ListGridRecord to) {
	    to.setAttribute ("idVinculoDocente", from.getIdVinculoDocente());
	    if( from.getPrograma() != null) {
	    	to.setAttribute ("programa", from.getPrograma().getIdPrograma());
		    to.setAttribute ("programaNombre", from.getPrograma().getNombre());
		    to.setAttribute ("programaCodigo", from.getPrograma().getCodigo());
	    }
	    if( from.getUsuario() != null) {
	    	to.setAttribute ("usuario", from.getUsuario().getIdUsuario());
	    	if(from.getUsuario().getPersona() != null) {
	    		to.setAttribute ("usuarioNombre", from.getUsuario().getPersona().getNombre());
			    to.setAttribute ("numeroDocumento", from.getUsuario().getPersona().getNumeroDocumento());
	    	}		    
	    }
	}
}