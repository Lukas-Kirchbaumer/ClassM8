package com.example.laubi.myapplication.Polling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by laubi on 3/29/2017.
 */

public class SendMessageThread extends Thread {

    private String message;

    public SendMessageThread(String message) {
        this.message = message;
        this.start();
    }

    @Override
    public void run() {
        try {
            DatagramSocket s = new DatagramSocket();
            byte[] buf = new byte[1000];

            DatagramPacket dp = new DatagramPacket(buf, buf.length);

            InetAddress hostAddress = InetAddress.getByName("localhost");

            String outMessage = this.message;

            String outString = "Client say: " + outMessage;
            buf = outString.getBytes();

            DatagramPacket out = new DatagramPacket(buf, buf.length, hostAddress, 4000);
            s.send(out);

        } catch (IOException e) {

        }
    }
}
