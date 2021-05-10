package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;

public class AlgoTestAStar extends AlgoTest{
	public static ShortestPathSolution makeSolution(ShortestPathData data) {
		AStarAlgorithm Astar = new AStarAlgorithm(data);
    	return Astar.run();
    }
}
