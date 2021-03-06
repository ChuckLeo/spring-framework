/*
 * Copyright 2002-2018 the original author or authors.
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

package org.springframework.web.reactive.socket;

import java.net.URI;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Simple container of information related to the handshake request that started
 * the {@link WebSocketSession} session.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 * @see WebSocketSession#getHandshakeInfo()
 */
public class HandshakeInfo {

	private final URI uri;

	private final Mono<Principal> principalMono;

	private final HttpHeaders headers;

	@Nullable
	private final String protocol;

	private final Map<String, Object> attributes;


	/**
	 * Constructor with information about the handshake.
	 * @param uri the endpoint URL
	 * @param headers request headers for server or response headers or client
	 * @param principal the principal for the session
	 * @param protocol the negotiated sub-protocol (may be {@code null})
	 */
	public HandshakeInfo(URI uri, HttpHeaders headers, Mono<Principal> principal, @Nullable String protocol) {
		this(uri, headers, principal, protocol, Collections.emptyMap());
	}

	/**
	 * Constructor with information about the handshake.
	 * @param uri the endpoint URL
	 * @param headers request headers for server or response headers or client
	 * @param principal the principal for the session
	 * @param protocol the negotiated sub-protocol (may be {@code null})
	 * @since 5.1
	 */
	public HandshakeInfo(URI uri, HttpHeaders headers, Mono<Principal> principal,
			@Nullable String protocol, Map<String, Object> attributes) {

		Assert.notNull(uri, "URI is required");
		Assert.notNull(headers, "HttpHeaders are required");
		Assert.notNull(principal, "Principal is required");
		Assert.notNull(principal, "'attributes' is required");

		this.uri = uri;
		this.headers = headers;
		this.principalMono = principal;
		this.protocol = protocol;
		this.attributes = attributes;
	}


	/**
	 * Return the URL for the WebSocket endpoint.
	 */
	public URI getUri() {
		return this.uri;
	}

	/**
	 * Return the handshake HTTP headers. Those are the request headers for a
	 * server session and the response headers for a client session.
	 */
	public HttpHeaders getHeaders() {
		return this.headers;
	}

	/**
	 * Return the principal associated with the handshake HTTP request.
	 */
	public Mono<Principal> getPrincipal() {
		return this.principalMono;
	}

	/**
	 * The sub-protocol negotiated at handshake time, or {@code null} if none.
	 * @see <a href="https://tools.ietf.org/html/rfc6455#section-1.9">
	 * https://tools.ietf.org/html/rfc6455#section-1.9</a>
	 */
	@Nullable
	public String getSubProtocol() {
		return this.protocol;
	}

	/**
	 * Attributes extracted from the handshake request to be added to the
	 * WebSocket session.
	 * @since 5.1
	 */
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}


	@Override
	public String toString() {
		return "HandshakeInfo[uri=" + this.uri + ", headers=" + this.headers + "]";
	}

}
