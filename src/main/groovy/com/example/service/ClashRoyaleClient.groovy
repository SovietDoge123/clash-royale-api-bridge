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

    //Comment to commit

    @Autowired
    CardRepository cardRepository

    final String authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImU0ZmUyNTcyLTg4MmQtNDA2Mi1hZmY2LWExMzYxNGIwOTFhZSIsImlhdCI6MTYzODkzMTA2Niwic3ViIjoiZGV2ZWxvcGVyL2M4NzNjZmRhLWE2MjQtZTU2ZC03MjBhLWQwNGFhMTk5MDIxMCIsInNjb3BlcyI6WyJyb3lhbGUiXSwibGltaXRzIjpbeyJ0aWVyIjoiZGV2ZWxvcGVyL3NpbHZlciIsInR5cGUiOiJ0aHJvdHRsaW5nIn0seyJjaWRycyI6WyIxOTIuMTQ5LjI0NS4xNjciXSwidHlwZSI6ImNsaWVudCJ9XX0.uaxqMR4SFck7JTxTCOHro3Jp_qvAGfu6DgShifFCck_j1svoqrWgSuXc81jCx8m9EUOqfLJSaL9jN_s2uo0glw"
    final HttpHeaders headers = new HttpHeaders()

    void getTopClans() {
        RestTemplate restTemplate = new RestTemplate()
        String topClanUrl = 'https://api.clashroyale.com/v1/clans?minScore=64000&limit=50'
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

    void getCards() {
        RestTemplate restTemplate = new RestTemplate()
        String topClanUrl = 'https://api.clashroyale.com/v1/cards'
        headers.add("Authorization", "Bearer ${authToken}")
        ResponseEntity<CardItems> response = restTemplate.exchange(topClanUrl, HttpMethod.GET, new HttpEntity<Object>(headers), CardItems)
        cardRepository.saveAll(response.body.items)
        getTopClans()
    }
}
