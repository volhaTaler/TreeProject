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

		IPerson elderSister = new Person("Elder Sister", Gender.FEMALE);
		IPerson youngerSister = new Person("Younger Sister", Gender.FEMALE);
		IPerson sonOfElderSister = new Person("Son", Gender.MALE);
		IPerson father = new Person("Father", Gender.MALE);

		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addChildToParent(elderSister, father);
		baum.addChildToParent(youngerSister, father);
		baum.addChildToParent(sonOfElderSister, elderSister);

		assertTrue(baum.isAuntOf(youngerSister, sonOfElderSister));
	}

	@Test
	public void isAuntOfFailedTest() {

		IPerson elderSister = new Person("Elder Sister", Gender.FEMALE);
		IPerson youngerSister = new Person("Younger Sister", Gender.FEMALE);
		IPerson sonOfElderSister = new Person("Son", Gender.MALE);
		IPerson father = new Person("Father", Gender.MALE);

		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addChildToParent(elderSister, father);
		baum.addChildToParent(sonOfElderSister, elderSister);

		assertFalse(baum.isAuntOf(youngerSister, sonOfElderSister));
	}
	
	/*die struktur: mother - Wurzelknoten - has one child: a son. 
	 * The son has two children: a son: (sonOfSon1) and a daughter: (daughter1).
	 *  The daughter (daughter1) has one son (sonOfDaughter1).
	 * Check of Uncle-Nephew Relation.   */
	@Test
	public void isUncleOfSuccessfullTest(){
		
		IPerson mother = new Person("Mother", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE);
		IPerson daughter1 = new Person("Daughter of Son", Gender.FEMALE);
		IPerson sonOfSon1 = new Person("Son of Son1", Gender.MALE);
		IPerson sonOfDaughter1 = new Person("Son of the Daughter", Gender.MALE);
	
		IBaum baum = new Baum();
		baum.addRootPerson(mother);
		baum.addChildToParent(son, mother);
		baum.addChildToParent(daughter1, son);
		baum.addChildToParent(sonOfSon1, son);
		baum.addChildToParent(sonOfDaughter1, daughter1);

		assertTrue(baum.isUncleOf(sonOfSon1, sonOfDaughter1));	
	}
	// Testcase: the gender is incorrect
	@Test
	public void isUncleOfFailedTest(){
		
		IPerson mother = new Person("Mother", Gender.FEMALE);
		IPerson youngerDaughter = new Person("Younger Daughter", Gender.FEMALE);
		IPerson elderDaughter = new Person("Elder Daughter", Gender.FEMALE);
		IPerson sonOfDaughter = new Person("Son1", Gender.MALE);
		
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
		IPerson son = new Person("Son", Gender.MALE);
		IPerson daughter = new Person("Daughter", Gender.FEMALE);
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

		IPerson mother = new Person("Mother", Gender.FEMALE);
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

	/*
	 * Testcase: Father has got married to fathersWife. Each of them had a
	 * child. After their marriage the children are defined as siblings
	 */
	@Test
	public void isSiblingOfMarriedParentsSuccessfullTest() {
		IPerson father = new Person("Father", Gender.MALE);
		IPerson fathersWife = new Person("Mother-in-Law ", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE);
		IPerson daughter = new Person("Daughter", Gender.FEMALE);
		
		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addRootPerson(fathersWife);
		baum.addChildToParent(son, father);
		baum.addPartner(father, fathersWife);
		baum.addChildToParents(daughter, fathersWife, father);
		baum.addChildToParent(son, fathersWife);

		assertTrue(baum.isSiblingOf(daughter, son));
	}
	
	@Test
	public void isSiblingOfMarriedParentsFailedTest() {
		IPerson father = new Person("Father", Gender.MALE);
		IPerson father2 = new Person("Father2", Gender.MALE);
		IPerson fathersWife = new Person("Mother-in-Law ", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE);
		IPerson daughter = new Person("Daughter", Gender.FEMALE);
		
		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addRootPerson(fathersWife);
		baum.addRootPerson(father2);
		baum.addPartner(father, fathersWife);
		baum.addChildToParent(daughter, father2);
		baum.addChildToParents(son, fathersWife, father);
		
		assertFalse(baum.isSiblingOf(daughter, son));
	}
	@Test
	public void addParentToRootSuccessfullTest() {

		IPerson father = new Person("Father", Gender.MALE);
		IPerson fathersMother = new Person("Grandma", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE); 
		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addChildToParent(son, father);
		baum.addRootPerson(fathersMother);
		baum.addChildToParent(father, fathersMother);
		

		assertTrue(baum.isGrandchildOf(son, fathersMother));
	}
	
	@Test
	public void isUncleOfSuccessfullSecondTest(){
		
		IPerson mother = new Person("Father", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE);
		IPerson daughter = new Person("Daughter", Gender.FEMALE);
		IPerson grandmom = new Person("Grandpa", Gender.FEMALE);
		//IPerson sonOfDaughter1 = new Person("son3", Gender.MALE);
	
		IBaum baum = new Baum();
		baum.addRootPerson(mother);
		baum.addRootPerson(son);
		baum.addChildToParent(son, mother);
		baum.addChildToParent(daughter, son);
		baum.addPartner(son, daughter);
		baum.addRootPerson(grandmom);
		baum.addChildToParent(mother, grandmom);
		//baum.addChildToParent(sonOf, son);
		//baum.addChildToParent(sonOfDaughter, daughter);

		assertTrue(baum.isGrandchildOf(mother, grandmom));	
	}
}
