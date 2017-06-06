package Easyui_Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter{
		private FilterConfig config = null;
		public void destroy() {
			this.config = null;
		}
		public void doFilter(ServletRequest request, ServletResponse response,
				FilterChain chain) throws IOException, ServletException {
			HttpSession session = ((HttpServletRequest) request).getSession();
			String a = String.valueOf(session.getAttribute("name"));
			//System.out.println(a);
			//System.out.println("���������");
			if ("null".equals(a)) {
				//System.out.println("��תǰ");
				((HttpServletResponse)response).sendRedirect("../test/login.jsp");
				//System.out.println("��ת��");
			} else {
				chain.doFilter(request, response); 
			}
		}
		public void init(FilterConfig config) throws ServletException {
			this.config = config;
		}
}
