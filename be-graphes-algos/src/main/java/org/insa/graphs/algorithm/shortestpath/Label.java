package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;

public class Label {
	
	//---------- ATTRIBUTS ----------//
	protected int numeroSommet;
	protected boolean marque;
	protected float cout;
	protected Arc pere;
	
	//---------- CONSTRUCTEURS ----------//
	public Label(int _nodeNum) {
		this.numeroSommet = _nodeNum;
	}
	
	//---------- METHODES ----------//
	public float getCost() {
		return this.cout;
	}

}