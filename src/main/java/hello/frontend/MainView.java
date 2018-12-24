package hello.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.theme.lumo.Lumo;
import hello.backend.CustomerRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Route
@Theme(value = Lumo.class)
public class MainView extends VerticalLayout {

    private final Tabs tabSwitch;


    Div createGlobalDiv(TabJavaContent tabJavaContent, TabWebCompContent tabWebCompContent) {
        Div globalDiv = new Div(tabJavaContent, tabWebCompContent);
        return globalDiv;
    }

    Tabs createTabSwitch(Tab forJava, Tab forWeb) {
        return new Tabs(forJava, forWeb);
    }

    Tab createTab(String label, String id) {
        Tab tab = new Tab(label);
        tab.setId(id);
        return tab;
    }

    public MainView( TabJavaContent tabJavaContent, TabWebCompContent tabWebCompContent) {

        Tab tabForJava = createTab("Using Java", "tab-for-java-comp");
        Tab tabForWeb = createTab("Using Web Component", "tab-for-web-comp");
        tabSwitch = createTabSwitch(tabForJava, tabForWeb);
        tabWebCompContent.setVisible(false);
        Div globalDiv = createGlobalDiv(tabJavaContent, tabWebCompContent);
        globalDiv.setWidth("700px");
        globalDiv.setHeight("500px");

        add(tabSwitch, globalDiv);
        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tabForJava, tabJavaContent);
        tabsToPages.put(tabForWeb, tabWebCompContent);

        Set<Component> pagesShown = new HashSet<>();
        pagesShown.add(tabJavaContent);
        tabSwitch.addSelectedChangeListener(createSelectedChangeEventListener(tabsToPages, pagesShown));
    }


    private ComponentEventListener<Tabs.SelectedChangeEvent> createSelectedChangeEventListener(Map<Tab, Component> tabsToPages, Set<Component> pagesShown) {
        return event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabSwitch.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        };
    }


}
