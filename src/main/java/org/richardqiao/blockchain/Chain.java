package org.richardqiao.blockchain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Chain {
  LinkedList<Block> list;
  List<Transaction> curTrans;
  String owner;
  public Chain(String owner){
    this.owner = owner;
    list = new LinkedList<>();
    list.add(new Block(1, new ArrayList<>(), new ProofOfWork(0), 0));
    curTrans = new ArrayList<>();
  }

  public void copyFrom(Chain chain){
    list.clear();
    for(Block blk: chain.list){
      list.add(blk.clone());
    }
  }

  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("{chain:[");

    for(Block blk: list){
      sb.append(blk.toString());
      sb.append(",");
    }
    sb.setLength(sb.length() - 1);

    sb.append("]}");
    return sb.toString();
  }

  public static Chain getFromJson(String json){
    int i = json.indexOf('[') + 1;
    int j = i;
    int stack = 0;
    Chain chain = new Chain("any");
    chain.list.clear();
    while(j < json.length()){
      char ch = json.charAt(j);
      if(stack == 0 && (ch == ',' || ch == ']' || ch == '}') && i + 1 < j - 1){
        String tmp = json.substring(i + 1, j - 1);
        chain.list.add(Block.getFromJson(tmp));
        i = j + 1;
      }else if(ch == '{' || ch == '['){
        stack++;
      }else if(ch == '}' || ch == ']'){
        stack--;
      }
      j++;
    }
    return chain;
  }

  /***
   * /mine
   * @return
   */
  public Block mine(){
    Block last = list.getLast();
    ProofOfWork proof = last.proof;
    ProofOfWork newProof = proof.getNextSolution();
    newTransaction("genesis", owner, 1);
    int prevHash = last.hashCode();
    Block blk = new Block(list.size() + 1, curTrans, newProof, prevHash);
    list.add(blk);
    curTrans = new ArrayList<>();
    return blk;
  }

  /***
   * /transaction/new
   * @return
   */
  public void newTransaction(String s, String r, int amount){
    curTrans.add(new Transaction(s, r, amount));
  }

  public boolean validChain(){
    Block last = null;
    for(Block cur: list){
      if(last != null){
        if(cur.previous_hash != last.hashCode()) return false;
        if(!ProofOfWork.validProof(cur.proof, last.proof)) return false;
      }
      last = cur;
    }
    return true;
  }

  public boolean resolveConflicts(Chain chain){
    if(chain.list.size() > list.size()){
      copyFrom(chain);
      return true;
    }
    return false;
  }
}
