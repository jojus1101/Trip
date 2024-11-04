package dat.daos.impl;

import dat.daos.IDAO;
import dat.daos.ITripGuideDAO;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.Category;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TripDAO implements IDAO<TripDTO, Integer>, ITripGuideDAO {

    private static TripDAO instance;
    private static EntityManagerFactory emf;
    Set<Trip> usaTrips = getusTrips();
    Set<Trip> denTrips = getdenTrips();

    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripDAO();
        }
        return instance;
    }

    @Override
    public TripDTO read(Integer integer) throws ApiException {
        try(EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, integer);
            return trip != null ? new TripDTO(trip) : null;
        }
    }

    @Override
    public List<TripDTO> readAll() throws ApiException {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<TripDTO> query = em.createQuery("SELECT new dat.dtos.TripDTO(t) FROM Trip t", TripDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public TripDTO create(TripDTO tripDTO) throws ApiException {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(tripDTO);
            em.persist(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO update(Integer integer, TripDTO tripDTO) throws ApiException {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, integer);
            trip.setName(tripDTO.getName());
            trip.setPrice(tripDTO.getPrice());
            trip.setEndtime(tripDTO.getEndtime());
            trip.setStarttime(tripDTO.getStarttime());
            trip.setStartposition(tripDTO.getStartposition());
            trip.setCategory(tripDTO.getCategory());
            Trip updatedTrip = em.merge(trip);
            em.getTransaction().commit();
            return new TripDTO(updatedTrip);

        }
    }

    @Override
    public void delete(Integer integer) throws ApiException {
        try(EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, integer);
            if(trip != null) {
                em.getTransaction().begin();
                Guide findGuide = trip.getGuide();
                if(findGuide != null) {
                    findGuide.getTrips().remove(trip);
                    em.merge(findGuide);
                }
                em.remove(trip);
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public void addGuideToTrip(int tripId, int guideId) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, guideId);
            Trip trip = em.find(Trip.class, tripId);
            if (trip == null || guide == null) {
                throw new PersistenceException("Trip or Guide not found");
        }
            trip.setGuide(guide);
            guide.getTrips().add(trip);

            em.getTransaction().commit();
        }
    }

    @Override
    public Set<TripDTO> getTripsByGuide(int guideId) {
        try(EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, guideId);
            if (guide == null) {
                throw new PersistenceException("Guide not found");
            }
            return guide.getTrips().stream()
                    .map(TripDTO::new)
                    .collect(Collectors.toSet());
        }
    }
    public void populate() throws ApiException {
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
        Trip t1 = new Trip("Beach Holiday", 299.99, LocalDateTime.of(2024, 7, 1, 10, 0), LocalDateTime.of(2024, 7, 15, 10, 0),"California", Category.SEA);
        Trip t2 = new Trip("Beach Holiday", 499.99, LocalDateTime.of(2024, 7, 1, 10, 0), LocalDateTime.of(2024, 7, 15, 10, 0),"Florida",Category.SEA);
        Trip t3 = new Trip("Ski Holiday", 4999.99, LocalDateTime.of(2024, 2, 1, 10, 0), LocalDateTime.of(2024, 7, 15, 10, 0),"Park City",Category.SNOW);

        Trip[] tripArray = {t1, t2,t3};
        return Set.of(tripArray);
    }

    @NotNull
    private static Set<Trip> getdenTrips() {
        Trip t10 = new Trip("Beach Holiday", 500.99, LocalDateTime.of(2024, 10, 25, 10, 0), LocalDateTime.of(2024, 7, 15, 10, 0), "Lyngby", Category.FOREST);
        Trip t11 = new Trip("Beach Holiday", 299.99, LocalDateTime.of(2024, 7, 1, 10, 0), LocalDateTime.of(2024, 7, 15, 10, 0), "Helsingør", Category.CITY);
        Trip t12 = new Trip("Tivoli", 100.00, LocalDateTime.of(2024, 7, 11, 10, 0), LocalDateTime.of(2024, 7, 15, 10, 0), "København", Category.CITY);
        Trip[] tripArray = {t10, t11, t12};
        return Set.of(tripArray);
    }

//    public List<GuideDTO> getGuideTotalPrices() {
//        try (var em = emf.createEntityManager()) {
//            em.getTransaction().begin();
//
//            // Create a query that retrieves guides along with the total price of their trips
//            List<GuideDTO> guideTotalPrices = em.createQuery(
//                            "SELECT new GuideDTO(g.id, g.firstname, g.lastname, COALESCE(SUM(t.price), 0)) " +
//                                    "FROM Guide g LEFT JOIN g.trips t " +
//                                    "GROUP BY g.id, g.firstname, g.lastname", GuideDTO.class)
//                    .getResultList();
//
//            em.getTransaction().commit();
//            return guideTotalPrices;
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new ApiException("Error retrieving guide total prices", e);
//        }
//    }

}
