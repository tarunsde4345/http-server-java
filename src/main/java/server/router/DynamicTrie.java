package server.router;

import http.PathParam;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DynamicTrie {
    private final TrieNode root = new TrieNode();

    public void addRoute(String httpMethod, String path, Route route) {
        TrieNode node = root;
        String[] segment = splitPath(path);

        List<String> paramNames = new ArrayList<>();

        for (String seg : segment) {
            if (isParameter(seg)) {
                String paramName = extractParamName(seg);
                paramNames.add(paramName);
                // NEW: Check if paramChild exists; if not, add new
                node = node.getParamChild();
            } else {
                node = node.getExactChildren().computeIfAbsent(seg, k -> new TrieNode());
            }
        }
        route.setParamNames(paramNames);
        node.getPathControllers().put(httpMethod, route);
    }

    public PathMatchResult match(String httpMethod, String path) {
        String[] segments = splitPath(path);
        List<String> paramValues = new ArrayList<>();
        Set<String> allowedMethods = new LinkedHashSet<>(); //why use linkedhashset? instead of hashset?

        TrieNode leaf = findLeaf(root, segments, 0, paramValues, allowedMethods);
        if (leaf == null) {
            return new PathMatchResult.NotFound();
        }

        Route route = leaf.getPathControllers().get(httpMethod);
        if (route != null) {
            // map collectedValues -> route.getParamNames()
            List<PathParam> params = new ArrayList<>();
            List<String> paramNames = route.getParamNames();
            for (int i = 0; i < paramNames.size() && i < paramValues.size(); i++) {
                params.add(new PathParam(paramNames.get(i), paramValues.get(i)));
            }
            return new PathMatchResult.Found(route, params);  //add support for params later
        } else {
            return new PathMatchResult.MethodNotAllowed(Set.copyOf(allowedMethods));
        }
    }

    private static String[] splitPath(String path) {
        if (path.isEmpty() || "/".equals(path)) return new String[0];
        return path.startsWith("/") ? path.substring(1).split("/") : path.split("/");
    }

    private static boolean isParameter(String seg) {
        return seg.startsWith("{") && seg.endsWith("}");    //add support for * later with proper understanding of curent imlemntation
    }

    private static String extractParamName(String seg) {
        return seg.substring(1, seg.length() - 1);          //hadnle *
    }

    private TrieNode findLeaf(TrieNode node, String[] segments, int index,
                              List<String> paramValues, Set<String> allowedMethods) {
        if (index == segments.length) {
            if (!node.getPathControllers().isEmpty()) {
                allowedMethods.addAll(node.getPathControllers().keySet());
                return node;
            }
            return null;
        }

        String seg = segments[index];

        // Exact match
        TrieNode child = node.getExactChildren().get(seg);
        if (child != null) {
            TrieNode result = findLeaf(child, segments, index + 1, paramValues, allowedMethods);
            if (result != null) {
                return result;
            }
        }

        // 2. NEW: Loop over all param children (priority = registration order)
        child = node.getParamChild();
        paramValues.add(seg);
        TrieNode result = findLeaf(child, segments, index + 1, paramValues, allowedMethods);
        if (result != null) {
            return result;
        }
        paramValues.remove(paramValues.size() - 1);  // Backtrack

        return null;
    }
}
