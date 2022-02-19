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
package com.matrix.k8s.merovingian;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1PodList;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/merovingian")
@RequiredArgsConstructor
public class MerovingianController {

    @Value("${matrix.namespace}")
    private String namespace;
    @Value("${service.name}")
    private String serviceName;

    public final CoreV1Api coreV1Api;

    @SuppressWarnings("ConstantConditions")
    @GetMapping("/exiled")
    public List<String> getExiledPrograms() throws ApiException {
        V1PodList v1PodList = coreV1Api.listNamespacedPod(namespace, null, null, null, null,
                "application=matrix,program="+serviceName,
                null, null, null, null, null);
        return v1PodList.getItems().stream().findAny().stream()
                .flatMap(v1Pod -> v1Pod.getSpec().getContainers().stream())
                .map(V1Container::getName).collect(Collectors.toList());
    }
}
