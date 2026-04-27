package io.github.stellhub.foundation.context;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultRuntimeContextAccessor implements RuntimeContextAccessor {

  private final RuntimeContext runtimeContext;

  @Override
  public RuntimeContext runtimeContext() {
    return runtimeContext;
  }
}
