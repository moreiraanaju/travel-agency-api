package com.example.travel_api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class TravelModelAssembler implements RepresentationModelAssembler<Travel, EntityModel<Travel>> {

  @Override
  public EntityModel<Travel> toModel(Travel travel) {  // it converts a non-model object (Travel) into a model-based object (EntityModel<Travel>)

    return EntityModel.of(travel, //
        linkTo(methodOn(TravelController.class).viewTravel(travel.getId())).withSelfRel(),
        linkTo(methodOn(TravelController.class).all()).withRel("travels"));
  }
}
