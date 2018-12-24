package hello.backend;

import elemental.json.Json;
import elemental.json.JsonObject;

import javax.persistence.*;

@Entity
@Table(name = "contact_person")
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    public ContactPerson() {
    }

    public ContactPerson(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("Person [id=%d, firstName='%s', lastName='%s']", id,
                firstName, lastName);
    }

    public JsonObject toJsonObject() {
        JsonObject jsonContact = Json.createObject();
        jsonContact.put("id", getId());
        jsonContact.put("firstName", getFirstName());
        jsonContact.put("lastName", getLastName());
// TODO: add new fields
//		jsonContact.put("phoneNumber", phoneNumber);
//		jsonContact.put("email", email);
        return jsonContact;
    }


}
