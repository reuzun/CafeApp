package ceng.estu.controller;


import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class MusicPlayerController implements Initializable {
   /* @FXML
    public AnchorPane mainPanel;


    /*private double xOffset = 0;
    private double yOffset = 0;*/

    @FXML
    private ListView<String> lv;
    @FXML
    private MediaView img;
    @FXML
    private Text VolumeText;
    @FXML
    private Text remainingTime;
    @FXML
    private JFXSlider playingTimeSlider = new JFXSlider();
    @FXML
    private Slider volumeSlider = new Slider();
    @FXML
    private Text nameOfSong;
    @FXML
    private FontAwesomeIcon playButton;

    private final Stack<String> playedSongs = new Stack<>();
    @FXML
    private ImageView imgView;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            adjustSongRepository();
        } catch (Exception ignored) {
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
        if(lv.getSelectionModel().getSelectedItem()==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Select a song please" , ButtonType.OK);
            alert.showAndWait();
            return;
        }

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
            else sb.append(name.charAt(i));
        }
        name = sb.toString();
        return name;
    }


    @javafx.fxml.FXML
    public void handleCloseAction(ActionEvent actionEvent) {
        System.exit(-1);
    }


    private MediaPlayer player;
    static boolean voiceFlag = true;

    @FXML
    public void startApplication(MouseEvent event) {
        if (event.getClickCount() == 2) {
            MainPanelController.staticLogo.setTextFill(Color.valueOf("#0f4ddb"));
            String str2 = (String) lv.getSelectionModel().getSelectedItem();
            playSong(str2);
        }
    }

    public void playLastSong() {
        MainPanelController.staticLogo.setTextFill(Color.valueOf("#0f4ddb"));
        String str = "";
        try {
            str = playedSongs.pop();
        } catch (Exception ignored) {
        }

        if (playedSongs.size() == 0) {
            lv.getSelectionModel().select(str);
        } else
            lv.getSelectionModel().select(playedSongs.pop());
        playSong(lv.getSelectionModel().getSelectedItem());
    }

    private void playRandomMusic() {
        MainPanelController.staticLogo.setTextFill(Color.valueOf("#0f4ddb"));
        int random = (int) (Math.random() * lv.getItems().size());
        lv.getSelectionModel().select(random);

        try {
            if (lv.getSelectionModel().getSelectedItem().equals(playedSongs.peek())) {
                playRandomMusic();
                return;
            }
        }catch (Exception ignored){
        }
        playSong(lv.getSelectionModel().getSelectedItem());

    }

    private void playSong(String str){

        if (str == null) return; //if selects null then get out of method.
        File musicFile = new File("musics" + File.separator + str);
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
            //player.pause();
            player.stop();

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

            player.setOnEndOfMedia(() -> {
                try {
                    playRandomMusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

    }
}
