package com.github;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import static java.nio.charset.StandardCharsets.UTF_8;
/**
 * java 发送Http请求
 */
public class JavaHttpURLConnectionStudy {

	String getUrl = "http://localhost:8080/edms/parcel/courier/list";
	String postUrl = "http://localhost:8080/edms/account/config/test";

	public static void main(String... args) {
		new JavaHttpURLConnectionStudy().javaHttpPost2();
	}

	public void javaHttpGet() {
		try {
			URL url = new URL(getUrl);
			URLConnection urlConnection = (HttpURLConnection) url.openConnection();
//			urlConnection.setRequestProperty("Authorization", "a eyJhbGciOiJIUzI1NiJ9.eyJjb21wYW55X2lkIjozODYsImNvbXBhbnlfbmFtZSI6IumCteWutuWkp-mZoiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwibW9iaWxlIjoiMTM1MjI0ODI5NzAiLCJpZCI6MTc2NjIsIm5hbWUiOiLpgrUiLCJob3N0IjoibG9jYWxob3N0OjgwODAiLCJ1YSI6Ik1vemlsbGEvNS4wIChXaW5kb3dzIE5UIDEwLjA7IFdpbjY0OyB4NjQpIEFwcGxlV2ViS2l0LzUzNy4zNiAoS0hUTUwsIGxpa2UgR2Vja28pIENocm9tZS81OS4wLjMwNzEuODYgU2FmYXJpLzUzNy4zNiIsImNvdXJpZXIiOnsiaWQiOjE3NjYyLCJjYXJkX251bSI6IiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwiY29tcGFueV9pZCI6Mzg2LCJjb21wYW55X25hbWUiOiLpgrXlrrblpKfpmaIiLCJyb2xlIjoxLCJjaXR5IjoxMTAxMDAsInNjb3JlIjowLCJ1c2VfYmFsYW5jZSI6MCwid29ya2VyX2NhcmRfaW1hZ2UiOiIiLCJsYXR0aWNlX2lkcyI6IiIsImFncmVlbWVudF9maWxlIjoiIiwidmVyaWZ5IjoyLCJzdGF0dXMiOjEsInBvc3RfY29kZSI6IjQ5OTIzNCIsInN0YWZmX25hbWUiOm51bGwsInNmYWZmX21vYmlsZSI6bnVsbCwiZmVlX3J1bGVzX2lkIjoxLCJqb2luX2FjdGl2aXR5IjowLCJ1c2VycyI6eyJuYW1lIjoi6YK1IiwicGFzc3dvcmQiOiJjNDk5NDczZThlMjRlYmNiZWQyNzViMDA3MWU3ZTViNSIsImVuY3J5cHQiOiJldDQyZ3JkeTVnZGc3aTlmIn19LCJ1c2VyIjp7ImlkIjo1MTYzMiwibW9iaWxlIjoiMTM1MjI0ODI5NzAiLCJwYXNzd29yZCI6ImM0OTk0NzNlOGUyNGViY2JlZDI3NWIwMDcxZTdlNWI1IiwiZW5jcnlwdCI6ImV0NDJncmR5NWdkZzdpOWYiLCJ1c2VyX3R5cGUiOjEsImJhbGFuY2UiOjAsImVtYWlsIjoiIiwibmlja25hbWUiOiIiLCJhdmF0YXIiOiIiLCJuYW1lIjoi6YK1IiwiYmlydGhkYXkiOm51bGwsInNleCI6MCwiaWRjYXJkIjoiMjIwMzIyMTk4NTAyMjAxMTk3IiwiaWRjYXJkX2Zyb250X2ltYWdlIjoiaHR0cDovL3N1ZGl5aS1lZG1zLm9zcy5hbGl5dW5jcy5jb20vc3VkaXlpLWVkbXMvNjZlOGJkZWU1YTg4NWIwMjk3OWIyOTE0NjhiZjQyOGEuanBnIiwiaWRjYXJkX2JhY2tfaW1hZ2UiOiJodHRwOi8vc3VkaXlpLWVkbXMub3NzLmFsaXl1bmNzLmNvbS9zdWRpeWktZWRtcy9hNTcwNzYwMzVlMWNhMWQwY2E2ZTkxNTM4NTgwMjJhMC5qcGciLCJjbGllbnRfb3MiOiIiLCJjbGllbnRfdXVpZCI6IkFORFJPSURfZTc3MzM5ZTAxZDZjOTI0Yjg0MjQwM2FjZWI0MmM4NGUiLCJ2aXAiOjAsInByb3ZpbmNlIjowLCJjaXR5IjowLCJkaXN0cmljdCI6MCwiYWRkcmVzcyI6IiIsInRpY2tldCI6ImE5ZGY3MjBiNmJhMDQ2NDE5MGQ3Y2NlNWZiY2MzOGFiIiwidGlja2V0X3RpbWUiOjE0NzAwMjA0MTIsInRvdGFsX2luY29tZSI6MCwidG90YWxfc3BlbmQiOjAsInZlcmlmeSI6Miwic3RhdHVzIjoxLCJwdXNoX292ZXJfZGF5IjoxLCJwdXNoX3N0YXR1cyI6MSwiY3JlYXRlZF9hdCI6MTQ2OTIwMTAzOCwidXBkYXRlZF9hdCI6MTQ5NTA4MDI2MX0sInZlcmlmeSI6Miwic3RhdHVzIjoxLCJwYXJlbnRfaWQiOjAsImNvbXBhbnkiOnsiaWQiOjM4NiwibmFtZSI6IumCteWutuWkp-mZoiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwiYWRkcmVzcyI6IuaZr-WxseWJjeihlzTlj7ciLCJsaWNlbmNlIjoiIiwiYmFsYW5jZSI6MCwicHJvdmluY2UiOjAsImNpdHkiOjExMDEwMCwiZGlzdHJpY3QiOjExMDEwMSwidmVyaWZ5IjoyLCJzdGF0dXMiOjEsImxhdGl0dWRlIjozOS45MTY3LCJsb25naXR1ZGUiOjExNi4zOTcsImluY29tZSI6MCwicGhvbmVfbnVtYmVyIjoiMTM1MjI0ODI5NzAiLCJsaWNlbmNlX2ltYWdlIjoiaHR0cDovL3N1ZGl5aS1lZG1zLm9zcy5hbGl5dW5jcy5jb20vc3VkaXlpLWVkbXMvMDJjNDRjMTA3NjQwM2M4OWMxZTczOTEzMjMyNDkzNDguanBnIiwibGVnYWxfZW50aXR5IjoiIiwibGVnYWxfZW50aXR5X2lkY2FyZCI6IiIsImxlZ2FsX2VudGl0eV9pZGNhcmRfZnJvbnRfaW1hZ2UiOiIiLCJsZWdhbF9lbnRpdHlfaWRjYXJkX2JhY2tfaW1hZ2UiOiIiLCJjcmVhdGVkX2J5IjowLCJzaXRlX3Bob3RlIjpudWxsLCJicmFuZF9pZCI6NTksImZlZV9ydWxlc19pZCI6MSwic3RhZmZfbmFtZSI6bnVsbCwic3RhZmZfbW9iaWxlIjpudWxsLCJtYW5hZ2VyX21vYmlsZSI6bnVsbCwib3JkZXJfY291bnQiOjB9LCJpYXQiOjE0OTgxMTQwNzgsImV4cCI6MTQ5ODcxODg3OCwiZmxhZyI6bnVsbH0.TviZv0abCSYngpRZOmFUp8LKF_twcjdWUOxf-1AQNhU");
			urlConnection.addRequestProperty("Authorization", "a eyJhbGciOiJIUzI1NiJ9.eyJjb21wYW55X2lkIjozODYsImNvbXBhbnlfbmFtZSI6IumCteWutuWkp-mZoiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwibW9iaWxlIjoiMTM1MjI0ODI5NzAiLCJpZCI6MTc2NjIsIm5hbWUiOiLpgrUiLCJob3N0IjoibG9jYWxob3N0OjgwODAiLCJ1YSI6Ik1vemlsbGEvNS4wIChXaW5kb3dzIE5UIDEwLjA7IFdpbjY0OyB4NjQpIEFwcGxlV2ViS2l0LzUzNy4zNiAoS0hUTUwsIGxpa2UgR2Vja28pIENocm9tZS81OS4wLjMwNzEuODYgU2FmYXJpLzUzNy4zNiIsImNvdXJpZXIiOnsiaWQiOjE3NjYyLCJjYXJkX251bSI6IiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwiY29tcGFueV9pZCI6Mzg2LCJjb21wYW55X25hbWUiOiLpgrXlrrblpKfpmaIiLCJyb2xlIjoxLCJjaXR5IjoxMTAxMDAsInNjb3JlIjowLCJ1c2VfYmFsYW5jZSI6MCwid29ya2VyX2NhcmRfaW1hZ2UiOiIiLCJsYXR0aWNlX2lkcyI6IiIsImFncmVlbWVudF9maWxlIjoiIiwidmVyaWZ5IjoyLCJzdGF0dXMiOjEsInBvc3RfY29kZSI6IjQ5OTIzNCIsInN0YWZmX25hbWUiOm51bGwsInNmYWZmX21vYmlsZSI6bnVsbCwiZmVlX3J1bGVzX2lkIjoxLCJqb2luX2FjdGl2aXR5IjowLCJ1c2VycyI6eyJuYW1lIjoi6YK1IiwicGFzc3dvcmQiOiJjNDk5NDczZThlMjRlYmNiZWQyNzViMDA3MWU3ZTViNSIsImVuY3J5cHQiOiJldDQyZ3JkeTVnZGc3aTlmIn19LCJ1c2VyIjp7ImlkIjo1MTYzMiwibW9iaWxlIjoiMTM1MjI0ODI5NzAiLCJwYXNzd29yZCI6ImM0OTk0NzNlOGUyNGViY2JlZDI3NWIwMDcxZTdlNWI1IiwiZW5jcnlwdCI6ImV0NDJncmR5NWdkZzdpOWYiLCJ1c2VyX3R5cGUiOjEsImJhbGFuY2UiOjAsImVtYWlsIjoiIiwibmlja25hbWUiOiIiLCJhdmF0YXIiOiIiLCJuYW1lIjoi6YK1IiwiYmlydGhkYXkiOm51bGwsInNleCI6MCwiaWRjYXJkIjoiMjIwMzIyMTk4NTAyMjAxMTk3IiwiaWRjYXJkX2Zyb250X2ltYWdlIjoiaHR0cDovL3N1ZGl5aS1lZG1zLm9zcy5hbGl5dW5jcy5jb20vc3VkaXlpLWVkbXMvNjZlOGJkZWU1YTg4NWIwMjk3OWIyOTE0NjhiZjQyOGEuanBnIiwiaWRjYXJkX2JhY2tfaW1hZ2UiOiJodHRwOi8vc3VkaXlpLWVkbXMub3NzLmFsaXl1bmNzLmNvbS9zdWRpeWktZWRtcy9hNTcwNzYwMzVlMWNhMWQwY2E2ZTkxNTM4NTgwMjJhMC5qcGciLCJjbGllbnRfb3MiOiIiLCJjbGllbnRfdXVpZCI6IkFORFJPSURfZTc3MzM5ZTAxZDZjOTI0Yjg0MjQwM2FjZWI0MmM4NGUiLCJ2aXAiOjAsInByb3ZpbmNlIjowLCJjaXR5IjowLCJkaXN0cmljdCI6MCwiYWRkcmVzcyI6IiIsInRpY2tldCI6ImE5ZGY3MjBiNmJhMDQ2NDE5MGQ3Y2NlNWZiY2MzOGFiIiwidGlja2V0X3RpbWUiOjE0NzAwMjA0MTIsInRvdGFsX2luY29tZSI6MCwidG90YWxfc3BlbmQiOjAsInZlcmlmeSI6Miwic3RhdHVzIjoxLCJwdXNoX292ZXJfZGF5IjoxLCJwdXNoX3N0YXR1cyI6MSwiY3JlYXRlZF9hdCI6MTQ2OTIwMTAzOCwidXBkYXRlZF9hdCI6MTQ5NTA4MDI2MX0sInZlcmlmeSI6Miwic3RhdHVzIjoxLCJwYXJlbnRfaWQiOjAsImNvbXBhbnkiOnsiaWQiOjM4NiwibmFtZSI6IumCteWutuWkp-mZoiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwiYWRkcmVzcyI6IuaZr-WxseWJjeihlzTlj7ciLCJsaWNlbmNlIjoiIiwiYmFsYW5jZSI6MCwicHJvdmluY2UiOjAsImNpdHkiOjExMDEwMCwiZGlzdHJpY3QiOjExMDEwMSwidmVyaWZ5IjoyLCJzdGF0dXMiOjEsImxhdGl0dWRlIjozOS45MTY3LCJsb25naXR1ZGUiOjExNi4zOTcsImluY29tZSI6MCwicGhvbmVfbnVtYmVyIjoiMTM1MjI0ODI5NzAiLCJsaWNlbmNlX2ltYWdlIjoiaHR0cDovL3N1ZGl5aS1lZG1zLm9zcy5hbGl5dW5jcy5jb20vc3VkaXlpLWVkbXMvMDJjNDRjMTA3NjQwM2M4OWMxZTczOTEzMjMyNDkzNDguanBnIiwibGVnYWxfZW50aXR5IjoiIiwibGVnYWxfZW50aXR5X2lkY2FyZCI6IiIsImxlZ2FsX2VudGl0eV9pZGNhcmRfZnJvbnRfaW1hZ2UiOiIiLCJsZWdhbF9lbnRpdHlfaWRjYXJkX2JhY2tfaW1hZ2UiOiIiLCJjcmVhdGVkX2J5IjowLCJzaXRlX3Bob3RlIjpudWxsLCJicmFuZF9pZCI6NTksImZlZV9ydWxlc19pZCI6MSwic3RhZmZfbmFtZSI6bnVsbCwic3RhZmZfbW9iaWxlIjpudWxsLCJtYW5hZ2VyX21vYmlsZSI6bnVsbCwib3JkZXJfY291bnQiOjB9LCJpYXQiOjE0OTgxMTQwNzgsImV4cCI6MTQ5ODcxODg3OCwiZmxhZyI6bnVsbH0.TviZv0abCSYngpRZOmFUp8LKF_twcjdWUOxf-1AQNhU");
			System.out.println(urlConnection.getURL().toString());
			InputStream inputStream = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder response = new StringBuilder("");
			String readLine = null;
			while ((readLine = bufferedReader.readLine()) != null) {
				response.append(readLine);
			}
			inputStream.close();
			bufferedReader.close();
			System.out.println(response.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Content-Type: application/x-www-form-urlencoded
	 */
	public void javaHttpPost() {
		try {
			URL url = new URL(postUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");
			urlConnection.setUseCaches(false);
			urlConnection.setInstanceFollowRedirects(false);
			urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConnection.addRequestProperty("Authorization", "a eyJhbGciOiJIUzI1NiJ9.eyJjb21wYW55X2lkIjozODYsImNvbXBhbnlfbmFtZSI6IumCteWutuWkp-mZoiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwibW9iaWxlIjoiMTM1MjI0ODI5NzAiLCJpZCI6MTc2NjIsIm5hbWUiOiLpgrUiLCJob3N0IjoibG9jYWxob3N0OjgwODAiLCJ1YSI6Ik1vemlsbGEvNS4wIChXaW5kb3dzIE5UIDEwLjA7IFdpbjY0OyB4NjQpIEFwcGxlV2ViS2l0LzUzNy4zNiAoS0hUTUwsIGxpa2UgR2Vja28pIENocm9tZS81OS4wLjMwNzEuODYgU2FmYXJpLzUzNy4zNiIsImNvdXJpZXIiOnsiaWQiOjE3NjYyLCJjYXJkX251bSI6IiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwiY29tcGFueV9pZCI6Mzg2LCJjb21wYW55X25hbWUiOiLpgrXlrrblpKfpmaIiLCJyb2xlIjoxLCJjaXR5IjoxMTAxMDAsInNjb3JlIjowLCJ1c2VfYmFsYW5jZSI6MCwid29ya2VyX2NhcmRfaW1hZ2UiOiIiLCJsYXR0aWNlX2lkcyI6IiIsImFncmVlbWVudF9maWxlIjoiIiwidmVyaWZ5IjoyLCJzdGF0dXMiOjEsInBvc3RfY29kZSI6IjQ5OTIzNCIsInN0YWZmX25hbWUiOm51bGwsInNmYWZmX21vYmlsZSI6bnVsbCwiZmVlX3J1bGVzX2lkIjoxLCJqb2luX2FjdGl2aXR5IjowLCJ1c2VycyI6eyJuYW1lIjoi6YK1IiwicGFzc3dvcmQiOiJjNDk5NDczZThlMjRlYmNiZWQyNzViMDA3MWU3ZTViNSIsImVuY3J5cHQiOiJldDQyZ3JkeTVnZGc3aTlmIn19LCJ1c2VyIjp7ImlkIjo1MTYzMiwibW9iaWxlIjoiMTM1MjI0ODI5NzAiLCJwYXNzd29yZCI6ImM0OTk0NzNlOGUyNGViY2JlZDI3NWIwMDcxZTdlNWI1IiwiZW5jcnlwdCI6ImV0NDJncmR5NWdkZzdpOWYiLCJ1c2VyX3R5cGUiOjEsImJhbGFuY2UiOjAsImVtYWlsIjoiIiwibmlja25hbWUiOiIiLCJhdmF0YXIiOiIiLCJuYW1lIjoi6YK1IiwiYmlydGhkYXkiOm51bGwsInNleCI6MCwiaWRjYXJkIjoiMjIwMzIyMTk4NTAyMjAxMTk3IiwiaWRjYXJkX2Zyb250X2ltYWdlIjoiaHR0cDovL3N1ZGl5aS1lZG1zLm9zcy5hbGl5dW5jcy5jb20vc3VkaXlpLWVkbXMvNjZlOGJkZWU1YTg4NWIwMjk3OWIyOTE0NjhiZjQyOGEuanBnIiwiaWRjYXJkX2JhY2tfaW1hZ2UiOiJodHRwOi8vc3VkaXlpLWVkbXMub3NzLmFsaXl1bmNzLmNvbS9zdWRpeWktZWRtcy9hNTcwNzYwMzVlMWNhMWQwY2E2ZTkxNTM4NTgwMjJhMC5qcGciLCJjbGllbnRfb3MiOiIiLCJjbGllbnRfdXVpZCI6IkFORFJPSURfZTc3MzM5ZTAxZDZjOTI0Yjg0MjQwM2FjZWI0MmM4NGUiLCJ2aXAiOjAsInByb3ZpbmNlIjowLCJjaXR5IjowLCJkaXN0cmljdCI6MCwiYWRkcmVzcyI6IiIsInRpY2tldCI6ImE5ZGY3MjBiNmJhMDQ2NDE5MGQ3Y2NlNWZiY2MzOGFiIiwidGlja2V0X3RpbWUiOjE0NzAwMjA0MTIsInRvdGFsX2luY29tZSI6MCwidG90YWxfc3BlbmQiOjAsInZlcmlmeSI6Miwic3RhdHVzIjoxLCJwdXNoX292ZXJfZGF5IjoxLCJwdXNoX3N0YXR1cyI6MSwiY3JlYXRlZF9hdCI6MTQ2OTIwMTAzOCwidXBkYXRlZF9hdCI6MTQ5NTA4MDI2MX0sInZlcmlmeSI6Miwic3RhdHVzIjoxLCJwYXJlbnRfaWQiOjAsImNvbXBhbnkiOnsiaWQiOjM4NiwibmFtZSI6IumCteWutuWkp-mZoiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwiYWRkcmVzcyI6IuaZr-WxseWJjeihlzTlj7ciLCJsaWNlbmNlIjoiIiwiYmFsYW5jZSI6MCwicHJvdmluY2UiOjAsImNpdHkiOjExMDEwMCwiZGlzdHJpY3QiOjExMDEwMSwidmVyaWZ5IjoyLCJzdGF0dXMiOjEsImxhdGl0dWRlIjozOS45MTY3LCJsb25naXR1ZGUiOjExNi4zOTcsImluY29tZSI6MCwicGhvbmVfbnVtYmVyIjoiMTM1MjI0ODI5NzAiLCJsaWNlbmNlX2ltYWdlIjoiaHR0cDovL3N1ZGl5aS1lZG1zLm9zcy5hbGl5dW5jcy5jb20vc3VkaXlpLWVkbXMvMDJjNDRjMTA3NjQwM2M4OWMxZTczOTEzMjMyNDkzNDguanBnIiwibGVnYWxfZW50aXR5IjoiIiwibGVnYWxfZW50aXR5X2lkY2FyZCI6IiIsImxlZ2FsX2VudGl0eV9pZGNhcmRfZnJvbnRfaW1hZ2UiOiIiLCJsZWdhbF9lbnRpdHlfaWRjYXJkX2JhY2tfaW1hZ2UiOiIiLCJjcmVhdGVkX2J5IjowLCJzaXRlX3Bob3RlIjpudWxsLCJicmFuZF9pZCI6NTksImZlZV9ydWxlc19pZCI6MSwic3RhZmZfbmFtZSI6bnVsbCwic3RhZmZfbW9iaWxlIjpudWxsLCJtYW5hZ2VyX21vYmlsZSI6bnVsbCwib3JkZXJfY291bnQiOjB9LCJpYXQiOjE0OTgxMTQwNzgsImV4cCI6MTQ5ODcxODg3OCwiZmxhZyI6bnVsbH0.TviZv0abCSYngpRZOmFUp8LKF_twcjdWUOxf-1AQNhU");
			urlConnection.connect();

			DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
			String content = "type=2&account=15144785620&password=123456&loginPassword=yui&companyId=77&mobile=15633257803";
			dataOutputStream.writeBytes(content);
			dataOutputStream.flush();
			dataOutputStream.close();

			InputStream inputStream = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder response = new StringBuilder("");
			String readLine = null;
			while ((readLine = bufferedReader.readLine()) != null) {
				response.append(readLine);
			}
			inputStream.close();
			bufferedReader.close();

			System.out.println(response.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Content-Type: application/json
	 */
	public void javaHttpPost2() {
		try {
			URL url = new URL(postUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");
			urlConnection.setUseCaches(false);
			urlConnection.setInstanceFollowRedirects(false);
			urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//			urlConnection.addRequestProperty("Authorization", "a eyJhbGciOiJIUzI1NiJ9.eyJjb21wYW55X2lkIjozODYsImNvbXBhbnlfbmFtZSI6IumCteWutuWkp-mZoiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwibW9iaWxlIjoiMTM1MjI0ODI5NzAiLCJpZCI6MTc2NjIsIm5hbWUiOiLpgrUiLCJob3N0IjoibG9jYWxob3N0OjgwODAiLCJ1YSI6Ik1vemlsbGEvNS4wIChXaW5kb3dzIE5UIDEwLjA7IFdpbjY0OyB4NjQpIEFwcGxlV2ViS2l0LzUzNy4zNiAoS0hUTUwsIGxpa2UgR2Vja28pIENocm9tZS81OS4wLjMwNzEuODYgU2FmYXJpLzUzNy4zNiIsImNvdXJpZXIiOnsiaWQiOjE3NjYyLCJjYXJkX251bSI6IiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwiY29tcGFueV9pZCI6Mzg2LCJjb21wYW55X25hbWUiOiLpgrXlrrblpKfpmaIiLCJyb2xlIjoxLCJjaXR5IjoxMTAxMDAsInNjb3JlIjowLCJ1c2VfYmFsYW5jZSI6MCwid29ya2VyX2NhcmRfaW1hZ2UiOiIiLCJsYXR0aWNlX2lkcyI6IiIsImFncmVlbWVudF9maWxlIjoiIiwidmVyaWZ5IjoyLCJzdGF0dXMiOjEsInBvc3RfY29kZSI6IjQ5OTIzNCIsInN0YWZmX25hbWUiOm51bGwsInNmYWZmX21vYmlsZSI6bnVsbCwiZmVlX3J1bGVzX2lkIjoxLCJqb2luX2FjdGl2aXR5IjowLCJ1c2VycyI6eyJuYW1lIjoi6YK1IiwicGFzc3dvcmQiOiJjNDk5NDczZThlMjRlYmNiZWQyNzViMDA3MWU3ZTViNSIsImVuY3J5cHQiOiJldDQyZ3JkeTVnZGc3aTlmIn19LCJ1c2VyIjp7ImlkIjo1MTYzMiwibW9iaWxlIjoiMTM1MjI0ODI5NzAiLCJwYXNzd29yZCI6ImM0OTk0NzNlOGUyNGViY2JlZDI3NWIwMDcxZTdlNWI1IiwiZW5jcnlwdCI6ImV0NDJncmR5NWdkZzdpOWYiLCJ1c2VyX3R5cGUiOjEsImJhbGFuY2UiOjAsImVtYWlsIjoiIiwibmlja25hbWUiOiIiLCJhdmF0YXIiOiIiLCJuYW1lIjoi6YK1IiwiYmlydGhkYXkiOm51bGwsInNleCI6MCwiaWRjYXJkIjoiMjIwMzIyMTk4NTAyMjAxMTk3IiwiaWRjYXJkX2Zyb250X2ltYWdlIjoiaHR0cDovL3N1ZGl5aS1lZG1zLm9zcy5hbGl5dW5jcy5jb20vc3VkaXlpLWVkbXMvNjZlOGJkZWU1YTg4NWIwMjk3OWIyOTE0NjhiZjQyOGEuanBnIiwiaWRjYXJkX2JhY2tfaW1hZ2UiOiJodHRwOi8vc3VkaXlpLWVkbXMub3NzLmFsaXl1bmNzLmNvbS9zdWRpeWktZWRtcy9hNTcwNzYwMzVlMWNhMWQwY2E2ZTkxNTM4NTgwMjJhMC5qcGciLCJjbGllbnRfb3MiOiIiLCJjbGllbnRfdXVpZCI6IkFORFJPSURfZTc3MzM5ZTAxZDZjOTI0Yjg0MjQwM2FjZWI0MmM4NGUiLCJ2aXAiOjAsInByb3ZpbmNlIjowLCJjaXR5IjowLCJkaXN0cmljdCI6MCwiYWRkcmVzcyI6IiIsInRpY2tldCI6ImE5ZGY3MjBiNmJhMDQ2NDE5MGQ3Y2NlNWZiY2MzOGFiIiwidGlja2V0X3RpbWUiOjE0NzAwMjA0MTIsInRvdGFsX2luY29tZSI6MCwidG90YWxfc3BlbmQiOjAsInZlcmlmeSI6Miwic3RhdHVzIjoxLCJwdXNoX292ZXJfZGF5IjoxLCJwdXNoX3N0YXR1cyI6MSwiY3JlYXRlZF9hdCI6MTQ2OTIwMTAzOCwidXBkYXRlZF9hdCI6MTQ5NTA4MDI2MX0sInZlcmlmeSI6Miwic3RhdHVzIjoxLCJwYXJlbnRfaWQiOjAsImNvbXBhbnkiOnsiaWQiOjM4NiwibmFtZSI6IumCteWutuWkp-mZoiIsImJyYW5kIjoi5ZC06Imz5rWL6K-VIiwiYWRkcmVzcyI6IuaZr-WxseWJjeihlzTlj7ciLCJsaWNlbmNlIjoiIiwiYmFsYW5jZSI6MCwicHJvdmluY2UiOjAsImNpdHkiOjExMDEwMCwiZGlzdHJpY3QiOjExMDEwMSwidmVyaWZ5IjoyLCJzdGF0dXMiOjEsImxhdGl0dWRlIjozOS45MTY3LCJsb25naXR1ZGUiOjExNi4zOTcsImluY29tZSI6MCwicGhvbmVfbnVtYmVyIjoiMTM1MjI0ODI5NzAiLCJsaWNlbmNlX2ltYWdlIjoiaHR0cDovL3N1ZGl5aS1lZG1zLm9zcy5hbGl5dW5jcy5jb20vc3VkaXlpLWVkbXMvMDJjNDRjMTA3NjQwM2M4OWMxZTczOTEzMjMyNDkzNDguanBnIiwibGVnYWxfZW50aXR5IjoiIiwibGVnYWxfZW50aXR5X2lkY2FyZCI6IiIsImxlZ2FsX2VudGl0eV9pZGNhcmRfZnJvbnRfaW1hZ2UiOiIiLCJsZWdhbF9lbnRpdHlfaWRjYXJkX2JhY2tfaW1hZ2UiOiIiLCJjcmVhdGVkX2J5IjowLCJzaXRlX3Bob3RlIjpudWxsLCJicmFuZF9pZCI6NTksImZlZV9ydWxlc19pZCI6MSwic3RhZmZfbmFtZSI6bnVsbCwic3RhZmZfbW9iaWxlIjpudWxsLCJtYW5hZ2VyX21vYmlsZSI6bnVsbCwib3JkZXJfY291bnQiOjB9LCJpYXQiOjE0OTgxMTQwNzgsImV4cCI6MTQ5ODcxODg3OCwiZmxhZyI6bnVsbH0.TviZv0abCSYngpRZOmFUp8LKF_twcjdWUOxf-1AQNhU");


			/**
			 * 使用字符输出流，在传入中文时不会乱码
			 */
//			DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "utf-8"));
			Map<String, Object> content = new HashMap<>();
			content.put("type", "2");
			content.put("account", "15144785620");
			content.put("password", "123456");
			content.put("loginPassword", "中国");
			content.put("companyId", 88);
			content.put("mobile", 15633257803L);
			System.out.println(new JSONObject(content).toString());
//			dataOutputStream.writeBytes(new JSONObject(content).toString());
			printWriter.print(new JSONObject(content).toString());
			printWriter.flush();
			printWriter.close();
//			dataOutputStream.flush();
//			dataOutputStream.close();
			urlConnection.connect();

			InputStream inputStream = null;
			if(HttpURLConnection.HTTP_OK <= urlConnection.getResponseCode() && urlConnection.getResponseCode() < HttpURLConnection.HTTP_MULT_CHOICE){
				inputStream = urlConnection.getInputStream();
			} else if(null != urlConnection.getErrorStream()){
				inputStream = urlConnection.getErrorStream();
			}
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF_8));
			StringBuilder response = new StringBuilder("");
			String readLine = null;
			while ((readLine = bufferedReader.readLine()) != null) {
				response.append(readLine);
			}

			inputStream.close();
			bufferedReader.close();

			System.out.println(response.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
