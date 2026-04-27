package io.github.stellhub.foundation.context;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class RequestMetadata {
  String method;
  String path;
  String scheme;
  String protocol;
  String remoteAddress;
  String serverName;
  Integer serverPort;
}
