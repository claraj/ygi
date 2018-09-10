import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "ygi")
public class YourGradeIs extends AbstractMojo {

    public void execute() throws MojoExecutionException {
        getLog().info("hello from the grade calculator");
        new GradeCalc(getLog()).gradeCalc();
    }

    // todo write

}
