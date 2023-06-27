package practicasoap;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.Provider.Service;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.junit.Test;

public class SoapCalculator { 

	
	@Test
	public void main() {
		
		System.out.println("Add: " + calculadora(5, 15, "Add"));
		assertEquals(15 + 5, calculadora(5, 15, "Add"));
		
		System.out.println("Div: " + calculadora(15, 5, "Divide"));
		assertEquals(15 / 5, calculadora(15, 5, "Divide"));
		
		System.out.println("Mul: " + calculadora(5, 15, "Multiply"));
		assertEquals(5 * 15, calculadora(5, 15, "Multiply"));
		
		System.out.println("Sub: " + calculadora(15, 1, "Subtract"));
		assertEquals(15 - 1, calculadora(15, 1, "Subtract"));

	}

	public static int calculadora(int intA, int intB, String op) {
		
		String result = "";
		try {
			String endpoint = "http://dneonline.com/calculator.asmx";
			//SOAPConnectionFactory soapCOnnectionFactory = SOAPConnectionFactory.newInstance();
			//Creamos la conexion
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			//Creamos el mensaje SOAP
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();
			//Crear el cuerpo del mensaje SOAP
			SOAPPart soapPart = soapMessage.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			//SOAPMessage soapResponse = soapConnection.call(soapMessage, endpoint);
			SOAPBody soapBody = soapEnvelope.getBody();
			
			
			//Construimos el mensaje a enviar
			//En base a la documentación, se declaran los elementos intA e intB
			SOAPElement operationElement = soapBody.addChildElement(op, "", "http://tempuri.org/");
			SOAPElement intAElement = operationElement.addChildElement("intA");
			intAElement.addTextNode(String.valueOf(intA));
			SOAPElement intBElement = operationElement.addChildElement("intB");
			intBElement.addTextNode(String.valueOf(intB));
			
			
			
			//Enviar el mensaje SOAP y recibir la respuesta
			SOAPMessage soapResponse = soapConnection.call(soapMessage, endpoint);
			//Procesamos la respuesta.
			SOAPBody responseBody = soapResponse.getSOAPBody();
			SOAPElement responseElement = (SOAPElement) responseBody.getElementsByTagName(op+"Response").item(0);
			result = responseElement.getTextContent();		
			soapConnection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Integer.parseInt(result);
	}
	
	
	

}
