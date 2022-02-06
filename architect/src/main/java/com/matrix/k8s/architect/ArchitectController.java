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
package com.matrix.k8s.architect;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServiceList;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/architect")
@RequiredArgsConstructor
public class ArchitectController {

    @Value("${architect.namespace}")
    private String namespace;

    public final CoreV1Api coreV1Api;

    @GetMapping("/programs")
    public List<String> getMatrixPrograms() throws ApiException {
        V1ServiceList v1ServiceList = coreV1Api.listNamespacedService(namespace, null, null, null, null, "application=matrix",
                null, null, null, null, null);
        return v1ServiceList.getItems().stream().map(s -> Objects.requireNonNull(s.getMetadata()).getName()).collect(Collectors.toList());
    }

}
