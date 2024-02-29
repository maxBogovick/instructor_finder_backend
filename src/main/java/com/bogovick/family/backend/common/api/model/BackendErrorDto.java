package com.bogovick.family.backend.common.api.model;

import lombok.Builder;

@Builder
public record BackendErrorDto(BackendErrorCode errorCode, String errorMessage) {
  public enum BackendErrorCode {
    USERNAME_PASSWORD_ALREADY_EXISTS,
    UNKNOWN_EXCEPTION,
  }
}
