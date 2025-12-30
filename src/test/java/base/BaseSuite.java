package base;

import org.testng.annotations.BeforeSuite;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BaseSuite {

    @BeforeSuite(alwaysRun = true)
    public void ensureFolders() throws Exception {
        Files.createDirectories(Paths.get("logs"));
        Files.createDirectories(Paths.get("reports"));
    }
}