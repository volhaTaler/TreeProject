import java.util.ArrayList;
import java.util.List;

public class Baum implements IBaum {

	List<Node> nodes = new ArrayList<Node>();

	/* Füge einen Knoten in den Baum */
	@Override
	public void addRootPerson(IPerson person) {
		Node node = new Node(person);
		nodes.add(node);

	}

	/*
	 * Füge einen Elternteil zum Wurzelknoten oder zu einer unverheirateten
	 * Person im Baum hinzu.
	 */
	@Override
	public void addChildToParent(IPerson child, IPerson parent) {
		Node parentNode = findNode(parent.getName());
		if (parentNode == null) {
			return;
		}

		Node childNode = findNode(child.getName());
		if (childNode == null) {
			childNode = new Node(child);
		}
		if (parentNode.gender == Gender.MALE) {
			childNode.fatherName = parent.getName();
		} else {
			childNode.motherName = parent.getName();
		}
		nodes.add(childNode);
	}

	@Override
	public void addChildToParents(IPerson child, IPerson mother, IPerson father) {
		Node childNode = new Node(child);
		Node fatherNode = findNode(father.getName());
		Node motherNode = findNode(mother.getName());
		if (motherNode == null || fatherNode == null) {
			return;
		}
		childNode.fatherName = father.getName();
		childNode.motherName = mother.getName();
		nodes.add(childNode);

	}

	@Override
	public void addPartner(IPerson husband, IPerson wife) {
		Node husbandNode = findNode(husband.getName());
		Node wifeNode = findNode(wife.getName());
		/*
		 * if(wifeNode == null){ wifeNode = new Node(wife); nodes.add(wifeNode);
		 * }
		 */
		if (husbandNode != null && wifeNode != null && husbandNode != wifeNode) {

			if (husbandNode.partner == "" && wifeNode.partner == "") {

				husbandNode.partner = wife.getName();
				wifeNode.partner = husband.getName();
			}
		}
	}

	@Override
	public boolean isSiblingOf(IPerson child1, IPerson child2) {

		Node childNode1 = findNode(child1.getName());
		Node childNode2 = findNode(child2.getName());

		if (childNode1 != null && childNode2 != null) {
			return (childNode1.fatherName != "" && childNode2.fatherName != "")
					|| (childNode1.motherName != "" && childNode2.motherName != "")
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
		Node grandchildNode = findNode(grandchild.getName());
		Node grandparentNode = findNode(grandparent.getName());

		if (grandparentNode == null) {
			return false;
		}
		String grandparentName = grandparent.getName();
		if (grandchildNode != null) {
			Node mother = findNode(grandchildNode.motherName);
			Node father = findNode(grandchildNode.fatherName);
			if (mother != null) {
				return (mother.motherName == grandparentName || mother.motherName == grandparentName);
			} else if (father != null) {
				return (father.motherName == grandparentName || father.fatherName == grandparentName);
			}
		}
		return false;
	}

	@Override
	public boolean isGrandparentOf(IPerson grandparent, IPerson grandchild) {

		IPerson person1 = grandchild;
		IPerson person2 = grandparent;

		return isGrandchildOf(person1, person2);

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
