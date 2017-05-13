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
import uni.posgrado.shared.model.Usuario;
import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;

import java.util.List;

public class UsuarioGwtRPCDS extends GwtRpcDataSource {

	public UsuarioGwtRPCDS (Boolean hidden) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idUsuario", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("descripcion", "Descripcion", 250, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("tipoDocumentoNombre", "tipoDocumento", 100, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipoDocumento", "Tipo de documento ", 10, false);
        field.setDisplayField("tipoDocumentoNombre");
        field.setCanEdit(false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("numeroDocumento", "Numero de documento", 20, false);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("nombreCompleto", "persona", 250, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("persona", "Nombre", 10, true);
        field.setDisplayField("nombreCompleto");
        if(!hidden)
        	field.setEditorProperties(new ListGridSelect("Personas", 200, 250, "nombreCompleto", new PersonaGwtRPCDS(true), "nombreCompleto", "idPersona", true));
        addField (field);        
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final UsuarioGwtRPCDSServiceAsync service = GWT.create (UsuarioGwtRPCDSService.class);
		final Usuario testRec = new Usuario ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Usuario>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Usuario> result) {
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
	    Usuario testRec = new Usuario ();
	    copyValues (rec, testRec, false);
	    UsuarioGwtRPCDSServiceAsync service = GWT.create (UsuarioGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Usuario> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Usuario result) {
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
	    Usuario testRec = new Usuario ();
	    copyValues (rec, testRec, false);
	    UsuarioGwtRPCDSServiceAsync service = GWT.create (UsuarioGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Usuario> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Usuario result) {
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
	    Usuario testRec = new Usuario ();
	    copyValues (rec, testRec, false);
	    UsuarioGwtRPCDSServiceAsync service = GWT.create (UsuarioGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, final Usuario to, Boolean is_filter) {
	    to.setIdUsuario (from.getAttributeAsInt ("idUsuario"));	    
	    if(from.getAttributeAsString ("descripcion") != null)
	    	to.setDescripcion (from.getAttributeAsString ("descripcion"));
	    
	    if(from.getAttribute ("persona") != null) {
	    	Persona per = new Persona();	    	
		    if(is_filter)
		    	per.setNombreCompleto(from.getAttributeAsString ("persona"));
		    else
		    	per.setIdPersona(from.getAttributeAsInt ("persona"));
		    to.setPersona(per);
	    }	    
	    if(from.getAttributeAsString ("numeroDocumento") != null)
	    	to.setNumeroDocumento (from.getAttributeAsString ("numeroDocumento"));
	    
	    if(from.getAttribute ("tipoDocumento") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPIDE");
			var_pk.setCodTabla(from.getAttributeAsString ("tipoDocumento"));
			var.setId(var_pk);
			to.setTipoDocumento(var);
		}
	    
	    if(is_filter) {
	    	if(from.getAttributeAsString ("nombreCompleto") != null)
		    	to.setNombreCompleto (from.getAttributeAsString ("nombreCompleto"));	    	

	    } else {
	    	to.setNombreCompleto (from.getAttributeAsString ("nombreCompleto"));	    	

	    }
	}	
	
	private static void copyValues (Usuario from, ListGridRecord to) {
	    to.setAttribute ("idUsuario", from.getIdUsuario());
	    to.setAttribute ("nombreCompleto", from.getNombreCompleto());
	    to.setAttribute ("descripcion", from.getDescripcion());
	    if( from.getTipoDocumento() != null) {
	    	to.setAttribute ("tipoDocumento", from.getTipoDocumento().getId().getCodTabla());
		    to.setAttribute ("tipoDocumentoNombre", from.getTipoDocumento().getNomTabla());
	    }
	    to.setAttribute ("numeroDocumento", from.getNumeroDocumento());
	    if(from.getPersona() != null) {
	    	to.setAttribute ("persona", from.getPersona().getIdPersona());
	    	//to.setAttribute ("nombreCompleto", from.getPersona().getNombreCompleto());
	    }	    	
	}
}