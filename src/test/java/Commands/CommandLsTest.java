package Commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static Commands.FileSystemUtility.setCurrentWorkingDirectory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by semionn on 04.10.2016.
 */
public class CommandLsTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    CommandLs commandLs = new CommandLs();

    @Test
    public void testGetName() {
        assertEquals("ls", commandLs.getName());
    }

    @Test
    public void testRun() throws IOException {
        final String fileName = "temp.txt";
        File file = folder.newFile(fileName);
        List<String> lines = Arrays.asList("some line 1", "some line 2");
        Files.write(file.toPath(), lines);
        setCurrentWorkingDirectory(folder.getRoot().toString());

        Feature feature = new Feature();
        feature = commandLs.run(feature, null);
        assertEquals(1, feature.getResults().size());
        assertEquals(fileName, feature.getResults().get(0));
        assertTrue(feature.getErrors().isEmpty());
    }
}