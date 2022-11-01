package matrices;

public class IdentityMatrix extends Matrix {

	public IdentityMatrix(int x) throws MatrixException {
		super(x,x);
		this.createIdentity();
	}
	
	private void createIdentity() throws MatrixException {
		int x = this.getRowCount();
		for(int i = 0;  i < x; i++) {
			this.set(i, i, 1);
		}
	}
}
