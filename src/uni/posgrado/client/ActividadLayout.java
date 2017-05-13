package uni.posgrado.client;


import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.gwt.ActividadGwtRPCDS;


public class ActividadLayout extends VLayout {

	public ActividadLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Maestro de Actividades", "ActividadServlet", 600, "normal", null);
		ActividadGwtRPCDS actividad = new ActividadGwtRPCDS();
		lista.setDataSource(actividad);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideModal();
		addMember(lista);
	}
}