package uni.posgrado.client;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.RecursoGwtRPCDS;
import uni.posgrado.gwt.PisoGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;

public class RecursoLayout extends VLayout {
	public RecursoLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Recurso", "RecursoServlet", 800, "normal", null);
		RecursoGwtRPCDS espacio = new RecursoGwtRPCDS();
		lista.setDataSource(espacio);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
		/*
		lista.getListGrid().hideField("pisoCodigo");
		lista.setFormTextType("idPiso");//se cambio a id_local
        */
        lista.hideExportButton();
        lista.hideModal();
        lista.getListGrid().getField("tipo").setFilterEditorProperties(new ListGridSelect("Tipo", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPREC"), "nomTabla", "codTabla", false));
        //lista.getForm().getForm().getField("pisoCodigo").setVisible(false);
        addMember(lista);
        /*
		lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("id_recurso")) {//local por id_local idLocal
    				return new ListGridSelect("Piso", 200, 300, "nombre", new PisoGwtRPCDS(true), "nombre", "idPiso", true);//id_local
    			}
    			return context.getDefaultProperties();
    		}
    	});
		*/
	}
}