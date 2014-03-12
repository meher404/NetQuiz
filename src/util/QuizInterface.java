package util;

import java.util.ArrayList;

import lib.Chapter;
import lib.Question;
import lib.Quiz;
import lib.Subject;


public interface QuizInterface {

	/**
	 * Generates a quiz with random questions from all the subjects and all chapters
	 * @param num_of_questions takes the number of questions the quiz should have. Minimum is the number of subjects
	 * @return a list of questions (equal weight to each question) from all subjects and all chapters,
	 * If the number of questions is less than the number of subjects, returns null.
	 * @author Meher
	 */
	public ArrayList<Question> generateQuiz(int num_of_questions);
	
	/**
	 * Generates a quiz with random questions from the subjects passed as parameters.
	 * @param subs list of subjects to be covered
	 * @param num_of_questions total number of questions in the quiz
	 * @return list of questions with default/equal weight to each question
	 * @author Meher
	 */
	public ArrayList<Question> generateQuiz(ArrayList<Subject> subs, int num_of_questions);
	
	/**
	 * Generates a quiz with random questions from the chapters passed as parameters
	 * @param chaps list of chapters to be covered
	 * @param num_of_questions total number of questions in the quiz
	 * @param chapters signals that chapter level questions should be picked
	 * @return list of questions with each question having equal weight
	 * @author Meher
	 */
	public ArrayList<Question> generateQuiz(ArrayList<Chapter> chaps, int num_of_questions, boolean chapters);
	
	/**
	 * Method gets all the questions of that subject
	 * @param subject object
	 * @return list of all questions in that subject of all chapters
	 * @author Meher
	 */
	public ArrayList<Question> getQuestions(Subject subject);
	
	/**
	 * Method gets all the questions of that chapter
	 * @param chapter object
	 * @return list of all questions in that chapter
	 * @author Meher
	 */
	public ArrayList<Question> getQuestions(Chapter chapter);
	
	/**
	 * Method to add a question into the question bank table.
	 * @param question object
	 * @return true if added else false.
	 * @author Meher
	 */
	public boolean addQuestion(Question question);
	
	/**
	 * Method to save the quiz questions in the database, so that a teacher can give retrive and student can take it.
	 * Weight should be given to each question while inserting.
	 * @param quiz is a list of questions with all fields filled
	 * @return quiz id if saved successfully else -1.
	 * @author Meher
	 */
	public int saveQuiz(ArrayList<Question> quiz);
	
	/**
	 * Method to evaluate the quiz taken by the student
	 * @param quiz object
	 * @return the score that student obtains.
	 */
	public int evaluateQuiz(Quiz quiz);
}
