package io.github.stellhub.foundation.env;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public interface EnvironmentAccessor {

  /** 获取单个环境值。 */
  Optional<String> get(String name);

  /** 按顺序获取第一个非空环境值。 */
  default Optional<String> getAny(String... names) {
    return Arrays.stream(names).map(this::get).flatMap(Optional::stream).findFirst();
  }

  /** 批量抓取环境值快照。 */
  default Map<String, String> snapshot(Collection<String> names) {
    Map<String, String> values = new LinkedHashMap<>();
    names.forEach(name -> get(name).ifPresent(v -> values.put(name, v)));
    return Map.copyOf(values);
  }
}
