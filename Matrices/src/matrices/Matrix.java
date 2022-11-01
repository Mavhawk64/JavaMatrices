package matrices;

public class Matrix {
	private static final int X_MAX = 32768;
	private static final int Y_MAX = 32767;
	
	private double[][] m;
	private int x = 0, y = 0; // Set default values of 0 x 0.

	public Matrix(int x, int y) throws MatrixCreationException {
		if(x > X_MAX || y > Y_MAX) throw new MatrixCreationException("Matrix Size is limited to a maximum of (" + X_MAX + " x " + Y_MAX + ").");
		this.m = new double[x][y];
		this.x = x;
		this.y = y;
	}

	public Matrix() throws MatrixCreationException {
		this(0, 0);
	}
	/**
	 * Function: size()
	 * Description: Returns the size of the matrix as a cantor-paired number using the x and y as inputs to pi(a,b).
	 * @return cantor-paired number representing the size of the matrix.
	 */
	public int size() {
		return ((this.getRowCount() + this.getColCount() + 1) * (this.getRowCount() + this.getColCount())) / 2 + this.getColCount();
	}
	
	public int getRowCount() {
		return this.x;
	}
	
	public int getColCount() {
		return this.y;
	}
	
	public Matrix plus(Matrix a) throws MatrixException {
		if(this.size() != a.size())
			throw new MatrixSizeMismatchException("Matrix Size (" + this.getRowCount() + " x " + this.getColCount() + ") != (" + a.getRowCount() + " x " + a.getColCount() + ").");
		for(int i = 0; i < this.getRowCount(); i++) {
			for(int j = 0; j < this.getColCount(); j++) {
				try {
				this.m[x][y] += a.get(i, j);
				} catch (MatrixException e) {
					throw e;
				}
			}
		}
		return this;
	}
	
	public Matrix minus(Matrix a) throws MatrixException {
		if(this.size() != a.size())
			throw new MatrixSizeMismatchException("Matrix Size (" + this.getRowCount() + " x " + this.getColCount() + ") != (" + a.getRowCount() + " x " + a.getColCount() + ").");
		for(int i = 0; i < this.getRowCount(); i++) {
			for(int j = 0; j < this.getColCount(); j++) {
				try {
				this.m[x][y] -= a.get(i, j);
				} catch (MatrixException e) {
					throw e;
				}
			}
		}
		return this;
	}

	public Matrix times(Matrix a) throws MatrixException {
		if(this.getColCount() != a.getRowCount())
			throw new MatrixSizeMismatchException("Matrix Sizes (" + this.getRowCount() + " x " + this.getColCount() + ") and (" + a.getRowCount() + " x " + a.getColCount() + ") are not compatible for multiplication operation.");
		Matrix b = new Matrix(this.getRowCount(), a.getColCount());
		for(int i = 0; i < b.getRowCount(); i++) {
			for(int j = 0; j < b.getColCount(); j++) {
				b.set(i, j, this.getRow(i).dot(a.getCol(j)));
			}
		}
		return b;
	}
	
	public Matrix inverse() throws MatrixException {
		if(this.getColCount() != this.getRowCount()) {
			throw new MatrixNotSquareException("Inverses cannot be taken on non-square matrices.");
		}
		Matrix t = this.augment(new IdentityMatrix(this.getRowCount())).rref();
//		Matrix r = t.rref();
		return t;
	}
	
	public Matrix augment(Matrix b) throws MatrixException {
		Matrix t = new Matrix(this.getRowCount(), this.getColCount() + b.getColCount());
		for(int i = 0; i < t.getRowCount(); i++) {
			for(int j = 0; j < t.getColCount(); j++) {
				double val = j >= this.getColCount() ? b.get(i, j - this.getColCount()) : this.get(i, j);
				t.set(i, j, val);
			}
		}
		return t;
	}
	
	public Matrix rref() throws MatrixException {
		this.ref();
		for (int i = this.getRowCount() - 2; i >= 0; i--) {
			for(int j = 1; j < this.getRowCount() - 1; j++) {
				this.m[i] = this.subtractRowByScalar(i, i+j, this.m[i][i+j]).toArray();
			}
		}
		return this;
	}
	
	// TODO: FIX THE REF, RREF, and INVERSE METHODS
	
	public Matrix ref() throws MatrixException {
		for(int i = 0; i < this.getRowCount(); i++) {
			for(int j = 0; j < i; j++) {
				this.m[i] = this.subtractRowByScalar(i,j,this.m[i][j]).toArray();
			}
			this.m[i] = this.multiplyRowByScalar(i,this.m[i][i]).toArray();
		}
		return this;
	}
	
	public Vector subtractRowByScalar(int i, int j, double s) throws MatrixException {
		Vector u = new Vector(this.getColCount(), false);
			for(int c = 0; c < u.getColCount(); c++) {
				u.set(0, c, this.get(i, c) - this.get(j, c) * s);
			}
		return u;
	}
	
	public Vector multiplyRowByScalar(int i, double s) throws MatrixException {
		Vector u = new Vector(this.getColCount(), false);
		return u.multiplyByScalar(s);
	}
	
	public void set(int x, int y, double value) throws MatrixException {
		try {
			this.checkErrorsAt(x, y);
		} catch (MatrixException e) {
			throw e;
		}
		this.m[x][y] = value;
	}

	public double get(int x, int y) throws MatrixException {
		try {
			this.checkErrorsAt(x, y);
		} catch (MatrixException e) {
			throw e;
		}
		return this.m[x][y];
	}
	
	public Vector getRow(int x) throws MatrixException {
		this.checkErrorsAt(x, 0);
		Vector v = new Vector(this.getColCount(), false);
		for(int i = 0; i < this.getColCount(); i++) {
			v.set(0, i, this.m[x][i]);
		}
		return v;
	}
	
	public Vector getCol(int x) throws MatrixException {
		this.checkErrorsAt(0, x);
		Vector v = new Vector(this.getRowCount(), true);
		for(int i = 0; i < this.getRowCount(); i++) {
			v.set(i, 0, this.m[i][x]);
		}
		return v;
	}

	private void checkErrorsAt(int x, int y) throws MatrixException {
		if (this.m == null)
			throw new MatrixException("Matrix Object not set to an instance of an object.");
		if (x < 0 || x >= this.m.length)
			throw new MatrixIndexOutOfBoundsException("Matrix Index Out of Bounds: Given: (" + x + ", " + y
					+ ") for matrix of size (" + this.getRowCount() + " x " + this.getColCount() + ").");
		if (this.m[x] == null)
			throw new MatrixException("Matrix Object not set to an instance of an object at index " + x + ".");
		if (y < 0 || y >= this.m[x].length)
			throw new MatrixIndexOutOfBoundsException("Matrix Index Out of Bounds: Given: (" + x + ", " + y
					+ ") for matrix of size (" + this.getRowCount() + " x " + this.getColCount() + ").");
	}
	
	@Override
	public String toString() {
		String r = "[\n";
		for(int i = 0; i < this.getRowCount(); i++) {
			r += "[";
			for(int j = 0; j < this.getColCount(); j++) {
				try {
					r += this.get(i, j);
				} catch (MatrixException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(j != this.getColCount() - 1)
					r += " ";
			}
			r += "]";
			if(i != this.getRowCount() - 1)
				r += ",\n";
		}
		r += "\n]";
		return r;
	}
}
