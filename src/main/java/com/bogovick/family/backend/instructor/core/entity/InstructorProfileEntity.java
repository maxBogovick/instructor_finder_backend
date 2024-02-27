package com.bogovick.family.backend.instructor.core.entity;

import com.bogovick.family.backend.instructor.api.model.TransmissionType;
import com.bogovick.family.backend.instructor.api.model.VehicleType;
import com.bogovick.family.backend.location.core.entity.DistrictEntity;
import com.bogovick.family.backend.util.core.CollectionToStringConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructor_profile")
@Getter
@Setter
public class InstructorProfileEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String username;
  @Column(nullable = false)
  private Integer experience;
  @Column(nullable = false, length = 100)
  private String email;
  @Column(nullable = false, length = 100)
  private String vehicleName;
  @Column(nullable = false, length = 30)
  private String vehicleType;
  @Column(nullable = false)
  private Integer vehicleYear;

  @Column(nullable = false, length = 20, name = "vehicle_transmission_type")
  @Enumerated(EnumType.STRING)
  private TransmissionType vehicleTransmissionType;

  @Fetch(FetchMode.JOIN)
  @BatchSize(size = 100)
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "instructor_district",
      joinColumns = @JoinColumn(name = "instructor_id"),
      inverseJoinColumns = @JoinColumn(name = "district_id")
  )
  @Column(nullable = false)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<DistrictEntity> selectedDistricts = new ArrayList<>();

  @Fetch(FetchMode.SUBSELECT)
  @BatchSize(size = 100)
  @ElementCollection(targetClass = TransmissionType.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "instructor_transmission_type", joinColumns = @JoinColumn(name = "instructor_id"))
  @Column(name = "transmission_type", length = 25, nullable = false) // Column to store the TransmissionType enums
  @Enumerated(EnumType.STRING)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<TransmissionType> transmissionTypes;

  @Fetch(FetchMode.SUBSELECT)
  @BatchSize(size = 100)
  @ElementCollection(targetClass = VehicleType.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "instructor_vehicle_type", joinColumns = @JoinColumn(name = "instructor_id"))
  @Column(name = "vehicle_type", length = 25, nullable = false) // Column to store the TransmissionType enums
  @Enumerated(EnumType.STRING)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<VehicleType> vehicleTypes = new ArrayList<>();

  @Column(nullable = true, length = 3000)
  private String bio;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private SexType sex;
  private Boolean privateInstructor;
  private Boolean schoolInstructor;
  private Boolean additionalBreak;

  @Convert(converter = CollectionToStringConverter.class)
  @Column(nullable = false, length = 300, name = "phones")
  private List<String> phones = new ArrayList<>();

  /*@Column(nullable = false, length = 300)
  private String mainProfileImageUrl;*/

  /*@Convert(converter = CollectionToStringConverter.class)
  @Column(nullable = false, length = 3000, name = "vehicle_image_urls")
  private List<String> vehicleImageUrls = new ArrayList<>();

  @Convert(converter = CollectionToStringConverter.class)
  @Column(nullable = false, length = 1000, name = "legal_document_urls")
  private List<String> legalDocumentUrls = new ArrayList<>();

  @Convert(converter = CollectionToStringConverter.class)
  @Column(nullable = false, length = 600, name = "driver_id_urls")
  private List<String> driverIdUrls = new ArrayList<>();*/

  @Column(name = "created_user", length = 300)
  private String createdUserInfo;

  @CreationTimestamp
  private LocalDateTime createdDate;

  @UpdateTimestamp
  private LocalDateTime updatedDate;

}

