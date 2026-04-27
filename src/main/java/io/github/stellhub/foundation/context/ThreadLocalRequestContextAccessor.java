package io.github.stellhub.foundation.context;

import java.util.Optional;

public class ThreadLocalRequestContextAccessor implements RequestContextAccessor {

  @Override
  public Optional<RequestContext> currentRequestContext() {
    return RequestContextHolder.get();
  }
}
