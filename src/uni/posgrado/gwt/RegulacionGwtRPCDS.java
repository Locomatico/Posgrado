package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import uni.posgrado.shared.model.Regulacion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RegulacionGwtRPCDS extends GwtRpcDataSource {

	public RegulacionGwtRPCDS (Boolean hidden) {
	    DataSourceField field;
        field = new DataSourceIntegerField ("idRegulacion", "ID");
        field.setPrimaryKey (true);
        field.setHidden(true);
        field.setRequired (false);
        addField (field);
        field = new DataSourceTextField ("codigo", "Código", 6, true);
        
        addField (field);
        field = new DataSourceTextField ("nombre", "Nombre", 250, true);
        addField (field);
        field = new DataSourceFloatField ("inasistencias", "Inasistencias", 3, false);
        field.setHidden(hidden);
        field.setCanEdit(false);
        addField (field);
        field = new DataSourceFloatField ("inasistenciaJustificada", "I. Justificada", 3, true);
        field.setHidden(hidden);
        CustomValidator cv = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				// TODO Auto-generated method stub
				if(value != null && !value.equals("")) {
					if(this.getRecord().getAttribute("inasistenciaInjustificada") != null && !this.getRecord().getAttribute("inasistenciaInjustificada").isEmpty()) {
						BigDecimal Valor = new BigDecimal(this.getRecord().getAttribute("inasistenciaInjustificada").toString());
						Valor = Valor.add(new BigDecimal(Valor.toString()));
                  		return true;
					}
				}
				return true;
			}        	
        };
        cv.setValidateOnChange(true);
        field.setValidators(cv);
        addField (field);
        field = new DataSourceFloatField ("inasistenciaInjustificada", "I. Injustificada", 3, true);
        field.setHidden(hidden);
        cv = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				// TODO Auto-generated method stub
				if(value != null && !value.equals("")) {
					if(this.getRecord().getAttribute("inasistenciaJustificada") != null && !this.getRecord().getAttribute("inasistenciaJustificada").isEmpty()) {
						BigDecimal Valor = new BigDecimal(this.getRecord().getAttribute("inasistenciaJustificada").toString());
						Valor = Valor.add(new BigDecimal(Valor.toString()));
                  		return true;
					}
				}
				return true;
			}        	
        };
        cv.setValidateOnChange(true);
        field.setValidators(cv);
        addField (field);
        field = new DataSourceFloatField ("retiro", "Retiro (%)", 3, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceFloatField ("notaMaxima", "Nota Máxima", 6, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceFloatField ("notaMinAprobatoria", "Nota Min. Aprob.", 6, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceFloatField ("notaMinima", "Nota Mínima", 6, true);
        field.setHidden(hidden);
        addField (field);
        field = new DataSourceDateField ("fechaVigencia", "Vigencia", 10, true);
        field.setHidden(hidden);
        DateItem DateFin = new DateItem();
        DateFin.setStartDate(new Date());        
        Date dueDate = new Date();
        CalendarUtil.addMonthsToDate(dueDate, 120);
        DateFin.setEndDate(dueDate);
        field.setEditorProperties(DateFin);
        addField (field);
        field = new DataSourceTextField ("descripcion", "Descripción", 1000, false);
        field.setHidden(hidden);
        addField (field);       
        
	}
	


	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {

	    final RegulacionGwtRPCDSServiceAsync service = GWT.create (RegulacionGwtRPCDSService.class);
		final Regulacion testRec = new Regulacion ();
		ListGridRecord rec = new ListGridRecord (request.getCriteria().getJsObj());
		copyValues(rec, testRec);
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
			        service.fetch (startRow, endRow, testRec, sortBy, new AsyncCallback<List<Regulacion>> () {
			            public void onFailure (Throwable caught) {
			                response.setStatus (RPCResponse.STATUS_FAILURE);
			                processResponse (requestId, response);
			            }
			            public void onSuccess (List<Regulacion> result) {
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
	    final Regulacion testRec = new Regulacion ();
	    copyValues (rec, testRec);
	    final RegulacionGwtRPCDSServiceAsync service = GWT.create (RegulacionGwtRPCDSService.class);
	    service.valida_codigo(0, testRec.getCodigo(), new AsyncCallback<Boolean>() {			
			@Override
			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub
				if(result) {
					service.add (testRec, new AsyncCallback<Regulacion> () {
				        public void onFailure (Throwable caught) {
				        	response.setStatus (RPCResponse.STATUS_FAILURE); 
				            response.setAttribute("data", caught.getMessage());
				            processResponse (requestId, response);
				        }
				        public void onSuccess (Regulacion result) {
				            ListGridRecord[] list = new ListGridRecord[1];
				            ListGridRecord newRec = new ListGridRecord ();
				            copyValues (result, newRec);
				            list[0] = newRec;
				            response.setData (list);
				            processResponse (requestId, response);
				        }
				    });
				} else {
					response.setStatus (RPCResponse.STATUS_VALIDATION_ERROR); 
		            response.setAttribute("data", "Código no disponible");
		            processResponse (requestId, response);
		            //SC.warn("Error", "Código no disponible");
				}				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				response.setStatus (RPCResponse.STATUS_FAILURE); 
	            response.setAttribute("data", caught.getMessage());
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
	    final Regulacion testRec = new Regulacion ();
	    copyValues (rec, testRec);
	    final RegulacionGwtRPCDSServiceAsync service = GWT.create (RegulacionGwtRPCDSService.class);
	    service.valida_codigo(testRec.getIdRegulacion(), testRec.getCodigo(), new AsyncCallback<Boolean>() {			
			@Override
			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub
				if(result) {
					service.update (testRec, new AsyncCallback<Regulacion> () {
				        public void onFailure (Throwable caught) {
				            response.setStatus (RPCResponse.STATUS_FAILURE); 
				            response.setAttribute("data", caught.getMessage());
				            processResponse (requestId, response);
				        }
				        public void onSuccess (Regulacion result) {
				            ListGridRecord[] list = new ListGridRecord[1];
				            ListGridRecord updRec = new ListGridRecord ();
				            copyValues (result, updRec);
				            list[0] = updRec;
				            response.setData (list);
				            processResponse (requestId, response);
				        }
				    });
				} else {
					response.setStatus (RPCResponse.STATUS_VALIDATION_ERROR); 
		            response.setAttribute("data", "Código no disponible");
		            processResponse (requestId, response);
		            //SC.warn("Error", "Código no disponible");
				}			
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				response.setStatus (RPCResponse.STATUS_FAILURE);
				response.setAttribute("data", caught.getMessage());
	            processResponse (requestId, response);
			}
		});
	}
	
	@Override
	protected void executeRemove (final String requestId, final DSRequest request, final DSResponse response) {
	    // Retrieve record which should be removed.
	    JavaScriptObject data = request.getData ();
	    final ListGridRecord rec = new ListGridRecord (data);
	    Regulacion testRec = new Regulacion ();
	    copyValues (rec, testRec);
	    RegulacionGwtRPCDSServiceAsync service = GWT.create (RegulacionGwtRPCDSService.class);
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
	
	static void copyValues (ListGridRecord from, Regulacion to) {
	    to.setIdRegulacion (from.getAttributeAsInt ("idRegulacion"));
	    to.setDescripcion (from.getAttributeAsString ("descripcion"));
	    if(from.getAttributeAsString ("nombre") != null)
	    	to.setNombre (from.getAttributeAsString ("nombre").toUpperCase().trim());
	    if(from.getAttributeAsString ("codigo") != null)
	    	to.setCodigo (from.getAttributeAsString ("codigo").toUpperCase().trim());    	
    	if(from.getAttributeAsString ("fechaVigencia") != null)
	    	to.setFechaVigencia (from.getAttributeAsDate ("fechaVigencia"));
    	if(from.getAttributeAsString ("inasistenciaInjustificada") != null)
	    	to.setInasistenciaInjustificada (new BigDecimal(from.getAttribute ("inasistenciaInjustificada").toString()));
    	if(from.getAttributeAsString ("inasistenciaJustificada") != null)
	    	to.setInasistenciaJustificada (new BigDecimal(from.getAttribute ("inasistenciaJustificada").toString()));
    	if(from.getAttributeAsString ("inasistencias") != null)
	    	to.setInasistencias (new BigDecimal(from.getAttribute ("inasistencias").toString()));    	
    	if(from.getAttribute ("notaMaxima") != null)
	    	to.setNotaMaxima (new BigDecimal(from.getAttribute ("notaMaxima").toString()));
    	if(from.getAttribute ("notaMinima") != null)
	    	to.setNotaMinima (new BigDecimal(from.getAttribute ("notaMinima").toString()));
    	if(from.getAttribute ("notaMinAprobatoria") != null)
	    	to.setNotaMinAprobatoria (new BigDecimal(from.getAttribute ("notaMinAprobatoria").toString()));
    	if(from.getAttributeAsString ("retiro") != null)
	    	to.setRetiro (new BigDecimal(from.getAttribute ("retiro").toString()));  
	}
	
	private static void copyValues (Regulacion from, ListGridRecord to) {
	    to.setAttribute ("idRegulacion", from.getIdRegulacion());
	    to.setAttribute ("nombre", from.getNombre());
	    to.setAttribute ("codigo", from.getCodigo());
	    to.setAttribute ("descripcion", from.getDescripcion());
	    to.setAttribute ("fechaVigencia", from.getFechaVigencia());
	    to.setAttribute ("inasistenciaInjustificada", from.getInasistenciaInjustificada());
	    to.setAttribute ("inasistenciaJustificada", from.getInasistenciaJustificada());
	    to.setAttribute ("inasistencias", from.getInasistencias());
	    to.setAttribute ("notaMaxima", from.getNotaMaxima());
	    to.setAttribute ("notaMinima", from.getNotaMinima());
	    to.setAttribute ("notaMinAprobatoria", from.getNotaMinAprobatoria());
	    to.setAttribute ("retiro", from.getRetiro());
	}
}