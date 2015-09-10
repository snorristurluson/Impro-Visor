/**
 * This Java Class is part of the Impro-Visor Application.
 *
 * Copyright (C) 2015 Robert Keller and Harvey Mudd College.
 *
 * Impro-Visor is free software; you can redistribute it and/or modifyc it under
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
package imp.gui;

import imp.ImproVisor;
import imp.data.ActiveTrading.ActiveTrading;
import imp.data.ActiveTrading.TradeListener;
import imp.util.TransformFilter;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 * This class embodies the UI and functionality of interactive trading.
 *
 * An instance of ActiveTradingWindow works by keeping a reference to the instance
 * of notate that instantiated itself. Each instance of ActiveTradingWindow is
 * destroyed when it's associated UI window is closed. When interactive trading
 * is initialized (the method '.startTrading()' is called), notate is triggered
 * to play its current open chorus; trading is made possible
 * by dispatching events based upon the position of the play head in said
 * chorus. Events are scheduled in three main phases, each phase with
 * its respective method:
 *
 * User turn - When the user plays. During this phase,
 * the user input is recorded into
 * the instance variable 'tradeScore'.
 * associated method: .userTurn()
 *
 * Processing - When processing takes place. During this
 * phase, user input is no longer recorded.
 * TradeScore is manipulated to produce a
 * finalized response for the computer to play.
 * associated method: .processInput()
 *
 * Computer Turn - When the computer plays. During this phase,
 * the response finalized in processing phase
 * is played. When the computer is first,
 * some solo pre-generated solo is used.
 * associated method: .computerTurn()
 *
 *
 * @author Zachary Kondak
 */
public class ActiveTradingWindow extends javax.swing.JFrame implements TradeListener, ActionListener {

    private final ActiveTrading activeTrading;
    private boolean isUserInputError = false;
    private final Integer initialTradeLength = 4;
    public static final java.awt.Point initialOpenPoint = new java.awt.Point(10, 0);

    public ActiveTradingWindow(Notate notate) {
        activeTrading = new ActiveTrading(notate);
        initComponents();
        notate.populateGenericGrammarMenu(tradeGrammarMenu);
        populateMusicianList();
        Component[] modes = modeMenu.getMenuComponents();
        for (Component c : modes) {
            JRadioButtonMenuItem mode = (JRadioButtonMenuItem) c;
            mode.addActionListener(this);
        }
        updateMusician();
        updateGUIComponents();
        activeTrading.register(this);
        tradeLengthSpinner.setValue(initialTradeLength);
        loopToggle.setSelected(true);
        updateLoop();
    }

    public void updateGUIComponents() {
        Integer tradeLength = activeTrading.getMeasures();
        updateProcessTimeText();
        loopToggle.setSelected(activeTrading.getIsLoop());
        int newVol = activeTrading.getVolume();
        volumeSlider.setValue(newVol);
        volume.setText(newVol + "%");
        userFirstButton.setSelected(activeTrading.getIsUserLeading());
        Double newTempo = activeTrading.getTempo();
        tempoSlider.setValue(newTempo.intValue());
        tempoLabel.setText(newTempo.toString());
        modeStatus.setText("Mode: " + activeTrading.getTradeMode());
        transformStatus.setText("Transform File: " + activeTrading.getMusician());
        String gramm = activeTrading.getGrammar();
        tradeGrammarMenu.setText(gramm);
        grammarStatus.setText("Grammar: " + gramm);
    }

    public void tradingWindowOpened() {
        activeTrading.setNotateDefaults();
        updateGUIComponents();
    }

    private void tradingWindowClosed() {
        activeTrading.tradingWindowClosed();
    }

    private void startTradingButtonPressed() {
        if (!isUserInputError) {
            updateMusician();
            updateTradeMode();
            activeTrading.startOrStop();
        }
    }

    private void updateProcessTimeText() {
        int slotsForProcessing = activeTrading.getSlotsForProcessing();
        if (slotsForProcessing == 1) {
            processTimeSelector.setText("0.0");
        } else {
            Double newBeatVal = activeTrading.slotsToBeats(slotsForProcessing);
            processTimeSelector.setText(newBeatVal.toString());
        }
    }

    private void updateTradeMode() {
        String newMode = getFromDropDown(modeMenu);
        activeTrading.setTradeMode(newMode);
        modeStatus.setText("Mode: " + newMode);
    }

    private void updateLoop() {
        activeTrading.setLoop(loopToggle.isSelected());
    }

    private void updateTempo() {
        Integer tempo = tempoSlider.getValue();
        tempoLabel.setText(tempo.toString());
        activeTrading.setTempo(tempo);
    }

