import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class TranslatorAdapter implements EngishSpeaker, FrenchSpeaker {
    private  FrenchSpeaker frenchSpeaker;
    private  EngishSpeaker engishSpeaker;

    public TranslatorAdapter(EngishSpeaker engishSpeaker, FrenchSpeaker frenchSpeaker) {
        this.engishSpeaker = engishSpeaker;
        this.frenchSpeaker = frenchSpeaker;
    }

    @Override
    public String SpeakeEnglish(String word) {
       String frenchtext= frenchSpeaker.SpeakFrench(word);
       return  callPythonScript(frenchtext, "fr", "en");
    }

    @Override
    public String SpeakFrench(String word) {
        String englishtext= engishSpeaker.SpeakeEnglish(word);
        return callPythonScript(englishtext, "en", "fr");
    }

    private static String callPythonScript(String text, String srcLang, String trgLang) {
        try {

            ProcessBuilder pb = new ProcessBuilder("python", "C:\\Users\\DELL\\IdeaProjects\\translator\\src\\translate_server.py");
            pb.redirectErrorStream(true);

            Process process = pb.start();

            // Write input to the Python script
            try (Writer writer = new OutputStreamWriter(process.getOutputStream())) {
                writer.write(text + "\n");
                writer.write(srcLang + "\n");
                writer.write(trgLang + "\n");
                writer.flush();
            }

            // Read output from the Python script
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                output.append(line).append("\n");
            }

            in.close();

            process.waitFor();
            String result = output.toString().trim();
            if (result.contains("Translation: ")) {
                System.out.println("traduction: "+result.split("Translation: ")[1].trim());
                return result.split("Translation: ")[1].trim();  // Extract the translation from the output
            } else {
                return "Translation failed.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Translation failed.";
        }
    }

}

