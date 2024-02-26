package com.bogovick.family.backend.location.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@Table(name = "district")
public class DistrictEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;
  @Column(nullable = false, length = 100)
  private String code;

  @ManyToOne
  @JoinColumn(name = "state_id")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private StateEntity state;

}