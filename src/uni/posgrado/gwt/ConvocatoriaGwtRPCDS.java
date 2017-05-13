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

import uni.posgrado.shared.model.Convocatoria;
import uni.posgrado.shared.model.Periodo;
import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;

import java.util.List;

public class ConvocatoriaGwtRPCDS extends GwtRpcDataSource {

	public ConvocatoriaGwtRPCDS (Boolean complete) {	    
	    DataSourceField field;
        field = new DataSourceIntegerField ("idConvocatoria", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);       
        // AutoIncrement on server.
        field.setRequired (false);
        addField (field);
        
        field = new DataSourceTextField ("periodoCodigo", "Cod. Periodo", 10, true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceIntegerField ("periodo", "Periodo", 10, true);
        field.setDisplayField("periodoCodigo");
        addField (field);
        
        field = new DataSourceTextField ("nombre", "Nombre", 150, true);
        //field.setRequired (true);
        addField (field);
        field = new DataSourceTextField ("convocTipoNombre", "Nombre TIPEXA");
        field.setRequired (true);
        field.setHidden(true);
        addField (field);
        field = new DataSourceTextField ("tipoConvocatoria", "Tipo Convocatoria.");
        field.setRequired (true);
        field.setDisplayField("convocTipoNombre");
        addField (field);
        field = new DataSourceTextField ("descripcion", "Descripción");
        field.setRequired (false);
        field.setCanFilter(false);        
        field.setCanSortClientOnly(false);
        if(!complete)
        	field.setHidden(true);
        addField (field);        
        field = new DataSourceDateField ("fecInicio", "F. Inicio");
        field.setRequired (true);
        if(!complete)
        	field.setHidden(true);
        addField (field);
        field = new DataSourceDateField ("fecFin", "F. Fin");
        field.setRequired (true);
        if(!complete)
        	field.setHidden(true);
        addField (field);
        field = new DataSourceBooleanField ("estado", "Estado");
        field.setRequired (false);
        if(!complete)
        	field.setHidden(true);
        addField (field); 	    
	}
	
	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {
	    // These can be used as parameters to create paging.
	    /*
		request.getStartRow ();
	    request.getEndRow ();        
	    request.getSortBy ();
	    */   	
	    final ConvocatoriaGwtRPCDSServiceAsync service = GWT.create (ConvocatoriaGwtRPCDSService.class);	    
		final Convocatoria testRec = new Convocatoria ();
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Convocatoria>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Convocatoria> result) {
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
	    Convocatoria testRec = new Convocatoria ();
	    copyValues (rec, testRec, false);
	    //testRec.setUserAdd(new Float(Cookies.getCookie("id")));
	    ConvocatoriaGwtRPCDSServiceAsync service = GWT.create (ConvocatoriaGwtRPCDSService.class);
	    service.add (testRec, new AsyncCallback<Convocatoria> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Convocatoria result) {
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
	    Convocatoria testRec = new Convocatoria ();
	    copyValues (rec, testRec, false);
	    //testRec.setUserEdit(new Float(Cookies.getCookie("id")));
	    ConvocatoriaGwtRPCDSServiceAsync service = GWT.create (ConvocatoriaGwtRPCDSService.class);
	    service.update (testRec, new AsyncCallback<Convocatoria> () {
	        public void onFailure (Throwable caught) {
	            response.setStatus (RPCResponse.STATUS_FAILURE);
	            processResponse (requestId, response);
	        }
	        public void onSuccess (Convocatoria result) {
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
	    Convocatoria testRec = new Convocatoria ();
	    copyValues (rec, testRec, false);
	    //testRec.setUserDel(new Float(Cookies.getCookie("id")));
	    ConvocatoriaGwtRPCDSServiceAsync service = GWT.create (ConvocatoriaGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Convocatoria to, Boolean is_filter) {
		to.setIdConvocatoria(from.getAttributeAsInt ("idConvocatoria"));
	    to.setNombre(from.getAttributeAsString ("nombre"));	    
	    if(from.getAttribute("estado") != null) {
	    	to.setEstado (from.getAttributeAsBoolean ("estado"));  
	    }    	
    	to.setDescripcion (from.getAttributeAsString ("descripcion"));    	
    	to.setFecFin(from.getAttributeAsDate("fecFin"));
    	to.setFecInicio(from.getAttributeAsDate ("fecInicio"));
    	
    	
    	if(from.getAttribute ("periodo") != null || from.getAttributeAsString ("periodoCodigo") != null) {
	    	Periodo periodo = new Periodo();
			if(from.getAttribute ("periodo") != null) {
				if(is_filter)
					periodo.setCodigo(from.getAttributeAsString ("periodo"));
				else
					periodo.setIdPeriodo(from.getAttributeAsInt ("periodo"));
			}				
			if(from.getAttributeAsString ("periodoCodigo") != null)
				periodo.setCodigo(from.getAttributeAsString ("periodoCodigo"));
			to.setPeriodo(periodo);
		}
    	
	    if(from.getAttribute ("tipoConvocatoria") != null) {
			Variable var1 = new Variable();
			VariablePK var_pk = new VariablePK();
			var_pk.setTipTabla("TIPEXA");
			var_pk.setCodTabla(from.getAttributeAsString ("tipoConvocatoria"));
			var1.setId(var_pk);
			to.setTipoConvocatoria(var1);
		}
	}
	
	private static void copyValues (Convocatoria from, ListGridRecord to) {		 
	    to.setAttribute ("idConvocatoria", from.getIdConvocatoria());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("descripcion", from.getDescripcion());
	    to.setAttribute ("estado", from.getEstado());	
	    to.setAttribute ("fecFin", from.getFecFin());
	    to.setAttribute ("fecInicio", from.getFecInicio());  
	    if( from.getPeriodo() != null) {
	    	to.setAttribute ("periodo", from.getPeriodo().getIdPeriodo());
		    to.setAttribute ("periodoCodigo", from.getPeriodo().getCodigo());
	    }
	    if( from.getTipoConvocatoria() != null) {
			to.setAttribute("convocTipoNombre", from.getTipoConvocatoria().getNomTabla());
			to.setAttribute("tipoConvocatoria", from.getTipoConvocatoria().getId().getCodTabla());
		}
	}	
}