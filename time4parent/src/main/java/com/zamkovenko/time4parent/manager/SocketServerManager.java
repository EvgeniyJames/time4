package com.zamkovenko.time4parent.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.zamkovenko.time4parent.receiver.ServerMessageReceiver;
import com.zamkovenko.utils.Connection;
import com.zamkovenko.utils.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * User: Yevgeniy Zamkovenko
 * Date: 18.12.2017
 */

public class SocketServerManager implements Runnable {

    @SuppressLint("StaticFieldLeak")
    private static SocketServerManager instance;

    public static SocketServerManager  getInstance() {
        return instance;
    }

    private Context context;

    private Connection connection;

    public void sendMessage(String message) {

        if (connection != null) {
            connection.sendMessage(message);
        }
    }

    public SocketServerManager(Context context) {
        this.context = context;
        instance = this;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);
            Log.d(getClass().getSimpleName(),("Server Init"));

            while (true) {

                Socket socket = serverSocket.accept();

                Log.d(getClass().getSimpleName(), ("Client connected: " + socket.getInetAddress()) );

                if (connection != null) {
                    connection.clear();
                }

                connection = new Connection(socket);
                connection.setRecievedListener(new ServerMessageReceiver());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



