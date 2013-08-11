package filter;

import java.io.IOException;

import javabeans.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbManager.DBManager;

public class UserFilter implements Filter {

	// 如果没有登录，返回的链接
	String backUrl;

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// System.out.println("权限过滤器");
		// 将ServletRequest转换成HttpServletRequest，
		// 实际上这里的arg0本来就是HttpServletRequest的实例
		HttpServletRequest request = null;
		if (arg0 instanceof HttpServletRequest)
			request = (HttpServletRequest) arg0;

		// 将ServletResponse转换成HttpServletResponse
		HttpServletResponse response = null;
		if (arg1 instanceof HttpServletResponse)
			response = (HttpServletResponse) arg1;

		// 获得session对象
		HttpSession session = request.getSession();

		// 没有登录就中断过滤器链，返回到登录页面
		if (session.getAttribute("user") == null) {
			response.sendRedirect(backUrl);
		} else {
			User user = (User) session.getAttribute("user");
			if (0 != DBManager.dbUtil.getGroupNameByGroupId(user.getGroupId())
					.compareTo("Admin")) {
				arg2.doFilter(arg0, arg1);
			} else {
				response.sendRedirect(backUrl);
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// 从过滤器的配置中获得初始化参数，如果没有就使用缺省值
		if (arg0.getInitParameter("backurl") != null) {
			backUrl = arg0.getInitParameter("backurl");
			// System.out.println("从配置中获得返回的链接"+backUrl);
		} else
			backUrl = "login.html";
	}
}
