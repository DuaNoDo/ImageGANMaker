package com.teadone.imgm;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//http.antMatcher("/**").authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated();
		http.authorizeRequests()
        .antMatchers("/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2Login()
        .loginPage("/login")
        .authorizationEndpoint()
        .baseUri("/oauth2/authorize-client")
        .authorizationRequestRepository(authorizationRequestRepository())
        .and()
        .tokenEndpoint()
        .accessTokenResponseClient(accessTokenResponseClient())
        .and()
        .defaultSuccessUrl("/loginSuccess")
        .failureUrl("/loginFailure");
		/*
		 * http.antMatcher("/**").authorizeRequests().antMatchers("/",
		 * "/login**").permitAll().anyRequest()
		 * .authenticated().and().exceptionHandling() .authenticationEntryPoint(new
		 * LoginUrlAuthenticationEntryPoint("/")).and() .addFilterBefore(ssoFilter(),
		 * BasicAuthenticationFilter.class); // logout http.logout()
		 * .invalidateHttpSession(true) .clearAuthentication(true)
		 * .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		 * .logoutSuccessUrl("/") .permitAll();
		 */

	}

		@Bean
	    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
	        return new HttpSessionOAuth2AuthorizationRequestRepository();
	    }

	    @Bean
	    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
	        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
	        return accessTokenResponseClient;
	    }

	    
	    // additional configuration for non-Spring Boot projects
	    private static List<String> clients = Arrays.asList("google", "facebook");

	    //@Bean
	    public ClientRegistrationRepository clientRegistrationRepository() {
	        List<ClientRegistration> registrations = clients.stream()
	            .map(c -> getRegistration(c))
	            .filter(registration -> registration != null)
	            .collect(Collectors.toList());

	        return new InMemoryClientRegistrationRepository(registrations);
	    }

	    private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";

	    @Autowired
	    private Environment env;

	    private ClientRegistration getRegistration(String client) {
	        String clientId = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-id");

	        if (clientId == null) {
	            return null;
	        }

	        String clientSecret = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-secret");
	        if (client.equals("google")) {
	            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
	                .clientId(clientId)
	                .clientSecret(clientSecret)
	                .build();
	        }
	        if (client.equals("facebook")) {
	            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
	                .clientId(clientId)
	                .clientSecret(clientSecret)
	                .build();
	        }
	        return null;
	    }
}
