package org.richardqiao.http;

import org.richardqiao.BootStrap;
import org.richardqiao.blockchain.Block;
import org.richardqiao.blockchain.Chain;
import org.richardqiao.blockchain.Transaction;
import org.richardqiao.util.Util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class HTTPRouter {
  private Map<String, Class<?>> map;
  private Map<String, Function<HTTPRequest, HTTPResponse>> router;

  private static HTTPRouter instance;

  private HTTPRouter() {
    router = new HashMap<>();
    router.put("404", func404);
    router.put("mine", mine);
    router.put("transaction", newTransaction);
    router.put("chain", listChain);
    router.put("chainraw", listChainRaw);
    router.put("nodes/register", nodesRegister);
    router.put("nodes/resolve", nodesResolve);
  }

  public static HTTPRouter getInstance() {
    if (instance == null) {
      synchronized (HTTPRouter.class) {
        if (instance == null) {
          instance = new HTTPRouter();
        }
      }
    }
    return instance;
  }

  public Function<HTTPRequest, HTTPResponse> getFunc(String path) {
    return router.getOrDefault(path, router.get("404"));
  }


  private Function<HTTPRequest, HTTPResponse> func404 = request ->  {
    String body = "{status: 404}";
    return new HTTPResponse(404, body);
  };

  /**
   * endpoint: mine
   * method: GET
   */
  private Function<HTTPRequest, HTTPResponse> mine = request ->  {
    Block block = BootStrap.chain.mine();
    return new HTTPResponse(200, Util.beautyJson(block.toString()));
  };

  /**
   * endpoint: transaction
   * method: POST
   * {sender:bill gates,recipient:ma yun,amount:5}
   */
  private Function<HTTPRequest, HTTPResponse> newTransaction = request -> {
    String str = request.body;
    str = str.substring(1, str.length() - 1);
    String[] strs = str.split(",");
    Transaction trans = new Transaction("", "", 1);
    for(String s: strs){
      String[] arr = s.split(":");
      if(arr[0].equals("sender")){
        trans.sender = arr[1];
      }else if(arr[0].equals("recipient")){
        trans.recipient = arr[1];
      }else if(arr[0].equals("amount")){
        trans.amount = Integer.parseInt(arr[1]);
      }
    }
    BootStrap.chain.newTransaction(trans.sender, trans.recipient, trans.amount);
    return new HTTPResponse(200, Util.beautyJson(trans.toString()));
  };

  /**
   * endpoint: chain
   * method: GET
   */
  private Function<HTTPRequest, HTTPResponse> listChain = request -> {
    String body = BootStrap.chain.toString();
    return new HTTPResponse(200, Util.beautyJson(body));
  };

  /**
   * endpoint: chainraw
   * method: GET
   */
  private Function<HTTPRequest, HTTPResponse> listChainRaw = request -> {
    String body = BootStrap.chain.toString();
    return new HTTPResponse(200, body);
  };

  /**
   * endpoint: nodes/register
   * method: POST
   * {nodes:[{address:localhost,port:5679},{address:localhost,port:5680}]}
   */
  private Function<HTTPRequest, HTTPResponse> nodesRegister = request -> {
    String str = request.toString();
    int i = str.indexOf('['), j = str.indexOf(']');
    str = str.substring(i + 1, j);
    i = 0;
    j = 0;
    List<String> strs = new ArrayList<>();
    while(j < str.length()){
      if(str.charAt(j) == '}'){
        strs.add(str.substring(i + 1, j));
        i = j + 2;
      }
      j++;
    }
    for(String s: strs){
      BootStrap.server.nodes.add(Node.getFromJson(s));
    }
    return new HTTPResponse(200, Util.beautyJson(request.toString()));
  };

  /**
   * endpoint: nodes/resolve
   * method: GET
   */
  private Function<HTTPRequest, HTTPResponse> nodesResolve = request -> {
    boolean diff = false;
    for(Node node: BootStrap.server.nodes){
      String url = node.toString();
      String response = Util.webCall(Util.getHeader(url, "chainraw"), request.toString(), node.address, node.port);
      Chain chain = Chain.getFromJson(response);
      if(BootStrap.chain.resolveConflicts(chain)){
        diff = true;
      }
    }
    String body = "{synched: " + diff + "}";
    return new HTTPResponse(200, Util.beautyJson(body));
  };

}
