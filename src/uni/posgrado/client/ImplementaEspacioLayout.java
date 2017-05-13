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
import uni.posgrado.gwt.EspacioGwtRPCDS;
import uni.posgrado.gwt.ImplementacionGwtRPCDS;
import uni.posgrado.gwt.RecursoGwtRPCDS;

public class ImplementaEspacioLayout extends VLayout {

	public ImplementaEspacioLayout() {
		setOverflow(Overflow.AUTO);
		final BasicVLayout lista = new BasicVLayout(null, "Espacios", "EspacioServlet", 800, "normal", null);
		EspacioGwtRPCDS espacio = new EspacioGwtRPCDS(false);
		lista.setDataSource(espacio);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		/*
		lista.setFormSimpleDate("fechaInicio");
		lista.setFormSimpleDate("fechaFin");
		lista.getListGrid().getField("fechaInicio").setWidth(75);
		lista.getListGrid().getField("fechaFin").setWidth(75);
		*/
		lista.hideModal();
		lista.hideExportButton();
		lista.getListGrid().getField("idEspacio");		
		addMember(lista);
		final HLayout HLDetails = new HLayout();
		HLDetails.setLayoutAlign(Alignment.CENTER);
		HLDetails.setAlign(Alignment.CENTER);
		HLDetails.setOverflow(Overflow.VISIBLE);
		HLDetails.setMargin(10);
		HLDetails.setMembersMargin(15);
		HLDetails.setWidth(1000);
		final BasicVLayout recursos = new BasicVLayout(null, "Recursos", "", 600, "normal", null);
		recursos.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		recursos.setDataSource(new RecursoGwtRPCDS());
		recursos.getListGrid().setHeight(300);
		recursos.getListGrid().setCanEdit(false);
		recursos.getListGrid().setCanPickFields(false);
		recursos.getListGrid().setCanGroupBy(false);
		recursos.hideAllButtons();
		recursos.hideSummary();
		recursos.setSize(450, false);
		recursos.getListGrid().setCanDragRecordsOut(true);
		recursos.getListGrid().setDragDataAction(DragDataAction.NONE);
		recursos.getListGrid().setAddDropValues(false);
		recursos.getListGrid().setCanAcceptDroppedRecords(true);	
		HLDetails.addMember(recursos);
		
		final BasicVLayout implementacion = new BasicVLayout(null, "Recursos del espacio", "", 600, "normal", null);	
		implementacion.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		implementacion.setDataSource(new ImplementacionGwtRPCDS(false));
		implementacion.getListGrid().setHeight(300);
		/*
		implementacion.setFormTextType("espacio");
		implementacion.setFormTextType("recurso");
		implementacion.getListGrid().hideField("espacio");
		implementacion.getListGrid().hideField("recursoNombre");
		implementacion.getListGrid().getField("recurso").setCanEdit(false);
		implementacion.getListGrid().getField("espacioCodigo").setCanEdit(false);
		*/
		implementacion.getListGrid().setCanEdit(true);
		implementacion.getListGrid().setAutoSaveEdits(true);
		implementacion.getListGrid().setCanPickFields(false);
		implementacion.getListGrid().setCanGroupBy(false);
		implementacion.hideAllButtons();
		implementacion.hideSummary();
		implementacion.setSize(550, false);
		implementacion.getListGrid().setCanDragRecordsOut(true);
		implementacion.getListGrid().setDragDataAction(DragDataAction.NONE);
		implementacion.getListGrid().setCanAcceptDroppedRecords(true);
		implementacion.getListGrid().setAddDropValues(false);
		/*
		implementacion.setFormSimpleDate("fechaInicio");
		implementacion.setFormSimpleDate("fechaFin");
		implementacion.getListGrid().getField("fechaInicio").setWidth(75);
		implementacion.getListGrid().getField("fechaFin").setWidth(75);
		*/
		//
		CustomValidator cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {
        			if(value != null && !value.equals("")) {
	        			int id = implementacion.getListGrid().getEditRow();
	        			if(implementacion.getListGrid().getEditedRecord(id).getAttribute("fechaInicio") != null && !implementacion.getListGrid().getEditedRecord(id).getAttribute("fechaInicio").isEmpty()) {
	        				Date date_ini = (Date) implementacion.getListGrid().getEditedRecord(id).getAttributeAsDate("fechaInicio");
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
        //implementacion.getListGrid().getField("fechaFin").setValidators(cv);
		
        
        cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {
        			if(value != null && !value.equals("")) {
        				int id = implementacion.getListGrid().getEditRow();
            			if(implementacion.getListGrid().getEditedRecord(id).getAttribute("fechaFin") != null && !implementacion.getListGrid().getEditedRecord(id).getAttribute("fechaFin").isEmpty()) {
            				Date date_ini = (Date) implementacion.getListGrid().getEditedRecord(id).getAttributeAsDate("fechaFin");
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
        //implementacion.getListGrid().getField("fechaInicio").setValidators(cv);

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
        //implementacion.getListGrid().getField("fechaInicio").setValidators(cv);
        
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
        //implementacion.getListGrid().getField("fechaFin").setValidators(cv);
        //
        implementacion.getListGrid().addCellSavedHandler(new CellSavedHandler() {
			@Override
			public void onCellSaved(CellSavedEvent event) {
				// TODO Auto-generated method stub
				implementacion.refreshListGrid();
			}
		});
		
		final TransferImgButton arrowLeftImg = new TransferImgButton(TransferImgButton.LEFT);
		final TransferImgButton arrowRightImg = new TransferImgButton(TransferImgButton.RIGHT);		
		final VLayout buttonsLayout = new VLayout();
		buttonsLayout.setLayoutAlign(Alignment.CENTER);
		buttonsLayout.setAlign(Alignment.CENTER);
		
		recursos.getListGrid().addRecordDropHandler(new RecordDropHandler() {			
			@Override
			public void onRecordDrop(RecordDropEvent event) {
				// TODO Auto-generated method stub
				event.cancel();
				if(implementacion.getListGrid().anySelected()) {
        	    	SC.confirm("Eliminar registros", "¿Está seguro que desea eliminar el/los registro(s) seleccionado(s)?", new BooleanCallback() {
        				@Override
        				public void execute(Boolean value) {
        					// TODO Auto-generated method stub						
        					if (value != null && value) {
        						DSRequest requestProperties = new DSRequest();
        						implementacion.getListGrid().removeSelectedData(new DSCallback() {								
        							@Override
        							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
        								//delButton.disable();
        								recursos.refreshListGridDepend();
        								implementacion.refreshListGridDepend();
        				           		if (implementacion.getListGrid().anySelected()) {
        				           			arrowLeftImg.setDisabled(false);
        				           		} else {
        				           			arrowLeftImg.setDisabled(true);
        				           		}
        				           		if (recursos.getListGrid().anySelected()) {
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
		
		implementacion.getListGrid().addRecordDropHandler(new RecordDropHandler() {			
			@Override
			public void onRecordDrop(RecordDropEvent event) {
				// TODO Auto-generated method stub
				event.cancel();
				if(recursos.getListGrid().anySelected() && lista.getListGrid().anySelected()) {
					Record record = new Record();
	            	record.setAttribute("recurso", recursos.getListGrid().getSelectedRecord().getAttributeAsInt("idRecurso"));
	            	record.setAttribute("espacio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idEspacio"));
	            	record.setAttribute("nombre", lista.getListGrid().getSelectedRecord().getAttributeAsString("nombre"));
	            	/*
	            	record.setAttribute("fechaInicio", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaInicio"));
	            	record.setAttribute("fechaFin", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaFin"));
	            	*/
	            	record.setAttribute("estado", true);	            	
	            	implementacion.getListGrid().startEditingNew(record);
	            	implementacion.getListGrid().addData(record, new DSCallback() {
	            		@Override
	            		public void execute(DSResponse dsResponse,
	            				Object data, DSRequest dsRequest) {
	            			// TODO Auto-generated method stub
	            			implementacion.refreshListGrid();
	    					recursos.refreshListGridDepend();
	    					if (implementacion.getListGrid().anySelected()) {
			           			arrowLeftImg.setDisabled(false);
			           		} else {
			           			arrowLeftImg.setDisabled(true);
			           		}
			           		if (recursos.getListGrid().anySelected()) {
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
           		if(implementacion.getListGrid().anySelected()) {
        	    	SC.confirm("Eliminar registros", "¿Está seguro que desea eliminar el/los registro(s) seleccionado(s)?", new BooleanCallback() {
        				@Override
        				public void execute(Boolean value) {
        					// TODO Auto-generated method stub						
        					if (value != null && value) {
        						DSRequest requestProperties = new DSRequest();
        						implementacion.getListGrid().removeSelectedData(new DSCallback() {								
        							@Override
        							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
        								//delButton.disable();
        								recursos.refreshListGridDepend();
        								implementacion.refreshListGridDepend();
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
            	if(recursos.getListGrid().anySelected() && lista.getListGrid().anySelected()) {
	            	Record record = new Record();
	            	record.setAttribute("recurso", recursos.getListGrid().getSelectedRecord().getAttributeAsInt("idRecurso"));
	            	record.setAttribute("espacio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idEspacio"));
	            	//record.setAttribute("nombre", lista.getListGrid().getSelectedRecord().getAttributeAsString("nombre"));
	            	//record.setAttribute("fechaInicio", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaInicio"));
	            	//record.setAttribute("fechaFin", lista.getListGrid().getSelectedRecord().getAttributeAsDate("fechaFin"));
	            	//record.setAttribute("estado", true);
	            	System.out.print(record.getAttribute("recurso").toString());
	            	System.out.print(record.getAttribute("espacio").toString());
	            	implementacion.getListGrid().addData(record, new DSCallback() {
	            		@Override
	            		public void execute(DSResponse dsResponse,
	            				Object data, DSRequest dsRequest) {
	            			// TODO Auto-generated method stub
	            			implementacion.refreshListGrid();
	            			recursos.refreshListGridDepend();
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
		HLDetails.addMember(implementacion);
		
		addMember(HLDetails);
		//lista.getListGrid().setCanEdit(false);
		//lista.getListGrid().setCanPickFields(false);
		lista.getListGrid().setCanGroupBy(false);
		//lista.hideActionButtons();
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {  
			public void onRecordClick(RecordClickEvent event) {
				Criteria criteria = new Criteria();
				criteria.addCriteria("idEspacio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idEspacio"));
				//System.out.println("var:"+lista.getListGrid().getSelectedRecord().getAttributeAsInt("idEspacio"));
				implementacion.getListGrid().setCriteria(criteria);
				implementacion.refreshListGrid();
				criteria=new Criteria();
				criteria.addCriteria("idEspacio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idEspacio"));
				recursos.getListGrid().setCriteria(criteria);
				recursos.refreshListGridDepend();
				HLDetails.setDisabled(false);
			}
		});
		
		implementacion.getListGrid().addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				if (implementacion.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (recursos.getListGrid().anySelected()) {
           			arrowRightImg.setDisabled(false);
           		} else {
           			arrowRightImg.setDisabled(true);
           		}
			}
		});
		
		if (implementacion.getListGrid().anySelected()) {
   			arrowLeftImg.setDisabled(false);
   		} else {
   			arrowLeftImg.setDisabled(true);
   		}
   		if (recursos.getListGrid().anySelected()) {
   			arrowRightImg.setDisabled(false);
   		} else {
   			arrowRightImg.setDisabled(true);
   		}
		
		recursos.getListGrid().addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				if (implementacion.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (recursos.getListGrid().anySelected()) {
           			arrowRightImg.setDisabled(false);
           		} else {
           			arrowRightImg.setDisabled(true);
           		}
			}
		});

		if(!lista.getListGrid().anySelected()) {
			Criteria criteria=new Criteria();
			criteria.addCriteria("idEspacio", 0);
			implementacion.getListGrid().setCriteria(criteria);
			implementacion.refreshListGridDepend();	
			criteria=new Criteria();
			criteria.addCriteria("recurso", 0);
			recursos.getListGrid().setCriteria(criteria);
			recursos.refreshListGridDepend();
			HLDetails.setDisabled(true);
		} else {
			Criteria criteria = new Criteria();
			criteria.addCriteria("idEspacio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idEspacio"));
			implementacion.getListGrid().setCriteria(criteria);
			implementacion.refreshListGrid();
			criteria=new Criteria();
			criteria.addCriteria("idEspacio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idEspacio"));
			recursos.getListGrid().setCriteria(criteria);
			recursos.refreshListGridDepend();
			HLDetails.setDisabled(false);
		}		
		
		recursos.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub
				if (implementacion.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (recursos.getListGrid().anySelected()) {
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
					criteria.addCriteria("idEspacio", 0);
					implementacion.getListGrid().setCriteria(criteria);
					implementacion.refreshListGridDepend();	
					criteria=new Criteria();
					criteria.addCriteria("recurso", 0);
					recursos.getListGrid().setCriteria(criteria);
					recursos.refreshListGridDepend();
					HLDetails.setDisabled(true);					
				} else {					
					Criteria criteria = new Criteria();
					criteria.addCriteria("idEspacio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idEspacio"));
					implementacion.getListGrid().setCriteria(criteria);
					implementacion.refreshListGrid();
					criteria=new Criteria();
					criteria.addCriteria("idEspacio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idEspacio"));
					recursos.getListGrid().setCriteria(criteria);
					recursos.refreshListGridDepend();
					HLDetails.setDisabled(false);
				}
			}
        });	
	}
}