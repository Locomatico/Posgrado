package uni.posgrado.client;

import java.util.Date;

import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;

import uni.posgrado.gwt.OpcionGwtRPCDS;
import uni.posgrado.shared.model.Opcion;
  
@SuppressWarnings("deprecation")
public class HomeLayout extends HLayout implements HistoryListener {  
	
	private enum Module {
		admi01, admi02, admi03, admi04, admi05, admi06,
		plae01, plae02, plae03, plae04, plae05, plae06, plae07, plae08, plae09,
		ofac01, ofac02, ofac03, ofac04, ofac05, ofac06, ofac07, ofac08,
		gper01, gper02, gper03,
		gsis01, gsis02, gsis03, gsis04, gsis05, gsis06, gsis07, gsis08, gsis09, gsis10, gsis11, gsis12;
	}

	final TabSet topTabSet = new TabSet();
	final String initToken = History.getToken();
	protected void onInit() {
        super.onInit();
        if (initToken.length() != 0) {
            onHistoryChanged(initToken);
        }
    } 
  
	public HomeLayout() {
        this.setWidth100();
        this.setHeight100();
        VLayout menuBarLayout = new VLayout();
        
        ToolStrip menuBarTop = new ToolStrip();
        menuBarTop.setHeight(25);
        menuBarTop.setWidth100();
        menuBarTop.addSpacer(6); 
        Label menuTitle = new Label("Menú");
        menuTitle.setWidth100();
        menuBarTop.addMember(menuTitle);
        ToolStrip menuBarBottom = new ToolStrip();
        menuBarBottom.setHeight(40);
        menuBarBottom.setWidth100();
        menuBarBottom.addSpacer(6);
        Date fecha = new Date();
        Label menuInfo = new Label("Versión 1.0<br>"+fecha.toString());
        menuInfo.setWidth100();
        menuBarBottom.addMember(menuInfo);
        
        final ListGrid listGridMenu = new ListGrid();
        final OpcionGwtRPCDS modulo = new OpcionGwtRPCDS();
        listGridMenu.setDataSource(modulo);
        listGridMenu.setCanTabToHeader(true);
        listGridMenu.setShowHeader(false);
        listGridMenu.setAllowFilterExpressions(false);
        listGridMenu.setAutoFetchData(true);
        listGridMenu.setShowFilterEditor(true);
        listGridMenu.setFilterOnKeypress(false);
        listGridMenu.setCanEdit(false);
        listGridMenu.setAutoSaveEdits(false);
        listGridMenu.setDataFetchMode(FetchMode.BASIC);
        listGridMenu.setShowAllRecords(true);
        
        listGridMenu.setGroupStartOpen("all");
        listGridMenu.setCanCollapseGroup(false);
        listGridMenu.setBackgroundColor("#FFFFFF");      

        menuBarLayout.addMember(menuBarTop);
        menuBarLayout.addMember(listGridMenu);
        menuBarLayout.addMember(menuBarBottom);
        menuBarLayout.setShowResizeBar(true);
        menuBarLayout.setMaxWidth(350);
        menuBarLayout.setMinWidth(180);
        this.addMember(menuBarLayout);

        topTabSet.setTabBarPosition(Side.TOP);
        topTabSet.setWidth("85%");
        topTabSet.setBackgroundColor("#FFFFFF");

        Tab tTab1 = new Tab("<span>" + Canvas.imgHTML("icons/headerIcon.png") + " Inicio</span>");
        tTab1.setID("main");
        HLayout vphome = new HLayout();
        vphome.setWidth100();
        vphome.setHeight100();
        vphome.setAlign(Alignment.CENTER);
        Img logo = new Img("logo_ecp.png", 933, 260);
        logo.setAlign(Alignment.CENTER);
        logo.setLayoutAlign(Alignment.CENTER);
        vphome.addMember(logo);
        tTab1.setPane(vphome);
        topTabSet.addTab(tTab1);         
        this.addMember(topTabSet);
  
        this.addDrawHandler(new DrawHandler() {			
			@Override
			public void onDraw(DrawEvent event) {
				// TODO Auto-generated method stub				
				if(listGridMenu.isDrawn()) {
		        	listGridMenu.getField("codigo").setWidth(50);
		        	listGridMenu.getField("icon").setWidth(20);
		        	listGridMenu.groupBy("entorno");		        	
		        }
			}
		});       
        
        
     // Add history listener
        History.addHistoryListener(this);        
       
        final Menu contextMenu = createContextMenu();
        topTabSet.addShowContextMenuHandler(new ShowContextMenuHandler() {
            public void onShowContextMenu(ShowContextMenuEvent event) {
                int selectedTab = topTabSet.getSelectedTabNumber();
                if (selectedTab != 0) {
                    contextMenu.showContextMenu();
                }
                event.cancel();
            }
        });

        topTabSet.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				selectTab(topTabSet.getSelectedTabNumber());
			}
		});
        
        listGridMenu.addRecordClickHandler(new RecordClickHandler() {
            @SuppressWarnings("static-access")
			public void onRecordClick(RecordClickEvent event) {
            	Opcion record = new Opcion();
            	modulo.copyValues(event.getRecord(), record);
            	Boolean state = false;
            	for (int i = 0; i < topTabSet.getTabs().length; i++) {
            		if(topTabSet.getTab(i).getID().equals(record.getCodigo())) {
            			state = true;
            			break;
            		}
            	}
            	
            	if(state == false)  {            		
            		if ( topTabSet.getTabs().length <= 5 ) {
	                	Tab tab = new Tab("<span>" + Canvas.imgHTML("icons/headerIcon.png") + " " + record.getNombre() + "</span>");
	                    tab.setCanClose(true);
	                    tab.setID(record.getCodigo());
	                    
	                    VLayout contentLayout = new VLayout(); 
	                    contentLayout.setWidth100();
	                    contentLayout.setHeight100();

	                    Module type = Module.valueOf(record.getCodigo());
	                    
	                    Canvas modLayout = null;
	                    switch (type) {
	                    	case admi01:
	                    		modLayout = new ModalidadLayout();
	                    		//modLayout = new CalendarLayout();
	                    		break;
	                    	case admi02:
	                    		modLayout = new ConvocatoriaLayout();
	                    		break;
	                    	case admi03:
	                    		modLayout = new TipoEvalLayout();
	                    		break;
	                    	case admi04:
	                    		modLayout = new RequisitoLayout();
	                    		break;
	                    	case admi05:
	                    		modLayout = new EvaluacionLayout();
	                    		break;
	                    	case admi06:
	                    		modLayout = new PostulacionLayout();
	                    		break;
							case plae01:
								modLayout = new RegulacionLayout();
								break;
							case plae02:
								modLayout = new CursoLayout();
								break;
							case plae03:
								modLayout = new UnidadLayout();
								break;
							case plae04:
								modLayout = new ProgramaLayout();
								break;
							case plae05:
								modLayout = new PlanEstudioLayout();
								break;
							case plae06:
								modLayout = new RolLayout();
								break;
							case plae07:
								modLayout = new MallaLayout();
								break;
							case plae08:
								modLayout = new RequisitoCursoLayout();
								break;
							case plae09:
								modLayout = new RolLayout();
								break;
							case ofac01:
								modLayout = new PeriodoLayout();
								break;
							case ofac02:
								modLayout = new PeriodoProgramaLayout();
								break;
							case ofac03:
								modLayout = new PeriodoActividadLayout();
								break;
							case ofac04:
								modLayout = new SeccionLayout();
								break;
							case ofac05:
								modLayout = new SeccionFechaLayout();
								break;
							case ofac06:
								modLayout = new VinculoDocenteLayout();
								break;
							case ofac07:
								modLayout = new SeccionDocenteLayout();
								break;
							case ofac08:
								modLayout = new SeccionHorarioLayout();
								break;
							case gper01:
								modLayout = new PersonaLayout();
								break;
							case gper02:
								modLayout = new RolUsuarioLayout();
								break;
							case gper03:
								modLayout = new UsuarioLayout();
								break;
							case gsis01:
								modLayout = new VariableLayout();
								break;
							case gsis02:
								modLayout = new RolLayout();
								break;
							case gsis03:
								modLayout = new InstitucionLayout();
								break;
							case gsis04:
								modLayout = new ActividadLayout();
								break;
							case gsis05:
								modLayout = new LocalLayout();//LocalImplementadoLayout();
								break;
							case gsis06:
								modLayout = new SuneduLayout();
								break;
							case gsis07:
								modLayout = new PabellonLayout();//PabellonImplementadoLayout();
								break;
							case gsis08:
								modLayout = new PisoLayout();//PisoImplementadoLayout();
								break;
							case gsis09:
								modLayout = new EspacioLayout();//EspacioImplementadoLayout();
								break;
							case gsis10:
								modLayout = new RecursoLayout();
								break;
							case gsis11:
								modLayout = new ImplementacionLayout();//EspacioImplementadoLayout();
								break;
							case gsis12:
								modLayout = new ImplementaEspacioLayout();//EspacioImplementadoLayout();
								break;
						default:
							break;
						}	    
	                    contentLayout.setOverflow(Overflow.AUTO);
	                    modLayout.setRedrawOnResize(true);
	                    contentLayout.addMember(modLayout);
	                    
	                    SectionStack bottomSideLayout = new SectionStack();
	                    bottomSideLayout.setHeight("100");
	                    bottomSideLayout.setVisibilityMode(VisibilityMode.MUTEX);  
	                    bottomSideLayout.setAnimateSections(true);
	                    
	                    HTMLFlow htmlFlow = new HTMLFlow();
	                    htmlFlow.setOverflow(Overflow.AUTO);
	                    htmlFlow.setPadding(10);
	                    htmlFlow.setContents("Agregar texto descriptivo");
	                   
	                    SectionStackSection itemDetailsSection = new SectionStackSection("&nbsp;&nbsp;&nbsp;Información");  
	                    itemDetailsSection.addItem(htmlFlow); 
	                    itemDetailsSection.setExpanded(true); 
	                    itemDetailsSection.setCanCollapse(false);
	                    bottomSideLayout.setSections(itemDetailsSection);
	                    contentLayout.addMember(bottomSideLayout);
	                    tab.setPane(contentLayout);
	                    topTabSet.addTab(tab);
	                    topTabSet.redraw();
	                    topTabSet.selectTab(record.getCodigo());
	                    History.newItem(record.getCodigo(), false);
	                } else {
	                	SC.warn("Aviso", "Excede el máximo de 5 actividades abiertas");
	                }
            	} else {
            		topTabSet.selectTab(record.getCodigo());
            	}	                
            }
        }); 
    }
    
    public void onHistoryChanged(String historyToken) {
        if (historyToken == null || historyToken.equals("")) {
        	topTabSet.selectTab(0);
        }
    }
    
    private void selectTab(int id) {
    	History.newItem(topTabSet.getSelectedTab().getID(), false);
    }
    
    private Menu createContextMenu() {
        Menu menu = new Menu();
        menu.setWidth(140);

        MenuItemIfFunction enableCondition = new MenuItemIfFunction() {
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                int selectedTab = topTabSet.getSelectedTabNumber();
                return selectedTab != 0;
            }
        };

        MenuItem closeItem = new MenuItem("<u>C</u>errar");
        closeItem.setEnableIfCondition(enableCondition);
        closeItem.setKeyTitle("Alt+C");
        KeyIdentifier closeKey = new KeyIdentifier();
        closeKey.setAltKey(true);
        closeKey.setKeyName("C");
        closeItem.setKeys(closeKey);
        closeItem.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
                int selectedTab = topTabSet.getSelectedTabNumber();
                topTabSet.removeTab(selectedTab);
                topTabSet.selectTab(selectedTab - 1);
            }
        });

        MenuItem closeAllButCurrent = new MenuItem("Cerrar todos menos actual");
        closeAllButCurrent.setEnableIfCondition(enableCondition);
        closeAllButCurrent.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
                int selected = topTabSet.getSelectedTabNumber();
                Tab[] tabs = topTabSet.getTabs();
                int[] tabsToRemove = new int[tabs.length - 2];
                int cnt = 0;
                for (int i = 1; i < tabs.length; i++) {
                    if (i != selected) {
                        tabsToRemove[cnt] = i;
                        cnt++;
                    }
                }
                topTabSet.removeTabs(tabsToRemove);
            }
        });

        MenuItem closeAll = new MenuItem("Cerrar todos");
        closeAll.setEnableIfCondition(enableCondition);
        closeAll.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
                Tab[] tabs = topTabSet.getTabs();
                int[] tabsToRemove = new int[tabs.length - 1];

                for (int i = 1; i < tabs.length; i++) {
                    tabsToRemove[i - 1] = i;
                }
                topTabSet.removeTabs(tabsToRemove);
                topTabSet.selectTab(0);
            }
        });

        menu.setItems(closeItem, closeAllButCurrent, closeAll);
        return menu;
    } 
}  