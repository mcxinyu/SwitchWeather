package io.github.mcxinyu.switchweather.database.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import io.github.mcxinyu.switchweather.database.dao.CityBean;

import io.github.mcxinyu.switchweather.database.dao.CityBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cityBeanDaoConfig;

    private final CityBeanDao cityBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cityBeanDaoConfig = daoConfigMap.get(CityBeanDao.class).clone();
        cityBeanDaoConfig.initIdentityScope(type);

        cityBeanDao = new CityBeanDao(cityBeanDaoConfig, this);

        registerDao(CityBean.class, cityBeanDao);
    }

    public void clear() {
        cityBeanDaoConfig.clearIdentityScope();
    }

    public CityBeanDao getCityBeanDao() {
        return cityBeanDao;
    }

}
