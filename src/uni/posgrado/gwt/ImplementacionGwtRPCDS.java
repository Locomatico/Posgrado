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
import uni.posgrado.shared.model.Implementacion;//espacio por implementacion
import uni.posgrado.shared.model.Local;
import uni.posgrado.shared.model.Pabellon;
import uni.posgrado.shared.model.Piso;
import uni.posgrado.shared.model.Espacio;
import uni.posgrado.shared.model.Recurso;//piso por recurso
import java.util.List;
/*
import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;
*/
/*
CREATE TABLE public.implementacion
(
  id integer NOT NULL DEFAULT nextval('implementacion_id_seq'::regclass),
  tipo character varying,--Debe cambiar por estado
  id_local integer,
  id_pabellon integer,
  id_piso integer,
  id_espacio integer,
  id_recurso integer,
  observacion character varying,
  fecha_creacion timestamp with time zone DEFAULT now(),
  fecha_ult_modificacion timestamp with time zone DEFAULT now(),
  usuario_creacion integer,
  usuario_actualizacion integer,
  CONSTRAINT pk PRIMARY KEY (id)
) 
*/

public class ImplementacionGwtRPCDS extends GwtRpcDataSource {
	//Agrega columnas a la tabla 
	public ImplementacionGwtRPCDS (Boolean hidden) {
	    DataSourceField field;
        field = new DataSourceIntegerField ("idImplementacion", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        //empieza lo del codigo y relacion
        //al ser una tabla detalle, no necesita codigo en la pantalla
        /*
        field = new DataSourceTextField ("implementacionCodigo", "id. Implementacion", 20, false);
        field.setCanEdit(false);
        field.setHidden(hidden);
        addField (field);
        */
        //LOCAL--SEDE
        field = new DataSourceTextField ("localImplementacion", "implementacion", 100, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idLocal", "Local", 10, true);
        field.setDisplayField("localNombre");
        field.setEditorProperties(new ListGridSelect("Local", 200, 300, "nombre", new LocalGwtRPCDS(true), "nombre", "idLocal", true));//de locales a Local
        field.setHidden(hidden);
        addField (field);
        //PABELLON
        field = new DataSourceTextField ("pabellonImplementacion", "implementacion", 100, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idPabellon", "Pabellon", 10, true);//Local por idLocal
        field.setDisplayField("pabellonNombre");
        field.setEditorProperties(new ListGridSelect("Pabellon", 200, 300, "nombre", new PabellonGwtRPCDS(true), "nombre", "idPabellon", true));//de locales a Local
        field.setHidden(hidden);
        addField (field);
        //PISO
        field = new DataSourceTextField ("pisoImplementacion", "implementacion", 100, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idPiso", "Piso", 10, true);//Local por idLocal
        field.setDisplayField("pisoNombre");
        field.setEditorProperties(new ListGridSelect("Piso", 200, 300, "nombre", new PisoGwtRPCDS(true), "nombre", "idPiso", true));//de locales a Local
        field.setHidden(hidden);
        addField (field);
        //ESPACIO
        field = new DataSourceTextField ("espacioImplementacion", "implementacion", 100, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("idEspacio", "Espacio", 10, true);//Local por idLocal
        field.setDisplayField("espacioNombre");
        field.setEditorProperties(new ListGridSelect("Espacio", 200, 300, "nombre", new EspacioGwtRPCDS(true), "nombre", "idEspacio", true));//de locales a Local
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceIntegerField ("idRecurso", "Recurso", 10, true);//Local por idLocal
        field.setDisplayField("recursoNombre");
        field.setEditorProperties(new ListGridSelect("Recurso", 200, 300, "nombre", new RecursoGwtRPCDS(), "nombre", "idRecurso", true));//de locales a Local
        field.setHidden(hidden);
        addField (field);
        //termina lo del codigo y relacion
        field = new DataSourceTextField ("tipo", "Tipo", 150, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("observacion", "Observacion", 150, true);
        field.setHidden(hidden);
        addField (field);
	}

	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final ImplementacionGwtRPCDSServiceAsync service = GWT.create (ImplementacionGwtRPCDSService.class);
		final Implementacion testRec = new Implementacion ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Implementacion>>(){
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Implementacion> result) {
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
	    Implementacion testRec = new Implementacion ();
	    copyValues (rec, testRec, false);
	    ImplementacionGwtRPCDSServiceAsync service = GWT.create (ImplementacionGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Implementacion> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Implementacion result) {
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
	    Implementacion testRec = new Implementacion ();
	    copyValues (rec, testRec, false);
	    ImplementacionGwtRPCDSServiceAsync service = GWT.create (ImplementacionGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Implementacion> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Implementacion result) {
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
	    Implementacion testRec = new Implementacion ();
	    copyValues (rec, testRec, false);
	    ImplementacionGwtRPCDSServiceAsync service = GWT.create (ImplementacionGwtRPCDSService.class);
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
	static void copyValues (ListGridRecord from, Implementacion to, Boolean is_filter) {
	    to.setIdImplementacion(from.getAttributeAsInt ("idImplementacion"));
	    if(from.getAttribute ("tipo") != null)
    		to.setTipo(from.getAttributeAsString("tipo").toUpperCase());
    	if(from.getAttribute ("observacion") != null) 
    		to.setObservacion(from.getAttributeAsString("observacion").toUpperCase());
    	
    	if(from.getAttribute ("idLocal") != null || from.getAttributeAsString ("idLocalNombre") != null || from.getAttributeAsString ("idLocalCodigo") != null) {//a id_local
			Local local = new Local();			
			if(from.getAttribute ("idLocal") != null) {//a id_local
				if(is_filter)
					local.setNombre (from.getAttributeAsString ("idLocal"));//de local a id_local
				else
					local.setIdLocal (from.getAttributeAsInt ("idLocal"));//de local a id_local
			}
			if(from.getAttributeAsString ("localNombre") != null)
				local.setNombre(from.getAttributeAsString ("localNombre"));			
			if(from.getAttributeAsString ("localCodigo") != null)
				local.setCodigo(from.getAttributeAsString ("localCodigo"));
			to.setIdLocal(local);
		}
    	if(from.getAttribute ("idPabellon") != null || from.getAttributeAsString ("idPabellonNombre") != null || from.getAttributeAsString ("idPabellonCodigo") != null) {//a id_local
    		Pabellon pabellon = new Pabellon();			
			if(from.getAttribute ("idPabellon") != null) {//a id_local
				if(is_filter)
					pabellon.setNombre (from.getAttributeAsString ("idPabellon"));//de local a id_local
				else
					pabellon.setIdPabellon (from.getAttributeAsInt ("idPabellon"));//de local a id_local
			}
			if(from.getAttributeAsString ("pabellonNombre") != null)
				pabellon.setNombre(from.getAttributeAsString ("pabellonNombre"));			
			if(from.getAttributeAsString ("pabellonCodigo") != null)
				pabellon.setCodigo(from.getAttributeAsString ("pabellonCodigo"));
			to.setIdPabellon(pabellon);
		}
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
    	if(from.getAttribute ("idEspacio") != null || from.getAttributeAsString ("idEspacioNombre") != null || from.getAttributeAsString ("idEspacioCodigo") != null) {//a id_local
    		Espacio espacio = new Espacio();			
			if(from.getAttribute ("idEspacio") != null) {//a id_local
				if(is_filter)
					espacio.setNombre (from.getAttributeAsString ("idEspacio"));//de local a id_local
				else
					espacio.setIdEspacio (from.getAttributeAsInt ("idEspacio"));//de local a id_local
			}
			if(from.getAttributeAsString ("espacioNombre") != null)
				espacio.setNombre(from.getAttributeAsString ("espacioNombre"));			
			if(from.getAttributeAsString ("espacioCodigo") != null)
				espacio.setCodigo(from.getAttributeAsString ("espacioCodigo"));
			to.setIdEspacio(espacio);
		}
    	if(from.getAttribute ("idRecurso") != null || from.getAttributeAsString ("idRecursoNombre") != null || from.getAttributeAsString ("idRecursoCodigo") != null) {//a id_local
    		Recurso recurso = new Recurso();			
			if(from.getAttribute ("idRecurso") != null) {//a id_local
				if(is_filter)
					recurso.setNombre (from.getAttributeAsString ("idRecurso"));//de local a id_local
				else
					recurso.setIdRecurso (from.getAttributeAsInt ("idRecurso"));//de local a id_local
			}
			if(from.getAttributeAsString ("recursoNombre") != null)
				recurso.setNombre(from.getAttributeAsString ("recursoNombre"));			
			if(from.getAttributeAsString ("recursoCodigo") != null)
				recurso.setCodigo(from.getAttributeAsString ("recursoCodigo"));
			to.setIdRecurso(recurso);
		}

    	/*
    	if(from.getAttribute ("tipo") != null) {
			Variable var = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPESP");
			var_pk.setCodTabla(from.getAttributeAsString ("tipo"));
			var.setId(var_pk);
			to.setTipo(var);
		}
		*/
	}
	
	// GUARDA LOS DATOS
	private static void copyValues (Implementacion from, ListGridRecord to) {
	    to.setAttribute ("idImplementacion", from.getIdImplementacion());
	    to.setAttribute ("tipo", from.getTipo());
	    to.setAttribute ("observacion", from.getObservacion());
	    if( from.getIdLocal() != null) {
	    	to.setAttribute ("idLocal", from.getIdLocal().getIdLocal());//a id_local
		    to.setAttribute ("localCodigo", from.getIdLocal().getCodigo());
		    to.setAttribute ("localNombre", from.getIdLocal().getNombre());
	    }
	    if( from.getIdPabellon() != null) {
	    	to.setAttribute ("idPabellon", from.getIdPabellon().getIdPabellon());//a id_local
		    to.setAttribute ("pabellonCodigo", from.getIdPabellon().getCodigo());
		    to.setAttribute ("pabellonNombre", from.getIdPabellon().getNombre());
	    }
	    if( from.getIdPiso() != null) {
	    	to.setAttribute ("idPiso", from.getIdPiso().getIdPiso());//a id_local
		    to.setAttribute ("pisoCodigo", from.getIdPiso().getCodigo());
		    to.setAttribute ("pisoNombre", from.getIdPiso().getNombre());
	    }
	    if( from.getIdEspacio() != null) {
	    	to.setAttribute ("idEspacio", from.getIdEspacio().getIdEspacio());//a id_local
		    to.setAttribute ("espacioCodigo", from.getIdEspacio().getCodigo());
		    to.setAttribute ("espacioNombre", from.getIdEspacio().getNombre());
	    }
	    if( from.getIdRecurso() != null) {
	    	to.setAttribute ("idRecurso", from.getIdRecurso().getIdRecurso());//a id_local
		    to.setAttribute ("recursoCodigo", from.getIdRecurso().getCodigo());
		    to.setAttribute ("recursoNombre", from.getIdRecurso().getNombre());
	    }
	    /*
	    if( from.getTipo() != null) {
	    	to.setAttribute ("tipo", from.getTipo().getId().getCodTabla());
		    to.setAttribute ("tipoNombre", from.getTipo().getNomTabla());
	    }
	    */
	}
}