package com.example.networkexamples;

import android.net.wifi.aware.DiscoverySession;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Requester extends Thread {

    Socket requestSocket;
    String message;
    StringBuilder returnStringBuffer = new StringBuilder(); Message lmsg;
    int ch;
    Handler handler;
    @Override public void run() {
        try {
            this.requestSocket = new Socket("100.70.37.55", 4444);
            InputStreamReader isr = new InputStreamReader(this.requestSocket.getInputStream(), "ISO-8859-1");
            while ((this.ch = isr.read()) != -1) {
                this.returnStringBuffer.append((char) this.ch);
            }
            this.message = this.returnStringBuffer.toString();
            this.lmsg = new Message();
            this.lmsg.obj = this.message;
            this.lmsg.what = 0;
            //handler.sendMessage(this.lmsg);
            this.requestSocket.close();
        } catch (Exception ee) {
            Log.d("sample	application", "failed	to	read	data" + ee.getMessage());
        }
    }
}
