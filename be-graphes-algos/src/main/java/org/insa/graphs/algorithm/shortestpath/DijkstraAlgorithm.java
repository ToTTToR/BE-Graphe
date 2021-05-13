package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	protected ArrayList<Label> labels = new ArrayList<Label>();
		
    public DijkstraAlgorithm(ShortestPathData data) {
    	super(data);
    }
    //Fonction d'initialisation qui sera utile pour le A star (Tous les sommets marqué faux, cout infini et aucun père.)
    protected void SetLabels(ShortestPathData data) {
    	for(int i=0;i<data.getGraph().getNodes().size();i++) 
        	labels.add(new Label(data.getGraph().getNodes().get(i),null,Double.POSITIVE_INFINITY,false));
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        SetLabels(data);
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        //System.out.println("Taille de labels : "+this.labels.size());
        Label labelOrigin = labels.get(data.getOrigin().getId());
        labelOrigin.setCost(0.0);;
        heap.insert(labelOrigin);
        while(!heap.isEmpty() && !heap.findMin().isMarked()) { //Condition : pile non vide et le sommet de la pile n'est pas marqué
        	Label minTas = heap.deleteMin();
        	minTas.setMarked();
        	notifyNodeMarked(minTas.SommetCourant);
        	if(minTas.SommetCourant.getId() == data.getDestination().getId()) { //Si le sommet extrait correspond à notre destination
        		break; //On a trouvé la destination avec un cout minimal
        	}
        	//System.out.println("Nombre de successeurs : "+ minTas.SommetCourant.getNumberOfSuccessors());
        	for(Arc successeur : minTas.SommetCourant.getSuccessors()){
        		//Il faut voir si le chemin est valide (peut être parcouru selon notre mode de transport)! Sinon on skip.
        		if (!data.isAllowed(successeur)) {
                    continue;
                }
        //Il faut voir si nous avons pas déjà marqué le sommet successeur, c'est à dire que l'on ait déjà traité ou non
        		if(!labels.get(successeur.getDestination().getId()).isMarked()) {
        			double w = data.getCost(successeur);
        			Label LabelSucc = labels.get(successeur.getDestination().getId());
        			Label LabelCourant = labels.get(successeur.getOrigin().getId());
        			//On compare le cout du sommet successeur avec le cout du sommet courant + le cout du trajet
        			if(LabelSucc.getCost() > LabelCourant.getCost() + w) { 
        				//On regarde si le sommet a un père => si le label est dans la pile.
        				if(LabelSucc.getFather()!=null) heap.remove(LabelSucc); 
        				LabelSucc.setCost(LabelCourant.getCost() + w);
        				LabelSucc.setFather(successeur); 
        				heap.insert(LabelSucc);
        			}
        		}
        	}
        }
        //Si dans notre tableau de labels, le dernier ne correspond pas à celui de notre destination, le problème n'est pas faisable
        //(Graphe non connexe par exemple.)
        if (labels.get(data.getDestination().getId()).getFather() == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labels.get(data.getDestination().getId()).getFather();
            //System.out.println("Construction path");
            while (arc != null) {
                arcs.add(arc);
                arc = labels.get(arc.getOrigin().getId()).getFather();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            Path chemin = new Path(graph, arcs);
            if(!chemin.isValid()) System.out.println("Chemin non valide!");
            //System.out.println(chemin.getLength());
            //System.out.println("Longueur du chemin : "+chemin.getLength());
            solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin);
            //System.out.println("Longueur du chemin par l'algo : "+solution.getPath().getLength());
        }
        
        return solution;
    }

}
