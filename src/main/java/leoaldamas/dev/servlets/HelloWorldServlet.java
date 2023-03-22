package leoaldamas.dev.servlets;

import leoaldamas.dev.container.http.HttpRequest;
import leoaldamas.dev.container.http.HttpResponse;
import leoaldamas.dev.container.HttpServlet;

import java.io.PrintWriter;

public class HelloWorldServlet extends HttpServlet {

    @Override
    public void init() {
        System.out.println("init() method *HelloWorldServlet*");
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        PrintWriter out = response.getWriter();
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println();
        out.println("""
                    <!DOCTYPE html>
                    <html>
                    <body>
                      <h2>Welcome to my doGet method HelloWorldServlet</h2>
                    </body>
                    </html>
                    """);
        out.flush();
    }

    @Override
    public void destroy() {
        System.out.println("destroy() method *HelloWorldServlet*");
    }
}
