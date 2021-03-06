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

package org.apache.james.smtpserver;

import org.apache.james.services.MailServer;
import org.apache.james.services.UsersRepository;

/**
 * Provides a number of server-wide constant values to the
 * SMTPHandlers
 *
 */
public interface SMTPHandlerConfigurationData {

    /**
     * Returns the service wide hello name
     *
     * @return the hello name
     */
    String getHelloName();

    /**
     * Returns the service wide reset length in bytes.
     *
     * @return the reset length
     */
    int getResetLength();

    /**
     * Returns the service wide maximum message size in bytes.
     *
     * @return the maximum message size
     */
    long getMaxMessageSize();

    /**
     * Returns whether relaying is allowed for the IP address passed.
     *
     * @param remoteIP the remote IP address in String form
     * @return whether relaying is allowed
     */
    boolean isRelayingAllowed(String remoteIP);

    /**
     * Returns whether SMTP AUTH is active for this server, and
     * necessary for the IP address passed.
     *
     * @param remoteIP the remote IP address in String form
     * @return whether SMTP authentication is on
     */
    boolean isAuthRequired(String remoteIP);

    /**
     * Returns whether SMTP auth is active for this server.
     *
     * @return whether SMTP authentication is on
     */
    boolean isAuthRequired();

    /**
     * Returns whether the service validates the identity
     * of its senders.
     *
     * @return whether SMTP authentication is on
     */
    boolean isVerifyIdentity();

    /**
     * Returns the MailServer interface for this service.
     *
     * @return the MailServer interface for this service
     */
    MailServer getMailServer();

    /**
     * Returns the UsersRepository for this service.
     *
     * @return the local users repository
     */
    UsersRepository getUsersRepository();
}
