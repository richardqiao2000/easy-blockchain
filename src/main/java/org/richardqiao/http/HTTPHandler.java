package org.richardqiao.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Function;

public class HTTPHandler {
  protected HTTPRequest httpRequest;
  protected Socket socket;
  protected HTTPRouter router;

  public HTTPHandler() {
    router = HTTPRouter.getInstance();
  }

  public HTTPResponse response(HTTPRequest request, Socket socket) {
    DataOutputStream output = null;
    HTTPResponse response = null;
    try {
      httpRequest = request;
      this.socket = socket;
      output = new DataOutputStream(socket.getOutputStream());
      Function<HTTPRequest, HTTPResponse> func = router.getFunc(request.getPath());
      response = func.apply(request);
      output.writeBytes(response.toString());
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (output != null) output.close();
      } catch (IOException se) {
        se.printStackTrace();
      }
    }
    return response;
  }

}
