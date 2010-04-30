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
			if ((oObject.getMutableClass().getFileName().equals(oMutableObject.getMutableClass().getFileName()) && oObject.getMutableClass().getClassName().equals(oMutableObject.getMutableClass().getClassName())) && oObject.getMutantLevel().equals(oMutableObject.getMutantLevel()) && oObject.getMutantType().equals(oMutableObject.getMutantType()) && oObject.getOldOperator().equalsIgnoreCase(oMutableObject.getOldOperator()) && oObject.getNewOperator().equalsIgnoreCase(oMutableObject.getNewOperator())) {
				if (oObject.getMutantLevel().equals(IMutableObject.eMutantLevel.METHOD)) { 
					if (oObject.getMethodName().equalsIgnoreCase(oMutableObject.getMethodName())) {
						return true;
					} else {
						return false;
					}					
				}
				return true;
			}
		}
		return false;
	}	
}