    private void updateMusician() {
        String newMusician = getFromDropDown(tradeMusicianMenu);
        activeTrading.setMusician(newMusician);
        transformStatus.setText("Transform File: " + newMusician);
    }

    private void updateVolume() {
        Integer newVol = volumeSlider.getValue();
        activeTrading.setVolume(newVol);
        volume.setText(newVol + "%");
    }

    private void updateProcessTime() {
        activeTrading.setProcessTime(tryFloat(processTimeSelector.getText()));
    }

    private void updateIsUserLeading() {
        activeTrading.setIsUserLeading(userFirstButton.isSelected());
    }

    private String getFromDropDown(JMenu menu) {
        Component[] modes = menu.getMenuComponents();
        String selection = "";
        for (Component mode : modes) {
            JRadioButtonMenuItem modeButton = (JRadioButtonMenuItem) mode;
            if (modeButton.isSelected()) {
                selection = modeButton.getText();
                //System.out.println(selection);
                return selection;
            }
        }
        return selection;
    }

    public void refreshSelectedGrammar(String gram) {
        tradeGrammarMenu.setText(gram);
        grammarStatus.setText("Grammar: " + activeTrading.getGrammar());
    }

    private void populateMusicianList() {
        File directory = ImproVisor.getTransformDirectory();
        //System.out.println("populating from " + directory);
        if (directory.isDirectory()) {
            String fileName[] = directory.list();

            // 6-25-13 Hayden Blauzvern
            // Fix for Linux, where the file list is not in alphabetic order
            Arrays.sort(fileName, new Comparator<String>() {
                public int compare(String s1, String s2) {
                    return s1.toUpperCase().compareTo(s2.toUpperCase());
                }

            });

            // Add names of grammar files
            for (String name : fileName) {
                if (name.endsWith(TransformFilter.EXTENSION)) {
                    int len = name.length();
                    String stem = name.substring(0, len - TransformFilter.EXTENSION.length());
                    JRadioButtonMenuItem newMusician = new JRadioButtonMenuItem();
                    newMusician.setText(stem);
                    newMusician.addActionListener(this);
                    newMusician.setSelected(true);
                    transformFileSelector.add(newMusician);
                    tradeMusicianMenu.add(newMusician);

                }
            }
        }
        updateMusician();
    }

    public void tradingStarted() {
        startTradingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imp/gui/graphics/toolbar/stop.gif")));
        startTradingButton.setText("Stop Trading");
    }

    public void tradingStopped() {
        startTradingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imp/gui/graphics/toolbar/play.gif")));
        startTradingButton.setText("Start Trading");
    }

    public void trackPlay(ActionEvent e) {
        activeTrading.trackPlay(e);
    }

