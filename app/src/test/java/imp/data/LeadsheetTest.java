package imp.data;

import imp.data.advice.Advisor;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LeadsheetTest {
    @Before
    public void setUp() throws Exception {
        Advisor.loadDefaultRules();
    }

    @Test
    public void readScore() throws FileNotFoundException {
        Score score = Leadsheet.readFile(new File("C:\\Users\\snorr\\impro-visor-version-10.2-files\\leadsheets\\_double_blues.ls"));
        assertNotNull(score);
        String expected = "Score: \n" +
                "ChordProg: \n" +
                "\n" +
                "Part with unitCount = 32: Beat 0 + 0 slots :CHORD: [Name = F13_][Voicing = null][RhythmValue = 480]\n" +
                "Beat 4 + 0 slots :CHORD: [Name = Bb13][Voicing = null][RhythmValue = 240]\n" +
                "Beat 6 + 0 slots :CHORD: [Name = Bo7][Voicing = null][RhythmValue = 240]\n" +
                "Beat 8 + 0 slots :CHORD: [Name = F13_][Voicing = null][RhythmValue = 480]\n" +
                "Beat 12 + 0 slots :CHORD: [Name = Cm9][Voicing = null][RhythmValue = 240]\n" +
                "Beat 14 + 0 slots :CHORD: [Name = F13b9][Voicing = null][RhythmValue = 240]\n" +
                "Beat 16 + 0 slots :CHORD: [Name = Bb13][Voicing = null][RhythmValue = 480]\n" +
                "Beat 20 + 0 slots :CHORD: [Name = Bo7][Voicing = null][RhythmValue = 480]\n" +
                "Beat 24 + 0 slots :CHORD: [Name = F13_][Voicing = null][RhythmValue = 480]\n" +
                "Beat 28 + 0 slots :CHORD: [Name = D7#5#9][Voicing = null][RhythmValue = 480]\n" +
                "Beat 32 + 0 slots :CHORD: [Name = Gm9][Voicing = null][RhythmValue = 480]\n" +
                "Beat 36 + 0 slots :CHORD: [Name = C13b9][Voicing = null][RhythmValue = 480]\n" +
                "Beat 40 + 0 slots :CHORD: [Name = F13_][Voicing = null][RhythmValue = 240]\n" +
                "Beat 42 + 0 slots :CHORD: [Name = D7#5#9][Voicing = null][RhythmValue = 240]\n" +
                "Beat 44 + 0 slots :CHORD: [Name = Gm9][Voicing = null][RhythmValue = 240]\n" +
                "Beat 46 + 0 slots :CHORD: [Name = C13b9][Voicing = null][RhythmValue = 240]\n" +
                "Beat 48 + 0 slots :CHORD: [Name = F13_][Voicing = null][RhythmValue = 480]\n" +
                "Beat 52 + 0 slots :CHORD: [Name = Bb13][Voicing = null][RhythmValue = 240]\n" +
                "Beat 54 + 0 slots :CHORD: [Name = Bo7][Voicing = null][RhythmValue = 240]\n" +
                "Beat 56 + 0 slots :CHORD: [Name = F13_][Voicing = null][RhythmValue = 480]\n" +
                "Beat 60 + 0 slots :CHORD: [Name = Cm9][Voicing = null][RhythmValue = 240]\n" +
                "Beat 62 + 0 slots :CHORD: [Name = F13b9][Voicing = null][RhythmValue = 240]\n" +
                "Beat 64 + 0 slots :CHORD: [Name = Bb13][Voicing = null][RhythmValue = 480]\n" +
                "Beat 68 + 0 slots :CHORD: [Name = Bo7][Voicing = null][RhythmValue = 480]\n" +
                "Beat 72 + 0 slots :CHORD: [Name = F13_][Voicing = null][RhythmValue = 480]\n" +
                "Beat 76 + 0 slots :CHORD: [Name = D7#5#9][Voicing = null][RhythmValue = 480]\n" +
                "Beat 80 + 0 slots :CHORD: [Name = Gm9][Voicing = null][RhythmValue = 480]\n" +
                "Beat 84 + 0 slots :CHORD: [Name = C13b9][Voicing = null][RhythmValue = 480]\n" +
                "Beat 88 + 0 slots :CHORD: [Name = F13_][Voicing = null][RhythmValue = 240]\n" +
                "Beat 90 + 0 slots :CHORD: [Name = D7#5#9][Voicing = null][RhythmValue = 240]\n" +
                "Beat 92 + 0 slots :CHORD: [Name = Gm9][Voicing = null][RhythmValue = 240]\n" +
                "Beat 94 + 0 slots :CHORD: [Name = C13b9][Voicing = null][RhythmValue = 240]\n" +
                "\n" +
                "Part 0:\n" +
                "MelodyPart with 11520 slots, 1 notes and rests: r1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1 \n";
        String scoreAsString = score.toString();
        assertEquals(expected, scoreAsString);
    }
}