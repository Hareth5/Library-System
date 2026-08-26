package library;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class ApplicationFiles {
    private ApplicationFiles() {
    }

    public static Path dataDirectory() throws IOException {
        Path directory = Path.of(System.getProperty("user.dir"), "data").toAbsolutePath().normalize();
        Files.createDirectories(directory);
        return directory;
    }

    public static Path updatedBooksFile() throws IOException {
        return dataDirectory().resolve("updatedBooks.txt");
    }

    public static Path sampleBooksFile() throws IOException {
        Path sample = dataDirectory().resolve("books.txt");
        if (Files.notExists(sample)) {
            try (InputStream input = ApplicationFiles.class.getResourceAsStream("/books.txt")) {
                if (input != null) {
                    Files.copy(input, sample, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        return sample;
    }
}
