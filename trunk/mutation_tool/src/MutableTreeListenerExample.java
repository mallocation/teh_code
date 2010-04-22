import controls.MutableNode;
import interfaces.IMutableTreeListener;


public class MutableTreeListenerExample implements IMutableTreeListener {

	@Override
	public void mutableNodeSelectionChanged(MutableNode oSelectedNode) {
		System.out.println(oSelectedNode.getMutableClass().getClassName());		
	}
	

}
