package org.richardqiao.http;

public class HTTPResponse {
  private String header;
  private String body;
  private int httpCode;

  /**
   * 200
   * 404
   *
   * @param httpcode
   */
  public HTTPResponse(int httpcode) {
    body = "{status: 0}";
    httpCode = httpcode;
    if (httpcode != 200) {
      body = "{httpCode: " + httpcode + "}";
    }
    header = "HTTP/1.1 " + httpcode + " OK\n" +
        "Content-Length: " + body.length() + "\n" +
        "Content-Type: text/html\n" +
        "Connection: Closed\r\n" +
        "\r\n";
  }

  public HTTPResponse(int httpcode, String resBody) {
    body = resBody;
    httpCode = httpcode;
    header = "HTTP/1.1 " + httpcode + " OK\n" +
        "Content-Length: " + body.length() + "\n" +
        "Content-Type: text/html\n" +
        "Connection: Closed\r\n" +
        "\r\n";
  }

  public void setHeader(int httpCode) {
    header = "HTTP/1.1 " + httpCode + " OK\n" +
        "Content-Length: " + body.length() + "\n" +
        "Content-Type: text/html\n" +
        "Connection: Closed\r\n" +
        "\r\n";
  }

  public String getHeader() {
    return header;
  }

  public String getBody() {
    return body;
  }

  public String toString() {
    return header + body;
  }
}
