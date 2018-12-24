package hello.frontend;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PubSubUiServiceTests {

    private PubSubUiService thePubSubUiService;

    @Mock
    UiListener listener1;
    @Mock
    UiListener listener2;

    @Before
    public void before(){
        thePubSubUiService = new PubSubUiService();
    }

    @Test
    public void testOfSubcribtions(){
        thePubSubUiService.addListener(listener1);
        thePubSubUiService.addListener(listener2);
        thePubSubUiService.updateAll();
        thePubSubUiService.updateAll();
        thePubSubUiService.updateAll();

        verify(listener1, times(3)).contactsUpdated();
        verify(listener2, times(3)).contactsUpdated();
    }


}
