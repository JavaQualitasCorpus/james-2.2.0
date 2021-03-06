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

package org.apache.james.imapserver.commands;

import org.apache.james.imapserver.AccessControlException;
import org.apache.james.imapserver.ImapRequest;
import org.apache.james.imapserver.ImapSession;
import org.apache.james.imapserver.ImapSessionState;
import org.apache.james.imapserver.MailboxException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Implements the Status Command for getting the status of a Mailbox.
 *
 * @version 0.2 on 04 Aug 2002
 */
class StatusCommand extends AuthenticatedSelectedStateCommand
{
    public StatusCommand()
    {
        this.commandName = "STATUS";

       // this.getArgs().add( "mailbox" );
       // this.getArgs().add( "status data item" );
        this.getArgs().add( new AstringArgument( "mailbox" ) );
        this.getArgs().add( new ListArgument( "status data item" ) );
    }

    protected boolean doProcess( ImapRequest request, ImapSession session, List argValues )
    {
        String command = this.getCommand();

        String folder = (String) argValues.get( 0 );
        List dataNames = (List) argValues.get( 1 );

        try {
            String response = session.getImapHost().getMailboxStatus( session.getCurrentUser(), folder,
                                                         dataNames );
            session.untaggedResponse( " STATUS \"" + folder + "\" ("
                                       + response + ")" );
            session.okResponse( command );
        }
        catch ( MailboxException mbe ) {
            if ( mbe.isRemote() ) {
                session.noResponse( command , "[REFERRAL "
                                           + mbe.getRemoteServer() + "]"
                                           + SP + "Wrong server. Try remote." );
            }
            else {
                session.noResponse( command, "No such mailbox" );
            }
            return true;
        }
        catch ( AccessControlException ace ) {
            session.noResponse( command, "No such mailbox" );
            session.logACE( ace );
            return true;
        }
        if ( session.getState() == ImapSessionState.SELECTED ) {
            session.checkSize();
            session.checkExpunge();
        }
        return true;
    }
}
