package com.ToDoList.IpFilter;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


@Component
@WebFilter("/*")
public class IndiaIPFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String clientIP = request.getRemoteAddr();

        if (isIndianIP(clientIP)) {
            chain.doFilter(request, response); // Allow the request to proceed
        } else {
            response.getWriter().write("Access denied. Requests must originate from India.");
        }
    }

    private boolean isIndianIP(String ip) {
        String country = lookupCountry(ip);
        return "IN".equals(country); // Replace with actual Indian country code
    }

    private String lookupCountry(String ip) {
        //we can call third party api to get geoDetails for an ip
        return "IN"; // Replace with actual lookup logic
    }

    @Override
    public void destroy() {
        // Cleanup code
    }
}

