import static org.junit.Assert.*;

import org.junit.Test;

public class BaumTest {

	@Test
	public void isSiblingOfSuccessTest(){
		IPerson father = new Person("Father", Gender.MALE);
		IPerson son = new Person("Son", Gender.MALE);
		IPerson daughter = new Person("Daughter", Gender.FEMALE);
		
		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addChildToParent(son, father);
		baum.addChildToParent(daughter, father);
		
		assertTrue(baum.isSiblingOf(son, daughter));
	}
	
	@Test
	public void isSiblingOfFailedTest(){
		
		IPerson son = new Person("Son", Gender.MALE);
		IPerson daughter = new Person("Daughter", Gender.FEMALE);
		
		IBaum baum = new Baum();
		
		assertFalse(baum.isSiblingOf(son, daughter));

	}
}
