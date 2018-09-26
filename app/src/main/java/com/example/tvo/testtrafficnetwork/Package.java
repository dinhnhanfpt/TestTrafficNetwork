package com.example.tvo.testtrafficnetwork;

import java.io.Serializable;

/**
 * Created by Robert Zagórski on 2016-12-14.
 */

public class Package implements Serializable{
    private String name;
    private String version;
    private String packageName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
