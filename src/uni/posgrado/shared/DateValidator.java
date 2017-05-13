package uni.posgrado.shared;

import com.smartgwt.client.widgets.form.validator.CustomValidator;

/**
 * Tests whether the value for this field matches the value of some other field. The field to compare against is
 * specified via the otherField property on the validator object (should be set to a field name). Note this validator
 * type is only supported for items being edited within a DynamicForm - it cannot be applied to a ListGrid field.
 */
public class DateValidator extends CustomValidator {

    /**
     * Set the otherField.
     *
     * @param otherField the otherField
     */
    public void setOtherField(String otherField) {
        setAttribute("otherField", otherField);
    }

    /**
     * Return the otherField.
     *
     * @return the otherField
     */
    public String getOtherField() {
        return getAttribute("otherField");
    }
    
    /*@Override
    public boolean equals(Object obj) {
    	// TODO Auto-generated method stub
    	 final JsObject other = (JsObject)obj;
    	return jsObj.equals(other.getJsObj());
    }*/
    
    @Override
	protected boolean condition(Object value) {
		/*try {
			if (!(obj instanceof JsObject)) return false;
	        final JsObject other = (JsObject)value;
	        if (jsObj == null) {
				Date date_ini = (Date) other;
        		if (date_ini.before((Date) this)){
        			return true;
        		} else
        			return false;
	        }
	        return jsObj.condition(other.getJsObj());
		} catch (Exception e) {
			System.out.println("error " + e);
			return false;
		} */
		return true;       		
	}
}

