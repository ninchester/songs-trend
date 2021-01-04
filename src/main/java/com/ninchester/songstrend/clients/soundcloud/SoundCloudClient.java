package com.ninchester.songstrend.clients.soundcloud;

import com.ninchester.songstrend.clients.soundcloud.domain.Track;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class SoundCloudClient {
  private static final Logger LOG = LoggerFactory.getLogger(SoundCloudClient.class);
    private static final String TRACKS_URI = "https://api.soundcloud.com/tracks?client_id=";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private VaultTemplate vaultTemplate;

    public Track[] getTracks() {
        LOG.info("Getting tracks from SoundCloud");

        Track[] tracks = restTemplate.getForObject(TRACKS_URI.concat(getApiKeyFromVault()), Track[].class);

        LOG.info("Fetched [{}] tracks from Soundcloud", (tracks != null) ? tracks.length : 0);

        return tracks;
    }

    private String getApiKeyFromVault() {
        Map<String, Object> vaultResponse = vaultTemplate.read("secret/data/songs-trend").getData();
        return (String) ( (LinkedHashMap) vaultResponse.get("data")).get("cloudApiKey");
    }
}
