package org.richardqiao.blockchain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProofOfWorkTest {

  ProofOfWork pr;
  @BeforeEach
  void setUp() {
    pr = new ProofOfWork(0);
  }

  @Test
  void getClone() {

  }

  @Test
  void getNextSolution() {
    System.out.println(pr.getNextSolution());
    assert(pr.getNextSolution().solution == 51);
  }

  @Test
  void validProof() {
    System.out.println(ProofOfWork.validProof(pr.getNextSolution(), pr));
    System.out.println(ProofOfWork.validProof(new ProofOfWork(51), new ProofOfWork(0)));
    assert(ProofOfWork.validProof(new ProofOfWork(51), new ProofOfWork(0)));
  }


  @Test
  void getFromJson() {
    ProofOfWork pr1 = ProofOfWork.getFromJson("proof:36");
    assert(pr1.solution == 36);
  }
}