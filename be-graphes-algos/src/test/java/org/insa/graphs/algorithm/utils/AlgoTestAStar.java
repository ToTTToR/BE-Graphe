package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Random;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.junit.Test;

public class AlgoTestAStar extends AlgoTest{
	public static ShortestPathSolution makeSolution(ShortestPathData data) {
		AStarAlgorithm Astar = new AStarAlgorithm(data);
    	return Astar.run();
    }
	
	@Test
	public void CompareAStarWithDijkstra() throws IOException{
		Random rand = new Random();
    	int max = rand.nextInt(graphINSA.getNodes().size());
    	
    	for(int j=0;j<50;j++) {
    		int origin = rand.nextInt(max);
    		int destination = rand.nextInt(max);
    	
	    	//Test en distance
	    	ShortestPathData data = new ShortestPathData(graphINSA, graphINSA.getNodes().get(origin),graphINSA.getNodes().get(destination), ArcInspectorFactory.getAllFilters().get(0));
	    	DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
	    	ShortestPathSolution solutionDijkstra = Dijkstra.run();
	    	solution = makeSolution(data);
	    	
	    	if(!solutionDijkstra.isFeasible())
	    		assertTrue(!solution.isFeasible());
	    	else {
		    	//Longueur des chemins égaux?
		    	assertEquals((int)solutionDijkstra.getPath().getLength(),(int)solution.getPath().getLength());
		    	
		    	//Chemins égaux?
		    	for(int i=0;i<solutionDijkstra.getPath().getArcs().size();i++) {
		    		assertEquals(solutionDijkstra.getPath().getArcs().get(i),solution.getPath().getArcs().get(i));
		    	}
	    	}
	    	
	    	//Test en temps
	    	data = new ShortestPathData(graphINSA, graphINSA.getNodes().get(origin),graphINSA.getNodes().get(destination), ArcInspectorFactory.getAllFilters().get(2));
	    	Dijkstra = new DijkstraAlgorithm(data);
	    	solutionDijkstra = Dijkstra.run();
	    	solution = makeSolution(data);
	    	
	    	if(!solutionDijkstra.isFeasible())
	    		assertTrue(!solution.isFeasible());
	    	else {
		    	//Cout en temps des chemins égaux?
		    	assertEquals((int)solutionDijkstra.getPath().getMinimumTravelTime(),(int)solution.getPath().getMinimumTravelTime());
		    	
		    	//Chemins égaux?
		    	for(int i=0;i<solutionDijkstra.getPath().getArcs().size();i++) {
		    		assertEquals(solutionDijkstra.getPath().getArcs().get(i),solution.getPath().getArcs().get(i));
		    	}
	    	}
    	}
	}
}
