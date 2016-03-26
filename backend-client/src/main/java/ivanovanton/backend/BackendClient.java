package ivanovanton.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import ivanovanton.user.UserView;
import org.msgpack.MessagePack;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class BackendClient {

  private final String backendAddress;
  private final AsyncHttpClient asyncHttpClient;
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final MessagePack messagePack = new MessagePack();

  public BackendClient(String backendAddress, AsyncHttpClient asyncHttpClient) {
    this.backendAddress = backendAddress;
    this.asyncHttpClient = asyncHttpClient;
    messagePack.register(UserView.class);
  }

  public Collection<UserView> getGeneratedUsersJson() {
    Request request = createUsersRequest("generated/json", emptyList());
    Response response = executeRequest(request);
    UserView[] usersViews;
    try {
      InputStream responseBodyStream = response.getResponseBodyAsStream();
      usersViews = objectMapper.readValue(responseBodyStream, UserView[].class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return asList(usersViews);
  }

  public Collection<UserView> getUsersJson(Collection<Integer> usersIds) {
    Request request = createUsersRequest("json", usersIds);
    Response response = executeRequest(request);
    UserView[] usersViews;
    try {
      InputStream responseBodyStream = response.getResponseBodyAsStream();
      usersViews = objectMapper.readValue(responseBodyStream, UserView[].class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return asList(usersViews);
  }

  public Collection<UserView> getUsersMsgPack(Collection<Integer> usersIds) {
    Request request = createUsersRequest("msgpack", usersIds);
    Response response = executeRequest(request);
    UserView[] usersViews;
    try {
      InputStream responseBodyStream = response.getResponseBodyAsStream();
      usersViews = messagePack.read(responseBodyStream, UserView[].class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return asList(usersViews);
  }

  private Request createUsersRequest(String format, Collection<Integer> usersIds) {
    RequestBuilder requestBuilder = new RequestBuilder("GET")
            .setUrl("http://" + backendAddress + "/users/" + format);
    for (int userId : usersIds) {
      requestBuilder.addQueryParam("id", String.valueOf(userId));
    }
    return requestBuilder.build();
  }

  private Response executeRequest(Request request) {
    Future<Response> responseFuture = asyncHttpClient.executeRequest(request);
    try {
      return responseFuture.get();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
