package ivanovanton.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class UserService {

  private final SessionFactory sessionFactory;

  public UserService(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  Collection<User> generateUsers() {
    Collection<User> users = new ArrayList<>();
    Random random = ThreadLocalRandom.current();
    for (int i=0; i<20; i++) {
      User user = new User();
      user.id = random.nextInt();
      user.firstName = "first" + random.nextInt();
      user.middleName = "middle" + random.nextInt();
      user.lastName = "last" + random.nextInt();
      user.birthDate = new Date();
      user.creationDate = new Date();
      users.add(user);
    }
    return users;
  }

  Collection<User> getUsers(List<Integer> usersIds) {
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      try {
        List<User> users = session.byMultipleIds(User.class).multiLoad(usersIds);
        transaction.commit();
        return users;
      } catch (Exception e) {
        transaction.rollback();
        throw e;
      }

    }
  }

  void saveUser(User user) {
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      try {
        session.save(user);
        transaction.commit();
      } catch (Exception e) {
        transaction.rollback();
        throw e;
      }
    }
  }
}
