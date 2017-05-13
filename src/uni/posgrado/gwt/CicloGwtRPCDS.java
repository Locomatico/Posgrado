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

import uni.posgrado.shared.model.Ciclo;
import uni.posgrado.shared.model.PlanEstudio;

import java.util.List;

public class CicloGwtRPCDS extends GwtRpcDataSource {

	public CicloGwtRPCDS (Boolean hidden) {
	    DataSourceField field;
        field = new DataSourceIntegerField ("idCiclo", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 100, true);
        addField (field);
        field = new DataSourceIntegerField ("numero", "Numero", 10, true);
        addField (field);
        field = new DataSourceIntegerField ("adelante", "Ciclo Posterior", 10, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceIntegerField ("atras", "Ciclo Anterior", 10, true);
        field.setHidden(hidden);
        addField (field);

        field = new DataSourceIntegerField ("idPlanEstudio", "id Plan", 10, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("planEstudioCodigo", "Cod. Plan", 100, true);
        field.setHidden(true);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceTextField ("planEstudioNombre", "planEstudio", 100, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("planEstudio", "Plan de estudio", 10, true);
        field.setHidden(true);
        field.setDisplayField("planEstudioNombre");
        field.setCanEdit(false);
        addField (field);
        
        field = new DataSourceIntegerField ("minimoCreditos", "Min. creditos", 10, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceIntegerField ("maximoCreditos", "Max. creditos", 10, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceIntegerField ("minimoCursos", "Min. cursos", 10, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceIntegerField ("maximoCursos", "Max. cursos", 10, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceTextField ("descripcion", "Descripción", 1000, false);
        field.setHidden(hidden);
        addField (field);
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final CicloGwtRPCDSServiceAsync service = GWT.create (CicloGwtRPCDSService.class);
		final Ciclo testRec = new Ciclo ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Ciclo>> () {
			            public void onFailure (Throwable caught) {
			            	response.setStatus (RPCResponse.STATUS_FAILURE); 
				            response.setAttribute("data", caught.getMessage());
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Ciclo> result) {
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
	    Ciclo testRec = new Ciclo ();
	    copyValues (rec, testRec, false);
	    CicloGwtRPCDSServiceAsync service = GWT.create (CicloGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Ciclo> () {
	        public void onFailure (Throwable caught) {
	        	response.setStatus (RPCResponse.STATUS_FAILURE); 
	            response.setAttribute("data", caught.getMessage());
                processResponse (requestId, response);
	        }
	        public void onSuccess (Ciclo result) {
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
	    Ciclo testRec = new Ciclo ();
	    copyValues (rec, testRec, false);
	    CicloGwtRPCDSServiceAsync service = GWT.create (CicloGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Ciclo> () {
	        public void onFailure (Throwable caught) {
	        	response.setStatus (RPCResponse.STATUS_FAILURE); 
	            response.setAttribute("data", caught.getMessage());
                processResponse (requestId, response);
	        }
	        public void onSuccess (Ciclo result) {
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
	    Ciclo testRec = new Ciclo ();
	    copyValues (rec, testRec, false);
	    CicloGwtRPCDSServiceAsync service = GWT.create (CicloGwtRPCDSService.class);
	    service.remove (testRec, new AsyncCallback<Object> () {
	        public void onFailure (Throwable caught) {
	        	response.setStatus (RPCResponse.STATUS_FAILURE); 
	            response.setAttribute("data", caught.getMessage());
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
	
	static void copyValues (ListGridRecord from, Ciclo to, Boolean is_filter) {
	    to.setIdCiclo (from.getAttributeAsInt ("idCiclo"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase().trim());
	    if(from.getAttribute ("adelante") != null)
	    	to.setAdelante (Integer.parseInt(from.getAttribute ("adelante").trim()));
	    if(from.getAttribute ("atras") != null)
	    	to.setAtras (Integer.parseInt(from.getAttribute ("atras").trim()));
	    if(from.getAttributeAsString ("descripcion") != null)
	    	to.setDescripcion (from.getAttributeAsString ("descripcion").toUpperCase().trim());

	    if(from.getAttribute ("planEstudio") != null || from.getAttributeAsString ("planEstudioNombre") != null || from.getAttributeAsString ("planEstudioCodigo") != null || from.getAttributeAsString ("idPlanEstudio") != null) {
	    	PlanEstudio plan = new PlanEstudio();
	    	if(from.getAttribute ("planEstudio") != null) {
				if(is_filter)
					plan.setNombre(from.getAttributeAsString ("planEstudio"));
				else
					plan.setIdPlanEstudio (from.getAttributeAsInt ("planEstudio"));
			}				
			if(from.getAttributeAsString ("planEstudioNombre") != null)
				plan.setNombre(from.getAttributeAsString ("planEstudioNombre"));
			if(from.getAttributeAsString ("planEstudioCodigo") != null)
				plan.setCodigo(from.getAttributeAsString ("planEstudioCodigo"));
			if(from.getAttribute ("idPlanEstudio") != null) {
				plan.setIdPlanEstudio (from.getAttributeAsInt ("idPlanEstudio"));
			}
			to.setPlanEstudio(plan);
	    }
	    if(from.getAttribute ("maximoCreditos") != null)
	    	to.setMaximoCreditos (Integer.parseInt(from.getAttribute ("maximoCreditos")));
	    if(from.getAttribute ("maximoCursos") != null)
	    	to.setMaximoCursos (Integer.parseInt(from.getAttribute ("maximoCursos")));
	    if(from.getAttribute ("minimoCreditos") != null)
	    	to.setMinimoCreditos (Integer.parseInt(from.getAttribute ("minimoCreditos")));
	    if(from.getAttribute ("minimoCursos") != null)
	    	to.setMinimoCursos (Integer.parseInt(from.getAttribute ("minimoCursos")));
	    if(from.getAttribute ("numero") != null)
	    	to.setNumero (Integer.parseInt(from.getAttribute ("numero")));	    
	}	
	
	private static void copyValues (Ciclo from, ListGridRecord to) {
	    to.setAttribute ("idCiclo", from.getIdCiclo());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("adelante", from.getAdelante());
	    to.setAttribute ("atras", from.getAtras());
	    to.setAttribute ("descripcion", from.getDescripcion());
	    to.setAttribute ("maximoCreditos", from.getMaximoCreditos());
	    to.setAttribute ("maximoCursos", from.getMaximoCursos());
	    to.setAttribute ("minimoCreditos", from.getMinimoCreditos());
	    to.setAttribute ("minimoCursos", from.getMinimoCursos());
	    to.setAttribute ("numero", from.getNumero());
	    if(from.getPlanEstudio() != null) {
	    	to.setAttribute ("planEstudio", from.getPlanEstudio().getIdPlanEstudio());
	    	to.setAttribute ("planEstudioNombre", from.getPlanEstudio().getNombre());
	    	to.setAttribute ("planEstudioCodigo", from.getPlanEstudio().getCodigo());
	    	to.setAttribute ("idPlanEstudio", from.getPlanEstudio().getIdPlanEstudio());
	    }
	}
}