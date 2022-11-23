package com.shopping.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.shopping.service.UserServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class UserServer {

	private static final Logger logger = Logger.getLogger(UserServer.class.getName());
	private Server server;

	public void startServer() {

		try {

			int port = 5001;
			server = ServerBuilder
					.forPort(port)
					.addService(new UserServiceImpl())
					.build();
			server.start();
			logger.info( "User server strated on port: "+port);
			
			shutdownHook();
			
		} catch (IOException ex) {
	       logger.log(Level.SEVERE, "Server  did not start",ex);
		}
	}


    public void stopServer() throws InterruptedException {
        if(server!=null){
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if(server!=null){
            server.awaitTermination();
        }
    }

	public void shutdownHook() {

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.info("Clean server shutdown in case JVM was shutdown!");

				try {
					UserServer.this.stopServer();
				} catch (InterruptedException ex) {
					logger.log(Level.SEVERE, "Server shutdown interrupted", ex);
				}
			};
		});
	}

	public static void main(String[] args) throws InterruptedException {

		UserServer userServer = new UserServer();

		userServer.startServer();
		userServer.blockUntilShutdown(); //Server will keep running without termination
	}
}
