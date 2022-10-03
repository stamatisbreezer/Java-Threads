package Question3;

import java.io.*;
import java.net.URL;
import java.util.HashMap;


public class IpsumThreadCount {
    private static final int THREAD_COUNT = 8;   //Ορίζουμε νήματα
    private static final int CALLS = 20;         //Ορίζουμε κλήσεις
    private static final String API_URL = "http://metaphorpsum.com/paragraphs/10";  

    public static void main(String[] args) {
        double startTime=System.currentTimeMillis();
        ProcessThread[] threads = new ProcessThread[THREAD_COUNT];  //Ανοιγμα νημάτων
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ProcessThread();
            threads[i].start();
        }

        for (ProcessThread thread : threads) {   
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        final HashMap<Character, Integer> letters = new HashMap<>();
            double mesosOros;
            int mikos=0;
            int lexeis=0;
            int synolo=0;   //long παρα είναι μεγάλος

        // Συγχονεύουμε τα αποτελέσματα από τα νήματα
        for (ProcessThread thread : threads) {
            
            // Merge casesPerDate
            mikos+=thread.getMikos();       //συγκεντρώνουμε τα νούμερα
            lexeis+=thread.getLexeis();
            synolo+=thread.getSynolo();
            
            thread.getLetterCount().forEach((word, count) -> {      //Στήνουμε το hashmap
                int countSum = count;
                if (letters.containsKey(word)) {
                    countSum += letters.get(word);
                }
                letters.put(word, countSum);
            });

        }
         mesosOros = (double) mikos/lexeis;     //Υπολογίζουμε τον μέσο όρο

        System.out.println("\n-------- R E S U L T S ---------");
         System.out.printf("  Average : %.2f chars",mesosOros);

        System.out.println("\n\n---------- % letters -----------");
        for(char word:letters.keySet()) 
        System.out.printf(word + ": %.2f%%\n",(double) letters.get(word)/synolo);
        
        double endTime=System.currentTimeMillis()-startTime;
        System.out.println("\n With "+THREAD_COUNT+" thread and "+CALLS+" calls. Total time is : "+endTime);
    }


    static class ProcessThread extends Thread {
        private final HashMap<Character, Integer> letters = new HashMap<>();
        private int lexeis,mikos,synolo;

        @Override
        public void run() {
            mikos=0;
            lexeis=0;
            for (int l = 0; l < CALLS; l++) {   
                String load = loadDataFromUrl();
                String data;
                String[] words = load.split(" ");                
                // Η προσέγγιση για το μέσο όρο μήκους των λέξεων. Αφενώς μετράμε πόσες λέξεις είναι και 
                // Aφαιρούμε τα σημεία στίξης ,!;-'. καθώς και το κενό οπότε και έχουμε το σύνολο του μήκους του κειμένου
                data=load.replace(",", "").replace(".", "").replace("!", "").replace(" ", "").replace(";", "").replace("'", "").replace("-", "");
                
                mikos+=load.length();
                lexeis+=words.length;
                //System.out.println(l+":Call "+mikos+"  /"+lexeis);  //debuging
                                
                //Προσέγγιση για την % αναλογία των γραμμάτων με καταγραφή τις εμφάνισης τους σε HashMap

                for (int i=0; i<data.length(); i++){
                    int count = 1;
                    if (letters.containsKey(data.charAt(i))) 
                        count += letters.get(data.charAt(i));
                    letters.put(data.charAt(i), count);
                }
            }
            
        }
        public int getSynolo(){ //Σύνολο των χαρακτήρων που βρίσκονται στο Hashmap. Θεωρητικά είναι το ίδιο με το mikos
            letters.keySet().forEach((word) -> {
                synolo+=letters.get(word);
            });
            return synolo;
        }
        
        public HashMap<Character, Integer> getLetterCount() {
            return letters;
        }
        
        public int getLexeis() {
            return lexeis;
        }
                
        public int getMikos() {
        return mikos;
        }
        
        //Λαμβάνουμε το κείμενο από το API
        private String loadDataFromUrl() {  //Κλήση ΑPI
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(API_URL);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    result.append(inputLine.toLowerCase());   //Όλα πεζά για να μην υπάρχει διπλοεγγραφή
                    //result.append(" ");
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.print(result); //debuging
            return result.toString();
        }
    }
}