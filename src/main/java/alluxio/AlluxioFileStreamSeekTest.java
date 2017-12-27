/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */
package alluxio;

import alluxio.client.ReadType;
import alluxio.client.WriteType;
import alluxio.client.file.FileInStream;
import alluxio.client.file.FileOutStream;
import alluxio.client.file.FileSystem;
import alluxio.client.file.options.CreateFileOptions;
import alluxio.client.file.options.OpenFileOptions;
import alluxio.util.io.BufferUtils;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class AlluxioFileStreamSeekTest {

  private final static long DEFAULT_BUFFER_SIZE = 10_000_000l;

  @Test
  @Ignore
  public void testWrite() throws Exception {
    Configuration.set(PropertyKey.MASTER_HOSTNAME, "localhost");
    Configuration.set(PropertyKey.USER_LOCAL_READER_PACKET_SIZE_BYTES, DEFAULT_BUFFER_SIZE);

    FileSystem fs = FileSystem.Factory.get();//get fileSystem
    FileOutStream fos = null;
    AlluxioURI uri = new AlluxioURI("/benchmark.txt");
    if (fs.exists(uri)) {
      fs.delete(uri);
    }
    CreateFileOptions options = CreateFileOptions.defaults();
    options
        .setBlockSizeBytes(DEFAULT_BUFFER_SIZE);//set block size,if enough big cant leave big data
    options.setWriteType(WriteType.CACHE_THROUGH);//set storage to memmory
    fos = fs.createFile(uri, options);
    long start = System.currentTimeMillis();
    for (int a = 0; a < DEFAULT_BUFFER_SIZE; a++) {
      fos.write(a % 2 == 0 ? 'a' : 'b');
    }
    fos.flush();
    fos.close();
    System.out.printf("total time:" + (System.currentTimeMillis() - start));

  }

  @Test
  @Ignore
  public void testNormalRead() {
    try {
      Configuration.set(PropertyKey.MASTER_HOSTNAME, "localhost");
      Configuration.set(PropertyKey.USER_LOCAL_READER_PACKET_SIZE_BYTES, DEFAULT_BUFFER_SIZE);
      FileInStream is;
      AlluxioURI uri = new AlluxioURI("/test4seek.txt");
      FileSystem fs = FileSystem.Factory.get();//get fileSystem
      is = fs.openFile(uri, OpenFileOptions.defaults().setReadType(ReadType.CACHE));
      byte[] b = new byte[1];
      long start = System.currentTimeMillis();
      StringBuffer sb = new StringBuffer();
      int count4Read = 1;
      while (is.read(b) != -1) {
        count4Read++;
      }

      System.out.println("time:" + (System.currentTimeMillis() - start));
      System.out.println("count:" + count4Read);
      System.out.println("avg time:" + (double) (System.currentTimeMillis() - start) / count4Read);

      System.out.println(sb.toString());
      is.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @Ignore
  public void testSeekRead() {
    try {
      Configuration.set(PropertyKey.MASTER_HOSTNAME, "localhost");
      Configuration.set(PropertyKey.USER_LOCAL_READER_PACKET_SIZE_BYTES, DEFAULT_BUFFER_SIZE);

      FileInStream is;
      AlluxioURI uri = new AlluxioURI("/test4seek.txt");
      FileSystem fs = FileSystem.Factory.get();
      is = fs.openFile(uri, OpenFileOptions.defaults());
      byte[] b = new byte[1];
      long start = System.currentTimeMillis();
      int count4Read = 0;
      int count4Seek = 0;
      int step = 10;
      long pos = 0;
      while (is.read(b) != -1) {
        pos += step;
        is.seek(pos);
        count4Seek++;
        count4Read++;
      }

      System.out.println("time consumed:" + (System.currentTimeMillis() - start));
      System.out.println("count4seek:" + count4Seek);
      System.out.println("count4Read:" + count4Read);
      System.out
          .println("avg time:" + (double) (System.currentTimeMillis() - start) / (count4Read + 1));
      System.out.println("last byte:" + b[0]);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @Ignore

  public void testPureSeekRead() {
    try {
      Configuration.set(PropertyKey.MASTER_HOSTNAME, "localhost");
      Configuration.set(PropertyKey.USER_LOCAL_READER_PACKET_SIZE_BYTES, DEFAULT_BUFFER_SIZE);

      FileInStream is;
      AlluxioURI uri = new AlluxioURI("/test.txt");
      FileSystem fs = FileSystem.Factory.get();
      is = fs.openFile(uri, OpenFileOptions.defaults());
      byte[] b = new byte[1];
      long start = System.currentTimeMillis();
      StringBuffer sb = new StringBuffer();
      int count4Read = 0;
      int count4Seek = 0;
      int step = 1;
      long pos = 0;
      while (pos + step < DEFAULT_BUFFER_SIZE) {
        is.seek(pos);
        pos = pos + step;
        count4Seek++;
        count4Read++;
      }

      System.out.println("time:" + (System.currentTimeMillis() - start));
      System.out.println("count4seek:" + count4Seek);
      System.out.println("count4Read:" + count4Read);
      System.out
          .println("avg time:" + (double) (System.currentTimeMillis() - start) / (count4Read + 1));
      System.out.println("last byte:" + b[0]);
      System.out.println(sb.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @Ignore
  public void testNIORead() throws Exception {
    System.out.println("Path:" + new File("vsGo").getAbsoluteFile());
//    FileChannel fc = new RandomAccessFile(new File("vsGo").getAbsoluteFile(), "r").getChannel();
//    FileChannel fc = new RandomAccessFile("/Volumes/ramdisk/alluxioworker/234964910080", "r").getChannel();
//        FileChannel fc = new RandomAccessFile("/Users/sean/MiniZone/temp/34158411776", "r").getChannel();
//    MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
    int size = 8;
    MappedByteBuffer[] buffers = new MappedByteBuffer[size];
    for (int i = 0; i < size; i++) {
      FileChannel fc = new RandomAccessFile("/Volumes/ramdisk/alluxioworker/234964910080", "r")
          .getChannel();
      buffers[i] = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
    }
    int count = 0;
    int result = 0;
    int pos = 0;
    int step = 4;
    long start = System.currentTimeMillis();
    for (int i = 0; i < size; i++) {
      MappedByteBuffer buffer = buffers[i];
      pos = 0;

      while (pos < DEFAULT_BUFFER_SIZE) {
//      r += buffer.get(temp);
//      r += buffer.get(i);
//      buffer.position(i);
//      buffer.position(i);
//      r += buffer.get();
        result = buffer.getInt();
        pos = pos + step * 2;
        buffer.position(pos);
        count++;
      }

    }
    System.out.println("count:" + count);
    System.out.println("result:" + result);
    System.out.println("time:" + (System.currentTimeMillis() - start));
  }

  @Test
  public void testNIOSpeed() throws Exception {
    long start = System.currentTimeMillis();
    long r = 0;
    RandomAccessFile f = new RandomAccessFile(
        new File("/Users/sean/MiniZone/temp/268469010432").getAbsoluteFile(), "r");
    FileChannel fc = f.getChannel();
    for (int i = 0; i < 1000000; i++) {

      MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, 10000);
//      r += buffer.getInt();

      BufferUtils.cleanDirectBuffer(buffer);
//      ((DirectBuffer) buffer).cleaner().clean();

    }
    f.close();
    System.out.println("r:" + r);
    System.out.println("time:" + (System.currentTimeMillis() - start));
  }

  @Test
  public void testOpenSpeed() throws Exception {
    long start = System.currentTimeMillis();
    long r = 0;

    for (int i = 0; i < 1000000; i++) {
      FileInputStream fc = new FileInputStream(new File("/Users/sean/MiniZone/temp/268469010432"));

//      BufferedInputStream inputStream = new BufferedInputStream(fc);
//
//      r += inputStream.read();

//      inputStream.close();
      fc.close();

    }
    System.out.println("r:" + r);
    System.out.println("time:" + (System.currentTimeMillis() - start));
  }

  @Test
  @Ignore
  public void testSeekRead4Benchmark() {
    try {
      Configuration.set(PropertyKey.MASTER_HOSTNAME, "localhost");
      Configuration.set(PropertyKey.USER_LOCAL_READER_PACKET_SIZE_BYTES, DEFAULT_BUFFER_SIZE);

      FileInStream is;
      AlluxioURI uri = new AlluxioURI("/test4seek.txt");
      FileSystem fs = FileSystem.Factory.get();
      is = fs.openFile(uri, OpenFileOptions.defaults());
      byte[] b = new byte[1];
      long start = System.currentTimeMillis();
      StringBuffer sb = new StringBuffer();
      int count4Read = 0;
      int count4Seek = 0;

      long pos = 0;
      for (long i = 0; i < DEFAULT_BUFFER_SIZE; i++) {
        pos += 10;
        is.seek(pos);
        count4Seek++;
        count4Read++;
        is.read(b);
      }

      System.out.println("time:" + (System.currentTimeMillis() - start));
      System.out.println("count4seek:" + count4Seek);
      System.out.println("count4Read:" + count4Read);
      System.out
          .println("avg time:" + (double) (System.currentTimeMillis() - start) / (count4Read + 1));
      System.out.println("last byte:" + b[0]);
      System.out.println(sb.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
