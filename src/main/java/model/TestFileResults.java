package model;

/**
 * Created by clara on 1/26/18.
 */

public class TestFileResults {
    
    private int run;
    
    private int failures;
    private int errors;
    private int skipped;
    private int totalNotPassed;
    private int totalPassed;
    
    public TestFileResults(String run, String failures, String errors, String skipped) {
        this(Integer.parseInt(run), Integer.parseInt(failures), Integer.parseInt(errors), Integer.parseInt(skipped));
    }
    
    public TestFileResults(int run, int failures, int errors, int skipped) {
        this.run = run;
        this.failures = failures;
        this.errors = errors;
        this.skipped = skipped;
        
        this.totalNotPassed = failures + errors + skipped;
        this.totalPassed = this.run - totalNotPassed;
    }
    
    
    public int getTotalNotPassed() {
        return this.totalNotPassed;
    }
    
    public int getTotalPassed() {
        return this.totalPassed;
    }
    
    public int getTotalRan() {
        return this.run;
    }
    
    @Override
    public String toString() {
        return String.format("%d test(s) ran. %d failed, %d errors, %d skipped. %d passed.",
                this.run, this.failures, this.skipped, this.errors, this.totalPassed);
    }
    
    public String summary() {
        return String.format("%d test(s) ran. %d failed, errored, or skipped. %d passed.",
                this.run, this.failures + this.skipped + this.errors, this.totalPassed);
    
    }
    
}

