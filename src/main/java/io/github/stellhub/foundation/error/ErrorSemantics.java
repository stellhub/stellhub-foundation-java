package io.github.stellhub.foundation.error;

import io.github.stellhub.foundation.constant.StellarErrorAttributeNames;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ErrorSemantics {
  String errorType;
  String code;
  String domain;
  String reason;
  Boolean retryable;
  String message;

  /** 转换为日志属性集合。 */
  public Map<String, String> toLogAttributes() {
    return buildSharedAttributes(true);
  }

  /** 转换为 Trace 属性集合。 */
  public Map<String, String> toTraceAttributes() {
    return buildSharedAttributes(true);
  }

  /** 转换为默认 Metrics 属性集合。 */
  public Map<String, String> toMetricAttributes() {
    Map<String, String> attributes = new LinkedHashMap<>();
    putIfPresent(attributes, StellarErrorAttributeNames.ERROR_TYPE, errorType);
    return Map.copyOf(attributes);
  }

  private Map<String, String> buildSharedAttributes(boolean includeBusinessDimensions) {
    Map<String, String> attributes = new LinkedHashMap<>();
    putIfPresent(attributes, StellarErrorAttributeNames.ERROR_TYPE, errorType);
    if (includeBusinessDimensions) {
      putIfPresent(attributes, StellarErrorAttributeNames.STELLAR_ERROR_CODE, code);
      putIfPresent(attributes, StellarErrorAttributeNames.STELLAR_ERROR_DOMAIN, domain);
      putIfPresent(attributes, StellarErrorAttributeNames.STELLAR_ERROR_REASON, reason);
      if (retryable != null) {
        attributes.put(
            StellarErrorAttributeNames.STELLAR_ERROR_RETRYABLE, String.valueOf(retryable));
      }
    }
    return Map.copyOf(attributes);
  }

  private void putIfPresent(Map<String, String> attributes, String key, String value) {
    if (value != null && !value.isBlank()) {
      attributes.put(key, value);
    }
  }
}
