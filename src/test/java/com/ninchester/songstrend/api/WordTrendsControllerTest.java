package com.ninchester.songstrend.api;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninchester.songstrend.SongsTrendApplication;
import com.ninchester.songstrend.db.WordTrendsRepository;
import com.ninchester.songstrend.db.entities.Word;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = SongsTrendApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class WordTrendsControllerTest {

  @Autowired private TestRestTemplate restTemplate;

  @Autowired
  WordTrendsRepository repository;

  @AfterEach
  public void cleanup() {
    repository.deleteAll();
  }

  @Test
  public void given_word_trends_when_get_word_in_period_then_successful()
      throws JsonProcessingException {
    prepareTestDataWithTwoWords();

    final ResponseEntity<String> response =
        restTemplate.getForEntity("/word?word=love&from=20160101&to=20160501", String.class);

    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(response.getBody());

    assertEquals("Word is not as expected", "love", node.path("word").textValue());
    assertTrue("Time series number is not as expected", node.path("timeSeries").size() == 2);
  }

  private void prepareTestDataWithTwoWords() {
    Word trends1 = new Word();
    trends1.setId(1L);
    trends1.setWord("love");
    trends1.setWordCount(13);
    trends1.setTrendDate("20160101");

    Word trends2 = new Word();
    trends2.setId(2L);
    trends2.setWord("love");
    trends2.setWordCount(43);
    trends2.setTrendDate("20160501");

    repository.save(trends1);
    repository.save(trends2);
  }
}
