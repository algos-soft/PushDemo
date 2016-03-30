package com.pushserver;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GCMPusher {


    public static final String API_KEY = "AIzaSyCvon6okvaGHIBb4ajwo0WCUZFXkXa_vrU";
    public static final String DEVICE_ID = "cwnd70n1ySE:APA91bHMA79qqHxkpHU2Z28nhkw_H4FMlQIZB1mxKN6MrPm8d6J-heFT5aWQWTck8VcPOny46mMbEV2miVsmqyKNjEZd1rZfVzG-bz0fh87zrbylOx25YFEM0cVsXqVRTkSwWtOSjWnV";

    private JTextArea apiField;
    private JTextArea idField;
    private JTextArea responseField;

    public GCMPusher() {
        JFrame frame = new JFrame();
        frame.add(creaPanBottoni());
        frame.setTitle("AlarmSender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setApiKey(API_KEY);
        setDeviceId(DEVICE_ID);

        frame.pack();
        frame.setVisible(true);

    }


    /**
     * Crea il pannello della UI
     */
    private JPanel creaPanBottoni() {
        JPanel pan = new JPanel();
        pan.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        apiField = new JTextArea();
        apiField.setPreferredSize(new Dimension(300,80));
        apiField.setLineWrap(true);

        idField = new JTextArea();
        idField.setPreferredSize(new Dimension(300,80));
        idField.setLineWrap(true);

        responseField = new JTextArea();
        responseField.setPreferredSize(new Dimension(300,80));
        responseField.setLineWrap(true);

        JButton bSend = new JButton();
        bSend.setText("Send message");
        bSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setResponse("");
                String resp=send();
                setResponse(resp);
            }
        });

        BoxLayout layout = new BoxLayout(pan, BoxLayout.Y_AXIS);
        pan.setLayout(layout);

        pan.add(new JLabel("API Key"));
        pan.add(apiField);
        apiField.setAlignmentX(Component.LEFT_ALIGNMENT);
        pan.add(new JLabel(" "));
        pan.add(new JLabel("Device ID"));
        pan.add(idField);
        idField.setAlignmentX(Component.LEFT_ALIGNMENT);
        pan.add(new JLabel(" "));
        pan.add(bSend);
        bSend.setAlignmentX(Component.LEFT_ALIGNMENT);
        pan.add(new JLabel(" "));
        pan.add(new JLabel("Response"));
        pan.add(responseField);
        responseField.setAlignmentX(Component.LEFT_ALIGNMENT);


        return pan;
    }

    private String getApiKey(){
        return apiField.getText();
    }

    private String getDeviceId(){
        return idField.getText();
    }

    private void setApiKey(String key){
        apiField.setText(key);
    }

    private void setDeviceId(String sid){
        idField.setText(sid);
    }

    private void setResponse(String sid){
        responseField.setText(sid);
    }




    private String send() {

        String resp="";

        try {

            // prepara il payload di dati del messaggio
            JSONObject jData = new JSONObject();
            jData.put("messagetype", "alarm");
            jData.put("timestamp", "0000000000");
            jData.put("siteid", "65a6391b-279a-42dd-90d8-1da9e92edfce");
            jData.put("plantid", "3");
            jData.put("areaid", "2");
            jData.put("sensorid", "99");
            jData.put("outputid", "7");
            jData.put("details", "eventuali dettagli in forma di testo");

            // Prepara un JSON con il contenuto del messaggio GCM - a chi mandare cosa
            JSONObject jGcmData = new JSONObject();
            jGcmData.put("to", getDeviceId());
            jGcmData.put("data", jData);

            // Crea una connessione per inviare la richiesta del messaggio GCM
            URL url = new URL("https://android.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + getApiKey());
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Invia il messaggio GCM
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jGcmData.toString().getBytes());

            // Legge la risposta GCM
            InputStream inputStream = conn.getInputStream();
            resp = IOUtils.toString(inputStream);
            System.out.println(resp);

        } catch (IOException e) {
            System.out.println("Unable to send GCM message.");
            resp=e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }


    public static void main(String[] args) {
        new GCMPusher();
    }


}
