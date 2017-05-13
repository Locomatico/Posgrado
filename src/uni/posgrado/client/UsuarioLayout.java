package uni.posgrado.client;


import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.events.EditorExitEvent;
import com.smartgwt.client.widgets.grid.events.EditorExitHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.PersonaGwtRPCDS;
import uni.posgrado.gwt.UsuarioGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;


public class UsuarioLayout extends VLayout {

	public UsuarioLayout() {       
		final BasicVLayout lista = new BasicVLayout(null, "Usuarios", "", 800, "normal", null);
		UsuarioGwtRPCDS user = new UsuarioGwtRPCDS(false);
		lista.setDataSource(user);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
        lista.hideModal();
        lista.setFormTextType("persona");
        lista.getListGrid().getField("tipoDocumento").setWidth(150);
        lista.getListGrid().getField("descripcion").setWidth(200);
        lista.getListGrid().getField("numeroDocumento").setWidth(150);
        lista.getListGrid().getField("persona").setWidth(220);
        lista.getListGrid().getField("tipoDocumento").setFilterEditorProperties(new ListGridSelect("Tipo de documento", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPIDE"), "nomTabla", "codTabla", false));
		addMember(lista);
		
		lista.getListGrid().getField("persona").addEditorExitHandler(new EditorExitHandler() {			
			@Override
			public void onEditorExit(final EditorExitEvent event) {
				// TODO Auto-generated method stub
				if(event.getNewValue() != null) {
					PersonaGwtRPCDS persona = new PersonaGwtRPCDS(true);
				    Criteria criteria=new Criteria();
					criteria.addCriteria("idPersona", Integer.parseInt(event.getNewValue().toString()));
				    persona.fetchData(criteria, new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
							// TODO Auto-generated method stub
							if(dsResponse.getTotalRows() == 1 && dsResponse.getData() != null) {
								RecordList record_prf = dsResponse.getDataAsRecordList();						
								lista.getListGrid().setEditValue(event.getRowNum(), "numeroDocumento", record_prf.get(0).getAttributeAsString("numeroDocumento"));
								lista.getListGrid().setEditValue(event.getRowNum(), "tipoDocumento", record_prf.get(0).getAttributeAsString("tipoDocumento"));
								lista.getListGrid().setEditValue(event.getRowNum(), "tipoDocumentoNombre", record_prf.get(0).getAttributeAsString("tipoDocumentoNombre"));
							}
						}
				    	
				    });
				}
				
			}
		});
	}
}