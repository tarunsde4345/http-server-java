package server.router;

import http.PathParam;

import java.util.List;
import java.util.Set;

public sealed interface PathMatchResult
        permits PathMatchResult.Found,
            PathMatchResult.MethodNotAllowed,
            PathMatchResult.NotFound {

    public record Found(Route route, List<PathParam> params) implements PathMatchResult {}
    public record NotFound() implements PathMatchResult {}
    public record MethodNotAllowed(Set<String> allowed) implements PathMatchResult {}
}
