
package org.apache.tika;

import java.io.IOException;
import java.net.Socket;

public class SemgrepValidation {

    // TEST TEMPORAIRE : connexion réseau non chiffrée
    public static void testInsecureConnection()
            throws IOException {

        try (Socket socket = new Socket("localhost", 8080)) {
            System.out.println(socket.isConnected());
        }
    }
}
