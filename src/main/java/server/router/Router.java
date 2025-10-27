package server.router;

import constants.HttpConstants.Methods;
import controllers.Controller;
import http.HttpRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Router {
    private final PathMatcher matcher = new PathMatcher();

    private final Map<String, Map<String, Route>> routes = new HashMap<>();

    public Router get(String path, Controller controller) {
        Route getRoute = new Route(Methods.GET, path, controller );
        return register(getRoute);
    }

    public Router post(String path, Controller controller) {
        Route postRoute = new Route(Methods.POST, path, controller );
        return register(postRoute);
    }

    public Router put(String path, Controller controller) {
        Route putRoute = new Route(Methods.PUT, path, controller );
        return register(putRoute);
    }

    public Router delete(String path, Controller controller) {
        Route deleteRoute = new Route(Methods.DELETE, path, controller );
        return register(deleteRoute);
    }

    private Router register(Route route) {
        matcher.register(route);
        return this;
    }

    public PathMatchResult resolve(HttpRequest request) {
        return matcher.resolve(request);
    }
}
