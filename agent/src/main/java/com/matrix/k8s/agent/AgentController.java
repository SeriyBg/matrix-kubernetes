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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final AtomicReference<String> agent = new AtomicReference<>("default");

    @PostMapping(value = "/", consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void initAgent(@RequestBody() String agentName) {
        if (!"default".equals(agent.get())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Agent is already initialized.");
        }
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("img/" + agentName.toLowerCase() + ".jpeg");
        if (resourceAsStream == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Agent " + agentName + " is not a known agent name");
        }
        agent.set(agentName);
    }

    @GetMapping(value = "/", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getAgent() throws IOException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("img/" + agent.get().toLowerCase() +
                ".jpeg");
        if (resourceAsStream == null) {
            resourceAsStream = getClass().getClassLoader().getResourceAsStream("img/default.jpeg");
        }
        return Objects.requireNonNull(resourceAsStream).readAllBytes();
    }
}
