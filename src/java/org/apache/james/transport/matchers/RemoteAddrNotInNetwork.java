/***********************************************************************
 * Copyright (c) 2000-2004 The Apache Software Foundation.             *
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

package org.apache.james.transport.matchers;

import org.apache.mailet.Mail;

import javax.mail.MessagingException;
import java.util.Collection;

/**
 * Checks the IP address of the sending server against a comma-
 * delimited list of IP addresses, domain names or sub-nets.
 *
 * <p>See AbstractNetworkMatcher for details on how to specify
 * entries.</p>
 *
 */
public class RemoteAddrNotInNetwork extends AbstractNetworkMatcher {
    public Collection match(Mail mail) {
        return matchNetwork(mail.getRemoteAddr()) ? null : mail.getRecipients();
    }
}
