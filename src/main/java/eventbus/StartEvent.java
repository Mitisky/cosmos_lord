package eventbus;

/**
 * This class created on 2017/12/21.
 *
 * @author Connery
 */
public class StartEvent {
  private String name;

  public StartEvent(String name) {
    this.name = name;
  }

  public String tableName() {
    return name;
  }
}
