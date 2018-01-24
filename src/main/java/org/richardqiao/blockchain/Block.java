package org.richardqiao.blockchain;

import java.util.ArrayList;
import java.util.List;

public class Block {
  int index;
  long timestamp;
  List<Transaction> transactions;
  ProofOfWork proof;
  int previous_hash;

  @Override
  public Block clone(){
    Block cln = new Block(index, new ArrayList<>(), proof.clone(), previous_hash);
    cln.timestamp = timestamp;
    cln.previous_hash = previous_hash;
    for(Transaction trans: transactions){
      cln.transactions.add(trans.clone());
    }
    return cln;
  }

  public Block(int idx, List<Transaction> trans, ProofOfWork proof, int prevHash){
    index = idx;
    timestamp = System.currentTimeMillis();
    transactions = trans;
    this.proof = proof;
    previous_hash = prevHash;
  }

  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    for(Transaction tran: transactions){
      sb.append(tran + ",");
    }
    if(!transactions.isEmpty()) sb.setLength(sb.length() - 1);
    return "{index:" + index + "," +
            "timestamp:" + timestamp + "," +
            "transactions:[" + sb.toString() + "]," +
            proof.toString() + "," +
            "previous_hash:" + previous_hash + "}";
  }

  @Override
  public int hashCode(){
    return toString().hashCode();
  }

  public static Block getFromJson(String json){
    int i = 0, j = 0;
    int stack = 0;
    List<String> list = new ArrayList<>();
    while(j < json.length()){
      if(stack == 0 && (json.charAt(j) == ',' || json.charAt(j) == '}' || json.charAt(j) == ']')){
        list.add(json.substring(i, j));
        i = j + 1;
      }else if(json.charAt(j) == '[' || json.charAt(j) == '{'){
        stack++;
      }else if(json.charAt(j) == ']' || json.charAt(j) == '}'){
        stack--;
      }
      j++;
    }
    if(i < j){
      list.add(json.substring(i, j));
    }
    Block blk = new Block(0, new ArrayList<>(), null, 0);
    for(String str: list){
      String[] strs = str.split(":");
      if(strs[0].equals("index")){
        blk.index = Integer.parseInt(strs[1]);
      }else if(strs[0].equals("previous_hash")){
        blk.previous_hash = Integer.parseInt(strs[1]);
      }else if(strs[0].equals("proof")){
        blk.proof = ProofOfWork.getFromJson(str);
      }else if(strs[0].equals("timestamp")){
        blk.timestamp = Long.parseLong(strs[1]);
      }else if(strs[0].equals("transactions")){
        i = str.indexOf('[') + 1;
        String trans = str.substring(i, str.length() - 1);
        i = 0;
        j = 0;
        while(j < trans.length()){
          if(trans.charAt(j) == '}'){
            blk.transactions.add(Transaction.getFromJson(trans.substring(i + 1, j)));
            i = j + 2;
          }
          j++;
        }
      }
    }
    return blk;
  }

}
