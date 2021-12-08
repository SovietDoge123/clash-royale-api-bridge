package com.example.entities

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "card")
class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @NotNull
    @Column(name = "name")
    String name

    @ManyToOne
    @JoinColumn(name = "card_ref_id")
    CardRef cardRef

    @ManyToOne
    @JoinColumn(name = "player_id")
    Player player
}
