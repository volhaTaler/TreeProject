import java.util.ArrayList;
import java.util.List;

public class Baum implements IBaum {
	
	List<Node> nodes = new ArrayList<Node>();

	@Override
	public void addRootPerson(IPerson person) {
		Node node = new Node(person);
		nodes.add(node);
		
	}

	@Override
	public void addChildToParent(IPerson child, IPerson parent) {
		Node parentNode = findNode(parent.getName());
		if(parentNode == null){
			return;
		}
		Node childNode = new Node(child);
		childNode.fatherName = parent.getName();
		nodes.add(childNode);
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
	public boolean isSiblingOf(IPerson child1, IPerson child2) {
		Node childNode1 = findNode(child1.getName());
		Node childNode2 = findNode(child2.getName());
		
		
		return childNode1 !=null && childNode2 !=null && 
				(childNode1.fatherName == childNode2.fatherName || 
				childNode1.motherName == childNode2.motherName);
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
	
	private Node findNode(String name){
		for(Node node : nodes){
			if(node.name == name){
				return node;
			}
		}
		
		return null;
	}
	

}
