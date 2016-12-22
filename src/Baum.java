import java.util.HashMap;

public class Baum implements IBaum {

	HashMap<String, Node> nodes = new HashMap<String, Node>();

	/* Füge einen Knoten in den Baum */
	@Override
	public boolean addRootPerson(IPerson person) {
		Node node = new Node(person);
		if (person.getName() != null) {
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
		if (child.getName() == null) {
			return;
		}
		nodes.put(child.getName(), childNode);
		if (parentNode.gender == Gender.MALE) {
			childNode.fatherName = parent.getName();
		} 
		else {
			childNode.motherName = parent.getName();
		}
	}

	@Override
	public void addChildToParents(IPerson child, IPerson mother, IPerson father) {
		Node childNode = new Node(child);
		Node fatherNode = findNode(father.getName());
		Node motherNode = findNode(mother.getName());
		if (motherNode == null || fatherNode == null || child.getName() == null) {
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
		int count = 0;

		if (childNode1 == null || childNode2 == null) {
			return false;
		}
		
		if (findNode(childNode1.fatherName) != null && findNode(childNode2.fatherName) != null) {
			if(childNode1.fatherName.equals(childNode2.fatherName)){
				count++;
			}
		}
		if (findNode(childNode1.motherName) != null && findNode(childNode2.motherName) != null) {
			if(childNode1.motherName.equals(childNode2.motherName)){
				count++;
			}
		}
		return count>0 ? true: false;
	}

	@Override
	public boolean isCousineOf(IPerson person1, IPerson person2) {
		Node cousin1Node = findNode(person1.getName());
		Node cousin2Node = findNode(person2.getName());
		
		if (cousin1Node == null ||cousin2Node == null || cousin1Node == cousin2Node) {
			return false;
		}
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
	public boolean isUncleOrAuntFromMotherSideOf(IPerson adult, IPerson child) {
		Node adultNode = findNode(adult.getName());
		Node childNode = findNode(child.getName());
		
		if (adultNode == null || childNode == null || findNode(childNode.motherName) == null || adultNode == childNode) {
			return false;
		}
		return isUncleOrAuntOf(adultNode, childNode);
	}
	
	@Override
	public boolean isUncleOrAuntFromFatherSideOf(IPerson adult, IPerson child) {
		Node adultNode = findNode(adult.getName());
		Node childNode = findNode(child.getName());
		
		if (adultNode == null || childNode == null || findNode(childNode.fatherName) == null ||
				adultNode == childNode || adult.getName() == childNode.fatherName) {
			return false;
		}
		return isUncleOrAuntOf(adultNode, childNode);
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

		if (grandparentNode == null || grandchildNode == null) {
			return false;
		}
		String grandparentName = grandparent.getName();
		return checkGrandchildOf(grandchildNode, grandparentName);
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
		Node node1Mother = findNode(node1.motherName);
		Node node2Mother = findNode(node2.motherName);

		if (node1Mother != null && node2Mother != null) {

			if (findNode(node1Mother.motherName) != null && findNode(node2Mother.motherName) != null
					&& node1Mother.motherName.equals(node2Mother.motherName)) {
				return true;
			}
			if (findNode(node1Mother.fatherName) != null && findNode(node2Mother.fatherName) != null
					&& node1Mother.fatherName.equals(node2Mother.fatherName)) {
				return true;
			}
		}
		Node node1Father = findNode(node1.fatherName);
		if (node1Father != null && node2Mother != null) {
			if (findNode(node1Father.motherName) != null && findNode(node2Mother.motherName) != null
					&& node1Father.motherName.equals(node2Mother.motherName)) {
				return true;
			}
			if (findNode(node1Father.fatherName) != null && findNode(node2Mother.fatherName) != null
					&& node1Father.fatherName.equals(node2Mother.fatherName)) {
				return true;
			}
		}
		Node node2Father = findNode(node2.fatherName);
		if (node2Father != null && node1Mother != null) {
			if (findNode(node2Father.motherName) != null && findNode(node1Mother.motherName) != null
					&& node2Father.motherName.equals(node1Mother.motherName)) {
				return true;
			}
			if (findNode(node2Father.fatherName) != null && findNode(node1Mother.fatherName) != null
					&& node2Father.fatherName.equals(node1Mother.fatherName)) {
				return true;
			}
		}
		if (node1Father != null && node2Father != null) {

			if (findNode(node1Father.motherName) != null && findNode(node2Father.motherName) != null
					&& node1Father.motherName.equals(node2Father.motherName)) {
				return true;
			}
			if (findNode(node1Father.fatherName) != null && findNode(node2Father.fatherName) != null
					&& node1Father.fatherName.equals(node2Father.fatherName)) {
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
				|| siblingOfParentNode.fatherName == childNodeMother.fatherName)) {
			return true;
		}

		Node childNodeFather = findNode(childNode.fatherName);
		if (childNodeFather != null && (siblingOfParentNode.motherName == childNodeFather.motherName
				|| siblingOfParentNode.fatherName == childNodeFather.fatherName)) {
			return true;
		}
		return false;
	}
	
	private boolean checkGrandchildOf(Node child, String grandparent) {
		Node grandchildNode = child;
		String grandparentName = grandparent;
		
		if (grandchildNode != null) {
			Node mother = findNode(grandchildNode.motherName);
			Node father = findNode(grandchildNode.fatherName);
			if (mother != null) {
				if (findNode(mother.motherName) != null && mother.motherName.equals(grandparentName)) {
					return true;
				}
				if (findNode(mother.fatherName) != null && mother.fatherName.equals(grandparentName)) {
					return true;
				}
			}
			 if (father != null) {
				if (findNode(father.motherName) != null && father.motherName.equals(grandparentName)) {
					return true;
				}
				if (findNode(father.fatherName) != null && father.fatherName.equals(grandparentName)) {
					return true;
				}
			}
		}
		return false;
	}
}
