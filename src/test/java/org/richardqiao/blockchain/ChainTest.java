package org.richardqiao.blockchain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChainTest {

  Chain chain;
  Chain chain2;
  @BeforeEach
  void setUp() throws InterruptedException{
    chain = new Chain("test");
    Thread.sleep(1);
    chain.mine();
    Thread.sleep(1);
    chain.mine();

    chain2 = new Chain("test2");
    chain2.mine();
  }

  @Test
  void copyFrom() {
    System.out.println("chain2 -- " + chain2);
    System.out.println("chain1 -- " + chain);
    chain2.resolveConflicts(chain);
    System.out.println(chain2);
    assert(chain.list.size() == chain2.list.size());
  }

  @Test
  void getString() {
    //System.out.println(chain);
  }

  @Test
  void getFromJson() {
    Chain ch = Chain.getFromJson(chain.toString());
    System.out.println(chain);
    System.out.println(ch);
    ch = Chain.getFromJson("{chain:[{index:1,timestamp:1516760710890,transactions:[],proof:0,previous_hash:0},{index:2,timestamp:1516760710893,transactions:[{sender:genisis,recipient:test,amount:1}],proof:51,previous_hash:-1752402827},{index:3,timestamp:1516760710895,transactions:[{sender:genisis,recipient:test,amount:1}],proof:66,previous_hash:-316078993},{index:4,timestamp:1516760710897,transactions:[{sender:bill,recipient:jobs,amount:20},{sender:genisis,recipient:test,amount:1}],proof:143,previous_hash:-1695102648}]}");
    System.out.println(ch);
    assert(ch.list.getFirst().timestamp == 1516760710890l);
  }

  @Test
  void mine() {
  }

  @Test
  void newTransaction() {
    chain.newTransaction("bill", "jobs", 20);
    System.out.println(chain);
    chain.mine();
    System.out.println(chain);
    assert(chain.list.getLast().transactions.size() == 2);
  }

  @Test
  void registerNode() {
  }

  @Test
  void validChain() {
    chain2.copyFrom(chain);
    System.out.println(chain);
    System.out.println(chain.validChain());
    assert(chain.validChain());
    System.out.println(chain2);
    System.out.println(chain2.validChain());
    assert(chain2.validChain());
    chain2.list.getFirst().proof = new ProofOfWork(3);
    System.out.println(chain2);
    System.out.println(chain2.validChain());
    assert(!chain2.validChain());

  }

}