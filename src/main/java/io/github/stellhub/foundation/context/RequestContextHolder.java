package io.github.stellhub.foundation.context;

import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestContextHolder {

  private static final ThreadLocal<RequestContext> CONTEXT_HOLDER = new ThreadLocal<>();

  /** 绑定当前线程请求上下文。 */
  public static void set(RequestContext requestContext) {
    CONTEXT_HOLDER.set(requestContext);
  }

  /** 获取当前线程请求上下文。 */
  public static Optional<RequestContext> get() {
    return Optional.ofNullable(CONTEXT_HOLDER.get());
  }

  /** 清理当前线程请求上下文。 */
  public static void clear() {
    CONTEXT_HOLDER.remove();
  }
}
