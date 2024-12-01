import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.Files;

public class FileAnalyzer {

    /**
     * Trouve les fichiers en double dans un dossier et ses sous-dossiers.
     *
     * @param directory Dossier à analyser
     * @return Map où chaque clé est un hash et chaque valeur est une liste de fichiers identiques
     */
    public static Map<String, List<File>> findDuplicates(File directory) throws IOException, NoSuchAlgorithmException {
        Map<String, List<File>> hashToFileMap = new HashMap<>();

        Files.walk(directory.toPath())
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        File file = path.toFile();
                        String fileHash = FileUtils.calculateFileHash(file);
                        hashToFileMap.computeIfAbsent(fileHash, k -> new ArrayList<>()).add(file);
                    } catch (Exception e) {
                        System.out.println("Erreur lors du traitement du fichier : " + path);
                    }
                });

        return hashToFileMap.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
