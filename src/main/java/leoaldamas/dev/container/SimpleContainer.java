package leoaldamas.dev.container;

import java.lang.reflect.InvocationTargetException;

import java.io.InputStream;
import java.io.IOException;

import java.net.Socket;
import java.net.ServerSocket;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

public class SimpleContainer {

    private final int PORT;
    private final String CONFIG_FILENAME;
    private Map<String, HttpServlet> servlets;

    public SimpleContainer(int PORT, String CONFIG_FILENAME) {
        this.PORT = PORT;
        this.CONFIG_FILENAME = CONFIG_FILENAME;
        this.servlets = new HashMap<>();
    }

    public void start() throws IOException {
        // Create server
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            // Accept socket to our server
            Socket socket = serverSocket.accept();

            // Thread handler
            Thread handler = new SocketHandler(socket, servlets);
            handler.start();
        }
    }

    public void loadPropertiesFile() throws IOException {
        // Get the content of our config properties
        InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILENAME);

        // Check if your input contains something
        if (input == null) {
            throw new RuntimeException("Unable to find file: " + CONFIG_FILENAME);
        }

        // Properties
        Properties properties = new Properties();
        properties.load(input);

        // Map of all properties
        properties.forEach((url, servletFileName) -> {
            HttpServlet servlet = getServletInstance((String) servletFileName);
            servlet.init();
            servlets.put((String) url, servlet);
        });
    }
    public HttpServlet getServletInstance(String classFileName) {
        try {
            // Reflection
            return (HttpServlet) Class.forName(classFileName).getConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Map<String, HttpServlet> getServlets() {
        return servlets;
    }
}
