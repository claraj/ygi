package model;

import java.util.Arrays;

/**
 * Created by clara on 1/26/18.
 */
public class GradeScheme {
    
    private Question[] questions;
    
    private int week;
    
    public Question[] getQuestions ()
    {
        return questions;
    }
    
    public void setQuestions (Question[] questions)
    {
        this.questions = questions;
    }
    
    public int getWeek ()
    {
        return week;
    }
    
    public void setWeek (int week)
    {
        this.week = week;
    }
    
    @Override
    public String toString()
    {
        return "GradeScheme [questions = "+ Arrays.toString(questions)+", week = "+week+"]";
    }
}

