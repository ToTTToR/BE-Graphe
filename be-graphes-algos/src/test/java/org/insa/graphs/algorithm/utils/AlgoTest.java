package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.insa.graphs.algorithm.*;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.*;
import org.insa.graphs.model.AccessRestrictions.AccessMode;
import org.insa.graphs.model.AccessRestrictions.AccessRestriction;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;

public class AlgoTest {
	//Le dit graph
	private static Graph graph1,graph2,graphOneNode,graphNotConnex;

    // Liste de nodes
    private static Node[] nodes;
    
    @SuppressWarnings("unused")
    private static Path emptyPath, singleNodePath1,singleNodePath2, shortPath1,shortPath2,shortPath3,shortPath4,invalidPath;
    
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2d,a2e, b2c,b2d,d2b,d2c,d2e,c2e,c2d,e2c,e2a,e2d,e2b;

    private static ShortestPathData data;
    
    private static AccessRestrictions restrictions;
    
    private static RoadInformation infoRoute1,infoRoute2;
    
    private static EnumMap<AccessMode, AccessRestriction> truc;
    
    protected static ShortestPathSolution solution;
    
    public static ShortestPathSolution makeSolution(ShortestPathData data) {
    	DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
    	return Dijkstra.run();
    }
    
    //@BeforeClass
    public static void initGraphNotConnex() {
    	nodes = new Node[2];
    	nodes[0] = new Node(0,new Point((float)0.0,(float)0.0));
    	nodes[1] = new Node(1,new Point((float)10.0,(float)0.0));
    	graphNotConnex = new Graph("NoConecto","",Arrays.asList(nodes),null);
    }
    
    //@BeforeClass
	public static void initGraph1() throws IOException{
		nodes = new Node[5];
	    for (int i = 0; i < nodes.length; ++i) {
	        nodes[i] = new Node(i, null);
	    }
	    a2b = Node.linkNodes(nodes[0], nodes[1], 10,infoRoute2,null);
	    b2c = Node.linkNodes(nodes[1], nodes[2], 1,infoRoute1,null);
	    a2d = Node.linkNodes(nodes[0], nodes[3], 5,infoRoute2,null);
	    b2d = Node.linkNodes(nodes[1], nodes[3], 2,infoRoute1,null);
	    d2b = Node.linkNodes(nodes[3], nodes[1], 3,infoRoute1,null);
	    a2b = Node.linkNodes(nodes[0], nodes[1], 10,infoRoute2,null);
	    d2c = Node.linkNodes(nodes[3], nodes[2], 9,infoRoute2,null);
	    d2e = Node.linkNodes(nodes[3], nodes[4], 2,infoRoute1,null);
	    c2e = Node.linkNodes(nodes[2], nodes[4], 4,infoRoute1,null);
	    e2c = Node.linkNodes(nodes[4], nodes[2], 5,infoRoute2,null);
	    e2a = Node.linkNodes(nodes[4], nodes[0], 3,infoRoute1,null);
	    
	    graph1 = new Graph("ID1", "", Arrays.asList(nodes), null);
	    //emptyPath = new Path(graph1, new ArrayList<Arc>());
        singleNodePath1 = new Path(graph1, nodes[0]);
        shortPath1 = new Path(graph1, Arrays.asList(new Arc[] { a2d, d2b, b2c })); //Chemin de a (0) vers c (2)
        shortPath3 = new Path(graph1, Arrays.asList(new Arc[] { e2a, a2d, d2b })); //Chemin e (4) vers b (1)
	}
    
    public static void initGraph2() throws IOException{
    	nodes = new Node[5];
	    for (int i = 0; i < nodes.length; ++i) {
	        nodes[i] = new Node(i, null);
	    }
	    a2b = Node.linkNodes(nodes[0], nodes[1], 4,infoRoute2,null);
	    a2e = Node.linkNodes(nodes[0], nodes[4], 2,infoRoute2,null);
	    e2a = Node.linkNodes(nodes[4], nodes[0], 1,infoRoute1,null);
	    e2b = Node.linkNodes(nodes[4], nodes[1], 1,infoRoute1,null);
	    b2c = Node.linkNodes(nodes[1], nodes[2], 3,infoRoute2,null);
	    e2c = Node.linkNodes(nodes[4], nodes[2], 3,infoRoute2,null);
	    c2d = Node.linkNodes(nodes[2], nodes[3], 3,infoRoute2,null);
	    e2d = Node.linkNodes(nodes[4], nodes[3], 2,infoRoute1,null);
	    d2e = Node.linkNodes(nodes[3], nodes[4], 2,infoRoute1,null);
	    
	    graph2 = new Graph("ID2","",Arrays.asList(nodes), null);
	    singleNodePath2 = new Path(graph2, nodes[0]);
	    shortPath2 = new Path(graph2, Arrays.asList(new Arc[] {a2e,e2d})); //Chemin de a (0) vers d (3)
	    shortPath4 = new Path(graph2, Arrays.asList(new Arc[] {a2e,e2b,b2c})); //Chemin de a (0) vers c (2)
    }
       
    
    public static void noRestrictionCar() {
    	truc = new EnumMap<>(AccessMode.class);
    	truc.put(AccessMode.MOTORCAR, AccessRestriction.ALLOWED);
    	restrictions = new AccessRestrictions(truc);
		infoRoute1 = new RoadInformation(RoadType.UNCLASSIFIED, restrictions, true, 5, null);
		infoRoute2 = new RoadInformation(RoadType.UNCLASSIFIED, restrictions, true, 10, null);
    }
    
