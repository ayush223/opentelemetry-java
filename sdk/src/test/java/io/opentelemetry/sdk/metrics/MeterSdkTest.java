/*
 * Copyright 2019, OpenTelemetry Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opentelemetry.sdk.metrics;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableMap;
import io.opentelemetry.metrics.LongCounter;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Unit tests for {@link MeterSdk}. */
@RunWith(JUnit4.class)
public class MeterSdkTest {

  @Test
  public void testLongCounter() {
    MeterSdk testSdk = new MeterSdk();

    LongCounter longCounter =
        testSdk
            .longCounterBuilder("testCounter")
            .setConstantLabels(ImmutableMap.of("sk1", "sv1"))
            .setLabelKeys(Collections.singletonList("sk1"))
            .setDescription("My very own counter")
            .setUnit("metric tonnes")
            .setMonotonic(true)
            .build();
    assertThat(longCounter).isNotNull();
    assertThat(longCounter).isInstanceOf(SdkLongCounter.class);

    // todo: verify that the MeterSdk has kept track of what has been created, once that's in place
  }

  @Test
  public void testLabelSets() {
    MeterSdk testSdk = new MeterSdk();

    assertThat(testSdk.emptyLabelSet()).isSameInstanceAs(testSdk.emptyLabelSet());
    assertThat(testSdk.createLabelSet("key", "value"))
        .isEqualTo(testSdk.createLabelSet("key", "value"));
    assertThat(testSdk.createLabelSet("k1", "v1", "k2", "v2"))
        .isEqualTo(testSdk.createLabelSet("k1", "v1", "k2", "v2"));
    assertThat(testSdk.createLabelSet("k1", "v1", "k2", "v2", "k3", "v3"))
        .isEqualTo(testSdk.createLabelSet("k1", "v1", "k2", "v2", "k3", "v3"));
    assertThat(testSdk.createLabelSet("k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4"))
        .isEqualTo(testSdk.createLabelSet("k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4"));
  }
}