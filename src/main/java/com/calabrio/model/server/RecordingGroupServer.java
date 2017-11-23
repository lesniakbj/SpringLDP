package com.calabrio.model.server;

import com.calabrio.model.telephony.RecordingGroup;

import javax.persistence.*;

@Entity
@Table(name = "RecordingGroupServer")
@PrimaryKeyJoinColumn(name = "serverFk", referencedColumnName = "id")
public class RecordingGroupServer extends Server {
    @OneToOne
    @JoinColumn(name = "recordingGroupId")
    private RecordingGroup recordingGroup;

    @Column(name = "priority")
    private Integer priority;

    public RecordingGroup getRecordingGroup() {
        return recordingGroup;
    }

    public void setRecordingGroup(RecordingGroup recordingGroup) {
        this.recordingGroup = recordingGroup;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
