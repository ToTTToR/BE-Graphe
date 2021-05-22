package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import org.insa.graphs.algorithm.*;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.*;
import org.insa.graphs.model.AccessRestrictions.AccessMode;
import org.insa.graphs.model.AccessRestrictions.AccessRestriction;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

//Class de test pour Dijkstra
public class AlgoTest {
	//Le dit graph
	private static Graph graph1,graph2,graphNotConnex,graphCarre;

	protected static Graph graphINSA;

    // Liste de nodes
    private static Node[] nodes;
    
    @SuppressWarnings("unused")
    private static Path emptyPath, singleNodePath1,singleNodePath2, shortPath1,shortPath2,shortPath3,shortPath4,invalidPath,
    fastPath1,fastPath2,fastPath3,fastPath4;
    
    private static Path Path1,Path2,Path3,Path4;
    
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
    
    @BeforeClass
    public static void initSomeGraphs() throws IOException{
    	
    	//INSA MAP
    	
    	String mapName = "/Users/macair/Desktop/INSA Learning/BE Graphe/Maps/insa.mapgr";
    	//String mapName = "/Users/viktor/Desktop/ReINSA LEARNING/BE Graphe/BE-Graphe/Maps/insa.mapgr"; Pour mon autre mac
    	GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
    	graphINSA = reader.read();
    	
    	//MAP CARRE
    	
    	mapName = "/Users/macair/Desktop/INSA Learning/BE Graphe/Maps/carre.mapgr";
    	//mapName = "/Users/viktor/Desktop/ReINSA LEARNING/BE Graphe/BE-Graphe/Maps/carre.mapgr";
    	reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
    	graphCarre = reader.read();
    }
    
    @BeforeClass
    public static void initGraphNotConnex() {
    	nodes = new Node[2];
    	nodes[0] = new Node(0,new Point((float)0.0,(float)0.0));
    	nodes[1] = new Node(1,new Point((float)10.0,(float)0.0));
    	graphNotConnex = new Graph("NoConecto","",Arrays.asList(nodes),null);
    }
    
    //@BeforeClass
	public static void initGraph1() throws IOException{
		nodes = new Node[5];
	    nodes[0]=new Node(0, new Point(0,0));
	    nodes[1]=new Node(1, new Point(10,1));
	    nodes[2]=new Node(2, new Point(-5,-1));
	    nodes[3]=new Node(3, new Point(13,1));
	    nodes[4]=new Node(4, new Point(13,-2));
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
        shortPath1 = new Path(graph1, Arrays.asList(new Arc[] { a2d, d2b, b2c })); //Chemin le plus court de a (0) vers c (2)
        fastPath1 = new Path(graph1,Arrays.asList(new Arc[] { a2b,b2c })); //Chemin le plus rapide de a (0) vers c (2)
        shortPath3 = new Path(graph1, Arrays.asList(new Arc[] { e2a, a2d, d2b })); //Chemin le plus court de e (4) vers b (1)
        fastPath3 = new Path(graph1,Arrays.asList(new Arc[] { e2a, a2b })); //Chemin le plus rapide de e (4) vers b (1)
	}
    
    public static void initGraph2() throws IOException{
    	nodes = new Node[5];
    	nodes[0]=new Node(0, new Point(0,0));
	    nodes[1]=new Node(1, new Point(4,1));
	    nodes[2]=new Node(2, new Point(7,3));
	    nodes[3]=new Node(3, new Point(7,-2));
	    nodes[4]=new Node(4, new Point(2,-2));
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
	    shortPath2 = new Path(graph2, Arrays.asList(new Arc[] {a2e,e2d})); //Chemin le plus court de a (0) vers d (3)
	    fastPath2 = new Path(graph2, Arrays.asList(new Arc[] {a2e,e2d})); //Chemin le plus rapide de a (0) vers d (3)
	    shortPath4 = new Path(graph2, Arrays.asList(new Arc[] {a2e,e2c})); //Chemin le plus court de a (0) vers c (2)
	    fastPath4 = new Path(graph2, Arrays.asList(new Arc[] {a2e,e2c})); //Chemin le plus rapide de a (0) vers c (2)
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
    	truc.put(AccessMode.FOOT, AccessRestriction.ALLOWED);
    	infoRoute1 = new RoadInformation(RoadType.UNCLASSIFIED, restrictions, true, 5, null);
		infoRoute2 = new RoadInformation(RoadType.UNCLASSIFIED, restrictions, true, 10, null);
    }
    @Test
    public void testShortSolutionForCar() throws IOException{
    	noRestrictionCar();
    	
    	//Graphe non connexe, pas de chemin possible entre 2 points
    	initGraphNotConnex();
    	data = new ShortestPathData(graphNotConnex, nodes[0], nodes[1], ArcInspectorFactory.getAllFilters().get(0));
    	solution = makeSolution(data);
    	assertTrue(!solution.isFeasible()); //Le problème n'est pas faisable pour un graphe non connexe
    	
    	//Test pour la première carte pour chemin le plus court
    	initGraph1();
    	validSolutionFromGraph1(ArcInspectorFactory.getAllFilters().get(0));
    	
    	//Test pour la deuxième carte
    	initGraph2();
    	validSolutionFromGraph2(ArcInspectorFactory.getAllFilters().get(0));
    }
    
