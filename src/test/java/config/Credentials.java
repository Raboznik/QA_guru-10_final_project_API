package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/credentials.properties"
})
public interface Credentials extends Config {
        @Key("create.name")
        String createName();

        @Key("create.job")
        String createJob();

        @Key("update.job")
        String updateJob();

        @Key("register.email")
        String crateEmail();

        @Key("register.password")
        String registerPassword();

        @Key("login.password")
        String loginPassword();

    }
