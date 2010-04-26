package mutations;

import interfaces.IMutableObject;

import java.util.ArrayList;

public class MutantCollection {
	
	private ArrayList<IMutableObject> alMutants;
	
	public MutantCollection() {
		this.alMutants = new ArrayList<IMutableObject>();
	}
	
	public MutantCollection(ArrayList<IMutableObject> alMutants) {
		this.alMutants = alMutants;
	}
	
	public ArrayList<IMutableObject> getMutants() {
		return this.alMutants;
	}
	
	public int getCollectionCount() {
		return this.alMutants.size();
	}
}
