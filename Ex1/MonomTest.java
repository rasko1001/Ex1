package Ex1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MonomTest {

	@Test
	void testAdd() 
	{
		Monom m1 = new Monom(4,5);
		Monom m2 = new Monom(1,5);
		Monom m3 = new Monom(-4,5);
		Monom mtest = new Monom(0,0);
		mtest.add(m1);
		
		assertEquals(mtest, m1);
		mtest.add(m2);
		assertEquals(mtest, new Monom(5,5));
		
		//fail("Not yet implemented");
	}

	@Test
	void testMultiply() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}

}
