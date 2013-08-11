package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodeFilter implements Filter {
	// 替换后的字符集，从过滤器的配置参数中读取
	String newCharSet;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		// 处理请求字符集
		// System.out.println("处理请求字符集");
		request.setCharacterEncoding(newCharSet);

		// 传递给下一个过滤器，这里并没有下一个，作为过滤器的规则和良好编程习惯，应该加上次代码
		filterChain.doFilter(request, response);

		// 处理响应字符集
		// System.out.println("处理响应字符集");
		response.setContentType("text/html; charset=" + newCharSet);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// 从过滤器的配置中获得初始化参数，如果没有就使用缺省值
		if (arg0.getInitParameter("newcharset") != null) {
			newCharSet = arg0.getInitParameter("newcharset");
			// System.out.println("从配置中获得转换后字符集" + newCharSet);
		} else
			newCharSet = "utf-8";
	}
}
