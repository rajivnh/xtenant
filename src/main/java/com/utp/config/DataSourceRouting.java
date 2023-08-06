package com.utp.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.utp.constant.ClientEnum;
import com.utp.context.ClientContextHolder;

public class DataSourceRouting extends AbstractRoutingDataSource {
  
    @Override
    protected Object determineCurrentLookupKey() {
        return ClientContextHolder.getClientContext();
    }
  
    public void initDatasource(DataSource cibcDataSource, DataSource citiDataSource) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        
        dataSourceMap.put(ClientEnum.CIBC, cibcDataSource);
        dataSourceMap.put(ClientEnum.CITI, citiDataSource);
        
        this.setTargetDataSources(dataSourceMap);
          	
        this.setDefaultTargetDataSource(cibcDataSource);
    }
}