package com.bogovick.family.backend.image.api.model;

import java.util.UUID;

public record ImageContentDto(UUID id, String name, String contentType, long size, String filePath,
                              String profileCardId) {
}
