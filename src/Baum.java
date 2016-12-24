import java.util.HashMap;

public class Baum implements IBaum {

	HashMap<String, Node> nodes = new HashMap<String, Node>();

	/* Füge einen Knoten in den Baum */
	@Override
	public void addRootPerson(IPerson person) {
		if (isNotValidPerson(person)) {
			return;
		}
		Node node = new Node(person);
		nodes.put(person.getName(), node);

	}

	/*
	 * Füge einen Elternteil zum Wurzelknoten oder zu einer unverheirateten
	 * Person im Baum hinzu. Wenn das Kind noch nicht im Baum ist - wird einen neuen KindKnoten in den Baum hinzugefuegt.
	 * Wenn das Elternteil noch nicht im Baum ist - wird einen neuen ElternKnoten in den Baum hizugefuegt.
	 * Die Situation, wenn KindKnoten und Elternknoten im Baum nicht vorhanden sind, wird nicht akzeptiert. 
	 */
	@Override
	public void addChildToParent(IPerson child, IPerson parent) {
		if (isNotValidPerson(child) || isNotValidPerson(parent)) {
			return;
		}
		Node parentNode = findNode(parent.getName());
		Node childNode = findNode(child.getName());
		if (isNotValidNode(childNode) && isNotValidNode(parentNode) ) {
			return;
		}
		if (isNotValidNode(childNode)) {
			childNode = new Node(child);
			nodes.put(child.getName(), childNode);
		}
		if(isNotValidNode(parentNode)){
			parentNode = new Node(parent);
			nodes.put(parent.getName(), parentNode);
		}

		
		if (parentNode.gender == Gender.MALE) {
			childNode.fatherName = parent.getName();
		} else {
			childNode.motherName = parent.getName();
		}
	}

	@Override
	public boolean addPartner(IPerson husband, IPerson wife) {
		if (isNotValidPerson(husband) || isNotValidPerson(wife)) {
			return false;
		}
		Node husbandNode = findNode(husband.getName());
		Node wifeNode = findNode(wife.getName());

		if (isNotValidNode(husbandNode) || isNotValidNode(wifeNode)) {
			return false;
		}
		if (husbandNode != wifeNode) {
			husbandNode.partner = wife.getName();
			wifeNode.partner = husband.getName();
			return true;
		}
		return false;
	}

