package server.router;

import http.HttpRequest;
import http.PathParam;

import java.util.*;

public class PathMatcher {
    private final Map<String, Map<String, Route>> exact = new HashMap<>();
    private final DynamicTrie dynamic = new DynamicTrie();

    public void register(Route route) {
        if (ifPathContainsParam(route.getPath())) {
            dynamic.addRoute(route.getMethod(), route.getPath(), route);
        } else {
            exact.computeIfAbsent(route.getPath(), k -> new HashMap<>())
                    .put(route.getMethod(), route);
        }
    }

    public PathMatchResult resolve(HttpRequest request) {
        String path = request.getPath();
        String method = request.getMethod();

        Map<String, Route> pathRoutes = exact.get(path);
        if (pathRoutes != null) {
            Route route = pathRoutes.get(method);
            if (route != null) {
                return new PathMatchResult.Found(route, Collections.emptyList());
            }
            return new PathMatchResult.MethodNotAllowed(Set.copyOf(pathRoutes.keySet()));
        }

        return dynamic.match(method, path);
    }

    private static boolean ifPathContainsParam(String path) {
        return path.contains("{");    //add support for * later with proper understanding of curent imlemntation
    }
}
