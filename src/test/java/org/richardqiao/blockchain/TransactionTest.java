package org.richardqiao.blockchain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

  Transaction tran;
  @BeforeEach
  void setUp() {
    tran = new Transaction("i", "u", 100);
  }

  @Test
  void getClone() {
    Transaction clone = tran.clone();
    assert(clone.amount == 100);
    assert(clone.sender == "i");
    assert(clone.recipient == "u");
  }

  @Test
  void getString() {
    System.out.println(tran.toString());
  }

  @Test
  void getFromJson() {
    Transaction clone = Transaction.getFromJson("sender:i,recipient:u,amount:100");
    assert(clone.amount == 100);
    assert(clone.sender.equals("i"));
    assert(clone.recipient.equals("u"));
  }
}