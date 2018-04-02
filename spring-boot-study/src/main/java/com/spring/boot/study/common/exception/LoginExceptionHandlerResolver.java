package com.spring.boot.study.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.study.common.RequestMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通用错误处理器.
 *
 * @author Wang.ch
 */
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@ControllerAdvice
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class LoginExceptionHandlerResolver extends AbstractErrorController {

    private static final Logger log = LoggerFactory.getLogger(LoginExceptionHandlerResolver.class);

    @Value("${server.error.path:${error.path:/error}}")
    private static String errorPath = "/error";

    public LoginExceptionHandlerResolver(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    /**
     * 500错误.
     *
     * @param req
     * @param rsp
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView serverError(HttpServletRequest req, HttpServletResponse rsp, Exception ex) throws Exception {
        RequestMatcher matcher = new RequestMatcher("/**");
        if (matcher.matches(req)) {
            log.error("!!! request uri:{} from {} server exception:{}", req.getRequestURI(), req.getRemoteHost(), ex.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//            String msg = mapper.writeValueAsString(BaseResponse.newFail(BaseResponse.STATUS_ERROR, "系统繁忙,请稍候重试"));
            String msg = mapper.writeValueAsString("系统繁忙,请稍候重试");
            return handleJSONError(rsp, msg, HttpStatus.OK);
        } else {
            throw ex;
        }
    }

    /**
     * 404的拦截.
     *
     * @param request
     * @param response
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> notFound(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
//        log.error("!!! request uri:{} from {} not found exception:{}", request.getRequestURI(), request.getRemoteHost(), ex.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        String msg = mapper.writeValueAsString(BaseResponse.newFail(BaseResponse.STATUS_BADREQUEST, "你访问的资源不存在"));
        String msg = mapper.writeValueAsString("你访问的资源不存在");
        handleJSONError(response, msg, HttpStatus.OK);
        return null;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> loginException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
//        log.error("!!! request uri:{} from {} not found exception:{}", request.getRequestURI(), request.getRemoteHost(), ex.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        String msg = mapper.writeValueAsString(BaseResponse.newFail(BaseResponse.STATUS_BADREQUEST, "你访问的资源不存在"));
        String msg = mapper.writeValueAsString(ex.getMessage());
        handleJSONError(response, msg, HttpStatus.OK);
        return null;
    }

    /**
     * 参数不完整错误.
     *
     * @param req
     * @param rsp
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView methodArgumentNotValidException(HttpServletRequest req, HttpServletResponse rsp, MethodArgumentNotValidException ex) throws Exception {
        RequestMatcher matcher = new RequestMatcher("/**");
        if (matcher.matches(req)) {
            BindingResult result = ex.getBindingResult();
            List<FieldError> fieldErrors = result.getFieldErrors();
            StringBuffer msg = new StringBuffer();
            fieldErrors.stream().forEach(fieldError -> {
                msg.append("[" + fieldError.getField() + "," + fieldError.getDefaultMessage() + "]");
            });
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//            String json = mapper.writeValueAsString(BaseResponse.newFail(BaseResponse.STATUS_BADREQUEST, "参数不合法:" + msg.toString()));
            String json = mapper.writeValueAsString("参数不合法");
            return this.handleJSONError(rsp, json, HttpStatus.OK);
        } else {
            throw ex;
        }
    }

    @RequestMapping
    @ResponseBody
    public ResponseEntity<?> handleErrors(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpStatus status = getStatus(request);
//        if (status == HttpStatus.NOT_FOUND) {
        return notFound(request, response, null);
//        }
//        return handleErrors(request, response);
    }

    @RequestMapping(produces = "text/html")
    public ModelAndView handleHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpStatus status = this.getStatus(request);
        Map<String, Object> model = Collections.unmodifiableMap(this.getErrorAttributes(request, this.isIncludeStackTrace(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());
        ModelAndView modelAndView = this.resolveErrorView(request, response, status, model);
        return modelAndView == null ? new ModelAndView("error", model) : modelAndView;
    }

    protected ModelAndView handleViewError(String url, String errorStack, String errorMessage, String viewName) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", errorStack);
        mav.addObject("url", url);
        mav.addObject("msg", errorMessage);
        mav.addObject("timestamp", new Date());
        mav.setViewName(viewName);
        return mav;
    }

    protected ModelAndView handleJSONError(HttpServletResponse rsp, String errorMessage, HttpStatus status) throws IOException {
        rsp.setCharacterEncoding("UTF-8");
        rsp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        rsp.setStatus(status.value());
        PrintWriter writer = rsp.getWriter();
        writer.write(errorMessage);
        writer.flush();
        writer.close();
        return null;
    }

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
        ErrorProperties.IncludeStacktrace include = new ErrorProperties().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        } else {
            return include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM ? this.getTraceParameter(request) : false;
        }
    }

}

//作者：LoWang
//        链接：https://www.jianshu.com/p/6e7651705d29
//        來源：简书
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。