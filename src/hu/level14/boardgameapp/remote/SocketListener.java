package hu.level14.boardgameapp.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.os.Handler;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import hu.level14.boardgameapp.MainActivity;

public class SocketListener extends Thread {
    
    private static SocketListener currentListener = null;
    
    public static void startListening(Session session, Handler activityMessageHandler) {
        if (currentListener != null) {
            // Abort the current listener
            currentListener.interrupt();
        }
        
        currentListener = new SocketListener(session, activityMessageHandler);
        
        currentListener.start();
    }
    
    private final Session session;
    private final Handler handler;
    
    private SocketListener(Session session, Handler handler) {
        this.session = session;
        this.handler = handler;
    }
    
    @Override
    public void run() {
        // TODO: use isInterrupted() or similar
        try {
            Socket socket = new Socket(session.getAddress(), session.getSocketPort());
            try {
                // The server should ping us every 10 seconds.
                // Having a 1 minute timeout means the connection is lost.
                socket.setSoTimeout(60000);
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                
                // First, introduce ourselves
                byte[] keyBytes = session.getKey().getBytes("UTF-8");
                os.write(keyBytes);
                
                while (!Thread.interrupted()) {
                    int msg = is.read();

                    // Ping
                    if (msg == 1) {
                        // TODO: remove magic numbers
                        // Pong
                        os.write(2);
                    }
                    else if (msg == -1) {
                        // EOF, socket was closed
                        Log.i("app", "Server socket closed");
                        return;
                    }
                    Log.d("app", "Read byte from socket: " + Integer.toString(msg));
                    handler.sendEmptyMessage(msg);
                }
            }
            finally {
                socket.close();
            }
        }
        catch (SocketTimeoutException e) {
            Log.i("app", "Server socket timeout", e);
        }
        catch (IOException e) {
            Log.w("app", "IO exception occured", e);
        }
//        catch (InterruptedException e) {
//            Log.i("app", "Socket thread was interrupted");
//        }
    }
}
