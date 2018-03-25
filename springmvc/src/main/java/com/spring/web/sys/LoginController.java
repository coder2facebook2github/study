package com.spring.web.sys;

import com.spring.domain.sys.SysUser;
import com.spring.exception.SelfException;
import com.spring.service.sys.SysUserService;
import com.utils.JedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JedisService jedisService;

    @Value("${test_name}")
    private String testName;

    private static final String SYS_USER_LIST = "SYS_USER_LIST";


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public Map<String, Object> welcome() {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> result = (Map<String, Object>) jedisService.get("userMap");
        data.put("message", "Hello, " + result.get("name") + ", " + result.get("mobile"));
        return data;
    }

    @RequestMapping(value = "/exception/test")
    public String exceptionTest() {
        System.out.println("--------exception");
        if (1 == 1) {
            throw new SelfException("空指针异常");
        }
        return "";
    }

//	@RequestMapping(value = "/register", method = RequestMethod.POST)
//	public String register(User ywxUser, Model model) {
//		userService.saveNewUser(ywxUser);
//		return "redirect:user/list";
//	}

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("userList", sysUserService.queryAllUsers());
        return "user/user_list";
    }


//	@ResponseBody
//	@RequestMapping(value = "/delete/user", method = RequestMethod.POST)
//	public Map<String, Object> deleteUser(Long userId){
//		if(userId == null) {
//			throw new SelfException("用户id为空");
//		}
//		Map<String, Object> result = new HashMap<String, Object>();
//		userService.deleteUserById(userId);
//		result.put("success", true);
//		return result;
//	}

    @ResponseBody
    @RequestMapping(value = "/get/user/info", method = RequestMethod.GET)
    public Map<String, Object> getUserInfo(Long userId) {
        if (userId == null) {
            throw new SelfException("用户id为空");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        SysUser user = sysUserService.getUserById(userId);
        result.put("user", user);
        return result;
    }

//	@RequestMapping(value = "/user/modify", method = RequestMethod.POST)
//	public String userModify(User user) {
//		userService.modifyUser(user);
//		return "redirect:/user/list";
//	}

//	@RequestMapping(value = "/export/user", method = RequestMethod.POST)
//	public void exportUser(HttpServletResponse response) throws IOException {
//		response.setHeader("Content-type", "text/html;charset=UTF-8");
//		OutputStream outputStream = null;
//		outputStream = response.getOutputStream();
//		response.reset();
//		XSSFWorkbook workBook = null;
//		String fileName = "用户列表";
//		fileName = URLEncoder.encode(fileName, "UTF8");
//		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
//		response.setContentType("application / vnd.ms-excel; charset=utf-8");
//		workBook = userService.exportUserWorkbook();
//		workBook.write(outputStream);
//		outputStream.flush();
//		if (outputStream != null) {
//			outputStream.close();
//		}
//	}

//	@ResponseBody
//	@RequestMapping(value = "/redis/set", method = RequestMethod.POST)
//	public Map<String, Object> redisSet(User user){
//		Map<String, Object> result = new HashMap<>();
//		user.setCreateTime(new Date());
//		jedisService.rpush("userList", user);
//		result.put("message", true);
//		return result;
//	}

//	@ResponseBody
//	@RequestMapping(value = "/redis/get", method = RequestMethod.GET)
//	public Map<String, Object> redisGet() {
//		Map<String, Object> result = new HashMap<>();
//		result.put("hasUser", false);
//		if(!jedisService.isExist("userList")){
//			return result;
//		}
//		long userListLength = jedisService.llen("userList");
//		if(userListLength <= 0){
//			return result;
//		}
//		result.put("hasUser", true);
//		List<User> userList = jedisService.lrange("userList", 0, -1);
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		for(User user : userList) {
//			System.out.println("userName: " + user.getName() + ", creatTime: " + simpleDateFormat.format(user.getCreateTime()));
//		}
//		result.put("userList", userList);
//		return result;
//	}

    @ResponseBody
    @RequestMapping(value = "/redis/del", method = RequestMethod.POST)
    public Map<String, Object> redisDel(String key) {
        Map<String, Object> result = new HashMap<>();
        long delCount = jedisService.del(key);
        if (delCount <= 0) {
            result.put("message", false);
            result.put("key", key);
            return result;
        }
        result.put("message", true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/redis/get")
    public Map<String, Object> redisGet(String key) {
        Map<String, Object> result = new HashMap<>();
        String redisValue = jedisService.getStr(key);
        result.put(key, redisValue);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/redis/set/sysUser/list")
    public Map<String, Object> redisSet(@RequestBody List<SysUser> list, String hello) {
        Map<String, Object> result = new HashMap<>();
        for (SysUser user : list) {
            jedisService.rpush(SYS_USER_LIST, user);
        }
        jedisService.setStr("name", testName);
        jedisService.setStr("hello", hello);
        List<SysUser> userList = jedisService.lrange(SYS_USER_LIST, 0, -1);
        result.put("userList", userList);
        result.put("message", "success");
        result.put("name", jedisService.getStr("name"));
        return result;
    }
}
