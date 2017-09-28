package org.darwin.shardingDataSource.dataSource;

import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

/**
 * Description:
 * Created by xiongjie.wxj on 2017/7/27.
 */
public interface DataSourceCollector {

  List<DataSource> getDataSources();

}
