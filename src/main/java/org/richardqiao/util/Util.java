package org.richardqiao.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Util {
  public static String getHeader(String url, String path) {
    return "GET /" + path + " HTTP/1.1\n" +
        "Host: " + url + "\n" +
        "Connection: close\n" +
        "User-Agent: Paw/3.1.5 (Macintosh; OS X/10.13.1) GCDHTTPRequest\n" +
        "Content-Length: 0\r\n\r\n";
  }

  public static String webCall(String header, String body, String address, int port) {
    try{
      Map<String, String> map = new HashMap<>();
      Socket socket = new Socket(address, port);
      DataOutputStream output = new DataOutputStream(socket.getOutputStream());
      BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      output.writeBytes(header + body);
      String response = null;
      while ((response = bf.readLine()) != null) {
        if (response.length() == 0) break;
        String[] kv = response.split(":");
        if (kv.length > 1) map.put(kv[0], kv[1]);
      }
      int len = Integer.parseInt(map.getOrDefault("Content-Length", "0").trim());
      if(len == 0) return "";
      char[] chs = new char[len];
      bf.read(chs, 0, len);
      return String.valueOf(chs);
    }catch(Exception ex){
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * {chain:[{index:1,timestamp:1516795700226,transactions:[],proof:0,previous_hash:0},{index:2,timestamp:1516795727582,transactions:[{sender:genisis,recipient:richard,amount:1}],proof:51,previous_hash:1404149101}]}
   * @param json
   * @return
   */
  public static String beautyJson(String json){
    StringBuilder sb = new StringBuilder();
    int indent = 0;
    for(char ch: json.toCharArray()){
      if(ch == ','){
        sb.append(ch);
        sb.append("\n");
        sb.append(spaces(indent));
      }else if (ch == '{' || ch == '['){
        indent ++;
        sb.append(ch);
        sb.append('\n');
        sb.append(spaces(indent));
      }else if(ch == ']' || ch == '}'){
        indent --;
        sb.append('\n');
        sb.append(spaces(indent));
        sb.append(ch);
      }else{
        sb.append(ch);
      }
    }
    return sb.toString();
  }

  private static String spaces(int count){
    return new String(new char[count]).replace('\0', ' ');
  }
}
