package com.bogovick.family.backend.location.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "state")
@Data
public class StateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, length = 100)
  private String code;

  @ManyToOne(fetch = FetchType.LAZY)
  private CountryEntity country;

  @OneToMany(mappedBy = "state", orphanRemoval = true)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<DistrictEntity> districts = new ArrayList<>();
}
