
public class Person implements IPerson{
	
	String name;
	Gender gender;
	
	public Person(String name, Gender gender){
		this.name = name;
		this.gender = gender;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Gender getGender() {	
		return gender;
	}
	
}
