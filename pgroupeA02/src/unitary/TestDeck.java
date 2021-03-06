package unitary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import enumerations.Round;
import exceptions.NotEnoughAnswersException;
import exceptions.QuestionAlreadyPresentException;
import model.Deck;
import model.Question;
import utilities.Explorer;

@SuppressWarnings("unchecked")
public class TestDeck {
	private Deck deck;
	private List<Question> questions;
	private Question q1;
	private Question q2;
	private Question q3;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		deck = new Deck();
		questions = (List<Question>) Explorer.getField(deck, "questions");
		q1 = new Question("", "QUESTION1 QUESTION1 QUESTION1", Round.FIRST_ROUND);
		q1.addChoice("A", false);
		q1.addChoice("B", false);
		q1.addChoice("C", true);
		q1.addChoice("D", false);

		q2 = new Question("Author 2", "QUESTION2 QUESTION2 QUESTION2", Round.SECOND_ROUND);
		q2.addChoice("A2", false);
		q2.addChoice("B2", false);
		q2.addChoice("C2", true);
		q2.addChoice("D2", false);

		q3 = new Question("Author 3", "QUESTION3 QUESTION3 QUESTION3", Round.LAST_ROUND);
		q3.addChoice("A3", false);
		q3.addChoice("B3", false);
		q3.addChoice("C3", true);
	}

	@After
	public void tearDown() throws Exception {
		deck = null;
		questions = null;
		q1 = q2 = q3 = null;
	}

	@Test
	public void testDeck() {
		assertNotNull("Constructor returns null", questions);
	}

	@Test
	public void testAddQuestion() throws QuestionAlreadyPresentException, NotEnoughAnswersException {
		deck.addQuestion(q1);
		assertTrue("Question has not been added", questions.size() == 1);
		assertNotNull("Question has not been added", questions.get(0));

		deck.addQuestion(q2);
		assertTrue("Question has not been added", questions.size() == 2);
		assertNotNull("Question has not been added", questions.get(1));
	}

	@Test(expected = QuestionAlreadyPresentException.class)
	public void testAddQuestionQuestionAlreadyPresentException()
			throws QuestionAlreadyPresentException, NotEnoughAnswersException {
		deck.addQuestion(q1);
		deck.addQuestion(q1);
	}

	@Test(expected = NotEnoughAnswersException.class)
	public void testAddQuestionNotAllAnswersException()
			throws QuestionAlreadyPresentException, NotEnoughAnswersException {
		deck.addQuestion(q3);
	}

	@Test
	public void testRemoveQuestion() {
		questions.add(q1);
		questions.add(q2);
		deck.removeQuestion(q1);

		assertFalse("Question has not been removed", questions.contains(q1));
	}

	@Test
	public void testGetDeckSize() {
		questions.add(q1);
		questions.add(q2);
		assertEquals("Returned deck size is not right", deck.questionsSize(), 2);
	}

	@Test
	public void testGetQuestions() {
		questions.add(q1);
		questions.add(q2);
		questions.add(q3);
		questions = (List<Question>) Explorer.getField(deck, "questions");

		assertEquals("Should be equal cause of same questions", questions, deck.getQuestions());
		assertFalse("Shouldn't have the same reference", q1 == deck.getQuestions().get(0));
		assertEquals("Should be equal", q1, deck.getQuestions().get(0));
	}

	@Test
	public void testToString() {
		questions.add(q2);
		deck.toString();
	}

	@Test
	public void testHashCode() {
		Deck deck2 = new Deck();
		List<Question> questions2 = (List<Question>) Explorer.getField(deck2, "questions");
		questions2.add(q1);
		questions2.add(q2);

		questions.add(q1);
		questions.add(q2);

		assertTrue("Should have the same hashcode", deck.hashCode() == deck2.hashCode());
		questions.add(q3);
		assertFalse("Shouldn't have the same hashcode", deck.hashCode() == deck2.hashCode());
	}

	@Test
	public void testEqualsObject() throws QuestionAlreadyPresentException, NotEnoughAnswersException {
		Deck deck2 = new Deck();
		questions.add(q1);
		deck2.addQuestion(q1);
		assertEquals("Decks are not equals", deck, deck2);

		questions.add(q2);
		assertNotEquals("Decks should not be equals", deck, deck2);

		assertEquals("Should be equal cause of same reference", deck, deck);
		assertNotEquals("Shouldn't be equal cause of null", deck, null);
		assertNotEquals("Shouldn't be equal cause of noot same class", deck, new Object());
	}

}
