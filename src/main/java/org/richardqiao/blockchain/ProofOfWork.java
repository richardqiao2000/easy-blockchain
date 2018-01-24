package org.richardqiao.blockchain;

public class ProofOfWork {
  int solution;
  public ProofOfWork(int solve){
    solution = solve;
  }

  @Override
  public ProofOfWork clone(){
    return new ProofOfWork(solution);
  }

  public ProofOfWork getNextSolution(){
    int sol = solution + 1;
    while(!validProof(new ProofOfWork(sol), this)){
      sol++;
      sol %= Integer.MAX_VALUE;
    }
    return new ProofOfWork(sol);
  }

  public static boolean validProof(ProofOfWork cur, ProofOfWork prev){
    int hash = (cur.solution + "" + prev.solution).hashCode();
    return (hash % 100) == 0;
  }

  @Override
  public String toString(){
    return "proof:" + solution;
  }

  public static ProofOfWork getFromJson(String json){
    return new ProofOfWork(Integer.parseInt(json.split(":")[1]));
  }

}
