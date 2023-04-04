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
 *Data structure to store the parameters for automatic voicing generator
 * @author Daniel Scanteianu
 */
public class AutomaticVoicingSettings {
    
    public AutomaticVoicingSettings(){
        setDefaults();
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

    public double getPreviousVoicingMultiplier() {
        return previousVoicingMultiplier;
    }

    public void setPreviousVoicingMultiplier(double previousVoicingMultiplier) {
        this.previousVoicingMultiplier = previousVoicingMultiplier;
    }

    public double getHalfStepAwayMultiplier() {
        return halfStepAwayMultiplier;
    }

    public void setHalfStepAwayMultiplier(double halfStepAwayMultiplier) {
        this.halfStepAwayMultiplier = halfStepAwayMultiplier;
    }

    public double getFullStepAwayMultiplier() {
        return fullStepAwayMultiplier;
    }

    public void setFullStepAwayMultiplier(double fullStepAwayMultiplier) {
        this.fullStepAwayMultiplier = fullStepAwayMultiplier;
    }

    public int getLeftColorPriority() {
        return leftColorPriority;
    }

    public void setLeftColorPriority(int leftColorPriority) {
        this.leftColorPriority = leftColorPriority;
    }

    public int getRightColorPriority() {
        return rightColorPriority;
    }

    public void setRightColorPriority(int rightColorPriority) {
        this.rightColorPriority = rightColorPriority;
    }

    public int getMaxPriority() {
        return maxPriority;
    }

    public void setMaxPriority(int maxPriority) {
        this.maxPriority = maxPriority;
    }

    public double getPriorityMultiplier() {
        return priorityMultiplier;
    }

    public void setPriorityMultiplier(double priorityMultiplier) {
        this.priorityMultiplier = priorityMultiplier;
    }

    public double getRepeatMultiplier() {
        return repeatMultiplier;
    }

    public void setRepeatMultiplier(double repeatMultiplier) {
        this.repeatMultiplier = repeatMultiplier;
    }

    public double getHalfStepReducer() {
        return halfStepReducer;
    }

    public void setHalfStepReducer(double halfStepReducer) {
        this.halfStepReducer = halfStepReducer;
    }

    public double getFullStepReducer() {
        return fullStepReducer;
    }

    //hand params
    public void setFullStepReducer(double fullStepReducer) {
        this.fullStepReducer = fullStepReducer;
    }
    
 
    
    public void setDefaults()
    {
        leftHandLowerLimit=46;
        rightHandLowerLimit=60;
        leftHandUpperLimit=67;
        rightHandUpperLimit=81;
        leftHandSpread=9;
        rightHandSpread=9;
        leftHandMinNotes=1;
        leftHandMaxNotes=2;
        rightHandMinNotes=1;
        rightHandMaxNotes=4;
        //voice leading controls
        preferredMotion=0;
        preferredMotionRange=3;
        previousVoicingMultiplier=4;// multiplier for notes used in previous voicing
        halfStepAwayMultiplier=3;
        fullStepAwayMultiplier=2;
        //voicing control
        leftColorPriority=0;//priority of any color note
        rightColorPriority=0;
        maxPriority=6;//max priority a note in the priority array can have
        priorityMultiplier=.667;//should be between 0 and 1, multiply this by the index in priority array, subtract result from max priority to get note priority
        repeatMultiplier=.3;
        halfStepReducer=0;
        fullStepReducer=.7;
        invertM9=false;
        voiceAll=false;
        rootless=false;
        leftMinInterval=0;
        rightMinInterval=0;
    }
    
    private int leftHandLowerLimit;
    private int rightHandLowerLimit;
    private int leftHandUpperLimit;
    private int rightHandUpperLimit;
    private int leftHandSpread;
    private int rightHandSpread;
    private int leftHandMinNotes;
    private int leftHandMaxNotes;
    private int rightHandMinNotes;
    private int rightHandMaxNotes;
    //voice leading controls
    private int preferredMotion;
    private int preferredMotionRange;
    private double previousVoicingMultiplier;// multiplier for notes used in previous voicing
    private double halfStepAwayMultiplier;
    private double fullStepAwayMultiplier;
    //voicing control
    private int leftColorPriority;//priority of any color note
    private int rightColorPriority;//priority of any color note
    private int maxPriority;//max priority a note in the priority array can have
    private double priorityMultiplier;//should be between 0 and 1, multiply this by the index in priority array, subtract result from max priority to get note priority
    private double repeatMultiplier;
    private double halfStepReducer;
    private double fullStepReducer;
    private boolean invertM9;
    private boolean voiceAll;
    private boolean rootless;
    private int leftMinInterval;
    private int rightMinInterval;

    public int getLeftMinInterval() {
        return leftMinInterval;
    }

    public void setLeftMinInterval(int leftMinInterval) {
        this.leftMinInterval = leftMinInterval;
    }

    public int getRightMinInterval() {
        return rightMinInterval;
    }

    public void setRightMinInterval(int rightMinInterval) {
        this.rightMinInterval = rightMinInterval;
    }
    
    public boolean isRootless() {
        return rootless;
    }
    public boolean getRootless() {
        return rootless;
    }
    public void setRootless(boolean rootless) {
        this.rootless = rootless;
    }

    public boolean isVoiceAll() {
        return voiceAll;
    }
     public boolean getVoiceAll() {
        return voiceAll;
    }
    public void setVoiceAll(boolean voiceAll) {
        this.voiceAll = voiceAll;
    }

    public boolean isInvertM9() {
        return invertM9;
    }

    public void setInvertM9(boolean invertM9) {
        this.invertM9 = invertM9;
    }
    public boolean getInvertM9(){
        return invertM9;
    }
    
       @Override
    public String toString()
      {
        StringBuilder buffer = new StringBuilder();
        buffer.append("(LH-lower-limit " + getLeftHandLowerLimit() + ")\n");
        buffer.append("(RH-lower-limit " + getRightHandLowerLimit() + ")\n");
        buffer.append("(LH-upper-limit " + getLeftHandUpperLimit() + ")\n");
        buffer.append("(RH-upper-limit " + getRightHandUpperLimit() + ")\n");
        buffer.append("(LH-spread " + getLeftHandSpread() + ")\n");
        buffer.append("(RH-spread " + getRightHandSpread() + ")\n");
        buffer.append("(LH-min-notes " + getLeftHandMinNotes() + ")\n");
        buffer.append("(LH-max-notes " + getLeftHandMaxNotes() + ")\n");
        buffer.append("(RH-min-notes " + getRightHandMinNotes() + ")\n");
        buffer.append("(RH-max-notes " + getRightHandMaxNotes() + ")\n");
                    //voice leading controls
        buffer.append("(pref-motion " + getPreferredMotion() + ")\n");
        buffer.append("(pref-motion-range " + getPreferredMotionRange() + ")\n");
        buffer.append("(prev-voicing-multiplier "+(int)(getPreviousVoicingMultiplier()*10) + ")\n");// multiplier for notes used in previous voicing
        buffer.append("(half-step-multiplier "+(int)(getHalfStepAwayMultiplier()*10) + ")\n");
        buffer.append("(full-step-multiplier "+(int)(getFullStepAwayMultiplier()*10) + ")\n");
                    //voicing control
        buffer.append("(LH-color-priority " + getLeftColorPriority() + ")\n");//priority of any color note
        buffer.append("(RH-color-priority " + getRightColorPriority() + ")\n");//priority of any color note
        buffer.append("(max-priority " + getMaxPriority() + ")\n");//max priority a note in the priority array can have
        buffer.append("(priority-multiplier "+(int)( + getPriorityMultiplier()*10) + ")\n");//should be between 0 and 1, multiply this by the index in priority array, subtract result from max priority to get note priority
        buffer.append("(repeat-multiplier "+(int)(getRepeatMultiplier()*10) + ")\n");
        buffer.append("(half-step-reducer "+(int)(getHalfStepReducer()*10) + ")\n");
        buffer.append("(full-step-reducer "+(int)(getFullStepReducer()*10) + ")\n");
        buffer.append("(left-min-interval "+(int)(getLeftMinInterval())+")");
        buffer.append("(right-min-interval "+(int)(getRightMinInterval())+")");
                    if(getInvertM9())
            buffer.append("(invert-9th on)\n");
                    else
            buffer.append("(invert-9th off)\n");
                    if(getVoiceAll())
            buffer.append("(voice-all on)\n");
                    else
            buffer.append("(voice-all off)");
                    if(getRootless())
            buffer.append("(rootless on)");
                    else
            buffer.append("(rootless off)");

    return buffer.toString();
    }
}
