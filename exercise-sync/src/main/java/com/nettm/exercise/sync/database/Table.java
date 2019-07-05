package com.nettm.exercise.sync.database;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@Data
@XmlRootElement(name = "table")
@XmlAccessorType(XmlAccessType.FIELD)
public class Table implements Serializable {

    private static final long serialVersionUID = 1205947706591685521L;
    
    @XmlAttribute
    private String source;

    @XmlAttribute
    private String target;

    @XmlElement(name = "column")
    private List<Column> columnList;

}
