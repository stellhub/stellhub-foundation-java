package io.github.stellhub.foundation.env;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class MapEnvironmentAccessor implements EnvironmentAccessor {

  private final Map<String, String> values;

  public MapEnvironmentAccessor(Map<String, String> values) {
    this.values = new LinkedHashMap<>(values);
  }

  @Override
  public Optional<String> get(String name) {
    return Optional.ofNullable(values.get(name))
        .map(String::trim)
        .filter(v -> !v.isEmpty());
  }
}
