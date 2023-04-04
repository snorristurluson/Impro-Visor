/**
 * Data structures and methods for storing and playing parallel melodies
 * and chord progressions.
 * The main class here is Score.  A Score can contain several Parts.
 * A Part has a Vector of slots, each of which can hold a Unit.  A
 * Unit is just an interface.  Chord, Note, and Rest are all Units.
 * <p>
 * After creating a Score, a Part or several Parts must be added.
 * A Part starts out with one long Rest, starting from its first
 * slot.
 * <p>
 * After creating a Part, Chords, Notes, or Rests can be inserted into
 * the slots.  (A slot is just the start of a Unit, the rhythm value
 * will continue until a non-empty slot is reached.)
 * <p>
 * A Score can be played by creating a MidiSynth object and calling
 * its play function on the Score.
 * <p>
 * <p>
 * Copyright (C) 2005-2011 Robert Keller and Harvey Mudd College
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
