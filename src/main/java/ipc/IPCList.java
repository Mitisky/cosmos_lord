package ipc;

import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

/**
 * This class created on 2017/12/8.
 *
 * @author Connery
 */
public class IPCList {

  public int[] value;

  public IPCList(int l) {
    value = new int[l];
    RandomGenerator generator = new JDKRandomGenerator(1);
    for (int i = 0; i < l; i++) {
      value[i] = generator.nextInt();
    }
  }

  public int get(int index, int scale) {
    return value[index] + scale;
  }

  public int size() {
    return value.length;
  }
}
