package dat.config;


import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.Category;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        Set<Trip> usaTrips = getusTrips();
        Set<Trip> denTrips = getdenTrips();

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = new Guide("Jens", "Hansen", "JensHansen@mail.com", "48392919", 5);
            Guide guide2 = new Guide("Mark", "Nielsen", "Markniller@mail.com", "12233445", 10);
            guide.setTrips(usaTrips);
            guide2.setTrips(denTrips);
            em.persist(guide);
            em.persist(guide2);
            em.getTransaction().commit();
        }
    }

    @NotNull
    private static Set<Trip> getusTrips() {
        Trip t1 = new Trip("Beach Holiday", 299.99, LocalDateTime.of(2024, 7, 1, 10, 0), LocalDateTime.of(2024, 7, 15, 10, 0),"California",Category.CITY);
        Trip t2 = new Trip("Beach Holiday", 499.99, LocalDateTime.of(2024, 7, 1, 10, 0), LocalDateTime.of(2024, 7, 15, 10, 0),"Florida",Category.CITY);
        Trip t3 = new Trip("Ski Holiday", 4999.99, LocalDateTime.of(2024, 2, 1, 10, 0), LocalDateTime.of(2024, 7, 15, 10, 0),"Park City",Category.CITY);

        Trip[] tripArray = {t1, t2,t3};
        return Set.of(tripArray);
    }

    @NotNull
    private static Set<Trip> getdenTrips() {
        Trip t10 = new Trip("Beach Holiday", 500.99, LocalDateTime.of(2024, 10, 25, 10, 0), LocalDateTime.of(2024, 7, 15, 10, 0), "Lyngby", Category.FOREST);
        Trip t11 = new Trip("Beach Holiday", 299.99, LocalDateTime.of(2024, 7, 1, 10, 0), LocalDateTime.of(2024, 7, 15, 10, 0), "Helsingør", Category.LAKE);
        Trip t12 = new Trip("Tivoli", 100.00, LocalDateTime.of(2024, 7, 11, 10, 0), LocalDateTime.of(2024, 7, 15, 10, 0), "København", Category.FOREST);
        Trip[] tripArray = {t10, t11, t12};
        return Set.of(tripArray);
    }
}
