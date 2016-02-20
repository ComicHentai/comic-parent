package com.comichentai.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.comichentai.security.AESLocker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hope6537 on 16/2/5.
 */
public class ComicHentaiDataSource extends DruidDataSource {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        logger.debug("decrypted [" + password + "]");
        password = AESLocker.decrypt(password);
        super.setPassword(password);
    }
}
