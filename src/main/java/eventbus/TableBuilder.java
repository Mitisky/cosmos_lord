package eventbus;

import com.google.common.eventbus.EventBus;

/**
 * This class created on 2017/12/21.
 *
 * @author Connery
 */
public class TableBuilder {
  private String tableName;
  private EventBus bus = new EventBus();

  public TableBuilder(String tableName) {
    this.tableName = tableName;
  }

  public void register(TableEventObserver observer) {
    bus.register(observer);
  }

  public void startBuild() {
    bus.post(new StartEvent(tableName));
  }

  public void finishBuild() {
    bus.post(new FinishEvent(tableName));
  }
}
