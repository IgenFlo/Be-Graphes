package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
        Graph graph = data.getGraph();
        List<Node> nodes = graph.getNodes();
        ArrayList<Label> labels = new ArrayList<Label>();
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        //INITIALISATION
        for (int i = 0 ; i < nodes.size()-1 ; i++) {
        	Label newLabel = new Label(nodes.get(i).getId());
        	newLabel.setMarque(false);
        	newLabel.setCost(Float.MAX_VALUE);
        	newLabel.setPere(null);
        	labels.add(newLabel);
        }
        
        labels.get(data.getOrigin().getId()).setCost(0);
        tas.insert(labels.get(data.getOrigin().getId()));
        
        //DEROULEMENT
        int compteur = 1;
        Label label_x = null;
        Label label_y = null;
        boolean aLaFin = false;
        
        while (compteur < nodes.size() && !aLaFin) {
        	label_x = tas.deleteMin();
        	if (label_x == labels.get(data.getDestination().getId())) {
        		aLaFin = true;
        	} else {
	        	label_x.setMarque(true);
	        	compteur++;
	        	for (Arc arc : nodes.get(label_x.getNum()).getSuccessors()) {
	        		label_y = labels.get(arc.getDestination().getId());
	        		if (!label_y.isMarked()) {
	        			float oldCost_y = label_y.getCost();
	        			label_y.setCost(Math.min(label_y.getCost(), label_x.getCost() + arc.getLength()));
	        			if (oldCost_y - label_y.getCost() != 0) {
	        				tas.remove(label_y);
	        				tas.insert(label_y);
	        				label_y.setPere(arc);
	        			}
	        		}
	        	}
        	}
        }
        
        System.out.println("fin parcours");
        
        List<Arc> listArcsSolution = new ArrayList<Arc>();
        Arc currentArc = label_y.getPere();
        while (currentArc != null) {
	        listArcsSolution.add(label_y.getPere());
	        currentArc = labels.get(label_y.getPere().getOrigin().getId()).getPere();
        }
        
        Path newPath = new Path(graph, listArcsSolution);
        
        solution = new ShortestPathSolution(data, Status.OPTIMAL, newPath);
        
        return solution;
    }

}
