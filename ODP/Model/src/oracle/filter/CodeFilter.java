package oracle.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CodeFilter implements Filter {
	protected FilterConfig filterConfig;
	private String targetEncoding = "UTF-8";

	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest srequest, ServletResponse sresponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) srequest;
		HttpServletResponse response = (HttpServletResponse) sresponse;
		
		request.setCharacterEncoding(targetEncoding);
		response.setContentType("text/html;charset=" + targetEncoding);

		chain.doFilter(srequest, sresponse);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
