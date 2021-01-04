package com.ninchester.songstrend.clients.soundcloud;

import com.ninchester.songstrend.clients.soundcloud.domain.Track;
import com.ninchester.songstrend.configuration.RestTemplateConfig;
import com.ninchester.songstrend.security.VaultEnvironmentConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RestTemplateConfig.class, SoundCloudClient.class, VaultEnvironmentConfig.class})
public class SoundCloudClientTest {

  private static final String TRACKS_URI =
      "https://api.soundcloud.com/tracks?client_id=";

  @Autowired private SoundCloudClient soundCloudClient;

  @Autowired private RestTemplate restTemplate;

  @Autowired private VaultTemplate vaultTemplate;

  private MockRestServiceServer mockServer;

  @BeforeEach
  public void init() {
    mockServer = MockRestServiceServer.createServer(restTemplate);
  }

  private SoundCloudClientTest() {}

  @Test
  public void given_tracks_exist_when_getting_tracks_then_tracks_returned()
      throws URISyntaxException {

    mockServer
        .expect(requestTo(new URI(TRACKS_URI.concat(getApiKeyFromVault()))))
        .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
        .andRespond(
            MockRestResponseCreators.withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ClassPathResource("testData/tracks.json")));

    Track[] returnedTracks = soundCloudClient.getTracks();

    mockServer.verify();

    assertTrue("Length of the actual tracks is not as expected", returnedTracks.length == 48);
  }

  private String getApiKeyFromVault() {
    Map<String, Object> vaultResponse = vaultTemplate.read("secret/data/songs-trend").getData();
    return (String) ( (LinkedHashMap) vaultResponse.get("data")).get("cloudApiKey");
  }
}
