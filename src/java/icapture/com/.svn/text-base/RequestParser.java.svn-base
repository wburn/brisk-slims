package icapture.com;

//~--- JDK imports ------------------------------------------------------------

import java.io.*;

import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

public final class RequestParser {
    private int                bufSize      = 16334;    // 32768;//49202;//65536;
    private byte               buffer[]     = new byte[16336];
    private int                head         = 0;
    private int                startOfBlock = -1;
    private int                tail         = 0;
    private int                del_l;
    private FileOutputStream   fos;
    private UserHttpSess       sessObj;
    private ServletInputStream sis;
    private HashMap            userParameters;

    public RequestParser(UserHttpSess tmpHttpSessObj) {
        sessObj        = tmpHttpSessObj;
        userParameters = new HashMap();
    }

    public String getParameter(String name) {
        String value = (String) userParameters.get(name);

        return value;
    }

    private int readByte() throws IOException {
        if (head >= tail) {
            head = 0;
            tail = sis.read(buffer, head, bufSize);

            if (tail < 1) {
                return -1;
            }
        }

        return buffer[head++];
    }

    private int readByteOfFile() throws IOException {
        if (head == tail) {
            fos.write(buffer, startOfBlock, tail - startOfBlock - del_l);

            if (del_l > 0) {
                System.arraycopy(buffer, tail - del_l, buffer, 0, del_l);
            }

            head         = del_l;
            startOfBlock = 0;
            tail         = sis.read(buffer, head, bufSize - head);

            if (tail < 1) {
                return -1;
            }

            tail += head;
        }

        return head++;
    }

    private int readFirstByteOfFile() throws IOException {
        fos = new FileOutputStream(sessObj.getTempFile());

        if (head == tail) {
            head = 0;
            tail = sis.read(buffer, 0, bufSize);

            if (tail < 1) {
                return -1;
            }
        }

        startOfBlock = head;

        return head++;
    }

    private void writeLastByteOfFile() throws IOException {
        if (head - del_l > startOfBlock) {
            fos.write(buffer, startOfBlock, head - startOfBlock - del_l);
        }

        startOfBlock = -1;
        fos.flush();
        fos.close();
    }

    public boolean parseRequest(HttpServletRequest request) throws IOException, ServletException {
        sis = request.getInputStream();

        // get Delimiter  RFC 1867

/*
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write('\r'); baos.write('\n');
        int i = readByte();
        for (;i!=-1&&i!='\r';i=readByte()) baos.write(i);
        byte[] delimiter = baos.toByteArray();
        int dellength = delimiter.length;
*/
        StringWriter sw = new StringWriter();

        sw.write("\r\n");

        int i = readByte();

        for (; (i != -1) && (i != '\r'); i = readByte()) {
            sw.write(i);
        }

        String delimiter = sw.toString();
        int    dellength = delimiter.length();

        readByte();    // ditch '\n'

        while (true) {    // loop over parameters

//          get Param header (ends with \r\n\r\n
            StringWriter h    = new StringWriter();
            int[]        temp = new int[4];

            temp[0] = readByte();
            temp[1] = readByte();
            temp[2] = readByte();
            h.write(temp[0]);
            h.write(temp[1]);
            h.write(temp[2]);

            for (temp[3] = readByte(); temp[3] != -1; temp[3] = readByte()) {
                if ((temp[0] == '\r') && (temp[1] == '\n') && (temp[2] == '\r') && (temp[3] == '\n')) {
                    break;
                }

                h.write(temp[3]);
                temp[0] = temp[1];
                temp[1] = temp[2];
                temp[2] = temp[3];
            }

            String header    = h.toString();
            int    startName = header.indexOf("name=\"");
            int    endName   = header.indexOf("\"", startName + 6);

            if ((startName == -1) || (endName == -1)) {
                break;
            }

            String name  = header.substring(startName + 6, endName);
            String value = "";

            if (name.equals("inputfile")) {
                startName = header.indexOf("filename=\"");
                endName   = header.indexOf("\"", startName + 10);
                value     = header.substring(startName + 10, endName);

                // trim out full path info if it is included in filename (IE includes it)
                int slash = value.lastIndexOf("\\");

                if (slash != -1) {
                    value = value.substring(slash + 1);
                }
            }

            del_l = 0;

            if (!value.equals("")) {    // write whole file to disk
                int ind = readFirstByteOfFile();

                while (ind != -1) {
                    if (buffer[ind] == delimiter.charAt(del_l)) {
                        if (++del_l == dellength) {
                            break;
                        }
                    } else {
                        if (del_l > 0) {
                            if (buffer[ind] == '\r') {
                                del_l = 1;
                            } else {
                                del_l = 0;
                            }
                        }
                    }

                    ind = readByteOfFile();
                }

                writeLastByteOfFile();
            } else {                    // get parameter value
                int          b   = readByte();
                StringWriter sos = new StringWriter();

                while (b != -1) {
                    if (b == delimiter.charAt(del_l)) {
                        if (++del_l == dellength) {
                            break;
                        }
                    } else {
                        if (del_l == 0) {
                            sos.write(b);
                        } else {
                            sos.write(delimiter, 0, del_l);

                            if (b == '\r') {
                                del_l = 1;
                            } else {
                                del_l = 0;
                                sos.write(b);
                            }
                        }
                    }

                    b = readByte();
                }

                value = sos.toString();
            }

            if (!userParameters.containsKey(name)) {
                userParameters.put(name, new String(value));
            }

            if ((readByte() == '-') && (readByte() == '-')) {
                break;
            }
        }

        return true;
    }
}



