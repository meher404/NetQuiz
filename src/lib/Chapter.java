package lib;

public class Chapter {
	
	private int chapID;
	private String chapter_name;
	
	//getters and setters
	public int getChapterID() {
		return chapID;
	}

	public void setChapterID(int chapID) {
		this.chapID = chapID;
	}

	public String getChapter_name() {
		return chapter_name;
	}

	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}
	
	//to String method
	public String toString(){
		return getChapter_name();
	}

}
