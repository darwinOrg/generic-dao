/**
 * org.darwin.genericDao.param.LikeParam.java
 * created by Tianxin(tianjige@163.com) on 2015年5月27日 下午1:59:59
 */
package org.darwin.genericDao.param;

import java.util.Arrays;
import java.util.List;

import org.darwin.common.utils.Utils;

/**
 * created by Tianxin on 2015年5月27日 下午1:59:59
 */
public class LikeParam implements Param {

  private boolean leftStrict = false;
  private boolean rightStrict = false;
  private Object value;
  private boolean reverse = false;
  
  /**
   * @param word
   * @param reverse
   */
  public LikeParam(String word, boolean reverse) {
    this.value = word;
    this.reverse = reverse;
  }

  /**
   * @param word
   */
  public LikeParam(String word) {
    this.value = word;
  }

  /**
   * @param word
   * @param leftStrict
   * @param rightStrict
   * @param reverse
   */
  public LikeParam(String word, boolean leftStrict, boolean rightStrict, boolean reverse) {
    this.value = word;
    this.leftStrict = leftStrict;
    this.rightStrict = rightStrict;
    this.reverse = reverse;
  }

  /**
   * @param word
   * @param leftStrict
   * @param rightStrict
   */
  public LikeParam(String word, boolean leftStrict, boolean rightStrict) {
    this(word, leftStrict, rightStrict, false);
  }

  public List<Object> getParams() {
    return Arrays.asList((Object) (Utils.concat(leftStrict ? "" : "%", value, rightStrict ? "" : "%")));
  }

  public String buildOperate(String column) {
    if (reverse) {
      if (leftStrict && rightStrict) {
        return Utils.concat(column, " != ?");
      } else {
        return Utils.concat(column, " not like ?");
      }
    } else {
      if (leftStrict && rightStrict) {
        return Utils.concat(column, " = ?");
      } else {
        return Utils.concat(column, " like ?");
      }

    }
  }

}
