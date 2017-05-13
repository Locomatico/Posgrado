package uni.posgrado.client;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.ModalidadGwtRPCDS;
import uni.posgrado.gwt.ProgramaGwtRPCDS;
import uni.posgrado.gwt.ReqEstadoGwtRPCDS;
import uni.posgrado.gwt.RequisitoGwtRPCDS;

public class RequisitoLayout extends VLayout {

	public RequisitoLayout() {       
		final BasicVLayout lista = new BasicVLayout(null, "Requisitos", "", 800, "normal", null);
		RequisitoGwtRPCDS req = new RequisitoGwtRPCDS();
		lista.setDataSource(req);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.setFormTextType("programa");
        lista.setFormTextType("programaNombre");
        lista.hideModal();
		addMember(lista);
		TextAreaItem textAreaItem = new TextAreaItem();  
        textAreaItem.setHeight(70);
        lista.getListGrid().getField("descripcion").setEditorProperties(textAreaItem);
		lista.getListGrid().hideField("programaNombre");
		lista.getListGrid().getField("modalidad").setFilterEditorProperties(new ListGridSelect("Modalidades", 200, 200, null, new ModalidadGwtRPCDS(false, true), "nombre", "idModalidad", false));
		//lista.getListGrid().getField("programa").setFilterEditorProperties(new ListGridSelect("Programas", 200, 200, null, new ProgramaGwtRPCDS(true), "nombre", "idPrograma", false));
		lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("modalidad")) {
    				return new ListGridSelect("Modalidades", 200, 200, null, new ModalidadGwtRPCDS(false, true), "nombre", "idModalidad", true);
    			} else if (field.getName().equals("programa")) {
    				return new ListGridSelect("Programas", 200, 200, null, new ProgramaGwtRPCDS(true), "nombre", "idPrograma", true);
    			}
    			return context.getDefaultProperties();
    		}
    	});
		
		final BasicVLayout reqEstado = new BasicVLayout(null, "Estados de Postulación", "", 500, "detail", null);
		ReqEstadoGwtRPCDS layoutReqEstado = new ReqEstadoGwtRPCDS();
		reqEstado.setDataSource(layoutReqEstado);
		reqEstado.hideResetButton();
		reqEstado.hideActionNewButtons();
		reqEstado.getListGrid().setCanGroupBy(false);
		reqEstado.getListGrid().setShowFilterEditor(false);
		reqEstado.getListGrid().setCanSort(false);
		addMember(reqEstado);
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {  
			public void onRecordClick(RecordClickEvent event) {
				if(lista.getListGrid().anySelected()) {
					reqEstado.setDisabled(false);
					Criteria criteria = new Criteria();
					criteria.addCriteria("requisito", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idRequisito"));
					reqEstado.getListGrid().setCriteria(criteria);
					reqEstado.refreshListGrid();
				} else {
					Criteria criteria=new Criteria();
					criteria.addCriteria("requisito", 0);
					reqEstado.getListGrid().filterData(criteria);
					reqEstado.setDisabled(true);
				}
			}
		});
		
		lista.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub		
				if(lista.getListGrid().anySelected()) {					
					Criteria criteria = new Criteria();
					criteria.addCriteria("requisito", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idRequisito"));
					reqEstado.getListGrid().setCriteria(criteria);
					reqEstado.refreshListGrid();
					reqEstado.setDisabled(false);
				} else {
					Criteria criteria=new Criteria();
					criteria.addCriteria("requisito", 0);
					reqEstado.getListGrid().filterData(criteria);
					reqEstado.setDisabled(true);
				}
			}
        });
		
		
		/*if(lista.getListGrid().anySelected()) {
			Criteria criteria = new Criteria();
			criteria.addCriteria("idRequisitoRequisito", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idRequisito"));
			reqEstado.getListGrid().setCriteria(criteria);
			reqEstado.refreshListGrid();
		}*/
	}
}