package org.insa.graphs.algorithm.shortestpath;

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
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
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
        		//Il faut voir si le chemin est valide! Sinon on skip.
        		if (!data.isAllowed(successeur)) {
                    continue;
                }
        //Il faut voir si nous avons pas déjà marqué le sommet, c'est à dire que l'on ait déjà traité ou non
        		if(!labels.get(successeur.getDestination().getId()).isMarked()) {
        			double w = data.getCost(successeur);
        			Label LabelSucc = labels.get(successeur.getDestination().getId());
        			Label LabelCourant = labels.get(successeur.getOrigin().getId());
        			if(LabelSucc.getCost() > LabelCourant.getCost() + w) {
        				LabelSucc.setCost(LabelCourant.getCost() + w);
        				LabelSucc.setFather(successeur);
        				try { //On regarde si l'élément est déjà dans la pile avec remove car on doit l'update
        					heap.remove(LabelSucc);
        				} catch (ElementNotFoundException e) {} //Sinon, il suffit juste d'insérer l'élément.
        				heap.insert(LabelSucc);
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
