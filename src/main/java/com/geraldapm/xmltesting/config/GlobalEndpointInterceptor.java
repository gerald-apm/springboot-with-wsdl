package com.geraldapm.xmltesting.config;

import com.geraldapm.xmltesting.util.MessageModifier;
import id.my.gpm.service.xmltesting.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.interceptor.EndpointInterceptorAdapter;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.soap.*;
import org.springframework.ws.context.MessageContext;

@Component
public class GlobalEndpointInterceptor extends EndpointInterceptorAdapter {

    private static final String SOAPENV_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final String SOAPENC_NAMESPACE = "http://schemas.xmlsoap.org/soap/encoding/";
    private static final String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    private static final String SOAPENV_PREFIX = "soap";
    private static final String SOAPENC_PREFIX = "soapenc";
    private static final String XSI_PREFIX = "xsi";
    private static final String XSD_PREFIX = "xsd";

    private final Marshaller marshaller;

    public GlobalEndpointInterceptor() throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
        this.marshaller = jaxbContext.createMarshaller();
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object o) throws Exception {
        SaajSoapMessage soapResponse = (SaajSoapMessage) messageContext.getResponse();
        alterSoapEnvelope(soapResponse);
        //removeNamespace(soapResponse);

        return super.handleResponse(messageContext, o);
    }

    @Override
    public boolean handleFault(org.springframework.ws.context.MessageContext messageContext, Object o) throws Exception {
        SaajSoapMessage soapResponse = (SaajSoapMessage) messageContext.getResponse();
        alterSoapEnvelope(soapResponse);
        removeNamespace(soapResponse);
        return super.handleResponse(messageContext, o);
    }

    private void removeNamespace(SaajSoapMessage soapResponse) {
        try {
            SOAPMessage soapMessage = soapResponse.getSaajMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPHeader header = soapMessage.getSOAPHeader();
            SOAPBody body = soapMessage.getSOAPBody();
            SOAPFault fault = body.getFault();
            body.getFirstChild().setPrefix(null);
            // Do something here
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    private void alterSoapEnvelope(SaajSoapMessage soapResponse) {
        try {
            SOAPMessage soapMessage = soapResponse.getSaajMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPHeader header = soapMessage.getSOAPHeader();
            SOAPBody body = soapMessage.getSOAPBody();
            SOAPFault fault = body.getFault();
            envelope.removeNamespaceDeclaration(envelope.getPrefix());


            String[][] prefixArray = {{SOAPENV_PREFIX, SOAPENV_NAMESPACE},{SOAPENC_PREFIX, SOAPENC_NAMESPACE}
                    ,{XSD_PREFIX, XSD_NAMESPACE},{XSI_PREFIX, XSI_NAMESPACE}};
            // add namespaces
            for (String[] prefix : prefixArray) {envelope.addNamespaceDeclaration(prefix[0], prefix[1]);

            }
            envelope.setPrefix(SOAPENV_PREFIX);
            header.setPrefix(SOAPENV_PREFIX);
            body.setPrefix(SOAPENV_PREFIX);
            if (fault != null) {
                fault.setPrefix(SOAPENV_PREFIX);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

