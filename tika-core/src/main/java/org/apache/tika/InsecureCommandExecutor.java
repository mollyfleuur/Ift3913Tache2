
package org.apache.tika;

import java.io.IOException;

public class InsecureCommandExecutor {

    // VULNERABILITE 2 : injection de commande
    // Une entree utilisateur non fiable est executee par le shell.
    public static Process execute(String userInput)
            throws IOException {

        return new ProcessBuilder(
                "sh",
                "-c",
                "echo " + userInput
        ).start();
    }
}
