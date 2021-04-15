package org.insa.graphs.model;

public class Label implements Comparable<Label>{
	public Node SommetCourant;
	
	private boolean Marked;
	
	private double Cout;
	
	private Arc père;
	
	public double getCost() {
		return this.Cout;
	}
	
	public Label(Node sommetCourant, Arc père,double cout,boolean marked) {
		this.Cout = cout;
		this.SommetCourant = sommetCourant;
		this.père = père;
		this.Marked = marked;
	}
	
	public void setCost(double newCost) {
		this.Cout = newCost;
	}
	
	public void setFather(Arc newFather) {
		this.père = newFather;
	}
	
	public Arc getFather() {
		return this.père;
	}
	
	public void setMarked() {
		this.Marked = true;
	}
	
	public boolean isMarked() {
		return this.Marked;
	}
	
	public double getCoutTotal() {return 0.0;}
	
	public int compareTo(Label other) {
        return Double.compare(this.getCost(), other.getCost());
    }
}