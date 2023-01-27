/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tony.cliente;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void StartClient() throws IOException {

        Socket clientSocket = null;
        InputStream in = null;

        try {
            clientSocket = new Socket("localhost", 10000);
            in = clientSocket.getInputStream();
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se pudo obtener I/O para el host");
            System.exit(1);
        }

        // Recibe el video
        byte[] videoData = new byte[1024];
        int bytesRead;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        while ((bytesRead = in.read(videoData, 0, videoData.length)) != -1) {
            buffer.write(videoData, 0, bytesRead);
        }

        // Guarda el video en el disco duro
        FileOutputStream fos = new FileOutputStream("video.mp4");
        fos.write(buffer.toByteArray());
        fos.close();

        // reproduce el video
        String mediaFile = "video.mp4";
        String[] cmd = { "cmd", "/c", "start", mediaFile };
        Runtime.getRuntime().exec(cmd);

        in.close();
        clientSocket.close();
    }
}