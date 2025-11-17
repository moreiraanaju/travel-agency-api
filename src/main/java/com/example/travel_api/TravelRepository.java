// A Repository is a class responsible for communicating with the DB
// This is where CRUD operations would normally be defined, but Spring Data provides them automatically by default 

package com.example.travel_api;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel, Long> {   // JpaRepository<domainType, idType>

    List<Travel> findByDestinationNameContainingIgnoreCaseOrLocationContainingIgnoreCase (
        String destinationName,
        String location
    );
}

