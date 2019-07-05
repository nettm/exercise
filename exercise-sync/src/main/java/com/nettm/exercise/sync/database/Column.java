package com.nettm.exercise.sync.database;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@XmlRootElement(name = "column")
@XmlAccessorType(XmlAccessType.FIELD)
public class Column implements Serializable {

    private static final long serialVersionUID = 1881081684201643655L;

    @XmlAttribute
    private String source;

    @XmlAttribute
    private String sourcetype;

    @XmlAttribute
    private String target;

    @XmlAttribute
    private String targetType;

    @XmlAttribute
    private String defaultValue;

    @XmlValue
    private Boolean isSync;

    @XmlTransient
    private String value;

}
