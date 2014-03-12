package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import lib.Chapter;
import lib.Subject;
import lib.User;


public class Database {
	
	static private Connection connect = null;
	static Statement st = null;
	static String classname="com.mysql.jdbc.Driver";
	static String	url="jdbc:mysql://localhost/netquiz";
	static String	un="root";
	static String	pwd="root";
	
	/**
	 * Method authenticates user credentials and returns the type_of_user, student or teacher in integer.
	 * @param un
	 * @param pwd
	 * @return user object corresponding to that details or if validation fails null.
	 * @author Meher
	 */
	public static User authenticate(String usn, String pswd){
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    ResultSet rs = st.executeQuery("select * from user where userid='"+usn+"' and password='"+pswd+"' ; ");
		    
		    
		    User user=null;
		    if (rs.next()){//ResultSet is not empty
		    	user = new User();
		    	user.setUserid(usn);
		    	user.setName(rs.getString("name"));
		    	user.setEmail(rs.getString("email"));
		    	user.setPhone(rs.getString("phone"));
		    	user.setInstitute(rs.getString("institute"));
		    	user.setCity(rs.getString("city"));
		    	user.setIs_student(rs.getInt("type_of_account")==0);
		    }
		    st.close();
		    return user;
		}
		catch(Exception e){
			System.out.println("Error in authenticate.");
			e.printStackTrace();
			return null;
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in authenticate");
				}
			}
		}
		
	}
	
	
	//Subjects CRUD
	/**
	 * Method returns all the subjects that are available in the database table subjects
	 * @return list of subject objects
	 * @author Meher
	 */
	public static ArrayList<Subject> getAllSubjects(){
		try{
			ArrayList<Subject> subs = new ArrayList<Subject>();
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    ResultSet rs = st.executeQuery("select * from subjects; ");
		    
		    while(rs.next()){
		    	Subject s = new Subject();
		    	s.setSubjectID(rs.getInt(1));
		    	s.setSubject_name(rs.getString(2));
		    	subs.add(s);
		    }
		   
		    st.close();
		    return subs;
		}
		catch(Exception e){
			System.out.println("Error in getAllSubjects.");
			e.printStackTrace();
			return null;
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in getAllSubjects");
				}
			}
		}
	}
	
	/**
	 * Method to create a new subject and store it in database.
	 * @param sub_name name of the subject
	 * @return the new list of subjects after adding new subject or null if the insertion fails
	 * @author Meher
	 */
	public static ArrayList<Subject> addSubject(String sub_name){
		try{
			
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    int i=0;
		    
		    if(!sub_name.isEmpty())
		    	i= st.executeUpdate("insert into subjects(subject) values ('"+sub_name+"'); ");
		    
		    st.close();
		    if(i==0)
		    	return null;
		    return getAllSubjects();
		}
		catch(Exception e){
			System.out.println("Error in addSubject.");
			e.printStackTrace();
			return null;
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in addSubject");
				}
			}
		}
	}
	
	/**
	 * Method to delete a non used subject, i.e if there are no chapters with this subject as base and no questions related 
	 * this subject.
	 * If you want to delete the subject even if it being used, try deleteSubject(sub_name,true).
	 * @param sub_name name of the subject to be deleted
	 * @return the new list of subjects after deletion. if any error in query null.
	 * @author Meher 
	 */
	public static ArrayList<Subject> deleteSubject(String sub_name){
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    int i=0;
		    
		    if(!sub_name.isEmpty())
		    	i= st.executeUpdate("delete from subjects where subject='"+sub_name+"' ;");
		    
		    st.close();
		    if(i==0)
		    	return null;
		    return getAllSubjects();
		}
		catch(Exception e){
			System.out.println("Error in deleteSubject.");
			e.printStackTrace();
			return null;
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in deleteSubject");
				}
			}
		}
	}
	
	/**
	 * (Yet to test) Method to completely remove the subject from the database, i.e. all questions of this subjects will also be removed.
	 * @param sub_name subject name
	 * @param full true or false to select full delete option.
	 * @return new list of subjects after deletion
	 * @author Meher
	 */
	public static ArrayList<Subject> deleteSubject(String sub_name, boolean full){
		//have to test. PLEASE DO NOT USE till add question and all other methods are written
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    int i=0;
		    
		    if(!sub_name.isEmpty()){
		    	ResultSet rs = st.executeQuery("select subID from subjects where subject='"+sub_name+"' ;");
		    	if(rs.next()){
		    		i = rs.getInt(1);
		    		ResultSet rs_inner = st.executeQuery("select qid from question_bank where subID="+i+" ;");
		    		while(rs_inner.next()){
		    			st.executeUpdate("delete from quiz where qid="+rs_inner.getInt(1)+" ;");
		    		}
		    		st.executeUpdate("delete from question_bank where subID="+i+" ;");
			    	i= st.executeUpdate("delete from subjects where subject='"+sub_name+"' ;");
		    	}
		    	else
		    		return null;
		    	
		    }
		    st.close();
		    if(i==0)
		    	return null;
		    return getAllSubjects();
		}
		catch(Exception e){
			System.out.println("Error in full deleteSubject .");
			e.printStackTrace();
			return null;
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in full deleteSubject");
				}
			}
		}
	}

	
	//Chapters CRUD
	/**
	 * Method returns all the Chapters that are available in the database table subjects
	 * @return list of chapter objects
	 * @author Meher
	 */
	public static ArrayList<Chapter> getAllChapters(){
		try{
			ArrayList<Chapter> chaps = new ArrayList<Chapter>();
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    ResultSet rs = st.executeQuery("select * from chapters; ");
		    
		    while(rs.next()){
		    	Chapter c = new Chapter();
		    	c.setChapterID(rs.getInt(1));
		    	c.setChapter_name(rs.getString(2));
		    	chaps.add(c);
		    }
		   
		    st.close();
		    return chaps;
		}
		catch(Exception e){
			System.out.println("Error in getAllChapters.");
			e.printStackTrace();
			return null;
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in getAllChapters");
				}
			}
		}
	}
	
	/**
	 * Method to create a new Chapter and store it in database.
	 * @param chap_name name of the chapter
	 * @return the new list of chapters after adding new chapter or null if the insertion fails
	 * @author Meher
	 */
	public static ArrayList<Chapter> addChapter(String chap_name){
		try{	
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    int i=0;
		    
		    if(!chap_name.isEmpty())
		    	i= st.executeUpdate("insert into chapters(chapter) values ('"+chap_name+"'); ");
		    
		    st.close();
		    if(i==0)
		    	return null;
		    return getAllChapters();
		}
		catch(Exception e){
			System.out.println("Error in addChapter.");
			e.printStackTrace();
			return null;
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in addChapter");
				}
			}
		}
	}
	
	/**
	 * Method to delete a non used chapter, i.e if there are no questions related 
	 * this chapter.
	 * If you want to delete the chapter even if it is being used, try deleteChapter(sub_name,true).
	 * @param chap_name name of the chapter to be deleted
	 * @return the new list of chapters after deletion. if any error in query null.
	 * @author Meher 
	 */
	public static ArrayList<Chapter> deleteChapter(String chap_name){
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    int i=0;
		    
		    if(!chap_name.isEmpty())
		    	i= st.executeUpdate("delete from chapters where chapter='"+chap_name+"' ;");
		    
		    st.close();
		    if(i==0)
		    	return null;
		    return getAllChapters();
		}
		catch(Exception e){
			System.out.println("Error in deleteChapter.");
			e.printStackTrace();
			return null;
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in deleteChapter");
				}
			}
		}
	}
	
	/**
	 * (Yet to test) Method to completely remove the chapter from the database, i.e. all questions of this chapter will also be removed.
	 * @param chap_name chapter name
	 * @param full true or false to select full delete option.
	 * @return new list of chapters after deletion
	 * @author Meher
	 */
	public static ArrayList<Chapter> deleteChapter(String chap_name, boolean full){
		//have to test. PLEASE DO NOT USE till add question and all other methods are written
		try{
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    int i=0;
		    
		    if(!chap_name.isEmpty()){
		    	ResultSet rs = st.executeQuery("select chapID from chapters where chapter='"+chap_name+"' ;");
		    	if(rs.next()){
		    		i = rs.getInt(1);
		    		ResultSet rs_inner = st.executeQuery("select qid from question_bank where chapID="+i+" ;");
		    		while(rs_inner.next()){
		    			st.executeUpdate("delete from quiz where qid="+rs_inner.getInt(1)+" ;");
		    		}
		    		st.executeUpdate("delete from question_bank where chapID="+i+" ;");
			    	i= st.executeUpdate("delete from chapters where chapter='"+chap_name+"' ;");
		    	}
		    	else
		    		return null;
		    	
		    }
		    st.close();
		    if(i==0)
		    	return null;
		    return getAllChapters();
		}
		catch(Exception e){
			System.out.println("Error in full deleteChapter .");
			e.printStackTrace();
			return null;
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in full deleteChapter");
				}
			}
		}
	}

	//User functions
	/**
	 * Method to add a user to the database.
	 * @param user object
	 * @return true if added properly, if it fails false.
	 */
	public static boolean addUser(User user){
		try{	
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    int i=0;
		    int k = 0;
		    if(user.is_student())
		    	k=0;
		    else
		    	k=1;
		   	i= st.executeUpdate("insert into user values ('"+user.getUserid()+"','"+user.getPassword()+"','"+user.getName()+"','"+user.getInstitute()+"','"+user.getCity()+"','"+user.getPhone()+"',"+k+",'"+user.getEmail()+"'); ");
		    
		    st.close();
		    if(i==0)
		    	return false;
		    return true;
		}
		catch(Exception e){
			System.out.println("Error in addUser.");
			e.printStackTrace();
			return false;
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in addUser");
				}
			}
		}
	}
	
	/**
	 * Method that gets all the chapters associated with that subject
	 * @param subject object
	 * @return list of chapters in that subject.
	 * @author Meher
	 */
	public static ArrayList<Chapter> getChapters(Subject s){
		try{	
			ArrayList<Chapter> chaps = new ArrayList<Chapter>();
			Class.forName(classname);
		    connect = DriverManager.getConnection(url,un,pwd);  
		    st = connect.createStatement();
		    ResultSet rs = st.executeQuery("select DISTINCT(chapID) from question_bank where subID="+s.getSubjectID()+" ;");
		    while (rs.next()){
				ResultSet rs1 = connect.createStatement().executeQuery("select * from chapters where chapID="+rs.getInt(1)+" ;");
				
				if(rs1.next()){
					Chapter c = new Chapter();
					c.setChapterID(rs1.getInt(1));
					c.setChapter_name(rs1.getString(2));
					chaps.add(c);
				}
			}
		    st.close();
		    return chaps;
		}
		catch(Exception e){
			System.out.println("Error in getChapters.");
			e.printStackTrace();
			return null;
		}
		finally{
			if(connect!=null){
				try {
					connect.close();
				} catch (Exception e) {
					System.out.println("Error during connection closing in getChapters");
				}
			}
		}
	}
	
}
