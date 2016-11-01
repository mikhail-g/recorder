package gui;

import recorder.Recorder;
import recorder.RecorderImpl;

import javax.swing.*;
import java.awt.*;

/**
 * Main class for recorder gui application
 * <p>
 * Created by Mykhailo on 22.09.16.
 */
public class RecorderGui {

    public static final String PLAYLIST_BUTTON_TEXT = "recorded files";
    public static final String RECORD_BUTTON_TEXT = "record";
    public static final String STOP_BUTTON_TEXT = "stop";
    private static final String TITLE = "HOT Recorder";
    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 150;
    private static Recorder recorder;

    public RecorderGui() {

        JFrame guiFrame = initFrame();
        recorder = RecorderImpl.getInstance();
        recorder.init();

        final JPanel recorderPanel = getRecorderPanel();
        final JPanel playListPanel = getPlayListPanel();


        JButton playListButton = new JButton(PLAYLIST_BUTTON_TEXT);
        playListButton.setVisible(false);
        playListButton.addActionListener(event -> {
                    playListPanel.setVisible(!playListPanel.isVisible());
                    recorderPanel.setVisible(!recorderPanel.isVisible());
                }
        );
        //The JFrame uses the BorderLayout layout manager.
        // Put the two JPanels and JButton in different areas.
        guiFrame.add(recorderPanel, BorderLayout.NORTH);
        guiFrame.add(playListPanel, BorderLayout.CENTER);
        guiFrame.add(playListButton, BorderLayout.SOUTH);
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
        JButton recordButton = new JButton(RECORD_BUTTON_TEXT);
        recordButton.addActionListener(event -> recorder.record());
        JButton stopButton = new JButton(STOP_BUTTON_TEXT);
        stopButton.addActionListener(event -> recorder.stop());
        recorderPanel.add(comboLbl);
        recorderPanel.add(recordButton);
        recorderPanel.add(stopButton);
        return recorderPanel;
    }

    private JPanel getPlayListPanel() {
        final JPanel playListPanel = new JPanel();
        playListPanel.setVisible(false);
        JLabel listLbl = new JLabel("Records list:");
        String[] audioFiles = {};
        JList audioFilesList = new JList(audioFiles);
        audioFilesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        playListPanel.add(listLbl);
        playListPanel.add(audioFilesList);
        return playListPanel;
    }
}
