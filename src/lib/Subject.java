package lib;

public class Subject {
	
	private int subjectID;
	private String subject_name;
	
	//getters and setters
	public int getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(int subjectID) {
		this.subjectID = subjectID;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	
	//to String method
	public String toString(){
		return getSubject_name();
	}

}
