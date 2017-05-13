package uni.posgrado.client;


import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.TransferImgButton;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.MallaGwtRPCDS;
import uni.posgrado.gwt.RequisitoCursoGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;



public class RequisitoCursoLayout extends VLayout {

	public RequisitoCursoLayout() {     
		setOverflow(Overflow.AUTO);
		final BasicVLayout lista = new BasicVLayout(null, "Cursos", "MallaServlet", 800, "normal", null);
		MallaGwtRPCDS malla = new MallaGwtRPCDS();
		lista.setDataSource(malla);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().getField("idMalla");
		lista.getListGrid().getField("tipoCurso").setFilterEditorProperties(new ListGridSelect("Tipo de curso", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPCUR"), "nomTabla", "codTabla", false));
		lista.getListGrid().hideField("horaAsesoria");
		lista.getListGrid().hideField("horaPractica");
		lista.getListGrid().hideField("horaTeoria");
		lista.getListGrid().hideField("inasistencia");
		lista.getListGrid().hideField("inasistenciaJustificada");
		lista.getListGrid().hideField("inasistenciaInjustificada");
		lista.getListGrid().hideField("retiro");
		lista.getListGrid().hideField("notaMaxima");
		lista.getListGrid().hideField("notaMinAprobatoria");
		lista.getListGrid().hideField("notaMinima");
		lista.getListGrid().hideField("fechaInicio");
		lista.getListGrid().hideField("fechaFin");
		lista.getListGrid().hideField("metodologia");
		lista.getListGrid().hideField("tipoNota");
		lista.getListGrid().setCanEdit(false);
		lista.getListGrid().setCanPickFields(false);
		lista.getListGrid().setCanGroupBy(false);
		lista.hideActionButtons();
		lista.setFormTextType("ciclo");
		lista.setFormTextType("curso");
		lista.getListGrid().getField("ciclo").setWidth(70);
		lista.getListGrid().getField("curso").setWidth(100);
		lista.getListGrid().getField("credito").setWidth(70);
		lista.getListGrid().getField("tipoCurso").setWidth(100);
		addMember(lista);
		
		final HLayout HLDetails = new HLayout();
		HLDetails.setLayoutAlign(Alignment.CENTER);
		HLDetails.setAlign(Alignment.CENTER);
		HLDetails.setOverflow(Overflow.VISIBLE);
		HLDetails.setMargin(10);
		HLDetails.setMembersMargin(15);
		HLDetails.setWidth(1000);
		final BasicVLayout requisito = new BasicVLayout(null, "Requisitos sin asignar", "", 600, "normal", null);
		requisito.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		requisito.setDataSource(new MallaGwtRPCDS());
		requisito.getListGrid().setHeight(300);
		requisito.getListGrid().hideField("horaAsesoria");
		requisito.getListGrid().hideField("horaPractica");
		requisito.getListGrid().hideField("horaTeoria");
		requisito.getListGrid().hideField("inasistencia");
		requisito.getListGrid().hideField("inasistenciaJustificada");
		requisito.getListGrid().hideField("inasistenciaInjustificada");
		requisito.getListGrid().hideField("retiro");
		requisito.getListGrid().hideField("notaMaxima");
		requisito.getListGrid().hideField("notaMinAprobatoria");
		requisito.getListGrid().hideField("notaMinima");
		requisito.getListGrid().hideField("fechaInicio");
		requisito.getListGrid().hideField("fechaFin");
		requisito.getListGrid().hideField("metodologia");
		requisito.getListGrid().hideField("tipoNota");
		requisito.getListGrid().hideField("credito");
		requisito.getListGrid().hideField("tipoCurso");
		requisito.getListGrid().hideField("electivo");
		requisito.getListGrid().hideField("estado");
		requisito.getListGrid().setCanEdit(false);
		requisito.getListGrid().setCanPickFields(false);
		requisito.getListGrid().setCanGroupBy(false);
		requisito.hideAllButtons();
		requisito.setFormTextType("ciclo");
		requisito.setFormTextType("curso");
		requisito.hideSummary();
		requisito.setSize(450, false);
		requisito.getListGrid().setCanDragRecordsOut(true);
		requisito.getListGrid().setDragDataAction(DragDataAction.NONE);
		requisito.getListGrid().setAddDropValues(false);
		requisito.getListGrid().setCanAcceptDroppedRecords(true);	
		HLDetails.addMember(requisito);
		
		final BasicVLayout requisitoCurso = new BasicVLayout(null, "Requisitos Asignados", "", 600, "normal", null);	
		requisitoCurso.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		requisitoCurso.setDataSource(new RequisitoCursoGwtRPCDS());
		requisitoCurso.getListGrid().setHeight(300);
		requisitoCurso.getListGrid().setCanEdit(true);
		requisitoCurso.getListGrid().setAutoSaveEdits(true);
		requisitoCurso.getListGrid().setCanPickFields(false);
		requisitoCurso.getListGrid().setCanGroupBy(false);
		requisitoCurso.hideAllButtons();
		requisitoCurso.hideSummary();
		requisitoCurso.setSize(550, false);
		requisitoCurso.getListGrid().setCanDragRecordsOut(true);
		requisitoCurso.getListGrid().setDragDataAction(DragDataAction.NONE);
		requisitoCurso.getListGrid().setCanAcceptDroppedRecords(true);
		requisitoCurso.getListGrid().setAddDropValues(false);
				
		requisitoCurso.getListGrid().addCellSavedHandler(new CellSavedHandler() {
			
			@Override
			public void onCellSaved(CellSavedEvent event) {
				// TODO Auto-generated method stub
				requisitoCurso.refreshListGrid();
			}
		});
		
		final TransferImgButton arrowLeftImg = new TransferImgButton(TransferImgButton.LEFT);
		final TransferImgButton arrowRightImg = new TransferImgButton(TransferImgButton.RIGHT);		
		
		final VLayout buttonsLayout = new VLayout();
		buttonsLayout.setLayoutAlign(Alignment.CENTER);
		buttonsLayout.setAlign(Alignment.CENTER);
		
		/*programas.getListGrid().addRecordDropHandler(new RecordDropHandler() {			
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
		*/
		LayoutSpacer spacer = new LayoutSpacer(); 
        spacer.setHeight(10);
        buttonsLayout.addMember(arrowLeftImg);
        buttonsLayout.addMember(spacer);
        buttonsLayout.addMember(arrowRightImg);
		HLDetails.addMember(buttonsLayout);
		HLDetails.addMember(requisitoCurso);
		
		addMember(HLDetails);		
		
		
		lista.getListGrid().setCanEdit(false);
		lista.getListGrid().setCanPickFields(false);
		lista.getListGrid().setCanGroupBy(false);
		lista.hideActionButtons();
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {  
			public void onRecordClick(RecordClickEvent event) {
				Criteria criteria = new Criteria();
				criteria.addCriteria("periodo", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
				requisitoCurso.getListGrid().setCriteria(criteria);
				requisitoCurso.refreshListGrid();
				criteria=new Criteria();
				criteria.addCriteria("idPeriodo", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
				requisito.getListGrid().setCriteria(criteria);
				requisito.refreshListGridDepend();
				HLDetails.setDisabled(false);
			}
		});
		
		/*periodoPrograma.getListGrid().addRecordClickHandler(new RecordClickHandler() {			
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
			criteria.addCriteria("periodo", 0);
			periodoPrograma.getListGrid().setCriteria(criteria);
			periodoPrograma.refreshListGridDepend();	
			criteria=new Criteria();
			criteria.addCriteria("programa", 0);
			programas.getListGrid().setCriteria(criteria);
			programas.refreshListGridDepend();
			HLDetails.setDisabled(true);
		} else {
			Criteria criteria = new Criteria();
			criteria.addCriteria("periodo", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
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
					criteria.addCriteria("periodo", 0);
					periodoPrograma.getListGrid().setCriteria(criteria);
					periodoPrograma.refreshListGridDepend();	
					criteria=new Criteria();
					criteria.addCriteria("programa", 0);
					programas.getListGrid().setCriteria(criteria);
					programas.refreshListGridDepend();
					HLDetails.setDisabled(true);					
				} else {					
					Criteria criteria = new Criteria();
					criteria.addCriteria("periodo", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPeriodo"));
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
		
		*/
		
		
	}
}