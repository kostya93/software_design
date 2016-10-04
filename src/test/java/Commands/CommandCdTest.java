package Commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static Commands.FileSystemUtility.setCurrentWorkingDirectory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by semionn on 04.10.2016.
 */
public class CommandCdTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    CommandCd commandCd = new CommandCd();

    @Test
    public void testGetName() {
        assertEquals("cd", commandCd.getName());
    }

    @Test
    public void testRun() throws IOException {
        final String fileName = "temp.txt";
        File file = folder.newFile(fileName);
        List<String> lines = Arrays.asList("some line 1", "some line 2");
        Files.write(file.toPath(), lines);
        String newCWD = folder.getRoot().toString();
        setCurrentWorkingDirectory(newCWD);

        Feature feature = new Feature();
        feature.setArgs(Collections.singletonList(newCWD));
        feature = commandCd.run(feature, null);
        assertEquals(1, feature.getResults().size());
        assertEquals(System.getProperty("user.dir"), newCWD);
        assertEquals(String.format("Current working directory: %s", newCWD), feature.getResults().get(0));
        assertTrue(feature.getErrors().isEmpty());
    }
}