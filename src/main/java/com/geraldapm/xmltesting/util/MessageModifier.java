package com.geraldapm.xmltesting.util;

import org.springframework.stereotype.Component;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.w3c.dom.DOMException;

import javax.xml.soap.SOAPException;

@Component
public class MessageModifier {
    private static final String SOAPENV_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final String SOAPENC_NAMESPACE = "http://schemas.xmlsoap.org/soap/encoding/";
    private static final String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    private static final String SOAPENV_PREFIX = "soap";
    private static final String SOAPENC_PREFIX = "soapenc";
    private static final String XSI_PREFIX = "xsi";
    private static final String XSD_PREFIX = "xsd";

    public SaajSoapMessage modifyENVMessagePrefix(SaajSoapMessage message) {
        try {
            message.getSaajMessage().getSOAPPart().getEnvelope().removeNamespaceDeclaration("SOAP-ENV");

            String[][] prefixArray = {{SOAPENV_PREFIX, SOAPENV_NAMESPACE},{SOAPENC_PREFIX, SOAPENC_NAMESPACE}
                    ,{XSD_PREFIX, XSD_NAMESPACE},{XSI_PREFIX, XSI_NAMESPACE}};
            // add namespaces
            for (String[] prefix : prefixArray) { message.getSaajMessage().getSOAPPart()
                    .getEnvelope().addNamespaceDeclaration(prefix[0],prefix[1]);
            }

        } catch (DOMException | SOAPException e1) {
            e1.printStackTrace();
        }
        return message;
    }
}
