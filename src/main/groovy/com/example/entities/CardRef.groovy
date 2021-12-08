package com.example.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "card_ref")
class CardRef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @NotNull
    @Column(name = "name")
    String name

    @OneToMany
    List<Card> cards
}
