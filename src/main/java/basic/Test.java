package basic;

/**
 * This class created on 2017/12/16.
 *
 * @author Connery
 */
public class Test {

  public static void main(String[] args) {
    int number = 10;
    System.out.println(Integer.toBinaryString(number));
    System.out.println(Integer.toBinaryString(number << 1));
    System.out.println(Integer.toBinaryString(number >> 1));
    number = -10;
    System.out.println(Integer.toBinaryString(number));
    System.out.println(Integer.toBinaryString(number << 1));
    System.out.println("" + (number >> 1) + ":\n" + Integer.toBinaryString(number >> 1));
    System.out.println("" + (number >>> 1) + ":\n" + Integer.toBinaryString(number >>> 1));
  }

  /**
   * 输出一个int的二进制数
   */
  private static void printInfo(int num) {

  }
}