package uni.posgrado.client;


import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.gwt.RegulacionGwtRPCDS;


public class RegulacionLayout extends VLayout {

	public RegulacionLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Reglamentos", "ReglamentoServlet", 900, "normal", null);
		RegulacionGwtRPCDS reglamento = new RegulacionGwtRPCDS(false);
		lista.setDataSource(reglamento);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideNoModal();
        lista.setFormSimpleDate("fechaVigencia");
		lista.getListGrid().getField("fechaVigencia").setWidth(120);
		lista.getListGrid().getField("codigo").setWidth(80);
		addMember(lista);
		lista.getForm().getForm().setWidth(700);
		lista.getForm().getForm().setColWidths(150, 200, 150, 200);
		lista.getListGrid().hideField("inasistencias");
		lista.getListGrid().hideField("inasistenciaJustificada");
		lista.getListGrid().hideField("inasistenciaInjustificada");
		lista.getListGrid().hideField("retiro");
		lista.getListGrid().hideField("notaMaxima");
		lista.getListGrid().hideField("notaMinAprobatoria");
		lista.getListGrid().hideField("notaMinima");
		lista.getListGrid().hideField("descripcion");
	}
}