package io.iamsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

/**
 * @author SG
 */
public class App {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            menu();
            for (int i = 0; i < 6; ++i) {
                System.out.println(String.format("Enter %s%s turn", i + 1, pos(i + 1)));
                turn(br);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static String pos(int i) {
        if (i == 1) {
            return "st";
        } else if (i == 2) {
            return "nd";
        } else if (i == 3) {
            return "rd";
        }
        return "th";
    }

    private static void turn(BufferedReader br) throws IOException {
        String word = null;
        int count = 0;
        do {
            if (count > 0) {
                System.out.println("Enter a five letter english word");
            }
            word = br.readLine();
            count++;
        } while (word.length() != 5 || !alphabetsOnly(word));
        System.out.println(word);
    }

    private static boolean alphabetsOnly(String word) {
        if (word == null || word == "" || StringUtils.isBlank(word)) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            if (!Character.isLetter(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static void menu() {
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
