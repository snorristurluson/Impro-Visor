/**
 * This Java Class is part of the Impro-Visor Application.
 *
 * Copyright (C) 2015-2017 Robert Keller and Harvey Mudd College
 *
 * Impro-Visor is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * Impro-Visor is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of merchantability or fitness
 * for a particular purpose. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Impro-Visor; if not, write to the Free Software Foundation, Inc., 51 Franklin
 * St, Fifth Floor, Boston, MA 02110-1301 USA
 */

package imp.voicing;

/**
 * facilitates hand movement by moving the hands between the lower and upper limits based on parameters set by the user
 * @author Daniel Scanteianu
 */
public class HandManager {
    private int leftHandLowerLimit;
    private int rightHandLowerLimit;
    private int leftHandUpperLimit;
    private int rightHandUpperLimit;
    private int leftHandSpread;
    private int rightHandSpread;
    private int leftHandMinNotes;

    public HandManager() {
        resetHands();
    }
    /*
    public HandManager(int leftHandLowerLimit, int rightHandLowerLimit, int leftHandUpperLimit, int rightHandUpperLimit, int leftHandSpread, int rightHandSpread, int leftHandMinNotes, int leftHandMaxNotes, int rightHandMinNotes, int preferredMotion, int preferredMotionRange) {
        this.leftHandLowerLimit = leftHandLowerLimit;
        this.rightHandLowerLimit = rightHandLowerLimit;
        this.leftHandUpperLimit = leftHandUpperLimit;
        this.rightHandUpperLimit = rightHandUpperLimit;
        this.leftHandSpread = leftHandSpread;
        this.rightHandSpread = rightHandSpread;
        this.leftHandMinNotes = leftHandMinNotes;
        this.leftHandMaxNotes = leftHandMaxNotes;
        this.rightHandMinNotes = rightHandMinNotes;
        this.preferredMotion = preferredMotion;
        this.preferredMotionRange = preferredMotionRange;
    }*/
    private int leftHandMaxNotes;
    private int rightHandMinNotes;
    private int rightHandMaxNotes;
    private int leftHandLowestNote;//used for calculating limits for current chord
    private int rightHandLowestNote;//used for calculating the limits for current chord
    private int preferredMotion;//set positive for moving up when possible, negative for moving down when possible, zero to keep the hands in the same place
    private int preferredMotionRange;//this is the plus/minus for preferred motion
    
    public void getSettings(AutomaticVoicingSettings av)
    {
        setLeftHandLowerLimit(av.getLeftHandLowerLimit());
        setLeftHandUpperLimit(av.getLeftHandUpperLimit());
        setLeftHandSpread(av.getLeftHandSpread());
        setRightHandLowerLimit(av.getRightHandLowerLimit());
        setRightHandUpperLimit(av.getRightHandUpperLimit());
        setRightHandSpread(av.getRightHandSpread());
        setLeftHandMinNotes(av.getLeftHandMinNotes());
        setLeftHandMaxNotes(av.getLeftHandMaxNotes());
        setRightHandMinNotes(av.getRightHandMinNotes());
        setRightHandMaxNotes(av.getRightHandMaxNotes());
        setPreferredMotion(av.getPreferredMotion());
        setPreferredMotionRange(av.getPreferredMotionRange());
    }
    
    /**
     * randomly generates a number of notes for the LH to play within range
     * @return a number of notes
     */
    public int getNumLeftNotes()
    {
        return (int)Math.round(Math.random()*(leftHandMaxNotes-leftHandMinNotes)) + leftHandMinNotes;
    }
    /**
     * randomly generate a number of notes for the RH to play within range
     * @return a number of notes
     */
    public int getNumRightNotes()
    {
        return (int)Math.round(Math.random()*(rightHandMaxNotes-rightHandMinNotes)) + rightHandMinNotes;
    }

    public int getLeftHandLowerLimit() {
        return leftHandLowerLimit;
    }
    
    public void setLeftHandLowerLimit(int leftHandLowerLimit) {
        this.leftHandLowerLimit = leftHandLowerLimit;
    }

    public int getRightHandLowerLimit() {
        return rightHandLowerLimit;
    }

    public void setRightHandLowerLimit(int rightHandLowerLimit) {
        this.rightHandLowerLimit = rightHandLowerLimit;
    }

    public int getLeftHandUpperLimit() {
        return leftHandUpperLimit;
    }

    public void setLeftHandUpperLimit(int leftHandUpperLimit) {
        this.leftHandUpperLimit = leftHandUpperLimit;
    }

    public int getRightHandUpperLimit() {
        return rightHandUpperLimit;
    }

    public void setRightHandUpperLimit(int rightHandUpperLimit) {
        this.rightHandUpperLimit = rightHandUpperLimit;
    }

    public int getLeftHandSpread() {
        return leftHandSpread;
    }

    public void setLeftHandSpread(int leftHandSpread) {
        this.leftHandSpread = leftHandSpread;
    }

    public int getRightHandSpread() {
        return rightHandSpread;
    }

    public void setRightHandSpread(int rightHandSpread) {
        this.rightHandSpread = rightHandSpread;
    }

    public int getLeftHandMinNotes() {
        return leftHandMinNotes;
    }

    public int getLeftHandLowestNote() {
        return leftHandLowestNote;
    }

    public void setLeftHandLowestNote(int leftHandLowestNote) {
        this.leftHandLowestNote = leftHandLowestNote;
    }

