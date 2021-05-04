package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.junit.Test;

public class AlgoTest {
	//Le dit graph
	private static Graph graph;

    // Liste de nodes
    private static Node[] nodes;
    
    private static Path emptyPath, singleNodePath, shortPath, longPath, loopPath, longLoopPath,
    invalidPath;
    
    private static Arc a2b, a2c, a2d, b2c,b2d,d2b,d2c,d2e,c2e,e2c,e2a;
	
	public static void initAll() {
		RoadInformation infoRoute = new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null);
		nodes = new Node[5];
	    for (int i = 0; i < nodes.length; ++i) {
	        nodes[i] = new Node(i, null);
	    }
	    a2b = Node.linkNodes(nodes[0], nodes[1], 10,infoRoute,null);
	    b2c = Node.linkNodes(nodes[1], nodes[2], 1,infoRoute,null);
	    a2d = Node.linkNodes(nodes[0], nodes[3], 5,infoRoute,null);
	    b2d = Node.linkNodes(nodes[1], nodes[3], 2,infoRoute,null);
	    d2b = Node.linkNodes(nodes[3], nodes[1], 3,infoRoute,null);
	    a2b = Node.linkNodes(nodes[0], nodes[1], 10,infoRoute,null);
	    d2c = Node.linkNodes(nodes[3], nodes[2], 9,infoRoute,null);
	    d2e = Node.linkNodes(nodes[3], nodes[4], 2,infoRoute,null);
	    c2e = Node.linkNodes(nodes[2], nodes[4], 4,infoRoute,null);
	    e2c = Node.linkNodes(nodes[4], nodes[2], 5,infoRoute,null);
	    e2a = Node.linkNodes(nodes[4], nodes[0], 3,infoRoute,null);
	    
	    graph = new Graph("ID", "", Arrays.asList(nodes), null);
	    emptyPath = new Path(graph, new ArrayList<Arc>());
        singleNodePath = new Path(graph, nodes[1]);
        shortPath = new Path(graph, Arrays.asList(new Arc[] { a2d, d2b, b2c }));
        invalidPath = new Path(graph, Arrays.asList(new Arc[] { a2d, b2c,c2e}));
	}
	
	

}
