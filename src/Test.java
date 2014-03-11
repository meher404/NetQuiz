import util.Database;


public class Test {

	public static void main(String[] args) {
		
		//Authenticate method tests
		System.out.println("Database.authenticate(adi1, \"\"): "+Database.authenticate("adi1", ""));
		System.out.println("Database.authenticate(\"\", \"\"): "+Database.authenticate("", ""));
		System.out.println("Database.authenticate(adi1, msit123): "+Database.authenticate("adi1", "msit123"));
		System.out.println("Database.authenticate(adi, msit123): "+Database.authenticate("adi", "msit123"));
		System.out.println("Database.authenticate(venu1, msit123): "+Database.authenticate("venu1", "msit123"));
		
		
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
		
		
	}

}
