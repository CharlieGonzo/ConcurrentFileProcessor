package org.example;


import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    static ConcurrentHashMap<String,Integer> wordCount=new ConcurrentHashMap<>();
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);

        Runnable readFileOne = new Runnable() {
            public void run() {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader("src/main/java/org/example/Submission.txt"));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                String line;
                while(true){
                    try {
                        if (!((line = reader.readLine()) != null)) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String[] words = line.split(" ");
                    for (String word : words) {

                        wordCount.compute(word, (key, value) -> (value == null) ? 1 : value + 1);
                    }
                }


            }
        };

        Runnable readFileTwo = new Runnable() {
            public void run() {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader("src/main/java/org/example/dummy_real_words_file.txt"));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                String line;
                while(true){
                    try {
                        if (!((line = reader.readLine()) != null)) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String[] words = line.split(" ");
                    for (String word : words) {
                        wordCount.compute(word, (key, value) -> (value == null) ? 1 : value + 1);;
                    }
                }


            }
        };

        Runnable readFileThree = new Runnable() {
            public void run() {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader("src/main/java/org/example/example.txt"));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                String line;
                while(true){
                    try {
                        if (!((line = reader.readLine()) != null)) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String[] words = line.split(" ");
                    for (String word : words) {

                        wordCount.compute(word, (key, value) -> (value == null) ? 1 : value + 1);

                    }
                }


            }
        };
        service.execute(readFileOne);
        service.execute(readFileTwo);
        service.execute(readFileThree);
        // Shut down the executor and wait for tasks to finish
        service.shutdown(); // No new tasks will be accepted
        service.awaitTermination(1, TimeUnit.MINUTES);
        Collections.sort(wordCount.);



    }
}