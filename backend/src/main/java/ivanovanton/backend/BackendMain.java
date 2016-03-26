package ivanovanton.backend;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import ivanovanton.server.ServerFactory;
import ivanovanton.user.User;
import ivanovanton.user.UserResource;
import ivanovanton.user.UserService;
import org.eclipse.jetty.server.Server;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

public class BackendMain {

  private static final int port = 8081;

  public static void main(String[] args) throws Exception {
    //DataSource dataSource = createDataSource();
    //SessionFactory sessionFactory = createSessionFactory(dataSource);
    //UserService userService = new UserService(sessionFactory);
    UserService userService = new UserService(null);
    UserResource userResource = new UserResource(userService);
    Server server = ServerFactory.create(port, userResource);
    server.start();
  }

  public static DataSource createDataSource() throws PropertyVetoException {
    ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
    comboPooledDataSource.setDriverClass("org.postgresql.Driver");
    comboPooledDataSource.setJdbcUrl("jdbc:postgresql://localhost/proxy-benchmark");
    comboPooledDataSource.setUser("proxy-benchmark");
    comboPooledDataSource.setPassword("123");
    return comboPooledDataSource;
  }

  public static SessionFactory createSessionFactory(DataSource dataSource) throws IOException {
    LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
    localSessionFactoryBean.setDataSource(dataSource);

    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
    localSessionFactoryBean.setHibernateProperties(properties);

    localSessionFactoryBean.setAnnotatedClasses(User.class);

    localSessionFactoryBean.afterPropertiesSet();
    return localSessionFactoryBean.getObject();
  }

  private BackendMain() {
  }
}
