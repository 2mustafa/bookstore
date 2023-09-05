package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.net.ssl.SSLContext;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.sun.net.httpserver.HttpServer;


import tool.Copyright;
import tool.RestJpaLifecycleProvider;

@Copyright(year=2013, holders="Sascha Baumeister")
public class ApplicationContainer {

    static public void main (final String[] args) throws IllegalArgumentException, NotDirectoryException, NoSuchFileException, IOException, ClassNotFoundException {
        final int servicePort = args.length > 0 ? Integer.parseInt(args[0]) : 8001;
        final Path resourceDirectory = Paths.get(args.length > 1 ? args[1] : "").toAbsolutePath();
        final Path keyStorePath = args.length > 2 ? Paths.get(args[2]).toAbsolutePath() : null;
        final String keyRecoveryPassword = args.length > 3 ? args[3] : "changeit";
        final String keyManagementPassword = args.length > 4 ? args[4] : keyRecoveryPassword;

        final ResourceConfig configuration = new ResourceConfig()
            .register(RestJpaLifecycleProvider.open("local_database"));
          
        try (InputStream byteSource = ApplicationContainer.class.getResourceAsStream("components.properties")) {
            final Properties properties = new Properties();
            properties.load(byteSource);
            for (final Object value : properties.values()) configuration.register(Class.forName(value.toString()));
        }

        final URI serviceURI = URI.create((keyStorePath == null ? "http://" : "https://") + TcpServers.localAddress().getCanonicalHostName() + ":" + servicePort + "/services");
        final SSLContext context = TcpServers.newTLSContext(keyStorePath, keyRecoveryPassword, keyManagementPassword);
        if (context != null) context.createSSLEngine(serviceURI.getHost(), serviceURI.getPort());

        final HttpServer container = JdkHttpServerFactory.createHttpServer(serviceURI, configuration, context);
        final HttpResourceHandler internalFileHandler = new HttpResourceHandler("/internal");
        final HttpResourceHandler externalFileHandler = new HttpResourceHandler("/external", resourceDirectory);
        container.createContext(internalFileHandler.getContextPath(), internalFileHandler);
        container.createContext(externalFileHandler.getContextPath(), externalFileHandler);

        try {
            final String origin = String.format("%s://%s:%s", serviceURI.getScheme(), serviceURI.getHost(), serviceURI.getPort());
            System.out.format("Web container running on origin %s, enter \"quit\" to stop.\n", origin);
            System.out.format("Context path \"%s\" is configured for REST service access.\n", serviceURI.getPath());
            System.out.format("Context path \"%s\" is configured for class loader access.\n", internalFileHandler.getContextPath());
            System.out.format("Context path \"%s\" is configured for file system access within \"%s\".\n", externalFileHandler.getContextPath(), resourceDirectory);
            final BufferedReader charSource = new BufferedReader(new InputStreamReader(System.in));
            while (!"quit".equals(charSource.readLine()));
        } finally {
            container.stop(0);
        }
    }
}
