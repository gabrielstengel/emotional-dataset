import java.io.File;
import java.util.ArrayList;

public class SpeechFile {


   private String fileName;
   private WavFile wavFile;
   private double[][] data;


   public SpeechFile(String fileName) {
      this.fileName = fileName;

      try
      {
         // Open the wav file specified as the first argument
         wavFile = WavFile.openWavFile(new File(fileName));

         // Display information about the wav file
         wavFile.display();

         // Get the number of audio channels in the wav file
         int numChannels = wavFile.getNumChannels();

         // Create a buffer of 100 frames
         data = new double[numChannels][(int) (wavFile.getNumFrames())];

         int framesRead;
         double min = Double.MAX_VALUE;
         double max = Double.MIN_VALUE;

         do
         {
            // Read frames into buffer
            framesRead = wavFile.readFrames(data, (int) wavFile.getNumFrames());

            // Loop through frames and look for minimum and maximum value
            for (int s=0 ; s<framesRead ; s++)
            {
               if (data[0][s] > max) max = data[0][s];
               if (data[0][s] < min) min = data[0][s];
            }
         }
         while (framesRead != 0);

         // Close the wavFile
         wavFile.close();

         // Output the minimum and maximum value
         System.out.printf("Min: %f, Max: %f\n", min, max);
      }
      catch (Exception e)
      {
         System.err.println(e);
      }
   }

   public void printFormat() {

   }

