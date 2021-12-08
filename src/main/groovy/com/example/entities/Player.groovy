package com.example.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "player")
class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @NotNull
    @Column(name = "crowns")
    Integer crowns

    @OneToMany
    List<Card> cards

    @ManyToOne
    @JoinColumn(name = "match_id")
    Match match


}
