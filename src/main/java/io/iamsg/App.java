package io.iamsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.apache.commons.lang3.StringUtils;

/**
 * @author SG
 */
public class App {

    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String URL = "https://api.datamuse.com/words";
    private static final String MEANS_LIKE = "do not surrender";
    private static String wordle = null;
    private static final boolean log = true; // make false for prod

    static {
        final long start = System.currentTimeMillis();
        wordle = getWordle();
        final long end = System.currentTimeMillis();
        if (log) {
            System.out.println("LOG :: computing time : " + (end - start) / 1000 + " seconds");
            System.out.println("LOG :: Wordle generated is: " + wordle);
        }
    }

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

    private static String getWordle() {
        HttpResponse<JsonNode> response = null;
        List<Word> findings = null;
        String theWord = null;
        try {
            do {
                response = Unirest.get(URL + "?ml=" + getMeansLike(MEANS_LIKE)).asJson();
            } while (response.getStatus() != 200);
            // to prevent from ----- org.json.JSONException ---------
            if (response.getStatus() == 200 && response.getBody() != null) {
                findings = getFromResponse(response.getBody().toString());
                findings.forEach(System.out::println);
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                int index = 0;
                do {
                    index = random.nextInt(findings.size());
                } while (findings.get(index).getWord().length() != 5);
                theWord = findings.get(index).getWord(); // we got our wordle - a five letter word
            }
        } catch (UnirestException e) {
            System.err.println(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }
        return theWord != null ? theWord : null;
    }

    private static List<Word> getFromResponse(String data) {
        Gson gson = new Gson();
        List<Word> list = null;
        Word word = null;
        if (log) {
            System.out.println("LOG :: From response as JSON ---->");
            System.out.println(data);
        }
        JsonArray findings = gson.fromJson(data, JsonArray.class);
        list = new ArrayList<Word>();
        for (Object f : findings) {
            word = new Word();
            JsonObject finding = (JsonObject) f;
            word.setScore(finding.get("score").getAsString());
            word.setWord(finding.get("word").getAsString());
            // JsonArray tags = finding.get("tags").getAsJsonArray();
            // if (tags != null) {
            // List<Object> tagList = new ArrayList<Object>();
            // for (Object t : tags) {
            // tagList.add(t);
            // }
            // word.setTags(tagList);
            // }
            list.add(word);
        }
        return list;
    }

    private static String getMeansLike(String meansLike) {
        return meansLike.replaceAll(" ", "+");
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
        System.out.println("!!! Welcome to WORDICTED !!!");
        System.out.println("Guess the WORDLE WORDLE in six tries");
        System.out.println(
                "After each try the color of the letter will change to show how close your guess was to the word");
        System.out.println(
                "***************************************************************************************************");
        System.out.println("__RULES__");
        System.out.println(">> Each word has to be a valid five letter english word");
        System.out.println(
                ">> If the letter is present in the WORDLE and postion of the letter is not correct then letter will turn "
                        + ANSI_YELLOW + "YELLOW" + ANSI_RESET);
        System.out.println(
                ">> If the letter and the position of the letter matches with that of the WORDLE then the letter will turn "
                        + ANSI_GREEN + "GREEN" + ANSI_RESET);
        System.out.println(">> If the letter is not present in the WORDLE then it will remain without colour");
        System.out.println();
    }
}
