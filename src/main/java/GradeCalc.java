import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import model.GradeScheme;
import model.Question;
import model.TestFileResults;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradeCalc {

    private Log log;

    GradeCalc(Log log) {
        this.log = log;
    }

    public void gradeCalc() throws MojoExecutionException {

        // Read grades week_X.json file

        GradeScheme gradeScheme = readGradeScheme();

        if (gradeScheme == null) {
            throw new MojoExecutionException("Unable to read grade scheme file from grades directory. " +
                    "\nSorry, can't calculate estimated grade.");
        }

        int week = gradeScheme.getWeek();

        Question[] questions = gradeScheme.getQuestions();

        double totalPointsEarnedForLab = 0;

        for (Question question : questions) {

            String codeFileName = question.getJava_file();
            int questionNumber = question.getQuestion();
            String[] testFiles = question.getTest_files();
            double pointsAvailableForQuestion = question.getPoints();

            log.info(String.format("Question %d, %s. Total points available: %.2f\n",
                    questionNumber, codeFileName, pointsAvailableForQuestion));

            double totalPass = 0;
            double totalFail = 0;

            for (String testFile : testFiles) {
                TestFileResults results = readTestResults(testFile);
                if (results == null) {
                    log.warn("Warning - could not read results from Test Results file. Skipping.");
                } else {
                    log.info(results.summary());
                    totalPass += results.getTotalPassed();
                    totalFail += results.getTotalNotPassed();
                }
            }

            double fractionPassed = totalPass / ( totalFail + totalPass );
            double pointsEarnedForQuestion = fractionPassed * pointsAvailableForQuestion;
            log.info(String.format("Fraction of tests passed for question %d: %.2f. Points earned %.2f\n", questionNumber, fractionPassed, pointsEarnedForQuestion));
            totalPointsEarnedForLab += pointsEarnedForQuestion;

        }

        log.info(String.format("Total estimated points for lab, based on tests passing: %.2f\n", totalPointsEarnedForLab));

    }

    private TestFileResults readTestResults(String fileBase) throws MojoExecutionException {

        // find the surefire file e.g  /target/surefire-reports/week_5.Question_4_Exception_Handling_QuestionsTest.txt

        log.info("Test result file: " + fileBase);

        String filename = String.format("%s.txt", fileBase);
        File dirOb = new File("target", "surefire-reports");
        File fileOb = new File(dirOb, filename);

        try ( BufferedReader r = new BufferedReader(new FileReader(fileOb)) ) {

            String line;
            StringBuilder all = new StringBuilder();
            while ((line = r.readLine()) != null) {
                all.append(line);
            }

            // An example line from the test report file looks like this
            // Tests run: 6, Failures: 4, Errors: 0, Skipped: 0, Time elapsed: 0.055 s <<< FAILURE! - in week_6.q1_course.ITECCourseTest

            Pattern testRun = Pattern.compile(".*Tests run: (?<run>\\d+), Failures: (?<failures>\\d+), Errors: (?<errors>\\d+), Skipped: (?<skipped>\\d).*");

            Matcher m = testRun.matcher(all.toString());
            if (m.matches()) {
                return new TestFileResults(m.group("run"), m.group("failures"), m.group("errors"), m.group("skipped"));
            } else {
                log.warn("Unable to extract test statistics from test file");
                return null;
            }
        } catch (IOException e) {
            log.error("Can't find test results file. Did you run this from the run configuration provided? Is there anything in target/surefire-reports?");
            //throw new MojoExecutionException("error finding test results file");
            return null;
        }

    }


    private GradeScheme readGradeScheme() throws MojoExecutionException {
        try {
            JsonReader reader = new JsonReader(new FileReader(new File("grades", "grade_scheme.json")));
            Gson gson = new Gson();
            return gson.fromJson(reader, GradeScheme.class);

        } catch (FileNotFoundException e) {
            log.error("Unable to read grade schema data from grade file.");
            throw new MojoExecutionException("Unable to find grades/grade_scheme.json file.");
        } catch (JsonParseException e) {
            throw new MojoExecutionException("Unable to read JSON data from grades/grades.json file");
        }
    }

}
