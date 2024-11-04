package dat.dtos;

import dat.entities.Trip;
import dat.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class TripDTO {
    private Integer id;
    private String name;
    private double price;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime starttime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endtime;
    private String startposition;
    private Category category;


    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.starttime = trip.getStarttime();
        this.endtime = trip.getEndtime();
        this.category = trip.getCategory();

    }
}
