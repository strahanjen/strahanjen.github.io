/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.rest.action.admin.cluster;

import org.elasticsearch.action.admin.cluster.repositories.verify.VerifyRepositoryRequest;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.RestToXContentListener;

import static org.elasticsearch.client.Requests.verifyRepositoryRequest;
import static org.elasticsearch.rest.RestRequest.Method.POST;

public class RestVerifyRepositoryAction extends BaseRestHandler {

    @Inject
    public RestVerifyRepositoryAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(POST, "/_snapshot/{repository}/_verify", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel, final NodeClient client) {
        VerifyRepositoryRequest verifyRepositoryRequest = verifyRepositoryRequest(request.param("repository"));
        verifyRepositoryRequest.masterNodeTimeout(request.paramAsTime("master_timeout", verifyRepositoryRequest.masterNodeTimeout()));
        verifyRepositoryRequest.timeout(request.paramAsTime("timeout", verifyRepositoryRequest.timeout()));
        client.admin().cluster().verifyRepository(verifyRepositoryRequest, new RestToXContentListener<>(channel));
    }
}
