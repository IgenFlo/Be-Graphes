package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;

public class Label implements Comparable<Label> {
	
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
	public int compareTo(Label l) {
		float diff = this.getTotalCost() - l.getTotalCost();
		if (diff == 0) {
			return 0;
		} else if (diff < 0) {
			return -1;
		} else {
			return 1;
		}
	}
	
	public boolean isMarked() {
		return this.marque;
	}
	
	public int getNum() {
		return this.numeroSommet;
	}
	
	public float getCost() {
		return this.cout;
	}
	
	public float getTotalCost() {
		return this.cout;
	}
	
	public Arc getPere() {
		return this.pere;
	}
	
	public void setCost(float _cost) {
		this.cout = _cost;
	}
	public void setMarque(boolean _marque) {
		this.marque = _marque;
	}
	public void setPere(Arc _pere) {
		this.pere = _pere;
	}

}