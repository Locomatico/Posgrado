package uni.posgrado.client;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.PisoGwtRPCDS;
import uni.posgrado.gwt.PabellonGwtRPCDS;
//import uni.posgrado.gwt.VariableFormGwtRPCDS;

public class PisoLayout extends VLayout {
	public PisoLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Piso", "PisoServlet", 800, "normal", null);
		PisoGwtRPCDS pabellon = new PisoGwtRPCDS(false);
		lista.setDataSource(pabellon);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
		lista.getListGrid().hideField("pabellonCodigo");
		lista.setFormTextType("idPabellon");//se cambio a id_local
        lista.hideExportButton();
        lista.hideModal();
        //lista.getListGrid().getField("tipo").setFilterEditorProperties(new ListGridSelect("Tipo", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPPAB"), "nomTabla", "codTabla", false));
        lista.getForm().getForm().getField("pabellonCodigo").setVisible(false);
		addMember(lista);
		lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("id_pabellon")) {//local por id_local idLocal
    				return new ListGridSelect("Pabellon", 200, 300, "nombre", new PabellonGwtRPCDS(true), "nombre", "idPabellon", true);//id_local
    			}
    			return context.getDefaultProperties();
    		}
    	});
		
	}
}