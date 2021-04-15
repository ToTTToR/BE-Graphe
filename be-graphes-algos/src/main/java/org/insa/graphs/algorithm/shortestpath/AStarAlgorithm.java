package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import org.insa.graphs.model.*;

public class AStarAlgorithm extends DijkstraAlgorithm {
	
	private ArrayList<LabelStar> labels;

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    	labels = new ArrayList<LabelStar>();
    }
    
    public void setLabels(ShortestPathData data) {
    	System.out.println("Je passe par là");
        for(int i=0;i<data.getGraph().getNodes().size();i++) 
        	this.labels.add(new LabelStar(data.getGraph().getNodes().get(i),null,Double.POSITIVE_INFINITY,false,data.getOrigin()));
        System.out.println("Taille de labels : "+labels.size());
    }
    
    
    @Override
    public ArrayList<LabelStar> getLabels(){
    	return this.labels;
    }
}
