package ipc;

import org.junit.Test;

/**
 * This class created on 2017/12/8.
 *
 * @author Connery
 */
public class Driver {

  int size = 200_000_000;

  @Test
  public void ipc() {
    int[] cont = new int[size];
    IPC ipc = new IPC(size);
    long start = System.currentTimeMillis();
    int count = 0;
    for (int i = 0; i < size; i++) {
      cont[i] = ipc.value[i] + 2;
      count++;
    }
    System.out.println("Time:" + (System.currentTimeMillis() - start));
    System.out.println("count:" + count);
  }

  @Test
  public void ipc_no() {
    int[] cont = new int[size];
    IPC ipc = new IPC(size);
    int count = 0;

    long start = System.currentTimeMillis();
    for (int i = 0; i < size; i++) {
      cont[i] = ipc.get(i, 2);
      count++;
    }
    System.out.println("Time:" + (System.currentTimeMillis() - start));
    System.out.println("count:" + count);

  }
}
