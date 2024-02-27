package com.bogovick.family.backend.image.api.model;

public enum ImageContentType {
  AVATAR, VEHICLE, LEGAL_DOCUMENT, DRIVER_CARD;

  public String getImageStorage() {
    return name().toLowerCase();
  }
}
