package uni.posgrado.client;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.EspacioGwtRPCDS;
import uni.posgrado.gwt.PisoGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;

public class EspacioLayout extends VLayout {
	public EspacioLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Espacio", "EspacioServlet", 800, "normal", null);
		EspacioGwtRPCDS espacio = new EspacioGwtRPCDS(false);
		lista.setDataSource(espacio);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
		lista.getListGrid().hideField("pisoCodigo");
		lista.setFormTextType("idPiso");//se cambio a id_local
        lista.hideExportButton();
        lista.hideModal();
        lista.getListGrid().getField("tipo").setFilterEditorProperties(new ListGridSelect("Tipo", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPESP"), "nomTabla", "codTabla", false));
        lista.getForm().getForm().getField("pisoCodigo").setVisible(false);
		addMember(lista);
		lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("id_piso")) {//local por id_local idLocal
    				return new ListGridSelect("Piso", 200, 300, "nombre", new PisoGwtRPCDS(true), "nombre", "idPiso", true);//id_local
    			}
    			return context.getDefaultProperties();
    		}
    	});
		
	}
}