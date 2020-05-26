package com.igeek.tools.db;

import com.igeek.tools.ToolsAppliction;
import com.igeek.tools.db.entity.ShadowSkyAccount;
import com.igeek.tools.db.entity.ShadowSkyCheckInLogEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.Property;
import io.objectbox.query.QueryBuilder;

public class DataManager {

    private static DataManager dataManager;

    public static synchronized DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public ConcurrentHashMap<String, Box> boxMap = new ConcurrentHashMap<>();

    public void init(ToolsAppliction toolsAppliction) {
        initUserEntityBox(toolsAppliction.getBoxStore());
    }

    private void initUserEntityBox(BoxStore boxStore) {
        boxMap.put(ShadowSkyAccount.class.getName(), boxStore.boxFor(ShadowSkyAccount.class));
        boxMap.put(ShadowSkyCheckInLogEntity.class.getName(), boxStore.boxFor(ShadowSkyCheckInLogEntity.class));
    }

    public <T> Box<T> getBox(Class<T> tClass) {
        return boxMap.get(tClass.getName());
    }

    public <T> long put(Class<T> tClass, T t) {
        Box<T> box = getBox(tClass);
        return box.put(t);
    }

    /**
     * 多条件查询
     *
     * @param tClass            查询对象
     * @param propertyObjectMap 查询条件集合 key查询条件，value具体值
     **/
    public <T> List<T> getListEqual(Class<T> tClass, Map<Property, Object> propertyObjectMap, Property orderProperty) {
        if (propertyObjectMap == null) {
            throw new NullPointerException("propertyObjectMap is not null and value is not null.");
        }

        if (propertyObjectMap.isEmpty()) {
            throw new IllegalStateException("propertyObjectMap is not empty and value is not empty.");
        }

        Box box = boxMap.get(tClass.getName());
        if (box != null) {
            QueryBuilder queryBuilder = box.query();
            Iterator<Map.Entry<Property, Object>> iterator = propertyObjectMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Property, Object> entry = iterator.next();
                queryBuilder.equal(entry.getKey(), entry.getValue().toString());
            }
            if (orderProperty != null) {
                queryBuilder.orderDesc(orderProperty);
            }
            return queryBuilder.build().find();
        }
        return new ArrayList<>();
    }

    /**
     * 多条件查询
     *
     * @param tClass            查询对象
     * @param propertyObjectMap 查询条件集合 key查询条件，value具体值
     **/
    public <T> List<T> getListEqual(Class<T> tClass, Map<Property, Object> propertyObjectMap) {
        if (propertyObjectMap == null) {
            throw new NullPointerException("propertyObjectMap is not null and value is not null.");
        }

        if (propertyObjectMap.isEmpty()) {
            throw new IllegalStateException("propertyObjectMap is not empty and value is not empty.");
        }

        Box box = boxMap.get(tClass.getName());
        if (box != null) {
            QueryBuilder queryBuilder = box.query();
            Iterator<Map.Entry<Property, Object>> iterator = propertyObjectMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Property, Object> entry = iterator.next();
                queryBuilder.equal(entry.getKey(), entry.getValue().toString());
            }

            return queryBuilder.build().find();
        }
        return new ArrayList<>();
    }
}
