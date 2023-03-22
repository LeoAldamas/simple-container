package leoaldamas.dev.servlets;

import leoaldamas.dev.container.http.HttpRequest;
import leoaldamas.dev.container.http.HttpResponse;

import leoaldamas.dev.container.HttpServlet;

import java.io.PrintWriter;

public class AnotherServlet extends HttpServlet {

    @Override
    public void init() {
        System.out.println("init() method *AnotherServlet*");
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        PrintWriter out = response.getWriter();
        out.println("HTTP/1.1 200");
        out.println("Content-Type: text/html");
        out.println();
        out.println("""
                    <!DOCTYPE html>
                    <html>
                    <body>
                      <h2>Form</h2>
                      <form method="POST">
                        <label for="username">Username</label></br>
                        <input id="username" type="text" name="username" placeholder="Username"/></br></br>
                        
                        <label for="password">Password</label></br>
                        <input id="password" type="password" name="password" placeholder="********"/></br></br>
                        
                        <input type="submit" value="Submit"/>
                      </form>
                    </body>
                    </html>
                    """);
        out.flush();
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        PrintWriter out = response.getWriter();
        String username = request.getRequestParameter("username");
        out.println("HTTP/1.1 200");
        out.println("Content-Type: text/html");
        out.println();
        out.println("""
                <!DOCTYPE html>
                <html>
                <body>
                  <h2>doPost() method in AnotherServlet...</h2>
                """);
        out.println("<p>Welcome " + username + "</p>");
        out.println("""
                </body>
                </html>
                """);
        out.flush();
    }

    @Override
    public void destroy() {
        System.out.println("destroy() method *AnotherServlet*");
    }
}
