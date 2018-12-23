package hello;

import com.vaadin.flow.testutil.ChromeBrowserTest;

public abstract class ChromeWrapper extends ChromeBrowserTest {

    @Override
    protected String getTestPath() {
        return "/";
    }

    @Override
    protected int getDeploymentPort() {
        return 8080;
    }
}
