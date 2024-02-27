package com.bogovick.family.backend.instructor.controller;

import com.bogovick.family.backend.image.api.model.ImageContentDto;
import com.bogovick.family.backend.image.api.model.ImageContentType;
import com.bogovick.family.backend.instructor.api.model.InstructorFilterDTO;
import com.bogovick.family.backend.instructor.api.model.InstructorProfileDto;
import com.bogovick.family.backend.instructor.api.model.PhonesDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.bogovick.family.backend.instructor.controller.InstructorProfileControllerWireMockTest.EXPECTED_PORT;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=" + EXPECTED_PORT)
public class InstructorProfileControllerWireMockTest {
  public final static int EXPECTED_PORT = 9999;

  @Autowired
  private ObjectMapper objectMapper;

  private final RestClient restClient = RestClient.create("http://localhost:" + EXPECTED_PORT);

  private WireMockServer wireMockServer;
  private final Faker faker = new Faker();

  @BeforeEach
  void setup() {
    /*wireMockServer = new WireMockServer(8089); // Port should match the one your facade is configured to use
    wireMockServer.start();
    configureFor("localhost", 8089);
    stubFor(post(urlEqualTo("/api/instructor"))
        .withHeader("Content-Type", equalTo("application/json"))
        .withRequestBody(containing("expectedRequestBodyPart"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json"))); // Adjust the response body as needed*/
  }

  /*private static MockMvc mockMvc = MockMvcBuilders
      .standaloneSetup(new FileController())
      .build()*/

  @Test
  void testCreateInstructorProfile() throws IOException {
    //String profileJson = JsonUtil.loadJsonFromFile("instructor/profile/create/request/instrucktorProfileRequest.json");
    InstructorProfileDto profile = InstructorProfileDto.builder()
        .username(faker.name().fullName())
        .experience(faker.number().numberBetween(1, 30))
        .email(faker.internet().emailAddress())
        .vehicleName(faker.commerce().productName())
        .vehicleType("CAR") // Simplify for example. Could be randomized if needed.
        .vehicleYear(faker.number().numberBetween(2000, 2022))
        .transmissionType("AUTOMAT") // Simplify for example. Could be randomized if needed.
        .selectedDistricts(List.of("PRIMORSKY", "KYIVSKY", "MALYNOVSKY", "SUVOROVSKY")) // Example districts
        .instructorTransmissionTypes(List.of("AUTOMAT", "MANUAL"))
        .instructorVehicleTypes(List.of("CAR", "BUS"))
        .bio(faker.lorem().paragraph())
        .sex(faker.options().option("MAN", "WOMAN"))
        .privateInstructor(faker.bool().bool())
        .schoolInstructor(faker.bool().bool())
        .additionalBreak(faker.bool().bool())
        .phones(IntStream.range(0, 2).mapToObj(i -> new PhonesDto(faker.phoneNumber().phoneNumber())).collect(Collectors.toList()))
        /*.mainProfileImageUrl(faker.avatar().image())
        .vehicleImageUrls(IntStream.range(0, 2).mapToObj(i -> faker.internet().image()).collect(Collectors.toList()))
        .legalDocumentUrls(IntStream.range(0, 2).mapToObj(i -> faker.internet().url()).collect(Collectors.toList()))
        .driverIdUrls(IntStream.range(0, 2).mapToObj(i -> faker.internet().url()).collect(Collectors.toList()))*/
        .build();

    //String profileJson = objectMapper.writeValueAsString(profile);

    final InstructorProfileDto exchange = restClient.post().uri("/api/instructor")
        .contentType(MediaType.APPLICATION_JSON)
        .body(profile, ParameterizedTypeReference.forType(InstructorProfileDto.class))
        .accept(MediaType.APPLICATION_JSON)
        .exchange((req, response) -> {
          if (response.getStatusCode().equals(HttpStatus.OK)) {
            return objectMapper.readValue(response.getBody(), InstructorProfileDto.class);
          } else {
            throw new RuntimeException();
          }
        });
    Assertions.assertNotNull(exchange.id());
    Assertions.assertFalse(exchange.instructorTransmissionTypes().isEmpty());
    System.out.println(exchange);
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    final FileSystemResource part = new FileSystemResource("C:/Users/Maxim/Downloads/diagram.png");
    builder.part("file", part);

    final ImageContentDto imageDto = restClient.post().uri("/api/image/upload/" + exchange.id()
            + "/" + ImageContentType.AVATAR.name() + "/0")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(builder.build())
        .accept(MediaType.APPLICATION_JSON)
        .exchange((req, response) -> {
          if (response.getStatusCode().equals(HttpStatus.OK)) {
            return objectMapper.readValue(response.getBody(), ImageContentDto.class);
          } else {
            throw new RuntimeException();
          }
        });
    System.out.println(imageDto);

    final byte[] result = restClient.get().uri("/api/image/" + imageDto.id())
        .exchange((req, response) -> {
          if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody().readAllBytes();
          } else {
            throw new RuntimeException();
          }
        });
    Assertions.assertArrayEquals(part.getContentAsByteArray(), result);
  }

  @Test
  void search_success_test() {
    Collection<InstructorProfileDto> exchange = restClient.post().uri("/api/instructor/search")
        .contentType(MediaType.APPLICATION_JSON)
        .body(InstructorFilterDTO.builder().selectedDistricts(List.of("PRIMORSKY")).build(), ParameterizedTypeReference.forType(InstructorProfileDto.class))
        .accept(MediaType.APPLICATION_JSON)
        .exchange((req, response) -> {
          if (response.getStatusCode().equals(HttpStatus.OK)) {
            return objectMapper.readValue(response.getBody(), new TypeReference<>() {
            });
          } else {
            throw new RuntimeException();
          }
        });
    Assertions.assertNotNull(exchange);
    Assertions.assertFalse(exchange.isEmpty());

    exchange = restClient.post().uri("/api/instructor/search")
        .contentType(MediaType.APPLICATION_JSON)
        .body(InstructorFilterDTO.builder()
            .selectedDistricts(List.of("PRIMORSKY"))
            .vehicleType("CAR_WITH_TRAILER")
            .build(), ParameterizedTypeReference.forType(InstructorProfileDto.class))
        .accept(MediaType.APPLICATION_JSON)
        .exchange((req, response) -> {
          if (response.getStatusCode().equals(HttpStatus.OK)) {
            return objectMapper.readValue(response.getBody(), new TypeReference<>() {
            });
          } else {
            throw new RuntimeException();
          }
        });
    Assertions.assertNotNull(exchange);
    Assertions.assertTrue(exchange.isEmpty());
  }
}
