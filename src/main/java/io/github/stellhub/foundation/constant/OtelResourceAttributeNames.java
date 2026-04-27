package io.github.stellhub.foundation.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OtelResourceAttributeNames {

  public static final String SERVICE_NAME = "service.name";
  public static final String SERVICE_NAMESPACE = "service.namespace";
  public static final String SERVICE_VERSION = "service.version";
  public static final String SERVICE_INSTANCE_ID = "service.instance.id";
  public static final String DEPLOYMENT_ENVIRONMENT_NAME = "deployment.environment.name";
  public static final String K8S_CLUSTER_NAME = "k8s.cluster.name";
  public static final String CLOUD_REGION = "cloud.region";
  public static final String CLOUD_AVAILABILITY_ZONE = "cloud.availability_zone";
  public static final String HOST_NAME = "host.name";
  public static final String HOST_IP = "host.ip";
  public static final String K8S_NODE_NAME = "k8s.node.name";
  public static final String K8S_NAMESPACE_NAME = "k8s.namespace.name";
  public static final String K8S_POD_NAME = "k8s.pod.name";
  public static final String K8S_POD_UID = "k8s.pod.uid";
  public static final String K8S_POD_IP = "k8s.pod.ip";
  public static final String K8S_CONTAINER_NAME = "k8s.container.name";
}
