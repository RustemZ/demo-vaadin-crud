package hello.frontend;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hello.backend.ContactPerson;
import hello.backend.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in MainView.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX.
 */
@SpringComponent
@UIScope
public class CustomerEditor extends VerticalLayout implements KeyNotifier, UiListener {

    private final CustomerRepository repository;
    private final PubSubUiService thePubSubUiService;

    /**
     * The currently edited contactPerson
     */
    private ContactPerson contactPerson;

    /* Fields to edit properties in ContactPerson entity */
    final TextField firstName = new TextField("First name");
    final TextField lastName = new TextField("Last name");

    /* Action buttons */
    private final Button save = new Button("Save", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Cancel");
    private final Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private final HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private final Binder<ContactPerson> binder = new Binder<>(ContactPerson.class);

    @Autowired
    public CustomerEditor(CustomerRepository repository, PubSubUiService aPubSubUiService) {
        this.repository = repository;
        thePubSubUiService = aPubSubUiService;

        add(firstName, lastName, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCustomer(contactPerson));
        aPubSubUiService.addListener(this);
        setVisible(false);
    }

    @Override
    public void contactsUpdated() {
        setVisible(false);
    }

    void delete() {
        repository.delete(contactPerson);
        //changeHandler.onChange();
        thePubSubUiService.updateAll();
    }

    void save() {
        repository.save(contactPerson);
        //changeHandler.onChange();
        thePubSubUiService.updateAll();
    }


    public final void editCustomer(ContactPerson contactPerson) {
        if (contactPerson == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = contactPerson.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            this.contactPerson = repository.findById(contactPerson.getId()).get();
        } else {
            this.contactPerson = contactPerson;
        }
        cancel.setVisible(persisted);

        // Bind contactPerson properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(this.contactPerson);

        setVisible(true);

        // Focus first name initially
        firstName.focus();
    }

}
