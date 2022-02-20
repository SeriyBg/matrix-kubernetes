/*
 * Copyright (c) 2008-2021, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.matrix.k8s.agent;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class AgentController {

    private final AtomicReference<String> agent = new AtomicReference<>("default");
    private final AtomicInteger health = new AtomicInteger(100);
    private final Random random = new Random();

    @GetMapping(value = "/health")
    public Health getHealth() {
        return new Health(agent.get(), health.get());
    }

    @PostMapping(value = "/", consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void initAgent(@RequestBody() String agentName) {
        if (!"default".equals(agent.get())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Agent is already initialized.");
        }
        InputStream resourceAsStream = getInputStream(agentName);
        if (resourceAsStream == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Agent " + agentName + " is not a known agent name");
        }
        agent.set(agentName);
    }

    @GetMapping(value = "/", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getAgent() throws IOException {
        InputStream resourceAsStream = getInputStream(agent.get());
        if (resourceAsStream == null) {
            resourceAsStream = getClass().getClassLoader().getResourceAsStream("img/default.jpeg");
        }
        return Objects.requireNonNull(resourceAsStream).readAllBytes();
    }

    @GetMapping("/fight")
    public FightHit fight() {
        int hit = random.nextInt(30);
        int agentHealth = health.accumulateAndGet(hit, (x, y) -> x - y);
        if (agentHealth < 0) {
            return new FightHit(0, 0, 0);
        }
        return new FightHit(hit/2, hit, agentHealth);
    }

    private InputStream getInputStream(String agent) {
        return getClass().getClassLoader().getResourceAsStream("img/" + agent.toLowerCase() +
                ".jpeg");
    }
}
