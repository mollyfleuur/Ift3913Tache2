
package org.apache.tika;

import java.io.IOException;
import java.net.Socket;

public class InsecureNetworkClient {

    // VULNERABILITE 1 : connexion reseau sans TLS
    public static void connect(String host, int port)
            throws IOException {

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected: " + socket.isConnected());
        }
    }
}
