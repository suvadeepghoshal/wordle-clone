package io.iamsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author SG
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
        System.out.println();
        System.out.println(
                "***************************************************************************************************");
        System.out.println("Welcome to WRODLE");
        System.out.println("Guess the WORDLE in six tries");
        System.out.println(
                "After each try the color of the letter will change to show how close your guess was to the word");
        System.out.println(
                "***************************************************************************************************");
        System.out.println("__RULES__");
        System.out.println(">> Each word has to be a valid five letter english word");
        System.out.println(
                ">> If the letter is present in the WORDLE and postion of the letter is not correct then letter will turn YELLOW");
        System.out.println(
                ">> If the letter and the position of the letter matches with that of the WORDLE then the letter will turn GREEN");
        System.out.println(">> If the letter is not present in the WORDLE then it will remain without colour");
        System.out.println();
    }
}
