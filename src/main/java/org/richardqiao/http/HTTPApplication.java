package org.richardqiao.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class HTTPApplication implements Runnable {
  private Socket socket;

  public HTTPApplication(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    BufferedReader bf = null;
    try {
      bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String line0 = bf.readLine();
      if (line0 != null) {
        // 1. Build HTTPRequest
        HTTPRequest request = new HTTPRequest(line0, bf);
        System.out.println("\nRequest:\n" + request);
        // 2. Output HTTPResponse by using handler
        HTTPResponse res = new HTTPHandler().response(request, socket);
        System.out.println("\nResponse:\n" + res);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (bf != null) bf.close();
        if (socket != null) socket.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

  }
}
