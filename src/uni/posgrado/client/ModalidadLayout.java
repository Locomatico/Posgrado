package uni.posgrado.client;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.ModalidadGwtRPCDS;


	public class ModalidadLayout extends VLayout {

		public ModalidadLayout() {       
			final BasicVLayout lista = new BasicVLayout(null, "Modalidades de ingreso", "ModalidadServlet", 900, "normal", null);
			ModalidadGwtRPCDS modalidad = new ModalidadGwtRPCDS(true, false);
			lista.setDataSource(modalidad);
			lista.getListGrid().setModalEditing(true); 
			lista.getListGrid().getField("nombre").setWidth(280);
			lista.getListGrid().getField("modNombreShort").setWidth(100);
			lista.getListGrid().getField("peso").setWidth(50);
			lista.getListGrid().getField("descripcion").setCanSort(false);
			lista.getListGrid().getField("descripcion").setCanGroupBy(false);
			lista.getListGrid().getField("modParticipantes").setCanSort(false);
			lista.getListGrid().getField("modParticipantes").setCanGroupBy(false);
			TextAreaItem textAreaItem = new TextAreaItem();  
	        textAreaItem.setHeight(70);
	        lista.getListGrid().getField("descripcion").setEditorProperties(textAreaItem);
	        lista.getListGrid().getField("modParticipantes").setEditorProperties(textAreaItem);
	        TextItem textItem = new TextItem();
			textItem.setDisplayField(null);
			textItem.setDefaultValue((String)null);
			lista.getListGrid().getField("idModPadre").setFilterEditorProperties(textItem);
			lista.getListGrid().getField("idModPadre").setCanSort(false);
			lista.setFormIntegerType("peso");
			lista.getListGrid().setCanGroupBy(false);
	        lista.hideExportButton();
	        lista.hideModal();
			addMember(lista);
			
			lista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
	    		@Override
	    		public FormItem getEditor(ListGridEditorContext context) {
	    			// TODO Auto-generated method stub
	    			ListGridField field = context.getEditField();
	    			if (field.getName().equals("idModPadre")) {
	    				return new ListGridSelect("Modalidades", 200, 200, null, new ModalidadGwtRPCDS(false, false), "nombre", "idModalidad", false);
	    			} 
	    			return context.getDefaultProperties();
	    		}
	    	});
			
			CustomValidator cv = new CustomValidator() {
	        	@Override
	        	protected boolean condition(Object value) {
	        		try {
	        			if(value != null && !value.equals("")) {
	        				int ids[] = lista.getListGrid().getAllEditRows();
	        				for (int i : ids) {
	        					if(lista.getListGrid().getEditedRecord(i).getAttributeAsInt("idModalidad") != lista.getListGrid().getEditedRecord(i).getAttributeAsInt("idModPadre")) {
		        					return true;
		                    	} else
		                    		return false;
							}
	        			} else
	        				return true;        			      			
	        		} catch (Exception e) {
	        			System.out.println("error " + e);
	        			return false;
	        		}
					return false;        		
	        	}
	        };
	        cv.setErrorMessage("Una modalidad no se puede asignar a ella misma como padre");
	        lista.getListGrid().getField("idModPadre").setValidators(cv);
		}		
		
	}