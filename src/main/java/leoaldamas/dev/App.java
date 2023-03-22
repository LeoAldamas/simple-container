package leoaldamas.dev;

import leoaldamas.dev.container.SimpleContainer;

import java.io.IOException;

public class App {
    /**
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        SimpleContainer container = new SimpleContainer(8003, "config.properties");
        //
        container.loadPropertiesFile();
        //
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                container.getServlets().forEach((url, servlet) -> servlet.destroy());
            }
        });
        //
        container.start();
    }
}
