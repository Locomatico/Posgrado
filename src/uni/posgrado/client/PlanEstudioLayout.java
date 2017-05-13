package uni.posgrado.client;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
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
import uni.posgrado.gwt.CicloGwtRPCDS;
import uni.posgrado.gwt.PlanEstudioGwtRPCDS;
import uni.posgrado.gwt.ProgramaGwtRPCDS;
import uni.posgrado.gwt.RegulacionGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;


public class PlanEstudioLayout extends VLayout {

	public PlanEstudioLayout() {       
		final BasicVLayout lista = new BasicVLayout(null, "Planes de Estudio", "", 1000, "normal", null);
		PlanEstudioGwtRPCDS plan = new PlanEstudioGwtRPCDS(false);
		lista.setDataSource(plan);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideNoModal();
        lista.setFormIntegerType("creditos");
        lista.setFormIntegerType("creditosElectivos");
        lista.setFormIntegerType("creditosObligatorios");
        lista.setFormIntegerType("totalCiclo");
        lista.setFormSimpleDate("vigenciaInicio");
        lista.setFormSimpleDate("vigenciaFin");
        lista.setFormTextType("programa");
        lista.setFormTextType("regulacion");        
        lista.getListGrid().getField("formacion").setFilterEditorProperties(new ListGridSelect("Formación", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPFOR"), "nomTabla", "codTabla", false));
        lista.getListGrid().getField("metodologia").setFilterEditorProperties(new ListGridSelect("Metodología", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMET"), "nomTabla", "codTabla", false));
        lista.getListGrid().getField("jornadaEstudio").setFilterEditorProperties(new ListGridSelect("Jornada de estudios", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPJOR"), "nomTabla", "codTabla", false));
        lista.getListGrid().hideField("formacion");
        lista.getListGrid().hideField("metodologia");
        lista.getListGrid().hideField("jornadaEstudio");
        lista.getListGrid().hideField("grado");
        lista.getListGrid().hideField("vigenciaInicio");
        lista.getListGrid().hideField("vigenciaFin");
        lista.getListGrid().hideField("descripcion");
        lista.getListGrid().getField("codigo").setWidth(65);
        lista.getListGrid().getField("programa").setWidth(150);
        lista.getListGrid().getField("regulacion").setWidth(150);
        lista.getListGrid().getField("nombre").setWidth(200);
		addMember(lista);
		
		lista.getForm().getForm().setWidth(800);
		lista.getForm().getForm().setColWidths(200, 200, 200, 200);
		
		lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("programa")) {
    				return new ListGridSelect("Programas", 200, 300, "nombre", new ProgramaGwtRPCDS(true), "nombre", "idPrograma", true);
    			} else if (field.getName().equals("regulacion")) {
    				return new ListGridSelect("Regulación", 200, 300, "nombre", new RegulacionGwtRPCDS(true), "nombre", "idRegulacion", true);    				
    			} else if (field.getName().equals("formacion")) {
    				return new ListGridSelect("Formación", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPFOR"), "nomTabla", "codTabla", true);
    			} else if (field.getName().equals("metodologia")) {
    				return new ListGridSelect("Metodología", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMET"), "nomTabla", "codTabla", true);
    			} else if (field.getName().equals("jornadaEstudio")) {
    				return new ListGridSelect("Jornada de estudios", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPJOR"), "nomTabla", "codTabla", true);
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
		detailTabSet.setWidth(1050);
		detailTabSet.setBackgroundColor("#FFFFFF");
		detailTabSet.setHeight(460);
		
		final BasicVForm form_layout = new BasicVForm(400, "DATOS GENERALES", 1000);
		form_layout.setHeight(420);
		form_layout.getForm().setAutoHeight();
		form_layout.getForm().setIsGroup(false);
		form_layout.setDataSource(new PlanEstudioGwtRPCDS(false));
		
		form_layout.getForm().setColWidths("200", "300", "200", "300");
		form_layout.setBorder("1px solid #0096CC");
				
		Tab tTabDG = new Tab("Datos Generales", "icons/headerIcon.png");
        tTabDG.setPane(form_layout);
        
        final BasicVLayout ciclo = new BasicVLayout(null, "Ciclos", "", 950, "normal", null);
        ciclo.getListGrid().setSelectionType(SelectionStyle.SINGLE);
        ciclo.setDataSource(new CicloGwtRPCDS(false));
        ciclo.getListGrid().setHeight(300);
        ciclo.hideTitle();
        ciclo.hideResetButton();
        ciclo.hideExportButton();
        ciclo.hideModal();
        ciclo.getListGrid().setCanGroupBy(false);

        //ciclo.getListGrid().hideField("planEstudioCodigo");
        //ciclo.getListGrid().hideField("planEstudio");
		
		Tab tTabDC = new Tab("Ciclos", "icons/headerIcon.png");
		tTabDC.setPane(ciclo);
        
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
					criteria.addCriteria("idPlanEstudio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPlanEstudio"));
					ciclo.getListGrid().setCriteria(criteria);
					ciclo.refreshListGridDepend();
					break;
				default:
					break;
				}
			}  
		});
		
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
		
		tTabDC.addTabSelectedHandler(new TabSelectedHandler() {			
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				// TODO Auto-generated method stub
				if(lista.getListGrid().anySelected()) {
					Criteria criteria=new Criteria();
	        		criteria.addCriteria("idPlanEstudio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPlanEstudio"));
	        		ciclo.getListGrid().setCriteria(criteria);
	        		ciclo.refreshListGridDepend();	            	
				}
			}
		});
		
		ciclo.getListGrid().addEditorEnterHandler(new EditorEnterHandler() {			
			@Override
			public void onEditorEnter(EditorEnterEvent event) {
				// TODO Auto-generated method stub				
				ciclo.getListGrid().setEditValue(event.getRowNum(), "idPlanEstudio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPlanEstudio"));
			}
		});
		
		if(!lista.getListGrid().anySelected()) {
			detailTabSet.setDisabled(true);
			form_layout.getForm().reset();
		}
		
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
						criteria.addCriteria("idPlanEstudio", 0);
						ciclo.getListGrid().setCriteria(criteria);
						ciclo.refreshListGridDepend();
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
        
	}
}