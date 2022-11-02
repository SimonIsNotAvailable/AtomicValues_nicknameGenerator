import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger threeChars = new AtomicInteger(0);
    public static AtomicInteger fourChars = new AtomicInteger(0);
    public static AtomicInteger fiveChars = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {


        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread secondThread = new Thread(() -> {
            for (String text : texts) {
                if (findPalindrome(text)) {
                    setCharsCounter(text.length());
                }
            }
        });
        secondThread.start();



       Thread firstThread =  new Thread(() -> {
            for (String text : texts) {
                if (findSingleLetter(text)) {
                    setCharsCounter(text.length());
                }
            }
        });
       firstThread.start();

        Thread thirdThread = new Thread(() -> {
            for (String text : texts) {
                if (findSorted(text)) {
                    setCharsCounter(text.length());
                }
            }
        });
        thirdThread.start();

        firstThread.join();
        secondThread.join();
        thirdThread.join();

        System.out.println("Красивых слов с длиной 3: " + threeChars );
        System.out.println("Красивых слов с длиной 4: " + fourChars );
        System.out.println("Красивых слов с длиной 5: " + fiveChars );
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean findPalindrome(String text) {
        if (!findSingleLetter(text)) {
            return text.equals(new StringBuilder(text).reverse().toString());
        }
        return false;
    }

    public static boolean findSingleLetter(String text) {
        String[] chars = text.split("");
        return text.replaceAll(chars[0], "").isEmpty();
    }

    public static boolean findSorted(String text) {
        String[] chars = text.split("");
        Arrays.sort(chars);
        if (!findSingleLetter(text)){
            return Arrays.equals(text.split(""), chars);
        }
        return false;
    }

    public static void setCharsCounter(int n) {
        switch (n) {
            case 3:
                threeChars.getAndIncrement();

            case 4:
                fourChars.getAndIncrement();

            case 5:
                fiveChars.getAndIncrement();
        }
    }
}
