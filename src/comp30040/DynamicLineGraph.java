package comp30040;

import edu.uci.ics.jung.graph.AbstractGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import sun.security.provider.certpath.Vertex;

import java.util.Collection;

/**
 * Created by rich on 26/10/2015.
 */
public class DynamicLineGraph<V, E> extends AbstractGraph<V, E>{

    void removeVertex(Vertex v){

    }

    public boolean addEdge(E e, Pair<? extends V> pair, EdgeType edgeType) {
        return false;
    }

    public Collection<E> getEdges() {
        return null;
    }

    public Collection<V> getVertices() {
        return null;
    }

    public boolean containsVertex(V v) {
        return false;
    }

    public boolean containsEdge(E e) {
        return false;
    }

    public int getEdgeCount() {
        return 0;
    }

    public int getVertexCount() {
        return 0;
    }

    public Collection<V> getNeighbors(V v) {
        return null;
    }

    public Collection<E> getIncidentEdges(V v) {
        return null;
    }

    public boolean addVertex(V v) {
        return false;
    }

    public boolean removeVertex(V v) {
        return false;
    }

    public boolean removeEdge(E e) {
        return false;
    }

    public EdgeType getEdgeType(E e) {
        return null;
    }

    public EdgeType getDefaultEdgeType() {
        return null;
    }

    public Collection<E> getEdges(EdgeType edgeType) {
        return null;
    }

    public int getEdgeCount(EdgeType edgeType) {
        return 0;
    }

    public Collection<E> getInEdges(V v) {
        return null;
    }

    public Collection<E> getOutEdges(V v) {
        return null;
    }

    public Collection<V> getPredecessors(V v) {
        return null;
    }

    public Collection<V> getSuccessors(V v) {
        return null;
    }

    public V getSource(E e) {
        return null;
    }

    public V getDest(E e) {
        return null;
    }

    public boolean isSource(V v, E e) {
        return false;
    }

    public boolean isDest(V v, E e) {
        return false;
    }

    public Pair<V> getEndpoints(E e) {
        return null;
    }
}
