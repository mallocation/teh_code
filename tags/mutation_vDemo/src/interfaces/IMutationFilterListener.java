package interfaces;

public interface IMutationFilterListener {
	
	public void selectAllVisible(boolean bSelectAll);
	
	public void filterMutations(String mutantSearch, String mutantType, String oldOp, String newOp);

}
