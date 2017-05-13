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
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.SeccionDocenteGwtRPCDS;
import uni.posgrado.gwt.SeccionGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;
import uni.posgrado.gwt.VinculoDocenteGwtRPCDS;


public class SeccionDocenteLayout extends VLayout {

	public SeccionDocenteLayout() {       
		setOverflow(Overflow.AUTO);
		final BasicVLayout lista = new BasicVLayout(null, "Grupo", "", 800, "normal", null);
		SeccionGwtRPCDS seccion = new SeccionGwtRPCDS();
		lista.setDataSource(seccion);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);	
		addMember(lista);
		
		final HLayout HLDetails = new HLayout();
		HLDetails.setLayoutAlign(Alignment.CENTER);
		HLDetails.setAlign(Alignment.CENTER);
		HLDetails.setOverflow(Overflow.VISIBLE);
		HLDetails.setMargin(10);
		HLDetails.setMembersMargin(15);
		HLDetails.setWidth(1000);
		final BasicVLayout vinculoDocente = new BasicVLayout(null, "Docentes", "", 800, "normal", null);
		vinculoDocente.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		vinculoDocente.setDataSource(new VinculoDocenteGwtRPCDS());
		vinculoDocente.getListGrid().setHeight(300);
		vinculoDocente.getListGrid().setCanEdit(false);
		vinculoDocente.getListGrid().setCanPickFields(false);
		vinculoDocente.getListGrid().setCanGroupBy(false);
		vinculoDocente.hideAllButtons();
		vinculoDocente.hideSummary();
		vinculoDocente.setSize(450, false);
		vinculoDocente.getListGrid().setCanDragRecordsOut(true);
		vinculoDocente.getListGrid().setDragDataAction(DragDataAction.NONE);
		vinculoDocente.getListGrid().setAddDropValues(false);
		vinculoDocente.getListGrid().setCanAcceptDroppedRecords(true);	
		HLDetails.addMember(vinculoDocente);
		
		final BasicVLayout seccionDocente = new BasicVLayout(null, "Docente asignados", "", 800, "normal", null);	
		seccionDocente.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		seccionDocente.setDataSource(new SeccionDocenteGwtRPCDS());
		seccionDocente.getListGrid().setHeight(300);
		seccionDocente.getListGrid().getField("rolDoc").setFilterEditorProperties(new ListGridSelect("Rol Docente", 200, 200, "codTabla", new VariableFormGwtRPCDS("ROLDOC"), "nomTabla", "codTabla", false));
		//seccionDocente.getListGrid().getField("vinculoDocente").setCanEdit(false);
		seccionDocente.getListGrid().setCanEdit(true);
		seccionDocente.getListGrid().setAutoSaveEdits(true);
		seccionDocente.getListGrid().setCanPickFields(false);
		seccionDocente.getListGrid().setCanGroupBy(false);
		seccionDocente.hideAllButtons();
		seccionDocente.hideSummary();
		seccionDocente.setSize(550, false);
		seccionDocente.getListGrid().setCanDragRecordsOut(true);
		seccionDocente.getListGrid().setDragDataAction(DragDataAction.NONE);
		seccionDocente.getListGrid().setCanAcceptDroppedRecords(true);
		seccionDocente.getListGrid().setAddDropValues(false);
		
		seccionDocente.setFormSimpleDate("fechaInicio");
		seccionDocente.setFormSimpleDate("fechaFin");
		seccionDocente.getListGrid().getField("fechaInicio").setWidth(65);
		seccionDocente.getListGrid().getField("fechaFin").setWidth(65);
		seccionDocente.getListGrid().getField("rolDoc").setWidth(120);
		
		seccionDocente.getListGrid().addCellSavedHandler(new CellSavedHandler() {
					
					@Override
					public void onCellSaved(CellSavedEvent event) {
						// TODO Auto-generated method stub
						seccionDocente.refreshListGrid();
					}
				});
		
