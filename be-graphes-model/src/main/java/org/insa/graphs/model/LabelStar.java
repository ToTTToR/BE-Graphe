package org.insa.graphs.model;

public class LabelStar extends Label {
	private double coutEstime;
	
	public LabelStar(Node sommetCourant, Arc père,double cout,boolean marked,Node Destination) {
		super(sommetCourant, père,cout,marked);
		this.coutEstime = Destination.getPoint().distanceTo(sommetCourant.getPoint());
	}
	
	public LabelStar(Node sommetCourant, Arc père,double cout,boolean marked,Node Destination, int vitesseEstimé) {
		super(sommetCourant, père,cout,marked);
		this.coutEstime = Destination.getPoint().distanceTo(sommetCourant.getPoint())*(double)vitesseEstimé;
	}
	
	public double getCoutTotal() {
		return this.coutEstime + this.getCost();
	}
}
