package com.pratham.readandspeak.modalclasses;


import com.pratham.readandspeak.domain.ContentTable;

/**
 * Created by PEF on 24/01/2018.
 */

public class File_Model implements Comparable {
    int progress = 0;
    ContentTable detail;
    boolean sent;

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public ContentTable getDetail() {
        return detail;
    }

    public void setDetail(ContentTable detail) {
        this.detail = detail;
    }

    @Override
    public int compareTo(Object o) {
        File_Model compare = (File_Model) o;
        if (compare.getDetail().getNodeId() != null) {
            if (compare.getDetail().getNodeId().equalsIgnoreCase(this.detail.getNodeId()))
                return 0;
            else return 1;
        } else {
            return 0;
        }
    }
}
