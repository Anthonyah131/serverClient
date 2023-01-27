/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tony.cliente.controller;

import com.jfoenix.controls.JFXButton;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class ReproductorViewController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private JFXButton jfxBtnAnt;
    @FXML
    private HBox rootVideo;
    @FXML
    private JFXButton jfxBtnSig;
    private MediaPlayer mediaPlayer;
    private Media media;

    private static Socket clientSocket;
    private static InputStream in;
    private static OutputStream out;
    String serverIP;
    boolean inputShutdown = false;
    MediaView mediaview = new MediaView();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
           // Pide la dirección IP del servidor
           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
           System.out.print("Ingresa la dirección IP del servidor: ");
           serverIP = br.readLine();

            clientSocket = new Socket(serverIP, 3000);
            in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();

            String inputString = "now";
            InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));

            String signal = input.readLine();
            out.write(signal.getBytes());
//            if (!inputShutdown) {
//                clientSocket.shutdownInput();
//                inputShutdown = true;
//            }

            byte[] videoData = new byte[1024];
            int bytesRead;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            while ((bytesRead = in.read(videoData, 0, videoData.length)) != -1) {
                buffer.write(videoData, 0, bytesRead);
            }
            System.out.println("Size of video received: " + buffer.size());
            FileOutputStream fos = new FileOutputStream("video.mp4");
            buffer.writeTo(fos);
            fos.close();
//            if (!inputShutdown) {
//                clientSocket.shutdownInput();
//                inputShutdown = true;
//            }

            media = new Media(new File("video.mp4").toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaview = new MediaView(mediaPlayer);
            mediaview.setPreserveRatio(true);
            //mediaview.setFitHeight(700);
            mediaview.setFitWidth(400);
            mediaview.getStyleClass().add("media-view");


            mediaPlayer.play();
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            rootVideo.getChildren().add(mediaview);



            // Cierra la conexión
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ReproductorViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionJfxBtnAnt(ActionEvent event) throws IOException {
        clientSocket = new Socket(serverIP, 3000);
        in = clientSocket.getInputStream();
        out = clientSocket.getOutputStream();

        // Espera por una acción del usuario para pasar al siguiente video o volver al anterior
        // BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String inputString = "prev";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));

        String signal = input.readLine();
        out.write(signal.getBytes());

        playVideo();

        // Cierra la conexión
        in.close();
        out.close();
        clientSocket.close();
    }

    @FXML
    private void onActionJfxBtnSig(ActionEvent event) throws IOException {
        clientSocket = new Socket(serverIP, 3000);
        in = clientSocket.getInputStream();
        out = clientSocket.getOutputStream();

        // Espera por una acción del usuario para pasar al siguiente video o volver al anterior
        // BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String inputString = "next";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));

        String signal = input.readLine();
        out.write(signal.getBytes());

        playVideo();

        // Cierra la conexión
        in.close();
        out.close();
        clientSocket.close();
    }

    private void playVideo() {
        try {
            byte[] videoData = new byte[1024];
            int bytesRead;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            while ((bytesRead = in.read(videoData, 0, videoData.length)) != -1) {
                buffer.write(videoData, 0, bytesRead);
            }
            System.out.println("Size of video received: " + buffer.size());
            FileOutputStream fos = new FileOutputStream("video.mp4");
            buffer.writeTo(fos);
            fos.close();

            media = new Media(new File("video.mp4").toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaview.setMediaPlayer(mediaPlayer);
            mediaview.setPreserveRatio(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            mediaPlayer.play();
            buffer.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
