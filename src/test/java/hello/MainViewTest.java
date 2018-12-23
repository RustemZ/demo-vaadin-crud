package hello;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.testbench.TestBenchTestCase;
import elemental.html.ButtonElement;
import org.junit.After;
import org.junit.Assert;
import org.mockito.Mock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.*;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MainViewTests.Config.class, properties = "spring.datasource.generate-unique-name=true")
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = Application.class)
public class MainViewTest extends TestBenchTestCase{

//	@Before
//	public void setup() {
//		// Create a new browser instance
//		setDriver(new ChromeDriver());
//		// Open the application
//		getDriver().get("http://localhost:8080/");
//	}
//
//	@After
//	public void tearDown()  {
//		// close the browser instance when all tests are done
//		getDriver().quit();
//	}

//	@Test
//	public void clickButton() {
//
//		// Find the first button (<vaadin-button>) on the page
//		Tab tab1 = (Tab) findElement(By.id("tab-for-java-comp"));
//		//Tab tab1 = $(Tab.class).id("tab-for-java-comp");
//		tab1.setSelected(true);
//		//Tab tab2 = $(Tab.class).id("tab-for-web-comp");
//		Tab tab2 = (Tab) findElement(By.id("tab-for-web-comp"));
//		//tab2.setSelected(true);
//
//		// Click it
//		//button.click();
//
//		// Check the the value of the button is "Clicked"
//		Assert.assertEquals(true, tab2.isVisible());
//	}
// 	@Autowired
//	CustomerRepository repository;
 	@Mock
	TabJavaContent javaComp;
	@Mock
	TabWebCompContent webComp;
	@Mock
	Div globalDiv;

	MainView mainView;

	@Test
	public void dump() {
		mainView.getTabSwitch().setSelectedTab( mainView.tabForJava );
		//mainView.getTabSwitch().setSelectedIndex(0);
		verify(webComp).setVisible(false);
		//verify(javaComp).setVisible(true);

	}

	@Test
	public void dump2() {
		mainView.getTabSwitch().setSelectedTab( mainView.tabForWeb );
		//mainView.getTabSwitch().setSelectedIndex(0);
		verify(javaComp).setVisible(false);
		//verify(javaComp).setVisible(true);

	}


	@Before
	public void setup() {
		this.mainView = new MainView(null, javaComp, webComp){
			@Override
			Div createGlobalDiv(TabJavaContent tabJavaContent, TabWebCompContent tabWebCompContent) {
				return globalDiv;
			}

			@Override
			public void add(Component... components) {
				super.add(components[0]);
				then(components).hasSize(2);
				then(components).contains(globalDiv);
			}
			//			@Override
//			public void add(Component... components) {
//				;
//				then(components).hasSize(2);
//				then(components).contains(globalDiv);
//			}
		};
		verify(webComp).setVisible(false);
	}


//
//	@Test
//	public void shouldInitializeTheGridWithCustomerRepositoryData() {
//		int customerCount = (int) this.repository.count();
//
//		then(mainView.grid.getColumns()).hasSize(3);
//		then(getCustomersInGrid()).hasSize(customerCount);
//	}
//
//	private List<Customer> getCustomersInGrid() {
//		ListDataProvider<Customer> ldp = (ListDataProvider) mainView.grid.getDataProvider();
//		return new ArrayList<>(ldp.getItems());
//	}
//
//	@Test
//	public void shouldFillOutTheGridWithNewData() {
//		int initialCustomerCount = (int) this.repository.count();
//
//		customerDataWasFilled(editor, "Marcin", "Grzejszczak");
//
//		this.editor.save();
//
//		then(getCustomersInGrid()).hasSize(initialCustomerCount + 1);
//
//		then(getCustomersInGrid().get(getCustomersInGrid().size() - 1))
//			.extracting("firstName", "lastName")
//			.containsExactly("Marcin", "Grzejszczak");
//
//	}
//
//	@Test
//	public void shouldFilterOutTheGridWithTheProvidedLastName() {
//
//		this.repository.save(new Customer("Josh", "Long"));
//
//		mainView.listCustomers("Long");
//
//		then(getCustomersInGrid()).hasSize(1);
//		then(getCustomersInGrid().get(getCustomersInGrid().size() - 1))
//			.extracting("firstName", "lastName")
//			.containsExactly("Josh", "Long");
//	}
//
//	@Test
//	public void shouldInitializeWithInvisibleEditor() {
//
//		then(this.editor.isVisible()).isFalse();
//	}
//
//	@Test
//	public void shouldMakeEditorVisible() {
//		Customer first = getCustomersInGrid().get(0);
//		this.mainView.grid.select(first);
//
//		then(this.editor.isVisible()).isTrue();
//	}
//
//	private void customerDataWasFilled(CustomerEditor editor, String firstName,
//			String lastName) {
//		this.editor.firstName.setValue(firstName);
//		this.editor.lastName.setValue(lastName);
//		editor.editCustomer(new Customer(firstName, lastName));
//	}

//	@Configuration
//	@EnableAutoConfiguration(exclude = com.vaadin.flow.spring.SpringBootAutoConfiguration.class)
//	static class Config {
//
//	}
}
