package eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * This class created on 2017/12/21.
 *
 * @author Connery
 */
public interface TableEventObserver {
  @Subscribe
  void onStart(StartEvent event);

  @Subscribe
  void onFinish(FinishEvent event);
}
