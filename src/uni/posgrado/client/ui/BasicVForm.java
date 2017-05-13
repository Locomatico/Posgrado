package uni.posgrado.client.ui;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

public class BasicVForm extends VLayout {

	final DynamicForm form = new DynamicForm();
	
	public BasicVForm(int height, String name, int with) { 
		setAlign(Alignment.CENTER);
		setLayoutAlign(Alignment.CENTER);		
		setWidth100();
		setOverflow(Overflow.VISIBLE);
		
		form.setIsGroup(true);  
        form.setGroupTitle(name);
        form.setNumCols(4);
        form.setWidth(with);        
        form.setAlign(Alignment.CENTER); 
        form.setLayoutAlign(Alignment.CENTER);
        form.setPadding(15);
        form.setHeight(height);
        form.setCellPadding(5);
        addMember(form);
	}
	
	public void setDataSource(DataSource source) {
		form.setDataSource(source);
    }
	
	public DynamicForm getForm() {
		return form;
    }
}
