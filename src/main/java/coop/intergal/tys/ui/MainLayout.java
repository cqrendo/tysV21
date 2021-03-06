package coop.intergal.tys.ui;

import static coop.intergal.AppConst.DEFAULT_API_NAME;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PWA;
//import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.tys.ui.components.FlexBoxLayout;
import coop.intergal.tys.ui.components.navigation.bar.AppBar;
import coop.intergal.tys.ui.components.navigation.bar.TabBar;
import coop.intergal.tys.ui.components.navigation.drawer.NaviDrawer;
import coop.intergal.tys.ui.components.navigation.drawer.NaviItem;
import coop.intergal.tys.ui.components.navigation.drawer.NaviMenu;
import coop.intergal.tys.ui.util.LumoStyles;
import coop.intergal.tys.ui.util.UIUtils;
import coop.intergal.tys.ui.util.css.Overflow;
import coop.intergal.tys.ui.views.Home;
import coop.intergal.ui.security.ldap.LdapClient;
import coop.intergal.ui.util.UtilSessionData;
import coop.intergal.ui.views.DynamicGridDisplay;
import coop.intergal.ui.views.DynamicQryGrid;
import coop.intergal.ui.views.DynamicQryGridDisplay;
import coop.intergal.ui.views.DynamicTreeDisplay;
import coop.intergal.vaadin.rest.utils.DataService;

@CssImport(value = "./styles/components/charts.css", themeFor = "vaadin-chart", include = "vaadin-chart-default-theme")
@CssImport(value = "./styles/components/floating-action-button.css", themeFor = "vaadin-button")
@CssImport(value = "./styles/components/grid.css", themeFor = "vaadin-grid")
@CssImport("./styles/lumo/border-radius.css")
@CssImport("./styles/lumo/icon-size.css")
@CssImport("./styles/lumo/margin.css")
@CssImport("./styles/lumo/padding.css")
@CssImport("./styles/lumo/shadow.css")
@CssImport("./styles/lumo/spacing.css")
@CssImport("./styles/lumo/typography.css")
@CssImport("./styles/misc/box-shadow-borders.css")
@CssImport(value = "./styles/styles.css", include = "lumo-badge")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
@JsModule("@vaadin/vaadin-lumo-styles/badge")
@PWA(name = "tys", shortName = "tys", iconPath = "images/logo-18.png", backgroundColor = "#233348", themeColor = "#233348")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")

