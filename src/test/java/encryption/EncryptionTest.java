package encryption;

import junitx.framework.FileAssert;
import org.junit.Test;

import java.io.File;

public class EncryptionTest {

   @Test
    public void performTest() throws Exception {

        final String KEY = "rty";

        File inFile = new File("src\\test\\java\\encryption\\test_files\\" +
                "testTextExpected.txt");
        File encryptionFile = new File("src\\test\\java\\encryption\\test_files\\" +
                "testEncryption.txt");
        File decryptionFile = new File("src\\test\\java\\encryption\\test_files\\" +
                "testDecryption.txt");

        Encryption encryption = new Encryption(inFile);
        encryptionFile.delete();
        encryption.setOutFile(encryptionFile);
        encryption.perform(KEY, true);

        Encryption decryption = new Encryption(encryptionFile);
        decryptionFile.delete();
        decryption.setOutFile(decryptionFile);
        decryption.perform(KEY, false);

        FileAssert.assertEquals(inFile, decryptionFile);
   }
}