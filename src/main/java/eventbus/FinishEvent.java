package eventbus;

/**
 * This class created on 2017/12/21.
 *
 * @author Connery
 */
public class FinishEvent {
  private String name;

  public FinishEvent(String name) {
    this.name = name;
  }

  public String tableName() {
    return name;
  }
}