    @Test
    public void testFastSolutionForCar() throws IOException{
    	noRestrictionCar();
    	
    	//Graphe non connexe, pas de chemin possible entre 2 points
    	initGraphNotConnex();
    	data = new ShortestPathData(graphNotConnex, nodes[0], nodes[1], ArcInspectorFactory.getAllFilters().get(2));
    	solution = makeSolution(data);
    	assertTrue(!solution.isFeasible()); //Le problème n'est pas faisable pour un graphe non connexe (si les 2 sommets ne sont pas connexe)
    	
    	//Test pour la première carte pour chemin le plus court
    	initGraph1();
    	validSolutionFromGraph1(ArcInspectorFactory.getAllFilters().get(2));
    	
    	//Test pour la deuxième carte
    	initGraph2();
    	validSolutionFromGraph2(ArcInspectorFactory.getAllFilters().get(2));
    }
	
    public void validSolutionFromGraph1(ArcInspector inspector) throws IOException{
    	if(inspector.getMode()==Mode.LENGTH) {
    		System.out.println("Mode longueur");
    		Path1 = shortPath1;
    		Path3 = shortPath3;
    	} else {
    		System.out.println("Mode temps");
    		Path1 = fastPath1;
    		Path3 = fastPath3;
    	}

    	//Noeud d'origine = Noeud de destination
    	data = new ShortestPathData(graph1, nodes[0], nodes[0], inspector);
    	solution = makeSolution(data);
    	assertEquals(null,solution.getPath());
    	
    	//Premier chemin 
    	data = new ShortestPathData(graph1, nodes[0], nodes[2], inspector);
    	solution = makeSolution(data);
    	
    	//Chemin valide?
    	assertTrue(solution.getPath().isValid());
    	
    	//Plus court/rapide chemin correct?
    	for(int i=0;i<Path1.getArcs().size();i++) {
    		assertEquals(Path1.getArcs().get(i).getOrigin(),solution.getPath().getArcs().get(i).getOrigin());
    		assertEquals(Path1.getArcs().get(i).getDestination(),solution.getPath().getArcs().get(i).getDestination());
    	}
    	
    	//Longueur Correcte?
    	assertEquals((int)Path1.getLength(),(int)solution.getPath().getLength());
    	
    	//Temps pris correct?
    	assertEquals((int)Path1.getMinimumTravelTime(),(int)solution.getPath().getMinimumTravelTime());
    	
    	//Deuxième chemin 
    	data = new ShortestPathData(graph1, nodes[4], nodes[1], inspector);
    	solution = makeSolution(data);
    	
    	//Chemin valide?
    	assertTrue(solution.getPath().isValid());
    	
    	//Plus court/rapide chemin correct?
    	for(int i=0;i<Path3.getArcs().size();i++) {
    		assertEquals(Path3.getArcs().get(i).getOrigin(),solution.getPath().getArcs().get(i).getOrigin());
    		assertEquals(Path3.getArcs().get(i).getOrigin(),solution.getPath().getArcs().get(i).getOrigin());
    	}
    	
    	//Longueur Correcte?
    	assertEquals((int)Path3.getLength(),(int)solution.getPath().getLength());
    	
    	//Temps pris correct?
    	assertEquals((int)Path3.getMinimumTravelTime(),(int)solution.getPath().getMinimumTravelTime());
    	
    }
    
