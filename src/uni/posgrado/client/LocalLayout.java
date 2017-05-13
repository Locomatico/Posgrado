package uni.posgrado.client;


import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.LocalGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;


public class LocalLayout extends VLayout {

	public LocalLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Local", "LocalServlet", 800, "normal", null);
		LocalGwtRPCDS local = new LocalGwtRPCDS(false);
		lista.setDataSource(local);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideModal();
        lista.getListGrid().getField("tipo").setFilterEditorProperties(new ListGridSelect("Tipo", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPLOC"), "nomTabla", "codTabla", false));
		addMember(lista);
	}
}