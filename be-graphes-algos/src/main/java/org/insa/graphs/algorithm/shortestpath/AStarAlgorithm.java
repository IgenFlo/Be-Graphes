package org.insa.graphs.algorithm.shortestpath;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    public LabelStar makeLabel(int nodeId, float estimatedCost) {
    	return new LabelStar(nodeId, estimatedCost);
    }
}
