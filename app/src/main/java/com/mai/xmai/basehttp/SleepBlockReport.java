package com.mai.xmai.basehttp;

import java.io.Serializable;

/**
 * Created by speed on 2016/2/24.
 */
public class SleepBlockReport implements Serializable {
    private int deep_sleep;
    private float sleep_score;
    private int awake_count;
    private int awake;
    private String mac;
    private String snore;
    private String blockId;
    private String apnea;
    private String uid;
    private int sleep_alltime;
    private String createTime;
    private int temperature;
    private int bed_away;
    private int light_sleep;
    private int bed_away_count;
    private int sleep_activity;
    private int sleep_freetime;
    private int aerophone;
    private String startTime;
    private String endTime;

    public int getDeep_sleep() {
        return deep_sleep;
    }

    public void setDeep_sleep(int deep_sleep) {
        this.deep_sleep = deep_sleep;
    }

    public float getSleep_score() {
        return sleep_score;
    }

    public void setSleep_score(float sleep_score) {
        this.sleep_score = sleep_score;
    }

    public int getAwake_count() {
        return awake_count;
    }

    public void setAwake_count(int awake_count) {
        this.awake_count = awake_count;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSnore() {
        return snore;
    }

    public void setSnore(String snore) {
        this.snore = snore;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getApnea() {
        return apnea;
    }

    public void setApnea(String apnea) {
        this.apnea = apnea;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getSleep_alltime() {
        return sleep_alltime;
    }

    public void setSleep_alltime(int sleep_alltime) {
        this.sleep_alltime = sleep_alltime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getBed_away() {
        return bed_away;
    }

    public void setBed_away(int bed_away) {
        this.bed_away = bed_away;
    }

    public int getLight_sleep() {
        return light_sleep;
    }

    public void setLight_sleep(int light_sleep) {
        this.light_sleep = light_sleep;
    }

    public int getBed_away_count() {
        return bed_away_count;
    }

    public void setBed_away_count(int bed_away_count) {
        this.bed_away_count = bed_away_count;
    }

    public int getSleep_activity() {
        return sleep_activity;
    }

    public void setSleep_activity(int sleep_activity) {
        this.sleep_activity = sleep_activity;
    }

    public int getSleep_freetime() {
        return sleep_freetime;
    }

    public void setSleep_freetime(int sleep_freetime) {
        this.sleep_freetime = sleep_freetime;
    }

    public int getAerophone() {
        return aerophone;
    }

    public void setAerophone(int aerophone) {
        this.aerophone = aerophone;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getAwake() {
        return awake;
    }

    public void setAwake(int awake) {
        this.awake = awake;
    }

    @Override
    public String toString() {
        return "SleepBlockReport{" +
                "deep_sleep=" + deep_sleep +
                ", sleep_score=" + sleep_score +
                ", awake_count=" + awake_count +
                ", awake=" + awake +
                ", mac='" + mac + '\'' +
                ", snore='" + snore + '\'' +
                ", blockId='" + blockId + '\'' +
                ", apnea='" + apnea + '\'' +
                ", uid='" + uid + '\'' +
                ", sleep_alltime=" + sleep_alltime +
                ", createTime='" + createTime + '\'' +
                ", temperature=" + temperature +
                ", bed_away=" + bed_away +
                ", light_sleep=" + light_sleep +
                ", bed_away_count=" + bed_away_count +
                ", sleep_activity=" + sleep_activity +
                ", sleep_freetime=" + sleep_freetime +
                ", aerophone=" + aerophone +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