    //@Test
    public void validSolutionFromGraph2(ArcInspector inspector) throws IOException{
    	
    	if(inspector.getMode()==Mode.LENGTH) {
    		System.out.println("Mode longueur");
    		Path2 = shortPath2;
    		Path4 = shortPath4;
    	} else {
    		System.out.println("Mode temps");
    		Path2 = fastPath2;
    		Path4 = fastPath4;
    	}
    	
    	//Noeud d'origine = Noeud de destination
    	data = new ShortestPathData(graph2, nodes[0], nodes[0], inspector);
    	solution = makeSolution(data);
    	assertEquals(null,solution.getPath());
    	
    	//Premier chemin
    	noRestrictionCar();
    	initGraph2();
    	data = new ShortestPathData(graph2, nodes[0], nodes[3], inspector);
    	solution = makeSolution(data);
    	
    	//Chemin valide?
    	assertTrue(solution.getPath().isValid());
    	
    	//Chemin correct?
    	for(int i=0;i<Path2.getArcs().size();i++) {
    		assertEquals(Path2.getArcs().get(i).getOrigin(),solution.getPath().getArcs().get(i).getOrigin());
    		assertEquals(Path2.getArcs().get(i).getDestination(),solution.getPath().getArcs().get(i).getDestination());
    	}
    	
    	//Longueur correcte? (pour distance)
    	assertEquals((int)Path2.getLength(),(int)solution.getPath().getLength());
    	
    	//Temps pris correct?
    	assertEquals((int)Path2.getMinimumTravelTime(),(int)solution.getPath().getMinimumTravelTime());
    	
    	//Deuxième chemin
    	data = new ShortestPathData(graph2, nodes[0], nodes[2], inspector);
    	solution = makeSolution(data);
    	
    	//Chemin valide?
    	assertTrue(solution.getPath().isValid());
    	
    	//Chemin correct?
    	for(int i=0;i<Path4.getArcs().size();i++) {
    		assertEquals(Path4.getArcs().get(i).getOrigin(),solution.getPath().getArcs().get(i).getOrigin());
    		assertEquals(Path4.getArcs().get(i).getDestination(),solution.getPath().getArcs().get(i).getDestination());
    	}
    	
    	//Longueur Correcte?
    	assertEquals((int)Path4.getLength(),(int)solution.getPath().getLength());
    	
    	//Temps pris correct?
    	assertEquals((int)Path4.getMinimumTravelTime(),(int)solution.getPath().getMinimumTravelTime());
    }
	
    @Test
    public void compareWithBellmanGraphCarre() throws IOException{
    	Random rand = new Random();
    	int max = graphCarre.getNodes().size();
    	//Test avec map carré
    	
    	//On fait 10 tests sur la carte carré en distance et en temps: 
    	
    	for(int i=0;i<10;i++) {
    		int origin = rand.nextInt(max);
    		int destination = rand.nextInt(max);
    	
	    	//Test en distance
	    	data = new ShortestPathData(graphCarre, graphCarre.getNodes().get(origin),graphCarre.getNodes().get(destination), ArcInspectorFactory.getAllFilters().get(0));
	    	BellmanFordAlgorithm BM = new BellmanFordAlgorithm(data);
	    	
	    	//Construction des solutions 
	    	ShortestPathSolution solutionBM = BM.run();
	    	solution = makeSolution(data);
	    	
	    	//Si le chemin n'est pas faisable, pas besoin de vérifier 
	    	if(!solutionBM.isFeasible())
	    		assertTrue(!solution.isFeasible());
	    	else {
		    	//Longueur des chemins égaux?
		    	assertEquals((int)solutionBM.getPath().getLength(),(int)solution.getPath().getLength());
		    	
		    	//Chemins égaux? (ici, on suppose qu'il n'existe qu'un unique chemin à cout minimal)
		    	/*for(int j=0;j<solutionBM.getPath().getArcs().size();j++) {
		    		assertEquals(solutionBM.getPath().getArcs().get(j),solution.getPath().getArcs().get(j));
		    	}*/
	    	}
		    //Test en temps
		    data = new ShortestPathData(graphCarre, graphCarre.getNodes().get(origin),graphCarre.getNodes().get(destination), ArcInspectorFactory.getAllFilters().get(2));
		    BM = new BellmanFordAlgorithm(data);
		    	
		    solutionBM = BM.run();
		    solution = makeSolution(data);
		    	
		    if(!solutionBM.isFeasible())
		    	assertTrue(!solution.isFeasible());
		    else {
		    	//Temps pris par les chemins égaux?
		    	assertEquals((int)solutionBM.getPath().getMinimumTravelTime(),(int)solution.getPath().getMinimumTravelTime());
		    	
		    	//Chemins égaux?
		    	/*for(int j=0;j<solutionBM.getPath().getArcs().size();j++) {
		    		assertEquals(solutionBM.getPath().getArcs().get(j),solution.getPath().getArcs().get(j));
		    	}*/
		    }
    	}
    }
    	
