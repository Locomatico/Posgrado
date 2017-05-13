package uni.posgrado.client;


import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.gwt.RolGwtRPCDS;


public class RolLayout extends VLayout {

	public RolLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Roles", "RolServlet", 500, "normal", null);
		RolGwtRPCDS rol = new RolGwtRPCDS();
		lista.setDataSource(rol);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideModal();
		addMember(lista);
	}
}