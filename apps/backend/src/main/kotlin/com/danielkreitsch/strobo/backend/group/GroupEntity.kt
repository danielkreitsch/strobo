package com.danielkreitsch.strobo.backend.group

import jakarta.persistence.*

@Entity
@Table(name = "device_groups")
class GroupEntity
{
    @Id
    var id: String = ""

    var name: String = ""

    var sortOrder: Int = 0

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "groups_devices")
    @Column(name = "device_id")
    var deviceIds: MutableList<String> = ArrayList()
}
