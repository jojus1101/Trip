package dat.entities;

import dat.dtos.GuideDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private int yearsOfExperience;

    @OneToMany(mappedBy = "guide", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Trip> trips;

    public Guide(String firstname, String lastname, String email, String phone, int yearsOfExperience) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }
    public Guide(GuideDTO guideDTO) {
        this.id = guideDTO.getId();
        this.firstname = guideDTO.getFirstname();
        this.lastname = guideDTO.getLastname();
        this.email = guideDTO.getEmail();
        this.phone = guideDTO.getPhone();
        this.yearsOfExperience = guideDTO.getYearsOfExperience();

    }

    public void setTrips(Set<Trip> trips) {
        if(trips != null) {
            this.trips = trips;
            for (Trip trip : trips) {
                trip.setGuide(this);
            }
        }
    }
    public void addTrip(Trip trip) {
        if(trips != null) {
            this.trips.add(trip);
            trip.setGuide(this);
        }
    }

}
