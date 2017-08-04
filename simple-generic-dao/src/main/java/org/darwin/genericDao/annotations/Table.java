/**
 * org.darwin.genericDao.annotations.Table.java
 * created by Tianxin(tianjige@163.com) on 2015年5月26日 下午8:28:45
 */
package org.darwin.genericDao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.darwin.genericDao.annotations.enums.ColumnStyle;
import org.darwin.genericDao.annotations.enums.TableShardRule;


/**
 * 标记一个bo对应着数据库中哪个table的注解
 * created by Tianxin on 2015年5月26日 下午8:28:45
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

  /**
   * 在哪个DB中
   * 
   * @return
   */
  String db();

  /**
   * 表名 添加默认值方便<code>@Inherited</code>后零注解
   * 
   * @return
   */
  String name();

  /**
   * 该表做了多少个切片拆分，0-1为无分片
   * 
   * @return 表的分片数
   */
  int shardCount() default 0;

  /**
   * 主键字段的名字，默认为<code>id</code>，复合主键时，则用逗号“,”分割
   * 
   * @return 主键字段的名字
   */
  String keyColumn() default "id";

  /**
   * 对象属性到数据库列的映射规则，默认为 {@link ColumnStyle#LOWER_CASE}向前兼容
   * 
   * @return 映射规则 @see ColumnStyle
   */
  ColumnStyle columnStyle() default ColumnStyle.JAVA_TO_MYSQL;
  
  /**
   * 配置该表的分表规则, 一般是内置的实现，如果需要自由定制请使用 {@link #forceShardRuleClass()}.
   * 二者的规则是:<br>
   *   <ul>
   *     <li>如果{@link #forceShardRuleClass()}不为null,则使用{@link #forceShardRuleClass()}</li>
   *      <li>否则使用{@link #shardRule()}</li>
   *   </ul><br>
   * 之所以使用这么矬的方式实现是因为java注解支持的成员支持:<br>
   *   <ul>
   *    <li>A primitive type</li>
   *    <li>String</li>
   *    <li>Class or an invocation of Class</li>
   *    <li>An enum type</li>
   *    <li>An annotation type</li>
   *    <li>An array type whose component type is one of the preceding types</li>
   *   </ul>
   *
   * @return
   *
   * <br/>created by Tianxin on 2016年6月29日 下午5:05:40
   *
   * @see <a href="https://docs.oracle.com/javase/tutorial/java/annotations/basics.html">
   *   https://docs.oracle.com/javase/tutorial/java/annotations/basics.html</a><br>
   *
   * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.6.1">
   *   https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.6.1</a>
   */
  TableShardRule shardRule() default TableShardRule.NORMAL;



  /**
   * 分库分布自定义规则
   *
   * @return
   *
   * @see #shardRule()
   */
  Class<? extends TableShardPolicy> forceShardRuleClass() default NullTableShardPolicy.class ;
}
