
/**
 * Manages table view screens, particularly multi-page navigation of tables with
 * more entries than the set 'view limit' per page (usually 1000).
 */
package icapture.com;

public final class ViewSampleProcessManager {
    private int          first;
    private UserHttpSess parent;
    private int          sampleprocessCount;
    private int          step;
    private int          total;

    public ViewSampleProcessManager(UserHttpSess tmpHttpSessObj) {
        parent = tmpHttpSessObj;
        step   = 0;
        total  = -1;
        first  = 0;
    }

    public void clearValues() {
        total = -1;
        first = 0;
        step  = parent.getViewLimit();
    }

    public void changeSampleProcessTotal(int diff) {
        total += diff;
    }

    public void setSampleProcessCount(int val) {
        sampleprocessCount = val;

        if (total < sampleprocessCount) {
            total = sampleprocessCount;
        }
    }

    public boolean hasSampleProcessPrevious() {
        return (step > 0) && (first > 0);
    }

    public void setSampleProcessPrevious() {
        first -= step;

        if (first < 0) {
            first = 0;
        }
    }

    public boolean hasSampleProcessNext() {
        return (step > 0) && (first + step < total - 1);
    }

    public void setSampleProcessNext() {
        first += step;

        if (first >= total) {
            setSampleProcessPrevious();
        }
    }

    public void setSampleProcessFirst() {
        first = 0;
    }

    public void setSampleProcessLast() {
        first = total - step;

        if (first < 0) {
            first = 0;
        }
    }

    public int getSampleProcessStep() {
        return step;
    }

    public int getSampleProcessCurrent() {
        return first;
    }

    public String getSampleProcessControlString() {
        StringBuffer xyz = new StringBuffer();

        if ((step > 0) && (total > step)) {
            if (first > 0) {
                xyz.append("<button type='button' onclick='gotoFirst()' title='first' class='lines'>").append(
                    "<img border='0' src='./images/left_d_10_b.gif' title='first' alt=''>").append("</button>");
                xyz.append("<button type='button' onclick='gotoPrevious()' title='previous' class='lines'>").append(
                    "<img border='0' src='./images/left_10_b.gif' title='previous' alt=''>").append("</button>");
            } else {
                xyz.append("<button type='button' disabled class='lines'>").append(
                    "<img border='0' src='./images/left_d_10_h.gif' alt=''>").append("</button>");
                xyz.append("<button type='button' disabled class='lines'>").append(
                    "<img border='0' src='./images/left_10_h.gif' alt=''>").append("</button>");
            }

            xyz.append(first + 1).append('-').append(first
                       + step).append("&nbsp;of&nbsp;").append(total).append("&nbsp;items&nbsp;");

            if (first + step < total) {
                xyz.append("<button type='button' onclick='gotoNext()' title='next' class='lines'>").append(
                    "<img border='0' src='./images/right_10_b.gif' title='next' alt=''>").append("</button>");
                xyz.append("<button type='button' onclick='gotoLast()' title='last' class='lines'>").append(
                    "<img border='0' src='./images/right_d_10_b.gif' title='last' alt=''>").append("</button>");
            } else {
                xyz.append("<button type='button' disabled class='lines'>").append(
                    "<img border='0' src='./images/right_10_h.gif' alt=''>").append("</button>");
                xyz.append("<button type='button' disabled class='lines'>").append(
                    "<img border='0' src='./images/right_d_10_h.gif' alt=''>").append("</button>");
            }
        } else {
            xyz.append(total).append("&nbsp;items&nbsp;");
        }

        return xyz.toString();
    }
}



