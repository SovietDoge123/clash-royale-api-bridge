package com.example.service

import com.example.CardRepository
import com.example.entities.CardItems
import com.example.entities.ClanItems
import com.example.entities.ClanMemberItems
import com.example.entities.Match
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder

@Slf4j
@Service
class ClashRoyaleClient {

    @Autowired
    CardRepository cardRepository

    final String authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImZmZTYyN2FkLWNkMzAtNDYwOC1hZDhjLWJjYzNjODdjZDA4YyIsImlhdCI6MTYzODcyNzU3NCwic3ViIjoiZGV2ZWxvcGVyL2M4NzNjZmRhLWE2MjQtZTU2ZC03MjBhLWQwNGFhMTk5MDIxMCIsInNjb3BlcyI6WyJyb3lhbGUiXSwibGltaXRzIjpbeyJ0aWVyIjoiZGV2ZWxvcGVyL3NpbHZlciIsInR5cGUiOiJ0aHJvdHRsaW5nIn0seyJjaWRycyI6WyI3MS44Ny41MS44OCJdLCJ0eXBlIjoiY2xpZW50In1dfQ.2GiiaspkYTf4bkyQTEQB6y64FarnmDJqmOmST8owvTjtF1ot_mfnAoZFxwn1_YHU0YOUkt9u4XVlsnOZKBcG4w"
    final HttpHeaders headers = new HttpHeaders()

/*    void getCards() {
        RestTemplate restTemplate = new RestTemplate()
        String topClanUrl = 'https://api.clashroyale.com/v1/cards'
        headers.add("Authorization", "Bearer ${authToken}")
        ResponseEntity<CardItems> response = restTemplate.exchange(topClanUrl, HttpMethod.GET, new HttpEntity<Object>(headers), CardItems)
        cardRepository.saveAll(response.body.items)
        getTopClans()
    }*/

    void getTopClans() {
        RestTemplate restTemplate = new RestTemplate()
        String topClanUrl = 'https://api.clashroyale.com/v1/clans?minScore=72229&limit=50'
        headers.add("Authorization", "Bearer ${authToken}")
        ResponseEntity<ClanItems> response = restTemplate.exchange(topClanUrl, HttpMethod.GET, new HttpEntity<Object>(headers), ClanItems)

        response.body.items.each {
            getTopClanMembers(it.tag)
        }
    }

    void getTopClanMembers(String clanTag) {
        RestTemplate restTemplate = new RestTemplate()
        Map<String, String> uriVariables = new HashMap<>()
        uriVariables.put("clanTag", clanTag)

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.clashroyale.com")
                .path("v1/clans")
                .pathSegment("{clanTag}")
                .pathSegment("members")
                .buildAndExpand(uriVariables)
                .encode()

        ResponseEntity<ClanMemberItems> response = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, new HttpEntity<Object>(headers), ClanMemberItems)

        response.body.items.each {
            getMatchesForClanMember(it.tag)
        }
    }

    void getMatchesForClanMember(String playerTag) {
        RestTemplate restTemplate = new RestTemplate()
        Map<String, String> uriVariables = new HashMap<>()
        uriVariables.put("playerTag", playerTag)

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.clashroyale.com")
                .path("v1/players")
                .pathSegment("{playerTag}")
                .pathSegment("battlelog")
                .buildAndExpand(uriVariables)
                .encode()

        ResponseEntity<Match[]> response = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, new HttpEntity<Object>(headers), Match[])

        response.body.each {
            log.info(it.type)
        }
    }
}
