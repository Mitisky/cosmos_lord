package file;

import alluxio.util.io.BufferUtils;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * This class created on 2017/12/4.
 *
 * @author Connery
 */
public class SpeedFileOpen {

  @Test
  public void testFileOpenSpeed() throws Exception {
    URL path = this.getClass().getClassLoader().getResource("forSpeedFileOpen");
    String p = path.getFile();
    int count = 1_000_000;
    long start = System.currentTimeMillis();
    while (count-- > 0) {
      RandomAccessFile f = new RandomAccessFile(p, "r");
      f.close();
    }
    System.out.printf("Time:" + (System.currentTimeMillis() - start));
  }

  @Test
  public void testFileMapSpeed() throws Exception {
    URL path = this.getClass().getClassLoader().getResource("forSpeedFileOpen");
    String p = path.getFile();
    int count = 1_000_000;
    long start = System.currentTimeMillis();
    RandomAccessFile f = new RandomAccessFile(p, "r");
    FileChannel channel = f.getChannel();
    while (count-- > 0) {
      ByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
      BufferUtils.cleanDirectBuffer(byteBuffer);
    }
    f.close();
    System.out.printf("Time:" + (System.currentTimeMillis() - start));
  }


}
