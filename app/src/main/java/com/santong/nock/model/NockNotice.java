package com.santong.nock.model;

import java.util.Date;

/**
 * Created by santong.
 * At 15/12/25 16:26
 */
public class NockNotice {
    private int notcie_ID;
    private String title;
    private String content;
    private Date noticeDate;

    public NockNotice() {
    }

    public NockNotice(int notcie_ID, String title, String content, Date noticeDate) {
        this.notcie_ID = notcie_ID;
        this.title = title;
        this.content = content;
        this.noticeDate = noticeDate;
    }

    public int getNotcie_ID() {
        return notcie_ID;
    }

    public void setNotcie_ID(int notcie_ID) {
        this.notcie_ID = notcie_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    @Override
    public String toString() {
        return "NockNotice{" +
                "notcie_ID=" + notcie_ID +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", noticeDate=" + noticeDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NockNotice that = (NockNotice) o;

        if (notcie_ID != that.notcie_ID) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return !(noticeDate != null ? !noticeDate.equals(that.noticeDate) : that.noticeDate != null);

    }

    @Override
    public int hashCode() {
        int result = notcie_ID;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (noticeDate != null ? noticeDate.hashCode() : 0);
        return result;
    }
}
