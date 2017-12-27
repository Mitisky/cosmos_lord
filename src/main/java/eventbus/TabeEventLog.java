package eventbus;

/**
 * This class created on 2017/12/21.
 *
 * @author Connery
 */
public class TabeEventLog implements TableEventObserver {
  @Override
  public void onStart(StartEvent event) {
    System.out.println("start:" + event.tableName());
    try {
      Thread.sleep(10_000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onFinish(FinishEvent event) {
    System.out.println("finish:" + event.tableName());
  }
}
