package thema2;

/**
 * @author Stamatis Chatzichristodoulou <breezer77@gmail.com>
 * 
 */

import java.io.*;
import java.util.*;

public class SimpsonsScript {
    
    //Δηλώσεις νημάτων και αρχείου
    private static final int THREAD_COUNT = 8;      //number of threads
    private static final int MIN_WORD_LENGHT = 5;  //Common word lenght
    // private static final String FILE_NAME = "../Thema2/src/thema2/simpsons_script_lines.csv";
	private static final String FILE_NAME = "simpsons_script_lines.csv";
    
    //Φόρτωση δεδομένων από αρχείο
    static List<String> loadDataFromFile() {
        System.out.println("Loading... " + FILE_NAME);
        List<String> lines = new ArrayList<>();   //Δημιουργία της λίστας στην μνήμη για αποθήκευση του αρχείου
        try {   //ανάγνωση αρχείου
            try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                lines.add(inputLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    
 
    
    static class ProcessThread extends Thread {
        private final List<String> lines;
        private final HashMap<Integer, Integer> mostWordEpisode = new HashMap<>(); //Το επεισόδειο με τις περισσότερες λέξεις
        private final HashMap<Integer, Integer> MostTalkingLocation = new HashMap<>();  //Η τοποθεσία με τις περισσότερες στοιχομηθείες 
        private final HashMap<Integer, String> Locations = new HashMap<>();             //Οι λίστα με τις τοποθεσίες
        private final    HashMap<String, Integer> CommonWordPerChar1 = new HashMap<>(); //Η συχνότερη ατάκα του χαρακτήρα ID=1
        private final    HashMap<String, Integer> CommonWordPerChar2 = new HashMap<>(); //Η συχνότερη ατάκα του χαρακτήρα ID=2
        private final    HashMap<String, Integer> CommonWordPerChar8 = new HashMap<>(); //Η συχνότερη ατάκα του χαρακτήρα ID=8
        private final    HashMap<String, Integer> CommonWordPerChar9 = new HashMap<>(); //Η συχνότερη ατάκα του χαρακτήρα ID=9
        
        public ProcessThread(List<String> lines) {
            this.lines = lines;
            System.out.println(this.getName() + " calculating " + lines.size() + " script lines");
        }

        @Override
        public void run() {
            int i=0;
            for (String line : lines) {   //χωρίζουμε την γραμμογράφηση του αρχείου σε στήλες
                String[] columns = line.split(",");

                //αν υπάρχει λάθος στον αριθμό στηλών επιλέγουμε να την απορρίψουμε
                //αν δεν έχει γραμμή σεναρίου την απορρίπτουμε
                if ((columns.length != 9) || (columns[7].length()<2)) {
                    continue;
                }
                
                String ataka = columns[7]; //Αναγνωρίζουμε το κείμενο
                String locationText=columns[6];
                int episode=0; //Αναγνωρίζουμε το επεισόδιο
                int words=0;  //Αναγνωρίζουμε τον αριθμό λέξεων
                int location=0; //Αναγνωρίζουμε την εμφάνιση της τοποθεσίας

                int character=0; //Αναγνωρίζουμε τον χαρακτήρα

                // δοκιμάζουμε τα raw δεδομένα 
                try {
                    episode = Integer.parseInt(columns[1]);
                    words = Integer.parseInt(columns[8]);
                    location = Integer.parseInt(columns[4]);
                    character = Integer.parseInt(columns[3]);
                    
                } catch (NumberFormatException e) {
                    //System.out.println("Εσφαλμένη γραμμή "+line); //Εμφάνιση προβληματικών γραμμών
                    i++;   //Μετρητής προβληματικών γραμμών
                    continue;
                }
                
                  
               processEpisode(episode, words);  //Ερώτημα 1
               processLoc(location,locationText); //Ερώτημα 2
               if(character==1 || character==2 || character==8 || character==9);{ 
                processCommonWord(character,ataka.toLowerCase());  //Ερώτημα 3
            }
            
            }
            
            System.out.println("Lines with faults : "+i);
        }

        public HashMap<Integer, Integer> getMostWordEpisode() {
            return mostWordEpisode;
        }

        public HashMap<Integer, Integer> getProcessLoc() {
            return MostTalkingLocation;
        }
        
        public HashMap<Integer, String> getLoc() {
            return Locations;
        }
        
        public HashMap<String, Integer> getWord1() {
            return CommonWordPerChar1;
        }
        public HashMap<String, Integer> getWord2() {
            return CommonWordPerChar2;
        }
        public HashMap<String, Integer> getWord8() {
            return CommonWordPerChar8;
        }
        public HashMap<String, Integer> getWord9() {
            return CommonWordPerChar9;
        }



        private void processEpisode(int episode, int words) {
            int wordsSum = words;
            if (mostWordEpisode.containsKey(episode)) {
                wordsSum += mostWordEpisode.get(episode);
            }
            mostWordEpisode.put(episode, wordsSum);  //Η μοναδικότητα του επισοδείου ορίζεται από την δομή δεδομένων
        }

        private void processLoc(int location, String locationT) {
            int locationSum = 1;
            if (MostTalkingLocation.containsKey(location)) {
                locationSum += MostTalkingLocation.get(location);
            }
            MostTalkingLocation.put(location, locationSum);
            Locations.put(location, locationT);   //Η μοναδικότητα της τοποθεσίας ορίζεται από την δομή δεδομένων
            }

        private void processCommonWord(int character, String ataka) {
            String[] tokens = ataka.split(" ");  //Χωρίζουμε τις λέξεις
            for (String token : tokens) {
                String word=token.toLowerCase();   //Την μετράμε όλες με πεζά
                if (word.length()>MIN_WORD_LENGHT-1) //Το ελάχιστο μήκος λέξης που μετράμε
                switch(character) {
                case 1:
                    if(CommonWordPerChar1.containsKey(word)) { //Για τον χαρακτήρα 1
                        int count=CommonWordPerChar1.get(word);
                        CommonWordPerChar1.put(word,count+1);
                    }
                    else CommonWordPerChar1.put(word,1);
                    break;
                case 2:
                    if(CommonWordPerChar2.containsKey(word)) { //Για τον χαρακτήρα 2
                        int count=CommonWordPerChar2.get(word);
                        CommonWordPerChar2.put(word,count+1);
                    }
                    else CommonWordPerChar2.put(word,1);
                    break;
                case 8:
                    if(CommonWordPerChar8.containsKey(word)) { //Για τον χαρακτήρα ID=8
                        int count=CommonWordPerChar8.get(word);
                        CommonWordPerChar8.put(word,count+1);
                    }
                    else CommonWordPerChar8.put(word,1);
                    break;
                case 9:
                    if(CommonWordPerChar9.containsKey(word)) { //Για τον χαρακτήρα ID=9
                        int count=CommonWordPerChar9.get(word);
                        CommonWordPerChar9.put(word,count+1);
                    }
                    else CommonWordPerChar9.put(word,1);
                    break;                    
                }
            }
        }
    }
    
public static void main(String[] args) {
        double startTime=System.currentTimeMillis();
        
        List<String> lines = loadDataFromFile();
        System.out.println("Loading " + lines.size() + " lines");
        
        String headers = lines.remove(0); //αφαίρεση επικεφαλίδων
        String[] columns = headers.split(",");
        System.out.println("The file data have the following columns:");
        for (int i = 0; i < columns.length; i++) {
            System.out.println(i + ") " + columns[i]);
        }

        // Στην περίπτωση που δεν υπάρχουν δεδομένα
        if (lines.isEmpty()) {
            System.err.println("Without data...");
            System.exit(1);
        }
        
        
        ProcessThread[] threads = new ProcessThread[THREAD_COUNT];
        // In this case we may have division with remainder, therefore we should address this later on.
        int batchSize = lines.size() / THREAD_COUNT;
        System.out.println("\nCalculating in pieces of " + batchSize + " lines");
        for (int i = 0; i < threads.length; i++) {
            int start = i * batchSize;
            int end = (i + 1) * batchSize;

            // Check whether the last batch should be extended to process the lines left (case of division with remainder).
            if (i == threads.length - 1 && end < lines.size()) {
                System.out.println("ending from " + end + " until " + lines.size());
                end = lines.size();
            }
            System.out.println("Batch ["+start+","+end+"]");

            // Pass the appropriate sublist to the thread for processing.
            threads[i] = new ProcessThread(lines.subList(start, end));
            threads[i].start();
        }

        for (ProcessThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // The approach that we follow here, is that we use different maps per thread and we combine the results at the end.
        // Another solution would be to us thread safe maps such as java.util.concurrent.ConcurrentHashMap
        HashMap<Integer, Integer> mostWordEpisode = new HashMap<>(); //Το επεισόδειο με τις περισσότερες λέξεις
        HashMap<Integer, Integer> MostTalkingLocation = new HashMap<>();  //Η τοποθεσία με τις περισσότερες στοιχομηθείες 
        HashMap<Integer, String> Locations = new HashMap<>();             //Οι λίστα με τις τοποθεσίες        
        HashMap<String, Integer> CommonWordPerChar1 = new HashMap<>();  //Η συχνότερη ατάκα του χαρακτήρα ID=1
        HashMap<String, Integer> CommonWordPerChar2 = new HashMap<>();  //Η συχνότερη ατάκα του χαρακτήρα ID=2
        HashMap<String, Integer> CommonWordPerChar8 = new HashMap<>();  //Η συχνότερη ατάκα του χαρακτήρα ID=8
        HashMap<String, Integer> CommonWordPerChar9 = new HashMap<>();  //Η συχνότερη ατάκα του χαρακτήρα ID=9        
        // Merge the results from the different threads
        for (ProcessThread thread : threads) {
            // Merge mostWordEpisode
            thread.getMostWordEpisode().forEach((episode, words) -> {
                int wordsSum = words;
                if (mostWordEpisode.containsKey(episode)) {
                    wordsSum += mostWordEpisode.get(episode);
                }
                mostWordEpisode.put(episode, wordsSum);
            });
            
            
            // Merge MostTalkingLocation
            thread.getProcessLoc().forEach((location,sum) -> {
                int sumLoc = sum;
                if (MostTalkingLocation.containsKey(location)) {
                    sumLoc += MostTalkingLocation.get(location);
                }
                MostTalkingLocation.put(location, sumLoc);
            });
            
            // Merge Locations
            thread.getLoc().forEach((location,text) -> {
                Locations.put(location, text);
            });
            
            thread.getWord1().forEach((word,sum) -> {
                int sumWord = sum;
                if (CommonWordPerChar1.containsKey(word)) {
                    sumWord += CommonWordPerChar1.get(word);
                }
                CommonWordPerChar1.put(word, sumWord);
            });
            
             thread.getWord2().forEach((word,sum) -> {
                int sumWord = sum;
                if (CommonWordPerChar2.containsKey(word)) {
                    sumWord += CommonWordPerChar2.get(word);
                }
                CommonWordPerChar2.put(word, sumWord);
            });
             
              thread.getWord8().forEach((word,sum) -> {
                int sumWord = sum;
                if (CommonWordPerChar8.containsKey(word)) {
                    sumWord += CommonWordPerChar8.get(word);
                }
                CommonWordPerChar8.put(word, sumWord);
            });            
            
             thread.getWord9().forEach((word,sum) -> {
                int sumWord = sum;
                if (CommonWordPerChar9.containsKey(word)) {
                    sumWord += CommonWordPerChar9.get(word);
                }
                CommonWordPerChar9.put(word, sumWord);
            });            
        }
            
        System.out.println("\n----------------- Results -----------------");

        int max = Collections.max(mostWordEpisode.values());
        for (Map.Entry<Integer, Integer> entry : mostWordEpisode.entrySet()) {
            if (entry.getValue()==max) {
                System.out.println("Episode "+entry.getKey()+" is with the most words ="+entry.getValue());
            }
        }
        
        max = Collections.max(MostTalkingLocation.values());
        for (Map.Entry<Integer, Integer> entry : MostTalkingLocation.entrySet()) {
            if (entry.getValue()==max) {
                System.out.println("Location "+entry.getKey()+" has the longest dialogues, and is the "+Locations.get(entry.getKey())+"="+entry.getValue());
            }
        }

        System.out.println("\n-------------- Favorite words with "+MIN_WORD_LENGHT+" letters and up -----------");        
        max = Collections.max(CommonWordPerChar1.values());
        for (Map.Entry<String, Integer> entry : CommonWordPerChar1.entrySet()) {
            if (entry.getValue()==max) {
                System.out.println("Margie Simpson ID=1 love the word "+entry.getKey()+" and has mention it "+CommonWordPerChar1.get(entry.getKey())+" times="+entry.getValue());
            }
        }
        
        max = Collections.max(CommonWordPerChar2.values());
        for (Map.Entry<String, Integer> entry : CommonWordPerChar2.entrySet()) {
            if (entry.getValue()==max) {
                System.out.println("Homer Simpson ID=2 love the word "+entry.getKey()+" and has mention it "+CommonWordPerChar2.get(entry.getKey())+" times="+entry.getValue());
            }
        }
        
        max = Collections.max(CommonWordPerChar8.values());
        for (Map.Entry<String, Integer> entry : CommonWordPerChar8.entrySet()) {
            if (entry.getValue()==max) {
                System.out.println("Bart Simpson ID=8 love the word "+entry.getKey()+" and has mention it "+CommonWordPerChar8.get(entry.getKey())+" times="+entry.getValue());
            }
        }
        
        max = Collections.max(CommonWordPerChar9.values());
        for (Map.Entry<String, Integer> entry : CommonWordPerChar9.entrySet()) {
            if (entry.getValue()==max) {
                System.out.println("Lisa Simpson ID=9 love the word "+entry.getKey()+" and has mention it "+CommonWordPerChar9.get(entry.getKey())+" times="+entry.getValue());
            }
        }
        double endTime=System.currentTimeMillis()-startTime;
        System.out.println(">>>>> Total calculation time for "+THREAD_COUNT+" threads: "+endTime);
}}