package eass.ros.mimic.sim;

import java.net.URL;

import com.sun.corba.se.impl.activation.ServerMain;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

public class RobotController {
    private int x = 0;
    private int y = 0;
    private Server server;
    public RobotController() throws Exception {
        int port = 8080;
        int sslPort= 8442;
        server = new Server(8080);
        SslContextFactory contextFactory = new SslContextFactory();
        contextFactory.setKeyStorePath("/Users/user/mcapl/src/examples/eass/ros/mimic/sim/server/keystore");
        contextFactory.setKeyStorePassword("password");
        SslConnectionFactory sslConnectionFactory = new SslConnectionFactory(contextFactory, org.eclipse.jetty.http.HttpVersion.HTTP_1_1.toString());

        HttpConfiguration config = new HttpConfiguration();
        config.setSecureScheme("https");
        config.setSecurePort(sslPort);
        config.setOutputBufferSize(32786);
        config.setRequestHeaderSize(8192);
        config.setResponseHeaderSize(8192);
        HttpConfiguration sslConfiguration = new HttpConfiguration(config);
        sslConfiguration.addCustomizer(new SecureRequestCustomizer());
        HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(sslConfiguration);

        ServerConnector connector = new ServerConnector(server, sslConnectionFactory, httpConnectionFactory);

        connector.setPort(sslPort);
        server.addConnector(connector);



        WebAppContext webappContext = new WebAppContext("/Users/user/mcapl/src/examples/eass/ros/mimic/sim", "/");
        server.setHandler(webappContext);


        server.start();
        System.out.println("Server started");
        server.join();

    }
    public static void main(String[] args)
    {
        try{
            RobotController rc = new RobotController();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
