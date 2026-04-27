package io.github.stellhub.foundation.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StellarHeaderNames {

  public static final String TRACEPARENT = "traceparent";
  public static final String TRACESTATE = "tracestate";
  public static final String BAGGAGE = "baggage";
  public static final String X_REQUEST_ID = "X-Request-Id";
  public static final String X_FORWARDED_FOR = "X-Forwarded-For";

  public static final String X_STELLAR_REQUEST_ID = "X-Stellar-Request-Id";
  public static final String X_STELLAR_SESSION_ID = "X-Stellar-Session-Id";
  public static final String X_STELLAR_USER_ID = "X-Stellar-User-Id";
  public static final String X_STELLAR_TENANT_ID = "X-Stellar-Tenant-Id";
  public static final String X_STELLAR_DEVICE_ID = "X-Stellar-Device-Id";
  public static final String X_STELLAR_CLIENT_IP = "X-Stellar-Client-Ip";
  public static final String X_STELLAR_SOURCE_SERVICE = "X-Stellar-Source-Service";
  public static final String X_STELLAR_SOURCE_REGION = "X-Stellar-Source-Region";
  public static final String X_STELLAR_ENV = "X-Stellar-Env";
  public static final String X_STELLAR_GRAY_TAG = "X-Stellar-Gray-Tag";
  public static final String X_STELLAR_CANARY_TAG = "X-Stellar-Canary-Tag";
}
