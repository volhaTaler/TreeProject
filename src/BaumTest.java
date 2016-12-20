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
	public void isAuntOfSuccessfullTest() {

		IPerson elderSister = new Person("elderSister", Gender.FEMALE);
		IPerson youngerSister = new Person("youngerSister", Gender.FEMALE);
		IPerson sonOfElderSister = new Person("son", Gender.MALE);
		IPerson father = new Person("father", Gender.MALE);

		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addChildToParent(elderSister, father);
		baum.addChildToParent(youngerSister, father);
		baum.addChildToParent(sonOfElderSister, elderSister);

		assertTrue(baum.isAuntOf(youngerSister, sonOfElderSister));
	}

	@Test
	public void isAuntOfFailedTest() {

		IPerson elderSister = new Person("elderSister", Gender.FEMALE);
		IPerson youngerSister = new Person("youngerSister", Gender.FEMALE);
		IPerson sonOfElderSister = new Person("son", Gender.MALE);
		IPerson father = new Person("father", Gender.MALE);

		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addChildToParent(elderSister, father);
		// baum.addChildToParent(youngerSister, father);
		baum.addChildToParent(sonOfElderSister, elderSister);

		assertFalse(baum.isAuntOf(youngerSister, sonOfElderSister));
	}
	
	/*die struktur: mother - Wurzelknoten - has a daughter (daughter1). 
	 * The daughter (daughter1) has a daughter (daughter2) and a son (sonOfDaughter1).
	 *  The daughter (daughter2) has two sons (elderSonOfDaughter2 and youngerSonOfDaughter2).
	 * Check of Uncle-Nephew Relation.   */
	@Test
	public void isUncleOfSuccessfullTest(){
		
		IPerson mother = new Person("mother", Gender.MALE);
		IPerson daughter1 = new Person("daughter1", Gender.FEMALE);
		IPerson daughter2 = new Person("daughter2", Gender.FEMALE);
		IPerson sonOfDaughter1 = new Person("son1", Gender.MALE);
		IPerson elderSonOfDaughter2 = new Person("son2", Gender.MALE);
		IPerson youngerSonOfDaughter2 = new Person("son3", Gender.MALE);
		

		IBaum baum = new Baum();
		baum.addRootPerson(mother);
		baum.addChildToParent(daughter1, mother);
		baum.addChildToParent(daughter2, daughter1);
		baum.addChildToParent(sonOfDaughter1, daughter1);
		baum.addChildToParent(elderSonOfDaughter2, daughter2);
		baum.addChildToParent(youngerSonOfDaughter2, daughter2);

		assertTrue(baum.isUncleOf(youngerSonOfDaughter2, sonOfDaughter1));	
	}
	// Testcase: the gender is incorrect
	@Test
	public void isUncleOfFailedTest(){
		
		IPerson mother = new Person("mother", Gender.MALE);
		IPerson youngerDaughter = new Person("daughter1", Gender.FEMALE);
		IPerson elderDaughter = new Person("daughter2", Gender.FEMALE);
		IPerson sonOfDaughter = new Person("son1", Gender.MALE);
		
		

		IBaum baum = new Baum();
		baum.addRootPerson(mother);
		baum.addChildToParent(youngerDaughter, mother);
		baum.addChildToParent(elderDaughter, mother);
		baum.addChildToParent(sonOfDaughter, youngerDaughter);
		

		assertFalse(baum.isUncleOf(elderDaughter, sonOfDaughter));	
	}

	@Test
	public void isCousineOfSuccessfullTest(){
		
		IPerson father = new Person("Father", Gender.MALE);
		IPerson son = new Person("son", Gender.MALE);
		IPerson daughter = new Person("daughter", Gender.FEMALE);
		IPerson childOfson = new Person("Son's child", Gender.MALE);
		IPerson childOfdaughter = new Person("Daughter's child", Gender.FEMALE);
		
		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addChildToParent(son, father);
		baum.addChildToParent(daughter, father);
		baum.addChildToParent(childOfson, son);
		baum.addChildToParent(childOfdaughter, daughter);
		
		assertTrue(baum.isCousineOf(childOfson, childOfdaughter));

	}
	/*
	 * a Testcase for a tree as follows:
	 *  mother(root) -- son (first child) -- a child of son (childOfSon) -- a child of childOfSon (grandchildOfSon) -- daughter
	 * (second child) --- a child of daughter (childOfDaughter) So the
	 * childOfDaughter and grandchildOfSon are aunt and nephew.
	 */

	@Test
	public void isCousineOfFailedTest() {

		IPerson mother = new Person("Mother", Gender.MALE);
		IPerson son = new Person("Son", Gender.MALE);
		IPerson daughter = new Person("Daughter", Gender.FEMALE);
		IPerson childOfson = new Person("Son's child", Gender.MALE);
		IPerson grandchildOfson = new Person("Son's grandchild", Gender.FEMALE);
		IPerson childOfdaughter = new Person("Daughter's child", Gender.FEMALE);

		IBaum baum = new Baum();
		baum.addRootPerson(mother);
		baum.addChildToParent(son, mother);
		baum.addChildToParent(daughter, mother);
		baum.addChildToParent(childOfson, son);
		baum.addChildToParent(childOfdaughter, daughter);
		baum.addChildToParent(childOfson, grandchildOfson);

		assertFalse(baum.isCousineOf(grandchildOfson, childOfdaughter));

	}
	
	@Test
	public void isGrandchildOfSuccessfullTest(){
		
		IPerson grandmom = new Person("Grandmom", Gender.FEMALE);
		IPerson mother = new Person("Mother", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE);
		IBaum baum = new Baum();
		baum.addRootPerson(grandmom);
		baum.addChildToParent(mother, grandmom);
		baum.addChildToParent(son, mother);
		
		assertTrue(baum.isGrandchildOf(son, grandmom));
	}
	
	@Test
	public void isGrandchildOfFailedTest(){
		
		IPerson grandmom = new Person("Grandmom", Gender.FEMALE);
		IPerson mother = new Person("Mother", Gender.FEMALE);
		IPerson father = new Person("Father", Gender.MALE);
		IPerson son = new Person("Son", Gender.MALE);
		IBaum baum = new Baum();
		baum.addRootPerson(grandmom);
		baum.addChildToParent(mother, grandmom);
		baum.addChildToParent(son, father);
		
		assertFalse(baum.isGrandchildOf(son, grandmom));
	}
	
	@Test
	public void isGrandparentOfSuccessfullTest(){
		
		IPerson grandmom = new Person("Grandmom", Gender.FEMALE);
		IPerson mother = new Person("Mother", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE);
		IBaum baum = new Baum();
		baum.addRootPerson(grandmom);
		baum.addChildToParent(mother, grandmom);
		baum.addChildToParent(son, mother);
		
		assertTrue(baum.isGrandparentOf(grandmom, son));
	}
	
	@Test
	public void isGrandparentOfFailedTest() {

		IPerson mother = new Person("Mother", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE);
		IPerson grandmom = new Person("Grandmom", Gender.FEMALE);
		IBaum baum = new Baum();
		baum.addRootPerson(mother);
		baum.addChildToParent(mother, grandmom);
		baum.addChildToParent(son, mother);

		assertFalse(baum.isGrandparentOf(grandmom, son));
	}
}
