package uni.posgrado.client;


import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
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
import uni.posgrado.gwt.MallaGwtRPCDS;
import uni.posgrado.gwt.SeccionGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;


public class SeccionLayout extends VLayout {

	public SeccionLayout() {      
		
		final BasicVLayout lista = new BasicVLayout(null, "Cursos", "", 900, "normal", null);
		MallaGwtRPCDS malla = new MallaGwtRPCDS();
		lista.setDataSource(malla);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.setHeight(300);
		lista.setFormTextType("curso");
		lista.getListGrid().getField("metodologia").setFilterEditorProperties(new ListGridSelect("Metodología", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPMET"), "nomTabla", "codTabla", false));
		lista.getListGrid().getField("tipoCurso").setFilterEditorProperties(new ListGridSelect("Tipo de curso", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPCUR"), "nomTabla", "codTabla", false));
		lista.getListGrid().getField("tipoNota").setFilterEditorProperties(new ListGridSelect("Tipo de nota", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPNOT"), "nomTabla", "codTabla", false));
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
		lista.getListGrid().hideField("planEstudio");		
		lista.getListGrid().setCanEdit(false);
		lista.getListGrid().setCanPickFields(false);
		lista.getListGrid().setCanGroupBy(false);
		lista.hideActionButtons();
		addMember(lista);
		
		final BasicVLayout listaSeccion = new BasicVLayout(null, "Secciones", "", 900, "detail", null);
		SeccionGwtRPCDS seccion = new SeccionGwtRPCDS();
		listaSeccion.setDataSource(seccion);
		listaSeccion.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		listaSeccion.getListGrid().setCanGroupBy(false);
		listaSeccion.setHeight(300);
		listaSeccion.hideExportButton();
		listaSeccion.hideResetButton();
		listaSeccion.hideNoModal();
		
		addMember(listaSeccion);
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				listaSeccion.setDisabled(false);
				Criteria criteria=new Criteria();
				criteria.addCriteria("malla", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idMalla"));
				listaSeccion.getListGrid().setCriteria(criteria);
				listaSeccion.refreshListGridDepend();
			}  
		});
		
		if(!lista.getListGrid().anySelected()) {
			Criteria criteria=new Criteria();
			criteria.addCriteria("malla", 0);
			listaSeccion.getListGrid().setCriteria(criteria);
			listaSeccion.refreshListGridDepend();
			listaSeccion.setDisabled(true);
		} else {
			listaSeccion.setDisabled(false);
		}
		
		lista.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub		
				if(!lista.getListGrid().anySelected()) {
						Criteria criteria=new Criteria();
						criteria.addCriteria("malla", 0);
						listaSeccion.getListGrid().setCriteria(criteria);
						listaSeccion.refreshListGridDepend();
						listaSeccion.setDisabled(true);
				} else {
					listaSeccion.setDisabled(false);
				}
			}
        });
		
		listaSeccion.getListGrid().addEditorEnterHandler(new EditorEnterHandler() {			
			@Override
			public void onEditorEnter(EditorEnterEvent event) {
				// TODO Auto-generated method stub				
				listaSeccion.getListGrid().setEditValue(event.getRowNum(), "malla", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idMalla"));
			}
		});			

		listaSeccion.getForm().getForm().getField("malla").setVisible(false);		
		listaSeccion.getListGrid().addDataArrivedHandler(new DataArrivedHandler() {
			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub
				if(lista.getListGrid().anySelected()) {
					listaSeccion.getForm().getForm().getField("malla").setValue(lista.getListGrid().getSelectedRecord().getAttributeAsInt("idMalla"));
				}
					
			}
		});

		final BasicVForm form_layout = new BasicVForm(380, "EDITAR SECCION", 900);
		form_layout.setHeight(380);
		form_layout.setWidth(900);
		form_layout.getForm().setAutoHeight();
		form_layout.getForm().setIsGroup(false);
		form_layout.setDataSource(new SeccionGwtRPCDS());
		
		form_layout.getForm().setColWidths("200", "250", "200", "250");
		form_layout.setBorder("1px solid #0096CC");
		
		addMember(form_layout);
		
		listaSeccion.getListGrid().addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {  
				form_layout.setDisabled(false);
				form_layout.getForm().reset();
				form_layout.getForm().editRecord(listaSeccion.getListGrid().getEditedRecord(event.getRecordNum()));
			}  
		});
		
		listaSeccion.getListGrid().addEditorExitHandler(new EditorExitHandler() {
			@Override
			public void onEditorExit(EditorExitEvent event) {
				// TODO Auto-generated method stub
				form_layout.getForm().editRecord(listaSeccion.getListGrid().getEditedRecord(event.getRowNum()));
			}
		});
		
		if(!listaSeccion.getListGrid().anySelected()) {
			form_layout.setDisabled(true);
			form_layout.getForm().reset();
		}
		
		listaSeccion.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub		
				if(!listaSeccion.getListGrid().anySelected()) {					
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
					listaSeccion.getListGrid().setEditValues(listaSeccion.getListGrid().getRecordIndex(listaSeccion.getListGrid().getSelectedRecord()), form_layout.getForm().getValues());
					listaSeccion.activeSave();
				} else
					listaSeccion.inactiveSave();
			}
		});
		
	}
}