package io.github.stellhub.foundation.context;

import io.github.stellhub.foundation.constant.OtelResourceAttributeNames;
import io.github.stellhub.foundation.constant.StellarEnvironmentNames;
import io.github.stellhub.foundation.env.EnvironmentAccessor;
import io.github.stellhub.foundation.env.OtelResourceAttributesParser;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RuntimeContextResolver {

  private final EnvironmentAccessor environmentAccessor;
  private final OtelResourceAttributesParser otelResourceAttributesParser;

  public RuntimeContextResolver(
      EnvironmentAccessor environmentAccessor,
      OtelResourceAttributesParser otelResourceAttributesParser) {
    this.environmentAccessor = environmentAccessor;
    this.otelResourceAttributesParser = otelResourceAttributesParser;
  }

  /** 解析当前进程的统一运行时上下文。 */
  public RuntimeContext resolve() {
    return resolve(RuntimeContextOverrides.builder().build());
  }

  /** 基于显式覆盖项解析运行时上下文。 */
  public RuntimeContext resolve(RuntimeContextOverrides overrides) {
    Map<String, String> otelAttributes =
        otelResourceAttributesParser.parse(
            environmentAccessor.get(StellarEnvironmentNames.OTEL_RESOURCE_ATTRIBUTES).orElse(null));

    String serviceName =
        firstNonBlank(
            overrides.getServiceName(),
            environmentAccessor.get(StellarEnvironmentNames.OTEL_SERVICE_NAME).orElse(null),
            otelAttributes.get(OtelResourceAttributeNames.SERVICE_NAME),
            fromEnvironmentAliases(List.of(StellarEnvironmentNames.STELLAR_APP_NAME)));
    String serviceNamespace =
        resolveAttribute(
            overrides.getServiceNamespace(),
            OtelResourceAttributeNames.SERVICE_NAMESPACE,
            otelAttributes,
            List.of(
                StellarEnvironmentNames.SERVICE_NAMESPACE,
                StellarEnvironmentNames.STELLAR_APP_NAMESPACE));
    String serviceVersion =
        resolveAttribute(
            overrides.getServiceVersion(),
            OtelResourceAttributeNames.SERVICE_VERSION,
            otelAttributes,
            List.of(
                StellarEnvironmentNames.SERVICE_VERSION,
                StellarEnvironmentNames.STELLAR_APP_VERSION));
    String podUid =
        resolveAttribute(
            overrides.getK8sPodUid(),
            OtelResourceAttributeNames.K8S_POD_UID,
            otelAttributes,
            List.of(StellarEnvironmentNames.POD_UID, StellarEnvironmentNames.STELLAR_POD_UID));
    String serviceInstanceId =
        firstNonBlank(
            overrides.getServiceInstanceId(),
            otelAttributes.get(OtelResourceAttributeNames.SERVICE_INSTANCE_ID),
            fromEnvironmentAliases(
                List.of(
                    StellarEnvironmentNames.SERVICE_INSTANCE_ID,
                    StellarEnvironmentNames.STELLAR_APP_INSTANCE_ID)),
            podUid);
    String deploymentEnvironmentName =
        resolveAttribute(
            overrides.getDeploymentEnvironmentName(),
            OtelResourceAttributeNames.DEPLOYMENT_ENVIRONMENT_NAME,
            otelAttributes,
            List.of(
                StellarEnvironmentNames.DEPLOYMENT_ENVIRONMENT_NAME,
                StellarEnvironmentNames.STELLAR_ENV));
    String k8sClusterName =
        resolveAttribute(
            overrides.getK8sClusterName(),
            OtelResourceAttributeNames.K8S_CLUSTER_NAME,
            otelAttributes,
            List.of(
                StellarEnvironmentNames.K8S_CLUSTER_NAME,
                StellarEnvironmentNames.CLUSTER_NAME,
                StellarEnvironmentNames.STELLAR_CLUSTER));
    String cloudRegion =
        resolveAttribute(
            overrides.getCloudRegion(),
            OtelResourceAttributeNames.CLOUD_REGION,
            otelAttributes,
            List.of(
                StellarEnvironmentNames.CLOUD_REGION,
                StellarEnvironmentNames.REGION,
                StellarEnvironmentNames.STELLAR_REGION));
    String cloudAvailabilityZone =
        resolveAttribute(
            overrides.getCloudAvailabilityZone(),
            OtelResourceAttributeNames.CLOUD_AVAILABILITY_ZONE,
            otelAttributes,
            List.of(
                StellarEnvironmentNames.CLOUD_AVAILABILITY_ZONE,
                StellarEnvironmentNames.ZONE,
                StellarEnvironmentNames.STELLAR_ZONE));
    String hostName =
        resolveAttribute(
            overrides.getHostName(),
            OtelResourceAttributeNames.HOST_NAME,
            otelAttributes,
            List.of(
                StellarEnvironmentNames.HOST_NAME,
                StellarEnvironmentNames.HOSTNAME,
                StellarEnvironmentNames.STELLAR_HOST_NAME));
    String hostIp =
        resolveAttribute(
            overrides.getHostIp(),
            OtelResourceAttributeNames.HOST_IP,
            otelAttributes,
            List.of(StellarEnvironmentNames.HOST_IP, StellarEnvironmentNames.STELLAR_HOST_IP));
    String k8sNodeName =
        resolveAttribute(
            overrides.getK8sNodeName(),
            OtelResourceAttributeNames.K8S_NODE_NAME,
            otelAttributes,
            List.of(StellarEnvironmentNames.NODE_NAME, StellarEnvironmentNames.STELLAR_NODE_NAME));
    String k8sNamespaceName =
        resolveAttribute(
            overrides.getK8sNamespaceName(),
            OtelResourceAttributeNames.K8S_NAMESPACE_NAME,
            otelAttributes,
            List.of(
                StellarEnvironmentNames.K8S_NAMESPACE,
                StellarEnvironmentNames.POD_NAMESPACE,
                StellarEnvironmentNames.STELLAR_K8S_NAMESPACE));
    String k8sPodName =
        resolveAttribute(
            overrides.getK8sPodName(),
            OtelResourceAttributeNames.K8S_POD_NAME,
            otelAttributes,
            List.of(StellarEnvironmentNames.POD_NAME, StellarEnvironmentNames.STELLAR_POD_NAME));
    String k8sPodIp =
        resolveAttribute(
            overrides.getK8sPodIp(),
            OtelResourceAttributeNames.K8S_POD_IP,
            otelAttributes,
            List.of(StellarEnvironmentNames.POD_IP, StellarEnvironmentNames.STELLAR_POD_IP));
    String k8sContainerName =
        resolveAttribute(
            overrides.getK8sContainerName(),
            OtelResourceAttributeNames.K8S_CONTAINER_NAME,
            otelAttributes,
            List.of(
                StellarEnvironmentNames.CONTAINER_NAME,
                StellarEnvironmentNames.STELLAR_CONTAINER_NAME));

    Map<String, String> resourceAttributes = new LinkedHashMap<>();
    putIfPresent(resourceAttributes, OtelResourceAttributeNames.SERVICE_NAME, serviceName);
    putIfPresent(
        resourceAttributes, OtelResourceAttributeNames.SERVICE_NAMESPACE, serviceNamespace);
    putIfPresent(resourceAttributes, OtelResourceAttributeNames.SERVICE_VERSION, serviceVersion);
    putIfPresent(
        resourceAttributes, OtelResourceAttributeNames.SERVICE_INSTANCE_ID, serviceInstanceId);
    putIfPresent(
        resourceAttributes,
        OtelResourceAttributeNames.DEPLOYMENT_ENVIRONMENT_NAME,
        deploymentEnvironmentName);
    putIfPresent(resourceAttributes, OtelResourceAttributeNames.K8S_CLUSTER_NAME, k8sClusterName);
    putIfPresent(resourceAttributes, OtelResourceAttributeNames.CLOUD_REGION, cloudRegion);
    putIfPresent(
        resourceAttributes,
        OtelResourceAttributeNames.CLOUD_AVAILABILITY_ZONE,
        cloudAvailabilityZone);
    putIfPresent(resourceAttributes, OtelResourceAttributeNames.HOST_NAME, hostName);
    putIfPresent(resourceAttributes, OtelResourceAttributeNames.HOST_IP, hostIp);
    putIfPresent(resourceAttributes, OtelResourceAttributeNames.K8S_NODE_NAME, k8sNodeName);
    putIfPresent(
        resourceAttributes, OtelResourceAttributeNames.K8S_NAMESPACE_NAME, k8sNamespaceName);
    putIfPresent(resourceAttributes, OtelResourceAttributeNames.K8S_POD_NAME, k8sPodName);
    putIfPresent(resourceAttributes, OtelResourceAttributeNames.K8S_POD_UID, podUid);
    putIfPresent(resourceAttributes, OtelResourceAttributeNames.K8S_POD_IP, k8sPodIp);
    putIfPresent(
        resourceAttributes, OtelResourceAttributeNames.K8S_CONTAINER_NAME, k8sContainerName);

    return RuntimeContext.builder()
        .serviceName(serviceName)
        .serviceNamespace(serviceNamespace)
        .serviceVersion(serviceVersion)
        .serviceInstanceId(serviceInstanceId)
        .deploymentEnvironmentName(deploymentEnvironmentName)
        .k8sClusterName(k8sClusterName)
        .cloudRegion(cloudRegion)
        .cloudAvailabilityZone(cloudAvailabilityZone)
        .hostName(hostName)
        .hostIp(hostIp)
        .k8sNodeName(k8sNodeName)
        .k8sNamespaceName(k8sNamespaceName)
        .k8sPodName(k8sPodName)
        .k8sPodUid(podUid)
        .k8sPodIp(k8sPodIp)
        .k8sContainerName(k8sContainerName)
        .resourceAttributes(Map.copyOf(resourceAttributes))
        .build();
  }

  private String resolveAttribute(
      String overrideValue,
      String otelAttributeName,
      Map<String, String> otelAttributes,
      List<String> environmentAliases) {
    return firstNonBlank(
        overrideValue,
        otelAttributes.get(otelAttributeName),
        fromEnvironmentAliases(environmentAliases));
  }

  private String fromEnvironmentAliases(List<String> aliases) {
    return aliases.stream()
        .map(environmentAccessor::get)
        .flatMap(java.util.Optional::stream)
        .findFirst()
        .orElse(null);
  }

  private String firstNonBlank(String... values) {
    return java.util.Arrays.stream(values)
        .filter(Objects::nonNull)
        .map(String::trim)
        .filter(v -> !v.isEmpty())
        .findFirst()
        .orElse(null);
  }

  private void putIfPresent(Map<String, String> target, String key, String value) {
    if (value != null && !value.isBlank()) {
      target.put(key, value);
    }
  }
}
