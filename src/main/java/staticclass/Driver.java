package staticclass;

import org.junit.Assert;
import staticclass.Outter.StaticInner;

/**
 * This class created on 2017/12/8.
 *
 * @author Connery
 */
public class Driver {

  public static void main(String[] args) {
    StaticInner inner = new Outter.StaticInner("abc");
    StaticInner inner_2 = new Outter.StaticInner("abcs");
//    Assert.assertEquals(inner.file, inner_2.file);
    new Outter.StaticInner("abc").print();
  }
}
