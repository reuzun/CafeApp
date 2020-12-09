package ceng.estu.controller;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.GlyphIconName;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class MusicPlayerController  implements Initializable {
   /* @FXML
    public AnchorPane mainPanel;


    /*private double xOffset = 0;
    private double yOffset = 0;*/

    @FXML
    private ListView lv;
    @FXML
    private MediaView img;
    @FXML
    private Text VolumeText;
    @FXML
    private Text remainingTime;
    @FXML
    private Slider playingTimeSlider = new JFXSlider();
    @FXML
    private Slider volumeSlider = new Slider();
    @FXML
    private Text nameOfSong;
    @FXML
    private FontAwesomeIcon playButton;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            adjustSongRepository();
        } catch (Exception e) {
        }
    }

    @javafx.fxml.FXML
    public void listenNextSong(ActionEvent actionEvent) throws MalformedURLException {
        this.playRandomMusic();
    }

    @javafx.fxml.FXML
    public void listenLastSong(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void startMusic(ActionEvent actionEvent) {

        if(player.getStatus() == MediaPlayer.Status.PLAYING) {
            MainPanelController.staticLogo.setTextFill((Color.color(0,0,0,1)));
            player.pause();
            playButton.setIconName("PLAY");
        }
        else {
            MainPanelController.staticLogo.setTextFill(Color.valueOf("#0f4ddb"));
            player.play();
            playButton.setIconName("PAUSE");
        }
    }

    private void adjustSongRepository() {
        String fileURL = "musics";
        System.out.println(fileURL);
        File file = new File(fileURL);
        for (File f : file.listFiles()) {
            //if(!f.getName().contains("mp4"))continue;
            String name = f.getAbsolutePath();
            name = procesString(name);
            lv.getItems().add(name.substring(name.lastIndexOf("\\")+1));
        }
    }

    /*private String adjustName(String name){
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < name.length() ; i++){
            if(name.charAt(i) == '\\')
            sb.append(name.charAt(i));
        }
    }*/

    private String procesString(String name){
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ;i < name.toCharArray().length ; i++){
            if(name.charAt(i)=='\\' && name.charAt(i+1) != '\\')sb.append("\\\\");
            else sb.append(String.valueOf((char)name.charAt(i)));
        }
        name = sb.toString();
        return name;
    }


    @javafx.fxml.FXML
    public void handleCloseAction(ActionEvent actionEvent) {
        System.exit(-1);
    }


    static MediaPlayer player;
    static boolean voiceFlag = true;

    @FXML
    public void startApplication(MouseEvent event) throws MalformedURLException {
        if (event.getClickCount() == 2) {
            MainPanelController.staticLogo.setTextFill(Color.valueOf("#0f4ddb"));
            File musicFile = new File("musics" + "\\\\" + lv.getSelectionModel().getSelectedItem());
            nameOfSong.setText(String.valueOf(lv.getSelectionModel().getSelectedItem()));
            Media music = new Media(musicFile.toURI().toURL().toString());


            if (null != player && player.getStatus() == MediaPlayer.Status.PLAYING)
                player.pause();
            player = new MediaPlayer(music);
            playButton.setIconName("PAUSE");
            if(voiceFlag) {
                player.setVolume(0.25);
                voiceFlag = false;
            }else {
                player.setVolume(volumeSlider.getValue());
            }
            player.setOnReady(() -> {
                player.setStartTime(Duration.ZERO);
                img.setMediaPlayer(player);

                //VOLUME PROPERTİES
                volumeSlider.setMin(0);
                volumeSlider.setMax(0.5);
                volumeSlider.setValue(player.getVolume());
                VolumeText.setText(String.valueOf((int) (volumeSlider.getValue() * 100*2)));

                volumeSlider.valueProperty().addListener(evs -> {
                    player.setVolume(volumeSlider.getValue());
                    VolumeText.setText(String.valueOf((int) (volumeSlider.getValue() * 100*2)));

                });

                playingTimeSlider.setMax(player.getStopTime().toMillis());
                player.currentTimeProperty().addListener(ev -> {
                    remainingTime.setText(String.valueOf((int) (player.getStopTime().toSeconds() - player.getCurrentTime().toSeconds())));
                    playingTimeSlider.valueProperty().setValue(player.getCurrentTime().toMillis());
                    if (!playingTimeSlider.isValueChanging()) {
                        playingTimeSlider.setValue(player.getCurrentTime().toMillis());
                    }
                });
                player.play();
                //StaticController.Amblem.setFill(Color.rgb(172,60,60,1));
                playingTimeSlider.valueProperty().addListener(ov -> {
                    if (playingTimeSlider.isValueChanging()) {
                        player.seek(Duration.millis(new Duration(playingTimeSlider.getValue()).toMillis()));
                    }

                });

                player.setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            playRandomMusic();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            });


        }

    }

    private void playRandomMusic() throws MalformedURLException {
        MainPanelController.staticLogo.setTextFill(Color.valueOf("#0f4ddb"));
        int random = (int) (Math.random() * lv.getItems().size());
        lv.getSelectionModel().select(random);
        File musicFile = new File("musics" + "\\" + lv.getSelectionModel().getSelectedItem() );
        nameOfSong.setText(String.valueOf(lv.getSelectionModel().getSelectedItem()));
        Media music = new Media(musicFile.toURI().toURL().toString());

        if (null != player && player.getStatus() == MediaPlayer.Status.PLAYING)
            player.pause();
        player = new MediaPlayer(music);
        player.setVolume(volumeSlider.getValue());
        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                player.setStartTime(Duration.ZERO);
                img.setMediaPlayer(player);



                //VOLUME PROPERTİES
                volumeSlider.setMin(0);
                volumeSlider.setMax(0.5);

                VolumeText.setText(String.valueOf((int)(player.getVolume() * 100*2)));

                volumeSlider.valueProperty().addListener(evs -> {
                    player.setVolume(volumeSlider.getValue());
                    VolumeText.setText(String.valueOf((int)(volumeSlider.getValue() * 100 *2)));

                });

                playingTimeSlider.setMax(player.getStopTime().toMillis());
                player.currentTimeProperty().addListener(ev -> {
                    remainingTime.setText(String.valueOf((int) (player.getStopTime().toSeconds() - player.getCurrentTime().toSeconds())));
                    playingTimeSlider.valueProperty().setValue(player.getCurrentTime().toMillis());
                    if (!playingTimeSlider.isValueChanging()) {
                        playingTimeSlider.setValue(player.getCurrentTime().toMillis());
                    }
                });

                playingTimeSlider.valueProperty().addListener(ov -> {
                    if (playingTimeSlider.isValueChanging()) {
                        player.seek(Duration.millis(new Duration(playingTimeSlider.getValue()).toMillis()));
                    }

                });
                player.play();
                //StaticController.Amblem.setFill(Color.rgb(172,60,60,1));
                player.setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            playRandomMusic();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }
}
