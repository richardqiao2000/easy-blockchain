package org.richardqiao.blockchain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

  Block block;
  @BeforeEach
  void setUp() {
    block = new Block(1, new ArrayList<>(), new ProofOfWork(0), "-1");
    block.transactions.add(new Transaction("i", "u", 100));
    block.transactions.add(new Transaction("i1", "u1", 200));
    assert(block.transactions.size() == 2);
  }

  @Test
  void getClone() {
    Block blk = block.clone();
    System.out.println(blk);
    assert(blk.transactions.size() == 2);
  }

  @Test
  void getString() {
    System.out.println(block);
  }

  @Test
  void getHashCode() {
  }

  @Test
  void getFromJson() {
    Block blk = Block.getFromJson("index:1,timestamp:1516755779028,transactions:[{sender:i,recipient:u,amount:100},{sender:i1,recipient:u1,amount:200}],proof:0,previous_hash:-1");
    System.out.println(blk);
    assert(blk.timestamp == 1516755779028l);
  }

  @Test
  void getSHA256() {
    System.out.println(block.getSHA256());
    assert(block.getSHA256().length() == 64);
  }
}