package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;

public class HTTPSVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new HTTPSVerticle());
  }

  @Override
  public void start() {
    vertx.createHttpServer(
      new HttpServerOptions()
        .setSsl(true)
        .setKeyStoreOptions(
          new JksOptions()
            .setPath("certificates.keystore")
            .setPassword("localhost")
        )
    ).requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8443, listen -> {
      if (listen.succeeded()) {
        System.out.println("Server listening on https://localhost:8443/");
      } else {
        listen.cause().printStackTrace();
        System.exit(1);
      }
    });
  }
}

