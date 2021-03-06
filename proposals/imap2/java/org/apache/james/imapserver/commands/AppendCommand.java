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

import org.apache.james.core.MimeMessageSource;
import org.apache.james.core.MimeMessageWrapper;
import org.apache.james.imapserver.ImapRequestLineReader;
import org.apache.james.imapserver.ImapResponse;
import org.apache.james.imapserver.ImapSession;
import org.apache.james.imapserver.ProtocolException;
import org.apache.james.imapserver.store.ImapMailbox;
import org.apache.james.imapserver.store.MailboxException;
import org.apache.james.imapserver.store.MessageFlags;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Handles processeing for the APPEND imap command.
 *
 *
 * @version $Revision: 1.2.2.3 $
 */
class AppendCommand extends AuthenticatedStateCommand
{
    public static final String NAME = "APPEND";
    public static final String ARGS = "<mailbox> [<flag_list>] [<date_time>] literal";

    private AppendCommandParser parser = new AppendCommandParser();

    /** @see CommandTemplate#doProcess */
    protected void doProcess( ImapRequestLineReader request,
                              ImapResponse response,
                              ImapSession session )
            throws ProtocolException, MailboxException
    {
        String mailboxName = parser.mailbox( request );
        MessageFlags flags = parser.optionalAppendFlags( request );
        if ( flags == null ) {
            flags = new MessageFlags();
        }
        Date datetime = parser.optionalDateTime( request );
        if ( datetime == null ) {
            datetime = new Date();
        }
        MimeMessage message = parser.mimeMessage( request );
        parser.endLine( request );

        ImapMailbox mailbox = null;
        try {
            mailbox = getMailbox( mailboxName, session, true );
        }
        catch ( MailboxException e ) {
            e.setResponseCode( "TRYCREATE" );
            throw e;
        }

        mailbox.createMessage( message, flags, datetime );

        session.unsolicitedResponses( response );
        response.commandComplete( this );
    }

    /** @see ImapCommand#getName */
    public String getName()
    {
        return NAME;
    }

    /** @see CommandTemplate#getArgSyntax */
    public String getArgSyntax()
    {
        return ARGS;
    }

    private class AppendCommandParser extends CommandParser
    {
        /**
         * If the next character in the request is a '(', tries to read
         * a "flag_list" argument from the request. If not, returns a
         * MessageFlags with no flags set.
         */
        public MessageFlags optionalAppendFlags( ImapRequestLineReader request )
                throws ProtocolException
        {
            char next = request.nextWordChar();
            if ( next == '(' ) {
                return flagList( request );
            }
            else {
                return null;
            }
        }

        /**
         * If the next character in the request is a '"', tries to read
         * a DateTime argument. If not, returns null.
         */
        public Date optionalDateTime( ImapRequestLineReader request )
                throws ProtocolException
        {
            char next = request.nextWordChar();
            if ( next == '"' ) {
                return dateTime( request );
            }
            else {
                return null;
            }
        }

        /**
         * Reads a MimeMessage encoded as a string literal from the request.
         * TODO shouldn't need to read as a string and write out bytes
         *      use FixedLengthInputStream instead. Hopefully it can then be dynamic.
         * @param request The Imap APPEND request
         * @return A MimeMessage read off the request.
         */
        public MimeMessage mimeMessage( ImapRequestLineReader request )
                throws ProtocolException
        {
            request.nextWordChar();
            String mailString = consumeLiteral( request );
            MimeMessageSource source;
            try {
                byte[] messageBytes = mailString.getBytes( "US-ASCII" );
                source = new MimeMessageByteArraySource( "Mail" + System.currentTimeMillis(),
                                                         messageBytes);
            }
            catch ( Exception e ) {
                throw new ProtocolException( "UnexpectedException: " + e.getMessage() );
            }

            return new MimeMessageWrapper( source );
        }


    }

    class MimeMessageByteArraySource extends MimeMessageSource
    {
        private String sourceId;
        private byte[] byteArray;

        public MimeMessageByteArraySource( String sourceId, byte[] byteArray )
        {
            this.sourceId = sourceId;
            this.byteArray = byteArray;
        }

        public String getSourceId()
        {
            return sourceId;
        }

        public InputStream getInputStream() throws IOException
        {
            return new ByteArrayInputStream( byteArray );
        }
    }

