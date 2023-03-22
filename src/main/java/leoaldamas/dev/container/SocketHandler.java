package leoaldamas.dev.container;

import leoaldamas.dev.container.http.HttpRequest;
import leoaldamas.dev.container.http.HttpResponse;

import java.io.*;

import java.net.Socket;

import java.util.Map;

public class SocketHandler extends Thread implements AutoCloseable {

    private Socket socket;
    private HttpRequest request;
    private HttpResponse response;
    private Map<String, HttpServlet> servlets;

    public SocketHandler(Socket socket, Map<String, HttpServlet> servlets) {
        this.socket = socket;
        this.servlets = servlets;
    }

    @Override
    public void run() {
        BufferedReader input = null;
        PrintWriter output = null;
        try {
            // Input request
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Output response
            output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            //
            request = new HttpRequest(input);
            if (!request.parse()) {
                output.println("HTTP/1.1 500 Internal Server Error");
                output.println("Content-Type: text/html");
                output.println();
                output.println("""
                        <!DOCTYPE html>
                        <html>
                        <body>
                          <h2>Internal Server Error</h2>
                        </body>
                        </html>
                        """);
                output.flush();
            } else {
                //
                HttpServlet servlet = servlets.get(request.getPath());
                if (servlet != null) {
                    //
                    response = new HttpResponse(new OutputStreamWriter(socket.getOutputStream()));
                    servlet.service(request, response);
                } else {
                    output.println("HTTP/1.1 404 Not Found");
                    output.println("Content-Type: text/html");
                    output.println();
                    output.println("""
                            <DOCTYPE html>
                            <html>
                            <body>
                              <h2>Not Found</h2>
                            </body>
                            </html>
                            """);
                    output.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }
}
