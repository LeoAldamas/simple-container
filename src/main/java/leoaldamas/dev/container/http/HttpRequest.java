package leoaldamas.dev.container.http;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private BufferedReader input;
    private String method;
    private String path;
    private Map<String, String> parameters;
    private Map<String, String> headers;

    public HttpRequest(BufferedReader input) {
        this.input = input;
        this.parameters = new HashMap<>();
        this.headers = new HashMap<>();
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public boolean parse() throws IOException {
        //   1                2                      3
        // /GET /example?username=ex&password=123 HTTP/1.1
        String line = input.readLine();
        // Split first line
        String[] firstLineArray = line.split(" ");
        if (firstLineArray.length != 3) return false;

        // Get method
        method = firstLineArray[0];

        // Get path
        String url = firstLineArray[1];
        int queryStringIndex = url.indexOf("?");
        if (queryStringIndex > -1) {
            path = url.substring(0, queryStringIndex);
            parseParameters(url.substring(queryStringIndex + 1));
        } else {
            path = url;
        }

        //
        line = input.readLine();
        while (line != null && !line.isEmpty()) {
            // Request headers
            String[] headerPairArray = line.split(":");
            headers.put(headerPairArray[0], headerPairArray[1]);
            line = input.readLine();
        }

        if (method.equals("POST")) {
            StringBuilder bodyParameters = new StringBuilder();
            while (input.ready()) {
                bodyParameters.append((char) input.read());
            }
            parseParameters(bodyParameters.toString());
        }

        return true;
    }

    private void parseParameters(String queryString) {
        for (String parameterPair : queryString.split("&")) {
            String[] parameterPairArray = parameterPair.split("=");
            parameters.put(parameterPairArray[0], parameterPairArray[1]);
        }
    }

    public String getRequestParameter(String s) {
        return parameters.get(s);
    }
}
