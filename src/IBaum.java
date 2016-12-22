
public interface IBaum {
	
	boolean addRootPerson(IPerson person);
	void addChildToParent(IPerson child, IPerson parent);
	void addChildToParents(IPerson child, IPerson mother, IPerson father);
	boolean addPartner(IPerson husbend, IPerson wife);
	
	
	boolean isSiblingOf(IPerson person1, IPerson person2);
	boolean isCousineOf(IPerson person1, IPerson person2);
	boolean isUncleOf(IPerson unlce, IPerson nephew);
	boolean isAuntOf(IPerson aunt, IPerson niece);
	boolean isUncleOrAuntFromMotherSideOf(IPerson adult, IPerson child);
	boolean isUncleOrAuntFromFatherSideOf(IPerson adult, IPerson child);
	boolean isGrandchildOf(IPerson grandchild, IPerson grandparent);
	boolean isGrandparentOf(IPerson grandparent, IPerson grandchild);
	


}
