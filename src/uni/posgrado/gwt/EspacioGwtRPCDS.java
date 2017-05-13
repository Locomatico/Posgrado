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
import uni.posgrado.shared.model.Espacio;
import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;
import uni.posgrado.shared.model.Piso;
import java.util.List;

public class EspacioGwtRPCDS extends GwtRpcDataSource {
	//Agrega columnas a la tabla 
	public EspacioGwtRPCDS (Boolean hidden) {
	    DataSourceField field;
        field = new DataSourceIntegerField ("idEspacio", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("codigo", "Codigo", 150, true);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 150, true);
        addField (field);
        //empieza lo del codigo y relacion
        field = new DataSourceTextField ("pisoCodigo", "Cod. piso", 20, false);
        field.setCanEdit(false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("pisoNombre", "piso", 100, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idPiso", "Piso", 10, true);//Local por idLocal
        field.setDisplayField("pisoNombre");
        field.setEditorProperties(new ListGridSelect("Piso", 200, 300, "nombre", new PisoGwtRPCDS(true), "nombre", "idPiso", true));//de locales a Local
        field.setHidden(hidden);
        addField (field);
        //termina lo del codigo y relacion
        field = new DataSourceTextField ("abreviatura", "Abreviatura", 150, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("observacion", "Observacion", 150, true);
        field.setHidden(hidden);
        addField (field);        
        field = new DataSourceIntegerField ("aforo", "Aforo", 20, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("tipoNombre", "tipo", 100, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipo", "Tipo", 10, true);
        field.setHidden(hidden);
        field.setDisplayField("tipoNombre");
        field.setEditorProperties(new ListGridSelect("Tipo", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPESP"), "nomTabla", "codTabla", true));
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final EspacioGwtRPCDSServiceAsync service = GWT.create (EspacioGwtRPCDSService.class);
		final Espacio testRec = new Espacio ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Espacio>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Espacio> result) {
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
	    Espacio testRec = new Espacio ();
	    copyValues (rec, testRec, false);
	    EspacioGwtRPCDSServiceAsync service = GWT.create (EspacioGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Espacio> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Espacio result) {
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
	    Espacio testRec = new Espacio ();
	    copyValues (rec, testRec, false);
	    EspacioGwtRPCDSServiceAsync service = GWT.create (EspacioGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Espacio> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Espacio result) {
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
	    Espacio testRec = new Espacio ();
	    copyValues (rec, testRec, false);
	    EspacioGwtRPCDSServiceAsync service = GWT.create (EspacioGwtRPCDSService.class);
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
	//TRAE LOS DATOS
	static void copyValues (ListGridRecord from, Espacio to, Boolean is_filter) {
	    to.setIdEspacio (from.getAttributeAsInt ("idEspacio"));
	    if(from.getAttributeAsString ("codigo") != null)
	    	to.setCodigo (from.getAttributeAsString ("codigo").toUpperCase());
	    if(from.getAttribute ("nombre") != null) 
    		to.setNombre(from.getAttributeAsString("nombre").toUpperCase());
	    if(from.getAttribute ("abreviatura") != null) 
    		to.setAbreviatura(from.getAttributeAsString("abreviatura").toUpperCase());
    	if(from.getAttribute ("observacion") != null) 
    		to.setObservacion(from.getAttributeAsString("observacion").toUpperCase());
    	
    	if(from.getAttribute ("idPiso") != null || from.getAttributeAsString ("idPisoNombre") != null || from.getAttributeAsString ("idPisoCodigo") != null) {//a id_local
			Piso piso = new Piso();			
			if(from.getAttribute ("idPiso") != null) {//a id_local
				if(is_filter)
					piso.setNombre (from.getAttributeAsString ("idPiso"));//de local a id_local
				else
					piso.setIdPiso (from.getAttributeAsInt ("idPiso"));//de local a id_local
			}
			if(from.getAttributeAsString ("pisoNombre") != null)
				piso.setNombre(from.getAttributeAsString ("pisoNombre"));			
			if(from.getAttributeAsString ("pisoCodigo") != null)
				piso.setCodigo(from.getAttributeAsString ("pisoCodigo"));
			to.setIdPiso(piso);
		}
    	if(from.getAttribute ("aforo") != null) 
    		to.setAforo (from.getAttributeAsInt("aforo"));
    	if(from.getAttribute ("tipo") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPESP");
			var_pk.setCodTabla(from.getAttributeAsString ("tipo"));
			var.setId(var_pk);
			to.setTipo(var);
		}
	}
	
	// GUARDA LOS DATOS
	private static void copyValues (Espacio from, ListGridRecord to) {
	    to.setAttribute ("idEspacio", from.getIdEspacio());
	    to.setAttribute ("codigo", from.getCodigo());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("abreviatura", from.getAbreviatura());
	    to.setAttribute ("observacion", from.getObservacion());
	    to.setAttribute ("aforo", from.getAforo());
	    if( from.getIdPiso() != null) {
	    	to.setAttribute ("idPiso", from.getIdPiso().getIdPiso());//a id_local
		    to.setAttribute ("pisoCodigo", from.getIdPiso().getCodigo());
		    to.setAttribute ("pisoNombre", from.getIdPiso().getNombre());
	    }
	    if( from.getTipo() != null) {
	    	to.setAttribute ("tipo", from.getTipo().getId().getCodTabla());
		    to.setAttribute ("tipoNombre", from.getTipo().getNomTabla());
	    }
	}
}