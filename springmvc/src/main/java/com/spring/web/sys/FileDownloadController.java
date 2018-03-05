package com.spring.web.sys;


import com.alibaba.fastjson.JSONObject;
import com.spring.comment.EnvironmentEnum;
import com.spring.domain.sys.SysUser;
import com.spring.service.JedisService;
import com.spring.service.sys.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Controller
public class FileDownloadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private JedisService jedisService;
	@Value("${environment}")
	private String environment;

	/**
	 * 通过往response 里写入json字符串流来返回json数据
	 * @param response
	 * @param userId
	 * @throws IOException
	 */
	@RequestMapping(value = "/get/user/id", method = RequestMethod.GET)
	public void userId(HttpServletResponse response, Long userId) throws IOException {
		SysUser user = sysUserService.getUserById(userId);
		response.setContentType("application/json");
		Map<String, Object> result = new HashMap<>();
		result.put("data", user);
		result.put("message", "success");
		result.put("code", 200);
		response.getOutputStream().write(JSONObject.toJSONString(result).getBytes(UTF_8));
	}

	/**
	 * 下载文件
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/download/file", method = RequestMethod.POST)
	public void downloadFile(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.reset();
		System.out.println("environment: " +environment);
		String filePath = "";
		if(EnvironmentEnum.HOME.name.equals(environment)){
			filePath = "D:\\MyIDE\\IDEA\\IdeaWorkspace\\apache-tomcat-9.0.0.M22/RUNNING.txt";
		} else if(EnvironmentEnum.COMPANY.name.equals(environment)){
			filePath = "D:\\MyDrivers\\readme.txt";
		}
		File file = new File(filePath);
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

		byte[] buffer = new byte[inputStream.available()];
		inputStream.read(buffer);
		inputStream.close();
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		LOGGER.info("userAgent: " + userAgent);
		String fileName = "模板";
		if (userAgent.indexOf("firefox") >= 0) {
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		} else {
			fileName = URLEncoder.encode(fileName, "UTF8");
		}
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".pdf\"");
		response.setContentType("application/pdf");
		response.addHeader("Content-Length", String.valueOf(file.length()));
		outputStream.write(buffer);
		outputStream.flush();
		outputStream.close();
	}

	public void download(HttpServletRequest request, HttpServletResponse response){

	}
}
