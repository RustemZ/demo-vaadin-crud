package hello;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.DomListenerRegistration;
import com.vaadin.flow.internal.JsonUtils;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

import java.util.List;

@SpringComponent
@UIScope
@Tag("contacts-table")
@HtmlImport("frontend://components/ContactsTable.html")
@StyleSheet("frontend://css/web-comp.css")
public class TabWebCompContent extends HtmlComponent {

    public TabWebCompContent(CustomerRepository repo) {
        updateTableContents(repo);


        DomListenerRegistration registration = getElement().addEventListener("row-selection-updated",
                e -> processEventData(e.getEventData()));
        registration.addEventData( "event.detail");


    }

//    private final SerializableConsumer<String> selectedRowIdProcessor;
//
//    ContactsTable(SerializableConsumer<String> selectedRowIdProcessor) {
//        this.selectedRowIdProcessor = selectedRowIdProcessor;
//
//    }

    void updateTableContents(CustomerRepository repo) {
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

//        final String selectedId;
//        switch (jsonValue.getType()) {
//            case STRING:
//                selectedId = jsonValue.asString();
//                break;
//            case NULL:
//                selectedId = null;
//                break;
//            default:
//                throw new IllegalStateException(
//                        "Unexpected json value: " + jsonValue);
//        }
//        selectedRowIdProcessor.accept(selectedId);
    }


}
