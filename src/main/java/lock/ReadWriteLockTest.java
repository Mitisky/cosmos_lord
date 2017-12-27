package lock;

import org.junit.Test;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class created on 2017/12/26.
 *
 * @author Connery
 */
public class ReadWriteLockTest {
  @Test
  public void testWR() throws Exception {
    ReadWriteLock lock = new ReentrantReadWriteLock();

    System.out.printf("get write lock");
    lock.writeLock().lock();
    Thread t = new Thread(new Run(lock, true));
    t.start();
    t.join();


  }

  @Test
  public void testWTR() throws Exception {
    ReadWriteLock lock = new ReentrantReadWriteLock();

    System.out.printf("get write lock");
    lock.writeLock().lock();
    Thread t = new Thread(new Run(lock, false));
    t.start();
    t.join();


  }

  class Run implements Runnable {
    private ReadWriteLock lock;
    private boolean getW;

    public Run(ReadWriteLock lock, boolean getW) {
      this.lock = lock;
      this.getW = getW;
    }

    @Override
    public void run() {
      if (getW) {
        System.out.printf("runner get write lock");
        lock.writeLock().lock();
      } else {
        System.out.printf("runner get read lock");
        lock.readLock().lock();
      }
    }
  }
}
