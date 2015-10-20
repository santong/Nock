package com.santong.nock.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by santong.
 * At 15/9/28 15:49
 */
public class NockPlan implements Parcelable {

    private int planId;
    private int recordDays;         // 打卡天数
    private String title;           // 计划标题
    private Date startDate;         // 开始日期
    private Date endDate;           // 结束日期
    private String description;     // 计划描述
    private boolean state;          // 计划状态 True为完成,False为未完成。
    private Date lastDate;          // 上一次打卡时间

    @Override
    public String toString() {
        return "NockPlan{" +
                "planId=" + planId +
                ", recordDays=" + recordDays +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", lastDate=" + lastDate +
                '}';
    }

    public NockPlan() {

    }

    public NockPlan(int planId, int recordDays, String title, Date startDate, Date endDate, String description, boolean state, Date lastDate) {
        this.planId = planId;
        this.recordDays = recordDays;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.state = state;
        this.lastDate = lastDate;
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


    public boolean isFinished() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getPlanId());
        dest.writeInt(getRecordDays());
        dest.writeString(getTitle());
        dest.writeString(getDescription());
        dest.writeLong(getStartDate().getTime());
        dest.writeLong(getEndDate().getTime());
        dest.writeLong(getLastDate().getTime());
        if (isFinished())
            dest.writeInt(1);
        else
            dest.writeInt(0);
    }

    public static final Parcelable.Creator<NockPlan> CREATOR = new Parcelable.Creator<NockPlan>() {

        @Override
        public NockPlan createFromParcel(Parcel source) {
            NockPlan plan = new NockPlan();
            plan.setPlanId(source.readInt());
            plan.setRecordDays(source.readInt());
            plan.setTitle(source.readString());
            plan.setDescription(source.readString());
            plan.setStartDate(new Date(source.readLong()));
            plan.setEndDate(new Date(source.readLong()));
            plan.setLastDate(new Date(source.readLong()));
            if (source.readInt() == 1)
                plan.setState(true);
            else
                plan.setState(false);
            return plan;
        }

        @Override
        public NockPlan[] newArray(int size) {
            return new NockPlan[size];
        }

    };
}
