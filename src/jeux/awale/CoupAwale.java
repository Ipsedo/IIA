package jeux.awale;

import iia.jeux.modele.CoupJeu;

public class CoupAwale implements CoupJeu {
	
	private int indiceTrou;
	
	public CoupAwale(int indiceTrou){
		this.indiceTrou = indiceTrou;
	}
	
	public int getIndiceTrou(){
		return this.indiceTrou;
	}
	
	public String toString(){
		return "(rangé:"+this.indiceTrou/6+", trou"+(this.indiceTrou>5?this.indiceTrou-6:this.indiceTrou);
	}

}
