package uni.posgrado.client;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.ImplementacionGwtRPCDS;
import uni.posgrado.gwt.LocalGwtRPCDS;
import uni.posgrado.gwt.PabellonGwtRPCDS;
import uni.posgrado.gwt.PisoGwtRPCDS;
import uni.posgrado.gwt.EspacioGwtRPCDS;
import uni.posgrado.gwt.RecursoGwtRPCDS;
//import uni.posgrado.gwt.VariableFormGwtRPCDS;

public class ImplementacionLayout extends VLayout {
	public ImplementacionLayout() {       
		BasicVLayout lista = new BasicVLayout(null, "Implementacion", "ImplementacionServlet", 800, "normal", null);
		ImplementacionGwtRPCDS implementacion = new ImplementacionGwtRPCDS(false);
		lista.setDataSource(implementacion);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().setCanGroupBy(false);
		/*
		lista.getListGrid().hideField("localCodigo");
		lista.setFormTextType("idLocal");//se cambio a id_local
		
		
		lista.getListGrid().hideField("pabellonCodigo");
		lista.setFormTextType("idPabellon");//se cambio a id_local
		
		lista.getListGrid().hideField("pisoCodigo");
		lista.setFormTextType("idPiso");//se cambio a id_local
		lista.getListGrid().hideField("espacioCodigo");
		lista.setFormTextType("idEspacio");//se cambio a id_local
		lista.getListGrid().hideField("recursoCodigo");
		lista.setFormTextType("idRecurso");//se cambio a id_local
		*/
        lista.hideExportButton();
        lista.hideModal();
        //lista.getListGrid().getField("tipo").setFilterEditorProperties(new ListGridSelect("Tipo", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPESP"), "nomTabla", "codTabla", false));
        /*
        lista.getForm().getForm().getField("localCodigo").setVisible(false);
        */
        //lista.getForm().getForm().getField("pabellonCodigo").setVisible(false);
        /*
        lista.getForm().getForm().getField("pisoCodigo").setVisible(false);
        lista.getForm().getForm().getField("espacioCodigo").setVisible(false);
        lista.getForm().getForm().getField("recursoCodigo").setVisible(false);
        */
		addMember(lista);
		/*
		lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("id_local")) {//local por id_local idLocal
    				return new ListGridSelect("Local", 200, 300, "nombre", new LocalGwtRPCDS(true), "nombre", "idLocal", true);//id_local
    			}
    			if (field.getName().equals("id_pabellon")) {//local por id_local idLocal
    				return new ListGridSelect("Pabellon", 200, 300, "nombre", new PabellonGwtRPCDS(true), "nombre", "idPabellon", true);//id_local
    			}
    			if (field.getName().equals("id_piso")) {//local por id_local idLocal
    				return new ListGridSelect("Piso", 200, 300, "nombre", new PisoGwtRPCDS(true), "nombre", "idPiso", true);//id_local
    			}
    			if (field.getName().equals("id_espacio")) {//local por id_local idLocal
    				return new ListGridSelect("Espacio", 200, 300, "espacio", new EspacioGwtRPCDS(true), "nombre", "idEspacio", true);//id_local
    			}
    			if (field.getName().equals("id_recurso")) {//local por id_local idLocal
    				return new ListGridSelect("Recurso", 200, 300, "nombre", new RecursoGwtRPCDS(), "nombre", "idRecurso", true);//id_local
    			}
    			return context.getDefaultProperties();
    		}
    	});
		*/
	}
}