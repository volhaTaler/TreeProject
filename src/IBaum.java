
public interface IBaum {
	
	void addRootPerson(IPerson person);
	void addChildToParent(IPerson child, IPerson parent);
	void addChildToParents(IPerson child, IPerson mother, IPerson father);
	void addParnter(IPerson husbend, IPerson wife);
	
	
	boolean isSiblingOf(IPerson person1, IPerson person2);
	boolean isCousineOf(IPerson person1, IPerson person2);
	boolean isUncleOf(IPerson unlce, IPerson nephew);
	boolean isAuntOf(IPerson aunt, IPerson niece);
	boolean isGrandchildOf(IPerson grandchild, IPerson grandparent);
	boolean isGrandparentOf(IPerson grandparent, IPerson grandchild);
	


}
