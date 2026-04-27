package io.github.stellhub.foundation.header;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MapHeaderAccessor implements HeaderAccessor {

  private final Map<String, List<String>> headers;

  public MapHeaderAccessor(Map<String, ? extends Collection<String>> headers) {
    Map<String, List<String>> normalizedHeaders = new LinkedHashMap<>();
    headers.forEach(
        (name, values) -> {
          if (name == null || values == null) {
            return;
          }
          List<String> normalizedValues =
              values.stream()
                  .filter(v -> v != null && !v.isBlank())
                  .map(String::trim)
                  .toList();
          if (!normalizedValues.isEmpty()) {
            normalizedHeaders.put(name.toLowerCase(), new ArrayList<>(normalizedValues));
          }
        });
    this.headers = Map.copyOf(normalizedHeaders);
  }

  @Override
  public Optional<String> getFirst(String name) {
    if (name == null) {
      return Optional.empty();
    }
    List<String> values = headers.get(name.toLowerCase());
    if (values == null || values.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(values.getFirst());
  }

  @Override
  public Map<String, List<String>> asMap() {
    return headers;
  }
}
