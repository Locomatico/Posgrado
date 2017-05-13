package uni.posgrado.client;

import uni.posgrado.client.ui.BasicVForm;
import uni.posgrado.gwt.LoginGwtRPCDS;

import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

public class LoginLayout extends VLayout {
	final IButton loginButton;
	final BasicVForm form_layout;
	public LoginLayout() {
		this.setHeight(300);
        this.setWidth(300);
        this.setAlign(Alignment.CENTER);
        this.setLayoutAlign(Alignment.CENTER);
        this.setLayoutMargin(10);
        this.setBackgroundColor("#F7F7F7");
        this.setBorder("1px solid #c0c0c0");
        this.setShowShadow(true);
        this.setShadowSoftness(10);
        this.setShadowOffset(5);
        
        final Image logo_layout = new Image("images/logo.jpg");
        logo_layout.setTitle("Sistema Admisión UPCH");
        logo_layout.setHeight("100px");
        logo_layout.setWidth("300px");
     
        form_layout = new BasicVForm(60, "", 800);
		form_layout.getForm().setNumCols(2);
		form_layout.setDataSource(new LoginGwtRPCDS());
		form_layout.setOverflow(Overflow.VISIBLE);
		form_layout.getForm().setIsGroup(false);		
		form_layout.getForm().setWidth(300);
		form_layout.getForm().setCellPadding(5);
		form_layout.getForm().getField("username").setHeight(30);
		form_layout.getForm().getField("username").setWidth(200);
		form_layout.getForm().getField("password").setHeight(30);
		form_layout.getForm().getField("password").setWidth(200);		
		
		loginButton = new IButton("Ingresar");
		loginButton.setShowRollOver(true);
		loginButton.setIcon("icons/logout.png");
		loginButton.setIconOrientation("left");
		loginButton.setShowDownIcon(false);
		loginButton.setWidth(100);
		loginButton.setHeight(30);
		loginButton.setAlign(Alignment.CENTER);
		loginButton.setLayoutAlign(Alignment.CENTER);
		/*loginButton.setTop(100);
		loginButton.setLeft(100);*/
		this.addMember(logo_layout);
		this.addMember(form_layout);
		this.addMember(loginButton);		
		
	}
	
	public IButton getLoginButton() {
		return loginButton;
	}
	
	public DynamicForm getForm() {
		return form_layout.getForm();
	}
}
