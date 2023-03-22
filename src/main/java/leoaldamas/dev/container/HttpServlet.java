package leoaldamas.dev.container;

import leoaldamas.dev.container.http.HttpRequest;
import leoaldamas.dev.container.http.HttpResponse;

public abstract class HttpServlet {

    public void init() {
        System.out.println("main init() method *HttpServlet*");
    }

    protected void service(HttpRequest request, HttpResponse response) {
        String method = request.getMethod();
        if (method.equals("GET")) {
            this.doGet(request, response);
        } else if (method.equals("POST")) {
            this.doPost(request, response);
        } else {
            throw new RuntimeException("Method not supported");
        }
    }

    protected void doGet(HttpRequest request, HttpResponse response) { }

    protected void doPost(HttpRequest request, HttpResponse response) { }

    public void destroy() {
        System.out.println("main destroy() method *HttpServlet*");
    }
}
