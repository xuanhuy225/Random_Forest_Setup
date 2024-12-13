package me.common.objs;

import me.common.util.DateTimeUtils;
import me.common.util.JacksonUtils;


public class ReportConfig {
    /**
     * Last Update of config build report
     * Unit: second
     */
    Integer lastUpdate;
    /**
     * Range time run build report
     * Unit: second
     */
    Integer rangeTime;
    /**
     * Deplay Time from run build report vs current
     * Unit: second
     */
    Integer delayTime;

    public Integer getLastUpdate() {
        return lastUpdate;
    }

    public ReportConfig setLastUpdate(Integer lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    public Integer getRangeTime() {
        return rangeTime;
    }

    public ReportConfig setRangeTime(Integer rangeTime) {
        this.rangeTime = rangeTime;
        return this;
    }

    public Integer getDelayTime() {
        return delayTime;
    }

    public ReportConfig setDelayTime(Integer delayTime) {
        this.delayTime = delayTime;
        return this;
    }

    public ReportConfig parseFromJsonString(String jsonString) {
        return JacksonUtils.from(jsonString, ReportConfig.class);
    }

    public String parseToJsonString() {
        return JacksonUtils.to(this);
    }

    public int getFromTime() {
        return DateTimeUtils.getBeginOfPartTimeZoneVN(this.lastUpdate, this.rangeTime);
    }

    public int getEndTime() {
        return getFromTime() + this.rangeTime - 1;
    }

    public int updateLastUpdate() {
        return this.lastUpdate = getEndTime() + 1;
    }

    public boolean isValidate() {
        return this.rangeTime != null && this.delayTime != null && this.lastUpdate != null;
    }
}
