
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package icapture.com;

//~--- non-JDK imports --------------------------------------------------------

import icapture.hibernate.SampleDocuments;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author ATan1
 */
public class DownloadSampleDocuments extends HttpServlet {
    /**
     * Returns the datastream that lets you download the SampleDocument file
     * @param req the http request
     * @param res the http response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String          sampId         = req.getParameter("sampDocId");
        UserHttpSess    tmpHttpSessObj = SessionListener.getUserHttpSessObject(req.getSession());
        String          selfId         = sampId;
        SampleDocuments sampDocs       = (SampleDocuments) tmpHttpSessObj.getObjectById(SampleDocuments.class, sampId);
        String          filename       = sampDocs.getFilename();
        int             tempIdx        = filename.indexOf(".");

        if (tempIdx == -1) {
            tempIdx = 0;
        }

        String fileType = filename.substring(tempIdx);

        if (fileType.trim().equalsIgnoreCase("txt")) {
            res.setContentType("text/plain");
        } else {
            if (fileType.trim().equalsIgnoreCase("doc")) {
                res.setContentType("application/msword");
            } else {
                if (fileType.trim().equalsIgnoreCase("xls")) {
                    res.setContentType("application/vnd.ms-excel");
                } else {
                    if (fileType.trim().equalsIgnoreCase("pdf")) {
                        res.setContentType("application/pdf");
                    } else {
                        if (fileType.trim().equalsIgnoreCase("ppt")) {
                            res.setContentType("application/ppt");
                        } else {
                            res.setContentType("application/octet-stream");
                        }
                    }
                }
            }
        }

        res.setHeader("Content-Disposition", "attachment; filename=" + filename);
        res.setHeader("cache-control", "no-cache");

        byte[]              file = sampDocs.getDocument();
        ServletOutputStream outs = res.getOutputStream();

        outs.write(file);
        outs.flush();
        outs.close();
    }
}



