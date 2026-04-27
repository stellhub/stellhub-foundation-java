package io.github.stellhub.foundation.env;

import java.util.LinkedHashMap;
import java.util.Map;

public class OtelResourceAttributesParser {

  /** 解析 OTEL_RESOURCE_ATTRIBUTES 字符串。 */
  public Map<String, String> parse(String rawAttributes) {
    if (rawAttributes == null || rawAttributes.isBlank()) {
      return Map.of();
    }

    Map<String, String> attributes = new LinkedHashMap<>();
    StringBuilder token = new StringBuilder();
    boolean escaped = false;

    for (int i = 0; i < rawAttributes.length(); i++) {
      char current = rawAttributes.charAt(i);
      if (escaped) {
        token.append(current);
        escaped = false;
        continue;
      }
      if (current == '\\') {
        escaped = true;
        continue;
      }
      if (current == ',') {
        addEntry(attributes, token.toString());
        token.setLength(0);
        continue;
      }
      token.append(current);
    }

    addEntry(attributes, token.toString());
    return Map.copyOf(attributes);
  }

  private void addEntry(Map<String, String> attributes, String token) {
    if (token == null || token.isBlank()) {
      return;
    }

    int separatorIndex = findSeparatorIndex(token);
    if (separatorIndex < 0) {
      return;
    }

    String key = unescape(token.substring(0, separatorIndex)).trim();
    String value = unescape(token.substring(separatorIndex + 1)).trim();
    if (!key.isEmpty() && !value.isEmpty()) {
      attributes.put(key, value);
    }
  }

  private int findSeparatorIndex(String token) {
    boolean escaped = false;
    for (int i = 0; i < token.length(); i++) {
      char current = token.charAt(i);
      if (escaped) {
        escaped = false;
        continue;
      }
      if (current == '\\') {
        escaped = true;
        continue;
      }
      if (current == '=') {
        return i;
      }
    }
    return -1;
  }

  private String unescape(String value) {
    StringBuilder output = new StringBuilder();
    boolean escaped = false;
    for (int i = 0; i < value.length(); i++) {
      char current = value.charAt(i);
      if (escaped) {
        output.append(current);
        escaped = false;
        continue;
      }
      if (current == '\\') {
        escaped = true;
        continue;
      }
      output.append(current);
    }
    return output.toString();
  }
}
