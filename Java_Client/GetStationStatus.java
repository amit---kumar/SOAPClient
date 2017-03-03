import java.io.*;
import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GetStationStatus {

    public static void main(String args[]) {


        try {


      	    String pattern = "enter you input\\d+).*";
      	    Pattern r = Pattern.compile(pattern);
	    String[] SearchRequestId = new String[20];

            String pattern2 = "enter you input\\d+).*";
            Pattern r2 = Pattern.compile(pattern2);
	    String[] ResultsStartIndex = new String[20];

            String pattern3 = "enter you input\\d+).*";
            Pattern r3 = Pattern.compile(pattern3);
	    String[] MaxResultsToReturn = new String[20];

            String pattern4 = "enter you input\\w+)<.*";
            Pattern r4 = Pattern.compile(pattern4);
	    String[] OperatorUri = new String[20];


            String request = "enter you input";
            Pattern r5 = Pattern.compile(request);
	    int i=0;	

    	    String line = null;	
	    FileReader fileReader = new FileReader("enter you input");
            BufferedReader bufferedReader =  new BufferedReader(fileReader);

	    File file =new File("enter you input");
	    if(!file.exists()){
    	   	file.createNewFile();
    	    }
	
    	    FileWriter fileWritter = new FileWriter(file.getName(),true);
    	    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	    	

	   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	   Date date = new Date();
	   String Request_time=dateFormat.format(date);


            while((line = bufferedReader.readLine()) != null) {
            Matcher m = r5.matcher(line);
            if (m.find( ))
             i++;

      	    // Now create matcher object.
	    m = r.matcher(line);
	    if (m.find( ))
	      {
		SearchRequestId[i]=m.group(1);
		System.out.println("Found value: " + m.group(1) );
		
              }
            m = r2.matcher(line);
            if (m.find( ))
              {
                ResultsStartIndex[i]=m.group(1);
		System.out.println("Found value: " + m.group(1) );
              }
            m = r3.matcher(line);
            if (m.find( ))
              {
                MaxResultsToReturn[i]=m.group(1);
		System.out.println("Found value: " + m.group(1) );
              }
            m = r4.matcher(line);
            if (m.find( ))
              {
                OperatorUri[i]=m.group(1);
		System.out.println("Found value: " + m.group(1) );
              }
              
            }//while close   

            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            String url = "enter you input";

	  bufferWritter.write("Time of the Request  |  Response File Name   ");
	  bufferWritter.write("\n");
	  bufferWritter.write("---------------------------------------------");
	  bufferWritter.write("\n");

		
	
          for(int j=1;j<=i;j++)
          {

          String time_in_file_name=Request_time.replace(' ', '_');
          time_in_file_name=time_in_file_name.replace('/', '_');
          String Response_file="enter you input"+time_in_file_name;


	  SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(SearchRequestId[j],ResultsStartIndex[j],MaxResultsToReturn[j],OperatorUri[j]), url);

          // Process the SOAP Response
          printSOAPResponse(soapResponse,Response_file);

	  String Request_file_entry=Request_time+"  |  "+Response_file; 	
	  bufferWritter.write(Request_file_entry);
	  bufferWritter.write("\n");	
	  }
       
         soapConnection.close(); 
	 bufferWritter.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest(String SearchRequestId,String ResultsStartIndex,String MaxResultsToReturn,String OperatorUri) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();


	String serverURI = "urn:schemas.nema.org:evse:dir:v1.0";
	String serverURI3 = "urn:schemas.nema.org:evse:cmn:v1.0";
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        //envelope.addNamespaceDeclaration("example", serverURI);
	envelope.addNamespaceDeclaration("urn", serverURI);
	envelope.addNamespaceDeclaration("urn1", serverURI3);
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("enter you input", "urn");

        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("enter you input", "urn");

        SOAPElement soapBodyElem11 = soapBodyElem1.addChildElement("enter you input", "urn");
        soapBodyElem11.addTextNode(SearchRequestId);
        SOAPElement soapBodyElem12 = soapBodyElem1.addChildElement("enter you input", "urn");
        soapBodyElem12.addTextNode(ResultsStartIndex);
        SOAPElement soapBodyElem13 = soapBodyElem1.addChildElement("enter you input", "urn");
        soapBodyElem13.addTextNode(MaxResultsToReturn);

        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("enter you input", "urn");

        SOAPElement soapBodyElem21 = soapBodyElem2.addChildElement("enter you input", "urn");
        soapBodyElem21.addTextNode(OperatorUri);


        MimeHeaders headers = soapMessage.getMimeHeaders();
//        headers.addHeader("SOAPAction", serverURI  + "   VerifyEmail");

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

    /**
     * Method used to print the SOAP Response
     */
    private static void printSOAPResponse(SOAPMessage soapResponse, String Response_file) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
	String Output_file="enter you input"+Response_file;

	FileWriter fileWriter = new FileWriter(Output_file);

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        StreamResult result = new StreamResult(bufferedWriter);
        transformer.transform(sourceContent, result);

       
    }

}
