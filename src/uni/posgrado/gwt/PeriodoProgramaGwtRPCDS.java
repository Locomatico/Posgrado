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


import uni.posgrado.shared.model.Periodo;
import uni.posgrado.shared.model.PeriodoPrograma;
import uni.posgrado.shared.model.Programa;

import java.util.List;

public class PeriodoProgramaGwtRPCDS extends GwtRpcDataSource {

	public PeriodoProgramaGwtRPCDS () {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idPeriodoPrograma", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        
        field = new DataSourceTextField ("programaCodigo", "programa", 100, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("programa", "Codigo", 10, true);
        field.setDisplayField("programaCodigo");
        addField (field);
        field = new DataSourceTextField ("programaNombre", "Programa", 150, true);
        //field.setHidden(true);
        field.setDisplayField("programaNombre");
        addField (field);
        
        field = new DataSourceTextField ("periodoCodigo", "Cod. Periodo", 10, true);
        //field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("periodo", "Periodo", 10, true);
        field.setDisplayField("periodoCodigo");
        addField (field);
        field = new DataSourceIntegerField ("idPeriodo", "IdPeriodo", 10, true);
        field.setHidden(true);
        addField (field);
        
        
        field = new DataSourceTextField ("nombre", "Nom. Periodo", 150, true);
        //field.setHidden(true);
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

	    final PeriodoProgramaGwtRPCDSServiceAsync service = GWT.create (PeriodoProgramaGwtRPCDSService.class);
		final PeriodoPrograma testRec = new PeriodoPrograma();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<PeriodoPrograma>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<PeriodoPrograma> result) {
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
	    PeriodoPrograma testRec = new PeriodoPrograma();
	    copyValues (rec, testRec, false);
	    PeriodoProgramaGwtRPCDSServiceAsync service = GWT.create (PeriodoProgramaGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<PeriodoPrograma> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (PeriodoPrograma result) {
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
	    PeriodoPrograma testRec = new PeriodoPrograma();
	    copyValues (rec, testRec, false);
	    PeriodoProgramaGwtRPCDSServiceAsync service = GWT.create (PeriodoProgramaGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<PeriodoPrograma> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (PeriodoPrograma result) {
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
	    PeriodoPrograma testRec = new PeriodoPrograma();
	    copyValues (rec, testRec, false);
	    PeriodoProgramaGwtRPCDSServiceAsync service = GWT.create (PeriodoProgramaGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, PeriodoPrograma to, Boolean is_filter) {
	    to.setIdPeriodoPrograma(from.getAttributeAsInt ("idPeriodoPrograma"));
	    
	    if(from.getAttribute ("programa") != null || from.getAttributeAsString ("programaCodigo") != null || from.getAttributeAsString ("programaNombre") != null) {
	    	Programa programa = new Programa();
			if(from.getAttribute ("programa") != null) {
				if(is_filter)
					programa.setCodigo(from.getAttributeAsString ("programa"));
				else
					programa.setIdPrograma (from.getAttributeAsInt ("programa"));
			}				
			if(from.getAttributeAsString ("programaCodigo") != null)
				programa.setCodigo(from.getAttributeAsString ("programaCodigo"));
			if(from.getAttributeAsString ("programaNombre") != null)
				programa.setNombre(from.getAttributeAsString ("programaNombre"));
			to.setPrograma(programa);
		}
	    
	    if(from.getAttribute ("periodo") != null || from.getAttributeAsString ("periodoCodigo") != null || from.getAttribute ("idPeriodo") != null) {
	    	Periodo periodo = new Periodo();
			if(from.getAttribute ("periodo") != null) {
				if(is_filter)
					periodo.setCodigo(from.getAttributeAsString ("periodo"));
				else
					periodo.setIdPeriodo(from.getAttributeAsInt ("periodo"));
			}
			if(from.getAttribute ("idPeriodo") != null) {
				//System.out.println("entro:"+from.getAttributeAsString ("idPeriodo"));
					periodo.setIdPeriodo(from.getAttributeAsInt ("idPeriodo"));
			}
			if(from.getAttributeAsString ("periodoCodigo") != null)
				periodo.setCodigo(from.getAttributeAsString ("periodoCodigo"));
			to.setPeriodo(periodo);
		}
	    
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase());
	    to.setFechaInicio(from.getAttributeAsDate ("fechaInicio"));
	    to.setFechaFin(from.getAttributeAsDate("fechaFin"));
	    if(from.getAttribute("estado") != null) 
			to.setEstado(from.getAttributeAsBoolean ("estado"));
	}
	
	
	private static void copyValues (PeriodoPrograma from, ListGridRecord to) {
	    to.setAttribute ("idPeriodoPrograma", from.getIdPeriodoPrograma());
	    
	    if( from.getPrograma() != null) {
	    	to.setAttribute ("programa", from.getPrograma().getIdPrograma());
		    to.setAttribute ("programaCodigo", from.getPrograma().getCodigo());
		    to.setAttribute ("programaNombre", from.getPrograma().getNombre());
	    }
	    if( from.getPeriodo() != null) {
	    	to.setAttribute ("periodo", from.getPeriodo().getIdPeriodo());
		    to.setAttribute ("periodoCodigo", from.getPeriodo().getCodigo());
	    }
	    
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("fechaInicio", from.getFechaInicio());
	    to.setAttribute ("fechaFin", from.getFechaFin());
	    to.setAttribute ("estado", from.getEstado());
	}
}