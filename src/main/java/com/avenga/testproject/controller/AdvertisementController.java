package com.avenga.testproject.controller;

import com.avenga.testproject.controller.assembler.AdvertisementAssembler;
import com.avenga.testproject.controller.model.AdvertisementModel;
import com.avenga.testproject.dto.AdvertisementDto;
import com.avenga.testproject.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/adv")
//@Api(tags = "Advertisement Api")
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    private final AdvertisementAssembler advertisementAssembler;

    @GetMapping
    @Operation(summary = "Get page of advertisement sorted by field message")
    public Page<AdvertisementDto> getAdvertisement(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 2, Sort.by("message"));
        Page<AdvertisementDto> advertisement = advertisementService.getAdvertisement(pageable);

        return advertisement;
    }

    @GetMapping(value = "/sender/{senderName}")
    @Operation(summary = "Get list of advertisement of one sender")
    public List<AdvertisementModel> getAdvertisementBySender(@PathVariable String senderName) {
        return advertisementService.getAdvertisementBySender(senderName)
                .stream().map(advertisementAssembler::toModel).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get advertisement by id")
    public AdvertisementModel getAdvertisementById(@PathVariable long id) {
        return advertisementAssembler.toModel(advertisementService.getAdvertisementById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new advertisement")
    public AdvertisementModel createAdvertisement(@RequestBody AdvertisementDto advertisementDto){
        AdvertisementDto advertisement = advertisementService.createAdvertisement(advertisementDto);
        return advertisementAssembler.toModel(advertisement);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Edit advertisement")
    public AdvertisementModel updateAdvertisement(@RequestBody AdvertisementDto advertisementDto,@PathVariable long id){
        AdvertisementDto dto = advertisementService.updateAdvertisement(id, advertisementDto);
        return advertisementAssembler.toModel(dto);
    }

    @Operation(summary = "Delete advertisement")
    @DeleteMapping(value = "/{id}")
    public AdvertisementDto deleteAdvertisement(@PathVariable long id){
        AdvertisementDto advertisementDto = advertisementService.deleteAdvertisement(id);
        return advertisementDto;
    }
}
