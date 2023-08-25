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
import java.util.Set;

@Component
@WebFilter("/*")
public class IndiaIPFilter implements Filter {

    private Set<String> indiaIPRanges; // Load Indian IP ranges from the geolocation database

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String clientIP = request.getRemoteAddr();

        if (isIndiaIP(clientIP)) {
            chain.doFilter(request, response); // Allow the request to proceed
        } else {
            response.getWriter().write("Access denied. Requests must originate from India.");
        }
    }

    private boolean isIndiaIP(String ip) {
        // Lookup the IP's country using the geolocation database
        String country = lookupCountry(ip);

        // Compare the country with the Indian country code
        return "IN".equals(country); // Replace with actual Indian country code
    }

    private String lookupCountry(String ip) {
        // Use the geolocation database to determine the country for the given IP
        // Return the country code (e.g., "IN" for India)
        return "IN"; // Replace with actual lookup logic
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Load Indian IP ranges from the geolocation database
     //
        //   indiaIPRanges = loadIndianIPRanges(); // Implement this method
    }

    @Override
    public void destroy() {
        // Cleanup code
    }
}

