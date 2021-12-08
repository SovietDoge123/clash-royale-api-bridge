package com.example.entities

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Match")
class Player {

    Integer crowns
    List<Card> cards

}
