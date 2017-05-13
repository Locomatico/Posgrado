package uni.posgrado.client;


import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.PersonaGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;


public class PersonaLayout extends VLayout {

	public PersonaLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Personas", "", 900, "normal", null);
		PersonaGwtRPCDS persona = new PersonaGwtRPCDS(false);
		lista.setDataSource(persona);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideNoModal();
        lista.setFormSimpleDate("fechaNacimiento");
        lista.getListGrid().getField("estadoCivil").setFilterEditorProperties(new ListGridSelect("Estado civil", 200, 200, "codTabla", new VariableFormGwtRPCDS("ESTCIV"), "nomTabla", "codTabla", false));
        lista.getListGrid().getField("tipoDocumento").setFilterEditorProperties(new ListGridSelect("Tipo de documento", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPIDE"), "nomTabla", "codTabla", false));
		addMember(lista);
		
		lista.getForm().getForm().setWidth(700);
		lista.getForm().getForm().setColWidths(150, 200, 150, 200);
	}
}