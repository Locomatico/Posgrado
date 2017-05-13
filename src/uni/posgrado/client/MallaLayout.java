package uni.posgrado.client;


import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
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

import uni.posgrado.client.ui.BasicVForm;
import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.CicloGwtRPCDS;
import uni.posgrado.gwt.CursoGwtRPCDS;
import uni.posgrado.gwt.MallaGwtRPCDS;
import uni.posgrado.gwt.PlanEstudioGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;


public class MallaLayout extends VLayout {

	public MallaLayout() {      
		
		final BasicVLayout lista = new BasicVLayout(null, "Planes de Estudio", "", 900, "normal", null);
		PlanEstudioGwtRPCDS plan = new PlanEstudioGwtRPCDS(false);
		lista.setDataSource(plan);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.setHeight(300);
		lista.getListGrid().hideField("creditos");
		lista.getListGrid().hideField("creditosElectivos");
		lista.getListGrid().hideField("creditosObligatorios");
		lista.getListGrid().hideField("grado");
		lista.getListGrid().hideField("vigenciaInicio");
		lista.getListGrid().hideField("vigenciaFin");
		lista.getListGrid().hideField("descripcion");		
		lista.getListGrid().setCanEdit(false);
		lista.getListGrid().setCanPickFields(false);
		lista.getListGrid().setCanGroupBy(false);
		lista.hideActionButtons();
		lista.setFormTextType("programa");
        lista.setFormTextType("regulacion");
        lista.getListGrid().getField("formacion").setFilterEditorProperties(new ListGridSelect("Formación", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPFOR"), "nomTabla", "codTabla", false));
        lista.getListGrid().getField("metodologia").setFilterEditorProperties(new ListGridSelect("Metodología", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMET"), "nomTabla", "codTabla", false));
        lista.getListGrid().getField("jornadaEstudio").setFilterEditorProperties(new ListGridSelect("Jornada de estudios", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPJOR"), "nomTabla", "codTabla", false));
        lista.getListGrid().getField("codigo").setWidth(60);
        lista.getListGrid().getField("programa").setWidth(120);
        lista.getListGrid().getField("regulacion").setWidth(120);
        lista.getListGrid().getField("nombre").setWidth(180);
        lista.getListGrid().getField("totalCiclo").setWidth(60);
        lista.getListGrid().getField("formacion").setWidth(85);
        lista.getListGrid().getField("metodologia").setWidth(90);
        lista.getListGrid().getField("jornadaEstudio").setWidth(90);
		addMember(lista);
		
		final BasicVLayout listaMalla = new BasicVLayout(null, "Malla curricular", "", 900, "detail", null);
		MallaGwtRPCDS malla = new MallaGwtRPCDS();
		listaMalla.setDataSource(malla);
		listaMalla.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		listaMalla.getListGrid().setCanGroupBy(false);
		listaMalla.setHeight(300);
		listaMalla.hideExportButton();
		listaMalla.hideResetButton();
		listaMalla.hideNoModal();
		listaMalla.setFormTextType("curso");
		listaMalla.setFormIntegerType("ciclo");
		listaMalla.getListGrid().getField("metodologia").setFilterEditorProperties(new ListGridSelect("Metodología", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMET"), "nomTabla", "codTabla", false));
		listaMalla.getListGrid().getField("tipoCurso").setFilterEditorProperties(new ListGridSelect("Tipo de curso", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPCUR"), "nomTabla", "codTabla", false));
		listaMalla.getListGrid().getField("tipoNota").setFilterEditorProperties(new ListGridSelect("Tipo de nota", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPNOT"), "nomTabla", "codTabla", false));
		listaMalla.getListGrid().hideField("horaAsesoria");
		listaMalla.getListGrid().hideField("horaPractica");
		listaMalla.getListGrid().hideField("horaTeoria");
		listaMalla.getListGrid().hideField("inasistencia");
		listaMalla.getListGrid().hideField("inasistenciaJustificada");
		listaMalla.getListGrid().hideField("inasistenciaInjustificada");
		listaMalla.getListGrid().hideField("retiro");
		listaMalla.getListGrid().hideField("notaMaxima");
		listaMalla.getListGrid().hideField("notaMinAprobatoria");
		listaMalla.getListGrid().hideField("notaMinima");
		listaMalla.getListGrid().hideField("fechaInicio");
		listaMalla.getListGrid().hideField("fechaFin");
		//listaMalla.getListGrid().hideField("planEstudio");
		listaMalla.getListGrid().getField("ciclo").setWidth(70);
		listaMalla.getListGrid().getField("curso").setWidth(70);
		listaMalla.getListGrid().getField("credito").setWidth(70);
		listaMalla.getListGrid().getField("metodologia").setWidth(100);
		listaMalla.getListGrid().getField("tipoCurso").setWidth(100);
		listaMalla.getListGrid().getField("tipoNota").setWidth(100);
		addMember(listaMalla);
		
		listaMalla.getForm().getForm().setWidth(700);
		listaMalla.getForm().getForm().setColWidths(150, 200, 150, 200);		
		
		listaMalla.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			String fieldName = field.getName();
    			if(fieldName.equals("curso")) {
    				return new ListGridSelect("Cursos", 200, 300, "codigo", new CursoGwtRPCDS(true), "codigo", "idCurso", true);
    			} else if(fieldName.equals("metodologia")) {
    				return new ListGridSelect("Metodología", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMET"), "nomTabla", "codTabla", true);
    			} else if(fieldName.equals("tipoCurso")) {
    				return new ListGridSelect("Tipo de curso", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPCUR"), "nomTabla", "codTabla", true);
    			} else if(fieldName.equals("tipoNota")) {
    				return new ListGridSelect("Tipo de nota", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPNOT"), "nomTabla", "codTabla", true);
    			} else if(fieldName.equals("ciclo")) {
    				Criteria criteria = new Criteria();
    				criteria.addCriteria("idPlanEstudio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPlanEstudio"));
    				ListGridSelect gridCiclo = new ListGridSelect("Ciclo", 200, 200, "numero", new CicloGwtRPCDS(true), "numero", "idCiclo", true);
    				gridCiclo.setOptionCriteria(criteria);
    				return gridCiclo;
    			} 			
    			return context.getDefaultProperties();
    		}
    	});
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				listaMalla.setDisabled(false);
				Criteria criteria=new Criteria();
				criteria.addCriteria("planEstudio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPlanEstudio"));
				listaMalla.getListGrid().setCriteria(criteria);
				listaMalla.refreshListGridDepend();
			}  
		});
		
		if(!lista.getListGrid().anySelected()) {
			Criteria criteria=new Criteria();
			criteria.addCriteria("planEstudio", 0);
			listaMalla.getListGrid().setCriteria(criteria);
			listaMalla.refreshListGridDepend();
			listaMalla.setDisabled(true);
		} else {
			listaMalla.setDisabled(false);
		}
		
		lista.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub		
				if(!lista.getListGrid().anySelected()) {
					Criteria criteria=new Criteria();
					criteria.addCriteria("planEstudio", 0);
					listaMalla.getListGrid().setCriteria(criteria);
					listaMalla.refreshListGridDepend();					
					listaMalla.setDisabled(true);
				} else {
					listaMalla.setDisabled(false);
				}
			}
        });
		
		listaMalla.getListGrid().addEditorEnterHandler(new EditorEnterHandler() {			
			@Override
			public void onEditorEnter(EditorEnterEvent event) {
				// TODO Auto-generated method stub
				listaMalla.getListGrid().setEditValue(event.getRowNum(), "planEstudio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPlanEstudio"));
			}
		});	

		listaMalla.getForm().getForm().addDrawHandler(new DrawHandler() {			
			@Override
			public void onDraw(DrawEvent event) {
				// TODO Auto-generated method stub				
				
				if(lista.getListGrid().anySelected()) {
					
					//listaMalla.getForm().getForm().getField("planEstudio").setVisible(false);
					//listaMalla.getForm().getForm().getField("planEstudio").setValue(lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPlanEstudio"));

					Criteria criteria = new Criteria();
					criteria.addCriteria("idPlanEstudio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPlanEstudio"));
					listaMalla.getForm().getForm().getField("ciclo").setOptionCriteria(criteria);
				}
			}
		});
		
		final BasicVForm form_layout = new BasicVForm(380, "EDITAR MALLA", 900);
		form_layout.setHeight(380);
		form_layout.setWidth(900);
		form_layout.getForm().setAutoHeight();
		form_layout.getForm().setIsGroup(false);
		form_layout.setDataSource(new MallaGwtRPCDS());
		
		form_layout.getForm().setColWidths("200", "250", "200", "250");
		form_layout.setBorder("1px solid #0096CC");
		
		addMember(form_layout);
		
		//listaMalla.getForm().getForm().getField("planEstudio").setVisible(false);
		//form_layout.getForm().getField("planEstudio").setVisible(false);
		
		listaMalla.getListGrid().addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {  
				form_layout.setDisabled(false);
				form_layout.getForm().reset();
				form_layout.getForm().editRecord(listaMalla.getListGrid().getEditedRecord(event.getRecordNum()));
				
				Criteria criteria = new Criteria();
				criteria.addCriteria("idPlanEstudio", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idPlanEstudio"));
				form_layout.getForm().getField("ciclo").setOptionCriteria(criteria);
			}  
		});
		
		
		listaMalla.getListGrid().addEditorExitHandler(new EditorExitHandler() {
			@Override
			public void onEditorExit(EditorExitEvent event) {
				// TODO Auto-generated method stub
				form_layout.getForm().editRecord(listaMalla.getListGrid().getEditedRecord(event.getRowNum()));
			}
		});
		
		if(!listaMalla.getListGrid().anySelected()) {
			form_layout.setDisabled(true);
			form_layout.getForm().reset();
		}
		
		listaMalla.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub		
				if(!listaMalla.getListGrid().anySelected()) {					
					form_layout.getForm().reset();
					form_layout.getForm().clearValues();
					form_layout.setDisabled(true);
				} else {
					form_layout.setDisabled(false);
				}
			}
        });
		
		form_layout.getForm().addItemChangedHandler(new ItemChangedHandler() {
			@Override
			public void onItemChanged(ItemChangedEvent event) {
				// TODO Auto-generated method stub
				if(form_layout.getForm().valuesHaveChanged()) {
					listaMalla.getListGrid().setEditValues(listaMalla.getListGrid().getRecordIndex(listaMalla.getListGrid().getSelectedRecord()), form_layout.getForm().getValues());
					listaMalla.activeSave();
				} else
					listaMalla.inactiveSave();
			}
		});
		
		
		// Autocompletar los campos dependiendo el reglamento seleccionado (al crear nuevo curso)
		
		listaMalla.getForm().getForm().getField("curso").addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if(event.getValue() != null) { 
					CursoGwtRPCDS curso = new CursoGwtRPCDS(true);
					Criteria criteria=new Criteria();
					criteria.addCriteria("idCurso", Integer.parseInt(event.getValue().toString()));
					curso.fetchData(criteria, new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
							// TODO Auto-generated method stub
							if(dsResponse.getTotalRows() == 1 && dsResponse.getData() != null) {
								RecordList record_prf = dsResponse.getDataAsRecordList();	
								listaMalla.getForm().getForm().getField("descripcion").setValue(record_prf.get(0).getAttributeAsString("descripcion"));
								listaMalla.getForm().getForm().getField("credito").setValue(record_prf.get(0).getAttributeAsString("credito"));								
								listaMalla.getForm().getForm().getField("inasistencia").setValue(record_prf.get(0).getAttributeAsString("inasistencia"));
								listaMalla.getForm().getForm().getField("inasistenciaJustificada").setValue(record_prf.get(0).getAttributeAsString("inasistenciaJustificada"));
								listaMalla.getForm().getForm().getField("inasistenciaInjustificada").setValue(record_prf.get(0).getAttributeAsString("inasistenciaInjustificada"));
								listaMalla.getForm().getForm().getField("retiro").setValue(record_prf.get(0).getAttributeAsString("retiro"));
								listaMalla.getForm().getForm().getField("notaMaxima").setValue(record_prf.get(0).getAttributeAsString("notaMaxima"));
								listaMalla.getForm().getForm().getField("notaMinAprobatoria").setValue(record_prf.get(0).getAttributeAsString("notaMinAprobatoria"));
								listaMalla.getForm().getForm().getField("notaMinima").setValue(record_prf.get(0).getAttributeAsString("notaMinima"));
								listaMalla.getForm().getForm().getField("tipoCurso").setValue(record_prf.get(0).getAttributeAsString("tipoCurso"));
								listaMalla.getForm().getForm().getField("tipoNota").setValue(record_prf.get(0).getAttributeAsString("tipoNota"));

							}
						}

					});
				}
			}
		});

	}
}