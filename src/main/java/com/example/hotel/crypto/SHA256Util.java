package com.example.hotel.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SHA256Util {
    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 32;
    private static final int ITERATIONS = 100000;

    /**
     * Encripta una contraseña en texto plano.
     *
     * @param password Contraseña en texto plano
     * @return Hash seguro de la contraseña con salt incluido
     */
    public String encryptPassword(String password) {
        try {
            // Generar un salt aleatorio seguro
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            // Derivar la clave usando múltiples iteraciones
            byte[] hash = hashPassword(password, salt, ITERATIONS);

            // Combinar salt + hash para almacenar
            byte[] saltAndHash = new byte[salt.length + hash.length];
            System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
            System.arraycopy(hash, 0, saltAndHash, salt.length, hash.length);

            // Codificar en Base64 para almacenamiento
            return Base64.getEncoder().encodeToString(saltAndHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar contraseña: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica si una contraseña en texto plano coincide con el hash almacenado.
     *
     * @param password Contraseña en texto plano a verificar
     * @param encryptedPassword Hash almacenado en la base de datos
     * @return true si coinciden, false en caso contrario
     */
    public boolean verifyPassword(String password, String encryptedPassword) {
        try {
            // Decodificar el hash almacenado
            byte[] saltAndHash = Base64.getDecoder().decode(encryptedPassword);

            // Extraer el salt (primeros 32 bytes)
            byte[] salt = new byte[SALT_LENGTH];
            System.arraycopy(saltAndHash, 0, salt, 0, SALT_LENGTH);

            // Extraer el hash original
            byte[] storedHash = new byte[saltAndHash.length - SALT_LENGTH];
            System.arraycopy(saltAndHash, SALT_LENGTH, storedHash, 0, storedHash.length);

            // Hashear la contraseña ingresada con el mismo salt e iteraciones
            byte[] computedHash = hashPassword(password, salt, ITERATIONS);

            // Comparar los hashes de forma segura
            return constantTimeEquals(computedHash, storedHash);
        } catch (IllegalArgumentException | NoSuchAlgorithmException e) {
            // Si hay error en decodificación o procesamiento, retornar false
            return false;
        }
    }

    /**
     * Realiza el hash de una contraseña con un salt específico usando iteraciones.
     */
    private byte[] hashPassword(String password, byte[] salt, int iterations)
            throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM);

        // Realizar múltiples iteraciones para aumentar la complejidad
        byte[] result = password.getBytes();
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            digest.update(salt);
            result = digest.digest(result);
        }
        return result;
    }

    /**
     * Compara dos arrays de bytes en tiempo constante para evitar timing attacks.
     */
    private boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
}
