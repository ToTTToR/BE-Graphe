package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.*;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    public void SetLabels(ShortestPathData data) {
    	if(data.getMode()==Mode.LENGTH) {
    		for(int i=0;i<data.getGraph().getNodes().size();i++) 
    			this.labels.add(new LabelStar(data.getGraph().getNodes().get(i),null,Double.POSITIVE_INFINITY,false,data.getDestination()));
    	} else {
    		for(int i=0;i<data.getGraph().getNodes().size();i++) 
    			this.labels.add(new LabelStar(data.getGraph().getNodes().get(i),null,Double.POSITIVE_INFINITY,false,data.getDestination(),data.getMaximumSpeed()));
    	}
    }
}
