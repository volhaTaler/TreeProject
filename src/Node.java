
public class Node {
	String name;
	Gender gender;
	String fatherName;
	String motherName;
	String spouse;
	
	public Node(IPerson person){
		
		this.name = person.getName();
		this.gender = person.getGender();
	}
	
	

}
