package io.github.stellhub.foundation.context;

import io.github.stellhub.foundation.constant.StellarHeaderNames;
import io.github.stellhub.foundation.header.HeaderAccessor;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RequestContextResolver {

  /** 仅基于请求头解析统一请求上下文。 */
  public RequestContext resolve(HeaderAccessor headerAccessor) {
    return resolve(headerAccessor, RequestMetadata.builder().build());
  }

  /** 基于请求头和请求元数据解析统一请求上下文。 */
  public RequestContext resolve(HeaderAccessor headerAccessor, RequestMetadata requestMetadata) {
    Map<String, List<String>> headers = headerAccessor.asMap();

    String requestId =
        firstNonBlank(
            headerAccessor.getFirst(StellarHeaderNames.X_STELLAR_REQUEST_ID).orElse(null),
            headerAccessor.getFirst(StellarHeaderNames.X_REQUEST_ID).orElse(null));
    String clientIp =
        firstNonBlank(
            headerAccessor.getFirst(StellarHeaderNames.X_STELLAR_CLIENT_IP).orElse(null),
            extractForwardedFor(headerAccessor),
            requestMetadata.getRemoteAddress());

    return RequestContext.builder()
        .requestId(requestId)
        .traceParent(headerAccessor.getFirst(StellarHeaderNames.TRACEPARENT).orElse(null))
        .traceState(headerAccessor.getFirst(StellarHeaderNames.TRACESTATE).orElse(null))
        .baggage(headerAccessor.getFirst(StellarHeaderNames.BAGGAGE).orElse(null))
        .sessionId(headerAccessor.getFirst(StellarHeaderNames.X_STELLAR_SESSION_ID).orElse(null))
        .userId(headerAccessor.getFirst(StellarHeaderNames.X_STELLAR_USER_ID).orElse(null))
        .tenantId(headerAccessor.getFirst(StellarHeaderNames.X_STELLAR_TENANT_ID).orElse(null))
        .deviceId(headerAccessor.getFirst(StellarHeaderNames.X_STELLAR_DEVICE_ID).orElse(null))
        .clientIp(clientIp)
        .sourceService(
            headerAccessor.getFirst(StellarHeaderNames.X_STELLAR_SOURCE_SERVICE).orElse(null))
        .sourceRegion(
            headerAccessor.getFirst(StellarHeaderNames.X_STELLAR_SOURCE_REGION).orElse(null))
        .sourceEnv(headerAccessor.getFirst(StellarHeaderNames.X_STELLAR_ENV).orElse(null))
        .grayTag(headerAccessor.getFirst(StellarHeaderNames.X_STELLAR_GRAY_TAG).orElse(null))
        .canaryTag(headerAccessor.getFirst(StellarHeaderNames.X_STELLAR_CANARY_TAG).orElse(null))
        .requestMethod(requestMetadata.getMethod())
        .requestPath(requestMetadata.getPath())
        .scheme(requestMetadata.getScheme())
        .protocol(requestMetadata.getProtocol())
        .remoteAddress(requestMetadata.getRemoteAddress())
        .serverName(requestMetadata.getServerName())
        .serverPort(requestMetadata.getServerPort())
        .headers(headers)
        .build();
  }

  private String extractForwardedFor(HeaderAccessor headerAccessor) {
    return headerAccessor
        .getFirst(StellarHeaderNames.X_FORWARDED_FOR)
        .map(
            value -> {
              String[] segments = value.split(",");
              return segments.length == 0 ? null : segments[0].trim();
            })
        .filter(Objects::nonNull)
        .filter(value -> !value.isBlank())
        .orElse(null);
  }

  private String firstNonBlank(String... values) {
    return java.util.Arrays.stream(values)
        .filter(Objects::nonNull)
        .map(String::trim)
        .filter(value -> !value.isEmpty())
        .findFirst()
        .orElse(null);
  }
}
