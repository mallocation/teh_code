package mutations;

import interfaces.IMutableObject;

import java.util.ArrayList;

/**
 * This class is used to hold mutants to be passed throughout different
 * modules in the mutation tool program.
 * 
 * @author teh_code
 */
public class MutantCollection {
	
	/**
	 * Inner collection of mutants (This class is basically a wrapper around an array list!)
	 */
	private ArrayList<IMutableObject> alMutants;
	
	/**
	 * Create a new empty collection.
	 */
	public MutantCollection() {
		this.alMutants = new ArrayList<IMutableObject>();
	}
	
	/**
	 * Create a new collection with the specific contents.
	 * 
	 * @param alMutants collection to contain
	 */
	public MutantCollection(ArrayList<IMutableObject> alMutants) {
		this.alMutants = alMutants;
	}
	
	/**
	 * Add an IMutableObject to the collection
	 * 
	 * @param oMutant mutant to add
	 */
	public void addMutant(IMutableObject oMutant) {
		this.alMutants.add(oMutant);
	}
	
	public void removeMutant(IMutableObject oMutant) {
		this.alMutants.remove(oMutant);
	}
	
	/**
	 * Return the mutants in this collection.
	 * 
	 * @return ArrayList of IMutableObjects
	 */
	public ArrayList<IMutableObject> getMutants() {
		return this.alMutants;
	}
	
	/**
	 * Return the number of mutants in this collection.
	 * 
	 * @return count of mutants
	 */
	public int getCollectionCount() {
		return this.alMutants.size();
	}
	
	public IMutableObject getMutant(int i) {
		return this.getMutants().get(i);
	}
	
	public void appendToCollection(MutantCollection oCollection) {
		for (int i=0; i<oCollection.getCollectionCount(); i++) {
			this.addMutant(oCollection.getMutant(i));
		}
	}
	
	public boolean containsMutableObject(IMutableObject oMutableObject) {
		for (int i=0; i<this.alMutants.size(); i++) {
			IMutableObject oObject = this.alMutants.get(i);
			if (oObject.getMutableClass() == oMutableObject.getMutableClass() &&
					oObject.getMutableMethod() == oMutableObject.getMutableMethod() &&
					oObject.getMutantLevel() == oMutableObject.getMutantLevel() &&
					oObject.getMutantType() == oMutableObject.getMutantType() &&
					oObject.getOldOperator() == oMutableObject.getOldOperator() &&
					oObject.getNewOperator() == oMutableObject.getNewOperator()) {
				return true;
			}
		}
		return false;
	}	
}
