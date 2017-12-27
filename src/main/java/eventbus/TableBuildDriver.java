package eventbus;

import org.junit.Test;

/**
 * This class created on 2017/12/21.
 *
 * @author Connery
 */
public class TableBuildDriver {
  @Test
  public void test() {
    TableBuilder builder = new TableBuilder("Table A");
    builder.register(new TabeEventLog());
    builder.startBuild();
    System.out.printf("finish");
    builder.finishBuild();
  }
}
