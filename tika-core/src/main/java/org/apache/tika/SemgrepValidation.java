
package org.apache.tika;

import java.util.Random;

public class SemgrepValidation {

    // TEST TEMPORAIRE : génération d'un jeton de sécurité
    // avec un générateur pseudo-aléatoire non cryptographique.
    public static String generateSecurityToken() {
        Random random = new Random();
        return Long.toHexString(random.nextLong());
    }
}
