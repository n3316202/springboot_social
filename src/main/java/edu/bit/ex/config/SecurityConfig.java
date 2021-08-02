package edu.bit.ex.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import edu.bit.ex.security.CustomNoOpPasswordEncoder;
import edu.bit.ex.security.EmpDetailsService;
import edu.bit.ex.security.PrincipalOauth2UserService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private EmpDetailsService empDetailsService;

	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { // 5
		http.authorizeRequests() // 6
				.antMatchers("/login", "/signup", "/user","/").permitAll() // 누구나 접근 허용
				.antMatchers("/user").hasRole("USER") // USER, ADMIN만 접근 가능
				.antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
				.anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
				.and()
				.formLogin() // 7
				.loginPage("/login") // 로그인 페이지 링크
				.defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
				.and()
				.logout() // 8
				.logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 주소
				.invalidateHttpSession(true) // 세션 날리기
				.and()
				.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정
				.userService(principalOauth2UserService)
		;
	}

	// 해당 메소드의 리턴되는 오브젝트를 ioc로 등록
	@Bean
	public CustomNoOpPasswordEncoder passwordEncoder() {
		return new CustomNoOpPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(empDetailsService).passwordEncoder(passwordEncoder());
	}

}
