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
        
        //INITIALISATION (creation d'un label pour chaque node)
        for (int i = 0 ; i < nodes.size() ; i++) {
        	Label newLabel = new Label(nodes.get(i).getId());
        	newLabel.setMarque(false);
        	newLabel.setCost(Float.MAX_VALUE);
        	newLabel.setPere(null);
        	labels.add(newLabel);
        	System.out.println("taille labels : " + labels.size()  + " et node actuel : " + nodes.get(i).getId());
        }
        System.out.println("nbre nodes : " + nodes.size());
        
        labels.get(data.getOrigin().getId()).setCost(0);
        labels.get(data.getOrigin().getId()).setMarque(true);
        tas.insert(labels.get(data.getOrigin().getId()));
        notifyOriginProcessed(data.getOrigin());
        
        //DEROULEMENT
        int compteur = 1;
        Label label_x = null;
        Label label_y = null;
        boolean aLaFin = false;
        
        while (compteur < nodes.size() && !aLaFin) {
        	if (!tas.isEmpty()){
        		label_x = tas.deleteMin();
        		//System.out.println("noeud actuel (label_x) : " + label_x.getNum());
        		label_x.setMarque(true);
        		System.out.println("cout du sommet marque : " + label_x.getCost());
        		notifyNodeMarked(nodes.get(label_x.getNum()));
            	if (label_x == labels.get(data.getDestination().getId())) {
            		notifyDestinationReached(nodes.get(label_x.getNum()));
            		//System.out.println(data.getDestination().getId());
            		aLaFin = true;
            	} else {
    	        	compteur++;
    	        	for (Arc arc : nodes.get(label_x.getNum()).getSuccessors()) {
    	        		if (!data.isAllowed(arc)) {
                            continue;
                        }
    	        		label_y = labels.get(arc.getDestination().getId());
    	        		if (!label_y.isMarked()) {
    	        			float oldCost_y = label_y.getCost();
    	        			label_y.setCost(Math.min(oldCost_y, label_x.getCost() + arc.getLength()));
    	        			if ((oldCost_y - label_y.getCost()) != 0) {
    	        				//System.out.println("UPDATE de : " + label_y.getNum() + " d'ancien cout : " + oldCost_y + " et de nouveau cout : " + label_y.getCost());
    	        				if (oldCost_y != Float.MAX_VALUE) {
    	        					//System.out.println("noeud a remove : " + label_y.getNum());
    	        					tas.remove(label_y);
    	        				} else {
    	        					notifyNodeReached(nodes.get(label_y.getNum()));
    	        				}
    	        				tas.insert(label_y);
    	        				label_y.setPere(arc);
    	        			}
    	        		}
    	        	}
            	}
        	} else {
        		//System.out.println("le tas est vide");
        		compteur++;
        	}
        	
        }
        
        //System.out.println("fin parcours :");
        //System.out.println(tas);
        //System.out.println("le dernier : "+ label_x);
        //System.out.println("le node associé : " + label_x.getNum());
        //System.out.println("son pere : " + label_x.getPere());
        
        List<Arc> listArcsSolution = new ArrayList<Arc>();
        List<Node> listNodesSolution = new ArrayList<Node>();
        Arc currentArc = label_x.getPere();
        listNodesSolution.add(nodes.get(label_x.getNum()));
        while (currentArc != null) {
	        //System.out.println("l'arc actuel : " + currentArc);
	        //System.out.println("son origine : " + currentArc.getOrigin().getId());
	        listArcsSolution.add(currentArc);
	        listNodesSolution.add(0, currentArc.getOrigin());
	        currentArc = labels.get(currentArc.getOrigin().getId()).getPere();
        }
        
        System.out.println("solution récupérée : ");
        System.out.println(listArcsSolution);
        
        Path newPath = Path.createShortestPathFromNodes(graph, listNodesSolution);//new Path(graph, listArcsSolution);
        System.out.println("chemin solution valide ? " + newPath.isValid());
        System.out.print("taille du chemin  : " + newPath.getLength());
        if (compteur >= nodes.size() && !aLaFin) {
        	//System.out.println("sorti du parcours sans arriver à destination");
            solution = new ShortestPathSolution(data, Status.INFEASIBLE, newPath);
        } else {
            solution = new ShortestPathSolution(data, Status.OPTIMAL, newPath);
        }
        
        return solution;
    }

}
