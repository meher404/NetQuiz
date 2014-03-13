package test;
import java.util.ArrayList;

import app.TeacherUIHandler;
import lib.Chapter;
import lib.Question;
import lib.Subject;
import lib.User;
import util.Database;
import util.QuizHandler;


public class Test {

	public static void main(String[] args) {
		
		//Authenticate method tests
//		System.out.println("Database.authenticate(adi1, \"\"): "+Database.authenticate("adi1", ""));
//		System.out.println("Database.authenticate(\"\", \"\"): "+Database.authenticate("", ""));
//		System.out.println("Database.authenticate(adi1, msit123): "+Database.authenticate("adi1", "msit123"));
//		System.out.println("Database.authenticate(adi, msit123): "+Database.authenticate("adi", "msit123"));
//		System.out.println("Database.authenticate(venu1, msit123): "+Database.authenticate("venu1", "msit123"));
		
		
		//getAllSubjects test
		/*System.out.println("Database.getAllSubjects().size(): "+Database.getAllSubjects().size());
		 */
		
		//addSubject test
		/*System.out.println("Database.addSubject(\"Chemistry\").size(): "+Database.addSubject("Chemistry").size());
		System.out.println("Database.addSubject(\"\").size(): "+Database.addSubject("").size());
		*/
		
		//deleteSubject test
		/*System.out.println("Database.deleteSubject(\"Chemistry\").size(): "+Database.deleteSubject("Chemistry").size());
		System.out.println("Database.deleteSubject(\"\").size(): "+Database.deleteSubject("").size());
		*/
		
		//getAllChapters test
		/*System.out.println("Database.getAllChapters().size(): "+Database.getAllChapters().size());
		*/
				
		//addChapter test
		/*System.out.println("Database.addChapter(\"Chemistry\").size(): "+Database.addChapter("Chemistry").size());
		System.out.println("Database.addChapter(\"\").size(): "+Database.addChapter("").size());
		*/	
				
		//deleteChapter test
		/*System.out.println("Database.deleteChapter(\"Chemistry\").size(): "+Database.deleteChapter("Chemistry").size());
		System.out.println("Database.deleteChapter(\"\").size(): "+Database.deleteChapter("").size());
		*/
		
		QuizHandler qh = new QuizHandler();
		
		//testing getQuestions
		/*ArrayList<Question> qs = qh.generateQuiz(3);
		for(Question q : qs){
			System.out.println("ans: "+q.getCorrect_opt()+" SUB: "+q.getSubject()+" CHAP: "+q.getChapter());
		}*/
		
		/*ArrayList<Subject> subs = Database.getAllSubjects();
		subs.remove(1);
		ArrayList<Question> qs = qh.generateQuiz(subs,9);
		for(Question q : qs){
			System.out.println("ans: "+q.getCorrect_opt()+" SUB: "+q.getSubject()+" CHAP: "+q.getChapter());
		}*/
		
		/*ArrayList<Chapter> chaps = Database.getAllChapters();
		chaps.remove(1);
		ArrayList<Question> qs = qh.generateQuiz(chaps,14,false);
		for(Question q : qs){
			System.out.println("ans: "+q.getCorrect_opt()+" SUB: "+q.getSubject()+" CHAP: "+q.getChapter());
		}*/
		
		/*Subject s = Database.getAllSubjects().get(0);
		ArrayList<Question> qs = qh.getQuestions(s);
		for(Question q : qs){
			System.out.println("ans: "+q.getCorrect_opt()+" SUB: "+q.getSubject()+" CHAP: "+q.getChapter());
		}*/
		
		/*Chapter c = Database.getAllChapters().get(0);
		ArrayList<Question> qs = qh.getQuestions(c);
		for(Question q : qs){
			System.out.println("ans: "+q.getCorrect_opt()+" SUB: "+q.getSubject()+" CHAP: "+q.getChapter());
		}*/
		
		/*Question q = new Question();
		q.setChapter("Light");
		q.setSubject("Physics");
		q.setQuestion("How many colors are there in VIBJYOR?");
		q.setOpt1("1");
		q.setOpt2("7");
		q.setOpt3("3");
		q.setOpt4("5");
		q.setCorrect_opt("7");
		System.out.println("adding Question: "+qh.addQuestion(q));*/
		
		//testing add user function
		/*User u = new User();
		u.setUserid("shalu1");
		u.setPassword("msit123");
		u.setCity("Nagpur");
		u.setEmail("sha@lu.com");
		u.setInstitute("MSIT");
		u.setName("Shalini");
		u.setPhone("9999999995");
		u.setIs_student(false);
		System.out.println("add user: "+Database.addUser(u));*/
		
		//testing save method
		/*ArrayList<Chapter> chaps = Database.getAllChapters();
		chaps.remove(0);
		ArrayList<Question> qs = qh.generateQuiz(chaps, 7, false);
		for(Question q : qs){
			System.out.println("qid: "+q.getQid()+" SUB: "+q.getSubject()+" CHAP: "+q.getChapter());
			q.setWeight(1);
		}
		System.out.println("Saving quiz: "+qh.saveQuiz(qs));
		*/
		
		//testing Teacher ui.
		/*User u = Database.authenticate("shalu1", "msit123");
		TeacherUI.main(u);*/
		
		//testing getChapters by subject
		/*Subject s = Database.getAllSubjects().get(0);
		System.out.println("number of chapters in "+s.getSubject_name()+": "+ Database.getChapters(s).size());
		*/
		
	}

}
