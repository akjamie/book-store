User service

Demonstrates basic user management , authentication and authorization functionalities.

Core config class using spring security 5.7.x
```
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	@Autowired
	private UserService userService;

	@Autowired
	private SmsCodeService smsCodeService;

	@Autowired
	private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

	@Autowired
	AuthenticationProviderExceptionFilter authenticationProviderExceptionFilter;
	@Autowired
	private JwtTokenAuthenticationSuccessHandler jwtTokenAuthenticationSuccessHandler;

	@Autowired
	private JwtTokenAuthenticationFailureHandler jwtTokenAuthenticationFailureHandler;

	@Autowired
	private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
	@Autowired
	private JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint;

	@Autowired
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.headers().frameOptions().disable()
				//use token thus no need session
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				//access control for endpoints
				.and().authorizeRequests()
				.antMatchers("/h2-console/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/actuator",
						"/actuator/**", "/v1/errors").permitAll()
				.antMatchers(DEFAULT_SIGN_IN_PROCESSING_URL_FORM, DEFAULT_SIGN_IN_PROCESSING_URL_SMS,
						"/v1/auth/sms-code").permitAll()
				.anyRequest().authenticated()
				// login config
				.and().formLogin()
				.loginProcessingUrl(DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
				.failureHandler(jwtTokenAuthenticationFailureHandler)
				.successHandler(jwtTokenAuthenticationSuccessHandler)
				.permitAll()
				//logout
				.and().logout().logoutUrl(DEFAULT_SIGN_OUT_PROCESSING_URL)
				.logoutSuccessHandler(jwtLogoutSuccessHandler)
				// authentication providers
				.and().authenticationManager(authenticationManager())
				// filters
				.addFilterBefore(mobileSmsCodeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtTokenAuthenticationFilter, LogoutFilter.class)
				.addFilterBefore(authenticationProviderExceptionFilter, CorsFilter.class)
				// exception handle
				.exceptionHandling().authenticationEntryPoint(jsonAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(List.of(usernamePasswordAuthenticationProvider(), mobileSmsCodeAuthenticationProvider()));
	}

	@Bean
	public MobileSmsCodeAuthenticationFilter mobileSmsCodeAuthenticationFilter() {
		MobileSmsCodeAuthenticationFilter filter = new MobileSmsCodeAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(jwtTokenAuthenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(jwtTokenAuthenticationFailureHandler);
		return filter;
	}


	public UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider() {
		return new UsernamePasswordAuthenticationProvider(bCryptPasswordEncoder, userService);
	}

	public MobileSmsCodeAuthenticationProvider mobileSmsCodeAuthenticationProvider() {
		return new MobileSmsCodeAuthenticationProvider(bCryptPasswordEncoder, userService, smsCodeService);
	}
```

RSA keystore PKCS12
keytool -genkeypair -alias rsa-4096-20221017 -keyalg rsa -keysize 4096 -validity 3650 -keystore keystore.p12 -storetype PKCS12

openssl pkcs12 -in keystore.p12 -nocerts -nodes -out rsa-20221017.key
