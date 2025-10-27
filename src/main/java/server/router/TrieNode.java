package server.router;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private final Map<String, TrieNode> exactChildren = new HashMap<>();
    private TrieNode paramChild = null;

    private final Map<String, Route> pathControllers = new HashMap<>();

    public Map<String, TrieNode> getExactChildren() {
        return exactChildren;
    }

    public TrieNode getParamChild() {
        if (paramChild == null) {
            paramChild = new TrieNode();
        }
        return paramChild;
    }

    public Map<String, Route> getPathControllers() {
        return pathControllers;
    }

    static final class ParamNode {  // Inner static class
        final String paramName;
        final TrieNode child;

        ParamNode(String paramName, TrieNode child) {
            this.paramName = paramName;
            this.child = child;
        }
    }


    @Override
    public String toString() {
        return "TrieNode[handlers=%s, children=%d]".formatted(
                 pathControllers.keySet(), exactChildren.size());
    }
}
