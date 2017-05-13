package uni.posgrado.client;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.EditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.EditorEnterHandler;
import com.smartgwt.client.widgets.grid.events.EditorExitEvent;
import com.smartgwt.client.widgets.grid.events.EditorExitHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

import uni.posgrado.client.ui.BasicVForm;
import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.PersonaGwtRPCDS;
import uni.posgrado.gwt.RepresentanteGwtRPCDS;
import uni.posgrado.gwt.SuneduGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;

public class SuneduLayout extends VLayout {
 
	public SuneduLayout() {       
		final BasicVLayout lista = new BasicVLayout(null, "Empresa", "EmpresaServlet", 900, "normal", null);
		SuneduGwtRPCDS Empresa = new SuneduGwtRPCDS(false);
		lista.setDataSource(Empresa);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideNoModal();
        lista.setFormTextType("razonSocial");
        lista.setFormTextType("telefono");
        lista.setFormTextType("telefono1");
        lista.setFormTextType("ruc");
        lista.setFormSimpleDate("empresaCreacion");    
        lista.getListGrid().getField("nombre").setWidth(70);
		lista.getListGrid().getField("ruc").setWidth(100);
		lista.getListGrid().getField("telefono").setWidth(100);
		lista.getListGrid().getField("tipoUniversidad").setFilterEditorProperties(new ListGridSelect("Tipo de universidad", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPUNI"), "nomTabla", "codTabla", false));
		lista.getListGrid().getField("tipoGestion").setWidth(100);
		addMember(lista);
		
		lista.getForm().getForm().setWidth(700);
		lista.getForm().getForm().setColWidths(150, 200, 150, 200);
		
		//autogenerar combo de otra tabla
		lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if  (field.getName().equals("tipoUniversidad")) {
    				return new ListGridSelect("Tipo de Universidad", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPUNI"), "nomTabla", "codTabla", true);
    			}
    			return context.getDefaultProperties();
    		}
    	});
		
		VLayout VLDetails = new VLayout();
		VLDetails.setLayoutAlign(Alignment.CENTER);
		VLDetails.setAlign(Alignment.CENTER);
		VLDetails.setWidth100();
		VLDetails.setMargin(10);
		VLDetails.setMembersMargin(15);
		final TabSet detailTabSet = new TabSet();
		detailTabSet.setLayoutAlign(Alignment.CENTER);
		detailTabSet.setAlign(Alignment.CENTER);
		detailTabSet.setTabBarPosition(Side.TOP);
		detailTabSet.setWidth(950);
		detailTabSet.setBackgroundColor("#FFFFFF");
		detailTabSet.setHeight(460);
		
		final BasicVForm form_layout = new BasicVForm(400, "DATOS GENERALES", 900);
		form_layout.setHeight(420);
		form_layout.getForm().setAutoHeight();
		form_layout.getForm().setIsGroup(false);
		form_layout.setDataSource(new SuneduGwtRPCDS(false));
		
		form_layout.getForm().setColWidths("150", "300", "150", "300");
		form_layout.setBorder("1px solid #0096CC");
		
		Tab tTabDG = new Tab("Datos Generales", "icons/headerIcon.png");
        tTabDG.setPane(form_layout);
		
		/*NUEVO TAB*/
		final BasicVLayout representante = new BasicVLayout(null, "Representantes", "", 950, "normal", null);
        representante.getListGrid().setSelectionType(SelectionStyle.SINGLE);
        representante.setDataSource(new RepresentanteGwtRPCDS(false));
        representante.getListGrid().setHeight(300);
        representante.hideTitle();
        representante.hideResetButton();
        representante.hideExportButton();
        representante.hideModal();
        representante.getListGrid().setCanGroupBy(false);
        
        Tab tTabDC = new Tab("Representantes", "icons/headerIcon.png");
		tTabDC.setPane(representante);
        
		/*FIN NUEVO TAB*/
              
        detailTabSet.addTab(tTabDG);
        detailTabSet.addTab(tTabDC);
        VLDetails.addMember(detailTabSet);
		addMember(VLDetails);
		
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				detailTabSet.setDisabled(false);
				switch (detailTabSet.getSelectedTabNumber()) {
				case 0:
					form_layout.getForm().reset();
					form_layout.getForm().editRecord(lista.getListGrid().getEditedRecord(event.getRecordNum()));
					break;
				case 1:
					Criteria criteria=new Criteria();
					criteria.addCriteria("idEmpresa", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idEmpresa"));
					representante.getListGrid().setCriteria(criteria);
					representante.refreshListGridDepend();
					break;
				default:
					break;
				}
			}  
		});
		
		
		/*
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				detailTabSet.setDisabled(false);
				if(detailTabSet.getSelectedTabNumber() == 0) {     	
					form_layout.getForm().reset();
					form_layout.getForm().editRecord(lista.getListGrid().getEditedRecord(event.getRecordNum()));
				}
			}  
		});
		*/
		
		lista.getListGrid().addEditorExitHandler(new EditorExitHandler() {
			@Override
			public void onEditorExit(EditorExitEvent event) {
				// TODO Auto-generated method stub
				form_layout.getForm().editRecord(lista.getListGrid().getEditedRecord(event.getRowNum()));
			}
		});
		
		tTabDG.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				// TODO Auto-generated method stub
				if(lista.getListGrid().anySelected()) {
					form_layout.getForm().reset();
	            	form_layout.getForm().editSelectedData(lista.getListGrid());
				}
			}
		});		
		
		/*
	
		
		*/
		
		tTabDC.addTabSelectedHandler(new TabSelectedHandler() {			
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				// TODO Auto-generated method stub
				if(lista.getListGrid().anySelected()) {
					Criteria criteria=new Criteria();
	        		criteria.addCriteria("idEmpresa", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idEmpresa"));
	        		representante.getListGrid().setCriteria(criteria);
	        		representante.refreshListGridDepend();	            	
				}
			}
		});
		
		//Guarda el registro de la tabla seleccionada
		representante.getListGrid().addEditorEnterHandler(new EditorEnterHandler() {			
			@Override
			public void onEditorEnter(EditorEnterEvent event) {
				// TODO Auto-generated method stub				
				representante.getListGrid().setEditValue(event.getRowNum(), "idEmpresa", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idEmpresa"));
			}
		});
		
		if(!lista.getListGrid().anySelected()) {
			detailTabSet.setDisabled(true);
			form_layout.getForm().reset();
		}
		
		
		/*
		lista.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub		
				if(!lista.getListGrid().anySelected()) {					
					form_layout.getForm().reset();
					form_layout.getForm().clearValues();
					detailTabSet.setDisabled(true);
				} else {
					detailTabSet.setDisabled(false);
				}
			}
        });
		
		*/
		
		lista.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub		
				if(!lista.getListGrid().anySelected()) {					
					form_layout.getForm().reset();
					form_layout.getForm().clearValues();
					
					switch (detailTabSet.getSelectedTabNumber()) {
					case 1:
						Criteria criteria=new Criteria();
						criteria.addCriteria("idEmpresa", 0);
						representante.getListGrid().setCriteria(criteria);
						representante.refreshListGridDepend();
						break;
					default:
						break;
					}					
					detailTabSet.setDisabled(true);
				} else {
					detailTabSet.setDisabled(false);
				}
			}
        });
		
		
		form_layout.getForm().addItemChangedHandler(new ItemChangedHandler() {
			@Override
			public void onItemChanged(ItemChangedEvent event) {
				// TODO Auto-generated method stub
				if(form_layout.getForm().valuesHaveChanged()) {
					lista.getListGrid().setEditValues(lista.getListGrid().getRecordIndex(lista.getListGrid().getSelectedRecord()), form_layout.getForm().getValues());
					lista.activeSave();
				} else
					lista.inactiveSave();
			}
		});
		/*
		
		// Autocompletar los campos dependiendo el reglamento seleccionado (al crear nuevo curso)
		
				representante.getForm().getForm().getField("idPersona").addChangedHandler(new ChangedHandler() {
					@Override
					public void onChanged(ChangedEvent event) {
						// TODO Auto-generated method stub
						if(event.getValue() != null) { 
							PersonaGwtRPCDS idPersona = new PersonaGwtRPCDS(true);
							Criteria criteria=new Criteria();
							criteria.addCriteria("idPersona", Integer.parseInt(event.getValue().toString()));
							idPersona.fetchData(criteria, new DSCallback() {
								@Override
								public void execute(DSResponse dsResponse, Object data,
										DSRequest dsRequest) {
									// TODO Auto-generated method stub
									if(dsResponse.getTotalRows() == 1 && dsResponse.getData() != null) {
										RecordList record_prf = dsResponse.getDataAsRecordList();	
										representante.getForm().getForm().getField("idPersonanombreCompleto").setValue(record_prf.get(0).getAttributeAsString("idPersonanombreCompleto"));
										representante.getForm().getForm().getField("segundoNombre").setValue(record_prf.get(0).getAttributeAsString("segundoNombre"));								
										representante.getForm().getForm().getField("numeroDocumento").setValue(record_prf.get(0).getAttributeAsString("numeroDocumento"));

									}
								}

							});
						}
					}
				});
				*/
	}
}