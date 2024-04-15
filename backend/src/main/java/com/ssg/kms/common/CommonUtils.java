package com.ssg.kms.common;

public class CommonUtils {

  /**
   * 두 ID를 비교하여 동일하지 않은지 확인합니다.
   *
   * @param id 비교할 첫 번째 ID
   * @param otherID 비교할 두 번째 ID
   * @return 두 ID가 동일하지 않은 경우 `true`, 동일한 경우 `false`
   */
  public static boolean isNotSameId(Long id, Long otherID) {
    return !isSameId(id, otherID);
  }

  /**
   * 두 ID를 비교하여 동일 값인지 확인
   *
   * @param id 비교할 첫 번째 ID
   * @param otherID 비교할 두 번째 ID
   * @return 두 ID 값이 동일 하면 `true`, 아닌경우 `false`
   */
  public static boolean isSameId(Long id, Long otherID) {
    return id.equals(otherID);
  }
}
