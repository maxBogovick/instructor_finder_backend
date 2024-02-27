package com.bogovick.family.backend.image.core.entity;

import com.bogovick.family.backend.image.api.model.ImageContentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "image_content")
@Getter
@Setter
@NoArgsConstructor
public class ImageContentEntity {
  @Id
  @UuidGenerator
  private UUID id;
  @Column(name = "file_name", nullable = false, length = 300)
  private String name; // original filename
  @Column(name = "content_type", nullable = true, length = 50)
  private String contentType; // MIME type
  private long size; // file size
  @Column(name = "file_path", nullable = false, length = 400)
  private String filePath;
  @Enumerated(EnumType.STRING)
  @Column(name = "profile_content_type", nullable = false, length = 20)
  private ImageContentType imageContentType;
  @Column(name = "image_order", nullable = false)
  private int imageOrder;
  @Column(name = "profile_id", nullable = false)
  private Long profileCardId;

}