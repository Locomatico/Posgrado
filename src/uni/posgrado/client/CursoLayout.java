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
import uni.posgrado.gwt.CursoGwtRPCDS;
import uni.posgrado.gwt.RegulacionGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;


public class CursoLayout extends VLayout {

	public CursoLayout() {       
		final BasicVLayout lista = new BasicVLayout(null, "Cursos", "CursoServlet", 900, "normal", null);
		CursoGwtRPCDS curso = new CursoGwtRPCDS(false);
		lista.setDataSource(curso);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideNoModal();
        lista.setFormIntegerType("credito");
        lista.setFormIntegerType("notaMaxima");
        lista.setFormIntegerType("notaMinAprobatoria");
        lista.setFormIntegerType("notaMinima");
        lista.setFormSimpleDate("fechaInicio");
        lista.setFormSimpleDate("fechaFin");        
        lista.setFormTextType("regulacion");
        lista.getListGrid().getField("modalidad").setFilterEditorProperties(new ListGridSelect("Modalidad", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMOD"), "nomTabla", "codTabla", false));
        lista.getListGrid().getField("tipoCurso").setFilterEditorProperties(new ListGridSelect("Tipo de curso", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPCUR"), "nomTabla", "codTabla", false));
        lista.getListGrid().getField("tipoNota").setFilterEditorProperties(new ListGridSelect("Tipo de nota", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPNOT"), "nomTabla", "codTabla", false));
        lista.getListGrid().hideField("inasistencia");
        lista.getListGrid().hideField("inasistenciaJustificada");
        lista.getListGrid().hideField("inasistenciaInjustificada");
        lista.getListGrid().hideField("retiro");
        lista.getListGrid().hideField("notaMaxima");
        lista.getListGrid().hideField("notaMinAprobatoria");
        lista.getListGrid().hideField("notaMinima");
        lista.getListGrid().hideField("fechaInicio");
        lista.getListGrid().hideField("fechaFin");
        lista.getListGrid().getField("codigo").setWidth(70);
		lista.getListGrid().getField("credito").setWidth(70);
		lista.getListGrid().getField("modalidad").setWidth(100);
		lista.getListGrid().getField("tipoCurso").setWidth(100);
		lista.getListGrid().getField("tipoNota").setWidth(100);
		lista.getListGrid().getField("regulacion").setWidth(100);
		addMember(lista);
		lista.getForm().getForm().setWidth(700);
		lista.getForm().getForm().setColWidths(150, 200, 150, 200);
		
		lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("regulacion")) {
    				return new ListGridSelect("Regulación", 200, 300, "nombre", new RegulacionGwtRPCDS(true), "nombre", "idRegulacion", true);
    			} else if (field.getName().equals("modalidad")) {
    				return new ListGridSelect("Modalidad", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMOD"), "nomTabla", "codTabla", true);
    			} else if (field.getName().equals("tipoCurso")) {
    				return new ListGridSelect("Tipo de curso", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPCUR"), "nomTabla", "codTabla", true);
    			} else if (field.getName().equals("tipoNota")) {
    				return new ListGridSelect("Tipo de nota", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPNOT"), "nomTabla", "codTabla", true);
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
		form_layout.setDataSource(new CursoGwtRPCDS(false));
		
		form_layout.getForm().setColWidths("150", "300", "150", "300");
		form_layout.setBorder("1px solid #0096CC");
		
		Tab tTabDG = new Tab("Datos Generales", "icons/headerIcon.png");
        tTabDG.setPane(form_layout);
        
        detailTabSet.addTab(tTabDG);
        VLDetails.addMember(detailTabSet);
		addMember(VLDetails);
		
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
		
		// Autocompletar los campos dependiendo el reglamento seleccionado (al crear nuevo curso)
		
		lista.getForm().getForm().getField("regulacion").addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if(event.getValue() != null) { 
					RegulacionGwtRPCDS regulacion = new RegulacionGwtRPCDS(true);
				    Criteria criteria=new Criteria();
					criteria.addCriteria("idRegulacion", Integer.parseInt(event.getValue().toString()));
					regulacion.fetchData(criteria, new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
							// TODO Auto-generated method stub
							if(dsResponse.getTotalRows() == 1 && dsResponse.getData() != null) {
								RecordList record_prf = dsResponse.getDataAsRecordList();	
								lista.getForm().getForm().getField("inasistencia").setValue(record_prf.get(0).getAttributeAsString("inasistencias"));
								lista.getForm().getForm().getField("inasistenciaJustificada").setValue(record_prf.get(0).getAttributeAsString("inasistenciaJustificada"));
								lista.getForm().getForm().getField("inasistenciaInjustificada").setValue(record_prf.get(0).getAttributeAsString("inasistenciaInjustificada"));
								lista.getForm().getForm().getField("retiro").setValue(record_prf.get(0).getAttributeAsString("retiro"));
								lista.getForm().getForm().getField("notaMaxima").setValue(record_prf.get(0).getAttributeAsString("notaMaxima"));
								lista.getForm().getForm().getField("notaMinAprobatoria").setValue(record_prf.get(0).getAttributeAsString("notaMinAprobatoria"));
								lista.getForm().getForm().getField("notaMinima").setValue(record_prf.get(0).getAttributeAsString("notaMinima"));
								
							}
						}
				    	
				    });
				}
			}
		});
        
	}
}