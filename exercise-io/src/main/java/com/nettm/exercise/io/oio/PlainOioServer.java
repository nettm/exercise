package com.nettm.exercise.io.oio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class PlainOioServer {
	
	private static final int port = 7777;
	
	public static void main(String[] args) throws IOException {
		PlainOioServer demo = new PlainOioServer();
		demo.server();
	}

	public void server() throws IOException {
		final ServerSocket socket = new ServerSocket(port);
		for(;;) {
			final Socket clientSocket = socket.accept();
			System.out.println("Accepted connection from " + clientSocket);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try(OutputStream out = clientSocket.getOutputStream()) {
						out.write("Hi!\r\n".getBytes(StandardCharsets.UTF_8));
						out.flush();
						clientSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