		CustomValidator cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {
        			if(value != null && !value.equals("")) {
	        			int id = seccionDocente.getListGrid().getEditRow();
	        			if(seccionDocente.getListGrid().getEditedRecord(id).getAttribute("fechaInicio") != null && !seccionDocente.getListGrid().getEditedRecord(id).getAttribute("fechaInicio").isEmpty()) {
	        				Date date_ini = (Date) seccionDocente.getListGrid().getEditedRecord(id).getAttributeAsDate("fechaInicio");
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
        seccionDocente.getListGrid().getField("fechaFin").setValidators(cv);
		
        
        cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {
        			if(value != null && !value.equals("")) {
        				int id = seccionDocente.getListGrid().getEditRow();
            			if(seccionDocente.getListGrid().getEditedRecord(id).getAttribute("fechaFin") != null && !seccionDocente.getListGrid().getEditedRecord(id).getAttribute("fechaFin").isEmpty()) {
            				Date date_ini = (Date) seccionDocente.getListGrid().getEditedRecord(id).getAttributeAsDate("fechaFin");
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
        seccionDocente.getListGrid().getField("fechaInicio").setValidators(cv);
		
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
        seccionDocente.getListGrid().getField("fechaInicio").setValidators(cv);
        
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
        seccionDocente.getListGrid().getField("fechaFin").setValidators(cv);
        
        /**/
        
		
		final TransferImgButton arrowLeftImg = new TransferImgButton(TransferImgButton.LEFT);
		final TransferImgButton arrowRightImg = new TransferImgButton(TransferImgButton.RIGHT);
		
		final VLayout buttonsLayout = new VLayout();
		buttonsLayout.setLayoutAlign(Alignment.CENTER);
		buttonsLayout.setAlign(Alignment.CENTER);
		
		vinculoDocente.getListGrid().addRecordDropHandler(new RecordDropHandler() {			
			@Override
			public void onRecordDrop(RecordDropEvent event) {
				// TODO Auto-generated method stub
				event.cancel();
				if(seccionDocente.getListGrid().anySelected()) {
        	    	SC.confirm("Eliminar registros", "Esta seguro que desea eliminar el/los registro(s) seleccionado(s)?", new BooleanCallback() {
        				@Override
        				public void execute(Boolean value) {
        					// TODO Auto-generated method stub						
        					if (value != null && value) {
        						DSRequest requestProperties = new DSRequest();
        						seccionDocente.getListGrid().removeSelectedData(new DSCallback() {								
        							@Override
        							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
        								//delButton.disable();
        								vinculoDocente.refreshListGridDepend();
        								seccionDocente.refreshListGridDepend();
        				           		if (seccionDocente.getListGrid().anySelected()) {
        				           			arrowLeftImg.setDisabled(false);
        				           		} else {
        				           			arrowLeftImg.setDisabled(true);
        				           		}
        				           		if (vinculoDocente.getListGrid().anySelected()) {
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
		
		seccionDocente.getListGrid().addRecordDropHandler(new RecordDropHandler() {			
			@Override
			public void onRecordDrop(RecordDropEvent event) {
				// TODO Auto-generated method stub
				event.cancel();
				if(vinculoDocente.getListGrid().anySelected() && lista.getListGrid().anySelected()) {
					Record record = new Record();
	            	record.setAttribute("vinculoDocente", vinculoDocente.getListGrid().getSelectedRecord().getAttributeAsInt("idVinculoDocente"));
	            	record.setAttribute("idSeccion", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
	            	record.setAttribute("fechaInicio", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaInicio"));
	            	record.setAttribute("fechaFin", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaFin"));
	            	record.setAttribute("estado", true);
	            	seccionDocente.getListGrid().startEditingNew(record);	            	
	            	seccionDocente.getListGrid().addData(record, new DSCallback() {
	            		@Override
	            		public void execute(DSResponse dsResponse,
	            				Object data, DSRequest dsRequest) {
	            			// TODO Auto-generated method stub
	            			seccionDocente.refreshListGrid();
	            			vinculoDocente.refreshListGridDepend();
	    					if (seccionDocente.getListGrid().anySelected()) {
			           			arrowLeftImg.setDisabled(false);
			           		} else {
			           			arrowLeftImg.setDisabled(true);
			           		}
			           		if (vinculoDocente.getListGrid().anySelected()) {
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
           		if(seccionDocente.getListGrid().anySelected()) {
        	    	SC.confirm("Eliminar registros", "Esta seguro que desea eliminar el/los registro(s) seleccionado(s)?", new BooleanCallback() {
        				@Override
        				public void execute(Boolean value) {
        					// TODO Auto-generated method stub						
        					if (value != null && value) {
        						DSRequest requestProperties = new DSRequest();
        						seccionDocente.getListGrid().removeSelectedData(new DSCallback() {								
        							@Override
        							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
        								//delButton.disable();
        								vinculoDocente.refreshListGridDepend();
        								seccionDocente.refreshListGridDepend();
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
            	if(vinculoDocente.getListGrid().anySelected() && lista.getListGrid().anySelected()) {
	            	Record record = new Record();
	            	record.setAttribute("vinculoDocente", vinculoDocente.getListGrid().getSelectedRecord().getAttributeAsInt("idVinculoDocente"));
	            	record.setAttribute("idSeccion", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
	            	record.setAttribute("fechaInicio", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaInicio"));
	            	record.setAttribute("fechaFin", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaFin"));
	            	record.setAttribute("estado", true);
	            	seccionDocente.getListGrid().addData(record, new DSCallback() {
	            		@Override
	            		public void execute(DSResponse dsResponse,
	            				Object data, DSRequest dsRequest) {
	            			// TODO Auto-generated method stub
	            			seccionDocente.refreshListGrid();
	            			vinculoDocente.refreshListGridDepend();
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
		HLDetails.addMember(seccionDocente);
		
		lista.getListGrid().setCanEdit(false);
		lista.getListGrid().setCanPickFields(false);
		lista.getListGrid().setCanGroupBy(false);
		lista.hideActionButtons();
		
		addMember(HLDetails);
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {  
			public void onRecordClick(RecordClickEvent event) {
				Criteria criteria = new Criteria();				
				criteria.addCriteria("idSeccion", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
				seccionDocente.getListGrid().setCriteria(criteria);
				seccionDocente.refreshListGrid();
				criteria=new Criteria();
				criteria.addCriteria("idSeccion", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
				vinculoDocente.getListGrid().setCriteria(criteria);
				vinculoDocente.refreshListGridDepend();
				HLDetails.setDisabled(false);
			}
		});
		
		seccionDocente.getListGrid().addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				if (seccionDocente.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (vinculoDocente.getListGrid().anySelected()) {
           			arrowRightImg.setDisabled(false);
           		} else {
           			arrowRightImg.setDisabled(true);
           		}
			}
		});
		
		if (seccionDocente.getListGrid().anySelected()) {
   			arrowLeftImg.setDisabled(false);
   		} else {
   			arrowLeftImg.setDisabled(true);
   		}
   		if (vinculoDocente.getListGrid().anySelected()) {
   			arrowRightImg.setDisabled(false);
   		} else {
   			arrowRightImg.setDisabled(true);
   		}
   		
   		vinculoDocente.getListGrid().addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				if (seccionDocente.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (vinculoDocente.getListGrid().anySelected()) {
           			arrowRightImg.setDisabled(false);
           		} else {
           			arrowRightImg.setDisabled(true);
           		}
			}
		});
   		
   		if(!lista.getListGrid().anySelected()) {
			Criteria criteria=new Criteria();
			criteria.addCriteria("idSeccion", 0);
			seccionDocente.getListGrid().setCriteria(criteria);
			seccionDocente.refreshListGridDepend();	
			criteria=new Criteria();			
			criteria.addCriteria("vinculoDocente", 0);
			vinculoDocente.getListGrid().setCriteria(criteria);
			vinculoDocente.refreshListGridDepend();
			HLDetails.setDisabled(true);
		} else {
			Criteria criteria = new Criteria();
			criteria.addCriteria("idSeccion", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
			seccionDocente.getListGrid().setCriteria(criteria);
			seccionDocente.refreshListGrid();
			criteria=new Criteria();
			criteria.addCriteria("idSeccion", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
			vinculoDocente.getListGrid().setCriteria(criteria);
			vinculoDocente.refreshListGridDepend();
			HLDetails.setDisabled(false);
		}
   		
   		vinculoDocente.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub
				if (seccionDocente.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (vinculoDocente.getListGrid().anySelected()) {
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
					criteria.addCriteria("idSeccion", 0);
					seccionDocente.getListGrid().setCriteria(criteria);
					seccionDocente.refreshListGridDepend();	
					criteria=new Criteria();
					criteria.addCriteria("vinculoDocente", 0);
					vinculoDocente.getListGrid().setCriteria(criteria);
					vinculoDocente.refreshListGridDepend();
					HLDetails.setDisabled(true);	
				} else {					
					Criteria criteria = new Criteria();
					criteria.addCriteria("idSeccion", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
					seccionDocente.getListGrid().setCriteria(criteria);
					seccionDocente.refreshListGrid();
					criteria=new Criteria();
					criteria.addCriteria("idSeccion", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
					vinculoDocente.getListGrid().setCriteria(criteria);
					vinculoDocente.refreshListGridDepend();
					HLDetails.setDisabled(false);
				}
			}
        });
	}
}