package test;

import junit.framework.TestCase;

public class TT1 extends TestCase {
	Test1 test1;
	protected void setUp() throws Exception {
		super.setUp();
		test1 = new Test1();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testBinSearch(){
		int[] arr = {1,4,2,8,3,0,5};
		assertEquals(1,test1.binSearch(arr, 4));
	}
}
