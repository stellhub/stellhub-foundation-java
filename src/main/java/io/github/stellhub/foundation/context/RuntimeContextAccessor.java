package io.github.stellhub.foundation.context;

public interface RuntimeContextAccessor {

  /** 获取当前进程级运行时上下文。 */
  RuntimeContext runtimeContext();
}
