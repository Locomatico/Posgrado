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

import uni.posgrado.shared.model.Actividad;
import uni.posgrado.shared.model.PeriodoActividad;
import uni.posgrado.shared.model.PeriodoPrograma;

import java.util.List;

public class PeriodoActividadGwtRPCDS extends GwtRpcDataSource {

	public PeriodoActividadGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idPeriodoActividad", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        field = new DataSourceIntegerField ("periodoPrograma", "Periodo Pograma");
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (true);
        addField (field);
        
        field = new DataSourceTextField ("actividadCodigo", "actividad", 100, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("actividad", "Codigo", 10, true);
        field.setDisplayField("actividadCodigo");
        addField (field);
        field = new DataSourceTextField ("actividadNombre", "Actividad", 150, true);
        field.setDisplayField("actividadNombre");
        addField (field);
        
        field = new DataSourceDateField ("fechaInicio", "Fecha de Inicio");
        field.setRequired (true);
        addField (field);
        field = new DataSourceDateField ("fechaFin", "Fecha Fin");
        field.setRequired (true);
        addField (field);
        field = new DataSourceBooleanField ("estado", "Estado");
        field.setRequired (false);        
        addField (field);
        /*field = new DataSourceIntegerField ("idUsuario", "Usuario");
        field.setRequired (false);
        field.setCanEdit(false);
        field.setHidden(true);
        addField (field);*/
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final PeriodoActividadGwtRPCDSServiceAsync service = GWT.create (PeriodoActividadGwtRPCDSService.class);
		final PeriodoActividad testRec = new PeriodoActividad();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<PeriodoActividad>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<PeriodoActividad> result) {
			                ListGridRecord[] list = new ListGridRecord[result.size ()];
			                for (int i = 0; i < list.length; i++) {
			                    ListGridRecord record = new ListGridRecord ();
			                    copyValues (result.get (i), record);
			                    list[i] = record;
			                    //System.out.print("iprimir abrev:"+ result.get(i).getAbreviatura());
			                    //System.out.print("iprimir id :"+ result.get(i).getPeriodoPrograma().getIdPeriodoPrograma());
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
	    PeriodoActividad testRec = new PeriodoActividad();
	    copyValues (rec, testRec, false);
	    PeriodoActividadGwtRPCDSServiceAsync service = GWT.create (PeriodoActividadGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<PeriodoActividad> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (PeriodoActividad result) {
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
	    PeriodoActividad testRec = new PeriodoActividad();
	    copyValues (rec, testRec, false);
	    PeriodoActividadGwtRPCDSServiceAsync service = GWT.create (PeriodoActividadGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<PeriodoActividad> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (PeriodoActividad result) {
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
	    PeriodoActividad testRec = new PeriodoActividad();
	    copyValues (rec, testRec,false);
	    PeriodoActividadGwtRPCDSServiceAsync service = GWT.create (PeriodoActividadGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, PeriodoActividad to, Boolean is_filter) {
	    to.setIdPeriodoActividad(from.getAttributeAsInt("idPeriodoActividad"));
	    
	    PeriodoPrograma pp = new PeriodoPrograma();
	    pp.setIdPeriodoPrograma(from.getAttributeAsInt("periodoPrograma"));
	    to.setPeriodoPrograma(pp);
	    
	    if(from.getAttribute ("actividad") != null || from.getAttributeAsString ("actividadCodigo") != null || from.getAttributeAsString ("actividadNombre") != null) {
	    	Actividad actividad = new Actividad();
			if(from.getAttribute ("actividad") != null) {
				if(is_filter)
					actividad.setCodigo(from.getAttributeAsString ("actividad"));
				else
					actividad.setIdActividad(from.getAttributeAsInt ("actividad"));
			}				
			if(from.getAttributeAsString ("actividadCodigo") != null)
				actividad.setCodigo(from.getAttributeAsString ("actividadCodigo"));
			if(from.getAttributeAsString ("actividadNombre") != null)
				actividad.setNombre(from.getAttributeAsString ("actividadNombre"));
			to.setActividad(actividad);
		}
	    
	    to.setFechaInicio(from.getAttributeAsDate ("fechaInicio"));
	    to.setFechaFin(from.getAttributeAsDate("fechaFin"));
	    if(from.getAttribute("estado") != null) 
			to.setEstado(from.getAttributeAsBoolean ("estado"));
	}
	
	/*static void copyValuesFilter (ListGridRecord from, PeriodoActividad to, Boolean is_filter) {
		to.setIdPeriodoActividad(from.getAttributeAsInt("idPeriodoActividad"));
	    
	    PeriodoPrograma pp = new PeriodoPrograma();
	    pp.setIdPeriodoPrograma(from.getAttributeAsInt("periodoPrograma"));
	    to.setPeriodoPrograma(pp);
	    
	    if(from.getAttribute ("actividad") != null || from.getAttributeAsString ("actividadCodigo") != null || from.getAttributeAsString ("actividadNombre") != null) {
	    	Actividad actividad = new Actividad();
			if(from.getAttribute ("actividad") != null) {
				if(!is_filter)
					actividad.setCodigo(from.getAttributeAsString ("actividad"));
				else
					actividad.setIdActividad (from.getAttributeAsInt ("actividad"));
			}				
			if(from.getAttributeAsString ("actividadCodigo") != null)
				actividad.setCodigo(from.getAttributeAsString ("actividadCodigo"));
			if(from.getAttributeAsString ("actividadNombre") != null)
				actividad.setNombre(from.getAttributeAsString ("actividadNombre"));
			to.setActividad(actividad);
		}
	    
	    
	    to.setFechaInicio(from.getAttributeAsDate ("fechaInicio"));
	    to.setFechaFin(from.getAttributeAsDate("fechaFin"));
	    to.setEstado(from.getAttributeAsBoolean ("estado"));
	    /*if(from.getAttribute ("idPersona") != null) {
    		to.setUsuarioCreacion(from.getAttributeAsInt("idUsuario"));
    	}
	}*/
	
	private static void copyValues (PeriodoActividad from, ListGridRecord to) {
	    to.setAttribute ("idPeriodoActividad", from.getIdPeriodoActividad());
	    to.setAttribute ("periodoPrograma", from.getPeriodoPrograma().getIdPeriodoPrograma());
	    
	    if( from.getActividad() != null) {
	    	to.setAttribute ("actividad", from.getActividad().getIdActividad());
		    to.setAttribute ("actividadCodigo", from.getActividad().getCodigo());
		    to.setAttribute ("actividadNombre", from.getActividad().getNombre());
	    }
	    
	    to.setAttribute ("fechaInicio", from.getFechaInicio());
	    to.setAttribute ("fechaFin", from.getFechaFin());
	    to.setAttribute ("estado", from.getEstado());
	}
}