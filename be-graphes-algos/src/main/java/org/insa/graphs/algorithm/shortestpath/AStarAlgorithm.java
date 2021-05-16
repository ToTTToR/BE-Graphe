package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.*;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
   
    @Override
    protected void SetLabels(ShortestPathData data) {
    	if(data.getMode()==Mode.LENGTH) {
    		System.out.println("Mode longueur");
    		for(int i=0;i<data.getGraph().getNodes().size();i++) 
    			labels.add(new LabelStar(data.getGraph().getNodes().get(i),null,Double.POSITIVE_INFINITY,false,data.getDestination()));
    	} else {
    		System.out.println("Mode temps");
    		for(int i=0;i<data.getGraph().getNodes().size();i++) 
    			labels.add(new LabelStar(data.getGraph().getNodes().get(i),null,Double.POSITIVE_INFINITY,false,data.getDestination(),data.getGraph().getGraphInformation().getMaximumSpeed()));
    	}
    }
}
