package staticclass;

import java.io.File;

/**
 * This class created on 2017/12/8.
 *
 * @author Connery
 */
public class Outter {

  public String a;

  public void open() {

  }

  public static class StaticInner {

    public final File file;

    public StaticInner(String path) {
      file = new File(path);
    }

    public void print() {
      System.out.printf(file.getAbsolutePath());
    }
  }

}
