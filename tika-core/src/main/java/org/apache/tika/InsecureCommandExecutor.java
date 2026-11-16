
package org.apache.tika;

import java.io.IOException;

public class InsecureCommandExecutor {

    // VULNERABILITE 2 : commande construite
    // a partir d'une entree non fiable
    public static Process execute(String userInput)
            throws IOException {

        String command = "sh -c " + userInput;

        return Runtime.getRuntime().exec(command);
    }
}
