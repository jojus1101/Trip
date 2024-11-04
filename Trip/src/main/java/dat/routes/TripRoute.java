package dat.routes;

import dat.controllers.impl.TripController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class TripRoute {

    private final TripController tripController = new TripController();

    protected EndpointGroup getRoutes(){
        return () -> {
            get("/populate",tripController::populate);
            get("/{id}", tripController::read);
            get("/category/{category}", tripController::filterByCategory);
//            get("guides/totalprice", tripController::getTotalPriceByGuide);
            get("/", tripController::readAll, Role.ADMIN);
            post("/", tripController::create);
            delete("/{id}", tripController::delete);
            put("/{id}", tripController::update, Role.ADMIN);
        };
    }
}
