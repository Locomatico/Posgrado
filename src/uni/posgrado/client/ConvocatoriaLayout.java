package uni.posgrado.client;


import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.EditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.EditorEnterHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.ConvocatoriaGwtRPCDS;
import uni.posgrado.gwt.PeriodoGwtRPCDS;
import uni.posgrado.gwt.ProgramaGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;
import uni.posgrado.gwt.ConvocDetGwtRPCDS;
import uni.posgrado.gwt.ModalidadGwtRPCDS;


public class ConvocatoriaLayout extends VLayout {

	public ConvocatoriaLayout() {       
		final BasicVLayout lista = new BasicVLayout(null, "Convocatorias", "", 900, "normal", null);
		ConvocatoriaGwtRPCDS convocatoria = new ConvocatoriaGwtRPCDS(true);
		lista.setDataSource(convocatoria);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
		lista.setFormSimpleDate("fecInicio");
		lista.setFormSimpleDate("fecFin");
		TextAreaItem textAreaItem = new TextAreaItem();  
        textAreaItem.setHeight(70);
        lista.setFormTextType("periodo");
        lista.getListGrid().getField("descripcion").setEditorProperties(textAreaItem);
        lista.hideExportButton();
        lista.hideModal();
        lista.getListGrid().getField("tipoConvocatoria").setFilterEditorProperties(new ListGridSelect("Tipo de Convocatoria", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPEXA"), "nomTabla", "codTabla", true));
		addMember(lista);
		
		lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("periodo")) {
    				return new ListGridSelect("Periodos Académicos", 200, 300, "codigo", new PeriodoGwtRPCDS(true), "codigo", "idPeriodo", true);
    			} else if (field.getName().equals("tipoConvocatoria")) {
    				return new ListGridSelect("Tipo de Convocatoria", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPEXA"), "nomTabla", "codTabla", true);
    			}
    			return context.getDefaultProperties();
    		}
    	});
		
		
		final BasicVLayout convocDet = new BasicVLayout(null, "Detalle de Convocatoria", "", 900, "detail", null);
		ConvocDetGwtRPCDS layoutDetConvoc = new ConvocDetGwtRPCDS();
		convocDet.setDataSource(layoutDetConvoc);
		convocDet.hideResetButton();
		convocDet.hideModal();
		convocDet.getListGrid().getField("modalidad").setFilterEditorProperties(new ListGridSelect("Modalidades", 200, 200, null, new ModalidadGwtRPCDS(false, false), "nombre", "idModalidad", false));
		convocDet.setFormTextType("convocatoria");		
		convocDet.setFormTextType("programa");
		convocDet.getListGrid().hideField("programaCodigo");
		convocDet.setFormIntegerType("vacantesTotales");
		convocDet.setFormSimpleDate("fecInicioInscrip");
		convocDet.setFormSimpleDate("fecFinInscrip");
		convocDet.setFormSimpleDate("fecInicioPreinscrip");
		convocDet.setFormSimpleDate("fecFinPreinscrip");
		convocDet.setFormSimpleDate("fechaEvaluacion");
		convocDet.setFormSimpleDate("fechaCaducidad");
		
		//convocDet.getListGrid().hideField("periodoCodigo");
		//convocDet.getListGrid().hideField("programaNombre");
		convocDet.getListGrid().hideField("convocatoria");
		convocDet.getListGrid().hideField("periodoCodigo");
		convocDet.setFormSimpleDate("fecInicioInscrip");
		convocDet.setFormSimpleDate("fecFinInscrip");
		convocDet.setFormSimpleDate("fecInicioPreinscrip");
		convocDet.setFormSimpleDate("fecFinPreinscrip");
		convocDet.getListGrid().getField("programa").setWidth(75);
		convocDet.getListGrid().getField("fecInicioInscrip").setWidth(85);
		convocDet.getListGrid().getField("fecFinInscrip").setWidth(85);
		convocDet.getListGrid().getField("modalidad").setWidth(120);
		convocDet.getListGrid().getField("vacantesTotales").setWidth(80);
		convocDet.getListGrid().setCanGroupBy(false);
		convocDet.hideExportButton();
		addMember(convocDet);
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {  
			public void onRecordClick(RecordClickEvent event) {
				if(lista.getListGrid().anySelected()) {
					convocDet.setDisabled(false);
					Criteria criteria = new Criteria();
					criteria.addCriteria("idConvocatoria", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idConvocatoria"));
					convocDet.getListGrid().setCriteria(criteria);
					convocDet.refreshListGrid();
				} else {
					Criteria criteria=new Criteria();
					criteria.addCriteria("idConvocatoria", 0);
					convocDet.getListGrid().filterData(criteria);
					convocDet.setDisabled(true);
				}
			}
		});
		
		lista.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub		
				if(lista.getListGrid().anySelected()) {					
					Criteria criteria = new Criteria();
					criteria.addCriteria("idConvocatoria", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idConvocatoria"));
					convocDet.getListGrid().setCriteria(criteria);
					convocDet.refreshListGrid();
					convocDet.setDisabled(false);
				} else {
					Criteria criteria=new Criteria();
					criteria.addCriteria("idConvocatoria", 0);
					convocDet.getListGrid().filterData(criteria);
					convocDet.setDisabled(true);
				}
			}
        });
		
		convocDet.getListGrid().addEditorEnterHandler(new EditorEnterHandler() {			
			@Override
			public void onEditorEnter(EditorEnterEvent event) {
				// TODO Auto-generated method stub				
				convocDet.getListGrid().setEditValue(event.getRowNum(), "convocatoria", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idConvocatoria"));
			}
		});
		
		
		convocDet.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("modalidad")) {
    				return new ListGridSelect("Modalidades", 200, 200, null, new ModalidadGwtRPCDS(false, false), "nombre", "idModalidad", true);
    			} else if (field.getName().equals("programa")) {
    				return new ListGridSelect("Programas", 200, 200, null, new ProgramaGwtRPCDS(false), "nombre", "idPrograma", true);
    			}
    			return context.getDefaultProperties();
    		}
    	});
	}
}