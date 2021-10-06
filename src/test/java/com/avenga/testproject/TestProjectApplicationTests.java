package com.avenga.testproject;

import com.avenga.testproject.controller.model.AdvertisementModel;
import com.avenga.testproject.dto.AdvertisementDto;
import com.avenga.testproject.repository.AdvertisementRepository;
import com.avenga.testproject.service.AdvertisementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class TestProjectApplicationTests {
    @Value("http://localhost:${local.server.port}/")
    private String baseURL;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    public TestRestTemplate testRestTemplate;

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:11")
            .withPassword("inmemory")
            .withUsername("inmemory");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Test
    public void getAdvertisementByIdTest() {
        AdvertisementModel advertisementModel = testRestTemplate
                .getForObject(baseURL + "/adv/1", AdvertisementModel.class);
        assertEquals(advertisementModel.getAdvertisementDto().getId(), 1);
    }

    @Test
    public void getAdvertisementListBySenderNameTest() {
        AdvertisementModel[] advertisementModels = testRestTemplate
                .getForObject(baseURL + "/adv/sender/user1", AdvertisementModel[].class);
        assertEquals("user1", advertisementModels[0].getAdvertisementDto().getSenderName());
    }

    @Test
    public void creteAdvertisementTest() {
        AdvertisementDto advertisementDto = AdvertisementDto.builder().message("mes2").senderName("user4").build();

        HttpEntity<AdvertisementDto> request = new HttpEntity<>(advertisementDto);
        AdvertisementModel responseEntity = testRestTemplate
                .postForObject(baseURL + "/adv", request, AdvertisementModel.class);

        assertThat(responseEntity, notNullValue());
        assertEquals("mes2", responseEntity.getAdvertisementDto().getMessage());

    }

    @Test
    public void updateAdvertisementTest() {
        AdvertisementDto updated = AdvertisementDto.builder().message("newMes").senderName("user1").build();

        HttpEntity<AdvertisementDto> request = new HttpEntity<>(updated);
        ResponseEntity<AdvertisementModel> entity = testRestTemplate
                .exchange(baseURL + "/adv/1", HttpMethod.PUT, request, AdvertisementModel.class);

        assertThat(entity, notNullValue());
        assertEquals(1, entity.getBody().getAdvertisementDto().getId());
        assertEquals(updated.getMessage(), entity.getBody().getAdvertisementDto().getMessage());
    }

    @Test
    public void deleteAdvertisementTest() {
        testRestTemplate.delete(baseURL + "/adv/1");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> advertisementService.deleteAdvertisement(1));
        assertEquals("Advertisement not found", exception.getMessage());
    }


}
