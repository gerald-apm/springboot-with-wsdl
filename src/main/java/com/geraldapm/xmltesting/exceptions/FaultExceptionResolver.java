package com.geraldapm.xmltesting.exceptions;

import com.geraldapm.xmltesting.util.MessageModifier;
import id.my.gpm.service.xmltesting.Fault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.AbstractEndpointExceptionResolver;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.w3c.dom.DOMException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.transform.Result;
import java.util.Locale;

@Component
public class FaultExceptionResolver extends AbstractEndpointExceptionResolver {
    private final Marshaller marshaller;

    @Autowired
    private MessageModifier messageModifier;

    public FaultExceptionResolver() throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(Fault.class);
        this.marshaller = jaxbContext.createMarshaller();
    }
    @Override
    protected boolean resolveExceptionInternal(MessageContext messageContext, Object endpoint, Exception ex) {
        if (ex instanceof BaseErrorException) {
            final BaseErrorException myfault = (BaseErrorException) ex;

            final Fault faultMessage = new Fault();
            faultMessage.setErrorDescription(myfault.getErrorMapper().getErrorDescription());
            faultMessage.setErrorNumber(myfault.getErrorMapper().getErrorNum());

            SaajSoapMessage soap = (SaajSoapMessage) messageContext.getResponse();
            messageModifier.modifyENVMessagePrefix(soap);

            try {
                SOAPBody body = soap.getSaajMessage().getSOAPBody();
                soap.getSoapBody().addServerOrReceiverFault(myfault.getErrorMapper().getErrorDescription(),
                        Locale.ENGLISH);

                SoapFault springFault = soap.getSoapBody().getFault();
                springFault.addNamespaceDeclaration("m", "https://service.gpm.my.id/xmltesting");
                SOAPFault fault = body.getFault();
                fault.setFaultCode("m:AppFault");
                fault.setFaultString(myfault.getErrorMapper().getErrorNum());

                SoapFaultDetail detail = springFault.addFaultDetail();
                Result result = detail.getResult();
                this.marshaller.marshal(faultMessage, result);
                detail.getDetailEntries().next().addAttribute(new QName("xsi:type"), "core:AppFault");
                detail.getDetailEntries().next().addNamespaceDeclaration("core", "https://service.gpm.my.id/xmltesting");

            } catch (SOAPException | JAXBException e1) {
                System.out.println(e1);
            }
        }
        return true;
    }


}

