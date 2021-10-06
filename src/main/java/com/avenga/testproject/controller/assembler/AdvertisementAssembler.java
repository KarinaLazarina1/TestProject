package com.avenga.testproject.controller.assembler;

import com.avenga.testproject.controller.AdvertisementController;
import com.avenga.testproject.controller.model.AdvertisementModel;
import com.avenga.testproject.dto.AdvertisementDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdvertisementAssembler extends RepresentationModelAssemblerSupport<AdvertisementDto, AdvertisementModel> {
    public static final String GET_ADVERTISEMENT = "get_advertisement";
    public static final String GET_ADVERTISEMENT_BY_SENDER_NAME = "get_advertisement_by_sender_name";
    public static final String CREATE_ADVERTISEMENT = "create_advertisement";
    public static final String UPDATE_ADVERTISEMENT = "update_advertisement";
    public static final String DELETE_ADVERTISEMENT = "delete_advertisement";

    public AdvertisementAssembler() {
        super(AdvertisementDto.class, AdvertisementModel.class);
    }

    @Override
    public AdvertisementModel toModel(AdvertisementDto entity) {
        AdvertisementModel advertisementModel = new AdvertisementModel(entity);

        Link get = linkTo(methodOn(AdvertisementController.class).getAdvertisement(0)).withRel(GET_ADVERTISEMENT);
        Link getByName = linkTo(methodOn(AdvertisementController.class).getAdvertisementBySender(entity.getSenderName())).withRel(GET_ADVERTISEMENT_BY_SENDER_NAME);
        Link post = linkTo(methodOn(AdvertisementController.class).createAdvertisement(entity)).withRel(CREATE_ADVERTISEMENT);
        Link put = linkTo(methodOn(AdvertisementController.class).updateAdvertisement(entity, entity.getId())).withRel(UPDATE_ADVERTISEMENT);
        Link delete = linkTo(methodOn(AdvertisementController.class).deleteAdvertisement(entity.getId())).withRel(DELETE_ADVERTISEMENT);

        return advertisementModel.add(get, getByName, post, put, delete);
    }
}
