package com.avenga.testproject.controller.model;

import com.avenga.testproject.dto.AdvertisementDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class AdvertisementModel extends RepresentationModel<AdvertisementModel> {
    @JsonUnwrapped
    private AdvertisementDto advertisementDto;
}
