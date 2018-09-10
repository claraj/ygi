package model;

import java.util.Arrays;

public class Question
{
    private String[] test_file;
    
    private double points;
    
    private int question;
    
    private String java_file;
    
    public String[] getTest_files ()
    {
        return test_file;
    }
    
    public void setTest_files (String[] test_file)
    {
        this.test_file = test_file;
    }
    
    public double getPoints ()
    {
        return points;
    }
    
    public void setPoints (double points)
    {
        this.points = points;
    }
    
    public int getQuestion ()
    {
        return question;
    }
    
    public void setQuestion (int question)
    {
        this.question = question;
    }
    
    public String getJava_file ()
    {
        return java_file;
    }
    
    public void setJava_file (String java_file)
    {
        this.java_file = java_file;
    }
    
    @Override
    public String toString()
    {
        return "Question [test_file = "+ Arrays.toString(test_file)+", points = "+points+", question = "+question+", java_file = "+java_file+"]";
    }
}