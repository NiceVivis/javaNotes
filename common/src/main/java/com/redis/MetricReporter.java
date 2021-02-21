package com.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetricReporter extends AbstractMetricMap{

    List<ExecutableMetricMap> list = new ArrayList<ExecutableMetricMap>();

    public Map<String,Object> getReport(){
        guageMap.clear();
        for(ExecutableMetricMap executable : list){
            executable.collect();
        }
        return guageMap;
    }

    public void setList(List<ExecutableMetricMap> list) {
        this.list = list;
    }
}

