package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class LabelStar extends Label{
	//---------- ATTRIBUTS ----------//
	private float estimatedCost;
	
	//---------- CONSTRUCTEURS ----------//
	public LabelStar(int _nodeNum, float _estimatedCost) {
		super(_nodeNum);
		this.estimatedCost = _estimatedCost;
	}
	
	//---------- METHODES ----------//
	
	public float getEstimatedCost() {
		return this.estimatedCost;
	}
	
	public float getTotalCost() {
		//cout depuis l'origine + cout estimé jusqu'a la destination
		return (float)(this.getCost() + this.getEstimatedCost());
	}
	
}
