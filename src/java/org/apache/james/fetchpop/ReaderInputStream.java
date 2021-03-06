/***********************************************************************
 * Copyright (c) 2002-2004 The Apache Software Foundation.             *
 * All rights reserved.                                                *
 * ------------------------------------------------------------------- *
 * Licensed under the Apache License, Version 2.0 (the "License"); you *
 * may not use this file except in compliance with the License. You    *
 * may obtain a copy of the License at:                                *
 *                                                                     *
 *     http://www.apache.org/licenses/LICENSE-2.0                      *
 *                                                                     *
 * Unless required by applicable law or agreed to in writing, software *
 * distributed under the License is distributed on an "AS IS" BASIS,   *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or     *
 * implied.  See the License for the specific language governing       *
 * permissions and limitations under the License.                      *
 ***********************************************************************/

package org.apache.james.fetchpop;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
/**
 * 
 * 
 * Simple class to allow a cast from a java.io.Reader to a java.io.InputStream<br>
 * <br>$Id: ReaderInputStream.java,v 1.1.4.3 2004/03/15 03:54:16 noel Exp $
 * 
 */
public class ReaderInputStream extends InputStream {
    private Reader reader = null;
    public ReaderInputStream(Reader reader) {
        this.reader = reader;
    }
    /**
     * @see java.io.InputStream#read()
     */
    public int read() throws IOException {
        return reader.read();
    }
}
