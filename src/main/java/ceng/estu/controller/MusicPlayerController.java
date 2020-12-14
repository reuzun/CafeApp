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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioTrack;
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
import java.util.Stack;

public class MusicPlayerController implements Initializable {
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

    private Stack<String> playedSongs = new Stack<>();
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private StackPane stackinthepanel;
    @FXML
    private ImageView imgView;
    @FXML
    private AnchorPane musicpanel;
    @FXML
    private JFXButton closeButton;
    @FXML
    private StackPane imgStackPane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            adjustSongRepository();
        } catch (Exception e) {
        }
        img.toFront();
    }

    @javafx.fxml.FXML
    public void listenNextSong(ActionEvent actionEvent) throws MalformedURLException {
        this.playRandomMusic();
    }

    @javafx.fxml.FXML
    public void listenLastSong(ActionEvent actionEvent) throws MalformedURLException {
        playLastSong();
    }

    @javafx.fxml.FXML
    public void startMusic(ActionEvent actionEvent) {

        if (player.getStatus() == MediaPlayer.Status.PLAYING) {
            MainPanelController.staticLogo.setTextFill((Color.color(0, 0, 0, 1)));
            player.pause();
            playButton.setIconName("PLAY");
        } else {
            MainPanelController.staticLogo.setTextFill(Color.valueOf("#0f4ddb"));
            player.play();
            playButton.setIconName("PAUSE");
        }
    }

    private void adjustSongRepository() {
        String fileURL = "musics";
        File file = new File(fileURL);
        for (File f : file.listFiles()) {
            String name = f.getAbsolutePath();
            if (System.getProperty("os.name").contains("Windows")) {
                name = procesString(name);
                lv.getItems().add(name.substring(name.lastIndexOf("\\") + 1));
            } else {
                name = name.substring(name.lastIndexOf("/") + 1);
                lv.getItems().add(name);
            }
        }
    }

    private String procesString(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.toCharArray().length; i++) {
            if (name.charAt(i) == '\\' && name.charAt(i + 1) != '\\') sb.append("\\\\");
            else sb.append(String.valueOf((char) name.charAt(i)));
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
            String str2 = (String) lv.getSelectionModel().getSelectedItem();
            if (str2 == null) return; //if selects null then get out of method.
            File musicFile = new File("musics" + File.separator + str2);
            Media music;
            try {
                 music = new Media(musicFile.toURI().toURL().toString());
            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Unsupported file format."
                        , ButtonType.OK);
                alert.setTitle("Error.");
                alert.showAndWait();
                return;
            }

            nameOfSong.setText(String.valueOf(lv.getSelectionModel().getSelectedItem()));

            if (null != player && player.getStatus() == MediaPlayer.Status.PLAYING)
                player.pause();

            try {
                player = new MediaPlayer(music);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please run this command on terminal \n" +
                        "sudo apt-get install ubuntu-restricted-extras", ButtonType.OK);
                alert.setTitle("Your System is unable to run mp3/mp4.");
                alert.showAndWait();
                return;
            }

            playButton.setIconName("PAUSE");
            if (voiceFlag) {
                player.setVolume(0.25);
                voiceFlag = false;
            } else {
                player.setVolume(volumeSlider.getValue());
            }
            player.setOnReady(() -> {
                player.setStartTime(Duration.ZERO);

                if (player.getMedia().getSource().contains(".mp3") || player.getMedia().getSource().contains(".wav")) {
                    imgView.toFront();
                } else {
                    img.setMediaPlayer(player);
                    img.toFront();
                }

                //VOLUME PROPERTİES
                volumeSlider.setMin(0);
                volumeSlider.setMax(0.5);
                volumeSlider.setValue(player.getVolume());
                VolumeText.setText(String.valueOf((int) (volumeSlider.getValue() * 100 * 2)));

                volumeSlider.valueProperty().addListener(evs -> {
                    player.setVolume(volumeSlider.getValue());
                    VolumeText.setText(String.valueOf((int) (volumeSlider.getValue() * 100 * 2)));

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
                playedSongs.push(player.getMedia().getSource().substring(player.getMedia().getSource().lastIndexOf("/") + 1));
                //System.out.println(playedSongs.peek());
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

    public void playLastSong() throws MalformedURLException {
        MainPanelController.staticLogo.setTextFill(Color.valueOf("#0f4ddb"));
        String str = "";
        try {
            str = playedSongs.pop();
        } catch (Exception e) {
        }
        //int random = (int) (Math.random() * lv.getItems().size());
        if (playedSongs.size() == 0) {
            lv.getSelectionModel().select(str);
        } else
            lv.getSelectionModel().select(playedSongs.pop());


        File musicFile = new File("musics" + File.separator + lv.getSelectionModel().getSelectedItem());


        nameOfSong.setText(String.valueOf(lv.getSelectionModel().getSelectedItem()));//setting up text
        Media music;
        try {
            music = new Media(musicFile.toURI().toURL().toString());
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unsupported file format."
                    , ButtonType.OK);
            alert.setTitle("Error.");
            alert.showAndWait();
            return;
        }

        if (null != player && player.getStatus() == MediaPlayer.Status.PLAYING)
            player.pause();

        try {
            player = new MediaPlayer(music);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please run this command on terminal \n" +
                    "sudo apt-get install ubuntu-restricted-extras", ButtonType.OK);
            alert.setTitle("Your System is unable to run mp3/mp4.");
            alert.showAndWait();
            return;
        }
        player.setVolume(volumeSlider.getValue());
        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                player.setStartTime(Duration.ZERO);
                img.setMediaPlayer(player);

                if (player.getMedia().getSource().contains(".mp3") || player.getMedia().getSource().contains(".wav")) {
                    imgView.toFront();
                } else {
                    img.setMediaPlayer(player);
                    img.toFront();
                }


                //VOLUME PROPERTİES
                volumeSlider.setMin(0);
                volumeSlider.setMax(0.5);

                VolumeText.setText(String.valueOf((int) (player.getVolume() * 100 * 2)));

                volumeSlider.valueProperty().addListener(evs -> {
                    player.setVolume(volumeSlider.getValue());
                    VolumeText.setText(String.valueOf((int) (volumeSlider.getValue() * 100 * 2)));

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
                playedSongs.push(player.getMedia().getSource().substring(player.getMedia().getSource().lastIndexOf("/") + 1));

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

    private void playRandomMusic() throws MalformedURLException {
        MainPanelController.staticLogo.setTextFill(Color.valueOf("#0f4ddb"));
        int random = (int) (Math.random() * lv.getItems().size());
        lv.getSelectionModel().select(random);

        if (lv.getSelectionModel().getSelectedItem().toString().equals(playedSongs.peek())) {
            playRandomMusic();
            return;
        }

        File musicFile = new File("musics" + File.separator + lv.getSelectionModel().getSelectedItem());


        nameOfSong.setText(String.valueOf(lv.getSelectionModel().getSelectedItem()));//setting up text
        Media music;
        try {
            music = new Media(musicFile.toURI().toURL().toString());
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unsupported file format."
                    , ButtonType.OK);
            alert.setTitle("Error.");
            alert.showAndWait();
            return;
        }

        if (null != player && player.getStatus() == MediaPlayer.Status.PLAYING)
            player.pause();

        try {
            player = new MediaPlayer(music);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please run this command on terminal \n" +
                    "sudo apt-get install ubuntu-restricted-extras", ButtonType.OK);
            alert.setTitle("Your System is unable to run mp3/mp4.");
            alert.showAndWait();
            return;
        }
        player.setVolume(volumeSlider.getValue());
        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                player.setStartTime(Duration.ZERO);
                img.setMediaPlayer(player);

                if (player.getMedia().getSource().contains(".mp3") || player.getMedia().getSource().contains(".wav") ||
                        player.getMedia().getSource().contains(".aif") || player.getMedia().getSource().contains(".aiff") ||
                        player.getMedia().getSource().contains(".m4a")
                ) {
                    imgView.toFront();
                } else {
                    img.setMediaPlayer(player);
                    img.toFront();
                }


                //VOLUME PROPERTİES
                volumeSlider.setMin(0);
                volumeSlider.setMax(0.5);

                VolumeText.setText(String.valueOf((int) (player.getVolume() * 100 * 2)));

                volumeSlider.valueProperty().addListener(evs -> {
                    player.setVolume(volumeSlider.getValue());
                    VolumeText.setText(String.valueOf((int) (volumeSlider.getValue() * 100 * 2)));

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
                playedSongs.push(player.getMedia().getSource().substring(player.getMedia().getSource().lastIndexOf("/") + 1));

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
