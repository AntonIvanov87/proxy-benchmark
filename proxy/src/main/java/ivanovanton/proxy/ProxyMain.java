package ivanovanton.proxy;

import com.ning.http.client.AsyncHttpClient;
import ivanovanton.backend.BackendClient;
import ivanovanton.server.ServerFactory;
import ivanovanton.user.UserResource;
import org.eclipse.jetty.server.Server;

class ProxyMain {

  private static final int port = 8083;
  private static final String backendAddress = "127.0.0.1:8081";

  public static void main(String[] args) throws Exception {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    BackendClient backendClient = new BackendClient(backendAddress, asyncHttpClient);
    UserResource userResource = new UserResource(backendClient);
    Server server = ServerFactory.create(port, userResource);
    server.start();
  }

  private ProxyMain() {
  }
}
