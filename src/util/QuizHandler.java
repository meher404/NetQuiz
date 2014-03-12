package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import lib.Chapter;
import lib.Question;
import lib.Quiz;
import lib.Subject;


public class QuizHandler implements QuizInterface {

	static private Connection connect = null;
	static Statement st = null;
	static String classname="com.mysql.jdbc.Driver";
	static String url="jdbc:mysql://localhost/netquiz";
	static String un="root";
	static String pwd="root";
	
	@Override
	public ArrayList<Question> generateQuiz(int num_of_questions) {
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    
			ArrayList<Question> questions = new ArrayList<Question>();
			ArrayList<Subject> subs = Database.getAllSubjects();
			
			if(num_of_questions<subs.size()){
				st.close();
				return null;
			}
			
			int n = num_of_questions/subs.size();
			
			for(Subject s : subs){
				ResultSet rs = st.executeQuery("select * from question_bank where subID="+s.getSubjectID()+" ORDER BY RAND() LIMIT "+n+" ;");
				while(rs.next()){
					Question q = new Question();
					q.setQid(rs.getInt(1));
					q.setSubject(s.getSubject_name());
					//set question chapter
					ResultSet rs1 = connect.createStatement().executeQuery("select chapter from chapters where chapID="+rs.getInt(2)+" ;");
					if(rs1.next()){
						q.setChapter(rs1.getString(1));
					}
					q.setQuestion(rs.getString(4));
					q.setOpt1(rs.getString(5));
					q.setOpt2(rs.getString(6));
					q.setOpt3(rs.getString(7));
					q.setOpt4(rs.getString(8));
					q.setCorrect_opt(rs.getString(9));
					questions.add(q);
				}
			}
			st.close();
			return questions;
		}
		catch(Exception e){
			System.out.println("error in generateQuiz");
			e.printStackTrace();
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in generateQuiz");
				}
			}
		}
		return null;
	}

	@Override
	public ArrayList<Question> generateQuiz(ArrayList<Subject> subs,
			int num_of_questions) {
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    
			ArrayList<Question> questions = new ArrayList<Question>();
			
			if(num_of_questions<subs.size()){
				st.close();
				return null;
			}
			
			int n = num_of_questions/subs.size();
			
			for(Subject s : subs){
				ResultSet rs = st.executeQuery("select * from question_bank where subID="+s.getSubjectID()+" ORDER BY RAND() LIMIT "+n+" ;");
				while(rs.next()){
					Question q = new Question();
					q.setQid(rs.getInt(1));
					q.setSubject(s.getSubject_name());
					//set question chapter
					ResultSet rs1 = connect.createStatement().executeQuery("select chapter from chapters where chapID="+rs.getInt(2)+" ;");
					if(rs1.next()){
						q.setChapter(rs1.getString(1));
					}
					q.setQuestion(rs.getString(4));
					q.setOpt1(rs.getString(5));
					q.setOpt2(rs.getString(6));
					q.setOpt3(rs.getString(7));
					q.setOpt4(rs.getString(8));
					q.setCorrect_opt(rs.getString(9));
					questions.add(q);
				}
			}
			st.close();
			return questions;
		}
		catch(Exception e){
			System.out.println("error in generateQuiz 2");
			e.printStackTrace();
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in generateQuiz 2");
				}
			}
		}
		return null;
	}

	@Override
	public ArrayList<Question> generateQuiz(ArrayList<Chapter> chaps,
			int num_of_questions, boolean chapters) {
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    
			ArrayList<Question> questions = new ArrayList<Question>();
			
			if(num_of_questions<chaps.size()){
				st.close();
				return null;
			}
			
			int n = num_of_questions/chaps.size();
			
			for(Chapter s : chaps){
				ResultSet rs = st.executeQuery("select * from question_bank where chapID="+s.getChapterID()+" ORDER BY RAND() LIMIT "+n+" ;");
				while(rs.next()){
					Question q = new Question();
					q.setQid(rs.getInt(1));
					q.setChapter(s.getChapter_name());
					//set question subject
					ResultSet rs1 = connect.createStatement().executeQuery("select subject from subjects where subID="+rs.getInt(3)+" ;");
					if(rs1.next()){
						q.setSubject(rs1.getString(1));
					}
					q.setQuestion(rs.getString(4));
					q.setOpt1(rs.getString(5));
					q.setOpt2(rs.getString(6));
					q.setOpt3(rs.getString(7));
					q.setOpt4(rs.getString(8));
					q.setCorrect_opt(rs.getString(9));
					questions.add(q);
				}
			}
			st.close();
			return questions;
		}
		catch(Exception e){
			System.out.println("error in generateQuiz 3");
			e.printStackTrace();
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in generateQuiz 3");
				}
			}
		}
		return null;
	}

	@Override
	public ArrayList<Question> getQuestions(Subject subject) {
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    
			ArrayList<Question> questions = new ArrayList<Question>();

			ResultSet rs = st.executeQuery("select * from question_bank where subID="+subject.getSubjectID()+";");
			while(rs.next()){
				Question q = new Question();
				q.setQid(rs.getInt(1));
				q.setSubject(subject.getSubject_name());
				//set question subject
				ResultSet rs1 = connect.createStatement().executeQuery("select chapter from chapters where chapID="+rs.getInt(2)+" ;");
				if(rs1.next()){
					q.setChapter(rs1.getString(1));
				}
				q.setQuestion(rs.getString(4));
				q.setOpt1(rs.getString(5));
				q.setOpt2(rs.getString(6));
				q.setOpt3(rs.getString(7));
				q.setOpt4(rs.getString(8));
				q.setCorrect_opt(rs.getString(9));
				questions.add(q);
			}
			st.close();
			return questions;
		}
		catch(Exception e){
			System.out.println("error in getQuestions sub");
			e.printStackTrace();
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in getQuestions sub");
				}
			}
		}
		return null;
	}

	@Override
	public ArrayList<Question> getQuestions(Chapter chapter) {
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    
			ArrayList<Question> questions = new ArrayList<Question>();

			ResultSet rs = st.executeQuery("select * from question_bank where chapID="+chapter.getChapterID()+";");
			while(rs.next()){
				Question q = new Question();
				q.setQid(rs.getInt(1));
				q.setChapter(chapter.getChapter_name());
				//set question subject
				ResultSet rs1 = connect.createStatement().executeQuery("select subject from subjects where subID="+rs.getInt(3)+" ;");
				if(rs1.next()){
					q.setSubject(rs1.getString(1));
				}
				q.setQuestion(rs.getString(4));
				q.setOpt1(rs.getString(5));
				q.setOpt2(rs.getString(6));
				q.setOpt3(rs.getString(7));
				q.setOpt4(rs.getString(8));
				q.setCorrect_opt(rs.getString(9));
				questions.add(q);
			}
			st.close();
			return questions;
		}
		catch(Exception e){
			System.out.println("error in getQuestions chap");
			e.printStackTrace();
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in getQuestions chap");
				}
			}
		}
		return null;
	}

	@Override
	public boolean addQuestion(Question question) {
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    int subID=0,i=0,chapID=0;
		    ResultSet rs1 = connect.createStatement().executeQuery("select subID from subjects where subject='"+question.getSubject()+"' ;");
			if(rs1.next()){
				subID = (rs1.getInt(1));
				rs1 = connect.createStatement().executeQuery("select chapID from chapters where chapter='"+question.getChapter()+"' ;");
				if(rs1.next()){
					chapID = (rs1.getInt(1));
					i = st.executeUpdate("insert into question_bank(chapID,subID,question,opt1,opt2,opt3,opt4,correct_opt) "
							+ "values ("+chapID+","+subID+",'"+question.getQuestion()+"','"+question.getOpt1()+"','"+question.getOpt2()+"',"
									+ "'"+question.getOpt3()+"','"+question.getOpt4()+"','"+question.getCorrect_opt()+"') ;");
					st.close();
					if(i==1)
						return true;
						
				}
			}
			else{
				st.close();
				return false;
			}
		}
		catch(Exception e){
			System.out.println("error in addQuestion");
			e.printStackTrace();
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in addQuestion");
				}
			}
		}
		return false;
	}

	public int saveQuiz(ArrayList<Question> quiz){
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    int quizid=0;
		    if(quiz.isEmpty()){
		    	st.close();
		    	return -1;
		    }
		    Question q = quiz.get(0);
		    st.executeUpdate("insert into quiz(qid,weight) values ("+q.getQid()+","+q.getWeight()+"); ");
		    ResultSet rs = st.executeQuery("select quiz_id from quiz ORDER BY quiz_id DESC LIMIT 1; ");
		    if(rs.next()){
		    	quizid = rs.getInt(1);
		    }
		    quiz.remove(q);
		    int i=0;
		    for(Question qs : quiz){
		    	i = st.executeUpdate("insert into quiz values ("+quizid+","+qs.getQid()+","+qs.getWeight()+"); ");
		    }
		    st.close();
		    if(i==1)
		    	return quizid;
		    return -1;
		}
		catch(Exception e){
			System.out.println("error in saveQuiz ");
			e.printStackTrace();
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in saveQuiz");
				}
			}
		}
		return -1;
	}

	@Override
	public int evaluateQuiz(Quiz quiz) { //yet to be tested..
		int result=0;
		for(Question q : quiz.getQuestions()){
			if(q.getAnswer().equalsIgnoreCase(q.getCorrect_opt())){
				result += 1;
			}
			else if(q.getWeight()!=1)
				result += q.getWeight();
		}
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    
		    Timestamp t = new Timestamp(new Date().getTime());
		    
		    st.executeUpdate("insert into student_history values ("+quiz.getQuizid()+",'"+quiz.getUser().getUserid()+"','"+t+"',"+result+"); ");
		    
		    st.close();
		}
		catch(Exception e){
			System.out.println("error in evaluateQuiz ");
			e.printStackTrace();
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in evaluateQuiz");
				}
			}
		}
		return result;
	}
	
}
