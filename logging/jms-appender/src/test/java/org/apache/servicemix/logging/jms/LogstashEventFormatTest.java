/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.servicemix.logging.jms;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;

import org.junit.Test;
import org.ops4j.pax.logging.spi.PaxLoggingEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test cases for the {@link LogstashEventFormat} class
 */
public class LogstashEventFormatTest {

    private final LoggingEventFormat format = new LogstashEventFormat();

    @Test
    public void testBasicLogstashFormat() throws JsonException {
        PaxLoggingEvent event = MockEvents.createInfoEvent();

        JsonObject object = Json.createReader(new StringReader(format.toString(event))).readObject();
        assertEquals(MockEvents.LOG_MESSAGE, object.getString(LogstashEventFormat.MESSAGE));
        assertEquals(MockEvents.LOGGER_NAME, object.getString(LogstashEventFormat.SOURCE));
        assertEquals("INFO", object.getJsonArray(LogstashEventFormat.TAGS).getString(0));
        assertEquals(MockEvents.LOG_PROPERTY_VALUE,
                     object.getJsonObject(LogstashEventFormat.FIELDS).getString(MockEvents.LOG_PROPERTY_ID));
        assertNotNull(object.get(LogstashEventFormat.TIMESTAMP));

        System.out.println(object);
    }


}
