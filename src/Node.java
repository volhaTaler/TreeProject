
public class Node {
	String name;
	Gender gender;
	String fatherName;
	String motherName;
	String partner;
	
	public Node(IPerson person){
		
		this.name = person.getName();
		this.gender = person.getGender();
	}
	
	

}
