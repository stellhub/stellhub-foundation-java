package io.github.stellhub.foundation.header;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HeaderAccessor {

  /** 获取指定请求头的首个值。 */
  Optional<String> getFirst(String name);

  /** 获取归一化后的请求头快照。 */
  Map<String, List<String>> asMap();
}
