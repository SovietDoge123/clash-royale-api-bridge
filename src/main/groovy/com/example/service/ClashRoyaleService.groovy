package com.example.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service

@Service
class ClashRoyaleService implements CommandLineRunner {

    @Autowired
    ClashRoyaleClient clashRoyaleClient

    @Override
    void run(String...args) throws Exception {
        clashRoyaleClient.getTopClans()
    }
}
