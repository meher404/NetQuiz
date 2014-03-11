package lib;

public class Question {
	private int qid;
	private String subject;
	private String chapter;
	private String question;
	private String opt1;
	private String opt2;
	private String opt3;
	private String opt4;
	private String correct_opt;
	private int weight;
	
	//getters and setters
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOpt1() {
		return opt1;
	}
	public void setOpt1(String opt1) {
		this.opt1 = opt1;
	}
	public String getOpt2() {
		return opt2;
	}
	public void setOpt2(String opt2) {
		this.opt2 = opt2;
	}
	public String getOpt3() {
		return opt3;
	}
	public void setOpt3(String opt3) {
		this.opt3 = opt3;
	}
	public String getOpt4() {
		return opt4;
	}
	public void setOpt4(String opt4) {
		this.opt4 = opt4;
	}
	public String getCorrect_opt() {
		return correct_opt;
	}
	public void setCorrect_opt(String correct_opt) {
		this.correct_opt = correct_opt;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	//to String method
	public String toString(){
		return getQuestion()+"\t\t"+"weight: "+getWeight()+
				"\n"+"1. "+getOpt1()+
				"\n"+"2. "+getOpt2()+
				"\n"+"3. "+getOpt3()+
				"\n"+"4. "+getOpt4();
	}
}
