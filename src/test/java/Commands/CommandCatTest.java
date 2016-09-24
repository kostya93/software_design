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

import static org.junit.Assert.assertEquals;

/**
 * Created by kostya on 24.09.2016.
 */
public class CommandCatTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    CommandCat commandCat = new CommandCat();

    @Test
    public void testGetName() {
        assertEquals(commandCat.getName(), "cat");
    }

    @Test
    public void testRunWithCorrectArgs() throws IOException {
        final String fileName = "temp.txt";
        File file = folder.newFile(fileName);

        List<String> lines = Arrays.asList("some line 1", "some line 2");

        Files.write(file.toPath(), lines);

        Feature feature = new Feature();
        feature.setArgs(Collections.singletonList(file.getPath()));
        feature = commandCat.run(feature, null);
        assertEquals(lines, feature.getResults());
        assertEquals(0, feature.getErrors().size());
    }

    @Test
    public void testRunWithWrongArgs() {
        Feature feature = new Feature();
        feature.setArgs(Collections.singletonList("temp.txt"));
        feature = commandCat.run(feature, null);
        assertEquals(0, feature.getResults().size());
        assertEquals(1, feature.getErrors().size());
    }
}