	@Override
	public boolean isSiblingOf(IPerson child1, IPerson child2) {
		if (isNotValidPerson(child1) || isNotValidPerson(child2)) {
			return false;
		}
		Node childNode1 = findNode(child1.getName());
		Node childNode2 = findNode(child2.getName());

		if (isNotValidNode(childNode1) || isNotValidNode(childNode2)) {
			return false;
		}
		Node child1Father = findNode(childNode1.fatherName);
		Node child2Father = findNode(childNode2.fatherName);
		if (isValidNode(child1Father) && isValidNode(child2Father)) {
			if (child1Father == child2Father) {
				return true;
			}
		}
		Node child1Mother = findNode(childNode1.motherName);
		Node child2Mother = findNode(childNode2.motherName);
		if (isValidNode(child1Mother) && isValidNode(child2Mother)) {
			if (child1Mother == child2Mother) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isCousineOf(IPerson person1, IPerson person2) {
		if (isNotValidPerson(person1) || isNotValidPerson(person2)) {
			return false;
		}
		Node cousin1Node = findNode(person1.getName());
		Node cousin2Node = findNode(person2.getName());

		if (isNotValidNode(cousin1Node) || isNotValidNode(cousin2Node)) {
			return false;
		}
		if (cousin1Node == cousin2Node) {
			return false;
		}
		return checkIfParentsAreSiblings(cousin1Node, cousin2Node);
	}

	@Override
	public boolean isUncleOrAuntFromMotherSideOf(IPerson adult, IPerson child) {
		if (isNotValidPerson(adult) || isNotValidPerson(child)) {
			return false;
		}
		Node childNode = findNode(child.getName());

		if (isNotValidNode(childNode) || isNotValidNode(findNode(childNode.motherName))) {
			return false;
		}
		Node childMotherNode = findNode(childNode.motherName);
		Node adultNode = findNode(adult.getName());

		if (isNotValidNode(adultNode)) {
			return false;
		}
		if (adultNode == childNode || adultNode == childMotherNode) {
			return false;
		}
		if (adultNode.motherName == childMotherNode.motherName || adultNode.fatherName == childMotherNode.fatherName) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isUncleOrAuntFromFatherSideOf(IPerson adult, IPerson child) {
		if (isNotValidPerson(adult) || isNotValidPerson(child)) {
			return false;
		}
		Node childNode = findNode(child.getName());
		if (isNotValidNode(childNode) || isNotValidNode(findNode(childNode.fatherName))) {
			return false;
		}
		Node childFatherNode = findNode(childNode.fatherName);
		Node adultNode = findNode(adult.getName());

		if (isNotValidNode(adultNode)) {
			return false;
		}
		if (adultNode == childNode || adultNode == childFatherNode) {
			return false;
		}
		if (adultNode.motherName == childFatherNode.motherName || adultNode.fatherName == childFatherNode.fatherName) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isGrandchildOf(IPerson grandchild, IPerson grandparent) {
		if (isNotValidPerson(grandchild) || isNotValidPerson(grandparent)) {
			return false;
		}
		Node grandchildNode = findNode(grandchild.getName());
		Node grandparentNode = findNode(grandparent.getName());

		if (isNotValidNode(grandchildNode) || isNotValidNode(grandparentNode)) {
			return false;
		}
		String grandparentName = grandparent.getName();
		return checkGrandchildOf(grandchildNode, grandparentName);
	}

	@Override
	public boolean isGrandparentOf(IPerson grandparent, IPerson grandchild) {
		if (isNotValidPerson(grandchild) || isNotValidPerson(grandparent)) {
			return false;
		}
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
		Node node1Mother = findNode(node1.motherName);
		Node node2Mother = findNode(node2.motherName);

		if (isValidNode(node1Mother) && isValidNode(node2Mother)) {

			if (isValidNode(findNode(node1Mother.motherName)) && isValidNode(findNode(node2Mother.motherName))
					&& node1Mother.motherName.equals(node2Mother.motherName)) {
				return true;
			}
			if (isValidNode(findNode(node1Mother.fatherName)) && isValidNode(findNode(node2Mother.fatherName))
					&& node1Mother.fatherName.equals(node2Mother.fatherName)) {
				return true;
			}
		}
		Node node1Father = findNode(node1.fatherName);
		if (isValidNode(node1Father) && isValidNode(node2Mother)) {
			if (isValidNode(findNode(node1Father.motherName)) && isValidNode(findNode(node2Mother.motherName))
					&& node1Father.motherName.equals(node2Mother.motherName)) {
				return true;
			}
			if (isValidNode(findNode(node1Father.fatherName)) && isValidNode(findNode(node2Mother.fatherName))
					&& node1Father.fatherName.equals(node2Mother.fatherName)) {
				return true;
			}
		}
		Node node2Father = findNode(node2.fatherName);
		if (isValidNode(node2Father) && isValidNode(node1Mother)) {
			if (isValidNode(findNode(node2Father.motherName)) && isValidNode(findNode(node1Mother.motherName))
					&& node2Father.motherName.equals(node1Mother.motherName)) {
				return true;
			}
			if (isValidNode(findNode(node2Father.fatherName)) && isValidNode(findNode(node1Mother.fatherName))
					&& node2Father.fatherName.equals(node1Mother.fatherName)) {
				return true;
			}
		}
		if (isValidNode(node1Father) && isValidNode(node2Father)) {

			if (isValidNode(findNode(node1Father.motherName)) && isValidNode(findNode(node2Father.motherName))
					&& node1Father.motherName.equals(node2Father.motherName)) {
				return true;
			}
			if (isValidNode(findNode(node1Father.fatherName)) && isValidNode(findNode(node2Father.fatherName))
					&& node1Father.fatherName.equals(node2Father.fatherName)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkGrandchildOf(Node child, String grandparent) {
		Node grandchildNode = child;
		String grandparentName = grandparent;

		if (isValidNode(grandchildNode)) {
			Node mother = findNode(grandchildNode.motherName);
			Node father = findNode(grandchildNode.fatherName);
			if (isValidNode(mother)) {
				if (isValidNode(findNode(mother.motherName)) && mother.motherName.equals(grandparentName)) {
					return true;
				}
				if (isValidNode(findNode(mother.fatherName)) && mother.fatherName.equals(grandparentName)) {
					return true;
				}
			}
			if (isValidNode(father)) {
				if (isValidNode(findNode(father.motherName)) && father.motherName.equals(grandparentName)) {
					return true;
				}
				if (isValidNode(findNode(father.fatherName)) && father.fatherName.equals(grandparentName)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isNotValidPerson(IPerson person) {
		if (person.getName() == null || person.getName() == "") {
			return true;
		}
		return false;
	}

	private boolean isNotValidNode(Node personNode) {
		if (personNode == null) {
			return true;
		}
		return false;
	}

	private boolean isValidNode(Node personNode) {
		if (personNode != null) {
			return true;
		}
		return false;
	}
	
}
