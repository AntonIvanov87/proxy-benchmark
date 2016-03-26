package ivanovanton.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  Integer id;

  @Column(name = "first_name")
  String firstName;

  @Column(name = "middle_name")
  String middleName;

  @Column(name = "last_name")
  String lastName;

  @Column(name = "birth_date")
  @Temporal(TemporalType.DATE)
  Date birthDate;

  @Column(name = "creation_time", updatable = false, nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  Date creationDate;

}
