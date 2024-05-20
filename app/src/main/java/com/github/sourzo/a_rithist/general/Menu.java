package com.github.sourzo.a_rithist.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

    /**Displays the input string array as a numeric list of options, and asks the user to pick one of the options.
     * @param displayText {@code String[]} The options to be displayed to the user
     * @return {@code int} The index of the selected option*/
    public static int getOptionNumber(String[] displayText) throws IOException {
        return getOptionNumber(Arrays.asList(displayText));

    }
    /**Displays the input string array as a numeric list of options, and asks the user to pick one of the options.
     * @param displayText {@code String[]} The options to be displayed to the user
     * @return {@code int} The index of the selected option*/
    public static int getOptionNumber(List<String> displayText) throws IOException {
        //Get selection numbers as a string
        ArrayList<String> selectionValues = new ArrayList<String>();
        for (int n = 0; n < displayText.size(); n++)
        {
            selectionValues.add(String.valueOf(n));
        }

        //User input loop:
        //Display the menu options and request valid input
        String userInput = "";
        System.out.println("Please select one of the following options:");
        System.out.println();
        while (selectionValues.contains(userInput) == false)
        {
            for (int i = 1; i <= displayText.size(); i++)
            {
                System.out.print(i + ": ");
                System.out.println(displayText.get(i));
            }
            System.out.println();

            //Request user input - choose a menu item
            System.out.print("> ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            userInput = reader.readLine().trim().toUpperCase();

            if (selectionValues.contains(userInput) == false) {
                System.out.println();
                System.out.println("That is not one of the options.");
                System.out.println("Type in the number of your choice from the list below:");
                System.out.println();
            }
        }

        return Integer.valueOf(userInput)-1;
    }

    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**Ask the user for an integer*/
    public static int getInteger(int max, String question) throws IOException {
        //User input loop:
        //Display the menu options and request valid input
        String userInput = "";
        if (question == null) {
            System.out.println("How many words do you want to practice? (Max " + max + ")");
        } else {
            System.out.println(question);
        }
        System.out.println();
        while (!isInteger(userInput) || Integer.valueOf(userInput) > max || Integer.valueOf(userInput) < 1)
        {
            //Request user input - choose a menu item
            System.out.print("> ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            userInput = reader.readLine().trim();

            if (!isInteger(userInput)) {
                System.out.println();
                System.out.println("Please choose a number between 1 and " + max + ".");
                System.out.println();
            }
        }

        return Integer.valueOf(userInput);
    }
}
