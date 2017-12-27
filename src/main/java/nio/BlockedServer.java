package nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用阻塞模式的SocketChannelB,ServerSocketChannel. 为了同时连接多个客户端，需要使用多线程
 */
public class BlockedServer {
  private int port = 8000;
  private ServerSocketChannel serverSocketChannel = null;
  private ExecutorService executorService;
  private static final int POOL_MULTIPLE = 4;

  public BlockedServer() throws IOException {
    executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
        .availableProcessors() * POOL_MULTIPLE);
    // 创建一个serverSocketChannel对象
    serverSocketChannel = ServerSocketChannel.open();
    // 使得在同一个主机上关闭了服务器程序，紧接着再启动该服务器程序时，
    // 可以顺利绑定相同的端口
    serverSocketChannel.socket().setReuseAddress(true);
    // 与一个本地端口绑定
    serverSocketChannel.socket().bind(new InetSocketAddress(port));
    System.out.println("服务器启动...");
  }

  public void service() {
    while (true) {
      SocketChannel socketChannel = null;
      try {
        socketChannel = serverSocketChannel.accept();
        // 将每个客户连接都使用线程池中的一个线程来处理
        executorService.execute(new Handler(socketChannel));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws IOException {
    new BlockedServer().service();
  }
}

class Handler implements Runnable {
  private SocketChannel socketChannel;

  public Handler(SocketChannel socketChannel) {
    this.socketChannel = socketChannel;
  }

  public void run() {
    handler(socketChannel);
  }

  public void handler(SocketChannel socketChannel) {
    try {
      Socket socket = socketChannel.socket();
      System.out.println("接收到客户连接，来自:" + socket.getInetAddress() + ":"
          + socket.getPort());
      BufferedReader br = getReader(socket);
      PrintWriter pw = getWriter(socket);
      String msg = null;
      while ((msg = br.readLine()) != null) {
        System.out.println(msg);
        pw.println(echo(msg));
        if (msg.equals("bye")) {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (socketChannel != null) {
          socketChannel.close();
        }
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

  public String echo(String msg) {
    return "echo:" + msg;
  }
}