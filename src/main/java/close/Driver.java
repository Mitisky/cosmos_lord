package close;

import java.io.IOException;

/**
 * This class created on 2017/12/15.
 *
 * @author Connery
 */
public class Driver {

  public static void main(String[] args) {
    try (Reso r = new Reso()) {
      r.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
