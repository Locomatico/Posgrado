package uni.posgrado.client;


import java.util.Date;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.gwt.PeriodoGwtRPCDS;


public class PeriodoLayout extends VLayout {

	public PeriodoLayout() {       
		final BasicVLayout lista = new BasicVLayout(null, "Periodos", "PeriodoServlet", 700, "normal", null);
		PeriodoGwtRPCDS periodo = new PeriodoGwtRPCDS(false);
		lista.setDataSource(periodo);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.setFormSimpleDate("fechaInicio");
		lista.setFormSimpleDate("fechaFin");
		lista.getListGrid().getField("fechaInicio").setWidth(75);
		lista.getListGrid().getField("fechaFin").setWidth(75);
		lista.hideModal();
		
		CustomValidator cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {
        			if(value != null && !value.equals("")) {
	        			int id = lista.getListGrid().getEditRow();
	        			if(lista.getListGrid().getEditedRecord(id).getAttribute("fechaInicio") != null && !lista.getListGrid().getEditedRecord(id).getAttribute("fechaInicio").isEmpty()) {
	        				Date date_ini = (Date) lista.getListGrid().getEditedRecord(id).getAttributeAsDate("fechaInicio");
	                		if (date_ini.before((Date) value)){
	                			return true;
	                		} else
	                			return false;
	        			} else
	        				return true;  
        			} else
        				return true; 
        		} catch (Exception e) {
        			System.out.println("error " + e);
        			return false;
        		}        		
        	}
        };
        cv.setErrorMessage("Debe ser mayor a la fecha de inicio");
        lista.getListGrid().getField("fechaFin").setValidators(cv);
		
        cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {
        			if(value != null && !value.equals("")) {
        				int id = lista.getListGrid().getEditRow();
            			if(lista.getListGrid().getEditedRecord(id).getAttribute("fechaFin") != null && !lista.getListGrid().getEditedRecord(id).getAttribute("fechaFin").isEmpty()) {
            				Date date_ini = (Date) lista.getListGrid().getEditedRecord(id).getAttributeAsDate("fechaFin");
                      		if (date_ini.after((Date) value)){
                    			return true;
                    		} else
                    			return false;
            			} else
            				return true;
        			} else
        				return true;        			      			
        		} catch (Exception e) {
        			System.out.println("error " + e);
        			return false;
        		}        		
        	}
        };
        cv.setErrorMessage("Debe ser menor a la fecha de fin");
        lista.getListGrid().getField("fechaInicio").setValidators(cv);
        
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
		addMember(lista);
	}
}