    public static void noRestrictionPed() {
    	truc = new EnumMap<>(AccessMode.class);
    	truc.put(AccessMode.MOTORCAR, AccessRestriction.ALLOWED);
    	infoRoute1 = new RoadInformation(RoadType.UNCLASSIFIED, restrictions, true, 5, null);
		infoRoute2 = new RoadInformation(RoadType.UNCLASSIFIED, restrictions, true, 10, null);
    }
    @Test
    public void testSolutionForCar() throws IOException{
    	noRestrictionCar();
    	
    	//Graphe non connexe, pas de chemin possible entre 2 points
    	initGraphNotConnex();
    	data = new ShortestPathData(graphNotConnex, nodes[0], nodes[1], ArcInspectorFactory.getAllFilters().get(0));
    	solution = makeSolution(data);
    	assertTrue(!solution.isFeasible()); //Le problème n'est pas faisable pour un graphe non connexe
    	
    	//Test pour la première carte pour chemin le plus court
    	initGraph1();
    	validPathFromGraph1();
    	
    	//Test pour la deuxième carte
    	initGraph2();
    	validPathFromGraph2();
    }
	
    public void validPathFromGraph1() throws IOException{
    	//Noeud d'origine = Noeud de destination
    	data = new ShortestPathData(graph1, nodes[0], nodes[0], ArcInspectorFactory.getAllFilters().get(0));
    	solution = makeSolution(data);
    	assertEquals(null,solution.getPath());
    	
    	//Premier chemin
    	data = new ShortestPathData(graph1, nodes[0], nodes[2], ArcInspectorFactory.getAllFilters().get(0));
    	solution = makeSolution(data);
    	
    	//Chemin valide?
    	assertTrue(solution.getPath().isValid());
    	
    	//Plus court chemin correct?
    	for(int i=0;i<shortPath1.getArcs().size();i++) {
    		assertEquals(shortPath1.getArcs().get(i),solution.getPath().getArcs().get(i));
    	}
    	
    	//Longueur Correcte?
    	assertEquals((int)shortPath1.getLength(),(int)solution.getPath().getLength());
    	
    	//Deuxième chemin (shortPath3)
    	data = new ShortestPathData(graph1, nodes[4], nodes[1], ArcInspectorFactory.getAllFilters().get(0));
    	solution = makeSolution(data);
    	
    	//Chemin valide?
    	assertTrue(solution.getPath().isValid());
    	
    	//Plus court chemin correct?
    	for(int i=0;i<shortPath3.getArcs().size();i++) {
    		assertEquals(shortPath3.getArcs().get(i),solution.getPath().getArcs().get(i));
    	}
    	
    	//Longueur Correcte?
    	assertEquals((int)shortPath3.getLength(),(int)solution.getPath().getLength());
    	
    }
    
    //@Test
    public void validPathFromGraph2() throws IOException{
    	//Noeud d'origine = Noeud de destination
    	data = new ShortestPathData(graph2, nodes[0], nodes[0], ArcInspectorFactory.getAllFilters().get(0));
    	solution = makeSolution(data);
    	assertEquals(null,solution.getPath());
    	
    	noRestrictionCar();
    	initGraph2();
    	data = new ShortestPathData(graph2, nodes[0], nodes[3], ArcInspectorFactory.getAllFilters().get(0));
    	solution = makeSolution(data);
    	assertTrue(solution.getPath().isValid());
    	for(int i=0;i<shortPath2.getArcs().size();i++) {
    		assertEquals(shortPath2.getArcs().get(i),solution.getPath().getArcs().get(i));
    	}
    	noRestrictionPed();
    	initGraph2();
    	data = new ShortestPathData(graph2, nodes[0], nodes[3], ArcInspectorFactory.getAllFilters().get(0));
    	solution = makeSolution(data);
    	assertTrue(solution.getPath().isValid());
    	for(int i=0;i<shortPath2.getArcs().size();i++) {
    		assertEquals(shortPath2.getArcs().get(i),solution.getPath().getArcs().get(i));
    	}
    }
    
    @Test
    public void testCost() throws IOException{
    	noRestrictionCar();
    	initGraph1();
    	data = new ShortestPathData(graph1, nodes[0], nodes[2], ArcInspectorFactory.getAllFilters().get(0));
    	solution = makeSolution(data);
    	assertEquals((int)shortPath1.getLength(),(int)solution.getPath().getLength());
    }
	

}
