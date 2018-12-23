package hello;

import elemental.json.Json;
import elemental.json.JsonObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	private Long id;

	private String firstName;

	private String lastName;

	protected Customer() {
	}

	public Customer(String firstName, String lastName) {
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
		jsonContact.put("id", id);
		jsonContact.put("firstName", firstName);
		jsonContact.put("lastName", lastName);
//		jsonContact.put("phoneNumber", phoneNumber);
//		jsonContact.put("email", email);
		return jsonContact;
	}



}
