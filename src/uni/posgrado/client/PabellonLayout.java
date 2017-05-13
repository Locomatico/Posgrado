package uni.posgrado.client;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.PabellonGwtRPCDS;
import uni.posgrado.gwt.LocalGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;

public class PabellonLayout extends VLayout {
	public PabellonLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Pabellon", "PabellonServlet", 800, "normal", null);
		PabellonGwtRPCDS local = new PabellonGwtRPCDS(false);
		lista.setDataSource(local);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
		lista.getListGrid().hideField("localCodigo");
		lista.setFormTextType("idLocal");//se cambio a id_local
        lista.hideExportButton();
        lista.hideModal();
        lista.getListGrid().getField("tipo").setFilterEditorProperties(new ListGridSelect("Tipo", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPPAB"), "nomTabla", "codTabla", false));
        lista.getForm().getForm().getField("localCodigo").setVisible(false);
		addMember(lista);
		lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("id_local")) {//local por id_local idLocal
    				return new ListGridSelect("Local", 200, 300, "nombre", new LocalGwtRPCDS(true), "nombre", "idLocal", true);//id_local
    			}
    			return context.getDefaultProperties();
    		}
    	});
		
	}
}