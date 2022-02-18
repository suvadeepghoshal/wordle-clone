package io.iamsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            menu();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void menu() {
        // TODO: Create default display method
    }
}
