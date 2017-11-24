package com.calabrio.model.tenant;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "DatabaseInstance")
public class DatabaseInstance {
    @Id
    @Column(name = "databaseInstanceId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Integer databaseInstanceId;

    @Column(name = "hostName", length = 200, nullable = false)
    @Expose
    private String hostName;

    @Column(name = "instanceName", length = 16)
    @Expose
    private String instanceName;

    @Column(name = "port")
    @Expose
    private Integer port;

    @Column(name = "master", nullable = false)
    @Expose
    private Integer master;

    @Column(name = "username", length = 100)
    @Expose
    private String username;

    @Column(name = "password", length = 200)
    @Expose
    private String password;

    public Integer getDatabaseInstanceId() {
        return databaseInstanceId;
    }

    public void setDatabaseInstanceId(Integer databaseInstanceId) {
        this.databaseInstanceId = databaseInstanceId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getMaster() {
        return master;
    }

    public void setMaster(Integer master) {
        this.master = master;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
