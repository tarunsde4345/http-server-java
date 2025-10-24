package server;

import controllers.Controller;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private final Map<String, Controller> routes = new HashMap<>();

    public void register(String path, Controller controller) {
        routes.put(path, controller);
    }

    public Controller resolve(String path) {
        return routes.get(path);
    }
}
