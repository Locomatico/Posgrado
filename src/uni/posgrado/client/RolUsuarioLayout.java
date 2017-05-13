package uni.posgrado.client;

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
import uni.posgrado.gwt.UsuarioGwtRPCDS;
import uni.posgrado.gwt.RolUsuarioGwtRPCDS;
import uni.posgrado.gwt.RolGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;

public class RolUsuarioLayout extends VLayout {
	
	public RolUsuarioLayout() {
		
		setOverflow(Overflow.AUTO);
		final BasicVLayout lista = new BasicVLayout(null, "Usuarios", "", 800, "normal", null);
		lista.setHeight(400);
		UsuarioGwtRPCDS persona = new UsuarioGwtRPCDS(false);
		lista.setDataSource(persona);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.setFormTextType("persona");
        lista.getListGrid().getField("tipoDocumento").setWidth(150);
        lista.getListGrid().getField("descripcion").setWidth(200);
        lista.getListGrid().getField("numeroDocumento").setWidth(150);
        lista.getListGrid().getField("persona").setWidth(220);
        lista.getListGrid().getField("tipoDocumento").setFilterEditorProperties(new ListGridSelect("Tipo de documento", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPIDE"), "nomTabla", "codTabla", false));
		
		addMember(lista);
		
		final HLayout HLDetails = new HLayout();
		HLDetails.setLayoutAlign(Alignment.CENTER);
		HLDetails.setAlign(Alignment.CENTER);
		HLDetails.setOverflow(Overflow.VISIBLE);
		HLDetails.setMargin(10);
		HLDetails.setMembersMargin(15);
		HLDetails.setWidth(900);
		final BasicVLayout roles = new BasicVLayout(null, "Roles sin asignar", "", 400, "normal", null);
		roles.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		roles.setDataSource(new RolGwtRPCDS());
		roles.getListGrid().setHeight(250);
		roles.getListGrid().setCanEdit(false);
		roles.getListGrid().setCanPickFields(false);
		roles.getListGrid().setCanGroupBy(false);
		roles.hideAllButtons();
		roles.hideSummary();
		roles.setSize(400, false);
		roles.getListGrid().setCanDragRecordsOut(true);
		roles.getListGrid().setDragDataAction(DragDataAction.NONE);
		roles.getListGrid().setAddDropValues(false);
		roles.getListGrid().setCanAcceptDroppedRecords(true);	
		HLDetails.addMember(roles);
		
		final BasicVLayout personaRol = new BasicVLayout(null, "Roles Asignados", "", 400, "normal", null);	
		personaRol.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		personaRol.setDataSource(new RolUsuarioGwtRPCDS());
		personaRol.getListGrid().setHeight(250);
		personaRol.getListGrid().setCanEdit(true);
		personaRol.getListGrid().setAutoSaveEdits(true);
		personaRol.getListGrid().setCanPickFields(false);
		personaRol.getListGrid().setCanGroupBy(false);
		personaRol.hideAllButtons();
		personaRol.hideSummary();
		personaRol.setSize(400, false);
		personaRol.getListGrid().setCanDragRecordsOut(true);
		personaRol.getListGrid().setDragDataAction(DragDataAction.NONE);
		personaRol.getListGrid().setCanAcceptDroppedRecords(true);
		personaRol.getListGrid().setAddDropValues(false);		
		
		personaRol.getListGrid().addCellSavedHandler(new CellSavedHandler() {
			
			@Override
			public void onCellSaved(CellSavedEvent event) {
				// TODO Auto-generated method stub
				personaRol.refreshListGrid();
			}
		});
		
		final TransferImgButton arrowLeftImg = new TransferImgButton(TransferImgButton.LEFT);
		final TransferImgButton arrowRightImg = new TransferImgButton(TransferImgButton.RIGHT);		
		
		final VLayout buttonsLayout = new VLayout();
		buttonsLayout.setLayoutAlign(Alignment.CENTER);
		buttonsLayout.setAlign(Alignment.CENTER);		
		
		roles.getListGrid().addRecordDropHandler(new RecordDropHandler() {			
			@Override
			public void onRecordDrop(RecordDropEvent event) {
				// TODO Auto-generated method stub
				event.cancel();
				if(personaRol.getListGrid().anySelected()) {
        	    	SC.confirm("Eliminar registros", "¿Está seguro que desea eliminar el/los registro(s) seleccionado(s)?", new BooleanCallback() {
        				@Override
        				public void execute(Boolean value) {
        					// TODO Auto-generated method stub						
        					if (value != null && value) {
        						DSRequest requestProperties = new DSRequest();
        						personaRol.getListGrid().removeSelectedData(new DSCallback() {								
        							@Override
        							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
        								//delButton.disable();
        								roles.refreshListGridDepend();
        								personaRol.refreshListGridDepend();
        				           		if (personaRol.getListGrid().anySelected()) {
        				           			arrowLeftImg.setDisabled(false);
        				           		} else {
        				           			arrowLeftImg.setDisabled(true);
        				           		}
        				           		if (roles.getListGrid().anySelected()) {
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
		
		personaRol.getListGrid().addRecordDropHandler(new RecordDropHandler() {			
			@Override
			public void onRecordDrop(RecordDropEvent event) {
				// TODO Auto-generated method stub
				event.cancel();
				if(roles.getListGrid().anySelected() && lista.getListGrid().anySelected()) {
					Record record = new Record();
	            	record.setAttribute("idRol", roles.getListGrid().getSelectedRecord().getAttributeAsInt("idRol"));
	            	record.setAttribute("idUsuario", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idUsuario"));
	            	record.setAttribute("habilitado", true);
	            	record.setAttribute("mantenimiento", true);
					personaRol.getListGrid().startEditingNew(record);
					personaRol.getListGrid().addData(record, new DSCallback() {
	            		@Override
	            		public void execute(DSResponse dsResponse,
	            				Object data, DSRequest dsRequest) {
	            			// TODO Auto-generated method stub
	            			personaRol.refreshListGrid();
	    					roles.refreshListGridDepend();
	    					if (personaRol.getListGrid().anySelected()) {
			           			arrowLeftImg.setDisabled(false);
			           		} else {
			           			arrowLeftImg.setDisabled(true);
			           		}
			           		if (roles.getListGrid().anySelected()) {
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
           		if(personaRol.getListGrid().anySelected()) {
        	    	SC.confirm("Eliminar registros", "¿Está seguro que desea eliminar el/los registro(s) seleccionado(s)?", new BooleanCallback() {
        				@Override
        				public void execute(Boolean value) {
        					// TODO Auto-generated method stub						
        					if (value != null && value) {
        						DSRequest requestProperties = new DSRequest();
        						personaRol.getListGrid().removeSelectedData(new DSCallback() {								
        							@Override
        							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
        								//delButton.disable();
        								roles.refreshListGridDepend();
        								personaRol.refreshListGridDepend();
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
            	if(roles.getListGrid().anySelected() && lista.getListGrid().anySelected()) {
	            	Record record = new Record();
	            	record.setAttribute("idRol", roles.getListGrid().getSelectedRecord().getAttributeAsInt("idRol"));
	            	record.setAttribute("idUsuario", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idUsuario"));
	            	record.setAttribute("habilitado", true);
	            	record.setAttribute("mantenimiento", true);
	            	personaRol.getListGrid().addData(record, new DSCallback() {
	            		@Override
	            		public void execute(DSResponse dsResponse,
	            				Object data, DSRequest dsRequest) {
	            			// TODO Auto-generated method stub
	            			personaRol.refreshListGrid();
	            			roles.refreshListGridDepend();
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
		HLDetails.addMember(personaRol);
		
		addMember(HLDetails);
		
		lista.getListGrid().setCanEdit(false);
		lista.getListGrid().setCanPickFields(false);
		lista.getListGrid().setCanGroupBy(false);
		lista.hideActionButtons();

		
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {  
			public void onRecordClick(RecordClickEvent event) {
				Criteria criteria = new Criteria();
				criteria.addCriteria("idUsuario", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idUsuario"));
				personaRol.getListGrid().setCriteria(criteria);
				personaRol.refreshListGrid();
				criteria=new Criteria();
				criteria.addCriteria("idUsuario", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idUsuario"));
				roles.getListGrid().setCriteria(criteria);
				roles.refreshListGridDepend();
				HLDetails.setDisabled(false);
			}
		});
		
		personaRol.getListGrid().addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				if (personaRol.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (roles.getListGrid().anySelected()) {
           			arrowRightImg.setDisabled(false);
           		} else {
           			arrowRightImg.setDisabled(true);
           		}
			}
		});
		
		if (personaRol.getListGrid().anySelected()) {
   			arrowLeftImg.setDisabled(false);
   		} else {
   			arrowLeftImg.setDisabled(true);
   		}
   		if (roles.getListGrid().anySelected()) {
   			arrowRightImg.setDisabled(false);
   		} else {
   			arrowRightImg.setDisabled(true);
   		}
		
		roles.getListGrid().addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				if (personaRol.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (roles.getListGrid().anySelected()) {
           			arrowRightImg.setDisabled(false);
           		} else {
           			arrowRightImg.setDisabled(true);
           		}
			}
		});

		if(!lista.getListGrid().anySelected()) {
			Criteria criteria=new Criteria();
			criteria.addCriteria("idUsuario", 0);
			personaRol.getListGrid().setCriteria(criteria);
			personaRol.refreshListGridDepend();	
			criteria=new Criteria();
			criteria.addCriteria("idRol", 0);
			roles.getListGrid().setCriteria(criteria);
			roles.refreshListGridDepend();
			HLDetails.setDisabled(true);
		} else {
			Criteria criteria = new Criteria();
			criteria.addCriteria("idUsuario", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idUsuario"));
			personaRol.getListGrid().setCriteria(criteria);
			personaRol.refreshListGrid();
			criteria=new Criteria();
			criteria.addCriteria("idUsuario", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idUsuario"));
			roles.getListGrid().setCriteria(criteria);
			roles.refreshListGridDepend();
			HLDetails.setDisabled(false);
		}		
		
		roles.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub
				if (personaRol.getListGrid().anySelected()) {
           			arrowLeftImg.setDisabled(false);
           		} else {
           			arrowLeftImg.setDisabled(true);
           		}
           		if (roles.getListGrid().anySelected()) {
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
					criteria.addCriteria("idUsuario", 0);
					personaRol.getListGrid().setCriteria(criteria);
					personaRol.refreshListGridDepend();	
					criteria=new Criteria();
					criteria.addCriteria("idRol", 0);
					roles.getListGrid().setCriteria(criteria);
					roles.refreshListGridDepend();
					HLDetails.setDisabled(true);					
				} else {					
					Criteria criteria = new Criteria();
					criteria.addCriteria("idUsuario", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idUsuario"));
					personaRol.getListGrid().setCriteria(criteria);
					personaRol.refreshListGrid();
					criteria=new Criteria();
					criteria.addCriteria("idUsuario", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idUsuario"));
					roles.getListGrid().setCriteria(criteria);
					roles.refreshListGridDepend();
					HLDetails.setDisabled(false);
				}
			}
        });
	}
}