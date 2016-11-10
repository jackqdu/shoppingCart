package com.security;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

 
public class RegisterApp extends ResourceConfig 
{
    public RegisterApp() 
    {
        packages("com.security");
        //register(LoggingFilter.class);
        
        //Register Auth Filter here
        //register(AuthenticationFilter.class);
        register(AuthorizationAndAuthenticationRequestFilter.class);
    }
}
