package dev.ebullient.fc5;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.io.TempDir;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainLauncher;
import io.quarkus.test.junit.main.QuarkusMainTest;

@QuarkusMainTest
public class Fc5ConvertTest {
    @TempDir
    Path tempPath;

    @Test
    @Launch(args = {})
    void testThings() {
        System.out.println("Life is good");
    }

    @Test
    @Launch(args = { "--help" })
    void testCommandHelp(LaunchResult result) {
        System.out.println(result.getOutputStream().stream().collect(Collectors.joining("\n")));
        Assertions.assertTrue(result.getOutputStream().stream()
                .anyMatch(x -> x.startsWith("Usage: fc5-convert [-hvV] [<input>...] [COMMAND]")),
                "Result should contain the CLI help message. Found: " + result);
    }

    @Test
    @Launch(args = { "obsidian", "--help" })
    void testObsidianCommandHelp(LaunchResult result) {
        System.out.println(result.getOutputStream().stream().collect(Collectors.joining("\n")));
        Assertions.assertTrue(result.getOutputStream().stream()
                .anyMatch(x -> x.startsWith("Usage: fc5-convert obsidian [-hvV] -o=<outputPath> [<input>...]")),
                "Result should contain the CLI help message. Found: " + result);
    }

    @Test
    @Launch(args = { "transform", "--help" })
    void testTransformCommandHelp(LaunchResult result) {
        System.out.println(result.getOutputStream().stream().collect(Collectors.joining("\n")));
        Assertions.assertTrue(result.getOutputStream().stream()
                .anyMatch(x -> x.startsWith("Usage: fc5-convert transform [-hvV] -o=<outputPath> [-t=<xsltFile>]")),
                "Result should contain the CLI help message. Found: " + result);
    }

    @Test
    @Launch(args = { "validate", "--help" })
    void testValidateCommandHelp(LaunchResult result) {
        String stream = result.getOutputStream().stream().collect(Collectors.joining("\n"));
        System.out.println(stream);
        Assertions.assertTrue(result.getOutputStream().stream()
                .anyMatch(x -> x.startsWith("Usage: fc5-convert validate [-hvV] [--collection | [--compendium] |")),
                "Result should contain the CLI help message. Found: " + stream);
    }

    @Test
    void testFc5Xml(TestInfo info, QuarkusMainLauncher launcher) throws IOException {
        final Path targetDir = tempPath.resolve(info.getDisplayName());
        Files.createDirectories(targetDir);
        LaunchResult result;

        result = launcher.launch(
                "validate", "--collection", "./src/test/resources/FC5-Collection.xml");
        Assertions.assertEquals(0, result.exitCode(), "An error occurred. " + result);

        result = launcher.launch(
                "validate", "--compendium", "src/test/resources/FC5-Compendium.xml");
        Assertions.assertEquals(0, result.exitCode(), "An error occurred. " + result);

        result = launcher.launch(
                "transform", "-o", targetDir.toString(), "-x", "-merged", "src/test/resources/FC5-Collection.xml");
        Assertions.assertEquals(0, result.exitCode(), "An error occurred. " + result);

        result = launcher.launch(
                "validate", "--compendium", targetDir.resolve("FC5-Collection-merged.xml").toString());
        Assertions.assertEquals(0, result.exitCode(), "An error occurred. " + result);

        result = launcher.launch(
                "validate", "--compendium", targetDir.resolve("FC5-Collection-merged.xml").toString());
        Assertions.assertEquals(0, result.exitCode(), "An error occurred. " + result);

        result = launcher.launch(
                "obsidian", "-o", targetDir.toString(), targetDir.resolve("FC5-Collection-merged.xml").toString());
        Assertions.assertEquals(0, result.exitCode(), "An error occurred. " + result);
    }

    @Test
    @Launch(args = { "obsidian", "-o", "testExportedXml", "../src/test/resources/testResources.xml" })
    void testExportedXml(LaunchResult result) throws Exception {
        System.out.println(String.join("\n", result.getOutputStream()));
    }

    public static void deleteDir(Path path) {
        if (!path.toFile().exists()) {
            return;
        }

        try {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        Assertions.assertFalse(path.toFile().exists());
    }
}
