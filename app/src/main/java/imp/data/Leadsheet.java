/**
 * This Java Class is part of the Impro-Visor Application
 * <p>
 * Copyright (C) 2005-2018 Robert Keller and Harvey Mudd College
 * <p>
 * Impro-Visor is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * Impro-Visor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * merchantability or fitness for a particular purpose.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Impro-Visor; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package imp.data;

import imp.style.Style;
import imp.data.advice.Advisor;
import imp.Constants;
import imp.util.ErrorLog;
import imp.util.ErrorLogWithResponse;
import imp.util.Preferences;
import imp.util.Trace;

import java.io.*;

import polya.Polylist;
import polya.Tokenizer;

public class Leadsheet
        implements Constants {
    static String[] keyword =
            {"title", "key", "meter", "bars", "tempo", "transpose", "volume",
                    "part", "type", "instrument", "chords", "melody", "swing",
                    "breakpoint", "composer", "comments",
                    "stave", "treble", "bass", "grand", "auto", "none", "layout",
                    "bass-volume", "drum-volume", "chord-volume", "style", "section",
                    "bass-instrument", "playback-transpose", "show", "year",
                    "chord-font-size", "phrase", "roadmap-layout", "roadmap",
                    "voicing-name", "melody-volume"
            };

    static final int TITLE = 0;

    static final int KEY = 1;

    static final int METER = 2;

    static final int BARS = 3;

    static final int TEMPO = 4;

    static final int TRANSPOSE = 5;

    static final int VOLUME = 6;

    static final int PART = 7;

    static final int TYPE = 8;

    static final int INSTRUMENT = 9;

    static final int CHORDS = 10;

    static final int MELODY = 11;

    static final int SWING = 12;

    static final int BREAKPOINT = 13;

    static final int COMPOSER = 14;

    static final int COMMENTS = 15;

    static final int STAVE = 16;

    static final int TREBLE = 17;

    static final int BASS = 18;

    static final int GRAND = 19;

    static final int AUTO = 20;

    static final int NONE = 21;

    static final int LAYOUT = 22;

    static final int BASS_VOLUME = 23;

    static final int DRUM_VOLUME = 24;

    static final int CHORD_VOLUME = 25;

    static final int STYLE = 26;

    static final int SECTION = 27;

    static final int BASS_INSTRUMENT = 28;

    static final int PLAYBACK_TRANSPOSE = 29;

    static final int SHOW_TITLE = 30;

    static final int YEAR = 31;

    static final int CHORD_FONT_SIZE = 32;

    static final int PHRASE = 33;

    static final int ROADMAP_LAYOUT = 34;

    static final int ROADMAP = 35;

    static final int VOICING_FILE = 36;

    static final int MELODY_VOLUME = 37;

    static final int UNKNOWN = -1;

    /**
     * Save a Score as a Leadsheet.
     */
    public static void saveLeadSheet(BufferedWriter out, Score score, boolean saveRoadMap)
            throws IOException {
        // Write out score information
        out.write("(" + keyword[TITLE] + " " + score.getTitle() + ")");
        out.newLine();
        out.write("(" + keyword[COMPOSER] + " " + score.getComposer() + ")");
        out.newLine();
        out.write("(" + keyword[SHOW_TITLE] + " " + score.getShowTitle() + ")");
        out.newLine();
        out.write("(" + keyword[YEAR] + " " + score.getYear() + ")");
        out.newLine();
        out.write("(" + keyword[COMMENTS] + " " + score.getComments() + ")");
        out.newLine();
        out.write("(" + keyword[METER] + " " + score.getMetre()[0] + " " + score.getMetre()[1] + ")");
        out.newLine();
        out.write("(" + keyword[KEY] + " " + score.getKeySignature() + ")");
        out.newLine();
        out.write("(" + keyword[TEMPO] + " " + score.getTempo() + ")");
        out.newLine();
        out.write("(" + keyword[VOLUME] + " " + score.getMasterVolume() + ")");
        out.newLine();
        out.write("(" + keyword[PLAYBACK_TRANSPOSE] + " " + score.getTransposition() + ")");
        out.newLine();
        out.write("(" + keyword[CHORD_FONT_SIZE] + " " + score.getChordFontSize() + ")");
        out.newLine();
        out.write("(" + keyword[BASS_INSTRUMENT] + " " + score.getBassInstrument() + ")");
        out.newLine();
        out.write("(" + keyword[BASS_VOLUME] + " " + score.getBassVolume() + ")");
        out.newLine();
        out.write("(" + keyword[DRUM_VOLUME] + " " + score.getDrumVolume() + ")");
        out.newLine();
        out.write("(" + keyword[CHORD_VOLUME] + " " + score.getChordVolume() + ")");
        out.newLine();
        out.write("(" + keyword[BREAKPOINT] + " " + score.getBreakpoint() + ")");
        out.newLine();
        out.write(score.getLayoutList().cons(keyword[LAYOUT]).toString());
        out.newLine();
        out.write("(" + keyword[ROADMAP_LAYOUT] + " " + score.getRoadmapLayout() + ")");
        out.newLine();
        out.write("(" + keyword[MELODY_VOLUME] + " " + score.getMelodyVolume() + ")");
        out.newLine();

        Style style = score.getChordProg().getStyle();
        if (style == null) {
            out.newLine();
            //ErrorLog.log(ErrorLog.WARNING, "Please fix invalid style to save chords");
        } else {
            style.saveLeadsheet(out);
        }
        // Write out the parts
        score.saveLeadsheet(out, saveRoadMap);
    }

    public static String concatElements(Polylist item) {
        StringBuilder buffer = new StringBuilder();
        if (item.nonEmpty()) {
            buffer.append(item.first());
            item = item.rest();
        }
        while (item.nonEmpty()) {
            Object first = item.first();
            // Insert blank unless the next item is a comma.
            if (!first.equals(",")) {
                buffer.append(" ");
            }
            buffer.append(item.first());
            item = item.rest();
        }
        return buffer.toString();
    }

    /**
     * Read leadsheet from tokens provided by Tokenizer into Score
     */
    public static boolean readLeadSheet(Tokenizer in, Score score) {
        LeadSheetReader leadSheetReader = new LeadSheetReader(in,
                score,
                Preferences.getAlwaysUseStave(),
                Preferences.getStaveTypeFromPreferences());
        return leadSheetReader.readLeadSheet();
    }


    private static class LeadSheetReader {

        private int beatValue;
        private int beatsPerBar;
        private int measureLength;

        Tokenizer in;
        Score score;
        boolean overrideStaveType;
        StaveType useStaveType;
        private ChordPart chords;
        private MelodyPart melody;
        private Key key;
        private boolean firstUnitPassed;
        private Part partReferenced;
        private Polylist chordInputReversed;
        private Polylist melodyInputReversed;
        private int rise;
        private boolean headStarted;

        public LeadSheetReader(Tokenizer in, Score score, boolean overrideStaveType, StaveType useStaveType) {
            this.in = in;
            this.score = score;
            this.overrideStaveType = overrideStaveType;
            this.useStaveType = useStaveType;
        }

        /**
         * Read leadsheet from tokens provided by Tokenizer into Score, with
         * a given StaveType, which can be overridden.
         */
        public boolean readLeadSheet() {

            // These may change as a result of reading metre!!
            beatValue = WHOLE / score.getMetre()[1];
            beatsPerBar = score.getMetre()[0];
            measureLength = beatsPerBar * beatValue;

            score.setTempo(160);
            chords = new ChordPart();
            melody = new MelodyPart();
            key = Key.getKey(0);

            chords.setStyle(Preferences.getPreference(Preferences.DEFAULT_STYLE));

            chords.setInstrument(Integer.parseInt(Preferences.getPreference(Preferences.DEFAULT_CHORD_INSTRUMENT)) - 1);
            melody.setInstrument(Integer.parseInt(Preferences.getPreference(Preferences.DEFAULT_MELODY_INSTRUMENT)) - 1);

            score.setScoreItemsFromPreferences();

            firstUnitPassed = false;
            // unless starts with bar

            partReferenced = chords;

            chordInputReversed = Polylist.nil;
            melodyInputReversed = Polylist.nil;

            Object ob;
            rise = 0;
            headStarted = false;
            while ((ob = in.nextSexp()) != Tokenizer.eof) {
                // Polylists are directives
                // Atoms by themselves are chords and melody

                if (ob instanceof Polylist) {
                    ParsePolylist((Polylist) ob);
                } else if (ob instanceof MelodySymbol) {
                    ParseMelodySymbol(ob);
                } else if (ob instanceof String) {
                    ParseString(ob);
                }
            }

            // Force a closing bar as necessary to prevent later mess-up

            if (chordInputReversed.nonEmpty() && !endsBar(chordInputReversed.first())) {
                chordInputReversed = chordInputReversed.cons(BARSTRING);
            }

            addToChordPart(chordInputReversed, chords, rise, measureLength, key);
            addToMelodyPart(melodyInputReversed, melody, rise);

            if (melody.getUnit(0) == null) {
                melody.addRest(new Rest(BEAT));
            }

            return true;
        }

        private void ParseMelodySymbol(Object ob) {
            melodyInputReversed = melodyInputReversed.cons(ob);
        }

        private void ParseString(Object ob) {
            String stringOb = (String) ob;

            // Because it was read from the tokenizer, stringOb is not empty.

            char firstChar = stringOb.charAt(0);

            boolean handled = false;

            // first non-list starts the head.

            if (!headStarted) {
                headStarted = true;
                score.addPart(melody);
                score.setChordProg(chords);
            }

            if (firstChar == BAR || firstChar == COMMA) {
                if (stringOb.length() > 1) {
                    ErrorLog.log(ErrorLog.WARNING,
                            "A space is required after a bar line. "
                                    + "Impro-Visor is ignoring what follows the bar in: " + ob);
                }

                if (!firstUnitPassed) {
                    firstUnitPassed = true;
                }

                chordInputReversed = chordInputReversed.cons(BARSTRING);
                handled = true;
            } else if (firstChar == SLASH) {
                chordInputReversed = chordInputReversed.cons(SLASHSTRING);
                handled = true;
            } else if (Character.isLetter(firstChar)) {
                firstUnitPassed = true;
                if (Character.isLowerCase(firstChar)) {
                    // Melody note or rest
                    MelodySymbol melodySymbol = MelodySymbol.makeMelodySymbol(stringOb);
                    if (melodySymbol != null) {
                        melodyInputReversed = melodyInputReversed.cons(melodySymbol);
                        handled = true;
                    }
                } else {
                    ChordSymbol symbol = ChordSymbol.makeChordSymbol(stringOb);

                    if (symbol != null) {
                        chordInputReversed = chordInputReversed.cons(stringOb);
                    } else {
                        if (ErrorLogWithResponse.log(ErrorLog.WARNING,
                                "Impro-Visor does not recognize this chord: " + stringOb
                                        + "\nusing NC instead")) {
                            // return false;
                        }
                    }
                    handled = true;
                }
            }
            if (!handled) {
                if (ErrorLogWithResponse.log(ErrorLog.SEVERE,
                        "Unidentified item in input: " + ob
                                + "\nJava class is " + ob.getClass())) {
                    // return false;
                }
                return;
            }
        }

        private void ParsePolylist(Polylist ob) {
            Polylist item = ob;

            if (item.nonEmpty()) {
                Object dispatcher = item.first();
                item = item.rest();        // bypass first thing in list

                if (!(dispatcher instanceof String)) {
                    ErrorLog.log(ErrorLog.SEVERE,
                            "Expected keyword, found '" + dispatcher + "', ingnoring");
                } else {
                    switch (lookup((String) dispatcher, keyword)) {
                        case TITLE: {
                            String titleString = concatElements(item);
                            score.setTitle(titleString);
                        }
                        break;

                        case COMPOSER: {
                            String composerString = concatElements(item);
                            score.setComposer(composerString);
                        }
                        break;

                        case SHOW_TITLE: {
                            String showTitleString = concatElements(item);
                            score.setShowTitle(showTitleString);
                        }
                        break;

                        case YEAR: {
                            String year = concatElements(item);
                            score.setYear(year);
                        }
                        break;

                        case COMMENTS: {
                            String commentsString = concatElements(item);
                            score.setComments(commentsString);
                        }
                        break;

                        case PLAYBACK_TRANSPOSE: {
                            switch (item.length()) {
                                case 1 -> { // old version, bass and chords the same
                                    if (item.first() instanceof Long) {
                                        int value1 = ((Long) item.first()).intValue();
                                        score.setTransposition(new Transposition(value1, value1, 0));
                                    }
                                }
                                case 3 -> { // new version, bass, chords and melody
                                    if (item.first() instanceof Long &&
                                            item.second() instanceof Long &&
                                            item.third() instanceof Long) {
                                        int value1 = ((Long) item.first()).intValue();
                                        int value2 = ((Long) item.second()).intValue();
                                        int value3 = ((Long) item.third()).intValue();

                                        score.setTransposition(new Transposition(value1, value2, value3));
                                    }
                                }
                            }
                        }
                        break;

                        case CHORD_FONT_SIZE:
                            if (item.nonEmpty() && item.first() instanceof Long) {
                                score.setChordFontSize(((Long) item.first()).intValue());
                            }
                            break;

                        case BASS_INSTRUMENT:
                            if (item.nonEmpty() && item.first() instanceof Long) {
                                score.setBassInstrument(((Long) item.first()).intValue());
                            }
                            break;

                        case BASS_VOLUME:
                            if (item.nonEmpty() && item.first() instanceof Long) {
                                score.setBassVolume(((Long) item.first()).intValue());
                            }
                            break;

                        case DRUM_VOLUME:
                            if (item.nonEmpty() && item.first() instanceof Long) {
                                score.setDrumVolume(((Long) item.first()).intValue());
                            }
                            break;

                        case CHORD_VOLUME:
                            if (item.nonEmpty() && item.first() instanceof Long) {
                                score.setChordVolume(((Long) item.first()).intValue());
                            }
                            break;

                        case MELODY_VOLUME:
                            if (item.nonEmpty() && item.first() instanceof Long) {
                                score.setMelodyVolume(((Long) item.first()).intValue());
                            }
                            break;


                        case STYLE:
                            if (item.nonEmpty() && item.first() instanceof String styleName) {
                                chordInputReversed =
                                        chordInputReversed.cons(item.cons(dispatcher));

                                Style style = Advisor.getStyle(styleName);
                                if (style == null) {
                                    String defaultStyleName =
                                            Preferences.getPreference(Preferences.DEFAULT_STYLE);
                                    ErrorLog.log(ErrorLog.WARNING,
                                            "Style named " + styleName +
                                                    " not found, using default " +
                                                    defaultStyleName);
                                    style = Advisor.getStyle(defaultStyleName);
                                }
                                item = item.rest();
                                while (item.nonEmpty()) {
                                    Polylist L = (Polylist) item.first();
                                    item = item.rest();
                                    assert style != null;
                                    style.load((String) L.first(), L.rest());
                                }
                            }
                            break;

                        case PHRASE:
                        case SECTION:
                            //if( item.nonEmpty() )
                        {
                            chordInputReversed =
                                    chordInputReversed.cons(item.cons(dispatcher));
                        }
                        break;

                        case KEY:
                            if (item.nonEmpty() && item.first() instanceof Long) {
                                int sharps = ((Long) item.first()).intValue();
                                score.setKeySignature(Key.getKeyDelta(sharps, rise));
                                key = Key.getKey(sharps);
                            }
                            break;

                        // Read in the meter from the leadesheet.  To support older versions
                        // that didn't recognize different time signatures, we look at the first
                        // value, and if there isn't a second value, we just assume that it's a
                        // four.
                        case METER:
                            if (item.nonEmpty() && item.first() instanceof Long) {
                                beatsPerBar = ((Long) item.first()).intValue();
                                if (beatsPerBar > MAX_BEATS_PER_BAR) {
                                    ErrorLog.log(ErrorLog.SEVERE, beatsPerBar
                                            + " beats per bar not supported, using "
                                            + DEFAULT_BEATS_PER_BAR);
                                    beatsPerBar = DEFAULT_BEATS_PER_BAR;
                                }
                            }

                            if (item.rest().nonEmpty() && item.rest().first() instanceof Long) {
                                long beat_denominator = ((Long) item.rest().first()).intValue();
                                if (beat_denominator < 1 || beat_denominator > MAX_BEAT_DENOMINATOR) {
                                    ErrorLog.log(ErrorLog.SEVERE, beat_denominator
                                            + " not supported in beat denominator, using "
                                            + DEFAULT_BEAT_DENOMINATOR);
                                    beat_denominator = DEFAULT_BEAT_DENOMINATOR;
                                }
                                beatValue = WHOLE / (int) beat_denominator;
                            } else {
                                beatValue = BEAT;
                            }

                            measureLength = beatsPerBar * beatValue;
                            score.setMetre(beatsPerBar, WHOLE / beatValue);
                            chords.setMetre(beatsPerBar, WHOLE / beatValue);

                            break;

                        case TEMPO:
                            if (item.nonEmpty() && item.first() instanceof Double) {
                                score.setTempo((Double) item.first());
                            }
                            break;

                        case BREAKPOINT:
                            if (item.nonEmpty() && item.first() instanceof Long) {
                                score.setBreakpoint(((Long) item.first()).intValue());
                            }
                            break;

                        case LAYOUT:
                            // FIX: check syntax
                            score.setLayoutList(item);
                            break;

                        case ROADMAP_LAYOUT:
                            // FIX: check syntax
                            score.setRoadmapLayout(((Long) item.first()).intValue());
                            break;

                        case BARS:
                            // No longer used
                            break;

                        case PART:
                            ParsePart(item);
                            break;

                        case TRANSPOSE:
                            if (item.nonEmpty() && item.first() instanceof Long) {
                                rise = ((Long) item.first()).intValue();
                                int sharps = score.getKeySignature() + rise;
                                score.setKeySignature(sharps);
                                key = Key.getKey(sharps);
                            }
                            break;
                    }
                }
            }
        }

        private void ParsePart(Polylist item) {
            while (item.nonEmpty()) {
                boolean handled = false;
                Object subOb = item.first();
                if (subOb instanceof Polylist subitem && subitem.nonEmpty()) {
                    Object subkey = subitem.first();
                    if (subkey instanceof String) {
                        switch (lookup((String) subkey, keyword)) {
                            case TYPE -> {
                                {
                                    if (subitem.rest().nonEmpty()) {
                                        if (subitem.rest().first() instanceof String type) {
                                            if (type.equals(keyword[CHORDS])) {
                                                //chords = new ChordPart();	// FIX
                                                handled = true;
                                                partReferenced = chords;
                                            } else if (type.equals(keyword[MELODY])) {
                                                // Start a new melody part iff head not already started

                                                if (headStarted && melodyInputReversed.nonEmpty()) {
                                                    // process accumulated melody

                                                    addToMelodyPart(melodyInputReversed,
                                                            melody, rise);
                                                    melodyInputReversed = Polylist.nil;

                                                    melody = new MelodyPart();
                                                    score.addPart(melody);
                                                }
                                                handled = true;
                                                partReferenced = melody;
                                            }
                                        }
                                    }
                                }
                                if (overrideStaveType) {
                                    partReferenced.setStaveType(useStaveType);
                                }
                            } // case TYPE

                            case INSTRUMENT -> {
                                if (subitem.rest().nonEmpty()) {
                                    Object inst = subitem.rest().first();
                                    if (inst instanceof Long) {
                                        partReferenced.setInstrument(((Long) inst).intValue());
                                        handled = true;
                                    }
                                }
                            }
                            // case INSTRUMENT

                            case VOLUME -> {
                                if (subitem.rest().nonEmpty()) {
                                    Object inst = subitem.rest().first();
                                    if (inst instanceof Long) {
                                        partReferenced.setVolume(((Long) inst).intValue());
                                        handled = true;
                                    }
                                }
                            }
                            // case VOLUME

                            case KEY -> {
                                if (subitem.rest().nonEmpty()) {
                                    Object inst = subitem.rest().first();
                                    if (inst instanceof Long) {
                                        int sharps =
                                                Key.getKeyDelta(((Long) inst).intValue(),
                                                        rise);
                                        partReferenced.setKeySignature(sharps);
                                        key = Key.getKey(sharps);
                                        handled = true;
                                    }
                                }
                            }
                            // case KEY

                            case ROADMAP -> {
                                Polylist roadmapPoly = ((Polylist) item.first()).rest();
                                ((ChordPart) partReferenced).setRoadmapPoly(roadmapPoly);
                                //System.out.println("roadmap read as " + roadmapPoly);
                                handled = true;
                            }
                            case METER -> handled = true;
                            case TITLE -> {
                                String title = concatElements(subitem.rest());
                                partReferenced.setTitle(title);
                                handled = true;
                            }
                            // case TITLE

                            case COMPOSER -> {
                                String partComposerString =
                                        concatElements(subitem.rest());
                                partReferenced.setComposer(partComposerString);
                                handled = true;
                            }
                            // case COMPOSER

                            case STAVE -> {
                                if (!overrideStaveType) {
                                    String partStaveString =
                                            concatElements(subitem.rest());
                                    switch (lookup(partStaveString, keyword)) {
                                        case TREBLE -> partReferenced.setStaveType(StaveType.TREBLE);
                                        case BASS -> partReferenced.setStaveType(StaveType.BASS);
                                        case GRAND -> partReferenced.setStaveType(StaveType.GRAND);
                                        case AUTO -> partReferenced.setStaveType(StaveType.AUTO);
                                        default -> ErrorLog.log(ErrorLog.WARNING,
                                                "Stave type unrecognized: "
                                                        + partStaveString);
                                    }
                                }

                                handled = true;
                            }
                            // case STAVE

                        }
                    } // end switch
                }
                if (!handled && subOb instanceof Polylist && ((Polylist) subOb).nonEmpty()) {
                    ErrorLog.log(ErrorLog.WARNING,
                            "item in part not handled: " + subOb);
                }
                item = item.rest();
            }
        }

    }

    public static Polylist extractChordsAndMelody(Polylist chordsAndMelody) {
        Polylist chordsReversed = Polylist.nil;
        Polylist melodyReversed = Polylist.nil;

        while (chordsAndMelody.nonEmpty()) {
            Object ob = chordsAndMelody.first();
            if (ob instanceof MelodySymbol) {
                melodyReversed = melodyReversed.cons(ob);
            } else if (!(ob instanceof String stringOb)) {
                ErrorLog.log(ErrorLog.SEVERE,
                        "Ignoring the following string, which is not recognized as chords or melody: " + ob);
            } else {

                // Because it was read from the tokenizer, stringOb is not empty.

                char firstChar = stringOb.charAt(0);

                boolean handled = false;

                if (firstChar == BAR || firstChar == COMMA) {
                    if (stringOb.length() > 1) {
                        ErrorLog.log(ErrorLog.WARNING,
                                "A space is required after a bar line, ignoring what follows the bar in: " + ob);
                    }

                    chordsReversed = chordsReversed.cons(BARSTRING);
                    handled = true;
                } else if (firstChar == SLASH) {
                    if (stringOb.length() > 1) {
                        ErrorLog.log(ErrorLog.WARNING,
                                "A space is required after a slash, ignoring what follows the bar in: " + ob);
                    }
                    chordsReversed = chordsReversed.cons(SLASHSTRING);
                    handled = true;
                } else if (Character.isLetter(firstChar)) {
                    if (Character.isLowerCase(firstChar)) {
                        // Melody note or rest

                        melodyReversed = melodyReversed.cons(stringOb);

                    } else {
                        ChordSymbol symbol = ChordSymbol.makeChordSymbol(stringOb);

                        if (symbol != null) {
                            chordsReversed = chordsReversed.cons(stringOb);
                        } else {
                            ErrorLog.log(ErrorLog.SEVERE,
                                    "Impro-Visor does not recognize this chord: " + stringOb
                                            + "\nusing NC instead");
                        }
                    }
                    handled = true;
                }
                if (!handled) {
                    ErrorLog.log(ErrorLog.SEVERE,
                            "Ignoring unidentified item: " + ob + " of " + ob.getClass());
                }
            }
            chordsAndMelody = chordsAndMelody.rest();
        }
        return Polylist.list(chordsReversed.reverse(),
                melodyReversed.reverse());
    }

    /**
     * Add a Polylist of MelodySymbols or Strings that can be converted to
     * MelodySymbols, in reverse, to a MelodyPart.
     */
    public static void addToMelodyPart(Polylist melodyInputReversed,
                                       MelodyPart melody,
                                       int rise) {
        int volume = MAX_VOLUME;
        Polylist melodyInput = melodyInputReversed.reverse();
        while (melodyInput.nonEmpty()) {
            Object ob = melodyInput.first();
            MelodySymbol melodySymbol = null;

            if (ob instanceof MelodySymbol) {
                melodySymbol = (MelodySymbol) ob;
            } else if (ob instanceof String) {
                melodySymbol = MelodySymbol.makeMelodySymbol((String) ob);
            }

            if (melodySymbol == null) {
                ErrorLog.log(ErrorLog.SEVERE,
                        "unrecognized melody note ignored: " + ob + ob.getClass().getName());
            } else if (melodySymbol instanceof NoteSymbol) {
                Note note = ((NoteSymbol) melodySymbol).transpose(rise).toNote();
                note.setVolume(volume);
                melody.addNote(note);
            } else if (melodySymbol instanceof VolumeSymbol) {
                volume = ((VolumeSymbol) melodySymbol).getVolume();
            }

            melodyInput = melodyInput.rest();
        }
    }

    /**
     * slotsAvailable is only the initial slots available; the rest are
     * determined by measure size.
     */
    static public void populatePartWithChords(ChordPart chordProg,
                                              Polylist chords,
                                              int slotsAvailable,
                                              int slotsPerBar) {
        Polylist L = chords;
        String previousChordName = null;
        int accumulatedDuration = 0;

        while (L.nonEmpty()) {
            // Handle one measure or part therof.

            Polylist currentMeasureIngoing = Polylist.nil;
            while (L.nonEmpty() && !endsBar(L.first())) {
                currentMeasureIngoing = currentMeasureIngoing.cons(L.first());
                L = L.rest();
            }

            if (L.nonEmpty()) {
                L = L.rest();    // Get rid of BAR
            }

            currentMeasureIngoing = currentMeasureIngoing.reverse();
            int chordsInMeasure = currentMeasureIngoing.length();

            // Check for conformance to slot structure.

            Trace.log(3, "slotsAvailable = " + slotsAvailable
                    + ", chords = " + chordsInMeasure);

            if (chordsInMeasure == 0) {
                accumulatedDuration += slotsAvailable;
            } else {
                if (slotsAvailable % chordsInMeasure != 0) {
                    ErrorLog.log(ErrorLog.SEVERE,
                            "Number of chords does not conform to slots available: " + chords + ", ignoring.");

                    return;
                }

                int slotsPerChord = slotsAvailable / chordsInMeasure;

                while (currentMeasureIngoing.nonEmpty()) {
                    String chordName = (String) currentMeasureIngoing.first();
                    if (chordName.equals(SLASHSTRING)) {
                        accumulatedDuration += slotsPerChord;
                    } else {
                        ChordSymbol symbol = ChordSymbol.makeChordSymbol(chordName);
                        if (symbol == null) {
                            ErrorLog.log(ErrorLog.SEVERE,
                                    "Impro-Visor does not recognize this chord: " + chordName
                                            + "\nusing NC instead");

                            chordName = NOCHORD;
                        }
                        if (previousChordName != null)  // false the first time
                        {
                            // push out the previous chord
                            Chord chord = new Chord(previousChordName,
                                    accumulatedDuration);

                            Trace.log(1, "adding chord: " + chord);

                            chordProg.addChord(chord);

                            // start a new chord

                        }
                        accumulatedDuration = slotsPerChord;
                        previousChordName = chordName;
                    }
                    currentMeasureIngoing = currentMeasureIngoing.rest();
                }
            }
            slotsAvailable = slotsPerBar;    // set up for non-first bar
        }

        // The last chord is treated specially.  It might not be a full
        // measure, and giving it such might overlay other chords.
        // Therefore we only give it duration 1, which fortunately
        // seems to be fleshed out upon insertion to the new part.

        if (previousChordName != null) {
            // push out the last chord
            Chord chord = new Chord(previousChordName, 1);

            Trace.log(1, "adding chord: " + chord);

            chordProg.addChord(chord);
        }
    }


    static void addToChordPart(Polylist chordInputReversed, ChordPart chords,
                               int rise, int slotsPerBar, Key key) {

        // NOCHORD is to force final output below

        Polylist L = chordInputReversed.cons(NOCHORD).reverse();

        int measure = 0;

        Trace.log(3, "chordInput = " + L);

        // Process chords

        String previousChord = null;
        int accumulated = 0;

        if (L.nonEmpty() && endsBar(L.first())) {
            // No Pickup
            L = L.rest();
        }

        while (L.nonEmpty()) {
            // Collect one bar's worth of chords
            Polylist chordsInBar = Polylist.nil;
            int numberOfChords = 0;
            while (L.nonEmpty() && !endsBar(L.first())) {
                if (!(L.first() instanceof Polylist)) {
                    numberOfChords++;
                }
                chordsInBar = chordsInBar.cons(L.first());
                L = L.rest();
            }

            if (L.nonEmpty()) {
                L = L.rest();    // lose the bar symbol
            }

            chordsInBar = chordsInBar.reverse();

            Trace.log(4, "chords in bar = " + chordsInBar);

            int spacing;

            if (numberOfChords <= 0) {
                spacing = slotsPerBar;
            } else if (slotsPerBar % numberOfChords != 0) {
                ErrorLog.log(ErrorLog.SEVERE,
                        "This sequence of chords " + chordsInBar
                                + " in one of the bars does not divide the number of slots per bar evenly, so the sheet will be badly-formed.\n\nAborting sheet construction, please fix and try again.");
                return;
            } else {
                spacing = slotsPerBar / numberOfChords;
            }

            if (numberOfChords == 0) {
                accumulated += slotsPerBar;
            }

            boolean seenFirstChord = false;
            while (chordsInBar.nonEmpty()) {
                Object ob = chordsInBar.first();
                chordsInBar = chordsInBar.rest();
                if (ob instanceof Polylist item) {
                    // All this code is placeholder until Sections are fully
                    // implemented
                    String dispatcher = (String) item.first();
                    item = item.rest();

                    boolean isPhrase = false;
                    switch (lookup(dispatcher, keyword)) {
                        case STYLE:
                            if (item.nonEmpty() && item.first() instanceof String stylename) {
                                Style style = Advisor.getStyle(stylename);
                                if (style == null) {
                                    ErrorLog.log(ErrorLog.WARNING, "Style " + stylename + " not found");
                                } else {

                                    item = item.rest();
                                    while (item.nonEmpty()) {
                                        Polylist param = (Polylist) item.first();
                                        item = item.rest();
                                        style.load((String) param.first(), param.rest());
                                    }

                                    int index = measure * slotsPerBar;
                                    if (seenFirstChord) {
                                        index += slotsPerBar;
                                    }
                                    chords.addSection(style.getName(), index, false);
                                }
                            }
                            break;

                        case PHRASE:
                            isPhrase = true;
                            /*
                             * fall-through
                             */

                        case SECTION: {
                            Style style; // default if no style specified

                            String styleName = Style.USE_PREVIOUS_STYLE;
                            while (item.nonEmpty()) {
                                if (item.first() instanceof Polylist sectItem
                                        && ((Polylist) item.first()).nonEmpty()) {
                                    if (sectItem.first().equals("style")) {
                                        if (!sectItem.rest().isEmpty()) {
                                            // Non-empty: get specified style

                                            if (sectItem.second() instanceof String) {
                                                styleName = (String) sectItem.second();
                                                if (!styleName.equals(Style.USE_PREVIOUS_STYLE)) {
                                                    style = Advisor.getStyle(styleName);

                                                    if (style == null) {
                                                        ErrorLog.log(ErrorLog.WARNING, "Style " + styleName + " not found");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                item = item.rest();
                            }
                            int index = measure * slotsPerBar;
                            if (seenFirstChord) {
                                index += slotsPerBar;
                            }
                            //System.out.println("style: " + styleName);
                            chords.addSection(styleName, index, isPhrase);
                        }
                        break;

                    }
                    continue;
                }


                // if it isn't a Polylist it must be a String
                String thisChord = (String) ob;
                seenFirstChord = true;

                if (thisChord.equals(SLASHSTRING)) {
                    accumulated += spacing;
                } else {
                    if (previousChord != null) {
                        String chordToAdd = Key.transposeChord(previousChord, rise, key);
                        chords.addChord(chordToAdd, accumulated);
                    }

                    previousChord = thisChord;
                    accumulated = spacing;

                    // last chord will be forced by NOCHORD inserted at start
                }
            }
            measure++;
        }
    }

    static public void addToMelodyFromPolylist(Polylist in, MelodyPart melody,
                                               int rise, int slotsPerBeat,
                                               Key key) {
        int volume = MAX_VOLUME;

        while (in.nonEmpty()) {
            Object ob = in.first();
            boolean handled = false;
            if (ob instanceof NoteSymbol) {
                Note note = ((NoteSymbol) ob).transpose(rise).toNote();
                note.setVolume(volume);
                melody.addNote(note);
                handled = true;
            } else if (ob instanceof VolumeSymbol) {
                volume = ((VolumeSymbol) ob).getVolume();
            } else if (ob instanceof String stringOb) {
                Polylist item = Polylist.explode(stringOb);
                Character c = (Character) item.first();
                if (Character.isLetter(c)) {
                    if (Character.isLowerCase(c)) {
                        Note note =
                                Key.noteFromLeadsheet(stringOb, rise, slotsPerBeat, key);
                        if (note != null) {
                            melody.addNote(note);
                            handled = true;
                        }
                    }
                }
            }
            if (!handled) {
                ErrorLog.log(ErrorLog.SEVERE,
                        "Ignoring 1 unidentified item: " + ob + " of " + ob.getClass());
            }
            in = in.rest();
        }
    }

    public static int lookup(String arg, String[] table) {
        for (int i = 0; i < table.length; i++) {
            if (table[i].equals(arg)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean endsBar(Object s) {
        return s.equals(BARSTRING) || s.equals(COMMASTRING);
    }

    public static Score readFile(File f) throws FileNotFoundException {
        Score score = new Score();
        FileInputStream leadStream = new FileInputStream(f);
        Tokenizer tokenizer = new Tokenizer(leadStream);
        Leadsheet.readLeadSheet(tokenizer, score);
        return score;
    }
}