   public void trimClip(Clip clip, int num, String labelStrut) {
      try
      {

         boolean labelAll = false;
         boolean labelTwo = false;
         boolean labelThree = false;
         boolean labelFour = false;

         switch (labelStrut) {
            case "all":
               labelAll = true;
               break;
            case "two":
               labelTwo = true;
               break;
            case "three":
               labelThree = true;
               break;
            case "four":
               labelFour = true;
               break;
            default:
               System.err.println("ERROR ERROR:  label is incorrect");
         }


         long sampleRate = wavFile.getSampleRate();    // Samples per second
         double duration = clip.getDuration();     // Seconds

         // Calculate the number of frames required for specified duration
         long numFrames = (long)(duration * sampleRate) + 1;

         if (numFrames % 2 != 0) numFrames++;

         /* SECTION WHERE WE FIGURE OUT THE LABELS ON THE DATA */
         int label = 0;


         if (labelAll) {
            if (clip.getEmotion() == "NEUTRAL") label = 0;
            else if (clip.getEmotion() == "DISGUST") label = 1;
            else if (clip.getEmotion() == "PANIC") label = 2;
            else if (clip.getEmotion() == "ANXIETY") label = 3;
            else if (clip.getEmotion() == "HOT-ANGER") label = 4;
            else if (clip.getEmotion() == "COLD-ANGER") label = 5;
            else if (clip.getEmotion() == "DESPAIR") label = 6;
            else if (clip.getEmotion() == "SADNESS") label = 7;
            else if (clip.getEmotion() == "ELATION") label = 8;
            else if (clip.getEmotion() == "HAPPY") label = 9;
            else if (clip.getEmotion() == "INTEREST") label = 10;
            else if (clip.getEmotion() == "BOREDOM") label = 11;
            else if (clip.getEmotion() == "SHAME") label = 12;
            else if (clip.getEmotion() == "PRIDE") label = 13;
            else if (clip.getEmotion() == "CONTEMPT") label = 14;
            else if (clip.getEmotion() == "PASSIVE") label = 15;
            else if (clip.getEmotion() == "DOMINANT") label = 16;
         }

         else if (labelTwo){
            if (clip.getEmotion() == "NEUTRAL") label = 0;
            else if (clip.getEmotion() == "DISGUST") label = 1;
            else if (clip.getEmotion() == "PANIC") label = 1;
            else if (clip.getEmotion() == "ANXIETY") label = 1;
            else if (clip.getEmotion() == "HOT-ANGER") label = 1;
            else if (clip.getEmotion() == "COLD-ANGER") label = 1;
            else if (clip.getEmotion() == "DESPAIR") label = 1;
            else if (clip.getEmotion() == "SADNESS") label = 1;
            else if (clip.getEmotion() == "ELATION") label = 1;
            else if (clip.getEmotion() == "HAPPY") label = 1;
            else if (clip.getEmotion() == "INTEREST") label = 1;
            else if (clip.getEmotion() == "BOREDOM") label = 1;
            else if (clip.getEmotion() == "SHAME") label = 1;
            else if (clip.getEmotion() == "PRIDE") label = 1;
            else if (clip.getEmotion() == "CONTEMPT") label = 1;
            else if (clip.getEmotion() == "PASSIVE") label = 1;
            else if (clip.getEmotion() == "DOMINANT") label = 1;
         }

         else if (labelThree) {
            if (clip.getEmotion() == "NEUTRAL") label = 1;
            else if (clip.getEmotion() == "DISGUST") label = 2;
            else if (clip.getEmotion() == "PANIC") label = 2;
            else if (clip.getEmotion() == "ANXIETY") label = 2;
            else if (clip.getEmotion() == "HOT-ANGER") label = 2;
            else if (clip.getEmotion() == "COLD-ANGER") label = 2;
            else if (clip.getEmotion() == "DESPAIR") label = 2;
            else if (clip.getEmotion() == "SADNESS") label = 2;
            else if (clip.getEmotion() == "ELATION") label = 3;
            else if (clip.getEmotion() == "HAPPY") label = 3;
            else if (clip.getEmotion() == "INTEREST") label = 3;
            else if (clip.getEmotion() == "SHAME") label = 2;
            else if (clip.getEmotion() == "CONTEMPT") label = 2;
            else return;
         }

         else if (labelFour) {
            if (clip.getEmotion() == "NEUTRAL") label = 1;
            else if (clip.getEmotion() == "HOT-ANGER") label = 2;
            else if (clip.getEmotion() == "COLD-ANGER") label = 2;
            else if (clip.getEmotion() == "SADNESS") label = 3;
            else if (clip.getEmotion() == "ELATION") label = 4;
            else if (clip.getEmotion() == "HAPPY") label = 4;
            else return;
         }



         // Create a wav file with the name specified as the first argument
         WavFile wavFile;
         if (labelAll)
            wavFile = WavFile.newWavFile(new File(".//outputCORRECT//all-labeled//" + label + "_" + clip.getEmotion() + "_" + num + ".wav"), 2, numFrames, 16, sampleRate);

         else if (labelTwo)
            wavFile = WavFile.newWavFile(new File(".//outputCORRECT//two-labeled//" + label + "_" + clip.getEmotion() + "_" + num + ".wav"), 2, numFrames, 16, sampleRate);

         else if (labelThree)
            wavFile = WavFile.newWavFile(new File(".//outputCORRECT//three-labeled//" + label + "_" + clip.getEmotion() + "_" + num + ".wav"), 2, numFrames, 16, sampleRate);

         else {
            if (labelFour != true) System.err.println("ERROR ERROR ERROR");
            wavFile = WavFile.newWavFile(new File(".//outputCORRECT//four-labeled//" + label + "_" + clip.getEmotion() + "_" + num + ".wav"), 2, numFrames, 16, sampleRate);
         }

         // Create a buffer of 100 frames
         double[][] buffer = new double[2][(int)numFrames];

         // Initialise a local frame counter
         long frameCounter = 0;

         long start = (long) ((clip.getStart()) * sampleRate)-1;

         // Loop until all frames written
         for (int s = 0; s < numFrames; s++)
         {
               buffer[0][s] = data[0][(int) start + s];
               buffer[1][s] = data[1][(int) start + s];
            // Write the buffer

         }

         wavFile.writeFrames(buffer, (int)numFrames);
         // Close the wavFile
         wavFile.close();
      }
      catch (Exception e)
      {
         System.err.println(e);
      }


   }

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

      int count = 0;
      for (int i = 0; i < 15; i++) {
         String name = transcripts[i].getFileName().substring(0,transcripts[i].getFileName().length()-4);
         SpeechFile testF = new SpeechFile(name + ".wav");
         ArrayList<Clip> clips = transcripts[i].getClips();
         for (int j = 0; j < transcripts[i].getNumClips(); j++, count++) {
            testF.trimClip(clips.get(j), count, "all");
            testF.trimClip(clips.get(j), count, "two");
            testF.trimClip(clips.get(j), count, "three");
            testF.trimClip(clips.get(j), count, "four");

         }
      }
   }
}
