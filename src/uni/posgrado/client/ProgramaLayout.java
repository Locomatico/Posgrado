package uni.posgrado.client;

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
import uni.posgrado.gwt.ProgramaGwtRPCDS;
import uni.posgrado.gwt.UnidadGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;


public class ProgramaLayout extends VLayout {

	public ProgramaLayout() {       
		final BasicVLayout lista = new BasicVLayout(null, "Programas Académicos", "ProgramaServlet", 1000, "normal", null);
		ProgramaGwtRPCDS programa = new ProgramaGwtRPCDS(false);
		lista.setDataSource(programa);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideNoModal();
        lista.setFormIntegerType("duracion");
        lista.setFormSimpleDate("fechaInicio");
        lista.setFormSimpleDate("fechaFin");
        lista.getListGrid().hideField("unidadCodigo");
        lista.getListGrid().hideField("duracion");
        lista.getListGrid().hideField("codigoSunedu");
        lista.getListGrid().hideField("grado");
        lista.getListGrid().hideField("titulo");
        lista.getListGrid().hideField("descripcion");
        lista.getListGrid().hideField("fechaInicio");
        lista.getListGrid().hideField("fechaFin");
        lista.setFormTextType("unidad");        
        lista.getListGrid().getField("formacion").setFilterEditorProperties(new ListGridSelect("Formación", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPFOR"), "nomTabla", "codTabla", false));
        lista.getListGrid().getField("metodologia").setFilterEditorProperties(new ListGridSelect("Metodología", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMET"), "nomTabla", "codTabla", false));
        lista.getListGrid().getField("modalidad").setFilterEditorProperties(new ListGridSelect("Modalidad", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMOD"), "nomTabla", "codTabla", false));
        lista.getListGrid().getField("tipoPeriodo").setFilterEditorProperties(new ListGridSelect("Tipo de Periodo", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPPER"), "nomTabla", "codTabla", false));
        lista.getListGrid().getField("codigo").setWidth(65);
        /*lista.getListGrid().getField("nombre").setWidth(160);
        lista.getListGrid().getField("unidad").setWidth(130);
        lista.getListGrid().getField("fechaInicio").setWidth(80);
        lista.getListGrid().getField("fechaFin").setWidth(80);*/
        lista.getForm().getForm().getField("unidadCodigo").setVisible(false);
        addMember(lista);
		
		lista.getForm().getForm().setWidth(700);
		lista.getForm().getForm().setColWidths(150, 200, 150, 200);		
		
		lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("unidad")) {
    				return new ListGridSelect("Dependencias", 200, 300, "nombre", new UnidadGwtRPCDS(true), "nombre", "idUnidad", true);
    			} else if (field.getName().equals("formacion")) {
    				return new ListGridSelect("Formación", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPFOR"), "nomTabla", "codTabla", true);
    			} else if (field.getName().equals("metodologia")) {
    				return new ListGridSelect("Metodología", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMET"), "nomTabla", "codTabla", true);
    			} else if (field.getName().equals("modalidad")) {
    				return new ListGridSelect("Modalidad", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMOD"), "nomTabla", "codTabla", true);
    			} else if (field.getName().equals("tipoPeriodo")) {
    				return new ListGridSelect("Tipo de Periodo", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPPER"), "nomTabla", "codTabla", true);
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
		form_layout.setDataSource(new ProgramaGwtRPCDS(false));
		
		form_layout.getForm().setColWidths("150", "300", "150", "300");
		form_layout.setBorder("1px solid #0096CC");
		
		Tab tTabDG = new Tab("Datos Generales", "icons/headerIcon.png");
        tTabDG.setPane(form_layout);
        
        detailTabSet.addTab(tTabDG);
        VLDetails.addMember(detailTabSet);
		addMember(VLDetails);
		
		lista.getForm().getForm().setWidth(700);
		lista.getForm().getForm().setColWidths(150, 200, 150, 200);

		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				detailTabSet.setDisabled(false);
				if(detailTabSet.getSelectedTabNumber() == 0) {     	
					form_layout.getForm().reset();
					form_layout.getForm().editRecord(lista.getListGrid().getEditedRecord(event.getRecordNum()));
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
		
		form_layout.getForm().getField("unidadCodigo").setVisible(false);
		
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