    public void actionPerformed(ActionEvent e) {
        updateMusician();
        updateTradeMode();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        leadingSelector = new javax.swing.ButtonGroup();
        modeSelector = new javax.swing.ButtonGroup();
        transformFileSelector = new javax.swing.ButtonGroup();
        grammarGroup = new javax.swing.ButtonGroup();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        colorLeft = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        leadSelectors = new javax.swing.JPanel();
        userFirstButton = new javax.swing.JRadioButton();
        improvisorFirstButton = new javax.swing.JRadioButton();
        tradeLengthPanel = new javax.swing.JPanel();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        tradeLengthSpinner = new javax.swing.JSpinner();
        modePanel = new javax.swing.JPanel();
        modeStatus = new javax.swing.JLabel();
        grammarStatus = new javax.swing.JLabel();
        transformStatus = new javax.swing.JLabel();
        playbackControls = new javax.swing.JPanel();
        startTradingButton = new javax.swing.JButton();
        loopToggle = new javax.swing.JCheckBox();
        processTimeSelector = new javax.swing.JTextField();
        volumePanel = new javax.swing.JPanel();
        volumeSlider = new javax.swing.JSlider();
        volume = new javax.swing.JLabel();
        tempoPanel = new javax.swing.JPanel();
        tempoSlider = new javax.swing.JSlider();
        tempoLabel = new javax.swing.JLabel();
        colorRight = new javax.swing.JPanel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        mainTradeMenuBar = new javax.swing.JMenuBar();
        modeMenu = new javax.swing.JMenu();
        tradeRepeat = new javax.swing.JRadioButtonMenuItem();
        tradeRepeatAndRectify = new javax.swing.JRadioButtonMenuItem();
        tradeRandomModify = new javax.swing.JRadioButtonMenuItem();
        tradeWithAMusician = new javax.swing.JRadioButtonMenuItem();
        tradeAbstract = new javax.swing.JRadioButtonMenuItem();
        tradeGrammarSolo = new javax.swing.JRadioButtonMenuItem();
        tradeStore = new javax.swing.JRadioButtonMenuItem();
        tradeMusicianMenu = new javax.swing.JMenu();
        tradeGrammarMenu = new javax.swing.JMenu();

        jScrollPane1.setViewportView(jEditorPane1);

        setTitle("Active Trading - Impro-Visor");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 700, 200));
        setMinimumSize(new java.awt.Dimension(700, 200));
        setSize(new java.awt.Dimension(600, 380));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        colorLeft.setBackground(new java.awt.Color(153, 153, 255));
        colorLeft.setMinimumSize(new java.awt.Dimension(100, 100));
        colorLeft.setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout colorLeftLayout = new javax.swing.GroupLayout(colorLeft);
        colorLeft.setLayout(colorLeftLayout);
        colorLeftLayout.setHorizontalGroup(
            colorLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colorLeftLayout.createSequentialGroup()
                .addComponent(filler4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        colorLeftLayout.setVerticalGroup(
            colorLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colorLeftLayout.createSequentialGroup()
                .addComponent(filler4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(colorLeft, gridBagConstraints);

        leadSelectors.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        leadSelectors.setMaximumSize(new java.awt.Dimension(200, 76));
        leadSelectors.setMinimumSize(new java.awt.Dimension(200, 100));
        leadSelectors.setPreferredSize(new java.awt.Dimension(200, 100));
        leadSelectors.setLayout(new java.awt.GridBagLayout());

        leadingSelector.add(userFirstButton);
        userFirstButton.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        userFirstButton.setSelected(true);
        userFirstButton.setText("User First");
        userFirstButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userFirstButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        leadSelectors.add(userFirstButton, gridBagConstraints);

        leadingSelector.add(improvisorFirstButton);
        improvisorFirstButton.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        improvisorFirstButton.setText("Impro-Visor First");
        improvisorFirstButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                improvisorFirstButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        leadSelectors.add(improvisorFirstButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(leadSelectors, gridBagConstraints);

        tradeLengthPanel.setMaximumSize(new java.awt.Dimension(200, 123));
        tradeLengthPanel.setMinimumSize(new java.awt.Dimension(200, 50));
        tradeLengthPanel.setPreferredSize(new java.awt.Dimension(200, 50));
        tradeLengthPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        tradeLengthPanel.add(filler9, gridBagConstraints);

        tradeLengthSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(4), Integer.valueOf(1), null, Integer.valueOf(1)));
        tradeLengthSpinner.setToolTipText("The number of bars in melody.");
        tradeLengthSpinner.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Length of Trade (bars)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Helvetica", 1, 12))); // NOI18N
        tradeLengthSpinner.setMinimumSize(new java.awt.Dimension(200, 50));
        tradeLengthSpinner.setPreferredSize(new java.awt.Dimension(200, 50));
        tradeLengthSpinner.setValue(new Integer(4));
        tradeLengthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lengthOfTradeSet(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        tradeLengthPanel.add(tradeLengthSpinner, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(tradeLengthPanel, gridBagConstraints);

        modePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        modePanel.setPreferredSize(new java.awt.Dimension(120, 100));
        modePanel.setLayout(new java.awt.GridBagLayout());

        modeStatus.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        modeStatus.setText("Mode: ___ ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        modePanel.add(modeStatus, gridBagConstraints);

        grammarStatus.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        grammarStatus.setText("Grammar: ___    ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        modePanel.add(grammarStatus, gridBagConstraints);

        transformStatus.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        transformStatus.setText("Transform File: ___ ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        modePanel.add(transformStatus, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(modePanel, gridBagConstraints);

        playbackControls.setMinimumSize(new java.awt.Dimension(261, 50));
        playbackControls.setPreferredSize(new java.awt.Dimension(261, 50));
        playbackControls.setLayout(new java.awt.GridBagLayout());

        startTradingButton.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        startTradingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imp/gui/graphics/toolbar/play.gif"))); // NOI18N
        startTradingButton.setLabel("Start Trading");
        startTradingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTradingButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 38;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        playbackControls.add(startTradingButton, gridBagConstraints);
        startTradingButton.getAccessibleContext().setAccessibleDescription("");

        loopToggle.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        loopToggle.setSelected(true);
        loopToggle.setText("Loop");
        loopToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loopToggleActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(6, 19, 11, 1);
        playbackControls.add(loopToggle, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(playbackControls, gridBagConstraints);

        processTimeSelector.setBackground(new java.awt.Color(238, 238, 238));
        processTimeSelector.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        processTimeSelector.setText("0.5");
        processTimeSelector.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Processing time (in beats)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Helvetica", 0, 12))); // NOI18N
        processTimeSelector.setMaximumSize(new java.awt.Dimension(50, 2147483647));
        processTimeSelector.setMinimumSize(new java.awt.Dimension(50, 50));
        processTimeSelector.setPreferredSize(new java.awt.Dimension(29, 50));
        processTimeSelector.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                processTimeSelectorCaretUpdate(evt);
            }
        });
        processTimeSelector.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                processTimeSelectorFocusLost(evt);
            }
        });
        processTimeSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processTimeSelectorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(processTimeSelector, gridBagConstraints);

        volumePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Volume of Response", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Helvetica", 0, 12))); // NOI18N
        volumePanel.setMaximumSize(new java.awt.Dimension(200, 76));
        volumePanel.setMinimumSize(new java.awt.Dimension(200, 50));
        volumePanel.setPreferredSize(new java.awt.Dimension(200, 50));
        volumePanel.setLayout(new java.awt.GridBagLayout());

        volumeSlider.setMaximumSize(new java.awt.Dimension(150, 29));
        volumeSlider.setMinimumSize(new java.awt.Dimension(150, 29));
        volumeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volumeSliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        volumePanel.add(volumeSlider, gridBagConstraints);

        volume.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        volume.setText("50%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        volumePanel.add(volume, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(volumePanel, gridBagConstraints);

        tempoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tempo (Beats/Minute)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Helvetica", 1, 12))); // NOI18N
        tempoPanel.setMaximumSize(new java.awt.Dimension(200, 76));
        tempoPanel.setMinimumSize(new java.awt.Dimension(200, 76));
        tempoPanel.setPreferredSize(new java.awt.Dimension(200, 50));
        tempoPanel.setLayout(new java.awt.GridBagLayout());

        tempoSlider.setMaximum(300);
        tempoSlider.setMinimum(30);
        tempoSlider.setValue(120);
        tempoSlider.setMaximumSize(new java.awt.Dimension(150, 29));
        tempoSlider.setMinimumSize(new java.awt.Dimension(150, 29));
        tempoSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tempoSliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        tempoPanel.add(tempoSlider, gridBagConstraints);

        tempoLabel.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        tempoLabel.setText("120");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        tempoPanel.add(tempoLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(tempoPanel, gridBagConstraints);

        colorRight.setBackground(new java.awt.Color(153, 153, 255));
        colorRight.setMinimumSize(new java.awt.Dimension(100, 100));
        colorRight.setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout colorRightLayout = new javax.swing.GroupLayout(colorRight);
        colorRight.setLayout(colorRightLayout);
        colorRightLayout.setHorizontalGroup(
            colorRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colorRightLayout.createSequentialGroup()
                .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        colorRightLayout.setVerticalGroup(
            colorRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colorRightLayout.createSequentialGroup()
                .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(colorRight, gridBagConstraints);

        mainTradeMenuBar.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N

        modeMenu.setText("TradeMode");
        modeSelector.add(modeMenu);

        modeSelector.add(tradeRepeat);
        tradeRepeat.setText("Repeat");
        modeMenu.add(tradeRepeat);

        modeSelector.add(tradeRepeatAndRectify);
        tradeRepeatAndRectify.setText("Repeat and Rectify");
        modeMenu.add(tradeRepeatAndRectify);

        modeSelector.add(tradeRandomModify);
        tradeRandomModify.setSelected(true);
        tradeRandomModify.setText("Modify and Rectify");
        modeMenu.add(tradeRandomModify);

        modeSelector.add(tradeWithAMusician);
        tradeWithAMusician.setText("Use Transforms");
        modeMenu.add(tradeWithAMusician);

        modeSelector.add(tradeAbstract);
        tradeAbstract.setText("Abstract");
        modeMenu.add(tradeAbstract);

        modeSelector.add(tradeGrammarSolo);
        tradeGrammarSolo.setText("Grammar Solo");
        modeMenu.add(tradeGrammarSolo);

        modeSelector.add(tradeStore);
        tradeStore.setText("Chop and Memorize");
        modeMenu.add(tradeStore);

        mainTradeMenuBar.add(modeMenu);

        tradeMusicianMenu.setText("Transform File");
        mainTradeMenuBar.add(tradeMusicianMenu);

        tradeGrammarMenu.setText("Grammar");
        mainTradeMenuBar.add(tradeGrammarMenu);

        setJMenuBar(mainTradeMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        tradingWindowClosed();
    }//GEN-LAST:event_formWindowClosed

    private void startTradingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTradingButtonActionPerformed
        startTradingButtonPressed();
    }//GEN-LAST:event_startTradingButtonActionPerformed

    private void processTimeSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processTimeSelectorActionPerformed
        updateProcessTimeText();
    }//GEN-LAST:event_processTimeSelectorActionPerformed

    private void processTimeSelectorCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_processTimeSelectorCaretUpdate
        updateProcessTime();
    }//GEN-LAST:event_processTimeSelectorCaretUpdate

    private void processTimeSelectorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_processTimeSelectorFocusLost
        updateProcessTimeText();
    }//GEN-LAST:event_processTimeSelectorFocusLost

    private void volumeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volumeSliderStateChanged
        updateVolume();
    }//GEN-LAST:event_volumeSliderStateChanged

    private void loopToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loopToggleActionPerformed
        updateLoop();
    }//GEN-LAST:event_loopToggleActionPerformed

    private void tempoSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tempoSliderStateChanged
        updateTempo();
    }//GEN-LAST:event_tempoSliderStateChanged

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        tradingWindowClosed();
    }//GEN-LAST:event_formComponentHidden

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        tradingWindowOpened();
    }//GEN-LAST:event_formComponentShown

    private void userFirstButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userFirstButtonActionPerformed
        updateIsUserLeading();
    }//GEN-LAST:event_userFirstButtonActionPerformed

    private void improvisorFirstButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_improvisorFirstButtonActionPerformed
        updateIsUserLeading();
    }//GEN-LAST:event_improvisorFirstButtonActionPerformed

    private void lengthOfTradeSet(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lengthOfTradeSet
        activeTrading.updateTradeLength("" + tradeLengthSpinner.getValue());
    }//GEN-LAST:event_lengthOfTradeSet

    private double tryDouble(String number) {
        double newNumber;
        try {
            newNumber = Double.parseDouble(number);
            isUserInputError = false;
        } catch (Exception e) {
            isUserInputError = true;
            newNumber = 0;
        }
        return newNumber;
    }

    private float tryFloat(String number) {
        float newNumber;
        try {
            newNumber = Float.parseFloat(number);
            isUserInputError = false;
        } catch (Exception e) {
            isUserInputError = true;
            newNumber = 0;
        }
        return newNumber;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel colorLeft;
    private javax.swing.JPanel colorRight;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler9;
    private javax.swing.ButtonGroup grammarGroup;
    private javax.swing.JLabel grammarStatus;
    private javax.swing.JRadioButton improvisorFirstButton;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel leadSelectors;
    private javax.swing.ButtonGroup leadingSelector;
    private javax.swing.JCheckBox loopToggle;
    private javax.swing.JMenuBar mainTradeMenuBar;
    private javax.swing.JMenu modeMenu;
    private javax.swing.JPanel modePanel;
    private javax.swing.ButtonGroup modeSelector;
    private javax.swing.JLabel modeStatus;
    private javax.swing.JPanel playbackControls;
    private javax.swing.JTextField processTimeSelector;
    private javax.swing.JButton startTradingButton;
    private javax.swing.JLabel tempoLabel;
    private javax.swing.JPanel tempoPanel;
    private javax.swing.JSlider tempoSlider;
    private javax.swing.JRadioButtonMenuItem tradeAbstract;
    private javax.swing.JMenu tradeGrammarMenu;
    private javax.swing.JRadioButtonMenuItem tradeGrammarSolo;
    private javax.swing.JPanel tradeLengthPanel;
    private javax.swing.JSpinner tradeLengthSpinner;
    private javax.swing.JMenu tradeMusicianMenu;
    private javax.swing.JRadioButtonMenuItem tradeRandomModify;
    private javax.swing.JRadioButtonMenuItem tradeRepeat;
    private javax.swing.JRadioButtonMenuItem tradeRepeatAndRectify;
    private javax.swing.JRadioButtonMenuItem tradeStore;
    private javax.swing.JRadioButtonMenuItem tradeWithAMusician;
    private javax.swing.ButtonGroup transformFileSelector;
    private javax.swing.JLabel transformStatus;
    private javax.swing.JRadioButton userFirstButton;
    private javax.swing.JLabel volume;
    private javax.swing.JPanel volumePanel;
    private javax.swing.JSlider volumeSlider;
    // End of variables declaration//GEN-END:variables

}
