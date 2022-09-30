package QuestionBank;

import java.util.LinkedList;

public class Database {

    //Variables
    private QuestionBank QuestionBank;


    //Constructors
    public Database(){
        QuestionBank = new QuestionBank();
    }

    //Methods
    public LinkedList<Questions> GetQuestions() {
        return QuestionBank.GetQuestions();
    }

    public LinkedList<Tag> GetTagsList() {
        return QuestionBank.GetTagsList();
    }

    public Boolean CreateQuestion() {   //I believe that this should take as arguments: question-String, correctAnswer-String, possibleAnswer-String
        return QuestionBank.CreateQuestion();
    }

    public boolean RemoveQuestion(int id) {
        return QuestionBank.RemoveQuestion(id);
    }

    public Questions GetQuestion(int id) {
        return QuestionBank.GetQuestion(id);
    }

    public LinkedList<Questions> GetTaggedQuestions(String tagName) {
        return QuestionBank.GetTaggedQuestions(tagName);
    }

}
