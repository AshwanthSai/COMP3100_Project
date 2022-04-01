import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;  
import java.lang.*;

class SaiClient{  

public static void main(String args[]) throws Exception{  

	Socket clientSocket = new Socket("127.0.0.1",50000); //Socket Initialized with IP Address and Port.
	
	BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Buffered Reader Initialized on Socket Input.
	DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

	// PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true); //True, Enable Auto Flush.
	
	// Sending HELO
	outputStream.write(("HELO\n").getBytes());
	outputStream.flush();
	//Reply OK
	System.out.println("Server Response : " + inputStream.readLine()); 

	//Authenticating UserName
	String username = System.getProperty("user.name"); 
	outputStream.write(("AUTH " + username + "\n").getBytes()); 
	outputStream.flush();	
	// System.out.println("Authenticated: " + username);
	System.out.println("Server Response : " + inputStream.readLine());

	//Send REDY to Server
	outputStream.write(("REDY" + "\n").getBytes());
	outputStream.flush();

	//Recieve JOBN in the form, JOBN estRuntime core memory disk
	//String jobN = inputStream.readLine();
	String jobN = inputStream.readLine();
	System.out.println("Ds-sim Response: " + jobN);


	// Parsing JOBN to GETS in the form, All|Type serverType|Capable core memory disk |Avail core memory disk
	String[] jobParsed = jobN.split(" ");
	System.out.println(jobParsed[jobParsed.length - 3] + " " + jobParsed[jobParsed.length - 2] + " "+ jobParsed[jobParsed.length - 1]);
	
	//Sending GETS Request
	outputStream.write(("GETS Capable " + jobParsed[jobParsed.length - 3] + " " + jobParsed[jobParsed.length - 2] + " "+ jobParsed[jobParsed.length - 1] +  "\n").getBytes());
	outputStream.flush();
	
	//Recieving DATA from the server in the form. DATA No Of Lines, Random ID.
	String Data = inputStream.readLine();
	System.out.println("Server Response: " + Data);


	//Parse Number of Lines(N) from Data, Use readLineMethod, within loop.
	String[] dataParsed = extractInt(Data.substring(4));
	int noOfLines = Integer.parseInt(dataParsed[1]);
	System.out.println(noOfLines);
	
	
	//Sending OK to Server, Start Commencing Sending Server Data.
	outputStream.write(("OK" + "\n").getBytes());
	outputStream.flush();
	
	//for(int k = 0; i < parsedData.length ; i++){
	//	System.out.println(parsedData[0]);
	//}

	// Parsing Recieved Data, into a Server List.
	ArrayList <Server> serverList = new ArrayList <Server>();
	for(int i = 0;  i < noOfLines ; i++){
		String serverData = inputStream.readLine();
		String[] parsedData = serverData.split(" ");
		// serverType serverID state curStartTime core memory disk #wJobs #rJobs 
		int serverId = Integer.parseInt(parsedData[1]);
		int core = Integer.parseInt(parsedData[4]);
		int memory = Integer.parseInt(parsedData[5]);
		int disk = Integer.parseInt(parsedData[6]);
		serverList.add(new Server(serverId, core, memory, disk));
	}

	for(Server S : serverList){
		System.out.println(S.core);
	}


	// Creating a Sorted List of Servers on the Basis of Cores.
	Collections.sort(serverList,new Comparator<Server>(){
		public int compare(Server s1,Server s2){
			  return s2.core - s1.core; //Sorts in Decreasing Order
		}
	});

	for(Server S : serverList){
		System.out.println(S.core);
	}

	// Implementing Algorithm


	
	// Scheduling the Job.  






	outputStream.write(("QUIT" + "\n").getBytes());
	outputStream.flush();
	System.out.println("Ds-sim Response: " + inputStream.readLine());
	
	
	outputStream.close();
	inputStream.close();
	clientSocket.close();

	}

	public static String [] extractInt(String jobN){
		String[] arr = jobN.split(" ");
		return arr;
	}



	
}
	
