package app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import util.Database;
import util.QuizHandler;
import lib.Chapter;
import lib.Question;
import lib.Subject;
import lib.User;

public class TeacherUIHandler extends Thread {
	
	Socket socket = null;
	User user = null;
	
	public TeacherUIHandler(Socket socket, User user){
		this.socket = socket;
		this.user = user;
	}

	private void sendText(String msg){
		try {
			DataOutputStream out=new DataOutputStream(socket.getOutputStream());
			out.writeUTF(msg);
		} catch (Exception e) {
			System.out.println("error in sending data.");
		}
	}
	
	private String recieveText(){
		//BufferedReader in;
		String msg_from_client="";
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());	 //to read from the client
			msg_from_client=in.readUTF();
		} catch (Exception e) {
			System.out.println("Error in recieveing text");
			e.printStackTrace();
		}
		return msg_from_client;
	}
	
	public void run() {
		try{
			int k=0;
			
			while(k!=-1){
				String menu="";
				menu+="\t\tTeacher Home Page";
				menu+="\nWelcome, "+user.getName()+"\n";
				menu+="\tMenu"+"\n";
				menu+="1. Manage All Subjects/Chapters/Questions"+"\n";
				menu+="2. Add Question to Question Bank"+"\n";
				menu+="3. Manage a Quiz"+"\n";
				menu+="4. Sign Out"+"\n";
				
				//send menu to client
				sendText(menu);
				System.out.println("sent main menu.");
				
				String msg_from_client=recieveText();
				
				k = Integer.parseInt(msg_from_client);
				if(k==4){
					sendText("EXIT\n");
					k=-1;
					continue;
				}
				switch(k){
				case 1: manageSubjects(); break;
				case 2: addQuestion(); break;
				case 3: createQuiz(); break;
				default: continue;
				}
			}
		
		}
		catch(Exception e){
			System.out.println("An unexpected error occured. Sorry. Try again.");
		}
	}

	private void manageSubjects() {
		ArrayList<Subject> subs = Database.getAllSubjects();
		String subjects_menu="";
		subjects_menu+="List of all subjects: \n";
		
		subjects_menu+="Type the subject Id to list all the chapters in that Subject."+"\n";
		for(Subject s : subs){
			subjects_menu+= s.getSubjectID()+" "+s.getSubject_name()+"\n";
		}
		
		subjects_menu+="\n101. Add another Subject"+"\n";
		subjects_menu+="102. Go back to main menu"+"\n";
		sendText(subjects_menu);
		try {
			int k=Integer.parseInt(recieveText());
			switch(k){
			case 102: return;
			case 101: {
						String add_sub_menu="";
						add_sub_menu+="Enter the name of the subject: "+"\n";
						sendText(add_sub_menu);
						String name = recieveText();
						
						if(Database.addSubject(name)!=null)
							sendText("#Subject Created Successfully, come back again to add chapters in it.\n");
						else
							sendText("#Sorry, could not add subject, please try again.\n");
					  }break;
			default: getChapters(k);
			}
			
		} catch (Exception e) {
			sendText("#Invalid option. Going back to main menu."+"\n");
		} 
		
	}
	
	private void getChapters(int k) {
		QuizHandler qh = new QuizHandler();
		ArrayList<Subject> subs = Database.getAllSubjects();
		try{
			Subject sub = null;
			for(Subject s : subs){
				if(s.getSubjectID()==k){
					sub = s;
					break;
				}
			}
			ArrayList<Chapter> chaps = Database.getChapters(sub);
			String cha = "";
			cha+="Enter the chapter code to view questions: "+"\n";
			
			for(Chapter c : chaps){
				cha+=c.getChapterID()+" "+c.getChapter_name()+"\n";
			}
			cha+="101. Create New Chapter"+"\n";
			cha+="102. Return to menu"+"\n";
			
			sendText(cha);
			int resp = Integer.parseInt(recieveText());
			
			switch(resp){
			case 102 : return;
			case 101 : {
						String add_chap_menu="";
						add_chap_menu+="Enter the name of the chapter: "+"\n";
						sendText(add_chap_menu);
						String name = recieveText();
						
						if(Database.addChapter(name)!=null)
						{
							Question question = new Question();
							question.setChapter(name);
							question.setSubject(sub.getSubject_name());
							add_chap_menu = "Enter a question to this chapter."+"\n";
							add_chap_menu += "Enter question text: "+"\n";
							sendText(add_chap_menu);
							question.setQuestion(recieveText());
							add_chap_menu = "Enter option 1: "+"\n";
							sendText(add_chap_menu);
							question.setOpt1(recieveText());
							
							add_chap_menu = "Enter option 2: "+"\n";
							sendText(add_chap_menu);
							question.setOpt2(recieveText());
							
							add_chap_menu = "Enter option 3: "+"\n";
							sendText(add_chap_menu);
							question.setOpt3(recieveText());
							
							add_chap_menu = "Enter option 4: "+"\n";
							sendText(add_chap_menu);
							question.setOpt4(recieveText());
							
							add_chap_menu = "Enter correct option: "+"\n";
							sendText(add_chap_menu);
							question.setCorrect_opt(recieveText());
							
							if(!qh.addQuestion(question)){
								sendText("#Sorry, could not add question. Please add chapter and question again.");
								Database.deleteChapter(name);
							}				
						}
						else
							sendText("#Sorry, could not add chapter, please try again.\n");
					   }
			default : {
						ArrayList<Chapter> chapters = Database.getAllChapters();
						Chapter chapter = null;
						for(Chapter c : chapters){
							if(c.getChapterID()==resp){
								chapter = c;
								break;
							}
						}
						if(chapter==null)
							break;
						ArrayList<Question> ques = qh.getQuestions(chapter);
						String chap_menu="#";
						for(Question q : ques){
							chap_menu += q.getQid()+". "+q.getQuestion()+"\n1. "+q.getOpt1()+"\n2. "+q.getOpt2()+"\n3. "+q.getOpt3()+"\n4. "+q.getOpt4()+"\nCorrect answer: "+q.getCorrect_opt()+"\n";
						}
						sendText(chap_menu);
						
					  }
			}
		}
		catch(Exception e){
			System.out.println("Error in getChapters");
		}
	}

	private void addQuestion() {
		String add_chap_menu="";
		try{
			Question question = new Question();
			add_chap_menu= "Enter a valid Subject Name: "+"\n";
			sendText(add_chap_menu);
			question.setSubject(recieveText());
			
			add_chap_menu= "Enter a valid Chapter Name in this subject: "+"\n";
			sendText(add_chap_menu);
			question.setChapter(recieveText());
		
			add_chap_menu = "Enter a question to this chapter."+"\n";
			add_chap_menu += "Enter question text: "+"\n";
			sendText(add_chap_menu);
			question.setQuestion(recieveText());
			add_chap_menu = "Enter option 1: "+"\n";
			sendText(add_chap_menu);
			question.setOpt1(recieveText());
			
			add_chap_menu = "Enter option 2: "+"\n";
			sendText(add_chap_menu);
			question.setOpt2(recieveText());
			
			add_chap_menu = "Enter option 3: "+"\n";
			sendText(add_chap_menu);
			question.setOpt3(recieveText());
			
			add_chap_menu = "Enter option 4: "+"\n";
			sendText(add_chap_menu);
			question.setOpt4(recieveText());
			
			add_chap_menu = "Enter correct option: "+"\n";
			sendText(add_chap_menu);
			question.setCorrect_opt(recieveText());
			
			QuizHandler qh = new QuizHandler();
			if(qh.addQuestion(question)){
				sendText("#Question Added Successfully\n");
			}
		}
		catch(Exception e){
			sendText("#Invalid subject/chapter. please re try.\n");
		}
	}

	private void createQuiz() {
		ArrayList<Subject> all_subjects = Database.getAllSubjects();
		ArrayList<Chapter> all_chapters = Database.getAllChapters();
		QuizHandler qh = new QuizHandler();
		String quiz_menu = "";
		quiz_menu += "1. Generate a quiz from all subjects\n";
		quiz_menu += "2. Generate a quiz from one subject(cutomize chapters)\n";
		
		sendText(quiz_menu);
		int resp = Integer.parseInt(recieveText());
		ArrayList<Question> quiz = null;
		switch(resp){
		case 1 : {
					quiz_menu = "Enter the number of questions in the quiz(by default ): \n";
					sendText(quiz_menu);
					int num = Integer.parseInt(recieveText());
					if(num<10)
						quiz = qh.generateQuiz(10);
					else
						quiz = qh.generateQuiz(num);
					
					
					for(Question q : quiz){
						quiz_menu = "";
						quiz_menu += q.getQid()+". "+q.getQuestion()+"\n1. "+q.getOpt1()+"\n2. "+q.getOpt2()+"\n3. "+q.getOpt3()+"\n4. "+q.getOpt4()+"\nCorrect answer: "+q.getCorrect_opt()+"\n";
						quiz_menu += "Set a weight for this question. By default, it carries 1 mark.\n";
						quiz_menu += "Weight range from (-1 to 0) or 1 describes that if the question goes wrong this weight will be penalized.\n";
						sendText(quiz_menu);
						int wt = Integer.parseInt(recieveText());
						q.setWeight(wt);
					}
					quiz_menu = "#Saving the quiz for students to use later\n";
					sendText(quiz_menu);
					sendText("#Quiz ID: "+qh.saveQuiz(quiz));
				 } break;
		case 2: {
					quiz_menu = "";
					quiz_menu += "Select a Subject to generate a quiz from: \n";
					for(Subject s : all_subjects){
						quiz_menu += s.getSubjectID()+". "+s.getSubject_name()+"\n";
					}
					sendText(quiz_menu);
					int sel = Integer.parseInt(recieveText());
					Subject sel_subject = null;
					for(Subject s : all_subjects){
						if(s.getSubjectID()==sel){
							sel_subject = s;
							break;
						}
					}
					if(sel_subject==null) return;
					all_chapters = Database.getChapters(sel_subject);
					quiz_menu = "";
					for(Chapter c : all_chapters){
						quiz_menu += c.getChapterID()+". "+c.getChapter_name()+"\n";
					}
					quiz_menu += "Type all chapter codes with spaces to include those chapters in quiz.\n";
					sendText(quiz_menu);
					String sel_chap = recieveText();
					all_chapters = new ArrayList<Chapter>();
					for(String c : sel_chap.split(" ")){
						int cin = Integer.parseInt(c);
						for(Chapter chap : Database.getChapters(sel_subject)){
							if(chap.getChapterID()==cin){
								all_chapters.add(chap);
								break;
							}
						}
					}
					ArrayList<Question> quiz_questions = qh.generateQuiz(all_chapters, 10, false);
					for(Question q : quiz_questions){
						quiz_menu = "";
						quiz_menu += q.getQid()+". "+q.getQuestion()+"\n1. "+q.getOpt1()+"\n2. "+q.getOpt2()+"\n3. "+q.getOpt3()+"\n4. "+q.getOpt4()+"\nCorrect answer: "+q.getCorrect_opt()+"\n";
						quiz_menu += "Set a weight for this question. By default, it carries 1 mark.\n";
						quiz_menu += "Weight range from (-1 to 0) or 1 describes that if the question goes wrong this weight will be penalized.\n";
						sendText(quiz_menu);
						int wt = Integer.parseInt(recieveText());
						q.setWeight(wt);
					}
					quiz_menu = "#Saving the quiz for students to use later\n";
					sendText(quiz_menu);
					sendText("#Quiz ID: "+qh.saveQuiz(quiz));
				}break;
			
		}
	}


}
