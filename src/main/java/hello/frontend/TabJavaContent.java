package hello.frontend;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hello.backend.ContactPerson;
import hello.backend.CustomerRepository;
import org.springframework.util.StringUtils;

@SpringComponent
@UIScope
public class TabJavaContent extends Div implements UiListener {

    private final CustomerRepository repo;
    private final CustomerEditor editor;
    private final Grid<ContactPerson> grid;
    private final TextField filter;
    private final Button addNewBtn;

    @Override
    public void contactsUpdated() {
        listCustomers(filter.getValue());
    }

    public TabJavaContent(CustomerRepository repo, CustomerEditor editor, PubSubUiService pubSubUiService) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(ContactPerson.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());
        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);

        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "firstName", "lastName");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter.setPlaceholder("Filter by last name");

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

        // Connect selected ContactPerson to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editCustomer(e.getValue());
        });

        // Instantiate and edit new ContactPerson the new button is clicked
        addNewBtn.addClickListener(e -> editor.editCustomer(new ContactPerson("", "")));

        // Listen changes made by the editor, refresh data from backend
        pubSubUiService.addListener(this);
        listCustomers(null);

    }

    void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }

    public Grid<ContactPerson> getGrid() {
        return grid;
    }
}
