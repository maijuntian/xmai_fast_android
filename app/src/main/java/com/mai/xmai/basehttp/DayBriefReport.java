package com.mai.xmai.basehttp;


import java.io.Serializable;
import java.util.List;

/**
 * Created by aye on 2016/2/24.
 */
public class DayBriefReport implements Serializable{
    private List<SleepBlockData> blockList;
    private List<SleepBlockReport> spec;

    public List<SleepBlockData> getBlockList() {
        return blockList;
    }

    public void setBlockList(List<SleepBlockData> blockList) {
        this.blockList = blockList;
    }

    public List<SleepBlockReport> getSpec() {
        return spec;
    }

    public void setSpec(List<SleepBlockReport> spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return "DayBriefReport{" +
                "blockList=" + blockList +
                ", spec=" + spec +
                '}';
    }
}
