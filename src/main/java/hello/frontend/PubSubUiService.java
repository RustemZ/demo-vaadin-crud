package hello.frontend;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.HashSet;
import java.util.Set;

@SpringComponent
@UIScope
public class PubSubUiService {
    private final Set<UiListener> uiListeners = new HashSet<>();

    public synchronized void updateAll() {
        for (UiListener listener : uiListeners) {
            listener.contactsUpdated();
        }
    }

    public synchronized void addListener(UiListener tabJavaContent) {
        uiListeners.add(tabJavaContent);
    }
}
