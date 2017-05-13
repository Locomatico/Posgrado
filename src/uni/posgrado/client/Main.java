package uni.posgrado.client;

import java.util.Arrays;

import uni.posgrado.client.ui.BasicVForm;
import uni.posgrado.gwt.LoginGwtRPCDS;
import uni.posgrado.gwt.PersonaFilterGwtRPCDS;
import uni.posgrado.gwt.RolUsuarioFilterGwtRPCDS;
import uni.posgrado.gwt.UsuarioFilterGwtRPCDS;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.events.SubmitValuesEvent;
import com.smartgwt.client.widgets.form.events.SubmitValuesHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

  
public class Main implements EntryPoint {  
	
		final TabSet topTabSet = new TabSet();
  
    public void onModuleLoad() {
        final VLayout mainLayout = new VLayout();
        mainLayout.setWidth100();  
        mainLayout.setHeight100(); 
        
        final ToolStrip topBar = new ToolStrip();
        topBar.setHeight(50);
        topBar.setWidth100();
        topBar.addSpacer(6);
        ImgButton sgwtHomeButton = new ImgButton();
        sgwtHomeButton.setSrc("logo_ban.png");
        sgwtHomeButton.setWidth(32);
        sgwtHomeButton.setHeight(40);
        sgwtHomeButton.setPrompt("UNI - CTIC");
        sgwtHomeButton.setHoverStyle("interactImageHover");
        sgwtHomeButton.setShowRollOver(false);
        sgwtHomeButton.setShowDownIcon(false);
        sgwtHomeButton.setShowDown(false);
        topBar.addMember(sgwtHomeButton);
        topBar.addSpacer(6);
        Label title = new Label("UNI - CTIC");
        title.setStyleName("sgwtTitle");
        title.setWidth(300);
        topBar.addMember(title);

        topBar.addFill();
        
        final ToolStripButton infoButton = new ToolStripButton();
        infoButton.setTitle("");
        infoButton.setIcon("icons/user.png");

        topBar.addButton(infoButton);
        
        topBar.addSeparator();

        final ToolStripButton logoutButton = new ToolStripButton();
        logoutButton.setTitle("SALIR");
        logoutButton.setIcon("icons/exit.png");

        topBar.addMember(logoutButton);

        topBar.addSpacer(6);
        infoButton.setVisible(false);
        logoutButton.setVisible(false);
        
        final VLayout bodyLayout = new VLayout();
        bodyLayout.setWidth100();
        bodyLayout.setHeight100();
        bodyLayout.setOverflow(Overflow.HIDDEN);
        bodyLayout.setLayoutMargin(5);
        bodyLayout.setLayoutAlign(Alignment.CENTER);
        bodyLayout.setAlign(Alignment.CENTER);
        bodyLayout.setStyleName("bg-front");
        
        final HLayout bodyLoginLayout = new HLayout();
        final HLayout bodyHorizontalLayout = new HLayout();
        final VLayout bodyVerticalLayout = new VLayout();        
        bodyVerticalLayout.setHeight(200);
        bodyVerticalLayout.setWidth(300);
        bodyVerticalLayout.setAlign(Alignment.CENTER);
        bodyVerticalLayout.setLayoutAlign(Alignment.CENTER);
        bodyVerticalLayout.setLayoutMargin(10);        
        bodyVerticalLayout.setBackgroundColor("#F7F7F7");        
        
        bodyLoginLayout.setAlign(Alignment.CENTER);
        bodyLoginLayout.setLayoutAlign(Alignment.RIGHT);
        bodyLoginLayout.setLayoutMargin(10);
        bodyLoginLayout.setMargin(100);
        bodyLoginLayout.setHeight(200);
        bodyLoginLayout.setWidth(300);
        
        bodyHorizontalLayout.setAlign(Alignment.CENTER);
        bodyHorizontalLayout.setLayoutAlign(Alignment.CENTER);
        bodyHorizontalLayout.setLayoutMargin(10); 
        bodyHorizontalLayout.setHeight(200);
        bodyHorizontalLayout.setWidth(300);
        bodyHorizontalLayout.setBackgroundColor("#F7F7F7");
        bodyHorizontalLayout.setBorder("1px solid #c0c0c0");
        bodyHorizontalLayout.setShowShadow(true);
        bodyHorizontalLayout.setShadowSoftness(10);
        bodyHorizontalLayout.setShadowOffset(5);
        
        final Image logo_layout = new Image("images/escudo.png");
        logo_layout.setTitle("UNI - CTIC");
        logo_layout.setHeight("196px");
        logo_layout.setWidth("155px");
     
        final BasicVForm form_layout = new BasicVForm(60, "", 800);
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
		
		
		final IButton loginButton = new IButton("Ingresar");
		loginButton.setShowRollOver(true);
		loginButton.setIcon("icons/logout.png");
		loginButton.setIconOrientation("left");
		loginButton.setShowDownIcon(false);
		loginButton.setWidth(100);
		loginButton.setHeight(30);
		loginButton.setAlign(Alignment.CENTER);
		loginButton.setLayoutAlign(Alignment.CENTER);
		bodyHorizontalLayout.addMember(logo_layout);
		bodyVerticalLayout.addMember(form_layout);
		bodyVerticalLayout.addMember(loginButton);
		bodyHorizontalLayout.addMember(bodyVerticalLayout);
		bodyLoginLayout.addMember(bodyHorizontalLayout);
		bodyLayout.addMember(bodyLoginLayout);
		
		loginButton.setCanFocus(true);
		loginButton.focus();
		form_layout.getForm().getField("username").addKeyPressHandler(new com.smartgwt.client.widgets.form.fields.events.KeyPressHandler() {			
			@Override
			public void onKeyPress(
					com.smartgwt.client.widgets.form.fields.events.KeyPressEvent event) {
				// TODO Auto-generated method stub
				if(event.getKeyName().equals("Enter")) {
					form_layout.getForm().submit();
				}
			}
		});
		
		form_layout.getForm().getField("password").addKeyPressHandler(new com.smartgwt.client.widgets.form.fields.events.KeyPressHandler() {			
			@Override
			public void onKeyPress(
					com.smartgwt.client.widgets.form.fields.events.KeyPressEvent event) {
				// TODO Auto-generated method stub
				if(event.getKeyName().equals("Enter")) {
					form_layout.getForm().submit();
				}
			}
		});
		
		final VLayout layout_in_login = new VLayout();
		//final HLayout layout_in_login = new HLayout();
		
		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub				
				form_layout.getForm().submit();
			}
		});
		
		form_layout.getForm().addSubmitValuesHandler(new SubmitValuesHandler() {			
			@Override
			public void onSubmitValues(SubmitValuesEvent event) {
				// TODO Auto-generated method stub
				if(form_layout.getForm().validate()) {
					loginButton.setDisabled(true);
					DSRequest requestProperties = new DSRequest();
					requestProperties.setOperationType(DSOperationType.ADD);
					form_layout.getForm().saveData(new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
							// TODO Auto-generated method stub
							if(dsResponse.getAttributeAsBoolean("is_login")) {
							    PersonaFilterGwtRPCDS persona = new PersonaFilterGwtRPCDS();
							    Cookies.setCookie("username",form_layout.getForm().getValueAsString("username"));
							    Criteria criteria=new Criteria();
								criteria.addCriteria("email", form_layout.getForm().getValueAsString("username"));
							    persona.fetchData(criteria, new DSCallback() {
									@Override
									public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
										// TODO Auto-generated method stub
										if(dsResponse.getTotalRows() == 1 && dsResponse.getData() != null) {
											RecordList record = dsResponse.getDataAsRecordList();
											Cookies.setCookie("id",record.get(0).getAttributeAsString("idPersona"));
											Cookies.setCookie("name",record.get(0).getAttributeAsString("nombreCompleto"));
											infoButton.setTitle(Cookies.getCookie("name")+" (<b>"+Cookies.getCookie("username")+"</b>)");
											
											UsuarioFilterGwtRPCDS usuario = new UsuarioFilterGwtRPCDS();
										    Criteria criteria=new Criteria();
											criteria.addCriteria("idPersona", record.get(0).getAttributeAsString("idPersona"));
											usuario.fetchData(criteria, new DSCallback() {
												
												@Override
												public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
													// TODO Auto-generated method stub
													
													if(dsResponse.getTotalRows() > 0 && dsResponse.getData() != null) {
													
														RecordList record = dsResponse.getDataAsRecordList();
														
														RolUsuarioFilterGwtRPCDS prf = new RolUsuarioFilterGwtRPCDS();
														
														Integer[] usuarios = new Integer[dsResponse.getTotalRows()];
														int i = 0;
														for(Record rec : record.toArray()){
															usuarios[i] = rec.getAttributeAsInt("idUsuario");
															i++;
														}
														
														Criteria criteria_prf=new Criteria();
														criteria_prf.addCriteria("usuarios", usuarios);
														prf.fetchData(criteria_prf, new DSCallback() {
															
															@Override
															public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
																// TODO Auto-generated method stub
																if(dsResponse.getTotalRows() > 0 && dsResponse.getData() != null) {	
																	
																	RecordList record_prf = dsResponse.getDataAsRecordList();
																	boolean is_user = false;
																	Integer[] roles = new Integer[dsResponse.getTotalRows()];
																	for (int i = 0; i < dsResponse.getTotalRows(); i++) {
																		roles[i] = record_prf.get(i).getAttributeAsInt("idRol");
																		is_user = true;
													                }
																	if(is_user) {
																		Cookies.setCookie("roles", Arrays.toString(roles));
																		layout_in_login.redraw();
																		layout_in_login.addMember(new HomeLayout());
																		infoButton.setVisible(true);
																		logoutButton.setVisible(true);
																		bodyLayout.setStyleName("bg-none");
																		bodyLayout.removeMember(bodyLoginLayout);
																		bodyLayout.addMember(layout_in_login);
																		mainLayout.redraw();
																	} else {
																		SC.warn("Acceso Denegado", "Usted no está autorizado para ingresar. Por favor póngase en contacto con el administrador.");
																		loginButton.setDisabled(false);
																	}														
																} else {
																	SC.warn("Acceso Denegado", "Usted no está autorizado para ingresar. Por favor póngase en contacto con el administrador.");
																	loginButton.setDisabled(false);
																}
															}
														});	
													} else {
														SC.warn("Acceso Denegado", "Usted no está autorizado para ingresar. Por favor póngase en contacto con el administrador.");
														loginButton.setDisabled(false);
													}
													
												}
											});
											
											
																					
										} else {
											SC.warn("Acceso Denegado", "Usted no está autorizado para ingresar. Por favor póngase en contacto con el administrador.");
											loginButton.setDisabled(false);
										}
									}
								});								
							} else {
								SC.warn("Acceso Denegado", "Username u contraseña incorrecto");
								loginButton.setDisabled(false);
							}
						}
					}, requestProperties);					
				}
			}
		});		
		
		logoutButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Cookies.removeCookie("id");
				Cookies.removeCookie("name");
				Cookies.removeCookie("username");
				Cookies.removeCookie("roles");
				infoButton.setVisible(false); 
				logoutButton.setVisible(false);
				layout_in_login.destroy();
				bodyLayout.setStyleName("bg-front");
				bodyLayout.addMember(bodyLoginLayout);
				form_layout.getForm().clearValues();
				loginButton.setDisabled(false);
				mainLayout.redraw();
			}
		});		
        
        mainLayout.addMember(topBar);
        mainLayout.addMember(bodyLayout);        
        mainLayout.draw();
    } 

} 