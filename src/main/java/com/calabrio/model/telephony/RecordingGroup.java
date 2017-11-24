package com.calabrio.model.telephony;

import com.calabrio.model.server.RecordingGroupServer;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RecordingGroup")
public class RecordingGroup {
    @Id
    @Column(name = "id", nullable =  false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Integer id;

    @Column(name =  "name", nullable = false)
    @Expose
    private String name;

    @ManyToOne
    @JoinColumn(name = "signalingGroupId")
    @Expose(serialize = false)
    private SignalingGroup signalingGroup;

    @OneToMany(mappedBy = "recordingGroup", fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SELECT)
    @Expose
    private List<RecordingGroupServer> servers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SignalingGroup getSignalingGroup() {
        return signalingGroup;
    }

    public void setSignalingGroup(SignalingGroup signalingGroup) {
        this.signalingGroup = signalingGroup;
    }

    public List<RecordingGroupServer> getServers() {
        return servers;
    }

    public void setServers(List<RecordingGroupServer> servers) {
        this.servers = servers;
    }
}