    public void setLeftHandMinNotes(int leftHandMinNotes) {
        this.leftHandMinNotes = leftHandMinNotes;
    }

    public int getLeftHandMaxNotes() {
        return leftHandMaxNotes;
    }

    public void setLeftHandMaxNotes(int leftHandMaxNotes) {
        this.leftHandMaxNotes = leftHandMaxNotes;
    }

    public int getRightHandMinNotes() {
        return rightHandMinNotes;
    }

    public void setRightHandMinNotes(int rightHandMinNotes) {
        this.rightHandMinNotes = rightHandMinNotes;
    }

    public int getRightHandMaxNotes() {
        return rightHandMaxNotes;
    }

    public void setRightHandMaxNotes(int rightHandMaxNotes) {
        this.rightHandMaxNotes = rightHandMaxNotes;
    }

    public int getRightHandLowestNote() {
        return rightHandLowestNote;
    }

    public void setRightHandLowestNote(int rightHandLowestNote) {
        this.rightHandLowestNote = rightHandLowestNote;
    }

    public int getPreferredMotion() {
        return preferredMotion;
    }

    public void setPreferredMotion(int preferredMotion) {
        this.preferredMotion = preferredMotion;
    }

    public int getPreferredMotionRange() {
        return preferredMotionRange;
    }

    public void setPreferredMotionRange(int preferredMotionRange) {
        this.preferredMotionRange = preferredMotionRange;
    }
    /**
     * moves hands between chords, ensuring that voicings are in ranges.
     */
    public void repositionHands()
    {
      //System.out.println("lll "+this.leftHandLowerLimit+"rll "+this.rightHandLowerLimit);
      //System.out.println("lul "+this.leftHandUpperLimit+"rul "+this.rightHandUpperLimit);
      //System.out.println("ll "+this.leftHandLowestNote+"rl "+this.rightHandLowestNote);
       leftHandLowestNote=(int)Math.round(leftHandLowestNote+((Math.random()*2.0*preferredMotionRange)-preferredMotionRange)+preferredMotion);
       rightHandLowestNote=(int)Math.round(rightHandLowestNote+((Math.random()*2.0*preferredMotionRange)-preferredMotionRange)+preferredMotion);
       if(leftHandLowestNote<leftHandLowerLimit)
           resetLH();
       if(rightHandLowestNote<rightHandLowerLimit)
           resetRH();
       if(leftHandLowestNote+leftHandSpread>leftHandUpperLimit)
           resetLH();
       if(rightHandLowestNote+rightHandSpread>rightHandUpperLimit)
           resetRH();
       
    }
    /**
     * sets hands to a starting position based on settings
     */
    public void resetHands()
    {
        if(preferredMotion>0)//to allow motion up
        {
            leftHandLowestNote=leftHandLowerLimit;
            rightHandLowestNote=rightHandLowerLimit;
        }
        else if(preferredMotion<0)//to allow motion down
        {
            leftHandLowestNote=leftHandUpperLimit-leftHandSpread;
            rightHandLowestNote=rightHandUpperLimit-rightHandSpread;
        }
        else //start in the middle to be able to go both ways
        {
            leftHandLowestNote=(leftHandUpperLimit-leftHandSpread+leftHandLowerLimit)/2;
            rightHandLowestNote=(rightHandUpperLimit-rightHandSpread+rightHandLowerLimit)/2;
        }
        VoicingDebug.println("Both Hands Reset");
    }
    public void resetLH()
    {
        if(preferredMotion>0)//to allow motion up
        {
            leftHandLowestNote=leftHandLowerLimit;
            //rightHandLowestNote=rightHandLowerLimit;
        }
        else if(preferredMotion<0)//to allow motion down
        {
            leftHandLowestNote=leftHandUpperLimit-leftHandSpread;
            //rightHandLowestNote=rightHandUpperLimit-rightHandSpread;
        }
        else //start in the middle to be able to go both ways
        {
            leftHandLowestNote=(leftHandUpperLimit-leftHandSpread+leftHandLowerLimit)/2;
            //rightHandLowestNote=(rightHandUpperLimit-rightHandSpread+rightHandLowerLimit)/2;
        }
    }
    public void resetRH()
    {
        if(preferredMotion>0)//to allow motion up
        {
            //leftHandLowestNote=leftHandLowerLimit;
            rightHandLowestNote=rightHandLowerLimit;
        }
        else if(preferredMotion<0)//to allow motion down
        {
            //leftHandLowestNote=leftHandUpperLimit-leftHandSpread;
            rightHandLowestNote=rightHandUpperLimit-rightHandSpread;
        }
        else //start in the middle to be able to go both ways
        {
            //leftHandLowestNote=(leftHandUpperLimit-leftHandSpread+leftHandLowerLimit)/2;
            rightHandLowestNote=(rightHandUpperLimit-rightHandSpread+rightHandLowerLimit)/2;
        }
        VoicingDebug.println("RH Reset");
    }
    public int getLeftLowerBound()
    {
        return leftHandLowestNote;
    }
     public int getLeftUpperBound()
    {
        return leftHandLowestNote+leftHandSpread;
    }
      public int getRightLowerBound()
    {
        return rightHandLowestNote;
    }
       public int getRightUpperBound()
    {
        return rightHandLowestNote+rightHandSpread;
    }
}
