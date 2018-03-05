package com.github;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;


public class ApacheHttpClientStudy {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApacheHttpClientStudy.class);
	public static void main(String... args) {
		new ApacheHttpClientStudy().httpPostRequest2();
	}


	public void httpGetRequest() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://www.baidu.com");
		System.out.println("Executing request: " + httpGet.getRequestLine());
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			System.out.println(response.getStatusLine());
			HttpEntity httpEntity = response.getEntity();
			if(httpClient != null) {
				InputStream inputStream = httpEntity.getContent();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				StringBuilder httpResult = new StringBuilder("");
				String readLine = null;
				while ((readLine = bufferedReader.readLine()) != null) {
					httpResult.append(readLine);
				}
				inputStream.close();
				bufferedReader.close();
				System.out.println(httpResult);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Content-Type: application/x-www-form-urlencoded
	 */
	public void httpPostRequest() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpPost httpPost = new HttpPost("http://localhost:8080/edms/account/config/test");
		StringEntity stringEntity = new StringEntity("type=2&account=15144785620&password=123456&loginPassword=yui&companyId=77&mobile=15633257803", "utf-8");
		stringEntity.setContentType("application/x-www-form-urlencoded");
		httpPost.addHeader("Authorization", "a eyJhbGciOiJIUzI1NiJ9.eyJjb21wYW55X2lkIjozODYsImNvbXBhbnlfbmFtZSI6IumCteWutuWkp-mZoiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwibW9iaWxlIjoiMTM1MjI0ODI5NzAiLCJpZCI6MTc2NjIsIm5hbWUiOiLpgrUiLCJob3N0IjoibG9jYWxob3N0OjgwODAiLCJ1YSI6Ik1vemlsbGEvNS4wIChXaW5kb3dzIE5UIDEwLjA7IFdpbjY0OyB4NjQpIEFwcGxlV2ViS2l0LzUzNy4zNiAoS0hUTUwsIGxpa2UgR2Vja28pIENocm9tZS81OS4wLjMwNzEuODYgU2FmYXJpLzUzNy4zNiIsImNvdXJpZXIiOnsiaWQiOjE3NjYyLCJjYXJkX251bSI6IiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwiY29tcGFueV9pZCI6Mzg2LCJjb21wYW55X25hbWUiOiLpgrXlrrblpKfpmaIiLCJyb2xlIjoxLCJjaXR5IjoxMTAxMDAsInNjb3JlIjowLCJ1c2VfYmFsYW5jZSI6MCwid29ya2VyX2NhcmRfaW1hZ2UiOiIiLCJsYXR0aWNlX2lkcyI6IiIsImFncmVlbWVudF9maWxlIjoiIiwidmVyaWZ5IjoyLCJzdGF0dXMiOjEsInBvc3RfY29kZSI6IjQ5OTIzNCIsInN0YWZmX25hbWUiOm51bGwsInNmYWZmX21vYmlsZSI6bnVsbCwiZmVlX3J1bGVzX2lkIjoxLCJqb2luX2FjdGl2aXR5IjowLCJ1c2VycyI6eyJuYW1lIjoi6YK1IiwicGFzc3dvcmQiOiJjNDk5NDczZThlMjRlYmNiZWQyNzViMDA3MWU3ZTViNSIsImVuY3J5cHQiOiJldDQyZ3JkeTVnZGc3aTlmIn19LCJ1c2VyIjp7ImlkIjo1MTYzMiwibW9iaWxlIjoiMTM1MjI0ODI5NzAiLCJwYXNzd29yZCI6ImM0OTk0NzNlOGUyNGViY2JlZDI3NWIwMDcxZTdlNWI1IiwiZW5jcnlwdCI6ImV0NDJncmR5NWdkZzdpOWYiLCJ1c2VyX3R5cGUiOjEsImJhbGFuY2UiOjAsImVtYWlsIjoiIiwibmlja25hbWUiOiIiLCJhdmF0YXIiOiIiLCJuYW1lIjoi6YK1IiwiYmlydGhkYXkiOm51bGwsInNleCI6MCwiaWRjYXJkIjoiMjIwMzIyMTk4NTAyMjAxMTk3IiwiaWRjYXJkX2Zyb250X2ltYWdlIjoiaHR0cDovL3N1ZGl5aS1lZG1zLm9zcy5hbGl5dW5jcy5jb20vc3VkaXlpLWVkbXMvNjZlOGJkZWU1YTg4NWIwMjk3OWIyOTE0NjhiZjQyOGEuanBnIiwiaWRjYXJkX2JhY2tfaW1hZ2UiOiJodHRwOi8vc3VkaXlpLWVkbXMub3NzLmFsaXl1bmNzLmNvbS9zdWRpeWktZWRtcy9hNTcwNzYwMzVlMWNhMWQwY2E2ZTkxNTM4NTgwMjJhMC5qcGciLCJjbGllbnRfb3MiOiIiLCJjbGllbnRfdXVpZCI6IkFORFJPSURfZTc3MzM5ZTAxZDZjOTI0Yjg0MjQwM2FjZWI0MmM4NGUiLCJ2aXAiOjAsInByb3ZpbmNlIjowLCJjaXR5IjowLCJkaXN0cmljdCI6MCwiYWRkcmVzcyI6IiIsInRpY2tldCI6ImE5ZGY3MjBiNmJhMDQ2NDE5MGQ3Y2NlNWZiY2MzOGFiIiwidGlja2V0X3RpbWUiOjE0NzAwMjA0MTIsInRvdGFsX2luY29tZSI6MCwidG90YWxfc3BlbmQiOjAsInZlcmlmeSI6Miwic3RhdHVzIjoxLCJwdXNoX292ZXJfZGF5IjoxLCJwdXNoX3N0YXR1cyI6MSwiY3JlYXRlZF9hdCI6MTQ2OTIwMTAzOCwidXBkYXRlZF9hdCI6MTQ5NTA4MDI2MX0sInZlcmlmeSI6Miwic3RhdHVzIjoxLCJwYXJlbnRfaWQiOjAsImNvbXBhbnkiOnsiaWQiOjM4NiwibmFtZSI6IumCteWutuWkp-mZoiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwiYWRkcmVzcyI6IuaZr-WxseWJjeihlzTlj7ciLCJsaWNlbmNlIjoiIiwiYmFsYW5jZSI6MCwicHJvdmluY2UiOjAsImNpdHkiOjExMDEwMCwiZGlzdHJpY3QiOjExMDEwMSwidmVyaWZ5IjoyLCJzdGF0dXMiOjEsImxhdGl0dWRlIjozOS45MTY3LCJsb25naXR1ZGUiOjExNi4zOTcsImluY29tZSI6MCwicGhvbmVfbnVtYmVyIjoiMTM1MjI0ODI5NzAiLCJsaWNlbmNlX2ltYWdlIjoiaHR0cDovL3N1ZGl5aS1lZG1zLm9zcy5hbGl5dW5jcy5jb20vc3VkaXlpLWVkbXMvMDJjNDRjMTA3NjQwM2M4OWMxZTczOTEzMjMyNDkzNDguanBnIiwibGVnYWxfZW50aXR5IjoiIiwibGVnYWxfZW50aXR5X2lkY2FyZCI6IiIsImxlZ2FsX2VudGl0eV9pZGNhcmRfZnJvbnRfaW1hZ2UiOiIiLCJsZWdhbF9lbnRpdHlfaWRjYXJkX2JhY2tfaW1hZ2UiOiIiLCJjcmVhdGVkX2J5IjowLCJzaXRlX3Bob3RlIjpudWxsLCJicmFuZF9pZCI6NTksImZlZV9ydWxlc19pZCI6MSwic3RhZmZfbmFtZSI6bnVsbCwic3RhZmZfbW9iaWxlIjpudWxsLCJtYW5hZ2VyX21vYmlsZSI6bnVsbCwib3JkZXJfY291bnQiOjB9LCJpYXQiOjE0OTgxMTQwNzgsImV4cCI6MTQ5ODcxODg3OCwiZmxhZyI6bnVsbH0.TviZv0abCSYngpRZOmFUp8LKF_twcjdWUOxf-1AQNhU");
		httpPost.setEntity(stringEntity);
		HttpEntity responseEntity = null;
		String httpResult = "";
		try {
			response = httpClient.execute(httpPost);
			responseEntity = response.getEntity();
			httpResult = EntityUtils.toString(responseEntity, UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(httpClient != null) {
					httpClient.close();
				}
				if(response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(httpResult);
	}


	/**
	 * Content-Type: application/json
	 */
	public void httpPostRequest2() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpPost httpPost = new HttpPost("http://test.sms.sudiyi.cn/api/v1/sms/send");

		Map<String, Object> params = new HashMap<>();
//		content.put("type", "2");
//		content.put("account", "15144785620");
//		content.put("password", "123456");
//		content.put("loginPassword", "小明");
//		content.put("companyId", 99);
//		content.put("mobile", 15633257803L);
//		System.out.println(new JSONObject(content).toString());
		params.put("mobile", "18511717504");
		params.put("content", "Hello world !");
		params.put("sms_type", "advertisement");
		params.put("source", "edms");
		String dataJsonStr = com.alibaba.fastjson.JSONObject.toJSONString(params);
		LOGGER.info("data: " + dataJsonStr);
		String sign = DigestUtils.md5Hex(dataJsonStr + "2tdqFQXXgA4Hk9s2");
		com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
		jsonObject.put("data", dataJsonStr);
		jsonObject.put("business_type", 100);
		jsonObject.put("sign", sign);
		String postData = jsonObject.toString();
		StringEntity stringEntity = new StringEntity(postData, UTF_8);
		stringEntity.setContentType("application/json");
		httpPost.addHeader("Authorization", "a eyJhbGciOiJIUzI1NiJ9.eyJjb21wYW55X2lkIjozODYsImNvbXBhbnlfbmFtZSI6IumCteWutuWkp-mZoiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwibW9iaWxlIjoiMTM1MjI0ODI5NzAiLCJpZCI6MTc2NjIsIm5hbWUiOiLpgrUiLCJob3N0IjoibG9jYWxob3N0OjgwODAiLCJ1YSI6Ik1vemlsbGEvNS4wIChXaW5kb3dzIE5UIDEwLjA7IFdpbjY0OyB4NjQpIEFwcGxlV2ViS2l0LzUzNy4zNiAoS0hUTUwsIGxpa2UgR2Vja28pIENocm9tZS81OS4wLjMwNzEuODYgU2FmYXJpLzUzNy4zNiIsImNvdXJpZXIiOnsiaWQiOjE3NjYyLCJjYXJkX251bSI6IiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwiY29tcGFueV9pZCI6Mzg2LCJjb21wYW55X25hbWUiOiLpgrXlrrblpKfpmaIiLCJyb2xlIjoxLCJjaXR5IjoxMTAxMDAsInNjb3JlIjowLCJ1c2VfYmFsYW5jZSI6MCwid29ya2VyX2NhcmRfaW1hZ2UiOiIiLCJsYXR0aWNlX2lkcyI6IiIsImFncmVlbWVudF9maWxlIjoiIiwidmVyaWZ5IjoyLCJzdGF0dXMiOjEsInBvc3RfY29kZSI6IjQ5OTIzNCIsInN0YWZmX25hbWUiOm51bGwsInNmYWZmX21vYmlsZSI6bnVsbCwiZmVlX3J1bGVzX2lkIjoxLCJqb2luX2FjdGl2aXR5IjowLCJ1c2VycyI6eyJuYW1lIjoi6YK1IiwicGFzc3dvcmQiOiJjNDk5NDczZThlMjRlYmNiZWQyNzViMDA3MWU3ZTViNSIsImVuY3J5cHQiOiJldDQyZ3JkeTVnZGc3aTlmIn19LCJ1c2VyIjp7ImlkIjo1MTYzMiwibW9iaWxlIjoiMTM1MjI0ODI5NzAiLCJwYXNzd29yZCI6ImM0OTk0NzNlOGUyNGViY2JlZDI3NWIwMDcxZTdlNWI1IiwiZW5jcnlwdCI6ImV0NDJncmR5NWdkZzdpOWYiLCJ1c2VyX3R5cGUiOjEsImJhbGFuY2UiOjAsImVtYWlsIjoiIiwibmlja25hbWUiOiIiLCJhdmF0YXIiOiIiLCJuYW1lIjoi6YK1IiwiYmlydGhkYXkiOm51bGwsInNleCI6MCwiaWRjYXJkIjoiMjIwMzIyMTk4NTAyMjAxMTk3IiwiaWRjYXJkX2Zyb250X2ltYWdlIjoiaHR0cDovL3N1ZGl5aS1lZG1zLm9zcy5hbGl5dW5jcy5jb20vc3VkaXlpLWVkbXMvNjZlOGJkZWU1YTg4NWIwMjk3OWIyOTE0NjhiZjQyOGEuanBnIiwiaWRjYXJkX2JhY2tfaW1hZ2UiOiJodHRwOi8vc3VkaXlpLWVkbXMub3NzLmFsaXl1bmNzLmNvbS9zdWRpeWktZWRtcy9hNTcwNzYwMzVlMWNhMWQwY2E2ZTkxNTM4NTgwMjJhMC5qcGciLCJjbGllbnRfb3MiOiIiLCJjbGllbnRfdXVpZCI6IkFORFJPSURfZTc3MzM5ZTAxZDZjOTI0Yjg0MjQwM2FjZWI0MmM4NGUiLCJ2aXAiOjAsInByb3ZpbmNlIjowLCJjaXR5IjowLCJkaXN0cmljdCI6MCwiYWRkcmVzcyI6IiIsInRpY2tldCI6ImE5ZGY3MjBiNmJhMDQ2NDE5MGQ3Y2NlNWZiY2MzOGFiIiwidGlja2V0X3RpbWUiOjE0NzAwMjA0MTIsInRvdGFsX2luY29tZSI6MCwidG90YWxfc3BlbmQiOjAsInZlcmlmeSI6Miwic3RhdHVzIjoxLCJwdXNoX292ZXJfZGF5IjoxLCJwdXNoX3N0YXR1cyI6MSwiY3JlYXRlZF9hdCI6MTQ2OTIwMTAzOCwidXBkYXRlZF9hdCI6MTQ5NTA4MDI2MX0sInZlcmlmeSI6Miwic3RhdHVzIjoxLCJwYXJlbnRfaWQiOjAsImNvbXBhbnkiOnsiaWQiOjM4NiwibmFtZSI6IumCteWutuWkp-mZoiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwiYWRkcmVzcyI6IuaZr-WxseWJjeihlzTlj7ciLCJsaWNlbmNlIjoiIiwiYmFsYW5jZSI6MCwicHJvdmluY2UiOjAsImNpdHkiOjExMDEwMCwiZGlzdHJpY3QiOjExMDEwMSwidmVyaWZ5IjoyLCJzdGF0dXMiOjEsImxhdGl0dWRlIjozOS45MTY3LCJsb25naXR1ZGUiOjExNi4zOTcsImluY29tZSI6MCwicGhvbmVfbnVtYmVyIjoiMTM1MjI0ODI5NzAiLCJsaWNlbmNlX2ltYWdlIjoiaHR0cDovL3N1ZGl5aS1lZG1zLm9zcy5hbGl5dW5jcy5jb20vc3VkaXlpLWVkbXMvMDJjNDRjMTA3NjQwM2M4OWMxZTczOTEzMjMyNDkzNDguanBnIiwibGVnYWxfZW50aXR5IjoiIiwibGVnYWxfZW50aXR5X2lkY2FyZCI6IiIsImxlZ2FsX2VudGl0eV9pZGNhcmRfZnJvbnRfaW1hZ2UiOiIiLCJsZWdhbF9lbnRpdHlfaWRjYXJkX2JhY2tfaW1hZ2UiOiIiLCJjcmVhdGVkX2J5IjowLCJzaXRlX3Bob3RlIjpudWxsLCJicmFuZF9pZCI6NTksImZlZV9ydWxlc19pZCI6MSwic3RhZmZfbmFtZSI6bnVsbCwic3RhZmZfbW9iaWxlIjpudWxsLCJtYW5hZ2VyX21vYmlsZSI6bnVsbCwib3JkZXJfY291bnQiOjB9LCJpYXQiOjE0OTgxMTQwNzgsImV4cCI6MTQ5ODcxODg3OCwiZmxhZyI6bnVsbH0.TviZv0abCSYngpRZOmFUp8LKF_twcjdWUOxf-1AQNhU");
		httpPost.setEntity(stringEntity);
		HttpEntity responseEntity = null;
		String httpResult = "";
		try {
			response = httpClient.execute(httpPost);
			responseEntity = response.getEntity();
			httpResult = EntityUtils.toString(responseEntity, UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null) {
					response.close();
				}
				if(httpClient != null) {
					httpClient.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(httpResult);
	}

	/**
	 * 上传文件
	 */
	public void httpPostFileUploadRequest() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/edms/account/config/file/upload");
		CloseableHttpResponse httpResponse = null;
		File fileText = new File("C:\\Users\\Desktop\\workFiles/jar.txt");
		String fileName = "C:\\Users\\Desktop\\workFiles/微信截图_20170626151040.png";
		File filePic = new File(fileName);
		MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
		/**
		 * 以浏览器兼容模式运行，防止文件名乱码
		 */
		entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		entityBuilder.setCharset(UTF_8);
		entityBuilder.addPart("textFile", new FileBody(fileText));
		entityBuilder.addPart("picFile", new FileBody(filePic));
		HttpEntity requestHttpEntity = entityBuilder.build();
		httpPost.setEntity(requestHttpEntity);

		HttpEntity responseHttpEntity = null;
		String httpResult = "";
		try {
			httpResponse = httpClient.execute(httpPost);
			responseHttpEntity = httpResponse.getEntity();
			httpResult = EntityUtils.toString(responseHttpEntity, UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
				httpResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(httpResult);
	}
}
