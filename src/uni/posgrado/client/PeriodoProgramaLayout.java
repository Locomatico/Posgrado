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
import uni.posgrado.gwt.PeriodoGwtRPCDS;
import uni.posgrado.gwt.PeriodoProgramaGwtRPCDS;
import uni.posgrado.gwt.ProgramaGwtRPCDS;


public class PeriodoProgramaLayout extends VLayout {

	public PeriodoProgramaLayout() {     
		setOverflow(Overflow.AUTO);
		final BasicVLayout lista = new BasicVLayout(null, "Periodos", "PeriodoServlet", 800, "normal", null);
		PeriodoGwtRPCDS periodo = new PeriodoGwtRPCDS(false);
		lista.setDataSource(periodo);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.setFormSimpleDate("fechaInicio");
		lista.setFormSimpleDate("fechaFin");
		lista.getListGrid().getField("fechaInicio").setWidth(75);
		lista.getListGrid().getField("fechaFin").setWidth(75);
		lista.hideModal();
		lista.hideExportButton();
		lista.getListGrid().getField("idPeriodo");		
		addMember(lista);
		
		final HLayout HLDetails = new HLayout();
		HLDetails.setLayoutAlign(Alignment.CENTER);
		HLDetails.setAlign(Alignment.CENTER);
		HLDetails.setOverflow(Overflow.VISIBLE);
		HLDetails.setMargin(10);
		HLDetails.setMembersMargin(15);
		HLDetails.setWidth(1000);
		
		final BasicVLayout programas = new BasicVLayout(null, "Programas sin asignar", "", 600, "normal", null);
		programas.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		programas.setDataSource(new ProgramaGwtRPCDS(true));
		programas.getListGrid().setHeight(300);
		programas.getListGrid().setCanEdit(false);
		programas.getListGrid().setCanPickFields(false);
		programas.getListGrid().setCanGroupBy(false);
		programas.hideAllButtons();
		programas.hideSummary();
		programas.setSize(450, false);
		programas.getListGrid().setCanDragRecordsOut(true);
		programas.getListGrid().setDragDataAction(DragDataAction.NONE);
		programas.getListGrid().setAddDropValues(false);
		programas.getListGrid().setCanAcceptDroppedRecords(true);	
		HLDetails.addMember(programas);
		
		final BasicVLayout periodoPrograma = new BasicVLayout(null, "Programas Asignados", "", 600, "normal", null);	
		periodoPrograma.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		periodoPrograma.setDataSource(new PeriodoProgramaGwtRPCDS());
		periodoPrograma.getListGrid().setHeight(300);
		periodoPrograma.setFormTextType("periodo");
		periodoPrograma.setFormTextType("programa");
		//periodoPrograma.setFormTextType("programaNombre");
		periodoPrograma.getListGrid().hideField("periodo");
		periodoPrograma.getListGrid().hideField("programaNombre");
		periodoPrograma.getListGrid().getField("programa").setCanEdit(false);
		periodoPrograma.getListGrid().getField("periodoCodigo").setCanEdit(false);
		periodoPrograma.getListGrid().setCanEdit(true);
		periodoPrograma.getListGrid().setAutoSaveEdits(true);
		periodoPrograma.getListGrid().setCanPickFields(false);
		periodoPrograma.getListGrid().setCanGroupBy(false);
		periodoPrograma.hideAllButtons();
		periodoPrograma.hideSummary();
		periodoPrograma.setSize(550, false);
		periodoPrograma.getListGrid().setCanDragRecordsOut(true);
		periodoPrograma.getListGrid().setDragDataAction(DragDataAction.NONE);
		periodoPrograma.getListGrid().setCanAcceptDroppedRecords(true);
		periodoPrograma.getListGrid().setAddDropValues(false);
		
		periodoPrograma.setFormSimpleDate("fechaInicio");
		periodoPrograma.setFormSimpleDate("fechaFin");
		periodoPrograma.getListGrid().getField("fechaInicio").setWidth(75);
		periodoPrograma.getListGrid().getField("fechaFin").setWidth(75);
		
		
		CustomValidator cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {
        			if(value != null && !value.equals("")) {
	        			int id = periodoPrograma.getListGrid().getEditRow();
	        			if(periodoPrograma.getListGrid().getEditedRecord(id).getAttribute("fechaInicio") != null && !periodoPrograma.getListGrid().getEditedRecord(id).getAttribute("fechaInicio").isEmpty()) {
	        				Date date_ini = (Date) periodoPrograma.getListGrid().getEditedRecord(id).getAttributeAsDate("fechaInicio");
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
        periodoPrograma.getListGrid().getField("fechaFin").setValidators(cv);
		
        
        cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {
        			if(value != null && !value.equals("")) {
        				int id = periodoPrograma.getListGrid().getEditRow();
            			if(periodoPrograma.getListGrid().getEditedRecord(id).getAttribute("fechaFin") != null && !periodoPrograma.getListGrid().getEditedRecord(id).getAttribute("fechaFin").isEmpty()) {
            				Date date_ini = (Date) periodoPrograma.getListGrid().getEditedRecord(id).getAttributeAsDate("fechaFin");
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
        periodoPrograma.getListGrid().getField("fechaInicio").setValidators(cv);
        
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
        periodoPrograma.getListGrid().getField("fechaInicio").setValidators(cv);
        
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
        periodoPrograma.getListGrid().getField("fechaFin").setValidators(cv);
        
        /**/
		
		periodoPrograma.getListGrid().addCellSavedHandler(new CellSavedHandler() {
			
			@Override
			public void onCellSaved(CellSavedEvent event) {
				// TODO Auto-generated method stub
				periodoPrograma.refreshListGrid();
			}
		});
		
		final TransferImgButton arrowLeftImg = new TransferImgButton(TransferImgButton.LEFT);
		final TransferImgButton arrowRightImg = new TransferImgButton(TransferImgButton.RIGHT);		
		
		final VLayout buttonsLayout = new VLayout();
		buttonsLayout.setLayoutAlign(Alignment.CENTER);
		buttonsLayout.setAlign(Alignment.CENTER);
		
		programas.getListGrid().addRecordDropHandler(new RecordDropHandler() {			
			@Override
			public void onRecordDrop(RecordDropEvent event) {
				// TODO Auto-generated method stub
				event.cancel();
				if(periodoPrograma.getListGrid().anySelected()) {
        	    	SC.confirm("Eliminar registros", "¿Está seguro que desea eliminar el/los registro(s) seleccionado(s)?", new BooleanCallback() {
        				@Override
        				public void execute(Boolean value) {
        					// TODO Auto-generated method stub						
        					if (value != null && value) {
        						DSRequest requestProperties = new DSRequest();
        						periodoPrograma.getListGrid().removeSelectedData(new DSCallback() {								
        							@Override
        							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
        								//delButton.disable();
        								programas.refreshListGridDepend();
        								periodoPrograma.refreshListGridDepend();
        				           		if (periodoPrograma.getListGrid().anySelected()) {
        				           			arrowLeftImg.setDisabled(false);
        				           		} else {
        				           			arrowLeftImg.setDisabled(true);
        				           		}
        				           		if (programas.getListGrid().anySelected()) {
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
		
		periodoPrograma.getListGrid().addRecordDropHandler(new RecordDropHandler() {			
			@Override
			public void onRecordDrop(RecordDropEvent event) {
				// TODO Auto-generated method stub
				event.cancel();
				if(programas.getListGrid().anySelected() && lista.getListGrid().anySelected()) {
					Record record = new Record();
	            	record.setAttribute("programa", programas.getListGrid().getSelectedRecord().getAttributeAsInt("idPrograma"));
	            	record.setAttribute("periodo", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
	            	record.setAttribute("nombre", lista.getListGrid().getSelectedRecord().getAttributeAsString("nombre"));
	            	record.setAttribute("fechaInicio", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaInicio"));
	            	record.setAttribute("fechaFin", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaFin"));
	            	record.setAttribute("estado", true);	            	
	            	periodoPrograma.getListGrid().startEditingNew(record);
	            	periodoPrograma.getListGrid().addData(record, new DSCallback() {
	            		@Override
	            		public void execute(DSResponse dsResponse,
	            				Object data, DSRequest dsRequest) {
	            			// TODO Auto-generated method stub
	            			periodoPrograma.refreshListGrid();
	    					programas.refreshListGridDepend();
	    					if (periodoPrograma.getListGrid().anySelected()) {
			           			arrowLeftImg.setDisabled(false);
			           		} else {
			           			arrowLeftImg.setDisabled(true);
			           		}
			           		if (programas.getListGrid().anySelected()) {
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
           		if(periodoPrograma.getListGrid().anySelected()) {
        	    	SC.confirm("Eliminar registros", "¿Está seguro que desea eliminar el/los registro(s) seleccionado(s)?", new BooleanCallback() {
        				@Override
        				public void execute(Boolean value) {
        					// TODO Auto-generated method stub						
        					if (value != null && value) {
        						DSRequest requestProperties = new DSRequest();
        						periodoPrograma.getListGrid().removeSelectedData(new DSCallback() {								
        							@Override
        							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
        								//delButton.disable();
        								programas.refreshListGridDepend();
        								periodoPrograma.refreshListGridDepend();
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
            	if(programas.getListGrid().anySelected() && lista.getListGrid().anySelected()) {
	            	Record record = new Record();
	            	record.setAttribute("programa", programas.getListGrid().getSelectedRecord().getAttributeAsInt("idPrograma"));
	            	record.setAttribute("periodo", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
	            	record.setAttribute("nombre", lista.getListGrid().getSelectedRecord().getAttributeAsString("nombre"));
	            	record.setAttribute("fechaInicio", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaInicio"));
	            	record.setAttribute("fechaFin", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaFin"));
	            	record.setAttribute("estado", true);	
	            	periodoPrograma.getListGrid().addData(record, new DSCallback() {
	            		@Override
	            		public void execute(DSResponse dsResponse,
	            				Object data, DSRequest dsRequest) {
	            			// TODO Auto-generated method stub
	            			periodoPrograma.refreshListGrid();
	            			programas.refreshListGridDepend();
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
		HLDetails.addMember(periodoPrograma);
		
		addMember(HLDetails);		
		
		
		//lista.getListGrid().setCanEdit(false);
		//lista.getListGrid().setCanPickFields(false);
		lista.getListGrid().setCanGroupBy(false);
		//lista.hideActionButtons();
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {  
			public void onRecordClick(RecordClickEvent event) {
				Criteria criteria = new Criteria();
				criteria.addCriteria("idPeriodo", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
				//System.out.println("var:"+lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
				periodoPrograma.getListGrid().setCriteria(criteria);
				periodoPrograma.refreshListGrid();
				criteria=new Criteria();
				criteria.addCriteria("idPeriodo", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
				programas.getListGrid().setCriteria(criteria);
				programas.refreshListGridDepend();
				HLDetails.setDisabled(false);
			}
		});
		
		periodoPrograma.getListGrid().addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				if (periodoPrograma.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (programas.getListGrid().anySelected()) {
           			arrowRightImg.setDisabled(false);
           		} else {
           			arrowRightImg.setDisabled(true);
           		}
			}
		});
		
		if (periodoPrograma.getListGrid().anySelected()) {
   			arrowLeftImg.setDisabled(false);
   		} else {
   			arrowLeftImg.setDisabled(true);
   		}
   		if (programas.getListGrid().anySelected()) {
   			arrowRightImg.setDisabled(false);
   		} else {
   			arrowRightImg.setDisabled(true);
   		}
		
		programas.getListGrid().addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				if (periodoPrograma.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (programas.getListGrid().anySelected()) {
           			arrowRightImg.setDisabled(false);
           		} else {
           			arrowRightImg.setDisabled(true);
           		}
			}
		});

		if(!lista.getListGrid().anySelected()) {
			Criteria criteria=new Criteria();
			criteria.addCriteria("idPeriodo", 0);
			periodoPrograma.getListGrid().setCriteria(criteria);
			periodoPrograma.refreshListGridDepend();	
			criteria=new Criteria();
			criteria.addCriteria("programa", 0);
			programas.getListGrid().setCriteria(criteria);
			programas.refreshListGridDepend();
			HLDetails.setDisabled(true);
		} else {
			Criteria criteria = new Criteria();
			criteria.addCriteria("idPeriodo", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
			periodoPrograma.getListGrid().setCriteria(criteria);
			periodoPrograma.refreshListGrid();
			criteria=new Criteria();
			criteria.addCriteria("idPeriodo", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
			programas.getListGrid().setCriteria(criteria);
			programas.refreshListGridDepend();
			HLDetails.setDisabled(false);
		}		
		
		programas.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub
				if (periodoPrograma.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (programas.getListGrid().anySelected()) {
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
					criteria.addCriteria("idPeriodo", 0);
					periodoPrograma.getListGrid().setCriteria(criteria);
					periodoPrograma.refreshListGridDepend();	
					criteria=new Criteria();
					criteria.addCriteria("programa", 0);
					programas.getListGrid().setCriteria(criteria);
					programas.refreshListGridDepend();
					HLDetails.setDisabled(true);					
				} else {					
					Criteria criteria = new Criteria();
					criteria.addCriteria("idPeriodo", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
					periodoPrograma.getListGrid().setCriteria(criteria);
					periodoPrograma.refreshListGrid();
					criteria=new Criteria();
					criteria.addCriteria("idPeriodo", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
					programas.getListGrid().setCriteria(criteria);
					programas.refreshListGridDepend();
					HLDetails.setDisabled(false);
				}
			}
        });
		
		
		
		
	}
}