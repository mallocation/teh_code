package mutations;

import interfaces.IMutableObject;

import java.util.ArrayList;
// TODO Implement this class in place of array lists.
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
