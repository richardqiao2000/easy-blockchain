package org.richardqiao.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

  @Test
  void getHeader() {
  }

  @Test
  void webCall() {
  }

  @Test
  void beautyJson() {
    String json = "{chain:[{index:1,timestamp:1516795700226,transactions:[],proof:0,previous_hash:0}," +
                          "{index:2,timestamp:1516795727582,transactions:[{sender:genisis,recipient:richard,amount:1}],proof:51,previous_hash:1404149101}]}";
    System.out.println(Util.beautyJson(json));
  }
}