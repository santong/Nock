package com.santong.nock.model;

import java.util.Date;

/**
 * Created by santong.
 * At 15/9/28 15:49
 */
public class NockPlan {

    private int planId;
    private int recordDays;         // 剩余打卡天数
    private String title;           // 计划标题
    private Date startDate;         // 开始日期
    private Date endDate;           // 结束日期
    private String description;     // 计划描述

    @Override
    public String toString() {
        return "NockPlan{" +
                "planId=" + planId +
                ", recordDays=" + recordDays +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                '}';
    }

    public NockPlan() {

    }

    public NockPlan(int planId, int recordDays, String title, Date startDate, Date endDate, String description) {
        this.planId = planId;
        this.recordDays = recordDays;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public int getRecordDays() {
        return recordDays;
    }

    public void setRecordDays(int recordDays) {
        this.recordDays = recordDays;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

}