    @Test
    public void compareWithBellmanGraphINSA() throws IOException{
    	Random rand = new Random();
    	int max = rand.nextInt(graphINSA.getNodes().size());
    	//Test avec carte de l'INSA
    	
    	//On va faire 50 tests pour la carte INSA
    	
    	for(int j=0;j<50;j++) {
    		int origin = rand.nextInt(max);
    		int destination = rand.nextInt(max);
    	//Test en distance
    	
	    	data = new ShortestPathData(graphINSA, graphINSA.getNodes().get(origin),graphINSA.getNodes().get(destination), ArcInspectorFactory.getAllFilters().get(0));
	    	BellmanFordAlgorithm BM = new BellmanFordAlgorithm(data);
	    	ShortestPathSolution solutionBM = BM.run();
	    	solution = makeSolution(data);
	    	
	    	if(!solutionBM.isFeasible())
	    		assertTrue(!solution.isFeasible());
	    	else {
		    	//Longueur des chemins égaux?
		    	assertEquals((int)solutionBM.getPath().getLength(),(int)solution.getPath().getLength());
		    	
		    	//Chemins égaux? Sur un graphe aussi grand, on suppose qu'il n'existe qu'un unique chemin
		    	for(int i=0;i<solutionBM.getPath().getArcs().size();i++) {
		    		assertEquals(solutionBM.getPath().getArcs().get(i),solution.getPath().getArcs().get(i));
		    	}
	    	}
	    	
	    	//Test en temps
	    	data = new ShortestPathData(graphINSA, graphINSA.getNodes().get(origin),graphINSA.getNodes().get(destination), ArcInspectorFactory.getAllFilters().get(2));
	    	BM = new BellmanFordAlgorithm(data);
	    	solutionBM = BM.run();
	    	solution = makeSolution(data);
	    	
	    	if(!solutionBM.isFeasible())
	    		assertTrue(!solution.isFeasible());
	    	else {
		    	//Temps pris des chemins égaux?
		    	assertEquals((int)solutionBM.getPath().getMinimumTravelTime(),(int)solution.getPath().getMinimumTravelTime());
		    	
		    	//Chemins égaux?
		    	for(int i=0;i<solutionBM.getPath().getArcs().size();i++) {
		    		assertEquals(solutionBM.getPath().getArcs().get(i),solution.getPath().getArcs().get(i));
		    	}
	    	}
    	}
    }
    
    @Test
    public void FonctionnementDijkstraCorrect() throws IOException{
    	//Ceci est un test vérifiant que un sous-chemin d'un plus court chemin est un plus court
    
    	
    	//En distance
    	data = new ShortestPathData(graphINSA, graphINSA.getNodes().get(1304),graphINSA.getNodes().get(725), ArcInspectorFactory.getAllFilters().get(0));
    	DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(data);
    	ShortestPathSolution solutionDij = dijkstra.run();
    	for(Arc arc : solutionDij.getPath().getArcs()) {
    		ShortestPathData testData = new ShortestPathData(graphINSA, graphINSA.getNodes().get(1304),arc.getDestination(), ArcInspectorFactory.getAllFilters().get(0));
    		DijkstraAlgorithm dijkstraTest = new DijkstraAlgorithm(testData);
    		ShortestPathSolution solutionTest = dijkstraTest.run();
    		//On a créé un sous chemin entre le noeud d'origine et un noeud sur le chemin vers la destination,
    		//Et on compare si son dernier arc est le même que pour notre solution.
    		assertEquals(arc,solutionTest.getPath().getArcs().get(solutionTest.getPath().getArcs().size()-1));
    	}
    	
    	//En temps
    	data = new ShortestPathData(graphINSA, graphINSA.getNodes().get(1304),graphINSA.getNodes().get(725), ArcInspectorFactory.getAllFilters().get(2));
    	dijkstra = new DijkstraAlgorithm(data);
    	solutionDij = dijkstra.run();
    	for(Arc arc : solutionDij.getPath().getArcs()) {
    		ShortestPathData testData = new ShortestPathData(graphINSA, graphINSA.getNodes().get(1304),arc.getDestination(), ArcInspectorFactory.getAllFilters().get(2));
    		DijkstraAlgorithm dijkstraTest = new DijkstraAlgorithm(testData);
    		ShortestPathSolution solutionTest = dijkstraTest.run();
    		assertEquals(arc,solutionTest.getPath().getArcs().get(solutionTest.getPath().getArcs().size()-1));
    	}
    }
}
