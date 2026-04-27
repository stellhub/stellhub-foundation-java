package io.github.stellhub.foundation.context;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class RuntimeContextOverrides {
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
}
