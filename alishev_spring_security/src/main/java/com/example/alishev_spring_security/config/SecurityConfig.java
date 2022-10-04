package com.example.alishev_spring_security.config;

import com.example.alishev_spring_security.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // для включения анотации @PreAuthorize() в AdminService
public  class SecurityConfig extends WebSecurityConfigurerAdapter {   // главный класс для настройки SpringSecurity

  private final PersonDetailsService personDetailsService;

  @Autowired
  public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }


      // конфигурируем сам спринг секьюрити, т.е. какая страница отвечает за вход, за ошибки и т.д
      // конфигурируем авторизацию (права доступа к страницам для пользователей по роли)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // http.csrf().disable() // временно отключаем защиту от межсайтовой подделки запросов
         // .authorizeRequests()
         http.authorizeRequests() //включаем защиту от межсайтовой подделки запросов + добавляем код в login.html

                // .antMatchers("/admin").hasRole("ADMIN") // на страницу админ, можно зайти только с ролью админ (1)
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll() //страницы, к-е доступны всем
                //.anyRequest().authenticated() // означает, что на все остальные страницы нужно аутентифицироваться (когда не было ролей)
                 .anyRequest().hasAnyRole("USER", "ADMIN") // на все остальные стр. могут зайти с ролью амин и юзер
                .and() // разделяет разные по логике настройки
                .formLogin().loginPage("/auth/login")        // спринг теперь знает что для входа в приложение использ. эта страница
                .loginProcessingUrl("/process_login")   // указано в login.html
                .defaultSuccessUrl("/hello", true)  // что будет после успешной аутентификации
                .failureUrl("/auth/login?error")  // что будет в случае не успешной аутентификации
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login"); //удаление пользователя из сессии, удаление cookies у пользователя
                                                                           // реализовано в hello.html

    }

    // настраивает аутентификацию
    @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(personDetailsService)
                 .passwordEncoder(getPasswordEncoder()); // спринг при аутентификации пароль из формы будет прогонять через
                                                         //BCryptEncoder и сравнивать пароли
}

@Bean
    public PasswordEncoder getPasswordEncoder() {

      return new BCryptPasswordEncoder();
     // return NoOpPasswordEncoder.getInstance(); // возвращали пароль без шифрования
}







}
