package io.github.stellhub.foundation.context;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.stellhub.foundation.constant.StellarHeaderNames;
import io.github.stellhub.foundation.header.MapHeaderAccessor;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class RequestContextResolverTest {

  private final RequestContextResolver resolver = new RequestContextResolver();

  @Test
  void shouldResolveHeadersAndMetadataIntoRequestContext() {
    RequestContext requestContext =
        resolver.resolve(
            new MapHeaderAccessor(
                Map.of(
                    StellarHeaderNames.X_STELLAR_REQUEST_ID,
                    List.of("req-001"),
                    StellarHeaderNames.TRACEPARENT,
                    List.of("00-abc-def-01"),
                    StellarHeaderNames.X_STELLAR_SOURCE_SERVICE,
                    List.of("gateway"),
                    StellarHeaderNames.X_FORWARDED_FOR,
                    List.of("10.0.0.8, 10.0.0.9"))),
            RequestMetadata.builder()
                .method("GET")
                .path("/orders/1")
                .scheme("https")
                .protocol("HTTP/1.1")
                .remoteAddress("127.0.0.1")
                .serverName("order-service")
                .serverPort(8443)
                .build());

    assertThat(requestContext.getRequestId()).isEqualTo("req-001");
    assertThat(requestContext.getTraceParent()).isEqualTo("00-abc-def-01");
    assertThat(requestContext.getSourceService()).isEqualTo("gateway");
    assertThat(requestContext.getClientIp()).isEqualTo("10.0.0.8");
    assertThat(requestContext.getRequestMethod()).isEqualTo("GET");
    assertThat(requestContext.header(StellarHeaderNames.X_STELLAR_SOURCE_SERVICE))
        .contains("gateway");
  }
}
