package io.github.stellhub.foundation.env;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class SystemEnvironmentAccessor implements EnvironmentAccessor {

  private final Map<String, String> environment;
  private final Properties systemProperties;

  public SystemEnvironmentAccessor(Map<String, String> environment, Properties systemProperties) {
    this.environment = new LinkedHashMap<>(environment);
    this.systemProperties = systemProperties;
  }

  @Override
  public Optional<String> get(String name) {
    String environmentValue = environment.get(name);
    if (environmentValue != null && !environmentValue.trim().isEmpty()) {
      return Optional.of(environmentValue.trim());
    }

    String systemPropertyValue = systemProperties.getProperty(name);
    if (systemPropertyValue != null && !systemPropertyValue.trim().isEmpty()) {
      return Optional.of(systemPropertyValue.trim());
    }

    return Optional.empty();
  }
}
