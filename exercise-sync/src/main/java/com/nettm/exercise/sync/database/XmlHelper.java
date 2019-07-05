package com.nettm.exercise.sync.database;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;

@UtilityClass
public class XmlHelper {

    public static <T> void writeXml(@NonNull T obj, @NonNull File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(obj, file);
        } catch (JAXBException e) {
            throw new MetadataConfigException(e);
        }
    }

    public static <T> T parseXml(@NonNull File file, Class<T> clz) {
        T obj = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            obj = (T)jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new MetadataConfigException(e);
        }

        return obj;
    }

    public static <T> T parseXml(@NonNull InputStream in, Class<T> clz) {
        T obj = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            obj = (T)jaxbUnmarshaller.unmarshal(in);
        } catch (JAXBException e) {
            throw new MetadataConfigException(e);
        }

        return obj;
    }

}
