package com.avenga.testproject.service;

import com.avenga.testproject.dto.AdvertisementDto;
import com.avenga.testproject.entity.Advertisement;
import com.avenga.testproject.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    public Page<AdvertisementDto> getAdvertisement(Pageable pageable) {
        log.info("Searching for advertisement...");
        Page<Advertisement> advertisementPage = advertisementRepository.findAll(pageable);

        log.info("Find advertisement: {}", advertisementPage.getContent());
        return new PageImpl<>(advertisementPage.getContent().stream()
                .map(this::mapToAdvertisementDto).collect(Collectors.toList()),
                pageable, advertisementPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<AdvertisementDto> getAdvertisementBySender(String name) {
        log.info("Searching for advertisement...");
        List<AdvertisementDto> list = advertisementRepository.findAdvertisementBySenderName(name)
                .map(this::mapToAdvertisementDto)
                .collect(Collectors.toList());

        log.info("Find advertisement: {}", list);
        return list;
    }

    public AdvertisementDto getAdvertisementById(long id){
        log.info("Searching for advertisement...");
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Advertisement not found"));

        log.info("Find advertisement: {}", advertisement);
        return mapToAdvertisementDto(advertisement);
    }

    public AdvertisementDto createAdvertisement(AdvertisementDto advertisementDto) {
        log.info("Creating advertisement...");
        Advertisement saved = advertisementRepository.save(mapToAdvertisement(advertisementDto));
        log.info("Advertisement created");
        return mapToAdvertisementDto(saved);
    }

    public AdvertisementDto updateAdvertisement(long id, AdvertisementDto newAdvertisementDto) {
        log.info("Updating advertisement...");
        advertisementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        newAdvertisementDto.setId(id);
        advertisementRepository.save(mapToAdvertisement(newAdvertisementDto));
        log.info("Advertisement updated");
        return newAdvertisementDto;
    }

    public AdvertisementDto deleteAdvertisement(long id) {
        log.info("Deleting advertisement...");
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        advertisementRepository.delete(advertisement);
        log.info("Advertisement deleted");
        return mapToAdvertisementDto(advertisement);
    }

    private AdvertisementDto mapToAdvertisementDto(Advertisement advertisement) {
        AdvertisementDto advertisementDto = AdvertisementDto.builder()
                .id(advertisement.getId())
                .message(advertisement.getMessage())
                .senderName(advertisement.getSenderName())
                .build();

        return advertisementDto;
    }

    private Advertisement mapToAdvertisement(AdvertisementDto advertisementDto) {
        Advertisement advertisement = Advertisement.builder()
                .message(advertisementDto.getMessage())
                .senderName(advertisementDto.getSenderName())
                .build();
        if(advertisementDto.getId() != null){
            advertisement.setId(advertisementDto.getId());
        }
        return advertisement;
    }
}
