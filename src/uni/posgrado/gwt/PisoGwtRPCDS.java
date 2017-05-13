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
import uni.posgrado.shared.model.Piso;
//import uni.posgrado.shared.model.Variable;
//import uni.posgrado.shared.model.VariablePK;
import uni.posgrado.shared.model.Pabellon;
import java.util.List;

public class PisoGwtRPCDS extends GwtRpcDataSource {
	//Agrega columnas a la tabla 
	public PisoGwtRPCDS (Boolean hidden) {
	    DataSourceField field;
        field = new DataSourceIntegerField ("idPiso", "ID");
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
        field = new DataSourceTextField ("pabellonCodigo", "Cod. pabellon", 20, false);
        field.setCanEdit(false);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("pabellonNombre", "pabellon", 100, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idPabellon", "Pabellon", 10, true);//Local por idLocal
        field.setDisplayField("pabellonNombre");
        field.setEditorProperties(new ListGridSelect("Pabellon", 200, 300, "nombre", new PabellonGwtRPCDS(true), "nombre", "idPabellon", true));//de locales a Local
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
        /*
        field = new DataSourceTextField ("tipoNombre", "tipo", 100, false);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipo", "Tipo", 10, true);
        field.setHidden(hidden);
        field.setDisplayField("tipoNombre");
        field.setEditorProperties(new ListGridSelect("Tipo", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPPAB"), "nomTabla", "codTabla", true));
        addField (field);
        */
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final PisoGwtRPCDSServiceAsync service = GWT.create (PisoGwtRPCDSService.class);
		final Piso testRec = new Piso ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Piso>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Piso> result) {
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
	    Piso testRec = new Piso ();
	    copyValues (rec, testRec, false);
	    PisoGwtRPCDSServiceAsync service = GWT.create (PisoGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Piso> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Piso result) {
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
	    Piso testRec = new Piso ();
	    copyValues (rec, testRec, false);
	    PisoGwtRPCDSServiceAsync service = GWT.create (PisoGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Piso> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Piso result) {
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
	    Piso testRec = new Piso ();
	    copyValues (rec, testRec, false);
	    PisoGwtRPCDSServiceAsync service = GWT.create (PisoGwtRPCDSService.class);
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
	static void copyValues (ListGridRecord from, Piso to, Boolean is_filter) {
	    to.setIdPiso (from.getAttributeAsInt ("idPiso"));
	    if(from.getAttributeAsString ("codigo") != null)
	    	to.setCodigo (from.getAttributeAsString ("codigo").toUpperCase());
	    if(from.getAttribute ("nombre") != null) 
    		to.setNombre(from.getAttributeAsString("nombre").toUpperCase());
	    if(from.getAttribute ("abreviatura") != null) 
    		to.setAbreviatura(from.getAttributeAsString("abreviatura").toUpperCase());
    	if(from.getAttribute ("observacion") != null) 
    		to.setObservacion(from.getAttributeAsString("observacion").toUpperCase());
    	if(from.getAttribute ("idPabellon") != null || from.getAttributeAsString ("idPabellonNombre") != null || from.getAttributeAsString ("idPabellonCodigo") != null) {//a id_local
			Pabellon pabellon = new Pabellon();			
			if(from.getAttribute("idPabellon") != null) {//a id_local
				if(is_filter)
					pabellon.setNombre(from.getAttributeAsString("idPabellon"));//de local a id_local
				else
					pabellon.setIdPabellon(from.getAttributeAsInt("idPabellon"));//de local a id_local
			}
			if(from.getAttributeAsString ("pabellonNombre") != null)
				pabellon.setNombre(from.getAttributeAsString ("pabellonNombre"));			
			if(from.getAttributeAsString ("pabellonCodigo") != null)
				pabellon.setCodigo(from.getAttributeAsString ("pabellonCodigo"));
			to.setIdPabellon(pabellon);//getIdPabellon
		}
    	if(from.getAttribute("aforo") != null) 
    		to.setAforo(from.getAttributeAsInt("aforo"));
    	/*
    	if(from.getAttribute ("tipo") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPPAB");
			var_pk.setCodTabla(from.getAttributeAsString ("tipo"));
			var.setId(var_pk);
			to.setTipo(var);
		}
		*/
	}
	
	// GUARDA LOS DATOS
	private static void copyValues (Piso from, ListGridRecord to) {
	    to.setAttribute ("idPiso", from.getIdPiso());
	    to.setAttribute ("codigo", from.getCodigo());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("abreviatura", from.getAbreviatura());
	    to.setAttribute ("observacion", from.getObservacion());
	    to.setAttribute ("aforo", from.getAforo());
	    if( from.getIdPabellon() != null) {
	    	to.setAttribute ("idPabellon", from.getIdPabellon().getIdPabellon());//a id_local//.getIdPabellon()
		    to.setAttribute ("pabellonCodigo", from.getIdPabellon().getCodigo());//getIdPabellon().
		    to.setAttribute ("pabellonNombre", from.getIdPabellon().getNombre());//getIdPabellon().
	    }
	    /*
	    if( from.getTipo() != null) {
	    	to.setAttribute ("tipo", from.getTipo().getId().getCodTabla());
		    to.setAttribute ("tipoNombre", from.getTipo().getNomTabla());
	    }
	    */
	}
}