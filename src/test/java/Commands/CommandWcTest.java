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

import static org.junit.Assert.*;

/**
 * Created by kostya on 24.09.2016.
 */
public class CommandWcTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    CommandWc commandWc = new CommandWc();

    @Test
    public void testGetName() {
        assertEquals("wc", commandWc.getName());
    }

    @Test
    public void testRunWithCorrectArgs() throws IOException {
        final String fileName = "test.txt";
        File file = folder.newFile(fileName);
        List<String> lines = Arrays.asList("some line 1", "some line 2");
        Files.write(file.toPath(), lines);
        Feature feature = new Feature();
        feature.setArgs(Collections.singletonList(file.getPath()));
        feature = commandWc.run(feature, null);
        assertEquals(0, feature.getErrors().size());
        assertEquals(1, feature.getResults().size());
        assertEquals("6 2 22 " + file.getPath(), feature.getResults().get(0));
    }

    @Test
    public void testRunWithWrongArgs() {
        Feature feature = new Feature();
        feature.setArgs(Collections.singletonList("test.txt"));
        feature = commandWc.run(feature, null);
        assertEquals(1, feature.getErrors().size());
        assertEquals(0, feature.getResults().size());
    }
}