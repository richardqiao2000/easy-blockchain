package org.richardqiao.http;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServer {

  public Set<Node> nodes;
  private final ExecutorService exec;
  private String address;
  private int port;

  private static HTTPServer instance;
  private HTTPServer(String addr, int port, int threadCount){
    nodes = new HashSet<>();
    address = addr;
    this.port = port;
    exec = Executors.newFixedThreadPool(threadCount);
  }

  public static HTTPServer getInstance(String addr, int port, int threadCount){
    if(instance == null){
      synchronized (HTTPServer.class){
        if(instance == null){
          instance = new HTTPServer(addr, port, threadCount);
        }
      }
    }
    return instance;
  }

  @Override
  public String toString(){
    return "http://" + address + ":" + port;
  }


  /**
   * start the http server
   * @throws Exception
   */
  public void start() throws Exception{
    final ServerSocket serverSocket = new ServerSocket(port);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      try{
        exec.shutdown();
        System.out.println("ExecutorServices shutdown.");
        serverSocket.close();
        System.out.println("ServerSocked closed.");
      }catch(Exception e){
        e.printStackTrace();
      }
    }));
    while (true) {
      Socket socket  = serverSocket.accept();
      exec.execute(new HTTPApplication(socket));
    }
  }

}
