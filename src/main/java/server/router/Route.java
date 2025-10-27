package server.router;

import controllers.Controller;

import java.util.List;

public class Route {
    private final String path;
    private final String method;
    private final Controller controller;
    private List<String> paramNames;

    public Route(String method, String path, Controller controller) {
        this.path = path;
        this.method = method;
        this.controller = controller;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public Controller getController() {
        return controller;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

    public void setParamNames(List<String> paramNames) {
        this.paramNames = paramNames;
    }

    @Override
    public String toString() {
        return "Route{" +
                "path='" + path + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
