package company;

import company.scanner.NetworkScanner;

public class Main {

    public static void main(String[] args) {

        get();
    }

    public static void get() {
        for (int i = 1; i <= 254; i++) {
            new NetworkScanner(i);
        }
    }
}
