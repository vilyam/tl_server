package com.c17.yyh.db.dao.impl.xml;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.server.EntitiesList;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XMLConfigLoader {

    @Autowired
    ServerConfig serverConfig;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<String, String> paths = new HashMap<>();
    private String configRootPath;

    @PostConstruct
    public void init() {
        configRootPath = serverConfig.getConfigRootPath();
        for (Object fileObject : FileUtils.listFiles(new File(configRootPath), ArrayUtils.toArray("xml"), true)) {
            File file = (File) fileObject;
            paths.put(file.getName(), file.getAbsolutePath());
        }
    }

    public <V, K extends EntitiesList<V>> void saveData(List<V> list, Class<K> clazz) {
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Marshaller u = jc.createMarshaller();
            File file = new File(paths.get(clazz.getSimpleName().toLowerCase() + ".xml"));
            K itemsToSave = clazz.newInstance();
            itemsToSave.setList(list);
            u.marshal(itemsToSave, file);
        } catch (IllegalAccessException | InstantiationException | JAXBException e) {
            logger.error("Error saving data " + e.getMessage());
            throw new IllegalStateException();
        }
    }

    public <V, K extends EntitiesList<V>> K loadEntityData(Class<K> clazz) {
        String filePath = paths.get(clazz.getSimpleName().toLowerCase() + ".xml");
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller u = jc.createUnmarshaller();
            File file = new File(filePath);
            K items = (K) u.unmarshal(file);
            return items;
        } catch (JAXBException e) {
            logger.error("Error loading data from file {}. Path maybe incorrect", filePath, e);
            throw new IllegalStateException();
        }
    }

    public <V, K extends EntitiesList<V>> List<V> loadData(Class<K> clazz) {
        return loadEntityData(clazz).getList();
    }

}
