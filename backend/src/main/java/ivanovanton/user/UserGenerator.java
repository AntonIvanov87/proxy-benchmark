package ivanovanton.user;

import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static ivanovanton.backend.BackendMain.createDataSource;
import static ivanovanton.backend.BackendMain.createSessionFactory;
import static java.lang.System.currentTimeMillis;

class UserGenerator {

  public static void main(String[] args) throws PropertyVetoException, IOException {
    DataSource dataSource = createDataSource();
    SessionFactory sessionFactory = createSessionFactory(dataSource);
    UserService userService = new UserService(sessionFactory);
    for (int i=0; i<1_000_000; i++) {
      User user = generateUser();
      userService.saveUser(user);
      System.out.println("Saved user " + i);
    }
  }

  private static User generateUser() {
    User user = new User();
    user.firstName = "firstName" + nextLong(1_000_000);
    user.middleName = "middleName" + nextLong(1_000_000);
    user.lastName = "lastName" + nextLong(1_000_000);
    user.birthDate = new Date(currentTimeMillis() - nextLong(1_000_000_000_000L));
    user.creationDate = new Date(currentTimeMillis() - nextLong(100_000_000_000L));
    return user;
  }

  private static long nextLong(long bound) {
    return ThreadLocalRandom.current().nextLong(bound);
  }

  private UserGenerator() {
  }
}
