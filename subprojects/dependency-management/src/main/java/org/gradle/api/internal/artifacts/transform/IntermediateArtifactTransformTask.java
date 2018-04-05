/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.artifacts.transform;

import org.gradle.api.NonNullApi;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.ResolvableArtifact;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.ResolvedArtifactSet;
import org.gradle.internal.work.WorkerLeaseService;

import javax.inject.Inject;

@NonNullApi
public class IntermediateArtifactTransformTask extends ArtifactTransformTask {
    private final ArtifactTransformTask delegateTransform;

    @Inject
    public IntermediateArtifactTransformTask(UserCodeBackedTransformer transform, ArtifactTransformTask delegateTransform, WorkerLeaseService workerLeaseService) {
        super(transform, workerLeaseService);
        this.delegateTransform = delegateTransform;
        dependsOn(delegateTransform);
    }

    @Override
    public ResolvedArtifactSet.Completion resolveArtifacts() {
        return delegateTransform.resolveArtifacts();
    }

    @Override
    public TransformationResult incomingTransformationResult(ResolvableArtifact artifact) {
        return delegateTransform.artifactResults.get(artifact);
    }
}