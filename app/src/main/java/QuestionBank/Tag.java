package QuestionBank;

import java.util.LinkedList;

public class Tag {

    //Attributes
    private String TagName;

    private LinkedList<Integer> QuestionsOfTagType;

    //Constructor
    protected Tag(String tagName) {
        TagName = tagName;
        QuestionsOfTagType = new LinkedList<>();
    }

    //Methods
    /**
     * Method that returns the current tag name.
     * @return Current tag name.
     */
    protected String GetTagName() { return this.TagName;}

    /**
     * Method that returns list with a tag related to an integer
     * @return A list containing a specific tag.
     */
    protected LinkedList<Integer> GetQuestionsOfTagType(){
        return this.QuestionsOfTagType;
    }

    /**
     * Method that updates the current tag name.
     * @param tagName The name of the tag being updated.
     */
    protected void setTagName(String tagName) {
        TagName = tagName;
    }

    /**
     * Method that will update a specific lists tag, if needed.
     * @param questionsOfTagType list of specific tags.
     */
    protected void setQuestionsOfTagType(LinkedList<Integer> questionsOfTagType) {
        QuestionsOfTagType = questionsOfTagType;
    }

    /**
     * Method that adds a question id to the list of Questions with this tag
     * @param id The id number of the question being added
     * @return True if the question id was added correctly; False otherwise
     */
    protected boolean AddQuestion(int id) {
        return this.QuestionsOfTagType.add(id);
    }
}

