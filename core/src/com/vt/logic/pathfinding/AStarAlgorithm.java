package com.vt.logic.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.vt.gameobjects.pointers.DrawableVector;
import com.vt.gameobjects.terrain.tiles.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by fckrsns on 06.01.2016.
 */
public class AStarAlgorithm extends Pathfinder {
    public interface Heuristic {
        float calculate(Graph.Node node, Graph.Node targetNode);
    }

    private Graph m_graph;
    private Heuristic m_heuristic;

    public AStarAlgorithm(Graph graph, Heuristic heuristic) {
        m_graph = graph;
        m_heuristic = heuristic;
    }

    @Override
    public List<Graph.Node> findPath(Graph.Node fromNode, Graph.Node toNode) {
        // initial
        d.put(fromNode.index, new DecisionInfo());
        processedNodes.add(fromNode.index);

        // calculate path costs
        Graph.Node currentNode = fromNode;
        while (currentNode.index != toNode.index) {
            for (Graph.DestinationNode adjacentNode : currentNode.incidentNodes) {
                Tile.Index index = adjacentNode.node.index;
                if (!d.containsKey(index))
                    d.put(index, new DecisionInfo(INFINITY));
                DecisionInfo info = d.get(index);
                info.sumWeight = Math.min(info.sumWeight, d.get(currentNode.index).sumWeight + adjacentNode.edgeWeight);
                info.heuristic = m_heuristic.calculate(adjacentNode.node, toNode);
                vectors.put(index, new DrawableVector(currentNode.x, currentNode.y, adjacentNode.node.x, adjacentNode.node.y, Color.RED, 0.05f, true));
            }
            Float min = INFINITY;
            Tile.Index minIndex = null;
            for (Map.Entry entry : d.entrySet()) {
                DecisionInfo info = (DecisionInfo) entry.getValue();
                Float currentValue = info.sumWeight + info.heuristic;
                Tile.Index currentIndex = (Tile.Index) entry.getKey();
                if (!processedNodes.contains(currentIndex) && currentValue < min) {
                    min = currentValue;
                    minIndex = currentIndex;
                }
            }
            if (min.equals(INFINITY) || minIndex == null)
                return null;
            currentNode = m_graph.getNode(minIndex);
            processedNodes.add(minIndex);
            if (variations != null)
                variations.add(vectors.get(minIndex));
        }

        // get optimal path
        currentNode = toNode;
        List<Graph.Node> path = new ArrayList<Graph.Node>();
        path.add(toNode);
        while (currentNode.index != fromNode.index) {
            Float min = INFINITY;
            Tile.Index minIndex = null;
            for (Graph.DestinationNode adjacentNode : currentNode.incidentNodes) {
                Tile.Index index = adjacentNode.node.index;
                if (!d.containsKey(index))
                    d.put(index, new DecisionInfo(INFINITY));
                Float weight = d.get(index).sumWeight + adjacentNode.edgeWeight;
                if (weight < min) {
                    min = weight;
                    minIndex = index;
                }
            }
            if (minIndex == null)
                return null;
            currentNode = m_graph.getNode(minIndex);
            path.add(currentNode);
        }

        Collections.reverse(path);
        return path;
    }
}