package hello;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.dom.DomListenerRegistration;
import com.vaadin.flow.internal.JsonUtils;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

import java.util.List;

@SpringComponent
@UIScope
@Tag("contacts-table")
@HtmlImport("frontend://components/ContactsTable.html")
@StyleSheet("frontend://css/web-comp.css")
public class TabWebCompContent extends HtmlComponent implements UiListener{

    final CustomerRepository repo;

    public TabWebCompContent(CustomerRepository repo, PubSubUiService pubSubUiService) {
        this.repo= repo;

        update();
        DomListenerRegistration registration = getElement().addEventListener("afterChange",
                e -> processEventData(e.getEventData()));
        registration.addEventData( "event.detail");
        pubSubUiService.addListener(this);
    }


    @Override
    public void update() {
        List<Customer> allContacts = repo.findAll();

        JsonArray contacts = JsonUtils.createArray();
        for (int i = 0; i < allContacts.size(); i++) {
            contacts.set(i, allContacts.get(i).toJsonObject());
        }
        getElement().setPropertyJson("contacts", contacts);
    }

    private void processEventData(JsonObject eventData) {
        JsonValue jsonValue = eventData.get("event.detail");
        System.out.println( jsonValue.toJson() );


    }


}
