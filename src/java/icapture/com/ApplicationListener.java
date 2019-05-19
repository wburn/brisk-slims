package icapture.com;

//~--- JDK imports ------------------------------------------------------------

import javax.servlet.*;

public final class ApplicationListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent e) {

//      System.out.println("----- before contextCreated");
//      UserHttpSess.openFactory();
//      System.out.println("----- after contextCreated");
    }

    public void contextDestroyed(ServletContextEvent e) {

//      System.out.println("----- before contextDestroyed");
        UserHttpSess.closeFactory();

//      System.out.println("----- after contextDestroyed");
    }
}