    /**
     * This class is not yet used in the AppendCommand.
     *
     * An input stream which reads a fixed number of bytes from the underlying
     * input stream. Once the number of bytes has been read, the FixedLengthInputStream
     * will act as thought the end of stream has been reached, even if more bytes are
     * present in the underlying input stream.
     */
    class FixedLengthInputStream extends FilterInputStream
    {
        private long pos = 0;
        private long length;

        public FixedLengthInputStream( InputStream in, long length )
        {
            super( in );
            this.length = length;
        }

        public int read() throws IOException
        {
            if ( pos >= length ) {
                return -1;
            }
            pos++;
            return super.read();
         }

        public int read( byte b[] ) throws IOException
        {
            if ( pos >= length ) {
                return -1;
            }

            if ( pos + b.length >= length ) {
                pos = length;
                return super.read( b, 0, (int) (length - pos) );
            }

            pos += b.length;
            return super.read( b );
        }

        public int read( byte b[], int off, int len ) throws IOException
        {
            throw new IOException("Not implemented");
//            return super.read( b, off, len );
        }

        public long skip( long n ) throws IOException
        {
            throw new IOException( "Not implemented" );
//            return super.skip( n );
        }

        public int available() throws IOException
        {
            return super.available();
        }

        public void close() throws IOException
        {
            // Don't do anything to the underlying stream.
        }

        public synchronized void mark( int readlimit )
        {
            // Don't do anything.
        }

        public synchronized void reset() throws IOException
        {
            throw new IOException( "mark not supported" );
        }

        public boolean markSupported()
        {
            return false;
        }
    }
}
/*
6.3.11. APPEND Command

   Arguments:  mailbox name
               OPTIONAL flag parenthesized list
               OPTIONAL date/time string
               message literal

   Responses:  no specific responses for this command

   Result:     OK - append completed
               NO - append error: can't append to that mailbox, error
                    in flags or date/time or message text
               BAD - command unknown or arguments invalid

      The APPEND command appends the literal argument as a new message
      to the end of the specified destination mailbox.  This argument
      SHOULD be in the format of an [RFC-822] message.  8-bit characters
      are permitted in the message.  A server implementation that is
      unable to preserve 8-bit data properly MUST be able to reversibly
      convert 8-bit APPEND data to 7-bit using a [MIME-IMB] content
      transfer encoding.

      Note: There MAY be exceptions, e.g. draft messages, in which
      required [RFC-822] header lines are omitted in the message literal
      argument to APPEND.  The full implications of doing so MUST be
      understood and carefully weighed.

   If a flag parenthesized list is specified, the flags SHOULD be set in
   the resulting message; otherwise, the flag list of the resulting
   message is set empty by default.

   If a date_time is specified, the internal date SHOULD be set in the
   resulting message; otherwise, the internal date of the resulting
   message is set to the current date and time by default.

   If the append is unsuccessful for any reason, the mailbox MUST be
   restored to its state before the APPEND attempt; no partial appending
   is permitted.

   If the destination mailbox does not exist, a server MUST return an
   error, and MUST NOT automatically create the mailbox.  Unless it is
   certain that the destination mailbox can not be created, the server
   MUST send the response code "[TRYCREATE]" as the prefix of the text
   of the tagged NO response.  This gives a hint to the client that it
   can attempt a CREATE command and retry the APPEND if the CREATE is
   successful.

   If the mailbox is currently selected, the normal new mail actions
   SHOULD occur.  Specifically, the server SHOULD notify the client
   immediately via an untagged EXISTS response.  If the server does not
   do so, the client MAY issue a NOOP command (or failing that, a CHECK
   command) after one or more APPEND commands.

   Example:    C: A003 APPEND saved-messages (\Seen) {310}
               C: Date: Mon, 7 Feb 1994 21:52:25 -0800 (PST)
               C: From: Fred Foobar <foobar@Blurdybloop.COM>
               C: Subject: afternoon meeting
               C: To: mooch@owatagu.siam.edu
               C: Message-Id: <B27397-0100000@Blurdybloop.COM>
               C: MIME-Version: 1.0
               C: Content-Type: TEXT/PLAIN; CHARSET=US-ASCII
               C:
               C: Hello Joe, do you think we can meet at 3:30 tomorrow?
               C:
               S: A003 OK APPEND completed

      Note: the APPEND command is not used for message delivery, because
      it does not provide a mechanism to transfer [SMTP] envelope
      information.

*/
