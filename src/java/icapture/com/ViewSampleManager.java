
/**
 * Manages table view screens, particularly multi-page navigation of tables with
 * more entries than the set 'view limit' per page (usually 1000).
 */
package icapture.com;

public final class ViewSampleManager {
    private int          first;
    private UserHttpSess parent;
    private int          sampleCount;
    private int          step;
    private String       subjectId;
    private int          total;

    public ViewSampleManager(UserHttpSess tmpHttpSessObj) {
        parent    = tmpHttpSessObj;
        step      = 0;
        total     = -1;
        first     = 0;
        subjectId = null;
    }

    public void clearValues() {
        total     = -1;
        first     = 0;
        step      = parent.getViewLimit();
        subjectId = null;
    }

    public void changeSampleTotal(int diff) {
        total += diff;
        System.out.println("ViewSampleManager, changeSampleTotal, diff=" + diff);
    }

    public void setSampleCount(int val) {
        total = val;

        // if (sampleCount > 0) total = sampleCount; //for search results
        // else if (total < sampleCount) total = sampleCount;
    }

    public boolean hasSamplePrevious() {
        return (step > 0) && (first > 0);
    }

    public void setSamplePrevious() {
        first -= step;

        if (first < 0) {
            first = 0;
        }
    }

    public boolean hasSampleNext() {
        return (step > 0) && (first + step < total - 1);
    }

    public void setSampleNext() {
        first += step;

        if (first >= total) {
            setSamplePrevious();
        }
    }

    public void setSampleFirst() {
        first = 0;
    }

    public void setSampleLast() {
        first = total - step;

        if (first < 0) {
            first = 0;
        }
    }

    public int getSampleStep() {
        return step;
    }

    public int getSampleCurrent() {
        return first;
    }

    // sets subject id in level below (aliquot)
    public void setSubjectIdDown(String newSubjectId) {
        boolean change = false;

        if (newSubjectId == null) {
            if (subjectId != null) {
                change = true;
            }
        } else {
            if (!newSubjectId.equals(subjectId)) {
                change = true;
            }
        }

        if (change) {
            total     = -1;
            first     = 0;
            subjectId = newSubjectId;

            // ViewAliquotManager vam = parent.getViewAliquotManager();
            // vam.setPersonAndSampleIdDown( personId, null);
        }
    }

    public String getSampleControlString() {
        System.out.println("in ViewSampleManager.java, total=" + total);

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

    /**
     * @return the subjectId
     */
    public String getSubjectId() {
        return subjectId;
    }
}



