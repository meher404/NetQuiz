package app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import util.Database;
import util.QuizHandler;
import lib.Chapter;
import lib.Question;
import lib.Quiz;
import lib.Subject;
import lib.User;

public class StudentUIHandler extends Thread {
	
	Socket socket=null;
	User user=null;
	
	public StudentUIHandler(Socket socket, User user){
		this.socket=socket;
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
	
	public void run(){
		try{
			String menu = "";
			int k=0;
			while(k!=-1){
				menu +="\t\tStudent Home Page";
				menu +="\nWelcome, "+user.getName()+"\n";
				menu +="\tMenu"+"\n";
				menu += "1. Browse subjects and chapters\n";
				menu += "2. Take Quizes\n";
				menu += "3. Sign Out\n";
				sendText(menu);
				k = Integer.parseInt(recieveText());
				if(k==3){
					k=-1;
					sendText("EXIT");
					continue;
				}
				switch(k){
				case 1 : browseSubjects(); break;
				case 2 : takeQuiz(); break;
				default : continue;
				}
			}
		}
		catch(Exception e){
			System.out.println("An unexpected error occured. Sorry. Try again.");
		}
	}

	private void browseSubjects() {
		ArrayList<Subject> subjects = Database.getAllSubjects();
		String subs_menu = "#Subjects and its chapters available for questions are: \n";
		for(Subject s : subjects){
			subs_menu += s.getSubjectID()+". "+s.getSubject_name()+"\n";
			ArrayList<Chapter> chapters = Database.getChapters(s);
			for(Chapter c : chapters){
				subs_menu += "\t"+c.getChapterID()+". "+c.getChapter_name()+"\n";
			}
		}
		sendText(subs_menu);
	}
	
	private void takeQuiz() {
		try{
			String take_menu = "List of all quizes available: \n";
			take_menu += "Select a particular quiz id to attempt quiz.\n";
			QuizHandler qh = new QuizHandler();
			for(Integer i : qh.getQuizes()){
				take_menu += i+"\n";
			}
			sendText(take_menu);
			int sel_quiz = Integer.parseInt(recieveText());
			Quiz quiz = qh.getQuiz(sel_quiz);
			sendText("Do you want to start the Quiz?\n");
			String resp = recieveText();
			if(resp.equalsIgnoreCase("yes")){
				//start quiz.
				String q_str = "#Duration of quiz is 10 minutes and your time starts now.\n";
				sendText(q_str);
				Long start_time = System.currentTimeMillis();
				int i=1;
				int completed = 0;
				for(Question q : quiz.getQuestions()){
					q_str = (i++)+". "+q.getQuestion()+"\na. "+q.getOpt1()+"\nb. "+q.getOpt2()+"\nc. "+q.getOpt3()+"\nd. "+q.getOpt4()+"\n";
					q_str += "Your answer: ";
					sendText(q_str);
					
					String ans = recieveText();
					if(ans.equalsIgnoreCase("a")){
						ans = q.getOpt1();
					}else if(ans.equalsIgnoreCase("b")){
						ans = q.getOpt2();
					}else if(ans.equalsIgnoreCase("c")){
						ans = q.getOpt3();
					}else if(ans.equalsIgnoreCase("d")){
						ans = q.getOpt4();
					}
					
					Long rec_time = System.currentTimeMillis();
					if((rec_time-start_time)>10*60*1000){
						sendText("#Timed out");
						return;
					}
					q.setAnswer(ans);
					completed++;
				}
				Long stop_time = System.currentTimeMillis();
				sendText("#Time taken to complete quiz: "+(stop_time-start_time)/(60*1000));
				sendText("#Calculating the score..");
				quiz.setUser(user);
				int scr = 0;
				if(completed==quiz.getQuestions().size()){
					scr = qh.evaluateQuiz(quiz);
					sendText("#Your score: "+scr);
				}
				
			}
			else
				return;
		}
		catch(Exception e){
			sendText("#There is seems to be a problem with the server. please re try. Sorry.");
		}
	}


}
