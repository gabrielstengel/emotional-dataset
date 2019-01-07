
import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SpeechTranscript {

    // Stores the name of the input text file
    private String fileName;

    // Total duration (in seconds) of the speech file
    private double totalDuration;

    // All clips
    private ArrayList<Clip> clips;

    // HashMap of emotions to count of number of clips
    private HashMap<String, Integer> emotions;

    // Constructor method
    public SpeechTranscript(String fileName) {


        // Declare & initialize instance variables
        this.fileName = fileName;
        clips = new ArrayList<Clip>();
        emotions = new HashMap<String, Integer>();
        
        // pass the path to the file as a parameter
        File file = new File(fileName);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        // PARSE TRANSCRIPT DATA
        sc.next();
        sc.nextLine();
        while (sc.hasNextLine()) {
            double start = sc.nextDouble();
            double end = sc.nextDouble();
            sc.next();
            String emotion =  sc.next();
            String correctEmotion;

            // process so that the emotion is correct
            if (emotion.startsWith("neut")) correctEmotion = "NEUTRAL";
            else if (emotion.startsWith("*neut")) correctEmotion = "NEUTRAL";
            else if (emotion.startsWith("dis")) correctEmotion = "DISGUST";
            else if (emotion.startsWith("pan")) correctEmotion = "PANIC";
            else if (emotion.startsWith("anx")) correctEmotion = "ANXIETY";
            else if (emotion.startsWith("hot")) correctEmotion = "HOT-ANGER";
            else if (emotion.startsWith("cold")) correctEmotion = "COLD-ANGER";
            else if (emotion.startsWith("des")) correctEmotion = "DESPAIR";
            else if (emotion.startsWith("*des")) correctEmotion = "DESPAIR";
            else if (emotion.startsWith("sad")) correctEmotion = "SADNESS";
            else if (emotion.startsWith("*sad")) correctEmotion = "SADNESS";
            else if (emotion.startsWith("ela")) correctEmotion = "ELATION";
            else if (emotion.startsWith("*ela")) correctEmotion = "ELATION";
            else if (emotion.startsWith("happ")) correctEmotion = "HAPPY";
            else if (emotion.startsWith("*happ")) correctEmotion = "HAPPY";
            else if (emotion.startsWith("inter")) correctEmotion = "INTEREST";
            else if (emotion.startsWith("bore")) correctEmotion = "BOREDOM";
            else if (emotion.startsWith("*bore")) correctEmotion = "BOREDOM";
            else if (emotion.startsWith("sham")) correctEmotion = "SHAME";
            else if (emotion.startsWith("prid")) correctEmotion = "PRIDE";
            else if (emotion.startsWith("con")) correctEmotion = "CONTEMPT";
            else if (emotion.startsWith("pass")) correctEmotion = "PASSIVE";
            else if (emotion.startsWith("dom")) correctEmotion = "DOMINANT";
            else if (emotion.startsWith("*pass")) correctEmotion = "PASSIVE";
            else if (emotion.startsWith("*dom")) correctEmotion = "DOMINANT";
            else {
                //System.out.println("MISMATCHED EMOTION: recieved (( " + emotion + ")). So not creating a clip.");
                sc.nextLine();
                sc.nextLine();
                continue;
            }

            //System.out.println(sc.nextLine());
            sc.nextLine();
            sc.nextLine();

            if (emotions.containsKey(correctEmotion)) emotions.put(correctEmotion, emotions.get(correctEmotion) + 1);
            else emotions.put(correctEmotion, 1);
            clips.add(new Clip(correctEmotion, start, end));
        }

        // count the total duration
        double totalD = 0;
        for (Clip c : clips) {
            totalD += c.getDuration();
        }

        totalDuration = totalD;
        //System.out.println(":: Length: "  + clips.size() + " | Average Duration: "  + (totalDuration/clips.size()) + " seconds | Total duration: " + totalDuration/60 + " minutes.");
        //this.printEmotions();
    }

    public void printEmotions() {
        System.out.println("Emotions count:");
        for (HashMap.Entry<String, Integer> pair: emotions.entrySet()) {
            System.out.println( pair.getKey()  + " : "  + pair.getValue());
        }
        System.out.println();
    }

    public void printClips(int howMany) {
        for (int i = 0; i < howMany; i++)
            System.out.println(clips.get(i).getEmotion() + ": (" + clips.get(i).getStart() + " - " + clips.get(i).getEnd() + ")");
    }

    public void printClips() {
        for (Clip c : clips) {
            System.out.println(c.getEmotion() + ": (" + c.getStart() + " - " + c.getEnd() + ")");
        }
    }

    public void printFileInfo() {
        System.out.print("File: " + this.getFileName());
        System.out.println(":: Length: "  + clips.size() + " | Average Duration: "  + (totalDuration/clips.size()) + " seconds | Total duration: " + totalDuration/60 + " minutes.");
    }

    public double getTotalDuration() { return totalDuration; }

    public int getNumClips() { return clips.size(); }

    public String getFileName() { return fileName; }

    public ArrayList<Clip> getClips() { return clips; }

    // Main method purely for testing and gathering data on audioFiles
    public static void main(String[] args) {
        SpeechTranscript[] transcripts = new SpeechTranscript[15];
        transcripts[0] = new SpeechTranscript("cc_001.txt");
        transcripts[1] = new SpeechTranscript("cc_002.txt");
        transcripts[2] = new SpeechTranscript("cl_001.txt");
        transcripts[3] = new SpeechTranscript("cl_002.txt");
        transcripts[4] = new SpeechTranscript("gg_001.txt");
        transcripts[5] = new SpeechTranscript("gg_002.txt");
        transcripts[6] = new SpeechTranscript("jg_001.txt");
        transcripts[7] = new SpeechTranscript("jg_002.txt");
        transcripts[8] = new SpeechTranscript("mf_001.txt");
        transcripts[9] = new SpeechTranscript("mf_002.txt");
        transcripts[10] = new SpeechTranscript("mg_000.txt");
        transcripts[11] = new SpeechTranscript("mk_001.txt");
        transcripts[12] = new SpeechTranscript("mk_002.txt");
        transcripts[13] = new SpeechTranscript("mm_001.txt");
        transcripts[14] = new SpeechTranscript("mm_002.txt");


        int length = 0;
        HashMap<String, Integer> emotions = new HashMap<>();
        double totalD = 0;
        for (int i = 0; i < 15; i++) {
            transcripts[i].printFileInfo();
            length += transcripts[i].getNumClips();
            totalD += transcripts[i].getTotalDuration();
            for (HashMap.Entry<String, Integer> pair: transcripts[i].emotions.entrySet()) {
                if (emotions.containsKey(pair.getKey()))
                    emotions.put(pair.getKey(), emotions.get(pair.getKey()) + pair.getValue());
                else emotions.put(pair.getKey(), pair.getValue());
            }
        }

        System.out.println("Total number of clips: " + length +  " | Average per person: " + (length/8) + " | Total Length: " + totalD/60  + "  minutes");
        System.out.println("Emotions count:");
        for (HashMap.Entry<String, Integer> pair: emotions.entrySet()) {
            System.out.println( pair.getKey()  + " : "  + pair.getValue());

        }
        System.out.println();
    }
}
