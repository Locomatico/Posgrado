package uni.posgrado.client;


import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.gwt.VinculoDocenteGwtRPCDS;


public class VinculoDocenteLayout extends VLayout {

	public VinculoDocenteLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Personas", "", 900, "normal", null);
		VinculoDocenteGwtRPCDS vinculoDocente = new VinculoDocenteGwtRPCDS();
		lista.setDataSource(vinculoDocente);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideModal();
        lista.setFormTextType("usuario");
        lista.setFormTextType("programa");        
		addMember(lista);
	}
}