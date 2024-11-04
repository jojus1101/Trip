package dat.entities;

import dat.dtos.TripDTO;
import dat.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Trip")
@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    private double price;

    private LocalDateTime starttime;

    private LocalDateTime endtime;

    private String startposition;

    private Category category;

    @ManyToOne
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    public Trip(String name, double price, LocalDateTime starttime, LocalDateTime endtime, String startposition, Category category) {
        this.name = name;
        this.price = price;
        this.starttime = starttime;
        this.endtime = endtime;
        this.startposition = startposition;
        this.category = category;
    }

    public Trip (TripDTO tripDTO) {
        this.id = tripDTO.getId();
        this.name = tripDTO.getName();
        this.price = tripDTO.getPrice();
        this.startposition = tripDTO.getStartposition();
        this.category = tripDTO.getCategory();
        this.starttime = tripDTO.getStarttime();
        this.endtime = tripDTO.getEndtime();

    }
}
