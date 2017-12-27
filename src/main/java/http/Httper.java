package http;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * This class created on 2017/12/4.
 *
 * @author Connery
 */
public class Httper {
    public static void httpGet(String url) throws Exception {

        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = client.execute(request);
            System.out.printf(response.toString());
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/

                url = URLDecoder.decode(url, "UTF-8");
            } else {

            }
        } catch (IOException e) {

        }

    }

    @Test
    public void testVote() throws Exception {
        for (int i = 0; i < 10; i++) {
            httpGet("http://www.niid.com.cn/tp_go.asp?id=2527");

        }
        httpGet("http://www.niid.com.cn/tp_go.asp?id=2527");
//        httpGet("www.baidu.com");
    }
}
