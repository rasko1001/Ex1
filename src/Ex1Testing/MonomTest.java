package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.Monom;
import Ex1.function;

class MonomTest {

	@Test
	void testMonomDoubleInt() {
		Monom m1= new Monom(3.4,5);
		Monom m2= new Monom(-5.4,3);
		Monom m3= new Monom(1.8,0);

		assertEquals("3.4X^5",m1.toString());	
		assertEquals("-5.4X^3",m2.toString());
		assertEquals("1.8",m3.toString());
	}

	@Test
	void testMonomMonom() {

		Monom m1 = new Monom(3.44,7);
		Monom m2 = new Monom(-6.8,3);
		Monom m3 = new Monom(-2.6,0);

		Monom mgetmonom1 = new Monom(m1);
		Monom mgetmonom2 = new Monom(m2);
		Monom mgetmonom3 = new Monom(m3);

		assertEquals("3.44X^7",mgetmonom1.toString());	
		assertEquals("-6.8X^3",mgetmonom2.toString());
		assertEquals("-2.6",mgetmonom3.toString());

	}

	@Test
	void testGet_coefficient() {
		Monom m1= new Monom(3.44,7);
		Monom m2= new Monom(-6.8,3);
		Monom m3= new Monom(-2.6,0);

		Monom mgetmonom1= new Monom(m1);
		Monom mgetmonom2= new Monom(m2);
		Monom mgetmonom3= new Monom(m3);

		assertEquals(3.44,m1.get_coefficient());	
		assertEquals(-6.8,mgetmonom2.get_coefficient());
		assertEquals(-2.6,m3.get_coefficient());

	}

	@Test
	void testGet_power() {
		Monom m1= new Monom(3.44,7);
		Monom m2= new Monom(-6.8,3);
		Monom m3= new Monom(-2.6,0);

		Monom mgetmonom1= new Monom(m1);
		Monom mgetmonom2= new Monom(m2);
		Monom mgetmonom3= new Monom(m3);

		assertEquals(7,m1.get_power());	
		assertEquals(3,mgetmonom2.get_power());
		assertEquals(0,m3.get_power());
	}

	@Test
	void testDerivative() {
		Monom m1 = new Monom(3.44,7);
		Monom m2 = new Monom(-6.8,3);
		Monom m3 = new Monom(-2.6,0);

		Monom mgetmonom1 = new Monom(m1);
		Monom mgetmonom2 = new Monom(m2);
		Monom mgetmonom3 = new Monom(m3);

		assertEquals("24.08X^6",m1.derivative().toString());
		assertEquals("-20.4X^2",m2.derivative().toString());
		assertEquals("0",m3.derivative().toString());
		assertEquals("24.08X^6",mgetmonom1.derivative().toString());
		assertEquals("-20.4X^2",mgetmonom2.derivative().toString());
		assertEquals("0",mgetmonom3.derivative().toString());
	}

	@Test
	void testF() {
		Monom m1= new Monom(3.44,3);
		Monom m2= new Monom(-6.8,3);
		Monom m3= new Monom(-2.6,0);

		assertEquals(0.220016,m1.f(0.4),0.01);
		assertEquals(-183.6,m2.f(3));
		assertEquals(-2.6,m3.f(-1.8));	
	}

	@Test
	void testIsZero() {
		Monom m1= new Monom(3.44,3);
		Monom m2= new Monom(-6.8,3);
		Monom m3= new Monom(0,0);
		
		assertEquals(false,m1.isZero());	
		assertEquals(false,m2.isZero());
		assertEquals(true,m3.isZero());	
		
	}

	@Test
	void testMonomString() {
		String [] monoms= {"-2.4x^3","13.0x^3","5x-4","0.002x^4","0.01","3x^2-5x+3","3xgh "};
		int result = 0;
		for (int i = 0; i < monoms.length; i++)
		{
			try
			{
				Monom m=new Monom(monoms[i]);
				result++;
			}
			catch (Exception e) {
					System.out.println(e.getMessage());
			}
			
		}
		assertEquals(4,result);	
	}



	@Test
	void testAdd() {
		Monom m1= new Monom("-2.5x^3");
		Monom m2= new Monom("1.5x^3");
		Monom m3= new Monom(0.5,4);
		Monom m4=new Monom("0.001x^4");
		
		m1.add(m2);
		m3.add(m4);
		
		assertEquals("-X^3",m1.toString());	
		assertEquals("1.5X^3",m2.toString());
		assertEquals("0.501X^4",m3.toString());
		assertEquals("0.001X^4",m4.toString());
	}

	@Test
	void testMultipy() {
		Monom m1= new Monom("-2.5x^3");
		Monom m2= new Monom("1.5x^3");
		Monom m3= new Monom(0.5,4);
		Monom m4=new Monom("0.01x^4");

		m1.multiply(m2);
		m3.multiply(m4);

		assertEquals("-3.75X^6",m1.toString());	
		assertEquals("1.5X^3",m2.toString());
		assertEquals("0.005X^8",m3.toString());
		assertEquals("0.01X^4",m4.toString());
	}

	@Test
	void testToString() {
		Monom m1= new Monom("-2.5x^3");
		Monom m2= new Monom("-1.5x^7");
		Monom m3= new Monom(m1);
		Monom m4=new Monom(5.4,6);

		assertEquals("-2.5X^3",m1.toString());	
		assertEquals("-1.5X^7",m2.toString());
		assertEquals("-2.5X^3",m3.toString());
		assertEquals("5.4X^6",m4.toString());
	}

	@Test
	void testInitFromString() {
		String [] monoms= {"-2.5x^3","-1.5x^7","4x-4","33x^3","-4,5x^2","3x^2-5x+3"};
		int result=0;

		for (int i = 0; i < monoms.length; i++) 
		{
			try 
			{
				function f=new Monom(monoms[i]);
				result++;
			}
			catch (Exception e) {
				
			}
		}
		assertEquals(3, result);
	}

	@Test
	void testCopy() {
		Monom m = new Monom(3.7,9);
		Monom a=new Monom(m.copy().toString());
		assertEquals(m.toString(),a.toString());
		Monom expected = new Monom(3.7, 9);
		Monom actual = (Monom) expected.copy();
		
		m.multiply(new Monom(2,0));
		assertFalse(m.toString().equals(a.toString()));		
	}

}