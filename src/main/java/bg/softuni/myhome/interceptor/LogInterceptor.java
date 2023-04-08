package bg.softuni.myhome.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;


public class LogInterceptor implements HandlerInterceptor {

    private final static Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);



    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        UUID uuid = UUID.randomUUID();
        request.setAttribute("start", System.currentTimeMillis());
        request.setAttribute("request-id", uuid);
        LOG.info("{} - calling {}", uuid, request.getRequestURI());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView){

        LOG.info("{} - response in {}ms",
                request.getAttribute("request-id"),
                System.currentTimeMillis() - (long) request.getAttribute("start"));

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception exception){

        LOG.info("{} - completed in {}ms",
        request.getAttribute("request-id"),
        System.currentTimeMillis() - (long) request.getAttribute("start"));

    }

}
