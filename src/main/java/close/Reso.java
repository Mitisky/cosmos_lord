package close;

import java.io.Closeable;
import java.io.IOException;

/**
 * This class created on 2017/12/15.
 *
 * @author Connery
 */
public class Reso implements Closeable {

  public Reso() {
  }

  public void show() {
    System.out.println("show me");
    throw new RuntimeException();

  }

  @Override
  public void close() throws IOException {
    System.out.println("close me!");
  }
}
