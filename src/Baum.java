import java.util.HashMap;

public class Baum implements IBaum {

	HashMap<String, Node> nodes = new HashMap<String, Node>();

	/* Füge einen Knoten in den Baum */
	@Override
	public boolean addRootPerson(IPerson person) {
		Node node = new Node(person);
		if (person.getName() != "") {
			nodes.put(person.getName(), node);
			return true;
		}
		return false;
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
		if (child.getName() == "") {
			return;
		}
		nodes.put(child.getName(), childNode);
		if (parentNode.gender == Gender.MALE) {
			childNode.fatherName = parent.getName();
		} else {
			childNode.motherName = parent.getName();
		}
	}

	@Override
	public void addChildToParents(IPerson child, IPerson mother, IPerson father) {
		Node childNode = new Node(child);
		Node fatherNode = findNode(father.getName());
		Node motherNode = findNode(mother.getName());
		if (motherNode == null || fatherNode == null || child.getName() == "") {
			return;
		}
		if(father.getGender() == Gender.MALE && mother.getGender() == Gender.FEMALE){
		childNode.fatherName = father.getName();
		childNode.motherName = mother.getName();
		}
		else{
			/* if parents have the same gender or the parameters are in the wrong order.*/
			childNode.motherName = father.getName();
			childNode.fatherName = mother.getName();
		}
		nodes.put(child.getName(), childNode);
	}

	@Override
	public boolean addPartner(IPerson husband, IPerson wife) {
		Node husbandNode = findNode(husband.getName());
		Node wifeNode = findNode(wife.getName());

		if (husbandNode != null && wifeNode != null && husbandNode != wifeNode) {
			husbandNode.partner = wife.getName();
			wifeNode.partner = husband.getName();
			return true;
		}
		return false;
	}

	@Override
	public boolean isSiblingOf(IPerson child1, IPerson child2) {
		Node childNode1 = findNode(child1.getName());
		Node childNode2 = findNode(child2.getName());

		if (childNode1 == null || childNode2 == null) {
			return false;
		}
		
		if (!childNode1.fatherName.equals(null) && !childNode2.fatherName.equals(null)) {
			return childNode1.fatherName.equals(childNode2.fatherName);
		}
		if (!(childNode1.motherName.equals(null) || childNode2.motherName.equals(null))) {
			return childNode1.motherName.equals(childNode2.motherName);
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
		return isUncleOrAuntOf(uncleNode, nephewNode);
	}

	@Override
	public boolean isAuntOf(IPerson aunt, IPerson niece) {
		Node auntNode = findNode(aunt.getName());
		Node nieceNode = findNode(niece.getName());

		if (auntNode == null || nieceNode == null || auntNode.gender != Gender.FEMALE) {
			return false;
		}
		return isUncleOrAuntOf(auntNode, nieceNode);
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
				if (!mother.motherName.equals(null)) {
					return mother.motherName.equals(grandparentName);
				}
				if (!mother.fatherName.equals(null)) {
					return mother.fatherName.equals(grandparentName);
				}
			} else if (father != null) {
				if (father.motherName != null) {
					return (father.motherName.equals(grandparentName));
				}
				if (father.fatherName != null) {
					return (father.fatherName.equals(grandparentName));
				}
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
		if (nodes.containsKey(name)) {
			return nodes.get(name);
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
			if (node1Mother.motherName.equals(node2Mother.motherName)) {
				return true;
			}
		}

		Node node1Father = findNode(node1.fatherName);
		if (node1Father != null && node2Mother != null) {
			if (node1Father.fatherName.equals(node2Mother.fatherName)) {
				return true;
			}
		}

		Node node2Father = findNode(node2.fatherName);
		if (node2Father != null && node1Mother != null) {
			if (node2Father.fatherName.equals(node1Mother.fatherName)) {
				return true;
			}
		}

		if (node1Father != null && node2Father != null) {
			if (node1Father.fatherName.equals(node2Father.fatherName)) {
				return true;
			}
		}

		return false;
	}

	private boolean isUncleOrAuntOf(Node person1, Node person2) {

		Node siblingOfParentNode = person1;
		Node childNode = person2;
		Node childNodeMother = findNode(childNode.motherName);

		if (childNodeMother != null && (siblingOfParentNode.motherName == childNodeMother.motherName
				|| childNodeMother.fatherName == siblingOfParentNode.fatherName)) {
			return true;
		}
		Node childNodeFather = findNode(childNode.fatherName);

		if (childNodeFather != null && (siblingOfParentNode.motherName == childNodeFather.motherName
				|| siblingOfParentNode.fatherName == childNodeFather.fatherName)) {
			return true;
		}

		return false;
	}
}
