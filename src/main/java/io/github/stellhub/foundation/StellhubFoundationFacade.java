package io.github.stellhub.foundation;

import io.github.stellhub.foundation.context.RequestContext;
import io.github.stellhub.foundation.context.RequestContextAccessor;
import io.github.stellhub.foundation.context.RuntimeContext;
import io.github.stellhub.foundation.context.RuntimeContextAccessor;
import io.github.stellhub.foundation.env.EnvironmentAccessor;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StellhubFoundationFacade {

  private final EnvironmentAccessor environmentAccessor;
  private final RuntimeContextAccessor runtimeContextAccessor;
  private final RequestContextAccessor requestContextAccessor;

  /** 获取当前进程统一运行时上下文。 */
  public RuntimeContext runtimeContext() {
    return runtimeContextAccessor.runtimeContext();
  }

  /** 获取当前线程统一请求上下文。 */
  public Optional<RequestContext> requestContext() {
    return requestContextAccessor.currentRequestContext();
  }

  /** 读取指定环境变量。 */
  public Optional<String> environment(String name) {
    return environmentAccessor.get(name);
  }

  /** 读取当前请求中的指定请求头。 */
  public Optional<String> header(String name) {
    return requestContext().flatMap(context -> context.header(name));
  }
}
