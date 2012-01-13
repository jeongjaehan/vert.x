package vertx.tests.http;

import org.vertx.java.core.Handler;
import org.vertx.java.core.SimpleHandler;
import org.vertx.java.core.app.VertxApp;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.shareddata.SharedData;
import org.vertx.java.newtests.ContextChecker;
import org.vertx.java.newtests.TestUtils;

import java.util.Set;
import java.util.UUID;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class InstanceCheckServer implements VertxApp {

  protected TestUtils tu = new TestUtils();

  private HttpServer server;

  protected ContextChecker check;

  private final String id = UUID.randomUUID().toString();

  public void start() {
    check = new ContextChecker(tu);

    server = new HttpServer().requestHandler(new Handler<HttpServerRequest>() {
      public void handle(final HttpServerRequest req) {
        check.check();

         //We add the object id of the server to the set
        Set<String> set = SharedData.getSet("instances");
        set.add(id);
        SharedData.getCounter("requests").increment();

        req.response.end();

      }
    }).listen(8080);

    tu.appReady();
  }

  public void stop() {
    server.close(new SimpleHandler() {
      public void handle() {
        check.check();
        tu.appStopped();
      }
    });
  }

}