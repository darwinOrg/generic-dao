/**
 * org.darwin.genericDao.query.QueryHandler.java created by Tianxin(tianjige@163.com) on 2015年5月27日
 * 下午6:46:45
 */
package org.darwin.genericDao.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.darwin.common.utils.Utils;
import org.darwin.genericDao.bo.BaseObject;
import org.darwin.genericDao.dao.TableAware;
import org.darwin.genericDao.mapper.ColumnMapper;

/**
 * created by Tianxin on 2015年5月27日 下午6:46:45
 */
public class WriteHandler<ENTITY> {

  // 私有化无参构造器
  private WriteHandler() {}


  /**
   * @param columnMappers
   * @param configKeeper
   */
  public WriteHandler(Map<String, ColumnMapper> columnMappers, TableAware tableAware) {

    this();
    this.tableAware = tableAware;
    this.columnMappers = columnMappers;

    int mapperCount = columnMappers.size();
    List<String> insertColumns = new ArrayList<String>(mapperCount);
    List<String> updateColumns = new ArrayList<String>(mapperCount);
    List<String> allColumns = new ArrayList<String>(mapperCount);
    Set<String> updateColumnSet = new HashSet<>(mapperCount * 4 / 3 + 1);

    Collection<ColumnMapper> mappers = columnMappers.values();
    for (ColumnMapper mapper : mappers) {

      // 装装载对象的字段集
      allColumns.add(mapper.getSQLColumn());
      if (mapper.isExtendColumn()) {
        continue;
      }

      // 新增和修改的字段集
      insertColumns.add(mapper.getSQLColumn());
      if (mapper.isModifiable()) {
        updateColumns.add(mapper.getSQLColumn());
        updateColumnSet.add(mapper.getSQLColumn());
      }
    }

    initColumns(insertColumns, updateColumns, allColumns, updateColumnSet);
  }

  /**
   * 初始化字段列表的设置
   * 
   * @param insertColumns
   * @param updateColumns
   * @param allColumns
   * @param updateColumnSet created by Tianxin on 2015年6月1日 上午6:56:20
   */
  private void initColumns(List<String> insertColumns, List<String> updateColumns,
      List<String> allColumns, Set<String> updateColumnSet) {

    // 普通的字段保存
    this.allColumns = allColumns;
    this.insertColumns = insertColumns.toArray(new String[insertColumns.size()]);
    this.updateColumns = updateColumns.toArray(new String[updateColumns.size()]);
    this.updateColumnSet = updateColumnSet;

    // 构造insert语句的字段列表与展位符
    StringBuilder sInsertColumnBuilder = new StringBuilder(insertColumns.size() * 9);
    StringBuilder sInsertPHolderBuilder = new StringBuilder(insertColumns.size() * 2 + 2);
    for (String column : insertColumns) {
      sInsertColumnBuilder.append(',').append(column);
      sInsertPHolderBuilder.append(",?");
    }
    sInsertColumnBuilder.append(')').setCharAt(0, '(');
    sInsertPHolderBuilder.append(')').setCharAt(0, '(');
    this.sInsertColumns = sInsertColumnBuilder.toString();
    this.sInsertPlaceHolder = sInsertPHolderBuilder.toString();
  }

  private List<String> allColumns;
  private String[] updateColumns;
  private Set<String> updateColumnSet;

  /**
   * insert的SQL中参数的展位符——(?,?,?,?)
   */
  private String sInsertPlaceHolder;
  private String[] insertColumns;
  private String sInsertColumns;

  private Map<String, ColumnMapper> columnMappers;
  private TableAware tableAware;


  /**
   * 获取insert时的参数列表
   * 
   * @return created by Tianxin on 2015年5月27日 下午7:42:28
   */
  public Object[] generateInsertParams(Collection<ENTITY> entities) {

    ArrayList<Object> params = new ArrayList<Object>(insertColumns.length * entities.size());
    for (ENTITY entity : entities) {

      if (entity == null) {
        continue;
      }

      params.addAll(getParamsByColumns(entity, insertColumns));
    }
    return params.toArray();
  }

