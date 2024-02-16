package com.hb.neobank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.hb.neobank.service.ClientDetailService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ClientDetailService clientDetailService;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(clientDetailService);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		final TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter(this.clientDetailService);
		http.addFilterBefore(tokenFilter, BasicAuthenticationFilter.class);
		http.cors().and().authorizeRequests().anyRequest().authenticated().and().csrf().disable().httpBasic().and()
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint();
	}

	@Override
	public void configure(WebSecurity web) {
//		web.ignoring().antMatchers("/**");
		web.ignoring().antMatchers("/icici-bank-eazypay/webhook-mid");//eazypay mid webhook
		web.ignoring().antMatchers("/rbl-banking/webhook");//eazypay mid webhook
		web.ignoring().antMatchers("/neo-webhook/razorpay");
		web.ignoring().antMatchers("/neo-webhook/razorpay/payment");
	}

}