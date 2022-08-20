package com.km220.utils;

import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class ExceptionUtils {

  private ExceptionUtils() {
  }

  public static void runSafely(ExceptionRunnable runnable) {
    runSafely(runnable, () -> StringUtils.EMPTY);
  }

  public static void runSafely(ExceptionRunnable runnable, Supplier<String> exceptionMessage) {
    try {
      runnable.run();
    } catch(Exception e) {
      log.error(exceptionMessage.get(), e);
    }
  }
}
