/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.servlet.ServletException;
import org.apache.catalina.LifecycleException;

/**
 *
 * @author aplik
 */
public class MainCore extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TomcatEmbeddedRunner runner = new TomcatEmbeddedRunner();
        runner.startServer();

        WebView view = new WebView();
        view.setMinSize(500, 400);
        view.setPrefSize(500, 400);
        final WebEngine eng = view.getEngine();
        eng.load("http://localhost:8090/comar-webpage");

        stage.setTitle("OK");
        stage.setScene(new Scene(view));
        stage.show();
    }

    public static void main(String[] args) throws ServletException, LifecycleException {
        Application.launch(args);
    }
}
