package hello.frontend;

import com.vaadin.flow.data.provider.ListDataProvider;
import hello.backend.ContactPerson;
import hello.backend.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.*;
import static org.mockito.Mockito.doAnswer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TabJavaContentTests.Config.class},
		properties = "spring.datasource.generate-unique-name=true")
public class TabJavaContentTests  {

    @Autowired
	CustomerRepository repository;

	//@Autowired
	PubSubUiService pubSubUiService = Mockito.mock(PubSubUiService.class); // new PubSubUiService() {
//		@Override
//		public synchronized void updateAll() {
//			editor.contactsUpdated();
//		}
//	};

    CustomerEditor editor;

    TabJavaContent tabJavaContent;

	@Before
	public void setup() {
		this.editor = new CustomerEditor(this.repository, pubSubUiService);
		this.tabJavaContent = new TabJavaContent(this.repository, editor, pubSubUiService);
	}

	@Test
	public void shouldInitializeTheGridWithCustomerRepositoryData() {
		int customerCount = (int) this.repository.count();

		then(tabJavaContent.getGrid().getColumns()).hasSize(3);
		then(getCustomersInGrid()).hasSize(customerCount);
	}

	private List<ContactPerson> getCustomersInGrid() {
		ListDataProvider<ContactPerson> ldp = (ListDataProvider) tabJavaContent.getGrid().getDataProvider();
		return new ArrayList<>(ldp.getItems());
	}

	@Test
	public void shouldFillOutTheGridWithNewData() {
		int initialCustomerCount = (int) this.repository.count();
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				tabJavaContent.contactsUpdated();
				return null;
			}
		}).when(pubSubUiService).updateAll();
//		when(pubSubUiService.updateAll()).thenAnswer(
//				(InvocationOnMock invocation) -> {
//					this.editor.contactsUpdated();
//				});

		customerDataWasFilled(editor, "Marcin", "Grzejszczak");

		this.editor.save();


		then(getCustomersInGrid()).hasSize(initialCustomerCount + 1);

		then(getCustomersInGrid().get(getCustomersInGrid().size() - 1))
			.extracting("firstName", "lastName")
			.containsExactly("Marcin", "Grzejszczak");

	}

	@Test
	public void shouldFilterOutTheGridWithTheProvidedLastName() {

		this.repository.save(new ContactPerson("Josh", "Long"));

		tabJavaContent.listCustomers("Long");

		then(getCustomersInGrid()).hasSize(1);
		then(getCustomersInGrid().get(getCustomersInGrid().size() - 1))
			.extracting("firstName", "lastName")
			.containsExactly("Josh", "Long");
	}

	@Test
	public void shouldInitializeWithInvisibleEditor() {

		then(this.editor.isVisible()).isFalse();
	}

	@Test
	public void shouldMakeEditorVisible() {
		ContactPerson first = getCustomersInGrid().get(0);
		this.tabJavaContent.getGrid().select(first);

		then(this.editor.isVisible()).isTrue();
	}

	private void customerDataWasFilled(CustomerEditor editor, String firstName,
			String lastName) {
		this.editor.firstName.setValue(firstName);
		this.editor.lastName.setValue(lastName);
		editor.editCustomer(new ContactPerson(firstName, lastName));
	}


    @Configuration
    @EnableAutoConfiguration(exclude = com.vaadin.flow.spring.SpringBootAutoConfiguration.class)
	@EnableJpaRepositories("hello.backend")
	@EntityScan("hello.backend")
    static class Config {

    }

}
