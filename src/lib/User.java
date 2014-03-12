package lib;

public class User {
	private String userid;
	private String password;
	private String name;
	private String institute;
	private String city;
	private String phone;
	private String email;
	private boolean is_student=false;
	
	//getters and setters
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setPassword(String pwd){
		password=pwd;
	}
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean is_student() {
		return is_student;
	}
	public void setIs_student(boolean is_student) {
		this.is_student = is_student;
	}
	
	//to string for an user
	public String toString(){
		//(for testing)return getName()+" account type: "+is_student;
		return getName();
	}
	
}
