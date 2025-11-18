// Aqui é definido o modelo (estrtutura) da tabela Travel
package com.example.travel_api;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Travel {

    @Id  //identifies the field as the primary key
    @GeneratedValue // this tells JPA to auto-generate the ID
    private Long  id;

    private String destinationName;
    private LocalDate date;
    private String location;
    private Double score;  // média atual
    private Integer ratingCount;  // qtd de avaliações recebidas
    private Boolean hotels;

    @ElementCollection
    private List<String> touristActivities;

//  JPA requires a no-args constructor
    Travel() {}
    
    Travel(String destinationName, LocalDate date, String location) {

        this.destinationName = destinationName;
        this.date = date;
        this.location = location;
    }

// getters
    public Long getId() {
        return this.id;
    }

    public String getDestinationName() {
        return this.destinationName;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getLocation() {
        return this.location;
    }

    public Double getScore() {
        return this.score;
    }

    public Boolean getHotels() {
        return this.hotels;
    }

    public List<String> getTouristActivities() {
        return this.touristActivities;
    }

// setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public void setDate (LocalDate date) {
        this.date = date;
    }

    public void setLocation (String location) {
        this.location = location;
    }

    public void setScore (Double score) {
        this.score = score;
    }

    public void setHotels (Boolean hotels) {
        this.hotels = hotels;
    }

    public void setTouristActivities (List<String> touristActivities) {
        this.touristActivities = touristActivities;
    }

    public void registerRating(int rating) {

        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException("A nota deve ser entre 1 e 10");
        }

        if (this.score == null || this.ratingCount == null || this.ratingCount == 0) {
            // define como primeira avaliação
            this.score = (double) rating;
            this.ratingCount = 1;
        } else {
            // média ponderada: (média_atual * qtd + nova_nota) / (qtd + 1)
            double total = this.score * this.ratingCount + rating;
            this.ratingCount +=1;
            this.score = total / this.ratingCount;
        }
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((destinationName == null) ? 0 : destinationName.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Travel other = (Travel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (destinationName == null) {
            if (other.destinationName != null)
                return false;
        } else if (!destinationName.equals(other.destinationName))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Viagem{" +
                "id=" + this.id +
                ", destinationName='" + this.destinationName + '\'' +
                ", date=" + this.date +
                '}';
    }

}

    
 








// questions:
// 1. my Travel constructor needs to recieve all the params defined in the atributes?
// for example: there is one endypoint for voting a note for  the destiny,
// but i dont want to send a note if i'm just booking the trip for the first time
// 2. What's the type of data of atividadesTuristicas?
