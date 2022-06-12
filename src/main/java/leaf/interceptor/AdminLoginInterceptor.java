package leaf.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leaf
 * @create 2022-04-03 16:36
 */
@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
        if ((uri.startsWith("/manager") || uri.startsWith("/cart")) && null == request.getSession().getAttribute("user")) {
            request.getSession().setAttribute("errMessage", "请重新登陆");
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        } else {
            request.getSession().removeAttribute("errMessage");
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
