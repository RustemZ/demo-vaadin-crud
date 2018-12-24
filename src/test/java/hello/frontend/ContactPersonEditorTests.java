package hello.frontend;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.then;

import hello.backend.ContactPerson;
import hello.backend.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContactPersonEditorTests {

	private static final String FIRST_NAME = "Marcin";
	private static final String LAST_NAME = "Grzejszczak";

	@Mock
    CustomerRepository customerRepository;
	@InjectMocks
    CustomerEditor editor;
	@Mock
    PubSubUiService changeHandler;


	@Test
	public void shouldStoreCustomerInRepoWhenEditorSaveClicked() {
		emptyCustomerWasSetToForm();

		this.editor.firstName.setValue(FIRST_NAME);
		this.editor.lastName.setValue(LAST_NAME);

		this.editor.save();

		then(this.customerRepository).should().save(argThat(customerMatchesEditorFields()));
	}

	@Test
	public void shouldDeleteCustomerFromRepoWhenEditorDeleteClicked() {
		customerDataWasFilled();

		editor.delete();

		then(this.customerRepository).should().delete(argThat(customerMatchesEditorFields()));
	}

	private void emptyCustomerWasSetToForm() {
		this.editor.editCustomer(new ContactPerson());
	}
	private void customerDataWasFilled() {
		this.editor.editCustomer(new ContactPerson(FIRST_NAME, LAST_NAME));
	}

	private ArgumentMatcher<ContactPerson> customerMatchesEditorFields() {
		return customer -> FIRST_NAME.equals(customer.getFirstName()) && LAST_NAME.equals(customer.getLastName());
	}

}