package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;

public class AlgoTestBM extends AlgoTest{
	
	public static ShortestPathSolution makeSolution(ShortestPathData data) {
    	BellmanFordAlgorithm Bellman = new BellmanFordAlgorithm(data);
    	return Bellman.run();
    }

}
