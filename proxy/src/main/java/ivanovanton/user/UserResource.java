package ivanovanton.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ivanovanton.backend.BackendClient;
import org.msgpack.MessagePack;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Path("users")
public class UserResource {

  private final BackendClient backendClient;
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final MessagePack messagePack = new MessagePack();

  public UserResource(BackendClient backendClient) {
    this.backendClient = backendClient;
    messagePack.register(UserView.class);
  }

  @GET
  @Path("generated/json")
  @Produces(MediaType.APPLICATION_JSON)
  public byte[] get() throws JsonProcessingException {
    Collection<UserView> usersViews = backendClient.getGeneratedUsersJson();
    return objectMapper.writeValueAsBytes(usersViews);
  }

  @GET
  @Path("json")
  @Produces(MediaType.APPLICATION_JSON)
  public byte[] get(@QueryParam("id") List<Integer> usersIds) throws JsonProcessingException {
    Collection<UserView> usersViews = backendClient.getUsersJson(usersIds);
    return objectMapper.writeValueAsBytes(usersViews);
  }

  @GET
  @Path("msgpack")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public byte[] getMsgPack(@QueryParam("id") List<Integer> usersIds) throws IOException {
    Collection<UserView> usersViews = backendClient.getUsersMsgPack(usersIds);
    return messagePack.write(usersViews);
  }
}
