package hello.frontend;


import com.vaadin.flow.dom.Element;
import elemental.json.JsonArray;
import hello.backend.ContactPerson;
import hello.backend.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TabWebCompContentTests {

    @Mock
    CustomerRepository repo;

    @Mock
    PubSubUiService thePubSubUiService;

    @Mock
    Element internalWebComp;

    private TabWebCompContent tabWebCompContent;

    private void contactEquals(List<ContactPerson> contacts, JsonArray array  ) {
        then(contacts.size()).isEqualTo(array.length());
        for(int i=0; i< contacts.size(); i++){
            String expected = contacts.get(i).toJsonObject().toJson();
            then(array.get(i).toJson()).isEqualTo(expected);
        }
    }


    @Test
    public void creationOfTabWebComp(){
        List<ContactPerson> contacts = new ArrayList<>();
        contacts.add( new ContactPerson("name1", "family1" ) {
            public Long getId() {
                return 1L;
            }
        } );
        contacts.add( new ContactPerson("name2", "family2" ){
            public Long getId() {
                return 2L;
            }
        });

        when(repo.findAll()).thenReturn(contacts);
        when(internalWebComp.setPropertyJson(any(String.class), any(JsonArray.class))).then(
                (InvocationOnMock invocation) -> {
                    JsonArray jsonArray = (JsonArray) (invocation.getArguments()[1]);
                    contactEquals(contacts, jsonArray);
                    return null;
                });

        tabWebCompContent= new TabWebCompContent(repo, thePubSubUiService){
            @Override
            public Element getElement() {
                return internalWebComp;
            }
        };


    }




}
