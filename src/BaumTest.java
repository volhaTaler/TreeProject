import static org.junit.Assert.*;

import org.junit.Test;

public class BaumTest {

	@Test
	public void isSiblingOfSuccessfullTest() {
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
	public void isSiblingOfFailedTest() {
		IPerson son = new Person("Son", Gender.MALE);
		IPerson daughter = new Person("Daughter", Gender.FEMALE);
		IBaum baum = new Baum();

		assertFalse(baum.isSiblingOf(son, daughter));
	}

	@Test
	public void isAuntOfSuccessfulTest() {
		IPerson daughter = new Person("Elder Sister", Gender.FEMALE);
		IPerson son = new Person("Younger Sister", Gender.MALE);
		IPerson sonOfSon = new Person("Son", Gender.MALE);
		IPerson father = new Person("Father", Gender.MALE);

		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addRootPerson(daughter);
		baum.addChildToParent(son, father);
		baum.addChildToParent(daughter, father);
		baum.addChildToParent(sonOfSon, son);

		assertTrue(baum.isUncleOrAuntFromFatherSideOf(daughter, sonOfSon));
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

		assertFalse(baum.isUncleOrAuntFromFatherSideOf(youngerSister, sonOfElderSister));
	}

	/*
	 * die struktur: mother - Wurzelknoten - has one child: a son. The son has
	 * two children: a son: (sonOfSon1) and a daughter: (daughter1). The
	 * daughter (daughter1) has one son (sonOfDaughter1). Check of Uncle-Nephew
	 * Relation.
	 */
	@Test
	public void isUncleOrAuntFromMotherSideOfSecondSuccessfulTest() {
		IPerson mother = new Person("Mother", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE);
		IPerson daughter1 = new Person("Daughter of Son", Gender.FEMALE);
		IPerson sonOfSon1 = new Person("Son of Son1", Gender.MALE);
		IPerson sonOfDaughter1 = new Person("Son of the Daughter", Gender.MALE);

		IBaum baum = new Baum();
		baum.addRootPerson(son);
		baum.addRootPerson(sonOfDaughter1);
		baum.addChildToParent(son, mother);
		baum.addChildToParent(daughter1, son);
		baum.addChildToParent(sonOfSon1, son);
		baum.addChildToParent(sonOfDaughter1, daughter1);

		assertTrue(baum.isUncleOrAuntFromMotherSideOf(sonOfSon1, sonOfDaughter1));
		assertTrue(baum.isGrandparentOf(mother, sonOfSon1));
	}

	// Testcase: the gender is incorrect
	@Test
	public void isUncleOfFailedTest() {
		IPerson mother = new Person("Mother", Gender.FEMALE);
		IPerson youngerDaughter = new Person("Younger Daughter", Gender.FEMALE);
		IPerson elderDaughter = new Person("Elder Daughter", Gender.FEMALE);
		IPerson sonOfDaughter = new Person("Son1", Gender.MALE);

		IBaum baum = new Baum();
		baum.addRootPerson(mother);
		baum.addChildToParent(youngerDaughter, mother);
		baum.addChildToParent(elderDaughter, mother);
		baum.addChildToParent(sonOfDaughter, youngerDaughter);

		assertFalse(baum.isUncleOrAuntFromMotherSideOf(sonOfDaughter, sonOfDaughter));
	}

	@Test
	public void isCousineOfSuccessfulTest() {
		IPerson father = new Person("Fatherdd", Gender.FEMALE);
		IPerson son = new Person("Sondd", Gender.FEMALE);
		IPerson daughter = new Person("Daughterdd", Gender.FEMALE);
		IPerson childOfSon = new Person("Son's child", Gender.MALE);
		IPerson childOfDaughter = new Person("Daughter's child", Gender.FEMALE);

		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addRootPerson(son);
		baum.addRootPerson(daughter);
		baum.addRootPerson(childOfSon);
		baum.addRootPerson(childOfDaughter);
		baum.addChildToParent(son, father);
		baum.addChildToParent(daughter, father);
		baum.addChildToParent(childOfSon, son);
		baum.addChildToParent(childOfDaughter, daughter);

		assertTrue(baum.isCousineOf(childOfDaughter, childOfSon));

	}
	/*
	 * a Testcase for a tree as follows: mother(root) -- son (first child) -- a
	 * child of son (childOfSon) -- a child of childOfSon (grandchildOfSon) --
	 * daughter (second child) --- a child of daughter (childOfDaughter) So the
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
		baum.addChildToParent(grandchildOfson, childOfson);

		assertFalse(baum.isCousineOf(grandchildOfson, childOfdaughter));
	}

	@Test
	public void isGrandchildOfSuccessfulTest() {
		IPerson grandmom = new Person("Grandmom", Gender.FEMALE);
		IPerson mother = new Person("Mother", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE);
		IBaum baum = new Baum();
		baum.addRootPerson(grandmom);
		baum.addRootPerson(mother);
		baum.addRootPerson(son);
		baum.addChildToParent(mother, grandmom);
		baum.addChildToParent(son, mother);

		assertTrue(baum.isGrandchildOf(son, grandmom));
	}

	@Test
	public void isGrandchildOfFailedTest() {
		IPerson grandmom = new Person("Grandmom", Gender.FEMALE);
		IPerson mother = new Person("Mother", Gender.FEMALE);
		IPerson father = new Person("Father", Gender.MALE);
		IPerson son = new Person("Son", Gender.MALE);
		IBaum baum = new Baum();
		baum.addRootPerson(grandmom);
		baum.addRootPerson(mother);
		baum.addChildToParent(mother, grandmom);
		baum.addChildToParent(son, father);

		assertFalse(baum.isGrandchildOf(son, grandmom));
	}

	@Test
	public void isGrandparentOfSuccessfulTest() {
		IPerson grandmom = new Person("Grandmom", Gender.FEMALE);
		IPerson mother = new Person("Mother", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE);
		IBaum baum = new Baum();
		baum.addRootPerson(grandmom);
		baum.addRootPerson(mother);
		baum.addRootPerson(son);
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
		baum.addChildToParent(mother, grandmom);
		baum.addChildToParent(son, mother);

		assertFalse(baum.isGrandparentOf(grandmom, son));
	}

	/*
	 * Testcase: Father has got married to fathersWife. Each of them had a
	 * child. After their marriage the children are defined as siblings
	 */
	@Test
	public void isSiblingOfMarriedParentsSuccessfulTest() {
		IPerson father = new Person("Father", Gender.MALE);
		IPerson fathersWife = new Person("Mother-in-Law ", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE);
		IPerson daughter = new Person("Daughter", Gender.FEMALE);

		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addRootPerson(fathersWife);
		baum.addChildToParent(son, father);
		baum.addPartner(father, fathersWife);
		baum.addChildToParent(daughter, fathersWife);
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
		baum.addRootPerson(son);
		baum.addRootPerson(daughter);
		baum.addPartner(father, fathersWife);
		baum.addChildToParent(daughter, father2);
		baum.addChildToParent(son, father);

		assertFalse(baum.isSiblingOf(daughter, son));
	}

	@Test
	public void addParentToRootSuccessfulTest() {

		IPerson father = new Person("Best Father", Gender.MALE);
		IPerson fathersMother = new Person("Grandmommy", Gender.FEMALE);
		IPerson son = new Person("Son", Gender.MALE);
		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addRootPerson(son);
		baum.addChildToParent(son, father);
		baum.addRootPerson(fathersMother);
		baum.addChildToParent(father, fathersMother);

		assertTrue(baum.isGrandchildOf(son, fathersMother));
	}

	/*
	 * Testcase: son has gotten two Mothers: mother and mother2. Our program
	 * accept only one Mother.
	 */
	@Test
	public void specificRelationsCheckTest() {
		IPerson mother = new Person("Super Mother", Gender.FEMALE);
		IPerson son = new Person("Sonny", Gender.MALE);
		IPerson mother2 = new Person("Star Mother", Gender.FEMALE);
		IPerson daughter = new Person("Sunny", Gender.FEMALE);

		IBaum baum = new Baum();
		baum.addRootPerson(mother);
		baum.addChildToParent(son, mother);
		baum.addRootPerson(daughter);
		baum.addChildToParent(daughter, mother);

		assertTrue(baum.isSiblingOf(son, daughter));

		baum.addChildToParent(son, mother2);
		baum.addChildToParent(mother, mother2);

		assertTrue(baum.isSiblingOf(son, mother));
		assertFalse(baum.isSiblingOf(son, daughter));
	}

	@Test
	public void isUncleOrAuntFromMotherSideOfSuccessfulTest() {
		IPerson mother = new Person("Super Mother", Gender.FEMALE);
		IPerson son = new Person("Sunny", Gender.MALE);
		IPerson daughter = new Person("Sanny", Gender.FEMALE);
		IPerson daughter2 = new Person("the Daughter of Sanny", Gender.FEMALE);

		IBaum baum = new Baum();
		baum.addRootPerson(mother);
		baum.addChildToParent(son, mother);
		baum.addChildToParent(daughter, mother);
		baum.addChildToParent(daughter2, daughter);

		assertTrue(baum.isUncleOrAuntFromMotherSideOf(son, daughter2));
	}

	@Test
	public void isUncleOrAuntFromMotherSideOfFailedTest() {

		IPerson mother = new Person("Super Mother", Gender.FEMALE);
		IPerson father = new Person("Daddy", Gender.MALE);
		IPerson son = new Person("Sunny", Gender.MALE);
		IPerson daughter = new Person("Sanny", Gender.FEMALE);
		IPerson daughter2 = new Person("the Daughter of Sanny", Gender.FEMALE);

		IBaum baum = new Baum();
		baum.addRootPerson(mother);
		baum.addRootPerson(father);
		baum.addChildToParent(son, father);
		baum.addChildToParent(daughter, mother);
		baum.addChildToParent(daughter2, daughter);

		assertFalse(baum.isUncleOrAuntFromMotherSideOf(son, daughter2));
	}

	@Test
	public void isUncleOrAuntFromFatherSideOfSuccessfulTest() {
		IPerson father = new Person("Super Daddy", Gender.MALE);
		IPerson son = new Person("Sunny", Gender.MALE);
		IPerson daughter = new Person("Sanny", Gender.FEMALE);
		IPerson daughter2 = new Person("the Daughter of Sanny", Gender.FEMALE);

		IBaum baum = new Baum();
		baum.addRootPerson(father);
		baum.addChildToParent(son, father);
		baum.addChildToParent(daughter, father);
		baum.addChildToParent(daughter2, son);

		assertTrue(baum.isUncleOrAuntFromFatherSideOf(daughter, daughter2));
	}

	@Test
	public void isUncleOrAuntFromFatherSideOfFailedTest() {

		IPerson mother = new Person("Super Mother", Gender.FEMALE);
		IPerson father = new Person("Daddy", Gender.MALE);
		IPerson son = new Person("Sunny", Gender.MALE);
		IPerson daughter = new Person("Sanny", Gender.FEMALE);

		IBaum baum = new Baum();
		baum.addRootPerson(mother);
		baum.addRootPerson(father);
		baum.addChildToParent(son, father);
		baum.addChildToParent(daughter, mother);

		assertFalse(baum.isUncleOrAuntFromFatherSideOf(son, daughter));
	}
}
