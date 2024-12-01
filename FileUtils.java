import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtils {

    /**
     * Calcule le hash SHA-256 d'un fichier.
     *
     * @param file Fichier dont on veut calculer le hash
     * @return Hash du fichier sous forme de chaîne hexadécimale
     */
    public static String calculateFileHash(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        StringBuilder hashString = new StringBuilder();
        for (byte b : digest.digest()) {
            hashString.append(String.format("%02x", b));
        }
        return hashString.toString();
    }
}
