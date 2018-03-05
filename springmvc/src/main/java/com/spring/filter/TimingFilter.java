package com.spring.filter;

import com.utils.RequestUtils;
import org.slf4j.profiler.Profiler;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TimingFilter implements Filter {

//    private static final Logger logger = LoggerFactory.getLogger(TimingFilter.class);

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		Profiler profiler = new Profiler("Timing filter ip : " + RequestUtils.getIP(req) + ",url : " + req.getRequestURI());
		profiler.start("request");
		request.setAttribute("profiler", profiler);

		chain.doFilter(request, response);

		profiler.stop();
//        int spentMs = (int) profiler.elapsedTime() / 1000000;
		System.out.println(profiler.toString());
//        if (spentMs > 500 || request.getParameter("dbg") != null) {
//            logger.warn(profiler.toString());
//        }
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
