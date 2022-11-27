package com.shopping.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import com.shopping.service.OderServiceImp;
import com.shopping.service.UserServiceImpl;

public class OrderServer {
	
	final static int PORT = 5002;
	
	private static final Logger logger = Logger.getLogger(OrderServer.class.getName());
	
	private Server server;

	public void startServer() throws SQLException {

		try {
			
			server = ServerBuilder
			        .forPort(PORT)
			        .addService(new OderServiceImp())
			        .build();
		
			server.start();
			
			logger.info( "Order server started on port: "+PORT);
			
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
					OrderServer.this.stopServer();
				} catch (InterruptedException ex) {
					logger.log(Level.SEVERE, "Server shutdown interrupted", ex);
				}
			};
		});
	}

	public static void main(String[] args) throws InterruptedException, SQLException {

		OrderServer oderServer = new OrderServer();

		oderServer.startServer();
		oderServer.blockUntilShutdown(); //Server will keep running without termination
	}
}
