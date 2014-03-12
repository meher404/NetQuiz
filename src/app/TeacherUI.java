package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import util.Database;
import lib.Subject;
import lib.User;

public class TeacherUI {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(User user)  {
		try{
			System.out.println("\t\tTeacher Home Page");
			System.out.println("\nWelcome, "+user.getName());
			System.out.println("\tMenu");
			int k=0;
			
			while(k!=-1){
				System.out.println("1. Manage All Subjects/Chapters/Questions");
				System.out.println("2. Add Question to Question Bank");
				System.out.println("3. Create a Quiz");
				System.out.println("4. Sign Out");
				k = Integer.parseInt(br.readLine());
				if(k==4){
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
			
			br.close();
		}
		catch(Exception e){
			System.out.println("An unexpected error occured. Sorry. Try again.");
		}
	}

	private static void manageSubjects() {
		ArrayList<Subject> subs = Database.getAllSubjects();
		System.out.println("List of all subjects: \n");
		
		System.out.println("Type the subject Id to list all the chapters in that Subject.");
		for(Subject s : subs){
			System.out.println(s.getSubjectID()+" "+s.getSubject_name());
		}
		
		System.out.println("\n101. Add another Subject");
		System.out.println("102. Go back to main menu");
		try {
			int k=Integer.parseInt(br.readLine());
			switch(k){
			case 102: return;
			case 101: {
						System.out.println("Enter the name of the subject: ");
						String name = br.readLine();
						if(Database.addSubject(name)!=null)
							System.out.println("Subject Created Successfully, come back again to add chapters in it.\n");
						else
							System.out.println("Sorry, could not add subject, please try again.\n");
					  }break;
			default: getChapters(k);
			}
			
		} catch (Exception e) {
			System.out.println("Invalid option. Going back to main menu.");
		} 
		
	}
	
	private static void getChapters(int k) {
		
		
	}

	private static void addQuestion() {
		
	}

	private static void createQuiz() {
		
	}


}
