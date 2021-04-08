package org.insa.graphs.algorithm.shortestpath;

import java.awt.List;
import java.util.ArrayList;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        ArrayList<Label> labels = new ArrayList<Label>();
        Graph graph = data.getGraph();
        BinaryHeap<Label> heap = new BinaryHeap();
      //Phase d'Initialisation (Tous les sommets marqué faux, cout infini et aucun père.
        for(int i=0;i<graph.getNodes().size();i++) {
        	labels.add(new Label(graph.getNodes().get(i),null,Double.POSITIVE_INFINITY,false));
        }
        Label labelOrigin = labels.get(data.getOrigin().getId());
        labelOrigin.setCost(0.0);;
        heap.insert(labelOrigin);
        while() {
        	Label minTas = heap.findMin();
        	minTas.setMarked();
        	for(Arc successeurs : minTas.SommetCourant.getSuccessors()){
        		if(labels.get(successeurs.getDestination().getId()))
        	}
        }
        return solution;
    }

}
