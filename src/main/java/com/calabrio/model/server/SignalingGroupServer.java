package com.calabrio.model.server;

import com.calabrio.model.telephony.SignalingGroup;

import javax.persistence.*;

@Entity
@Table(name = "SignalingGroupServer")
@PrimaryKeyJoinColumn(name = "serverFk", referencedColumnName = "id")
public class SignalingGroupServer extends Server {
    @OneToOne
    @JoinColumn(name = "signalingGroupId")
    private SignalingGroup signalingGroup;

    @Column(name = "signalingAssociation")
    private Integer signalingAssociation;

    @Column(name = "priority")
    private Integer priority;

    public SignalingGroup getSignalingGroup() {
        return signalingGroup;
    }

    public void setSignalingGroup(SignalingGroup signalingGroup) {
        this.signalingGroup = signalingGroup;
    }

    public Integer getSignalingAssociation() {
        return signalingAssociation;
    }

    public void setSignalingAssociation(Integer signalingAssociation) {
        this.signalingAssociation = signalingAssociation;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
