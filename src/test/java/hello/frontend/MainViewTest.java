package hello.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.BDDAssertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class MainViewTest {

    @Mock
    Tabs tabs;
    @Mock
    Tab tabJavaComp;
    @Mock
    Tab tabWebComp;
    @Mock
    TabJavaContent contentJavaComp;
    @Mock
    TabWebCompContent contentWebComp;
    @Mock
    Div globalDiv;


    @Test
    public void testOfCreationMainView() {
        final ComponentEventListener[] listener = {null};
        when(tabs.addSelectedChangeListener(any(ComponentEventListener.class))).thenAnswer(
                (InvocationOnMock invocation) -> {
                    listener[0] = (ComponentEventListener) (invocation.getArguments()[0]);
                    return null;
                });
        MainView mainView = createMainView();

        when(tabs.getSelectedTab()).thenReturn(tabWebComp);
        listener[0].onComponentEvent(null);

        verify(contentWebComp, times(1)).setVisible(false);
        verify(contentWebComp, times(1)).setVisible(true);
        verify(contentJavaComp, times(1)).setVisible(false);

    }

    private MainView createMainView() {
        return new MainView(contentJavaComp, contentWebComp) {
            @Override
            Div createGlobalDiv(TabJavaContent tabJavaContent, TabWebCompContent tabWebCompContent) {
                return globalDiv;
            }

            @Override
            public void add(Component... components) {
                then(components).hasSize(2);
                then(components).contains(tabs);
                then(components).contains(globalDiv);
            }

            @Override
            Tabs createTabSwitch(Tab forJava, Tab forWeb) {
                then(forJava).isEqualTo(tabJavaComp);
                then(forWeb).isEqualTo(tabWebComp);
                return tabs;
            }

            @Override
            Tab createTab(String label, String id) {
                switch (label) {
                    case "Using Java":
                        then(id).isEqualTo("tab-for-java-comp");
                        return tabJavaComp;
                    case "Using Web Component":
                        then(id).isEqualTo("tab-for-web-comp");
                        return tabWebComp;
                    default:
                        fail("Not expected Tab label : " + label);
                        return null;
                }
            }
        };
    }


}
