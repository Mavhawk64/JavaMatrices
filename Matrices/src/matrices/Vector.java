package matrices;

public class Vector extends Matrix {
	private boolean isColumn;
	public Vector() throws MatrixCreationException {
		super();
	}
	
	public Vector(int x, boolean isColumn) throws MatrixCreationException {
		super(isColumn ? x : 1, isColumn ? 1 : x);
		this.isColumn = isColumn;
	}
	
	public boolean getIsColumn() {return this.isColumn;}
	
	public double dot(Vector a) throws MatrixException {
		double[] v = this.toArray();
		double[] w = a.toArray();		
		
		double r = 0;
		
		if(v.length != w.length) {
			throw new VectorLengthMismatchException("Vector length " + v.length + " != " + w.length + ".");
		}
		
		for(int i = 0; i < v.length; i++) {
			r += v[i] * w[i];
		}
		
		return r;
	}
	
	public double[] toArray() throws MatrixException {
		double[] a = new double[this.isColumn ? this.getRowCount() : this.getColCount()];
		for(int i = 0; i < a.length; i++) {
			a[i] = this.isColumn ? this.get(i, 0) : this.get(0, i);
		}
		return a;
	}
	
	public Vector multiplyByScalar(double s) throws MatrixException {
		for(int i = 0; i < (this.isColumn ? this.getRowCount() : this.getColCount()); i++) {
			if(this.isColumn)
				this.set(i, 0, this.get(i, 0) * s);
			else
				this.set(0, i, this.get(0, i) * s);
		}
		return this;
	}

}
