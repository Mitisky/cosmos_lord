package random;

import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

/**
 * This class created on 2017/12/25.
 *
 * @author Connery
 */
public class RandomGeneratorEx implements RandomGenerator {
  private RandomGenerator randomGenerator = new JDKRandomGenerator();
  /**
   * TODO 可以直接用 UNICODE 编码生成随机
   */
  private String base = "一二三四五六七八九你我他今日明阴阳正负大小天地金木水火土";
  private int length = base.length();

  public String randomCCString_1() {
    int pos = Math.abs(randomGenerator.nextInt() % length);
    return base.substring(pos, pos + 1);
  }

  public String randomCCString_2() {
    StringBuilder sb = new StringBuilder();
    sb.append(randomCCString_1())
        .append(randomCCString_1());
    return sb.toString();
  }

  public String randomCCString_5() {
    StringBuilder sb = new StringBuilder();
    sb.append(randomCCString_1())
        .append(randomCCString_1())
        .append(randomCCString_1())
        .append(randomCCString_1())
        .append(randomCCString_1());
    return sb.toString();
  }

  public String randomCCString_10() {
    StringBuilder sb = new StringBuilder();
    sb.append(randomCCString_1())
        .append(randomCCString_1())
        .append(randomCCString_1())
        .append(randomCCString_1())
        .append(randomCCString_1())
        .append(randomCCString_1())
        .append(randomCCString_1())
        .append(randomCCString_1())
        .append(randomCCString_1())
        .append(randomCCString_1());
    return sb.toString();
  }


  @Override
  public void setSeed(int i) {
    randomGenerator.setSeed(i);
  }

  @Override
  public void setSeed(int[] ints) {
    randomGenerator.setSeed(ints);

  }

  @Override
  public void setSeed(long l) {
    randomGenerator.setSeed(l);
  }

  @Override
  public void nextBytes(byte[] bytes) {
    randomGenerator.nextBytes(bytes);
  }

  @Override
  public int nextInt() {
    return randomGenerator.nextInt();
  }

  @Override
  public int nextInt(int i) {
    return randomGenerator.nextInt(i);
  }

  @Override
  public long nextLong() {
    return randomGenerator.nextLong();
  }

  @Override
  public boolean nextBoolean() {
    return randomGenerator.nextBoolean();
  }

  @Override
  public float nextFloat() {
    return randomGenerator.nextFloat();
  }

  @Override
  public double nextDouble() {
    return randomGenerator.nextDouble();
  }

  @Override
  public double nextGaussian() {
    return randomGenerator.nextGaussian();
  }

  public int nextInt10() {
    return Math.abs(randomGenerator.nextInt() % 10);
  }

  public int nextInt100() {
    return Math.abs(randomGenerator.nextInt() % 100);
  }

  public int nextInt1_000() {
    return Math.abs(randomGenerator.nextInt() % 1_000);
  }

  public int nextInt10_000() {
    return Math.abs(randomGenerator.nextInt() % 10_000);
  }

  public int nextInt100_000() {
    return Math.abs(randomGenerator.nextInt() % 100_000);
  }
}
