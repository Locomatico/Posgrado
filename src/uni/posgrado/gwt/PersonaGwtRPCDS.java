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

import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.shared.model.Persona;
import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;

import java.util.List;

public class PersonaGwtRPCDS extends GwtRpcDataSource {

	public PersonaGwtRPCDS (Boolean hidden) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idPersona", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("tipoDocumentoNombre", "tipoDocumento", 100, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipoDocumento", "Tipo de documento ", 10, true);
        field.setDisplayField("tipoDocumentoNombre");
        field.setEditorProperties(new ListGridSelect("Tipo de documento", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPIDE"), "nomTabla", "codTabla", true));
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("numeroDocumento", "Numero de documento", 20, true);
        addField (field);
        field = new DataSourceTextField ("nombreCompleto", "Nombre", 250, false);
        field.setHidden(!hidden);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 150, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("segundoNombre", "Segundo Nombre", 150, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("apellidoPaterno", "A. Paterno", 150, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("apellidoMaterno", "A. Materno", 150, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("correoInstitucion", "Correo", 150, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("estadoCivilNombre", "estadoCivil", 100, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("estadoCivil", "Estado civil", 10, true);
        field.setDisplayField("estadoCivilNombre");
        field.setEditorProperties(new ListGridSelect("Estado civil", 200, 200, "codTabla", new VariableFormGwtRPCDS("ESTCIV"), "nomTabla", "codTabla", true));
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceDateField ("fechaNacimiento", "Fecha de nacimiento", 20, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceBooleanField ("indicadorVive", "Vive", 10, false);
        field.setHidden(hidden);
        addField (field);

	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final PersonaGwtRPCDSServiceAsync service = GWT.create (PersonaGwtRPCDSService.class);
		final Persona testRec = new Persona ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Persona>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Persona> result) {
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
	    Persona testRec = new Persona ();
	    copyValues (rec, testRec, false);
	    PersonaGwtRPCDSServiceAsync service = GWT.create (PersonaGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Persona> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Persona result) {
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
	    Persona testRec = new Persona ();
	    copyValues (rec, testRec, false);
	    PersonaGwtRPCDSServiceAsync service = GWT.create (PersonaGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Persona> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Persona result) {
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
	    Persona testRec = new Persona ();
	    copyValues (rec, testRec, false);
	    PersonaGwtRPCDSServiceAsync service = GWT.create (PersonaGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Persona to, Boolean is_filter) {
	    to.setIdPersona (from.getAttributeAsInt ("idPersona"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase());
	    if(from.getAttributeAsString ("apellidoMaterno") != null)
	    	to.setApellidoMaterno (from.getAttributeAsString ("apellidoMaterno").toUpperCase());
	    if(from.getAttributeAsString ("apellidoPaterno") != null)
	    	to.setApellidoPaterno (from.getAttributeAsString ("apellidoPaterno").toUpperCase());
	    if(from.getAttributeAsString ("segundoNombre") != null)
	    	to.setSegundoNombre (from.getAttributeAsString ("segundoNombre").toUpperCase());
	    if(from.getAttributeAsString ("correoInstitucion") != null)
	    	to.setCorreoInstitucion (from.getAttributeAsString ("correoInstitucion"));
	    if(from.getAttribute ("estadoCivil") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("ESTCIV");
			var_pk.setCodTabla(from.getAttributeAsString ("estadoCivil"));
			var.setId(var_pk);
			to.setEstadoCivil(var);
		}
	    if(from.getAttributeAsString ("fechaNacimiento") != null)
	    	to.setFechaNacimiento (from.getAttributeAsDate ("fechaNacimiento"));
	    
	    if(is_filter) {
	    	if(from.getAttribute("indicadorVive") != null) 
	    		to.setIndicadorVive(from.getAttributeAsBoolean ("indicadorVive"));
	    	if(from.getAttribute("nombreCompleto") != null) 
	    		to.setNombreCompleto(from.getAttributeAsString("nombreCompleto"));
	    	
	    } else {
	    	to.setIndicadorVive(from.getAttributeAsBoolean ("indicadorVive"));
	    	String  nombreCompleto = from.getAttributeAsString ("apellidoPaterno").toUpperCase().trim();
	    	if(from.getAttributeAsString ("apellidoMaterno") != null)
	    		nombreCompleto += " " + from.getAttributeAsString ("apellidoMaterno").toUpperCase().trim();
	    	nombreCompleto += " " + from.getAttributeAsString ("nombre").toUpperCase().trim();
	    	if(from.getAttributeAsString ("segundoNombre") != null)
	    		nombreCompleto += " " + from.getAttributeAsString ("segundoNombre").toUpperCase().trim();
	    	to.setNombreCompleto(nombreCompleto);
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
	}	
	
	private static void copyValues (Persona from, ListGridRecord to) {
	    to.setAttribute ("idPersona", from.getIdPersona());
	    to.setAttribute ("nombreCompleto", from.getNombreCompleto());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("apellidoMaterno", from.getApellidoMaterno());
	    to.setAttribute ("apellidoPaterno", from.getApellidoPaterno());
	    to.setAttribute ("segundoNombre", from.getSegundoNombre());
	    to.setAttribute ("correoInstitucion", from.getCorreoInstitucion());
	    to.setAttribute ("fechaNacimiento", from.getFechaNacimiento());
	    to.setAttribute ("indicadorVive", from.getIndicadorVive());
	    to.setAttribute ("numeroDocumento", from.getNumeroDocumento());
	    if( from.getEstadoCivil() != null) {
	    	to.setAttribute ("estadoCivil", from.getEstadoCivil().getId().getCodTabla());
		    to.setAttribute ("estadoCivilNombre", from.getEstadoCivil().getNomTabla());
	    }
	    if( from.getTipoDocumento() != null) {
	    	to.setAttribute ("tipoDocumento", from.getTipoDocumento().getId().getCodTabla());
		    to.setAttribute ("tipoDocumentoNombre", from.getTipoDocumento().getNomTabla());
	    }
	}
}