package com.example.entities

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Player")

class Match {

    String type
    List<Player> team
    List<Player> opponent

}
