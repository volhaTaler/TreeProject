import java.util.ArrayList;
import java.lang.*;
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
		if (parentNode == null) {
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

		if (childNode1 != null && childNode2 != null) {
			return (childNode1.fatherName != null && childNode2.fatherName != null)
					|| (childNode1.motherName != null && childNode2.motherName != null)
					|| (childNode1.fatherName == childNode2.fatherName
							|| childNode1.motherName == childNode2.motherName);
		}
		return false;
	}

	@Override
	public boolean isCousineOf(IPerson person1, IPerson person2) {
		Node cousin1Node = findNode(person1.getName());
		Node cousin2Node = findNode(person1.getName());

		return checkIfParentsAreSiblings(cousin1Node, cousin2Node);
	}

	@Override
	public boolean isUncleOf(IPerson uncle, IPerson nephew) {
		Node uncleNode = findNode(uncle.getName());
		Node nephewNode = findNode(nephew.getName());

		if (uncleNode == null || nephewNode == null || uncleNode.gender != Gender.MALE) {
			return false;
		}
		Node nephewMother = findNode(nephewNode.motherName);

		if (nephewMother != null && (uncleNode.motherName == nephewMother.motherName
				|| uncleNode.fatherName == nephewMother.fatherName)) {
			return true;
		}
		Node nephewFather = findNode(nephewNode.fatherName);

		if (nephewFather != null && (uncleNode.motherName == nephewFather.motherName
				|| uncleNode.fatherName == nephewFather.fatherName)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isAuntOf(IPerson aunt, IPerson niece) {

		Node auntNode = findNode(aunt.getName());
		Node nieceNode = findNode(niece.getName());

		if (auntNode == null || nieceNode == null || auntNode.gender != Gender.FEMALE) {
			return false;
		}
		Node nieceMother = findNode(nieceNode.motherName);

		if (nieceMother != null
				&& (auntNode.motherName == nieceMother.motherName || auntNode.fatherName == nieceMother.fatherName)) {
			return true;
		}
		Node nieceFather = findNode(nieceNode.fatherName);

		if (nieceFather != null
				&& (auntNode.motherName == nieceFather.motherName || auntNode.fatherName == nieceFather.fatherName)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isGrandchildOf(IPerson grandchild, IPerson grandparent) {

		return false;
	}

	@Override
	public boolean isGrandparentOf(IPerson grandparent, IPerson grandchild) {
		// TODO Auto-generated method stub
		return false;
	}

	private Node findNode(String name) {
		for (Node node : nodes) {
			if (node.name == name) {
				return node;
			}
		}

		return null;
	}
	// The parameters are the nodes of cousins, that should be checked.

	private boolean checkIfParentsAreSiblings(Node node1, Node node2) {

		if (node1 == null || node2 == null) {
			return false;
		}

		Node node1Mother = findNode(node1.motherName);
		Node node2Mother = findNode(node2.motherName);

		if (node1Mother != null && node2Mother != null) {
			if (!node1Mother.motherName.equals(null) && !node2Mother.motherName.equals(null)
					&& node1Mother.motherName == node2Mother.motherName) {
				return true;
			}
		}

		Node node1Father = findNode(node1.fatherName);
		if (node1Father != null && node2Mother != null) {
			if (!node1Father.fatherName.equals(null) && !node2Mother.motherName.equals(null)
					&& node1Father.fatherName == node2Mother.fatherName) {
				return true;
			}
		}

		Node node2Father = findNode(node2.fatherName);
		if (node2Father != null && node1Mother != null) {
			if (!node2Father.fatherName.equals(null) && !node1Mother.motherName.equals(null)
					&& node2Father.fatherName == node1Mother.fatherName) {
				return true;
			}
		}

		if (node1Father != null && node2Father != null) {
			if (!node1Father.fatherName.equals(null) && !node2Father.fatherName.equals(null)
					&& node1Father.fatherName == node2Father.fatherName) {
				return true;
			}
		}

		return false;
	}

}
