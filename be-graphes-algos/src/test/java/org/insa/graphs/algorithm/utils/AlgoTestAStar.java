package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.*;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Test;

public class AlgoTestAStar extends AlgoTest{
	public static ShortestPathSolution makeSolution(ShortestPathData data) {
		AStarAlgorithm Astar = new AStarAlgorithm(data);
		System.out.println("haha");
    	return Astar.run();
    }
	
	@Test
	public void CompareAStarWithDijkstra() throws IOException{
		//String mapName = "/Users/macair/Desktop/INSA Learning/BE Graphe/Maps/insa.mapgr";
		String mapName = "/Users/viktor/Desktop/ReINSA LEARNING/BE Graphe/BE-Graphe/Maps/insa.mapgr";
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
    	Graph graphINSA = reader.read();
    	
    	//Test en distance
    	ShortestPathData data = new ShortestPathData(graphINSA, graphINSA.getNodes().get(1304),graphINSA.getNodes().get(725), ArcInspectorFactory.getAllFilters().get(0));
    	DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
    	ShortestPathSolution solutionDijkstra = Dijkstra.run();
    	solution = makeSolution(data);
    	
    	//Longueur des chemins égaux?
    	assertEquals((int)solutionDijkstra.getPath().getLength(),(int)solution.getPath().getLength());
    	
    	//Chemins égaux?
    	for(int i=0;i<solutionDijkstra.getPath().getArcs().size();i++) {
    		assertEquals(solutionDijkstra.getPath().getArcs().get(i),solution.getPath().getArcs().get(i));
    	}
    	
    	//Test en temps
    	data = new ShortestPathData(graphINSA, graphINSA.getNodes().get(804),graphINSA.getNodes().get(429), ArcInspectorFactory.getAllFilters().get(2));
    	Dijkstra = new DijkstraAlgorithm(data);
    	solutionDijkstra = Dijkstra.run();
    	solution = makeSolution(data);
    	
    	//Cout en temps des chemins égaux?
    	assertEquals((int)solutionDijkstra.getPath().getMinimumTravelTime(),(int)solution.getPath().getMinimumTravelTime());
    	
    	//Chemins égaux?
    	for(int i=0;i<solutionDijkstra.getPath().getArcs().size();i++) {
    		assertEquals(solutionDijkstra.getPath().getArcs().get(i),solution.getPath().getArcs().get(i));
    	}
	}
}
