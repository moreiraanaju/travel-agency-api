package com.example.travel_api;
import com.example.travel_api.service.TravelService;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;




@RestController
class TravelController {
    
    private final TravelRepository repository;
    private final TravelModelAssembler assembler;
    private final TravelService travelService;

    TravelController(TravelRepository repository, TravelModelAssembler assembler, TravelService travelService) {
        this.repository = repository;
        this.assembler = assembler;
        this.travelService = travelService;
    }


    // Agregate root

    
    //tag::get-aggregate-root[]

    // Retornar todos os destinos disponíveis
    @GetMapping("/travels")
    CollectionModel<EntityModel<Travel>> all() {

        List<EntityModel<Travel>> travels = travelService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

            return CollectionModel.of(travels, linkTo(methodOn(TravelController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // Create a new travel destination
    @PostMapping("/travels")
    Travel newTravel(@RequestBody Travel newTravel) {
        return travelService.create(newTravel);
    }


    // Single item

    // Search a travel destiny by name or location
    @GetMapping("/travels/search")
    public List<Travel> searchTravel(
        @RequestParam(required = false) String destinationName,
        @RequestParam(required = false) String location) {

        return travelService.search(destinationName, location);
    }

    // View detailed information about a specific travel destination 
    @GetMapping("/travels/{id}")
    EntityModel<Travel> viewTravel(@PathVariable Long id) {  //EntityModel<t> is a generic container from Spring HATEOAS that includes not only the data but a collection of links

        Travel travel = travelService.findById(id);
        return assembler.toModel(travel);
            
        
    }
   

    // Update all the information about a travel destination
    @PutMapping("/travels/{id}")  
    Travel replaceTravel(@RequestBody Travel newTravel, @PathVariable Long id) { 
        return travelService.replace(id, newTravel);
    }

    // Update the rating note of a travel destination
    @PatchMapping("/travels/{id}/rating")
    public Travel updateRating(@RequestBody RatingRequest request, @PathVariable Long id) {
        return travelService.updateRating(id, request.getRating());
    }

    // Delete a specific travel destination
    @DeleteMapping("/travels/{id}") 
    void deleteTravel(@PathVariable Long id) {
        repository.deleteById(id);
    }
    

}

