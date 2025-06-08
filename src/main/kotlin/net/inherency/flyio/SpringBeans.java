package net.inherency.flyio;

import com.google.api.client.json.gson.GsonFactory;
import net.inherency.google.base.GoogleSheetClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans {

    @Value("${googleAuthJson}") //this may be read from file. See Env.kt.
    private String googleAuthJson;

    @Value("${googleSheetId}")
    private String googleSheetId;

    @Value("${googleAppName}")
    private String googleAppName;

    @Bean
    public GoogleSheetClient googleSheetClient() {
        var googleAuthJsonEnv = googleAuthJson;
        if (googleAuthJsonEnv.equalsIgnoreCase("file")) {
            googleAuthJsonEnv = Env.Companion.getGOOGLE_AUTH_JSON();
        }

        return new GoogleSheetClient(
                new GsonFactory(), googleSheetId, googleAuthJsonEnv, googleAppName);
    }
}
