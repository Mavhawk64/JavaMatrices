package matrices;

public class Main {

	public static void main(String[] args) {
		Matrix a;
		Matrix b;
		Matrix c;
		try {
			a = new Matrix(2,3);
			b = new Matrix(3,2);
			c = new Matrix(3,3);
			a.set(0, 0, 1);
			a.set(0, 1, 2);
			a.set(0, 2, 3);
			a.set(1, 0, 4);
			a.set(1, 1, 5);
			a.set(1, 2, 6);
			
			b.set(0, 0, 7);
			b.set(0, 1, 8);
			b.set(1, 0, 9);
			b.set(1, 1, 10);
			b.set(2, 0, 11);
			b.set(2, 1, 12);
			
			int co = 1;
			for(int i = 0; i < c.getRowCount(); i++) {
				for(int j = 0; j < c.getColCount(); j++) {
					c.set(i, j, co++);
				}
			}
			
//			System.out.println(a.toString());
//			System.out.println("\n");
//			System.out.println(b.toString());
			
			// Following Errors as expected.
//			System.out.println(a.plus(b).toString());
			
			
//			System.out.println(a.getCol(2).toString());
			
//			System.out.println("\n");
//			
//			System.out.println(a.times(b).toString());
			
			
			// Following Errors as expected
//			System.out.println(a.inverse());
			
			
			System.out.println(c.toString());
			
			System.out.println(c.ref().toString());
			
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
	}

}
