package io.github.stellhub.foundation.context;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.stellhub.foundation.constant.OtelResourceAttributeNames;
import io.github.stellhub.foundation.constant.StellarEnvironmentNames;
import io.github.stellhub.foundation.env.MapEnvironmentAccessor;
import io.github.stellhub.foundation.env.OtelResourceAttributesParser;
import java.util.Map;
import org.junit.jupiter.api.Test;

class RuntimeContextResolverTest {

  @Test
  void shouldPreferOverridesThenOtelAndFallbackToStellarAliases() {
    Map<String, String> environment =
        Map.of(
            StellarEnvironmentNames.OTEL_SERVICE_NAME, "billing-service",
            StellarEnvironmentNames.OTEL_RESOURCE_ATTRIBUTES,
                "service.namespace=stellar.trade,service.version=1.0.0,k8s.namespace.name=pay",
            StellarEnvironmentNames.STELLAR_APP_INSTANCE_ID, "instance-from-stellar",
            StellarEnvironmentNames.POD_NAME, "billing-7fd9f76d7f-x2c2h",
            StellarEnvironmentNames.NODE_NAME, "worker-01");

    RuntimeContextResolver resolver =
        new RuntimeContextResolver(
            new MapEnvironmentAccessor(environment), new OtelResourceAttributesParser());

    RuntimeContext runtimeContext =
        resolver.resolve(RuntimeContextOverrides.builder().serviceVersion("2.1.3").build());

    assertThat(runtimeContext.getServiceName()).isEqualTo("billing-service");
    assertThat(runtimeContext.getServiceNamespace()).isEqualTo("stellar.trade");
    assertThat(runtimeContext.getServiceVersion()).isEqualTo("2.1.3");
    assertThat(runtimeContext.getServiceInstanceId()).isEqualTo("instance-from-stellar");
    assertThat(runtimeContext.getK8sNamespaceName()).isEqualTo("pay");
    assertThat(runtimeContext.getK8sPodName()).isEqualTo("billing-7fd9f76d7f-x2c2h");
    assertThat(runtimeContext.attribute(OtelResourceAttributeNames.K8S_NODE_NAME))
        .contains("worker-01");
  }
}
