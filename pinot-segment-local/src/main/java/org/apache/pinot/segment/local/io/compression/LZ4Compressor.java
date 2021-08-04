/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.pinot.segment.local.io.compression;

import java.io.IOException;
import java.nio.ByteBuffer;
import net.jpountz.lz4.LZ4Factory;
import org.apache.pinot.segment.spi.compression.ChunkCompressor;

/**
 * Implementation of {@link ChunkCompressor} using LZ4 compression algorithm.
 * LZ4Factory.fastestInstance().fastCompressor().compress(sourceBuffer, destinationBuffer)
 */
public class LZ4Compressor implements ChunkCompressor {

  private static LZ4Factory _lz4Factory;

  public LZ4Compressor() {
    _lz4Factory = LZ4Factory.fastestInstance();
  }

  @Override
  public int compress(ByteBuffer inUncompressed, ByteBuffer outCompressed)
      throws IOException {

    _lz4Factory.fastCompressor().compress(inUncompressed, outCompressed);
    // When the compress method returns successfully,
    // dstBuf's position() will be set to its current position() plus the compressed size of the data.
    // and srcBuf's position() will be set to its limit()
    // Flip operation Make the destination ByteBuffer(outCompressed) ready for read by setting the position to 0
    outCompressed.flip();
    return outCompressed.limit();
  }
}