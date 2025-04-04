package com.db.iPayments.service;

import com.db.iPayments.model.Payment;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringReader;
import java.io.StringWriter;


@Service
public class XmlConverterService {
    public String convertToXml(Payment payment) {
        try {
            JAXBContext context = JAXBContext.newInstance(Payment.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(payment, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert to XML", e);
        }
    }

    public Payment convertFromXml(String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(Payment.class);
            return (Payment) context.createUnmarshaller().unmarshal(new StringReader(xml));
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert from XML", e);
        }
    }
}
