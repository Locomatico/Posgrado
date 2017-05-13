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
import uni.posgrado.shared.model.Persona;
import uni.posgrado.shared.model.Representante;
import uni.posgrado.shared.model.Empresa;

import java.util.List;

public class RepresentanteGwtRPCDS extends GwtRpcDataSource {

	public RepresentanteGwtRPCDS (Boolean hidden) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idRepresentante", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("asiento", "Asiento", 150, true);
        addField (field);
        field = new DataSourceTextField ("cargo", "Cargo", 250, true);
        addField (field);
        field = new DataSourceTextField ("domicilioLegal", "Domicilio Legal", 150, true);
        addField (field);
        field = new DataSourceTextField ("estado", "Estado", 150, true);
        addField (field);

        field = new DataSourceTextField ("idPersonaNombre", "persona", 250, true);//n mayuscula
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idPersona", "nombre", 10, true);
        field.setDisplayField("idPersonaNombre");
        field.setEditorProperties(new ListGridSelect("Personas", 300, 300, "nombreCompleto", new PersonaGwtRPCDS(true), "nombreCompleto", "idPersona", true));
        addField (field);
        
        field = new DataSourceTextField ("nroPartida", "Nro Partida", 150, true);
        addField (field);
        field = new DataSourceTextField ("oficinaRegistral", "Oficina Registral", 150, true);
        addField (field);
        field = new DataSourceTextField ("telefono", "Telefono", 150, true);
        addField (field);
        field = new DataSourceTextField ("telefono1", "Telefono 1", 150, true);
        addField (field);   
        
       
        field = new DataSourceIntegerField ("idEmpresa", "id Empresa", 10, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("idEmpresaRuc", "Ruc", 100, true);
        field.setHidden(true);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("idEmpresaNombre", "idEmpresa", 100, true);
        field.setHidden(true);
        addField (field);
	}

	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final RepresentanteGwtRPCDSServiceAsync service = GWT.create (RepresentanteGwtRPCDSService.class);
		final Representante testRec = new Representante ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Representante>> () {
			            public void onFailure (Throwable caught) {
			            	response.setStatus (RPCResponse.STATUS_FAILURE); 
				            response.setAttribute("data", caught.getMessage());
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Representante> result) {
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
	    Representante testRec = new Representante ();
	    copyValues (rec, testRec, false);
	    RepresentanteGwtRPCDSServiceAsync service = GWT.create (RepresentanteGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Representante> () {
	        public void onFailure (Throwable caught) {
	        	response.setStatus (RPCResponse.STATUS_FAILURE); 
	            response.setAttribute("data", caught.getMessage());
                processResponse (requestId, response);
	        }
	        public void onSuccess (Representante result) {
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
	    Representante testRec = new Representante ();
	    copyValues (rec, testRec, false);
	    RepresentanteGwtRPCDSServiceAsync service = GWT.create (RepresentanteGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Representante> () {
	        public void onFailure (Throwable caught) {
	        	response.setStatus (RPCResponse.STATUS_FAILURE); 
	            response.setAttribute("data", caught.getMessage());
                processResponse (requestId, response);
	        }
	        public void onSuccess (Representante result) {
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
	    Representante testRec = new Representante ();
	    copyValues (rec, testRec, false);
	    RepresentanteGwtRPCDSServiceAsync service = GWT.create (RepresentanteGwtRPCDSService.class);
	    service.remove (testRec, new AsyncCallback<Object> () {
	        public void onFailure (Throwable caught) {
	        	response.setStatus (RPCResponse.STATUS_FAILURE); 
	            response.setAttribute("data", caught.getMessage());
                processResponse (requestId, response);
	        }
	        public void onSuccess (Object result) {
	            ListGridRecord[] list = new ListGridRecord[1];
	            list[0] = rec;
	            response.setData (list);
	            processResponse (requestId, response);
	        }
	    });
	}
	
	
	//Guarda información
	static void copyValues (ListGridRecord from, Representante to, Boolean is_filter) {
	    to.setIdRepresentante (from.getAttributeAsInt ("idRepresentante"));
	    if(from.getAttributeAsString ("asiento") != null)
	    	to.setAsiento (from.getAttributeAsString ("asiento"));
	    if(from.getAttributeAsString ("cargo") != null)
	    	to.setCargo (from.getAttributeAsString ("cargo").toUpperCase());
	    if(from.getAttributeAsString ("domicilioLegal") != null)
	    	to.setDomicilioLegal (from.getAttributeAsString ("domicilioLegal").toUpperCase());
	    if(from.getAttributeAsString ("estado") != null)
	    	to.setEstado (from.getAttributeAsString ("estado").toUpperCase());
	    
	    
	    if(from.getAttributeAsString ("nroPartida") != null)
	    	to.setNroPartida (from.getAttributeAsString ("nroPartida").toUpperCase());
	    if(from.getAttributeAsString ("oficinaRegistral") != null)
	    	to.setOficinaRegistral (from.getAttributeAsString ("oficinaRegistral").toUpperCase());
	    if(from.getAttributeAsString ("telefono") != null)
	    	to.setTelefono (from.getAttributeAsString ("telefono").toUpperCase());
	    if(from.getAttributeAsString ("telefono1") != null)
	    	to.setTelefono1 (from.getAttributeAsString ("telefono1").toUpperCase());  
	    
	    if(from.getAttribute ("idEmpresa") != null || from.getAttributeAsString ("idEmpresaNombre") != null || from.getAttributeAsString ("idEmpresaRuc") != null || from.getAttributeAsString ("idEmpresa") != null) {
	    	Empresa plan = new Empresa();
	    	if(from.getAttribute ("idEmpresa") != null) {
	    		plan.setIdEmpresa (from.getAttributeAsInt ("idEmpresa"));
					
			}				
			if(from.getAttributeAsString ("idEmpresaNombre") != null)
				plan.setNombre(from.getAttributeAsString ("idEmpresaNombre"));
			if(from.getAttributeAsString ("idEmpresaRuc") != null)
				plan.setRuc(from.getAttributeAsString ("idEmpresaRuc"));
			to.setIdEmpresa(plan);
	    }
	    
	    if(from.getAttribute ("idPersona") != null || from.getAttributeAsString ("idPersonaNombre") != null) {
	    	Persona idPersona = new Persona();
			if(from.getAttribute ("idPersona") != null) {
				if(is_filter)
					idPersona.setNombre(from.getAttributeAsString ("idPersona"));
				else
					idPersona.setIdPersona (from.getAttributeAsInt ("idPersona"));
			}				
			if(from.getAttributeAsString ("idPersonaNombre") != null)
				idPersona.setNombre(from.getAttributeAsString ("idPersonaNombre"));
			to.setIdPersona(idPersona);
		}
	    
	    
	    }

	private static void copyValues (Representante from, ListGridRecord to) {
		to.setAttribute ("idRepresentante", from.getIdRepresentante());
	    to.setAttribute ("asiento", from.getAsiento());
	    to.setAttribute ("cargo", from.getCargo());
	    to.setAttribute ("domicilioLegal", from.getDomicilioLegal());
	    to.setAttribute ("estado", from.getEstado());
	    to.setAttribute ("nroPartida", from.getNroPartida());
	    to.setAttribute ("oficinaRegistral", from.getOficinaRegistral());
	    to.setAttribute ("telefono", from.getTelefono());
	    to.setAttribute ("telefono1", from.getTelefono1());
	    if(from.getIdEmpresa() != null) {
	    	to.setAttribute ("idEmpresa", from.getIdEmpresa().getIdEmpresa());
	    	to.setAttribute ("idEmpresaNombre", from.getIdEmpresa().getNombre());
	    	to.setAttribute ("idEmpresaRuc", from.getIdEmpresa().getRuc());
	    	to.setAttribute ("idEmpresa", from.getIdEmpresa().getIdEmpresa());
	    }
	    
	    if( from.getIdPersona() != null) {
	    	System.out.println(from.getIdPersona().getNombreCompleto());
	    	to.setAttribute ("idPersona", from.getIdPersona().getIdPersona());
		    to.setAttribute ("idPersonaNombre", from.getIdPersona().getNombreCompleto());
	    }
	    
	}
	}