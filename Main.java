import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("==== Détecteur de fichiers en double ====");

            // Demander le chemin du dossier
            System.out.println("Veuillez entrer le chemin du dossier à analyser :");
            String directoryPath = scanner.nextLine();
            File rootDirectory = new File(directoryPath);

            // Vérifier si le chemin est un dossier valide
            if (!rootDirectory.isDirectory()) {
                System.out.println("Le chemin spécifié n'est pas un dossier valide !");
                return;
            }

            // Trouver les fichiers en double
            Map<String, List<File>> duplicates = FileAnalyzer.findDuplicates(rootDirectory);

            // Afficher les doublons trouvés
            if (duplicates.isEmpty()) {
                System.out.println("Aucun fichier en double trouvé !");
            } else {
                DuplicateActionHandler.displayDuplicates(duplicates);

                // Proposer des actions à l'utilisateur
                System.out.println("Que souhaitez-vous faire avec les doublons ?");
                System.out.println("1. Supprimer tous les doublons sauf un par groupe.");
                System.out.println("2. Déplacer les doublons dans un dossier spécifique.");
                System.out.println("3. Quitter sans action.");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consommer la ligne restante

                switch (choice) {
                    case 1:
                        DuplicateActionHandler.deleteDuplicates(duplicates);
                        System.out.println("Les doublons ont été supprimés !");
                        break;
                    case 2:
                        System.out.println("Veuillez entrer le chemin du dossier de destination :");
                        String destinationPath = scanner.nextLine();
                        DuplicateActionHandler.moveDuplicates(duplicates, destinationPath);
                        System.out.println("Les doublons ont été déplacés !");
                        break;
                    case 3:
                        System.out.println("Aucune action appliquée.");
                        break;
                    default:
                        System.out.println("Option non valide !");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
