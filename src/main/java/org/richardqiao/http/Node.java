package org.richardqiao.http;

public class Node {
  public String address;
  public int port;
  public Node(String addr, int pt){
    address = addr;
    port = pt;
  }

  @Override
  public String toString(){
    return "http://" + address + ":" + port;
  }

  /**
   * address:localhost,port:5679
   * @param json
   * @return
   */
  public static Node getFromJson(String json){
    Node node = new Node("", 0);
    String[] strs = json.split(",");
    for(String str: strs){
      String[] kv = str.split(":");
      if(kv[0].equals("address")) node.address = kv[1];
      else node.port = Integer.parseInt(kv[1]);
    }
    return node;
  }
}
