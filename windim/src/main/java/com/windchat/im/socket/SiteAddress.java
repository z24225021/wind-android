package com.windchat.im.socket;


import com.windchat.im.bean.Site;

import org.apache.commons.lang3.StringUtils;

public class SiteAddress {
    public static String TAG = SiteAddress.class.getSimpleName();

    private String host;
    private int port;
    private ConnectionConfig config;

    public SiteAddress(ConnectionConfig config) {
        this.host = config.getHost();
        this.port = config.getPort();
        this.config = config;
    }

    // 兼容旧代码
    public SiteAddress(String address) {
        String[] ret = address.split("[:_]");
        int retLength = ret.length;
        if (retLength > 0) {
            if (retLength == 2) {
                this.host = ret[0];
                this.port = Integer.valueOf(ret[1]);
            } else {
                this.host = StringUtils.join(ret, ".", 0, retLength - 1).toString();
                this.port = Integer.valueOf(ret[retLength - 1]);
            }
        }
    }

    public SiteAddress(Site site) {
        this.config = ConnectionConfig.getConnectionCfg(site);
        this.host = this.config.getHost();
        this.port = this.config.getPort();
    }

    public SiteAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getFullUrl() {
        return this.host + ":" + this.port;
    }

    @Override
    public String toString() {
        return this.getFullUrl();
    }

    // 兼容老代码
    public ConnectionConfig toConnectionConfig() {

        if (null != this.config) {
            return this.config;
        } else {
            ConnectionConfig tmpConfig = new ConnectionConfig();
            tmpConfig.setHost(this.host);
            tmpConfig.setPort(this.port);
            return tmpConfig;
        }
    }

    public String getSiteDBAddress() {
        if (this.host != null) {
            String dbHost = this.host.replace(".", "-");
            return dbHost + "-" + this.port;
        }
        return null;
    }

}