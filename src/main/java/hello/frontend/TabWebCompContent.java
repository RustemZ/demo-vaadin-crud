package hello.frontend;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.internal.JsonUtils;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import elemental.json.JsonArray;
import hello.backend.ContactPerson;
import hello.backend.CustomerRepository;

import java.util.List;

@SpringComponent
@UIScope
@Tag("contacts-table")
@HtmlImport("frontend://components/ContactsTable.html")
@StyleSheet("frontend://css/web-comp.css")
public class TabWebCompContent extends HtmlComponent implements UiListener {

    private final CustomerRepository repo;

    public TabWebCompContent(CustomerRepository repo, PubSubUiService pubSubUiService) {
        this.repo= repo;
        contactsUpdated();
        pubSubUiService.addListener(this);
    }

    @Override
    public void contactsUpdated() {
        List<ContactPerson> allContacts = repo.findAll();

        JsonArray contacts = JsonUtils.createArray();
        for (int i = 0; i < allContacts.size(); i++) {
            contacts.set(i, allContacts.get(i).toJsonObject());
        }
        getElement().setPropertyJson("contacts", contacts);
    }


}
