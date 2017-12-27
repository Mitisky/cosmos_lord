package bitset;

import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.junit.Test;
import org.roaringbitmap.BitSetUtil;
import org.roaringbitmap.IntIterator;
import org.roaringbitmap.RoaringBitmap;

import java.util.BitSet;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

/**
 * This class created on 2017/12/14.
 *
 * @author Connery
 */
public class Driver {

  public static void main(String[] args) throws Exception {
    RoaringBitmap rr = new RoaringBitmap();
//    rr.add(Integer.parseInt("111110000000000000", 2));
//    rr.add(Integer.parseInt("111110000000000001", 2));
    rr.add(-10);
    IntIterator it = rr.getIntIterator();
    System.out.println(rr.getSizeInBytes());
    while (it.hasNext()) {
      System.out.println(it.next());
    }
  }

  @Test
  public void and() throws Exception {
    RoaringBitmap rr = new RoaringBitmap();
    RoaringBitmap rr2 = new RoaringBitmap();
    rr.add(Integer.parseInt("111110000000000000", 2));
    rr2.add(Integer.parseInt("111110000000000000", 2));
    rr.and(rr2);
  }

  @Test
  public void usage() {

    BitSet set = new BitSet();
    set.set(10);
    set.set(20);
    set.set(3100);
    RoaringBitmap map = BitSetUtil.bitmapOf(set);
    System.out.println("bit size:" + set.length());
    IntStream intStream = set.stream();
    PrimitiveIterator.OfInt bitit = intStream.iterator();
    while (bitit.hasNext()) {
      System.out.println(bitit.next());
    }
    IntIterator it = map.getIntIterator();
    System.out.println("roar size:" + map.getSizeInBytes());
    while (it.hasNext()) {
      System.out.println(it.next());
    }
  }

  @Test
  public void itSpeed() {
    RandomGenerator generator = new JDKRandomGenerator();
    int size = 32767;
    int t = 1;
//    for (int k = 0; k < t; k++) {
    BitSet bit = new BitSet();
    for (int i = 0; i < size; i++) {
      bit.set(Math.abs(generator.nextInt() / 2));
    }
//    }
    IntStream intStream = bit.stream();
    PrimitiveIterator.OfInt bitit = intStream.iterator();
    int i = 0;
    long bitTime = System.currentTimeMillis();

    while (bitit.hasNext()) {
      bitit.next();
      i++;
    }
    System.out.println("bit time:" + (System.currentTimeMillis() - bitTime));
    System.out.println("bit count:" + i);

    RoaringBitmap map = BitSetUtil.bitmapOf(bit);

    IntIterator it = map.getIntIterator();
    bitTime = System.currentTimeMillis();

    i = 0;
    while (it.hasNext()) {
      i++;
      it.next();
    }
    System.out.println("roar time:" + (System.currentTimeMillis() - bitTime));
    System.out.println("roar count:" + i);
    System.out.println("roar size:" + map.getSizeInBytes());

  }


  public void save() {
    BitSet bit = new BitSet();
    bit.set(10);
    bit.set(20);
    RoaringBitmap map = BitSetUtil.bitmapOf(bit);
//    DataOutput output = new DataOutputStream();
//    map.serialize();

  }

  @Test
  public void inflateRatio() {
    int sizeSeed = (int) System.currentTimeMillis();
    RandomGenerator generator = new JDKRandomGenerator(sizeSeed);
    int dataSeed = (int) System.currentTimeMillis() + generator.nextInt();
    RandomGenerator generatorData = new JDKRandomGenerator(dataSeed);
    int count = 0;
    double sum = 0;
    double sum4serial = 0;
    double sum4bitset = 0;
    double sum4serial4bitset = 0;
    int loopTime = 10;
    int row = 100_000_000;
    int valueScope = row;
    int groupSize = 1000;
    while (count++ < loopTime) {
      int size = Math.abs(generator.nextInt() % row) / groupSize + 1;
      System.out.println("size:" + size);

      RoaringBitmap bit = new RoaringBitmap();
      BitSet set = new BitSet();
      for (int i = 0; i < size; i++) {
        int v = Math.abs(generatorData.nextInt() % valueScope);
        bit.add(v);
//        set.set(v);
      }
//      bit.runOptimize();
      System.out.println("size in bytes:" + bit.getSizeInBytes());
      System.out.println("size in serial:" + bit.serializedSizeInBytes());
      System.out.println("size in bitset:" + set.length());
      System.out.println("");
      sum += (bit.getSizeInBytes() * 8 / size);
      sum4serial += (bit.serializedSizeInBytes() * 8 / size);

      sum4bitset += (set.length() / size);
    }

    System.out.println("inflate in byes:" + (sum / loopTime));
    System.out.println("inflate in ser:" + (sum4serial / loopTime));
    System.out.println("inflate in bitset:" + (sum4bitset / loopTime));
    System.out.println("sizeSeed:" + sizeSeed);
    System.out.println("dataSeed:" + dataSeed);
  }

  @Test
  public void speed() {
    RandomGenerator generator = new JDKRandomGenerator();
    int size = 1000_000;
    int t = 1;
    long intTime = System.currentTimeMillis();
    for (int k = 0; k < t; k++) {
      int[] ins = new int[size];
      for (int i = 0; i < size; i++) {
        ins[i] = Math.abs(generator.nextInt() / 2);

      }
      RoaringBitmap.bitmapOf(ins);
    }
    System.out.println("int time:" + (System.currentTimeMillis() - intTime));

    long bitTime = System.currentTimeMillis();
    for (int k = 0; k < t; k++) {
      BitSet bit = new BitSet();
      for (int i = 0; i < size; i++) {
        bit.set(Math.abs(generator.nextInt() / 2));
      }
      BitSetUtil.bitmapOf(bit);
    }

    System.out.println("bit time:" + (System.currentTimeMillis() - bitTime));
  }

  @Test
  public void speedPut() {
    RandomGenerator generator = new JDKRandomGenerator();
    int size = 2000_000;
    int t = 1;
    long intTime = System.currentTimeMillis();
    for (int k = 0; k < t; k++) {
      int[] ins = new int[size];
      for (int i = 0; i < size; i++) {
        ins[i] = Math.abs(generator.nextInt());
      }
      RoaringBitmap.bitmapOf(ins);
    }
    System.out.println("int time:" + (System.currentTimeMillis() - intTime));

    long bitTime = System.currentTimeMillis();
    for (int k = 0; k < t; k++) {
      RoaringBitmap bit = new RoaringBitmap();
      for (int i = 0; i < size; i++) {
        bit.add(Math.abs(generator.nextInt()));
      }
      System.out.println("size:" + bit.getSizeInBytes());

    }
    System.out.println("bit time:" + (System.currentTimeMillis() - bitTime));
  }

  @Test
  public void size() {
    int size = 10_000_000;
    long sum = 0;
    for (int i = 0; i < size; i++) {
      RoaringBitmap bit = new RoaringBitmap();
      bit.add(i);
      sum += bit.serializedSizeInBytes();
    }
    System.out.println("size:" + sum);
  }
}
