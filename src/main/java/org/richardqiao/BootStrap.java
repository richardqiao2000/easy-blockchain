package org.richardqiao;

/**
 * TODO: Exception responses
 * Logging
 * NGinx Load Balancer
 */

import org.richardqiao.blockchain.Chain;
import org.richardqiao.http.HTTPServer;

public class BootStrap {
  public static Chain chain;
  public static HTTPServer server;
  /**
   * start command
   * java -cp quick-web-0.01.jar org.richardqiao.web.BootStrap 5678 5
   * @param args args[0]: adress, args[1]: portNumber, args[2]: threadCount, args[3] owner
   * @throws Exception
   */
  public static void main(String[] args) {
    chain = new Chain(args[3]);
    try {
      server = HTTPServer.getInstance(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
      server.start();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
