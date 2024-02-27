package com.bogovick.family.backend.image.core.repository;

import com.bogovick.family.backend.image.api.model.ImageContentType;
import com.bogovick.family.backend.image.core.entity.ImageContentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageContentRepository extends ListCrudRepository<ImageContentEntity, UUID> {

  @Query("""
      select i.id from ImageContentEntity i where i.imageContentType = :type and i.profileCardId = :profileId
      
      """)
  Optional<UUID> getBy(@Param("type") ImageContentType imageContentType, @Param("profileId") Long profileId);
  @Query("""
      select i.id from ImageContentEntity i where i.imageContentType = :type and i.profileCardId = :profileId
      """)
  List<UUID> getAllBy(@Param("type") ImageContentType imageContentType, @Param("profileId") Long profileId);
}
