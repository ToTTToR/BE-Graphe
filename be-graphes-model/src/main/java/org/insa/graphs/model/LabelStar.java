package org.insa.graphs.model;

import java.util.ArrayList;
import java.util.Collections;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.*;

public class LabelStar extends Label {
	private double coutEstime;
	
	private double coutOrigin;
	
	private Node truc;
	
	public LabelStar(Node sommetCourant, Arc père,double cout,boolean marked,ShortestPathData data) {
		super(sommetCourant, père,cout,marked);
		this.truc.
	}
	
	public double getCoutTotal() {
		return this.coutEstime + this.coutOrigin;
	}
}
