package com.juan;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ConsoleMenu menu = new ConsoleMenu(scanner);
            menu.run();
        }
    }
}
