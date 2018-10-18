package model;

import java.util.Arrays;

public class Question
{
    private String[] test_files;
    
    private double points;
    
    private int question;
    
    private String java_file;
    
    public String[] getTest_files ()
    {
        return test_files;
    }
    
    public void setTest_files (String[] test_files)
    {
        this.test_files = test_files;
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
        return "Question [test_file = "+ Arrays.toString(test_files)+", points = "+points+", question = "+question+", java_file = "+java_file+"]";
    }
}