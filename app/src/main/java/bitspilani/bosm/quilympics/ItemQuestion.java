package bitspilani.bosm.quilympics;

public class ItemQuestion {
    int question_no;
    String question;
    String optionA, optiionB, optionC, optionD;
    int answer;
    int answer1, answer2;

    public ItemQuestion(int question_no, String question, String optionA, String optiionB, String optionC, String optionD, int answer1, int answer2) {
        this.question_no = question_no;
        this.question = question;
        this.optionA = optionA;
        this.optiionB = optiionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    public ItemQuestion(int question_no, String question, String optionA, String optiionB, String optionC, String optionD) {
        this.question_no = question_no;
        this.question = question;
        this.optionA = optionA;
        this.optiionB = optiionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    public ItemQuestion(int question_no, String question, String optionA, String optiionB, String optionC, String optionD, int answer) {
        this.question_no = question_no;
        this.question = question;
        this.optionA = optionA;
        this.optiionB = optiionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
    }

    public int getAnswer1() {
        return answer1;
    }

    public int getAnswer2() {
        return answer2;
    }

    public int getAnswer() {
        return answer;
    }

    public int getQuestion_no() {
        return question_no;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptiionB() {
        return optiionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }
}
