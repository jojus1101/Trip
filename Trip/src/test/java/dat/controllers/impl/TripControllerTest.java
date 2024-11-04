package dat.controllers.impl;

import dat.dtos.TripDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripControllerTest {

    @Test
    void readAll() {
        System.out.println("usertoken: " + userToken);
        System.out.println("admintoken: " + adminToken);
        List<TripDTO> tripDTO =
                given()
                        .when()
                        .header("Authorization", userToken)
                        .get(BASE_URL + "/hotels")
                        .then()
                        .statusCode(200)
                        .body("size()", is(2))
                        .log().all()
                        .extract()
                        .as(new TypeRef<List<HotelDTO>>() {});

        assertThat(TripDTO.size(), is(2));
        assertThat(TripDTO.get(0).getHotelName(), is("København"));
        assertThat(TripDTO.get(1).getHotelName(), is("FLorida"));
    }
}