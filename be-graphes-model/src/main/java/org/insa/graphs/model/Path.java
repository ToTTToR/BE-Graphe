package org.insa.graphs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Class representing a path between nodes in a graph.
 * </p>
 * 
 * <p>
 * A path is represented as a list of {@link Arc} with an origin and not a list
 * of {@link Node} due to the multi-graph nature (multiple arcs between two
 * nodes) of the considered graphs.
 * </p>
 *
 */
public class Path {

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the fastest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     * deprecated Need to be implemented.
     */
    public static Path createFastestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
    	Path result;
        List<Arc> arcs = new ArrayList<Arc>();
        if(nodes.size()==1) //Il n'y a pas grand chose à faire avec ces 2 conditions, car il n'y a pas d'arc.
			result = new Path(graph,nodes.get(0));
        else if(nodes.size() == 0)
        	result = new Path(graph);
		else {
			int indice = 0;
	        for(Node truc : nodes) {
	        	if (truc == nodes.get(nodes.size()-1)) break; /* Si l'on est au dernier noeud de la liste,
	        	peu importe ses successeurs, on skip. */
	        	List<Arc> successeurs = truc.getSuccessors();
	        	float travelMin = (float)1/0;
	        	Arc arcMin = truc.getSuccessors().get(0);
	        	int successeurNonValide = 0;
		        for(Arc machin : successeurs) { /*Il faut aller voir chaque successeur de chaque noeud de la liste
		        pour voir si il y a un noeud successeur qui fait partie de la liste de noeud donnée. */
		        	if(machin.getDestination()==nodes.get(indice+1)) { //On regarde si le successeur fait partie de la liste donnée
		        		if(machin.getMinimumTravelTime()<=travelMin) { //Et il faut voir s'il y a un arc plus court pour y aller.
		        			travelMin = (float)machin.getMinimumTravelTime();
		        			arcMin = machin;
		        		}
		        	} else successeurNonValide++;
		        }
		        if(successeurNonValide == successeurs.size()) throw new IllegalArgumentException("Aucun successeur valide");
	        	arcs.add(arcMin);
	        	indice++;
	        }
	        result = new Path(graph, arcs);
        }
        return result;
    }

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the shortest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     * deprecated Need to be implemented.
     */
    public static Path createShortestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
    	Path result;
        List<Arc> arcs = new ArrayList<Arc>();
        if(nodes.size()==1) //Il n'y a pas grand chose à faire avec ces 2 conditions, car il n'y a pas d'arc.
			result = new Path(graph,nodes.get(0));
        else if(nodes.size() == 0)
        	result = new Path(graph);
		else {
			int indice = 0;
	        for(Node truc : nodes) {
	        	if (truc == nodes.get(nodes.size()-1)) break; /* Si l'on est au dernier noeud de la liste,
	        	peu importe ses successeurs, on skip. */
	        	List<Arc> successeurs = truc.getSuccessors();
	        	float longMin = (float)1/0;
	        	Arc arcMin = truc.getSuccessors().get(0);
	        	int successeurNonValide = 0;
		        for(Arc machin : successeurs) { /*Il faut aller voir chaque successeur de chaque noeud de la liste
		        pour voir si il y a un noeaud successeur qui fait partie de la liste de noeud donnée. */
		        	if(machin.getDestination()==nodes.get(indice+1)) { //On regarde si le successeur fait partie de la liste donnée
		        		if(machin.getLength()<=longMin) { //Et il faut voir s'il y a un arc plus court pour y aller.
		        			longMin = machin.getLength();
		        			arcMin = machin;
		        		}
		        	} else successeurNonValide++;
		        if(successeurNonValide == successeurs.size()) throw new IllegalArgumentException("Aucun successeur valide");
	        	}
	        	arcs.add(arcMin);
	        	indice++;
	        }
	        result = new Path(graph, arcs);
        }
        return result;
    }

    /**
     * Concatenate the given paths.
     * 
     * @param paths Array of paths to concatenate.
     * 
     * @return Concatenated path.
     * 
     * @throws IllegalArgumentException if the paths cannot be concatenated (IDs of
     *         map do not match, or the end of a path is not the beginning of the
     *         next).
     */
    public static Path concatenate(Path... paths) throws IllegalArgumentException {
        if (paths.length == 0) {
            throw new IllegalArgumentException("Cannot concatenate an empty list of paths.");
        }
        final String mapId = paths[0].getGraph().getMapId();
        for (int i = 1; i < paths.length; ++i) {
            if (!paths[i].getGraph().getMapId().equals(mapId)) {
                throw new IllegalArgumentException(
                        "Cannot concatenate paths from different graphs.");
            }
        }
        ArrayList<Arc> arcs = new ArrayList<>();
        for (Path path: paths) {
            arcs.addAll(path.getArcs());
        }
        Path path = new Path(paths[0].getGraph(), arcs);
        if (!path.isValid()) {
            throw new IllegalArgumentException(
                    "Cannot concatenate paths that do not form a single path.");
        }
        return path;
    }

    // Graph containing this path.
    private final Graph graph;

    // Origin of the path
    private final Node origin;

    // List of arcs in this path.
    private final List<Arc> arcs;

    /**
     * Create an empty path corresponding to the given graph.
     * 
     * @param graph Graph containing the path.
     */
    public Path(Graph graph) {
        this.graph = graph;
        this.origin = null;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path containing a single node.
     * 
     * @param graph Graph containing the path.
     * @param node Single node of the path.
     */
    public Path(Graph graph, Node node) {
        this.graph = graph;
        this.origin = node;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path with the given list of arcs.
     * 
     * @param graph Graph containing the path.
     * @param arcs Arcs to construct the path.
     */
    public Path(Graph graph, List<Arc> arcs) {
        this.graph = graph;
        this.arcs = arcs;
        this.origin = arcs.size() > 0 ? arcs.get(0).getOrigin() : null;
    }

    /**
     * @return Graph containing the path.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @return First node of the path.
     */
    public Node getOrigin() {
        return origin;
    }

    /**
     * @return Last node of the path.
     */
    public Node getDestination() {
        return arcs.get(arcs.size() - 1).getDestination();
    }

    /**
     * @return List of arcs in the path.
     */
    public List<Arc> getArcs() {
        return Collections.unmodifiableList(arcs);
    }

    /**
     * Check if this path is empty (it does not contain any node).
     * 
     * @return true if this path is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.origin == null;
    }

    /**
     * Get the number of <b>nodes</b> in this path.
     * 
     * @return Number of nodes in this path.
     */
    public int size() {
        return isEmpty() ? 0 : 1 + this.arcs.size();
    }

    /**
     * Check if this path is valid.
     * 
     * A path is valid if any of the following is true:
     * <ul>
     * <li>it is empty;</li>
     * <li>it contains a single node (without arcs);</li>
     * <li>the first arc has for origin the origin of the path and, for two
     * consecutive arcs, the destination of the first one is the origin of the
     * second one.</li>
     * </ul>
     * 
     * @return true if the path is valid, false otherwise.
     * 
     * deprecated Need to be implemented.
     */
    public boolean isValid() {
        boolean result = true;
        List<Arc> arcs = this.getArcs();
        if(this.isEmpty() || this.size()==1) {
        	//System.out.println("Il y a un chemin avec 0 ou 1 noeud");
        	result = true;
        } else if(arcs.get(0).getOrigin() == this.origin){
        	int taille = this.getArcs().size();
        	//System.out.println("Nombre d'arc du chemin : "+taille);
        	for(int i=0;i<taille-1;i++) {
        		if(arcs.get(i).getDestination() != arcs.get(i+1).getOrigin()) {
        			//System.out.println("Chemin non valide");
        			result = false;
        			break;
        		}
        	}
        } else result = false;
        return result;
    }

    /**
     * Compute the length of this path (in meters).
     * 
     * @return Total length of the path (in meters).
     * 
     * deprecated Need to be implemented.
     */
    public float getLength() {
    	float length = 0;
    	for(Arc truc : this.getArcs()) {
    		length += truc.getLength();
    	}
        return length;
    }

    /**
     * Compute the time required to travel this path if moving at the given speed.
     * 
     * @param speed Speed to compute the travel time.
     * 
     * @return Time (in seconds) required to travel this path at the given speed (in
     *         kilometers-per-hour).
     * 
     * deprecated Need to be implemented.
     */
    public double getTravelTime(double speed) {
        double time = 0;
        for(Arc truc : this.getArcs()) {
    		time += truc.getTravelTime(speed);
    	}
        return time;
    }

    /**
     * Compute the time to travel this path if moving at the maximum allowed speed
     * on every arc.
     * 
     * @return Minimum travel time to travel this path (in seconds).
     * 
     * deprecated Need to be implemented.
     */
    public double getMinimumTravelTime() {
    	double time = 0;
        for(Arc truc : this.getArcs()) {
    		time += truc.getMinimumTravelTime();
    	}
        return time;
    }

}
