import static org.junit.Assert.*;

import org.junit.Test;

public class BaumTest {

	@Test
	public void isSiblingOfSuccessfullTest(){
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
	
	@Test
	public void isAuntOfSuccessfullTest(){
		
		IPerson elderSister = new Person("elderSister", Gender.FEMALE);
		IPerson youngerSister = new Person("youngerSister", Gender.FEMALE);
		IPerson sonOfElderSister = new Person("son", Gender.MALE);
		IPerson father = new Person("father", Gender.MALE);
		
		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addChildToParent(elderSister, father);
		baum.addChildToParent(youngerSister, father);
		
		IBaum baum2 = new Baum();
		baum2.addRootPerson(elderSister);
		baum2.addChildToParent(sonOfElderSister, elderSister);
		
		
		assertTrue(baum.isAuntOf(youngerSister, sonOfElderSister));
		
		
		
	}
	
	/*@Test
	public void isCousineOfTest(){
		IPerson son = new Person("Son", Gender.MALE);
		IPerson daughter = new Person("Daughter", Gender.FEMALE);	
		IBaum baum = new Baum();
		
	}*/
}
