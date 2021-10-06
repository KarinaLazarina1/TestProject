package com.avenga.testproject.service;

import com.avenga.testproject.TestData;
import com.avenga.testproject.dto.AdvertisementDto;
import com.avenga.testproject.entity.Advertisement;
import com.avenga.testproject.repository.AdvertisementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.avenga.testproject.TestData.TEST_ID;
import static com.avenga.testproject.TestData.UPDATED_TEST_MESSAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceTest {
    @InjectMocks
    private AdvertisementService advertisementService;

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Test
    void getAdvertisementByIdTest() {
        Advertisement advertisement = TestData.createAdvertisement();
        when(advertisementRepository.findById(TEST_ID)).thenReturn(Optional.of(advertisement));

        AdvertisementDto advertisementDto = advertisementService.getAdvertisementById(TEST_ID);

        assertThat(advertisementDto, allOf(
                hasProperty("message", equalTo(advertisement.getMessage())),
                hasProperty("senderName", equalTo(advertisement.getSenderName()))
        ));
    }

    @Test
    void getAdvertisementByIdWhenAdvertisementNotFoundTest(){
        when(advertisementRepository.findById(TEST_ID)).thenReturn(Optional.empty());
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> advertisementService.getAdvertisementById(TEST_ID));

        assertThat(runtimeException.getMessage(), equalTo("Advertisement not found"));
    }

    @Test
    void createAdvertisementTest() {
        Advertisement advertisement = TestData.createAdvertisement();
        AdvertisementDto testAdvertisementDto = TestData.createAdvertisementDto();
        when(advertisementRepository.save(any())).thenReturn(advertisement);

        AdvertisementDto advertisementDto = advertisementService.createAdvertisement(testAdvertisementDto);

        assertThat(advertisementDto, allOf(
                hasProperty("id", equalTo(advertisement.getId())),
                hasProperty("message", equalTo(advertisement.getMessage())),
                hasProperty("senderName", equalTo(advertisement.getSenderName()))
        ));
    }

    @Test
    void updateAdvertisementTest(){
        AdvertisementDto updatedAdvertisementDto = TestData.createUpdatedAdvertisementDto();
        Advertisement advertisement = TestData.createAdvertisement();
        when(advertisementRepository.findById(TEST_ID)).thenReturn(Optional.of(advertisement));
        when(advertisementRepository.save(any())).thenReturn(advertisement);

        AdvertisementDto updated = advertisementService.updateAdvertisement(advertisement.getId(), updatedAdvertisementDto);

        assertThat(updated, allOf(
                hasProperty("id", equalTo(advertisement.getId())),
                hasProperty("message", equalTo(UPDATED_TEST_MESSAGE)),
                hasProperty("senderName", equalTo(advertisement.getSenderName()))
        ));
    }

    @Test
    void updateAdvertisementWhenAdvertisementNotFoundTest(){
        AdvertisementDto updatedAdvertisementDto = TestData.createUpdatedAdvertisementDto();
        when(advertisementRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> advertisementService.updateAdvertisement(TEST_ID, updatedAdvertisementDto));

        assertThat(runtimeException.getMessage(), equalTo("Advertisement not found"));
    }

    @Test
    void deleteAdvertisementTest(){
        Advertisement advertisement = TestData.createAdvertisement();
        when(advertisementRepository.findById(TEST_ID)).thenReturn(Optional.of(advertisement));

        advertisementService.deleteAdvertisement(advertisement.getId());
        verify(advertisementRepository, times(1)).delete(advertisement);
    }

    @Test
    void deleteAdvertisementWhenAdvertisementNotFoundTest(){
        when(advertisementRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> advertisementService.deleteAdvertisement(TEST_ID));

        assertThat(runtimeException.getMessage(), equalTo("Advertisement not found"));
    }


}
