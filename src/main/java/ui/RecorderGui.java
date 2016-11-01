package ui;

import recorder.CommandApi;

import javax.swing.*;
import java.awt.*;

public class RecorderGui {

    public static final String TITLE = "HOT Recorder";
    public static final int FRAME_WIDTH = 300;
    public static final int FRAME_HEIGHT = 150;

    public RecorderGui() {

        JFrame guiFrame = initFrame();
        CommandApi.getCommandInit().execute();

        final JPanel recorderPanel = getRecorderPanel();
        final JPanel playListPanel = getPlayListPanel();


        JButton vegFruitBut = new JButton("Fruit or Veg");
        vegFruitBut.addActionListener(event -> {
                    playListPanel.setVisible(!playListPanel.isVisible());
                    recorderPanel.setVisible(!recorderPanel.isVisible());
                }
        );
        //The JFrame uses the BorderLayout layout manager.
        // Put the two JPanels and JButton in different areas.
        guiFrame.add(recorderPanel, BorderLayout.NORTH);
        guiFrame.add(playListPanel, BorderLayout.CENTER);
        guiFrame.add(vegFruitBut, BorderLayout.SOUTH);
        //make sure the JFrame is visible
        guiFrame.setVisible(true);
    }

    //Note: Typically the main method will be in a
    // separate class. As this is a simple one class
    // example it's all in the one class.
    public static void main(String[] args) {
        new RecorderGui();
    }

    private JFrame initFrame() {
        JFrame guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        guiFrame.setTitle(TITLE);
        guiFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        guiFrame.setLocationRelativeTo(null);
        return guiFrame;
    }

    private JPanel getRecorderPanel() {
        final JPanel recorderPanel = new JPanel();
        JLabel comboLbl = new JLabel("Let the sound begin:");
        JButton record = new JButton("record");
        record.addActionListener(event -> CommandApi.getCommandRecord().execute());
        JButton stop = new JButton("stop");
        stop.addActionListener(event -> CommandApi.getCommandStop().execute());
        recorderPanel.add(comboLbl);
        recorderPanel.add(record);
        recorderPanel.add(stop);
        return recorderPanel;
    }

    private JPanel getPlayListPanel() {
        final JPanel playListPanel = new JPanel();
        playListPanel.setVisible(false);
        JLabel listLbl = new JLabel("Vegetables:");
        String[] audioFiles = {};
        JList audioFilesList = new JList(audioFiles);
        audioFilesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        playListPanel.add(listLbl);
        playListPanel.add(audioFilesList);
        return playListPanel;
    }
}
