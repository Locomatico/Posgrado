package uni.posgrado.client;

import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.gwt.TipoEvalGwtRPCDS;

public class TipoEvalLayout extends VLayout {

	public TipoEvalLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Tipos de Evaluación", "TipoEvalServlet", 500, "normal", null);
		TipoEvalGwtRPCDS tipoEval = new TipoEvalGwtRPCDS();
		lista.setDataSource(tipoEval);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideModal();
		addMember(lista);
	}
}