package me.common.apis.ping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PingApiImpl implements PingApi {

    @Override
    public int ping() {
        log.info("Server ping!");
        return 0;
    }
}
