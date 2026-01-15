package Front_Controller_Pattern;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.jasper.servlet.JasperInitializer;

import java.io.File;

public class ServletApplication {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        // 커넥터 초기화
        tomcat.getConnector();

        File webappDir = new File("src/main/webapp");
        // 웹 루트 연결
        Context ctx = tomcat.addWebapp("", webappDir.getAbsolutePath());
        ctx.addServletContainerInitializer(new JasperInitializer(), null);

        StandardRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(
                resources,
                "/",
                new File("src/main/resources/static").getAbsolutePath(),
                "/"
        ));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}