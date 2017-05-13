package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.shared.model.Empresa;
import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class SuneduGwtRPCDS extends GwtRpcDataSource {

	
	public SuneduGwtRPCDS (Boolean hidden) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idEmpresa", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 150, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("ruc", "RUC", 11, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("razonSocial", "Razon Social", 150, true);
        field.setHidden(hidden);
        addField (field);
        
        field = new DataSourceTextField ("tipoUniversidadNombre", "TipoUniversidad", 100, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipoUniversidad", "Tipo de universidad", 10, true);
        field.setHidden(hidden);
        field.setDisplayField("tipoUniversidadNombre");
        field.setEditorProperties(new ListGridSelect("Tipo de universidad", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPUNI"), "nomTabla", "codTabla", true));
        addField (field);        
        
        field = new DataSourceTextField ("tipoGestion", "Tipo de Gestion", 150, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("modalidadCreacion", "Modalidad de Creacion", 150, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("documentoCreacion", "Documento de Creacion", 150, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceDateField ("empresaCreacion", "Fecha de Creacion", 20, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("telefono", "Telefono", 20, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("telefono1", "Telefono 1", 20, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("correo", "Correo electronico institucional", 150, false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("web", "Pagina web institucional", 150, false);
        field.setHidden(hidden);
        addField (field);
        
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final SuneduGwtRPCDSServiceAsync service = GWT.create (SuneduGwtRPCDSService.class);
		final Empresa testRec = new Empresa ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Empresa>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Empresa> result) {
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
	    Empresa testRec = new Empresa ();
	    copyValues (rec, testRec, false);
	    SuneduGwtRPCDSServiceAsync service = GWT.create (SuneduGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Empresa> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Empresa result) {
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
	    Empresa testRec = new Empresa ();
	    copyValues (rec, testRec, false);
	    SuneduGwtRPCDSServiceAsync service = GWT.create (SuneduGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Empresa> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Empresa result) {
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
	    Empresa testRec = new Empresa ();
	    copyValues (rec, testRec, false);
	    SuneduGwtRPCDSServiceAsync service = GWT.create (SuneduGwtRPCDSService.class);
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
	static void copyValues (ListGridRecord from, Empresa to, Boolean is_filter) {
	    to.setIdEmpresa (from.getAttributeAsInt ("idEmpresa"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase());
	    if(from.getAttributeAsString ("razonSocial") != null)
	    	to.setRazonSocial (from.getAttributeAsString ("razonSocial").toUpperCase());
	    if(from.getAttributeAsString ("ruc") != null)
	    	to.setRuc (from.getAttributeAsString ("ruc").toUpperCase());
	    if(from.getAttributeAsString ("telefono") != null)
	    	to.setTelefono (from.getAttributeAsString ("telefono").toUpperCase());
	    if(from.getAttributeAsString ("telefono1") != null)
	    	to.setTelefono1 (from.getAttributeAsString ("telefono1").toUpperCase());
	    if(from.getAttributeAsString ("empresaCreacion") != null)
	    	to.setEmpresaCreacion (from.getAttributeAsDate ("empresaCreacion"));
	    
	    if(from.getAttribute ("tipoUniversidad") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPUNI");
			var_pk.setCodTabla(from.getAttributeAsString ("tipoUniversidad"));
			var.setId(var_pk);
			to.setTipoUniversidad(var);
		}

    	if(from.getAttributeAsString ("tipoGestion") != null)
	    	to.setTipoGestion (from.getAttributeAsString ("tipoGestion"));
    	if(from.getAttributeAsString ("modalidadCreacion") != null)
	    	to.setModalidadCreacion (from.getAttributeAsString ("modalidadCreacion").toUpperCase());
    	if(from.getAttributeAsString ("documentoCreacion") != null)
	    	to.setDocumentoCreacion (from.getAttributeAsString ("documentoCreacion").toUpperCase());
    	if(from.getAttributeAsString ("correo") != null)
	    	to.setCorreo (from.getAttributeAsString ("correo").toUpperCase());
    	if(from.getAttributeAsString ("web") != null)
	    	to.setWeb (from.getAttributeAsString ("web").toUpperCase());
	}	
	
	private static void copyValues (Empresa from, ListGridRecord to) {
	    to.setAttribute ("idEmpresa", from.getIdEmpresa());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("razonSocial", from.getRazonSocial());
	    to.setAttribute ("ruc", from.getRuc());
	    to.setAttribute ("empresaCreacion", from.getEmpresaCreacion());
	    to.setAttribute ("telefono", from.getTelefono());
	    to.setAttribute ("telefono1", from.getTelefono1());
	    if( from.getTipoUniversidad() != null) {
	    	to.setAttribute ("tipoUniversidad", from.getTipoUniversidad().getId().getCodTabla());
		    to.setAttribute ("tipoUniversidadNombre", from.getTipoUniversidad().getNomTabla());
	    }
	    to.setAttribute ("tipoGestion", from.getTipoGestion());
	    to.setAttribute ("modalidadCreacion", from.getModalidadCreacion());
	    to.setAttribute ("documentoCreacion", from.getDocumentoCreacion());
	    to.setAttribute ("correo", from.getCorreo());
	    to.setAttribute ("web", from.getWeb());
	}
}