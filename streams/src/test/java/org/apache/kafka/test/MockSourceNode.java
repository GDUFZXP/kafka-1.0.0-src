/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.test;


import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.internals.SourceNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class MockSourceNode<K, V> extends SourceNode<K, V> {

    public static final String NAME = "MOCK-SOURCE-";
    public static final AtomicInteger INDEX = new AtomicInteger(1);

    public int numReceived = 0;
    public final ArrayList<K> keys = new ArrayList<>();
    public final ArrayList<V> values = new ArrayList<>();
    public boolean initialized;
    public boolean closed;

    public MockSourceNode(String[] topics, Deserializer<K> keyDeserializer, Deserializer<V> valDeserializer) {
        super(NAME + INDEX.getAndIncrement(), Arrays.asList(topics), keyDeserializer, valDeserializer);
    }

    @Override
    public void process(K key, V value) {
        this.numReceived++;
        this.keys.add(key);
        this.values.add(value);
    }

    @Override
    public void init(final ProcessorContext context) {
        super.init(context);
        initialized = true;
    }

    @Override
    public void close() {
        super.close();
        this.closed = true;
    }
}
