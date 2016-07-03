package bg.uni.sofia.fmi.homeworksystem.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.uni.sofia.fmi.homeworksystem.services.CurrentUserContext;

@WebFilter("/AuthorizationRequestFilter")
public class AuthorizationRequestFilter implements Filter {

	private static final String LOGIN_URI = "/webapi/hmwsrest/v1/user/login"; 
	
	@Inject
	private CurrentUserContext curUserCtx;
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String loginPath = request.getContextPath() + "/login";
        String restLoginPath = request.getContextPath() + LOGIN_URI;
        String path = request.getRequestURI().toString();
		if (curUserCtx.getUser() == null && !path.equals(restLoginPath)) {
			response.sendRedirect(loginPath);
		}
		
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
