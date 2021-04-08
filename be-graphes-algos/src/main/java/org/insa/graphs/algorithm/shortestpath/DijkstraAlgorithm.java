package org.insa.graphs.algorithm.shortestpath;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
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
        while(!heap.isEmpty()) {
        	Label minTas = heap.deleteMin();
        	minTas.setMarked();
        	for(Arc successeur : minTas.SommetCourant.getSuccessors()){
        		if(!labels.get(successeur.getDestination().getId()).isMarked()) {
        			double w = data.getCost(successeur);
        			int IdSucc = successeur.getDestination().getId();
        			int IdCourant = successeur.getOrigin().getId();
        			if(labels.get(IdSucc).getCost() > labels.get(IdCourant).getCost() + w) {
        				labels.get(IdSucc).setCost(labels.get(IdCourant).getCost() + w);
        				labels.get(IdSucc).setFather(successeur);
        				try {
        					heap.remove(labels.get(IdSucc));
        				} catch (ElementNotFoundException e) {}
        				heap.insert(labels.get(IdSucc));
        			}
        		}
        	}
        }
        
        if (labels.get(data.getDestination().getId()) == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labels.get(data.getDestination().getId()).getFather();
            while (arc != null) {
                arcs.add(arc);
                arc = labels.get(arc.getOrigin().getId()).getFather();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        
        return solution;
    }

}
