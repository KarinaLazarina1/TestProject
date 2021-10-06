package com.avenga.testproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AdvertisementDto {
    private Long id;
    private String message;
    private String senderName;
}
