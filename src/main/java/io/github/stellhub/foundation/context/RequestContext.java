package io.github.stellhub.foundation.context;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class RequestContext {
  String requestId;
  String traceParent;
  String traceState;
  String baggage;
  String sessionId;
  String userId;
  String tenantId;
  String deviceId;
  String clientIp;
  String sourceService;
  String sourceRegion;
  String sourceEnv;
  String grayTag;
  String canaryTag;
  String requestMethod;
  String requestPath;
  String scheme;
  String protocol;
  String remoteAddress;
  String serverName;
  Integer serverPort;

  @Singular("header")
  Map<String, List<String>> headers;

  /** 按请求头名称读取首个值。 */
  public Optional<String> header(String name) {
    if (name == null) {
      return Optional.empty();
    }
    List<String> values = headers.get(name.toLowerCase());
    if (values == null || values.isEmpty()) {
      return Optional.empty();
    }
    String first = values.getFirst();
    return first == null || first.isBlank() ? Optional.empty() : Optional.of(first);
  }
}
