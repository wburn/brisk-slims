
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package icapture.com;

//~--- non-JDK imports --------------------------------------------------------

import icapture.hibernate.SampleDocuments;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author ATan1
 */
public class FilenameValidator extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter  outs           = res.getWriter();
        UserHttpSess tmpHttpSessObj = SessionListener.getUserHttpSessObject(req.getSession());
        String       filename       = req.getParameter("filename");
        String       sampId         = req.getParameter("sampId");
        String       sampDocId      = req.getParameter("sampDocId");

        if (sampId.equals("-1")) {
            outs.println(true);
        }

        outs.println(tmpHttpSessObj.checkFilenameUniqueness(sampId, filename, sampDocId));
    }
}



