package com.santong.nock.model;

import java.util.Date;

/**
 * Created by santong.
 * At 15/12/25 16:26
 */
public class NockNotice {
    private int noticeId;
    private String title;
    private String content;
    private Date createDate;        // 创建日期
    private Date noticeDate;        // 提醒日期
    private boolean isComplete;     // 是否完成

    public NockNotice() {
    }

    public NockNotice(int noticeId, String title, String content, Date createDate, Date noticeDate, boolean isComplete) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.noticeDate = noticeDate;
        this.isComplete = isComplete;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    @Override
    public String toString() {
        return "NockNotice{" +
                "noticeId=" + noticeId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", noticeDate=" + noticeDate +
                ", isComplete=" + isComplete +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NockNotice that = (NockNotice) o;

        if (noticeId != that.noticeId) return false;
        if (isComplete != that.isComplete) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null)
            return false;
        return !(noticeDate != null ? !noticeDate.equals(that.noticeDate) : that.noticeDate != null);

    }

    @Override
    public int hashCode() {
        int result = noticeId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (noticeDate != null ? noticeDate.hashCode() : 0);
        result = 31 * result + (isComplete ? 1 : 0);
        return result;
    }
}
