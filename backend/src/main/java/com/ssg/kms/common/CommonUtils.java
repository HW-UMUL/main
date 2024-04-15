package com.ssg.kms.common;

public class CommonUtils {

  /**
   * 두 ID를 비교하여 동일하지 않은지 확인합니다.
   *
   * @param id 비교할 첫 번째 ID
   * @param meID 비교할 두 번째 ID
   * @return 두 ID가 동일하지 않은 경우 `true`, 동일한 경우 `false`
   * @throws NullPointerException `id` 또는 `meID`가 null인 경우
   */
  public static boolean isNotSameId(Long id, Long meID) {
    return !id.equals(meID);
  }
}
