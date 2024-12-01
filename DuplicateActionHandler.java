import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

public class DuplicateActionHandler {

    /**
     * Affiche les doublons détectés.
     *
     * @param duplicates Map contenant les doublons
     */
    public static void displayDuplicates(Map<String, List<File>> duplicates) {
        System.out.println("Fichiers en double détectés :");
        int groupIndex = 1;
        for (List<File> group : duplicates.values()) {
            System.out.println("Groupe " + groupIndex + ":");
            for (File file : group) {
                System.out.println(" - " + file.getAbsolutePath());
            }
            System.out.println("-----");
            groupIndex++;
        }
    }

    /**
     * Supprime tous les doublons sauf un fichier par groupe.
     *
     * @param duplicates Map contenant les doublons groupés par hash
     */
    public static void deleteDuplicates(Map<String, List<File>> duplicates) {
        for (List<File> group : duplicates.values()) {
            for (int i = 1; i < group.size(); i++) {
                File file = group.get(i);
                if (file.delete()) {
                    System.out.println("Supprimé : " + file.getAbsolutePath());
                } else {
                    System.out.println("Impossible de supprimer : " + file.getAbsolutePath());
                }
            }
        }
    }

    /**
     * Déplace les doublons dans un dossier spécifique.
     *
     * @param duplicates     Map contenant les doublons groupés par hash
     * @param destinationDir Chemin du dossier de destination
     */
    public static void moveDuplicates(Map<String, List<File>> duplicates, String destinationDir) {
        File destinationFolder = new File(destinationDir);
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        for (List<File> group : duplicates.values()) {
            for (int i = 1; i < group.size(); i++) {
                File file = group.get(i);
                File destinationFile = new File(destinationFolder, file.getName());
                try {
                    Files.move(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Déplacé : " + file.getAbsolutePath() + " -> " + destinationFile.getAbsolutePath());
                } catch (IOException e) {
                    System.out.println("Impossible de déplacer : " + file.getAbsolutePath());
                }
            }
        }
    }
}
