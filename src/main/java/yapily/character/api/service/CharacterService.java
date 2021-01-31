package yapily.character.api.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yapily.character.api.model.CharacterWrapper;
import yapily.character.api.model.Character;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

@Service
@Component
public class CharacterService {

    @Value("${marvel.url.base}")
    private String BASE_URL;
    @Value("${marvel.api.public.key}")
    private String PUBLIC_KEY;
    @Value("${marvel.api.private.key}")
    private String PRIVATE_KEY;

    private final RestTemplate restTemplate;

    @Autowired
    public CharacterService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    private String buildUrl() throws URISyntaxException {
        String timeStamp = Long.toString(System.currentTimeMillis());
        URI uri = new URIBuilder()
                .addParameter("ts", timeStamp)
                .addParameter("apikey", PUBLIC_KEY)
                .addParameter("hash", DigestUtils.md5Hex(timeStamp + PRIVATE_KEY + PUBLIC_KEY))
                .build();
        return uri.toString();
    }

    @Cacheable(value = "characters")
    public Set<Long> getAllCharacters() throws URISyntaxException {
        Set<Long> characterIDs = new HashSet<>();
        long offset = 0;
        long total;
        do {
            URIBuilder uri = new URIBuilder(BASE_URL + buildUrl())
                    .addParameter("limit", "100")
                    .addParameter("offset", Long.toString(offset));
            CharacterWrapper characterWrapper = restTemplate.getForObject(uri.toString(), CharacterWrapper.class);
            assert characterWrapper != null;
            total = characterWrapper.getData().getTotal();
            offset += characterWrapper.getData().getCount();
            characterWrapper.getData().getResults()
                    .forEach(character -> characterIDs.add(character.getId()));
        } while (offset < total);

        return characterIDs;
    }

    public Character getCharacterById(long characterId) {
        try {
            URIBuilder uri = new URIBuilder(BASE_URL + "/" + characterId + buildUrl());
            CharacterWrapper characterWrapper = restTemplate.getForObject(uri.toString(), CharacterWrapper.class);
            assert characterWrapper != null;
            Character character = characterWrapper.getData().getResults().get(0);
            character.setDescription(character.getDescription());
            return character;
        } catch (URISyntaxException e) {
            throw new RuntimeException();
        }
    }
}