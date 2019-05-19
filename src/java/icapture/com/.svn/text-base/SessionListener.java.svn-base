package icapture.com;

//~--- JDK imports ------------------------------------------------------------

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public final class SessionListener implements HttpSessionListener {
    static HashMap UserSessions = new HashMap();
    static int     users        = 0;

    public void sessionCreated(HttpSessionEvent e) {
        if (!UserSessions.containsKey(e.getSession())) {
            users++;
            UserSessions.put(e.getSession(), new UserHttpSess(e.getSession()));
        }
    }

    public void sessionDestroyed(HttpSessionEvent e) {
        UserHttpSess tmpSessConfObj = (UserHttpSess) UserSessions.get(e.getSession());

        if (tmpSessConfObj != null) {
            users--;
            tmpSessConfObj.closeAll();
            UserSessions.remove(e.getSession());
        }
    }

    public static int getConcurrentUsers() {
        return users;
    }

    public static UserHttpSess getUserHttpSessObject(HttpSession s) {
        return (UserHttpSess) UserSessions.get(s);
    }
}



