package uni.posgrado.client;


import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.gwt.InstitucionGwtRPCDS;


public class InstitucionLayout extends VLayout {

	public InstitucionLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Instituciones", "InstitucionServlet", 700, "normal", null);
		InstitucionGwtRPCDS inst = new InstitucionGwtRPCDS(false);
		lista.setDataSource(inst);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideModal();
        lista.setFormTextType("persona");
        lista.getListGrid().getField("persona").setWidth(300);
		addMember(lista);
	}
}