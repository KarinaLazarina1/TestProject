package com.avenga.testproject;

import com.avenga.testproject.dto.AdvertisementDto;
import com.avenga.testproject.entity.Advertisement;

public class TestData {
    static final String TEST_MESSAGE = "Message1";
    public static final String UPDATED_TEST_MESSAGE = "New Message";
    static final String TEST_SENDER = "User";
    public static final long TEST_ID = 1;

    public static Advertisement createAdvertisement(){
        return Advertisement.builder()
                .id(TEST_ID)
                .message(TEST_MESSAGE)
                .senderName(TEST_SENDER)
                .build();
    }

    public static AdvertisementDto createAdvertisementDto(){
        return AdvertisementDto.builder()
                .id(TEST_ID)
                .message(TEST_MESSAGE)
                .senderName(TEST_SENDER)
                .build();
    }

    public static AdvertisementDto createUpdatedAdvertisementDto(){
        return AdvertisementDto.builder()
                .message(UPDATED_TEST_MESSAGE)
                .senderName(TEST_SENDER)
                .build();
    }
}
