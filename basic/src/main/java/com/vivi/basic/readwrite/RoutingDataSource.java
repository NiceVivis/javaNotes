package com.vivi.basic.readwrite;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dbKey = DbContextHolder.get();
        log.info("select db , dbKey = {}", dbKey);
        return dbKey;
    }
}
