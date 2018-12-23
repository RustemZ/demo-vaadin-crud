package hello;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Route
@Theme(value = Lumo.class)
public class MainView extends VerticalLayout {

    private final CustomerRepository repo;

//    private final CustomerEditor editor;
//
//    final Grid<Customer> grid;
//
//    final TextField filter;
    final Tab tabForJava;
    final Tab tabForWeb;

    final Tabs tabSwitch;

//    private final Button addNewBtn;

    private void createTabs() {
    }

    Div createGlobalDiv(TabJavaContent tabJavaContent, TabWebCompContent tabWebCompContent) {
        return new Div(tabJavaContent, tabWebCompContent);
    }

    public MainView(CustomerRepository repo, TabJavaContent tabJavaContent, TabWebCompContent tabWebCompContent) {

        this.repo = repo;
//        this.editor = editor;
//        this.grid = new Grid<>(Customer.class);
//        this.filter = new TextField();
//        this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());
//        // build layout
//        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);

        this.tabForJava = new Tab("Using Java");
        tabForJava.setId("tab-for-java-comp");
        this.tabForWeb = new Tab("Using Web Component");
        tabForWeb.setId("tab-for-web-comp");
        this.tabSwitch = new Tabs(tabForJava, tabForWeb);
        //Div divJava = new Div(actions, grid, editor);
        //divJava.setText("For java ");

//        Div div2 = new Div();
//        div2.setText("For web component ");
//        div2.setVisible(false);
        tabWebCompContent.setVisible(false);
        Div globalDiv = createGlobalDiv(tabJavaContent, tabWebCompContent);
        globalDiv.setWidth("700px");
        globalDiv.setHeight("500px");

        add( tabSwitch, globalDiv);
        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tabForJava, tabJavaContent);
        tabsToPages.put(tabForWeb, tabWebCompContent );

        Set<Component> pagesShown = new HashSet<>();
        pagesShown.add(tabJavaContent);
        tabSwitch.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabSwitch.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
    }

    public Tabs getTabSwitch() {
        return tabSwitch;
    }




}
