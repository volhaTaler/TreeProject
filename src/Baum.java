
public class Baum implements IBaum {

	@Override
	public void addRootPerson(IPerson person) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addChildToParent(IPerson child, IPerson parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addChildToParents(IPerson child, IPerson mother, IPerson father) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addParnter(IPerson husbend, IPerson wife) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSiblingOf(IPerson person1, IPerson person2) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCousineOf(IPerson person1, IPerson person2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUncleOf(IPerson unlce, IPerson nephew) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAuntOf(IPerson aunt, IPerson niece) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGrandchildOf(IPerson grandchild, IPerson grandparent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGrandparentOf(IPerson grandparent, IPerson grandchild) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
