
public class Average {
	int n1, n2, n3, n4;
	
	public Average() {
		n1 = 0;
		n2 = 1;
		n3 = 2;
		n4 = 3;
	}
	
	public int subtract() {
		return n1 - n2;
	}
	
	public int add() {
		return n1 + n2;
	}
	
	public int rem() {
		return n1 + n2 % n3 * n4 - n3 /n2;
	}
}
