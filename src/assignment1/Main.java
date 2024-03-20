package assignment1;

import java.io.*;
import java.security.NoSuchAlgorithmException;


public class Main {

    static final String HASH_ALGORITHMS = "MD5";

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        run();
    }

    private static void run() throws IOException, NoSuchAlgorithmException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int pwLength = setPwLength(br);
        int type = setType(br);

        String password = PasswordGenerator.generateRandomPw(pwLength, type);

        String unSafetyHashed = PasswordGenerator.generateUnsafetyHash(password);
        String hashed = PasswordGenerator.generateHash(password, HASH_ALGORITHMS);
        showPasswords(password, hashed, unSafetyHashed);

        Thread unsafetyThread = new Thread(new Runnable() {
            @Override
            public void run() {
//                System.out.println("\n\n---------Result: unsafety---------");
                try {
                    Cracker.crackingUsingBruteForce(unSafetyHashed, type, false);
                } catch (Exception e) {
                    e.printStackTrace();

                }
                System.out.println();
            }
        });

        Thread md5Thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                System.out.println("\n\n---------Result: MD5---------");
                try {
                    Cracker.crackingUsingBruteForce(hashed, type, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println();
            }
        });

        unsafetyThread.start();
        md5Thread.start();

        try {
            unsafetyThread.join();
            md5Thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        System.out.println("\n\n---------Result: unsafety---------");
//        Cracker.crackingUsingBruteForce(unSafetyHashed, type, false);
//        System.out.println();
//
//        System.out.println("\n\n---------Result: MD5---------");
//        Cracker.crackingUsingBruteForce(hashed, type, true);

        br.close();
    }

    private static int setType(BufferedReader br) throws IOException {
        System.out.print("Password format(1.num 2.chars 3.num+chars 4.num+char+special_char : ");
        int type = Integer.parseInt(br.readLine());
        return type;
    }

    private static int setPwLength(BufferedReader br) throws IOException {
        System.out.print("Length of user password: ");
        int pwLength = Integer.parseInt(br.readLine());
        return pwLength;
    }

    private static void showPasswords(String password, String hashed, String unSafetyHashed) {
        System.out.println("original password : " + password);
        System.out.println("unSafetyHashed password : " + unSafetyHashed);
        System.out.println("hashed password : " + hashed);
        System.out.println();
    }
}
