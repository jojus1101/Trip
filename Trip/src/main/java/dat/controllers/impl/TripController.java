package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.TripDAO;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class TripController implements IController<TripDTO, Integer> {
    private final TripDAO dao;

    public TripController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = TripDAO.getInstance(emf);

    }

    @Override
    public void read(Context ctx) throws ApiException, ApiException {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not and id").get();
        TripDTO trip = dao.read(id);
        ctx.res().setStatus(200);
        ctx.json(trip, TripDTO.class);
    }

    @Override
    public void readAll(Context ctx) throws ApiException {
        List<TripDTO> trips = dao.readAll();

        ctx.res().setStatus(200);
        ctx.json(trips, TripDTO.class);
    }

    @Override
    public void create(Context ctx) throws ApiException {
        TripDTO jsonRequest = validateEntity(ctx);

        // Get the trip ID from the URL path parameters
        int tripId = ctx.pathParamAsClass("id", Integer.class)
                .check(this::validatePrimaryKey, "Not a valid id")
                .get();

        // Get the guide ID from the request body or as a parameter
        int guideId = ctx.bodyValidator(GuideDTO.class)
                .check(g -> g.getId() > 0, "Not a valid guide id")
                .get().getId();

        // Add the guide to the trip
        dao.addGuideToTrip(tripId, guideId);

        // Set the response status to OK
        ctx.status(200);
    }

    @Override
    public void update(Context ctx) throws ApiException {
        TripDTO tripDTO = validateEntity(ctx);

        // Get the trip ID from the URL path parameters and validate it
        int tripId = ctx.pathParamAsClass("id", Integer.class)
                .check(this::validatePrimaryKey, "Not a valid id")
                .get();

        // Attempt to update the trip using the DAO
        try {
            // Call the DAO to update the trip and receive the updated TripDTO in return
            TripDTO updatedTrip = dao.update(tripId, tripDTO);

            // If the updatedTrip is null, it means the trip was not found
            if (updatedTrip == null) {
                ctx.status(404);
                ctx.json("{\"error\": \"Trip not found\"}");
                return;
            }

            // Set the response status and return the updated TripDTO
            ctx.status(200);
            ctx.json(updatedTrip, TripDTO.class);
        } catch (Exception e) {
            // Handle any exceptions during the update process
            ctx.status(500);
            ctx.json("{\"error\": \"Error updating trip: " + e.getMessage() + "\"}");
        }
    }

    @Override
    public void delete(Context ctx) throws ApiException {
        int tripId = ctx.pathParamAsClass("id", Integer.class)
                .check(this::validatePrimaryKey, "Not a valid id")
                .get();

        // Call the DAO to delete the trip
        dao.delete(tripId);

        // Set the response status indicating successful deletion
        ctx.status(204);
    }
    private boolean validatePrimaryKey(Integer id) {
        return id > 0; // Example validation; adjust according to your requirements
    }
    private TripDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(TripDTO.class)
                .check(t -> t.getName() != null && !t.getName().isEmpty(), "Trip name is required")
                .check(t -> t.getPrice() > 0, "Price must be greater than zero")
                .check(t -> t.getStarttime() != null, "Start time is required")
                .check(t -> t.getEndtime() != null, "End time is required")
                .check(dto -> dto.getStartposition() != null && !dto.getStartposition().trim().isEmpty(), "Start position cannot be null or empty")
                .check(t -> t.getCategory() != null, "Category is required")
                .get();
    }
    public void populate(Context ctx) throws ApiException {
        dao.populate();
        ctx.res().setStatus(200);
        ctx.json("{ \"message\": \"Database has been populated\" }");
    }
    // Method to filter trips by category
    public void filterByCategory(Context ctx) throws ApiException {
        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://packingapi.cphbusinessapps.dk/packinglist/{category}"))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the status code and print the response
            if (response.statusCode() == 200) {
                System.out.println(response.body());
            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

    // Method to get total price of trips offered by each guide
//    public void getTotalPriceByGuide(Context ctx) {
//        List<GuideDTO> guideTotalPrices = dao.getGuideTotalPrices();
//        ctx.status(200);
//        ctx.json(guideTotalPrices);
//    }

