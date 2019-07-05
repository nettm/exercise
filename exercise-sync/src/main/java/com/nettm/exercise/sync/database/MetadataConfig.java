package com.nettm.exercise.sync.database;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MetadataConfig {

    private String config;

    private Metadata metadata;

    public MetadataConfig(String config) {
        this.config = config;
    }

    public List<Column> getSourceColumns() {
        List<Column> columns = getTable().getColumnList();
        return columns.stream().filter(s -> Objects.nonNull(s.getSource())).collect(Collectors.toList());
    }

    public List<Column> getTargetColumns() {
        List<Column> columns = getTable().getColumnList();
        return columns.stream().filter(Column::getIsSync).collect(Collectors.toList());
    }

    public boolean isSync(Column column) {
        return column.getIsSync();
    }

    public Table getTable() {
        return getMetadata().getTableList().get(0);
    }

    private Metadata init() {
        Metadata metadata = null;

        try (InputStream in = Metadata.class.getResourceAsStream("/" + config)) {
            if (Objects.nonNull(in)) {
                metadata = XmlHelper.parseXml(in, Metadata.class);
            } else {
                URL url = MetadataConfig.class.getClassLoader().getResource(".");
                if (Objects.nonNull(url)) {
                    File file = new File(url.getPath() + config);
                    if (file.exists()) {
                        metadata = XmlHelper.parseXml(file, Metadata.class);
                    }
                }
            }
        } catch (IOException e) {
            throw new MetadataConfigException("解析配置文件失败", e);
        }

        check(metadata);
        complete(metadata);
        return metadata;
    }

    private void complete(Metadata metadata) {
        Table table = metadata.getTableList().get(0);
        List<Column> columns = table.getColumnList();
        columns.forEach(s -> {
            if (Objects.isNull(s.getTarget())) {
                s.setTarget(s.getSource());
            }
            if (Objects.isNull(s.getTargetType())) {
                s.setTargetType(s.getSourcetype());
            }
        });
    }

    private void check(Metadata metadata) {
        if (Objects.isNull(metadata)) {
            throw new MetadataConfigException("配置文件不存在");
        }

        for (Table table : metadata.getTableList()) {
            if (Objects.isNull(table)) {
                throw new MetadataConfigException("表不能为空");
            }
            if (StringUtils.isBlank(table.getSource())) {
                throw new MetadataConfigException("数据库的source不能为空");
            }
            if (StringUtils.isBlank(table.getTarget())) {
                throw new MetadataConfigException("数据库的target不能为空");
            }
        }

    }

    private Metadata getMetadata() {
        if (Objects.isNull(metadata)) {
            metadata = init();
        }
        return metadata;
    }

}
