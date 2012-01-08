package org.vertx.java.core.sockjs;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.streams.ReadStream;
import org.vertx.java.core.streams.WriteStream;

/**
 *
 * <p>You interact with SockJS clients through instances of SockJS socket.</p>
 *
 * <p>The API is very similar to {@link org.vertx.java.core.http.WebSocket}. It implements both
 * {@link ReadStream} and {@link WriteStream} so it can be used with {@link org.vertx.java.core.streams.Pump} to enable
 * flow control.</p>
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public abstract class SockJSSocket implements ReadStream, WriteStream {

  public abstract void close();

  public final long writeHandlerID;

  SockJSSocket() {
    this.writeHandlerID = Vertx.instance.registerHandler(new Handler<Buffer>() {
      public void handle(Buffer buff) {
        writeBuffer(buff);
      }
    });
  }

}