  /**
   * @param params
   * @param entity created by Tianxin on 2015年5月27日 下午8:22:04
   */
  private List<Object> getParamsByColumns(ENTITY entity, String...columns) {
    ArrayList<Object> params = new ArrayList<Object>(columns.length + 1);
    for (String column : columns) {
      try {
        ColumnMapper cMapper = columnMappers.get(column);
        Object value = cMapper.getGetter().invoke(entity);
        params.add(value);
      } catch (Exception e) {
        throw new RuntimeException(column + "getter invoke error!", e);
      }
    }
    return params;
  }
  
  private Map<String, String> defaultInsertSQLMap = new ConcurrentHashMap<String, String>();

  /**
   * 生成insert语句
   * 
   * @return created by Tianxin on 2015年5月27日 下午7:44:14
   */
  public String generateInsertSQL(Collection<ENTITY> entities) {
      
    if(entities.size() == 1) {
      String table = tableAware.table();
      String sql = defaultInsertSQLMap.get(table);
      if(sql == null) {
        sql = generateInsertSQL(entities, 0);
        defaultInsertSQLMap.put(table, sql);
      }
      return sql;
    }
    
    return generateInsertSQL(entities, 0);
  }

  /**
   * 生成insert语句
   * 
   * @param entities
   * @param type 0为普通,1为replace,2为insert ignore
   * 
   * @return created by Tianxin on 2015年5月27日 下午7:44:14
   */
  public String generateInsertSQL(Collection<ENTITY> entities, int type) {

    String[] operates = new String[] {"insert", "replace", "insert ignore"};
    if (type >= operates.length || type < 0) {
      throw new RuntimeException("不是合法的type!");
    }

    StringBuilder sb = new StringBuilder(512);
    sb.append(operates[type]).append(" into ").append(tableAware.table());
    sb.append(' ').append(sInsertColumns).append(" values ");
    for (ENTITY entity : entities) {
      if (entity != null) {
        sb.append(sInsertPlaceHolder).append(',');
      }
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  /**
   * 生成update的参数列表
   * 
   * @return created by Tianxin on 2015年5月27日 下午7:42:28
   */
  public Object[] generateUpdateParams(ENTITY entity) {
    return generateUpdateParams(entity, updateColumns);
  }
  
  /**
   * 生成update的参数列表
   * 
   * @return created by Tianxin on 2015年5月27日 下午7:42:28
   */
  public Object[] generateUpdateParams(ENTITY entity, String...targetColumns) {
    if (entity instanceof BaseObject<?>) {
      List<Object> params = getParamsByColumns(entity, targetColumns);
      params.add(((BaseObject<?>) entity).getId());
      return params.toArray();
    }
    
    throw new RuntimeException(
        Utils.concat(entity.getClass().getSimpleName(), " 不是BaseObject的子类!"));
  }
  
  private Map<String, String> defaultUpdateSQLMap = new ConcurrentHashMap<String, String>();

  /**
   * 生成update的SQL语句
   * 
   * @return created by Tianxin on 2015年5月27日 下午7:44:14
   */
  public String generateUpdateSQL(ENTITY entity) {
      
    String table = tableAware.table();
    String sql = defaultUpdateSQLMap.get(table);
    if(sql == null) {
      sql = generateUpdateSQL(entity, updateColumns);
      defaultUpdateSQLMap.put(table, sql);
    }
    return sql;
  }

  /**
   *  生成update的SQL语句
   *
   * @return created by Tianxin on 2015年5月27日 下午7:44:14
   */
  public String generateUpdateSQL(ENTITY entity, String... targetColumns) {
    StringBuilder sb = new StringBuilder(512);
    sb.append("update ").append(tableAware.table()).append(" set ");
    for (String column : targetColumns) {
      
      //如果该字段是无效字段，则直接抛出错误
      if (!updateColumnSet.contains(column)) {
        throw new RuntimeException("column not exist! " + column);
      }

      sb.append(column).append("=?,");
    }
    sb.setCharAt(sb.length() - 1, ' ');
    sb.append(" where ").append(tableAware.keyColumn()).append("=?");
    return sb.toString();
  }

  /**
   * @return created by Tianxin on 2015年5月28日 下午5:22:32
   */
  public List<String> allColumns() {
    return this.allColumns;
  }
}
