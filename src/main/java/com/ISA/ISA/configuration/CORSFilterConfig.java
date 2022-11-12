package com.ISA.ISA.configuration;

import io.jsonwebtoken.io.IOException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

@Component
public class CORSFilterConfig implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException, java.io.IOException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader(
                "Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Authorization, Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Access-Control-Allow-Headers");
        response.setHeader("Access-Control-Expose-Headers", "Location");
        chain.doFilter(req, res);
        }

        @Override
        public void init(FilterConfig filterConfig){
        }

        public void destroy(){
        }
}
