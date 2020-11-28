package com.nettm.exercise.base.utils;

import lombok.experimental.UtilityClass;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

@UtilityClass
public class XmlConfigUtil {

    public <T> T readXmlConfig(InputStream input, Class<T> cls) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(cls);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (T) jaxbUnmarshaller.unmarshal(input);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }
}
