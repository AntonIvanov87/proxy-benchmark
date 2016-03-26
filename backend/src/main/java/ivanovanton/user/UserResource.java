package ivanovanton.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.MessagePack;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("users")
public class UserResource {

  private final UserService userService;
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final MessagePack messagePack = new MessagePack();

  public UserResource(UserService userService) {
    this.userService = userService;
    messagePack.register(UserView.class);
  }

  @GET
  @Path("generated/json")
  @Produces(MediaType.APPLICATION_JSON)
  public byte[] getGeneratedJson() throws JsonProcessingException {
    try {
      Thread.sleep(2);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
    Collection<User> users = userService.generateUsers();
    List<UserView> usersViews = convertUsers(users);
    return objectMapper.writeValueAsBytes(usersViews);
  }

  @GET
  @Path("json")
  @Produces(MediaType.APPLICATION_JSON)
  public byte[] getJson(@QueryParam("id") List<Integer> usersIds) throws JsonProcessingException {
    Collection<User> users = userService.getUsers(usersIds);
    List<UserView> usersViews = convertUsers(users);
    return objectMapper.writeValueAsBytes(usersViews);
  }

  @GET
  @Path("msgpack")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public byte[] getMsgPack(@QueryParam("id") List<Integer> usersIds) throws IOException {
    Collection<User> users = userService.getUsers(usersIds);
    List<UserView> usersViews = convertUsers(users);
    return messagePack.write(usersViews);
  }

  private static List<UserView> convertUsers(Collection<User> users) {
    return users.stream().map(UserResource::convertUser).collect(toList());
  }

  private static UserView convertUser(User user) {
    return new UserView(user.id, user.firstName, user.middleName, user.lastName, user.birthDate);
  }
}