public class MainLayout extends FlexBoxLayout
        implements RouterLayout, PageConfigurator, BeforeEnterObserver, AfterNavigationObserver {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(MainLayout.class);
    private static final String CLASS_NAME = "root";

    private Div appHeaderOuter;

    private FlexBoxLayout row;
    private NaviDrawer naviDrawer;
    private FlexBoxLayout column;

    private Div appHeaderInner;
    private FlexBoxLayout viewContainer;
    private Div appFooterInner;

    private Div appFooterOuter;

    private TabBar tabBar;
    private boolean navigationTabs = false;
    private AppBar appBar;
	private String apiname;
	private String cache;
	private NaviItem submenu;
	private Label titleLogo = new Label("Empresa sin escoger");


    public Label getTitleLogo() {
		return titleLogo;
	}

	public void setTitleLogo(String titleLogo) {
		
		this.titleLogo.setText(titleLogo);
	}

	@SuppressWarnings("unchecked")
	public MainLayout() {
		this.setId("MainLayout");
		titleLogo.addClassName(LumoStyles.Heading.H3);
        VaadinSession.getCurrent()
                .setErrorHandler((ErrorHandler) errorEvent -> {
                    log.error("Uncaught UI exception",
                            errorEvent.getThrowable());
                    Notification.show(
                            "We are sorry, but an internal error occurred");
                });
      	RouteConfiguration configuration = RouteConfiguration.forSessionScope();
    	if (AppConst.INCLUDE_MAIN_LAYOUT) {
    		if (configuration.getRoute(AppConst.PAGE_DYNAMIC_QGD).isPresent() == false)
    			{
					configuration.setRoute(AppConst.PAGE_DYNAMIC_QGD, DynamicQryGridDisplay.class,
							this.getClass());	
			//				Class.forName(AppConst.MAIN_LAYOUT_CLASS).asSubclass(RouterLayout.class));
				// 			MainLayout.class);
    			}
    		if (configuration.getRoute(AppConst.PAGE_DYNAMIC_QG).isPresent() == false)
			{
				configuration.setRoute(AppConst.PAGE_DYNAMIC_QG, DynamicGridDisplay.class,
						this.getClass());	
		//				Class.forName(AppConst.MAIN_LAYOUT_CLASS).asSubclass(RouterLayout.class));
			// 			MainLayout.class);
			}
    		if (configuration.getRoute(AppConst.PAGE_DYNAMIC_GD).isPresent() == false)
			{
				configuration.setRoute(AppConst.PAGE_DYNAMIC_GD, DynamicGridDisplay.class,
						this.getClass());	
		//				Class.forName(AppConst.MAIN_LAYOUT_CLASS).asSubclass(RouterLayout.class));
			// 			MainLayout.class);
			}

    	} else {
    		if (configuration.getRoute(AppConst.PAGE_DYNAMIC_QGD).isPresent() == false)
    			configuration.setRoute(AppConst.PAGE_DYNAMIC_QGD, DynamicQryGridDisplay.class);
    		if (configuration.getRoute(AppConst.PAGE_DYNAMIC_QG).isPresent() == false)
    			configuration.setRoute(AppConst.PAGE_DYNAMIC_QG, DynamicQryGrid.class);
      		if (configuration.getRoute(AppConst.PAGE_DYNAMIC_GD).isPresent() == false)
    			configuration.setRoute(AppConst.PAGE_DYNAMIC_GD, DynamicGridDisplay.class);
 
    	}
          addClassName(CLASS_NAME);
        setFlexDirection(FlexDirection.COLUMN);
        setSizeFull();

        // Initialise the UI building blocks
        initStructure();

        // Populate the navigation drawer
 //       initNaviItems();

        // Configure the headers and footers (optional)
        initHeadersAndFooters();
    }

    /**
     * Initialise the required components and containers.
     */
    public void initStructure() {
 //       Label title = new Label("cc");
		naviDrawer = new NaviDrawer(titleLogo);
 //       naviDrawer.removeAll();

        viewContainer = new FlexBoxLayout();
        viewContainer.addClassName(CLASS_NAME + "__view-container");
        viewContainer.setOverflow(Overflow.HIDDEN);

        column = new FlexBoxLayout(viewContainer);
        column.addClassName(CLASS_NAME + "__column");
        column.setFlexDirection(FlexDirection.COLUMN);
        column.setFlexGrow(1, viewContainer);
        column.setOverflow(Overflow.HIDDEN);

        row = new FlexBoxLayout(naviDrawer, column);
        row.addClassName(CLASS_NAME + "__row");
        row.setFlexGrow(1, column);
        row.setOverflow(Overflow.HIDDEN);
        add(row);
        setFlexGrow(1, row);
    }

    /**
     * Initialise the navigation items.
     */
    public void initNaviItems() {
        NaviMenu menu = naviDrawer.getMenu();
        menu.removeAll();
		try {
			JsonNode rowsList = JSonClient.get("menu","APIname='"+DEFAULT_API_NAME+"'%20AND%20application='GFER'%20AND%20isFKidMenu%20is%20null",false,AppConst.PRE_CONF_PARAM_METADATA,500+"");
			for (JsonNode eachRow : rowsList)  {
				String optionName = eachRow.get("optionName").asText();
				String resource = eachRow.get("resource").asText();
				String params = eachRow.get("params").asText();
				String queryFormClassName = eachRow.get("queryFormClassName").asText();
				String displayFormClassName = eachRow.get("displayFormClassName").asText();
				String gridClassName = eachRow.get("gridClassName").asText();
				String idMenu00 = eachRow.get("idMenu").asText();
				String esTab = eachRow.get("esTab").asText();
				int countSubMenus= eachRow.get("countSubmenus").asInt();
				if (countSubMenus > 0)
				{
					 
			   		Map<String,List<String>> parameters0 = new HashMap<>();
					parameters0.put("resource", Collections.singletonList(resource));
					parameters0.put("resourceName", Collections.singletonList(resource));
					parameters0.put("title", Collections.singletonList(optionName));
					parameters0.put("params", Collections.singletonList(params));
					parameters0.put("queryFormClassName" , Collections.singletonList(queryFormClassName));
					parameters0.put("displayFormClassName" , Collections.singletonList(displayFormClassName));
					parameters0.put("gridClassName" , Collections.singletonList(gridClassName));
					
					parameters0.put("filter",  Collections.singletonList("isFKidMenuEEQQ"+idMenu00));  // ("&filter=isFKidMenuEEQQ"oinly when calls to idMenu @@ TODO apply filter is exist to all options
 
					if (esTab.equals("1")) {
						 submenu = menu.addNaviItem(VaadinIcon.ACCORDION_MENU, optionName,
					                SubMenu.class, parameters0);
					}
					else {
						 submenu = menu.addNaviItem(VaadinIcon.ACCORDION_MENU, optionName,
					                SubSubmenu.class, parameters0);
					}

//					 submenu.addClickListener(e->(System.out.println("MainLayout.initNaviItems()")));
					
					 String idMenu = eachRow.get("idMenu").asText();
					 JsonNode rowsList1 = JSonClient.get("menu","isFKidMenu="+idMenu,false,AppConst.PRE_CONF_PARAM_METADATA,500+"");
					 for (JsonNode eachRow1 : rowsList1)  {
							String optionName1 = eachRow1.get("optionName").asText();
							String resource1 = eachRow1.get("resource").asText();
							String params1 = eachRow1.get("params").asText();
							String queryFormClassName1 = eachRow1.get("queryFormClassName").asText();
							String displayFormClassName1 = eachRow1.get("displayFormClassName").asText();
							String gridClassName1 = eachRow1.get("gridClassName").asText();
							

							int countSubMenus1= eachRow1.get("countSubmenus").asInt();
							if (countSubMenus1 > 0)
							{
								 NaviItem submenu1 = menu.addNaviItem(submenu, optionName1,
							                null);
						//		 submenu1.
								 String idMenu1 = eachRow1.get("idMenu").asText();
								 JsonNode rowsList2 = JSonClient.get("menu","isFKidMenu="+idMenu1,false,AppConst.PRE_CONF_PARAM_METADATA,500+"");
								 for (JsonNode eachRow2 : rowsList2)  {
										String optionName2 = eachRow2.get("optionName").asText();
										String resource2 = eachRow2.get("resource").asText();
										String params2 = eachRow2.get("params").asText();
										String queryFormClassName2 = eachRow2.get("queryFormClassName").asText();
										String displayFormClassName2 = eachRow2.get("displayFormClassName").asText();
										String gridClassName2 = eachRow2.get("gridClassName").asText();
										
										int countSubMenus2= eachRow2.get("countSubmenus").asInt();
										if (countSubMenus2 > 0)
										{
											 NaviItem submenu2 = menu.addNaviItem(submenu1, optionName2,
										                null);
											 String idMenu2 = eachRow2.get("idMenu").asText();
											 JsonNode rowsList3 = JSonClient.get("menu","isFKidMenu="+idMenu2,false,AppConst.PRE_CONF_PARAM_METADATA,500+"");
											 for (JsonNode eachRow3 : rowsList3)  {
													String optionName3 = eachRow3.get("optionName").asText();
													String resource3 = eachRow3.get("resource").asText();
													String params3 = eachRow3.get("params").asText();
													String queryFormClassName3 = eachRow3.get("queryFormClassName").asText();
													String displayFormClassName3 = eachRow3.get("displayFormClassName").asText();
													String gridClassName3 = eachRow3.get("gridClassName").asText();
													
													int countSubMenus3= eachRow3.get("countSubmenus").asInt();
													if (countSubMenus3 > 0)
													{
														 NaviItem submenu3 = menu.addNaviItem(submenu2, optionName3,
													                null);
														 String idMenu3 = eachRow3.get("idMenu").asText();
														 JsonNode rowsList4 = JSonClient.get("menu","isFKidMenu="+idMenu3,false,AppConst.PRE_CONF_PARAM_METADATA,500+"");
								//						 xxxx
														 for (JsonNode eachRow4 : rowsList4)  {
																String optionName4 = eachRow3.get("optionName").asText();
																String resource4 = eachRow4.get("resource").asText();
																String params4 = eachRow4.get("params").asText();
																String queryFormClassName4 = eachRow4.get("queryFormClassName").asText();
																String displayFormClassName4 = eachRow4.get("displayFormClassName").asText();
																String gridClassName4 = eachRow4.get("gridClassName").asText();
																
																int countSubMenus4= eachRow3.get("countSubmenus").asInt();
																if (countSubMenus4 > 0)
																{
																	 NaviItem submenu4 = menu.addNaviItem(submenu3, optionName4,
																                null);
																	 String idMenu4 = eachRow4.get("idMenu").asText();
																//	 JsonNode rowsList5 = JSonClient.get("menu","isFKidMenu="+idMenu,false,AppConst.PRE_CONF_PARAM,500+"");
																	 
																	 
																}
																else
																{
															   		Map<String,List<String>> parameters = new HashMap<>();
																	parameters.put("resource", Collections.singletonList(resource4));
																	parameters.put("resourceName", Collections.singletonList(resource4));
																	parameters.put("title", Collections.singletonList(optionName4));
																	parameters.put("params", Collections.singletonList(params4));
																	parameters.put("queryFormClassName" , Collections.singletonList(queryFormClassName4));
																	parameters.put("displayFormClassName" , Collections.singletonList(displayFormClassName4));
																	parameters.put("gridClassName" , Collections.singletonList(gridClassName4));

																	
																	if (cache != null)
																		parameters.put("cache", Collections.singletonList(cache));
																	else
																		parameters.put("cache", Collections.singletonList("true"));
																	menu.addNaviItem(submenu3, optionName4, DynamicQryGridDisplay.class, parameters);
																}
														 
														 }
														 submenu3.setSubItemsVisible(false);
								//						 xxxx
														 
													}
													else
													{
												   		Map<String,List<String>> parameters = new HashMap<>();
														parameters.put("resourceName", Collections.singletonList(resource3));
														parameters.put("title", Collections.singletonList(optionName3));
														parameters.put("params", Collections.singletonList(params3));
														parameters.put("queryFormClassName" , Collections.singletonList(queryFormClassName3));
														parameters.put("displayFormClassName" , Collections.singletonList(displayFormClassName3));
														parameters.put("gridClassName" , Collections.singletonList(gridClassName3));

														if (cache != null)
															parameters.put("cache", Collections.singletonList(cache));
														else
															parameters.put("cache", Collections.singletonList("true"));
														menu.addNaviItem(submenu2, optionName3, DynamicQryGridDisplay.class, parameters);
													}
											 
											 }
											 submenu2.setSubItemsVisible(false);
											 
										}
										else
										{
											Map<String,List<String>> parameters = new HashMap<>();
											parameters.put("resourceName", Collections.singletonList(resource2));
											parameters.put("title", Collections.singletonList(optionName2));
											parameters.put("params", Collections.singletonList(params2));
											parameters.put("queryFormClassName" , Collections.singletonList(queryFormClassName2));
											parameters.put("displayFormClassName" , Collections.singletonList(displayFormClassName2));
											parameters.put("gridClassName" , Collections.singletonList(gridClassName2));

											if (cache != null)
												parameters.put("cache", Collections.singletonList(cache));
											menu.addNaviItem(submenu1, optionName2, DynamicQryGridDisplay.class,  parameters);
										}	
								 }
								 submenu1.setSubItemsVisible(false);
							}
							else
							{
								Map<String,List<String>> parameters = new HashMap<>();
								parameters.put("resourceName", Collections.singletonList(resource1));
								parameters.put("title", Collections.singletonList(optionName1));
								parameters.put("params", Collections.singletonList(params1));
								parameters.put("queryFormClassName" , Collections.singletonList(queryFormClassName1));
								parameters.put("displayFormClassName" , Collections.singletonList(displayFormClassName1));
								parameters.put("gridClassName" , Collections.singletonList(gridClassName1));

								if (cache != null)
									parameters.put("cache", Collections.singletonList(cache));
								menu.addNaviItem(submenu, optionName1, DynamicQryGridDisplay.class, parameters);
							}
					 
					 }
					 submenu.setSubItemsVisible(false);
				}
				else
				{
					Map<String,List<String>> parameters = new HashMap<>();
					parameters.put("resourceName", Collections.singletonList(resource));
					parameters.put("title", Collections.singletonList(optionName));
					parameters.put("params", Collections.singletonList(params));
					parameters.put("queryFormClassName" , Collections.singletonList(queryFormClassName));
					parameters.put("displayFormClassName" , Collections.singletonList(displayFormClassName));
					parameters.put("gridClassName" , Collections.singletonList(gridClassName));

					if (cache != null)
						parameters.put("cache", Collections.singletonList(cache));
					menu.addNaviItem(VaadinIcon.CIRCLE, optionName, DynamicQryGridDisplay.class, parameters); 
				}
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        menu.addNaviItem(VaadinIcon.HOME, "Home", Home.class);
//        menu.addNaviItem(VaadinIcon.INSTITUTION, "Accounts", GenericGrid.class);
//        menu.addNaviItem(VaadinIcon.CREDIT_CARD, "Payments", GenericGridDetails.class);
//        menu.addNaviItem(VaadinIcon.CHART, "Statistics", Statistics.class);
//
//        NaviItem personnel = menu.addNaviItem(VaadinIcon.USERS, "Personnel",
//                null);
//        menu.addNaviItem(personnel, "Accountants", Accountants.class);
//        menu.addNaviItem(personnel, "Managers", Managers.class);
    }

//	private void initNaviItemsOld() {
//        NaviMenu menu = naviDrawer.getMenu();
//        menu.removeAll();
//        Map<String,List<String>> parameters = new HashMap<>();
//		parameters.put("resourceName", Collections.singletonList("CR-FormTemplate"));
//		parameters.put("title", Collections.singletonList("Compras y Ventas"));
//		parameters.put("queryFormClassName", Collections.singletonList("dev.lac.FormTemplateQuery"));
//		parameters.put("displayFormClassName", Collections.singletonList("dev.lac.FormTemplateForm"));
//		parameters.put("apiname", Collections.singletonList(apiname));
//		parameters.put("filter",  Collections.singletonList("&filter=isFKidMenuEEQQ1"));
////		parameters.put("inlinelimit",  Collections.singletonList("10240")); // to bring big (100k)text string in result
//		menu.addNaviItem(VaadinIcon.ASTERISK, "Compras y Ventas", SubMenu.class, parameters); 
//        parameters = new HashMap<>();
//		parameters.put("resourceName", Collections.singletonList("CR-menu"));
//		parameters.put("title", Collections.singletonList("Menu"));
//		parameters.put("queryFormClassName", Collections.singletonList("dev.lac.MenuQuery"));
//		parameters.put("displayFormClassName", Collections.singletonList("dev.lac.MenuForm"));
//		parameters.put("apiname", Collections.singletonList(apiname));
//
//		menu.addNaviItem(VaadinIcon.ASTERISK, "Menus", DynamicTreeDisplay.class, parameters); 
//
////		menu.addNaviItem(VaadinIcon.ASTERISK, "Plantillas formularios", FormTemplateQuery.class, parameters); 
//		
//     }

//    private Object onCLickOptionMenu(String resource, String title) {
//    		ComponentUtil.setData(UI.getCurrent(), "resource", resource);
//    		ComponentUtil.setData(UI.getCurrent(), "title", title);
//    		return null;
//		
//    	}

	/**
     * Configure the app's inner and outer headers and footers.
     */
    private void initHeadersAndFooters() {
        // setAppHeaderOuter();
        // setAppFooterInner();
        // setAppFooterOuter();

        // Default inner header setup:
        // - When using tabbed navigation the view title, user avatar and main menu button will appear in the TabBar.
        // - When tabbed navigation is turned off they appear in the AppBar.

        appBar = new AppBar("");

        // Tabbed navigation
        if (navigationTabs) {
            tabBar = new TabBar();
            UIUtils.setTheme(Lumo.DARK, tabBar);

            // Shift-click to add a new tab
            for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
                item.addClickListener(e -> {
                    if (e.getButton() == 0 && e.isShiftKey()) {
                        tabBar.setSelectedTab(tabBar.addClosableTab(item.getText(), item.getNavigationTarget()));
                    }
                });
            }
            appBar.getAvatar().setVisible(false);
            setAppHeaderInner(tabBar, appBar);

        // Default navigation
        } else {
            UIUtils.setTheme(Lumo.DARK, appBar);
            setAppHeaderInner(appBar);
        }
    }

    private void setAppHeaderOuter(Component... components) {
        if (appHeaderOuter == null) {
            appHeaderOuter = new Div();
            appHeaderOuter.addClassName("app-header-outer");
            getElement().insertChild(0, appHeaderOuter.getElement());
        }
        appHeaderOuter.removeAll();
        appHeaderOuter.add(components);
    }

    private void setAppHeaderInner(Component... components) {
        if (appHeaderInner == null) {
            appHeaderInner = new Div();
            appHeaderInner.addClassName("app-header-inner");
            column.getElement().insertChild(0, appHeaderInner.getElement());
        }
        appHeaderInner.removeAll();
        appHeaderInner.add(components);
    }

    private void setAppFooterInner(Component... components) {
        if (appFooterInner == null) {
            appFooterInner = new Div();
            appFooterInner.addClassName("app-footer-inner");
            column.getElement().insertChild(column.getElement().getChildCount(),
                    appFooterInner.getElement());
        }
        appFooterInner.removeAll();
        appFooterInner.add(components);
    }

    private void setAppFooterOuter(Component... components) {
        if (appFooterOuter == null) {
            appFooterOuter = new Div();
            appFooterOuter.addClassName("app-footer-outer");
            getElement().insertChild(getElement().getChildCount(),
                    appFooterOuter.getElement());
        }
        appFooterOuter.removeAll();
        appFooterOuter.add(components);
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addMetaTag("apple-mobile-web-app-capable", "yes");
        settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");

        settings.addFavIcon("icon", "frontend/images/favicons/favicon.ico",
                "256x256");
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.viewContainer.getElement().appendChild(content.getElement());
    }

    public NaviDrawer getNaviDrawer() {
        return naviDrawer;
    }

    public static MainLayout get() {
        return (MainLayout) UI.getCurrent().getChildren()
                .filter(component -> component.getClass() == MainLayout.class)
                .findFirst().get();
    }

    public AppBar getAppBar() {
        return appBar;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

//        if (navigationTabs) {
//            afterNavigationWithTabs(event);
//        } else {
//            afterNavigationWithoutTabs(event);
//        }
    }

//    private void afterNavigationWithTabs(AfterNavigationEvent e) {
//        NaviItem active = getActiveItem(e);
//        if (active == null) {
//            if (tabBar.getTabCount() == 0) {
//                tabBar.addClosableTab("", Home.class);
//            }
//        } else {
//            if (tabBar.getTabCount() > 0) {
//                tabBar.updateSelectedTab(active.getText(),
//                        active.getNavigationTarget());
//            } else {
//                tabBar.addClosableTab(active.getText(),
//                        active.getNavigationTarget());
//            }
//        }
//        appBar.getMenuIcon().setVisible(false);
//    }

//    private NaviItem getActiveItem(AfterNavigationEvent e) {
//        for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
//            if (item.isHighlighted(e)) {
//                return item;
//            }
//        }
//        return null;
//    }

//    private void afterNavigationWithoutTabs(AfterNavigationEvent e) {
//        NaviItem active = getActiveItem(e);
//        if (active != null) {
//            getAppBar().setTitle(active.getText());
//        }
//    }

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		Location location = event.getLocation();
	    QueryParameters queryParameters = location
	            .getQueryParameters();

	    Map<String, List<String>> parametersMap =
	            queryParameters.getParameters();
	    System.out.println("afterNavigation.....parametersMap.get(\"apiname\")"+parametersMap.get("apiname"));
//	    String username = SecurityUtils.getUsername();
//	    String filterMyData = SecurityUtils.getFilterMyData();
//	    System.out.println("MainLayout.beforeEnter() username " + username + " filterMyData " + filterMyData);
//	    CustomUser user = UserRepository.findByEmailIgnoreCase(username);;
//	    System.out.println("UtilSessionData.userHasRole"+UtilSessionData.userHasRole("central"));
//	    System.out.println("UtilSessionData.isMemberOfOu(central)"+UtilSessionData.userIsMemberOfOU("central"));
//	    String[] types = {"admin"}; 
//	    System.out.println("UtilSessionData.userHasAnyOfThisTypes " +UtilSessionData.userHasTypes(types));
//	    try {
//	    	Hashtable<String, String> rolesHT = new Hashtable<String, String>();
//	    	rolesHT.put("employeeType","jefe compras");
//	    	rolesHT.put("organizationName","GFER");
//			LdapClient.createLDAPUser("uid=20user"+AppConst.LDAP_BASE, "admin", rolesHT, "apellido", "nombre");
//			LdapClient.createLDAPUser("uid=user"+AppConst.LDAP_BASE, "admin", rolesHT, "apellido", "nombre" );
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    if ( parametersMap.get("apiname") != null)	    	
	    	apiname = parametersMap.get("apiname").get(0);
	    else
	    	apiname = AppConst.DEFAULT_API_NAME; 
	    if ( parametersMap.get("cache") != null)	    	
	    	cache = parametersMap.get("cache").get(0);
	    else
	    {
	    	if (UtilSessionData.getCache())
	    		cache = "true";
	    	else
	    		cache = "false";
	    }		
	    UtilSessionData.setCache(cache); // initNaviItems() -> is call from Home
        String defaultPRE_CONF_PARAM = AppConst.DEFAULT_COMPANY+AppConst.CURRENT_YEAR+AppConst.PRE_CONF_PARAM;
		String companies = UtilSessionData.getValueFromTableUser("EMPRESA",defaultPRE_CONF_PARAM);
		UtilSessionData.setCompanies(companies);
		if (companies.equals("N/A"))
        {
        	DataService.get().showError("ERROR -> Usuario no autorizado");
        }        
        else if (companies.indexOf(",") == -1) // only one company
        {
            String companyYear = companies+AppConst.CURRENT_YEAR;
            UtilSessionData.setCompanyYear(companyYear);
            UtilSessionData.setCompany(companies);
            setTitleLogo(companyYear);
            initNaviItems();
         }

//	    if (UtilSessionData.getCompanyYear() != null && UtilSessionData.getCompanyYear().isEmpty() == false) // not menu until company is choose
//	    {
//	    	if (naviDrawer.getMenu() == null || naviDrawer.getMenu().getNaviItems().isEmpty())
//	    		initNaviItems();
//	    }	
	}

}
