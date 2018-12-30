/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.server;

import java.io.File;
import javax.servlet.ServletException;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.HostConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.VersionLoggerListener;

/**
 *
 * @author aplik
 */
public class TomcatEmbeddedRunner {

    public void startServer() throws LifecycleException, ServletException {
        File catalinaHome = new File("server");
        if (!catalinaHome.exists()) {
            catalinaHome.mkdir();
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8090); // HTTP port
        tomcat.setBaseDir(catalinaHome.getAbsolutePath());
        tomcat.getServer().addLifecycleListener(new VersionLoggerListener()); // nice to have

        // This magic line makes Tomcat look for WAR files in catalinaHome/webapps
        // and automatically deploy them
        tomcat.getHost().addLifecycleListener(new HostConfig());

        // Manually add WAR archives to deploy.
        // This allows to define the order in which the apps are discovered
        // plus the context path.
        File war = new File("server/webapps/comar-webpage.war");
        tomcat.addWebapp("/comar-webpage", war.getAbsolutePath());

        tomcat.start();
//        tomcat.getServer().await();
    }

}
