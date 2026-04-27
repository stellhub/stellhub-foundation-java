package io.github.stellhub.foundation.env;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OtelResourceAttributesParserTest {

  private final OtelResourceAttributesParser parser = new OtelResourceAttributesParser();

  @Test
  void shouldParseEscapedResourceAttributes() {
    Map<String, String> attributes =
        parser.parse(
            "service.name=order-service,k8s.namespace.name=trade,custom.value=hello\\,world");

    assertThat(attributes)
        .containsEntry("service.name", "order-service")
        .containsEntry("k8s.namespace.name", "trade")
        .containsEntry("custom.value", "hello,world");
  }
}
