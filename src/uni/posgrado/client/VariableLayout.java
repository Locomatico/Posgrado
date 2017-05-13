package uni.posgrado.client;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.gwt.VariableGwtRPCDS;

public class VariableLayout extends VLayout {

	public VariableLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Parámetros", "VariableServlet", 700, "normal", null);
		VariableGwtRPCDS variable = new VariableGwtRPCDS();
		lista.setDataSource(variable);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideModal();
		addMember(lista);
	}
}