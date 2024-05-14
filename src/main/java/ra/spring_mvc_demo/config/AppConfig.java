package ra.spring_mvc_demo.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ra")
public class AppConfig implements WebMvcConfigurer {
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    //Cấu hình kết nối cơ sở dữ liệu (thay connectDB)
    @Bean
    public DataSource getDataSource(){
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/employee_db");
        source.setUsername("root");
        source.setPassword("123456");
        return source;
    }
    // Cấu hình SessionFactory
    @Bean
    public SessionFactory sessionFactory(){
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        //thiết lập các thông số kết nối csdl
        sf.setDataSource(getDataSource());

        //quét qua các package tìm các class ORM (class map với bảng trong database
        sf.setPackagesToScan("ra.spring_mvc_demo");

        //Các thông số cấu hình hibernate
        Properties properties = new Properties();
        properties.put("hibernate.show_sql",true);
        // khi gọi đến hàm của hibernate thì sẽ sinh ra câu lệnh sql ở màn hình console
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        // nói cho hibernate biết sẽ làm việc với hệ quản trị csdl nào (mysql, sqlsever, oracle)
        sf.setHibernateProperties(properties);
        try {
            sf.afterPropertiesSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sf.getObject();
    }
}
