package ivanovanton.user;

import java.util.Date;

public class UserView {

  public int id;
  public String firstName;
  public String middleName;
  public String lastName;
  public Date birthDate;

  public UserView() {
  }

  public UserView(int id, String firstName, String middleName, String lastName, Date birthDate) {
    this.id = id;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.birthDate = birthDate;
  }
}
