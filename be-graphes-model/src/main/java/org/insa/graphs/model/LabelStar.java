package org.insa.graphs.model;

public class LabelStar extends Label {
	private double coutEstime;
	
	public LabelStar(Node sommetCourant, Arc père,double cout,boolean marked,Node Destination) {
		super(sommetCourant, père,cout,marked);
		this.coutEstime = Destination.getPoint().distanceTo(sommetCourant.getPoint());
	}
	
	public double getCoutTotal() {
		return this.coutEstime + this.getCost();
	}
	
	public int compareTo(LabelStar other) {
		if(this.getCoutTotal() == other.getCoutTotal())
			return Double.compare(other.coutEstime,this.coutEstime);
		else
			return Double.compare(this.getCoutTotal(), other.getCoutTotal());
    }
}
