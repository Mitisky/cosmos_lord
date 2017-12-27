package nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class BlockedClient {

  private SocketChannel socketChannel = null;

  public BlockedClient() throws IOException {
    socketChannel = SocketChannel.open();
    InetAddress ia = InetAddress.getLocalHost();
    InetSocketAddress isa = new InetSocketAddress(ia, 8000);
    socketChannel.connect(isa);
    System.out.println("与服务器的连接建立成功");
  }

  public static void main(String[] args) throws IOException {
    new BlockedClient().talk();
  }

  public void talk() throws IOException {
    try {
      BufferedReader br = getReader(socketChannel.socket());
      PrintWriter pw = getWriter(socketChannel.socket());
      BufferedReader localReader = new BufferedReader(
          new InputStreamReader(System.in));
      String msg = null;
      while ((msg = localReader.readLine()) != null) {
        pw.println(msg);
        System.out.println(br.readLine());// 接收服务器返回的消息
        // 当输出的字符串为"bye"时停止
        if (msg.equals("bye")) {
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        socketChannel.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private PrintWriter getWriter(Socket socket) throws IOException {
    OutputStream socketOut = socket.getOutputStream();
    return new PrintWriter(socketOut, true);
  }

  private BufferedReader getReader(Socket socket) throws IOException {
    InputStream socketIn = socket.getInputStream();
    return new BufferedReader(new InputStreamReader(socketIn));
  }
}