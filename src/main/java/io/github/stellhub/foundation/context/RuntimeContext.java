package io.github.stellhub.foundation.context;

import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class RuntimeContext {
  String serviceName;
  String serviceNamespace;
  String serviceVersion;
  String serviceInstanceId;
  String deploymentEnvironmentName;
  String k8sClusterName;
  String cloudRegion;
  String cloudAvailabilityZone;
  String hostName;
  String hostIp;
  String k8sNodeName;
  String k8sNamespaceName;
  String k8sPodName;
  String k8sPodUid;
  String k8sPodIp;
  String k8sContainerName;

  @Singular("resourceAttribute")
  Map<String, String> resourceAttributes;

  /** 按标准属性名读取资源值。 */
  public Optional<String> attribute(String name) {
    return Optional.ofNullable(resourceAttributes.get(name));
  }
}
