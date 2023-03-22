package leoaldamas.dev.container.http;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class HttpResponse {

    private OutputStreamWriter outputStreamWriter;
    private PrintWriter writer;

    public HttpResponse(OutputStreamWriter outputStreamWriter) {
        this.outputStreamWriter = outputStreamWriter;
        this.writer = new PrintWriter(outputStreamWriter);
    }

    public OutputStreamWriter getOutputStreamWriter() {
        return outputStreamWriter;
    }

    public PrintWriter getWriter() {
        return writer;
    }
}
