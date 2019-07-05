package com.nettm.exercise.sync.database;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement(name = "metadata")
@XmlAccessorType(XmlAccessType.FIELD)
public class Metadata implements Serializable {

    @XmlElement(name = "table")
    private List<Table> tableList = new ArrayList<>();

    public void addTable(Table table) {
        tableList.add(table);
    }

}
