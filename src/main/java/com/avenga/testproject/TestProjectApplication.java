package com.avenga.testproject;

import com.avenga.testproject.entity.Advertisement;
import com.avenga.testproject.repository.AdvertisementRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition
public class TestProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestProjectApplication.class, args);
    }

    private void insertFour(AdvertisementRepository repository) {
        repository.save(Advertisement.builder().message("mes1").senderName("user1").build());
        repository.save(Advertisement.builder().message("mes2").senderName("user1").build());
        repository.save(Advertisement.builder().message("mes3").senderName("user2").build());
        repository.save(Advertisement.builder().message("mes4").senderName("user1").build());
    }

    @Bean
    public CommandLineRunner run(AdvertisementRepository repository) {
        return (args) -> {
            insertFour(repository);
            System.out.println(repository.findAll());
        };
    }

}
