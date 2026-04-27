package io.github.stellhub.foundation.context;

import java.util.Optional;

public interface RequestContextAccessor {

  /** 获取当前线程绑定的请求上下文。 */
  Optional<RequestContext> currentRequestContext();
}
