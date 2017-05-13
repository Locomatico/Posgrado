package uni.posgrado.client;


import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.InstitucionGwtRPCDS;
import uni.posgrado.gwt.UnidadGwtRPCDS;


public class UnidadLayout extends VLayout {

	public UnidadLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Dependencias", "UnidadServlet", 1000, "normal", null);
		UnidadGwtRPCDS unidad = new UnidadGwtRPCDS(false);
		lista.setDataSource(unidad);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideNoModal();
        lista.getListGrid().getField("idInstitucion").setFilterEditorProperties(new ListGridSelect("Instituciones", 200, 200, "nombre", new InstitucionGwtRPCDS(true), "nombre", "idInstitucion", false));
        lista.getListGrid().getField("codigo").setWidth(70);
        lista.getListGrid().getField("anexo").setWidth(60);
        lista.getListGrid().getField("nombre").setWidth(150);
        
        lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("idInstitucion")) {
    				return new ListGridSelect("Instituciones", 200, 200, "nombre", new InstitucionGwtRPCDS(true), "nombre", "idInstitucion", true);
    			} 
    			return context.getDefaultProperties();
    		}
    	});
        
		addMember(lista);
		lista.getForm().getForm().setWidth(700);
		lista.getForm().getForm().setColWidths(150, 200, 150, 200);
	}
}