package com.lids.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;

		String[] allowDomain = {"http://118.89.45.205", "http://localhost:8080"};
		Set<String> allowedOrigins = new HashSet<String>(Arrays.asList(allowDomain));
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String originHeader = httpServletRequest.getHeader("Origin");
		if (allowedOrigins.contains(originHeader)) {
			res.setHeader("Access-Control-Allow-Origin", originHeader);
		}

//		res.setHeader("Access-Control-Allow-Origin", "http://118.89.45.205");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "0");
		res.setHeader("Access-Control-Allow-Headers",
				"Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("XDomainRequestAllowed", "1");
		chain.doFilter(request, response);
	}

	public void destroy() {

	}

}
