/**
*	TCP Client Program
*	Connects to a TCP Server
*	Receives a line of input from the keyboard and sends it to the server
*	Receives a response from the server and displays it.
*
*	@author: Yoko Yamaguchi
@	version: 2.1
*/

import java.io.*; 
import java.net.*; 
class Green { 

    public static void main(String argv[]) throws Exception 
    { 
        String sentence; 
        String modifiedSentence; 
        String firstContact = "HELLO";

		Socket clientSocket = null;
		BufferedReader inFromUser = null;
		DataInputStream inFromServer = null;
		DataOutputStream outToServer = null;

		
		try
		{
			clientSocket = new Socket("10.49.139.26", 6789);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new DataInputStream(clientSocket.getInputStream());
			inFromUser = new BufferedReader(new InputStreamReader(System.in));
		}
		catch (IOException Ioe)
		{
			System.out.println(Ioe);
		}
		catch(Exception e)
		{
			System.out.println("Failed to open socket connection");
			System.exit(0);
		}

		while (true)
		{
			String firstRawReceive = inFromServer.readLine();
       		int firstReceive = Integer.parseInt(firstRawReceive);
       		boolean initiate = false;
			int secondReceive = 0;
       		if (firstReceive == 100)
       		{		            
		        System.out.println("100 Received");
		        System.out.println("Waiting for other person to connect...");
		        initiate = true;
				String secondR = inFromServer.readLine();
				secondReceive = Integer.parseInt(secondR);
		            
		    }
		    if (firstReceive == 200 || secondReceive == 200)
		    {
		        System.out.println("200 Received");
		        System.out.println("Connected.");
		        initiate = false;
		    }
		    if (initiate) {
		    	System.out.print("Green>");
		    	outToServer.writeBytes(inFromUser.readLine() + "\n");
		    }
			while (true){
				/* Receive from other user */
				System.out.print('\n');
				sentence = inFromServer.readLine();
				if (sentence.equals("300")) break;
				System.out.println("Yellow>" + sentence);
				/* Write out to the server */
				System.out.print("Green>");
				outToServer.writeBytes(inFromUser.readLine() + '\n');
			}
				outToServer.close();
				inFromServer.close();
				clientSocket.close();
				System.out.println("Chat Disconnected");
				System.exit(0);

		}
	}
} 