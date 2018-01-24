package org.richardqiao.blockchain;

public class Transaction {
  public String sender;
  public String recipient;
  public int amount;
  public Transaction(String s, String r, int amt){
    sender = s;
    recipient = r;
    amount = amt;
  }

  @Override
  public Transaction clone(){
    return new Transaction(sender, recipient, amount);
  }

  @Override
  public String toString(){
    return "{sender:" + sender + ",recipient:" + recipient + ",amount:" + amount + "}";
  }

  public static Transaction getFromJson(String json){
    String[] strs = json.split(",");
    String s = strs[0].split(":")[1];
    String r = strs[1].split(":")[1];
    int amt = Integer.parseInt(strs[2].split(":")[1]);
    return new Transaction(s, r, amt);
  }
}
