package imp;
import imp.data.Leadsheet;
import imp.data.Score;
import imp.data.Transposition;
import imp.data.advice.Advisor;
import imp.util.Preferences;
import org.apache.commons.cli.*;
import polya.Polylist;
import polya.Tokenizer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ls2midi {
    Advisor advisor;
    public static void main(String[] args) {
        System.out.println("Leadsheet to midi conversion\n");
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            String[] inputs = cmd.getArgs();

            ls2midi converter = new ls2midi();
            converter.processInputFile(inputs[0]);
        } catch (ParseException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ls2midi() throws FileNotFoundException {
        Preferences.loadPreferences();
        // Load the default rule file from the Preferences file
        String ruleFilePath = Preferences.getPreference(Preferences.DEFAULT_VOCAB_FILE);
        String ruleFileName;
        if (ruleFilePath.lastIndexOf(File.separator) == -1) {
            ruleFileName = ruleFilePath;
        } else {
            ruleFileName = ruleFilePath.substring(ruleFilePath.lastIndexOf(File.separator));
        }

        File ruleFile = new File(getVocabDirectory(), ruleFileName);

        Polylist rules = loadRules(ruleFile);
        this.advisor = new Advisor(rules);
    }

    private Polylist loadRules(File ruleFile) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(ruleFile);
        Tokenizer in = new Tokenizer(inputStream);
        Object ob;
        Polylist rules = new Polylist();

        while ((ob = in.nextSexp()) != Tokenizer.eof) {
            rules = rules.cons(ob);
        }

        return rules.reverse();
    }

    private void processInputFile(String inputFilename) {
        File f = new File(inputFilename);
        if (f.exists()) {
            processInputFile(f);
        } else {
            System.out.println(inputFilename + " does not exist\n");
        }
    }

    private static void processInputFile(File f) {
        FileInputStream leadStream = null;
        try {
            leadStream = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Reading input file\n");

        Score score = new Score();
        Tokenizer tokenizer = new Tokenizer(leadStream);
        Leadsheet.readLeadSheet(tokenizer, score);

        System.out.println(score.getTitle());

        try {
            Sequence sequence = score.render((short) 480, Transposition.none);
            String originalFilename = f.getName();
            String originalFilenameMinusExtension = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String outputFilename = originalFilenameMinusExtension + ".mid";
            File midiFile = new File(f.getParent(), outputFilename);
            MidiSystem.write(sequence, 1, midiFile);
        } catch (InvalidMidiDataException e) {
            System.out.println("Invalid Midi data");
        } catch (IOException e) {
            System.out.println("IO exception");
        }
    }

    public static File getUserDirectory() {
        String userHome = System.getProperty("user.home");
        return new File(userHome, Directories.improHome);
    }

    public static File getVocabDirectory() {
        return new File(getUserDirectory(), Directories.vocabDirName);
    }
}
