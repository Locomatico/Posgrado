package uni.posgrado.client;

import java.util.Date;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.TransferImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.gwt.ActividadGwtRPCDS;
import uni.posgrado.gwt.PeriodoActividadGwtRPCDS;
import uni.posgrado.gwt.PeriodoProgramaGwtRPCDS;



public class PeriodoActividadLayout extends VLayout {

	public PeriodoActividadLayout() {     
		setOverflow(Overflow.AUTO);
		final BasicVLayout lista = new BasicVLayout(null, "Programación Academica", "PeriodoProgramaServlet", 800, "normal", null);
		PeriodoProgramaGwtRPCDS periodo = new PeriodoProgramaGwtRPCDS();
		lista.setDataSource(periodo);
		lista.setFormTextType("periodo");
		lista.setFormTextType("programa");
		lista.setFormTextType("programaNombre");
		lista.getListGrid().hideField("periodo");
		lista.getListGrid().hideField("nombre");
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().getField("idPeriodoPrograma");		
		addMember(lista);
		
		final HLayout HLDetails = new HLayout();
		HLDetails.setLayoutAlign(Alignment.CENTER);
		HLDetails.setAlign(Alignment.CENTER);
		HLDetails.setOverflow(Overflow.VISIBLE);
		HLDetails.setMargin(10);
		HLDetails.setMembersMargin(15);
		HLDetails.setWidth(1000);
		final BasicVLayout actividad = new BasicVLayout(null, "Actividades sin asignar", "", 600, "normal", null);
		actividad.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		actividad.setDataSource(new ActividadGwtRPCDS());
		actividad.getListGrid().setHeight(300);
		actividad.getListGrid().setCanEdit(false);
		actividad.getListGrid().setCanPickFields(false);
		actividad.getListGrid().setCanGroupBy(false);
		actividad.hideAllButtons();
		actividad.hideSummary();
		actividad.setSize(450, false);
		actividad.getListGrid().setCanDragRecordsOut(true);
		actividad.getListGrid().setDragDataAction(DragDataAction.NONE);
		actividad.getListGrid().setAddDropValues(false);
		actividad.getListGrid().setCanAcceptDroppedRecords(true);	
		HLDetails.addMember(actividad);
		
		final BasicVLayout periodoActividad = new BasicVLayout(null, "Actividades Asignadas", "", 600, "normal", null);	
		periodoActividad.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		periodoActividad.setDataSource(new PeriodoActividadGwtRPCDS());
		periodoActividad.getListGrid().setHeight(300);
		periodoActividad.setFormTextType("actividad");
		periodoActividad.setFormTextType("actividadNombre");
		periodoActividad.getListGrid().getField("actividad").setCanEdit(false);
		periodoActividad.getListGrid().getField("actividadNombre").setCanEdit(false);
		periodoActividad.getListGrid().setCanEdit(true);
		periodoActividad.getListGrid().setAutoSaveEdits(true);
		periodoActividad.getListGrid().setCanPickFields(false);
		periodoActividad.getListGrid().setCanGroupBy(false);
		periodoActividad.hideAllButtons();
		periodoActividad.hideSummary();
		periodoActividad.setSize(550, false);
		periodoActividad.getListGrid().setCanDragRecordsOut(true);
		periodoActividad.getListGrid().setDragDataAction(DragDataAction.NONE);
		periodoActividad.getListGrid().setCanAcceptDroppedRecords(true);
		periodoActividad.getListGrid().setAddDropValues(false);
		
		periodoActividad.setFormSimpleDate("fechaInicio");
		periodoActividad.setFormSimpleDate("fechaFin");
		periodoActividad.getListGrid().getField("fechaInicio").setWidth(75);
		periodoActividad.getListGrid().getField("fechaFin").setWidth(75);
		
		CustomValidator cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {
        			if(value != null && !value.equals("")) {
	        			int id = periodoActividad.getListGrid().getEditRow();
	        			if(periodoActividad.getListGrid().getEditedRecord(id).getAttribute("fechaInicio") != null && !periodoActividad.getListGrid().getEditedRecord(id).getAttribute("fechaInicio").isEmpty()) {
	        				Date date_ini = (Date) periodoActividad.getListGrid().getEditedRecord(id).getAttributeAsDate("fechaInicio");
	                		if (date_ini.before((Date) value)){
	                			return true;
	                		} else
	                			return false;
	        			} else
	        				return true;  
        			} else
        				return true; 
        		} catch (Exception e) {
        			System.out.println("error " + e);
        			return false;
        		}        		
        	}
        };
        cv.setErrorMessage("Debe ser mayor a la fecha de inicio");
        periodoActividad.getListGrid().getField("fechaFin").setValidators(cv);
        
        cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {
        			if(value != null && !value.equals("")) {
        				int id = periodoActividad.getListGrid().getEditRow();
            			if(periodoActividad.getListGrid().getEditedRecord(id).getAttribute("fechaFin") != null && !periodoActividad.getListGrid().getEditedRecord(id).getAttribute("fechaFin").isEmpty()) {
            				Date date_ini = (Date) periodoActividad.getListGrid().getEditedRecord(id).getAttributeAsDate("fechaFin");
                      		if (date_ini.after((Date) value)){
                    			return true;
                    		} else
                    			return false;
            			} else
            				return true;
        			} else
        				return true;        			      			
        		} catch (Exception e) {
        			System.out.println("error " + e);
        			return false;
        		}        		
        	}
        };
        cv.setErrorMessage("Debe ser menor a la fecha de fin");
        periodoActividad.getListGrid().getField("fechaInicio").setValidators(cv);
        
        /**/
        cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {
        			if(value != null && !value.equals("")) {  				
            			
            			//Convocatoria dates
            			if(lista.getListGrid().anySelected() && lista.getListGrid().getSelectedRecord().getAttribute("fechaInicio") != null && !lista.getListGrid().getSelectedRecord().getAttribute("fechaInicio").isEmpty()) {
            				Date date = (Date) lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaInicio");
                      		if (!date.before((Date) value)) {
                      			return false;
                      		}
            			}
            			if(lista.getListGrid().anySelected() && lista.getListGrid().getSelectedRecord().getAttribute("fechaFin") != null && !lista.getListGrid().getSelectedRecord().getAttribute("fechaFin").isEmpty()) {
            				Date date = (Date) lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaFin");
                      		if (!date.after((Date) value)) {
                      			return false;
                      		}
            			}
        			}
        			return true;
        		} catch (Exception e) {
        			System.out.println("error " + e);
        			return false;
        		}        		
        	}
        };
        cv.setErrorMessage("Fecha no válida");
        periodoActividad.getListGrid().getField("fechaInicio").setValidators(cv);
        
        cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {
        			if(value != null && !value.equals("")) {   				
            			if(lista.getListGrid().anySelected() && lista.getListGrid().getSelectedRecord().getAttribute("fechaInicio") != null && !lista.getListGrid().getSelectedRecord().getAttribute("fechaInicio").isEmpty()) {
            				Date date = (Date) lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaInicio");
                      		if (!date.before((Date) value)) {
                      			return false;
                      		}
            			}
            			if(lista.getListGrid().anySelected() && lista.getListGrid().getSelectedRecord().getAttribute("fechaFin") != null && !lista.getListGrid().getSelectedRecord().getAttribute("fechaFin").isEmpty()) {
            				Date date = (Date) lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaFin");
                      		if (!date.after((Date) value)) {
                      			return false;
                      		}
            			}
        			}     
        			return true;
        		} catch (Exception e) {
        			System.out.println("error " + e);
        			return false;
        		}        		
        	}
        };
        cv.setErrorMessage("Fecha no válida");
        periodoActividad.getListGrid().getField("fechaFin").setValidators(cv);
        
        /**/
		
		periodoActividad.getListGrid().addCellSavedHandler(new CellSavedHandler() {
			
			@Override
			public void onCellSaved(CellSavedEvent event) {
				// TODO Auto-generated method stub
				periodoActividad.refreshListGrid();
			}
		});
		
		final TransferImgButton arrowLeftImg = new TransferImgButton(TransferImgButton.LEFT);
		final TransferImgButton arrowRightImg = new TransferImgButton(TransferImgButton.RIGHT);		
		
		final VLayout buttonsLayout = new VLayout();
		buttonsLayout.setLayoutAlign(Alignment.CENTER);
		buttonsLayout.setAlign(Alignment.CENTER);
		
		actividad.getListGrid().addRecordDropHandler(new RecordDropHandler() {			
			@Override
			public void onRecordDrop(RecordDropEvent event) {
				// TODO Auto-generated method stub
				event.cancel();
				if(periodoActividad.getListGrid().anySelected()) {
        	    	SC.confirm("Eliminar registros", "¿Está seguro que desea eliminar el/los registro(s) seleccionado(s)?", new BooleanCallback() {
        				@Override
        				public void execute(Boolean value) {
        					// TODO Auto-generated method stub						
        					if (value != null && value) {
        						DSRequest requestProperties = new DSRequest();
        						periodoActividad.getListGrid().removeSelectedData(new DSCallback() {								
        							@Override
        							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
        								//delButton.disable();
        								actividad.refreshListGridDepend();
        								periodoActividad.refreshListGridDepend();
        				           		if (periodoActividad.getListGrid().anySelected()) {
        				           			arrowLeftImg.setDisabled(false);
        				           		} else {
        				           			arrowLeftImg.setDisabled(true);
        				           		}
        				           		if (actividad.getListGrid().anySelected()) {
        				           			arrowRightImg.setDisabled(false);
        				           		} else {
        				           			arrowRightImg.setDisabled(true);
        				           		}
        							}
        						}, requestProperties);								
        	                } else {					
        	                }
        				}
        			});
            	}					
			}
		});
		
		periodoActividad.getListGrid().addRecordDropHandler(new RecordDropHandler() {			
			@Override
			public void onRecordDrop(RecordDropEvent event) {
				// TODO Auto-generated method stub
				event.cancel();
				if(actividad.getListGrid().anySelected() && lista.getListGrid().anySelected()) {
					Record record = new Record();
	            	record.setAttribute("actividad", actividad.getListGrid().getSelectedRecord().getAttributeAsInt("idActividad"));
	            	record.setAttribute("periodoPrograma", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodoPrograma"));
	            	//record.setAttribute("abreviatura", actividad.getListGrid().getSelectedRecord().getAttributeAsString("abreviatura"));
	            	record.setAttribute("fechaInicio", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaInicio"));
	            	record.setAttribute("fechaFin", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaFin"));
	            	record.setAttribute("estado", true);	            	
	            	periodoActividad.getListGrid().startEditingNew(record);
	            	periodoActividad.getListGrid().addData(record, new DSCallback() {
	            		@Override
	            		public void execute(DSResponse dsResponse,
	            				Object data, DSRequest dsRequest) {
	            			// TODO Auto-generated method stub
	            			periodoActividad.refreshListGrid();
	    					actividad.refreshListGridDepend();
	    					if (periodoActividad.getListGrid().anySelected()) {
			           			arrowLeftImg.setDisabled(false);
			           		} else {
			           			arrowLeftImg.setDisabled(true);
			           		}
			           		if (actividad.getListGrid().anySelected()) {
			           			arrowRightImg.setDisabled(false);
			           		} else {
			           			arrowRightImg.setDisabled(true);
			           		}
	            		}
	            	});
				}					
			}
		});
        
		arrowLeftImg.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
           		if(periodoActividad.getListGrid().anySelected()) {
        	    	SC.confirm("Eliminar registros", "¿Está seguro que desea eliminar el/los registro(s) seleccionado(s)?", new BooleanCallback() {
        				@Override
        				public void execute(Boolean value) {
        					// TODO Auto-generated method stub						
        					if (value != null && value) {
        						DSRequest requestProperties = new DSRequest();
        						periodoActividad.getListGrid().removeSelectedData(new DSCallback() {								
        							@Override
        							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
        								//delButton.disable();
        								actividad.refreshListGridDepend();
        								periodoActividad.refreshListGridDepend();
        							}
        						}, requestProperties);								
        	                } else {					
        	                }
        				}
        			});
            	}           		
            }  
        });	
		
		arrowRightImg.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	if(actividad.getListGrid().anySelected() && lista.getListGrid().anySelected()) {
	            	Record record = new Record();
	            	record.setAttribute("actividad", actividad.getListGrid().getSelectedRecord().getAttributeAsInt("idActividad"));
	            	record.setAttribute("periodoPrograma", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodoPrograma"));
	            	//record.setAttribute("abreviatura", actividad.getListGrid().getSelectedRecord().getAttributeAsString("abreviatura"));
	            	record.setAttribute("fechaInicio", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaInicio"));
	            	record.setAttribute("fechaFin", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaFin"));
	            	record.setAttribute("estado", true);	
	            	periodoActividad.getListGrid().addData(record, new DSCallback() {
	            		@Override
	            		public void execute(DSResponse dsResponse,
	            				Object data, DSRequest dsRequest) {
	            			// TODO Auto-generated method stub
	            			periodoActividad.refreshListGrid();
	            			actividad.refreshListGridDepend();
	            		}
	            	});  
            	}
            }  
        });
		
		LayoutSpacer spacer = new LayoutSpacer(); 
        spacer.setHeight(10);
        buttonsLayout.addMember(arrowLeftImg);
        buttonsLayout.addMember(spacer);
        buttonsLayout.addMember(arrowRightImg);
		HLDetails.addMember(buttonsLayout);
		HLDetails.addMember(periodoActividad);
		
		addMember(HLDetails);		
		
		
		lista.getListGrid().setCanEdit(false);
		lista.getListGrid().setCanPickFields(false);
		lista.getListGrid().setCanGroupBy(false);
		lista.hideActionButtons();
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {  
			public void onRecordClick(RecordClickEvent event) {
				Criteria criteria = new Criteria();
				criteria.addCriteria("periodoPrograma", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodoPrograma"));
				periodoActividad.getListGrid().setCriteria(criteria);
				periodoActividad.refreshListGrid();
				criteria=new Criteria();
				criteria.addCriteria("idPeriodoPrograma", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodoPrograma"));
				actividad.getListGrid().setCriteria(criteria);
				actividad.refreshListGridDepend();
				HLDetails.setDisabled(false);
			}
		});
		
		periodoActividad.getListGrid().addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				if (periodoActividad.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (actividad.getListGrid().anySelected()) {
           			arrowRightImg.setDisabled(false);
           		} else {
           			arrowRightImg.setDisabled(true);
           		}
			}
		});
		
		if (periodoActividad.getListGrid().anySelected()) {
   			arrowLeftImg.setDisabled(false);
   		} else {
   			arrowLeftImg.setDisabled(true);
   		}
   		if (actividad.getListGrid().anySelected()) {
   			arrowRightImg.setDisabled(false);
   		} else {
   			arrowRightImg.setDisabled(true);
   		}
		
		actividad.getListGrid().addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				if (periodoActividad.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (actividad.getListGrid().anySelected()) {
           			arrowRightImg.setDisabled(false);
           		} else {
           			arrowRightImg.setDisabled(true);
           		}
			}
		});

		if(!lista.getListGrid().anySelected()) {
			Criteria criteria=new Criteria();
			criteria.addCriteria("periodoPrograma", 0);
			periodoActividad.getListGrid().setCriteria(criteria);
			periodoActividad.refreshListGridDepend();	
			criteria=new Criteria();
			criteria.addCriteria("actividad", 0);
			actividad.getListGrid().setCriteria(criteria);
			actividad.refreshListGridDepend();
			HLDetails.setDisabled(true);
		} else {
			Criteria criteria = new Criteria();
			criteria.addCriteria("periodoPrograma", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodoPrograma"));
			periodoActividad.getListGrid().setCriteria(criteria);
			periodoActividad.refreshListGrid();
			criteria=new Criteria();
			criteria.addCriteria("idPeriodoPrograma", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodoPrograma"));
			actividad.getListGrid().setCriteria(criteria);
			actividad.refreshListGridDepend();
			HLDetails.setDisabled(false);
		}		
		
		actividad.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub
				if (periodoActividad.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (actividad.getListGrid().anySelected()) {
           			arrowRightImg.setDisabled(false);
           		} else {
           			arrowRightImg.setDisabled(true);
           		}
			}			
		});
		
		lista.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub	
				if(!lista.getListGrid().anySelected()) {
					Criteria criteria=new Criteria();
					criteria.addCriteria("periodoPrograma", 0);
					periodoActividad.getListGrid().setCriteria(criteria);
					periodoActividad.refreshListGridDepend();	
					criteria=new Criteria();
					criteria.addCriteria("actividad", 0);
					actividad.getListGrid().setCriteria(criteria);
					actividad.refreshListGridDepend();
					HLDetails.setDisabled(true);					
				} else {					
					Criteria criteria = new Criteria();
					criteria.addCriteria("periodoPrograma", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodoPrograma"));
					periodoActividad.getListGrid().setCriteria(criteria);
					periodoActividad.refreshListGrid();
					criteria=new Criteria();
					criteria.addCriteria("idPeriodoPrograma", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodoPrograma"));
					actividad.getListGrid().setCriteria(criteria);
					actividad.refreshListGridDepend();
					HLDetails.setDisabled(false);
				}
			}
        });
		
	}
}