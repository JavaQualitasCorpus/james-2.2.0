/***********************************************************************
 * Copyright (c) 2003-2004 The Apache Software Foundation.             *
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
 
package org.apache.james.fetchmail;

import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.apache.avalon.framework.logger.Logger;
import org.apache.james.services.MailServer;
import org.apache.mailet.MailAddress;
import org.apache.james.services.UsersRepository;

/**
 * <p>Class <code>ProcessorAbstract</code> is an abstract class that
 * provides support for JavaMail processors. Concrete implementations are
 * required to implement the abstract method <code>void process()</code> to
 * process a JavaMail element.</p>
 * 
 * <p>Typically, processors are chained. A Store processor delegates to a Folder
 * processor that delegates to a Message processor.</p>
 * 
 * <p><code>ProcessorAbstract</code> wraps an Account - see 
 * <code>org.apache.james.fetchmail.Account</code>
 * - providing contextual information about the environment for the processor.</p>
 * 
 * <p>Creation Date: 27-May-03</p>
 * 
 */
abstract public class ProcessorAbstract
{
    /**
     * The prefix to place in front of any mail attributes used by this Processor.
     */ 
    private String fieldAttributePrefix;
    
    /**
     * The Account for this task
     */
    private Account fieldAccount;       

    /**
     * Constructor for ProcessorAbstract.
     */
    private ProcessorAbstract()
    {
        super();
    }
    
    /**
     * Constructor for ProcessorAbstract.
     * @param account The <code>Account</code> to be processed 
     */
    protected ProcessorAbstract(Account account)
    {
        this();
        setAccount(account);        
    }   
    
    /**
     * Returns the defaultDomainName.
     * @return String
     */
    protected String getDefaultDomainName()
    {
        return getConfiguration().getDefaultDomainName();
    }
    
    /**
     * Returns the message ids. of messages for which processing has been
     * deferred as the recipient could not be found
     * @return List
     */
    protected List getDeferredRecipientNotFoundMessageIDs()
    {
        return getAccount().getDeferredRecipientNotFoundMessageIDs();
    }    
    
    /**
     * Returns the fetchTaskName.
     * @return String
     */
    protected String getFetchTaskName()
    {
        return getConfiguration().getFetchTaskName();
    }   


    /**
     * Returns the host.
     * @return String
     */
    protected String getHost()
    {
        return getConfiguration().getHost();
    }


    /**
     * Returns the javaMailFolderName.
     * @return String
     */
    protected String getJavaMailFolderName()
    {
        return getConfiguration().getJavaMailFolderName();
    }



    /**
     * Returns the javaMailProviderName.
     * @return String
     */
    protected String getJavaMailProviderName()
    {
        return getConfiguration().getJavaMailProviderName();
    }



    /**
     * Returns the logger.
     * @return Logger
     */
    protected Logger getLogger()
    {
        return getConfiguration().getLogger();
    }


    /**
     * Returns the password.
     * @return String
     */
    protected String getPassword()
    {
        return getAccount().getPassword();
    }


    /**
     * Returns the recipient.
     * @return MailAddress
     */
    protected MailAddress getRecipient()
    {
        return getAccount().getRecipient();
    }
    
    /**
     * Method getRemoteReceivedHeaderIndex.
     * @return int
     */
    protected int getRemoteReceivedHeaderIndex()
    {
        return getConfiguration().getRemoteReceivedHeaderIndex();    
    }


    /**
     * Returns the server.
     * @return MailServer
     */
    protected MailServer getServer()
    {
        return getConfiguration().getServer();
    }
    
    /**
     * Returns the session.
     * @return Session
     */
    protected Session getSession()
    {
        return getAccount().getSession();
    }    
    
    /**
     * Returns the repository of local users.
     * @return UsersRepository
     */
    protected UsersRepository getLocalUsers()
    {
        return getConfiguration().getLocalUsers();
    }   


    /**
     * Returns the user.
     * @return String
     */
    protected String getUser()
    {
        return getAccount().getUser();
    }


    /**
     * Returns the fetchAll.
     * @return boolean
     */
    protected boolean isFetchAll()
    {
        return getConfiguration().isFetchAll();
    }


    /**
     * Returns the isDeferRecipientNotFound.
     * @return boolean
     */
    protected boolean isDeferRecipientNotFound()
    {
        return getConfiguration().isDeferRecipientNotFound();
    }
    
    /**
     * Returns the ignoreOriginalRecipient.
     * @return boolean
     */
    protected boolean isIgnoreRecipientHeader()
    {
        return getAccount().isIgnoreRecipientHeader();
    }


    /**
     * Returns the leave.
     * @return boolean
     */
    protected boolean isLeave()
    {
        return getConfiguration().isLeave();
    }


    /**
     * Returns the markSeen.
     * @return boolean
     */
    protected boolean isMarkSeen()
    {
        return getConfiguration().isMarkSeen();
    }
    
    /**
     * Returns the leaveBlacklisted.
     * @return boolean
     */
    protected boolean isLeaveBlacklisted()
    {
        return getConfiguration().isLeaveBlacklisted();
    }
    
    /**
     * Returns the leaveRemoteRecipient.
     * @return boolean
     */
    protected boolean isLeaveRemoteRecipient()
    {
        return getConfiguration().isLeaveRemoteRecipient();
    }   
    
    /**
     * Returns the leaveUserUndefinded.
     * @return boolean
     */
    protected boolean isLeaveUserUndefined()
    {
        return getConfiguration().isLeaveUserUndefined();
    }
    
    /**
     * Returns the leaveRemoteReceivedHeaderInvalid.
     * @return boolean
     */
    protected boolean isLeaveRemoteReceivedHeaderInvalid()
    {
        return getConfiguration().isLeaveRemoteReceivedHeaderInvalid();
    }    
    
    /**
     * Returns the LeaveMaxMessageSizeExceeded.
     * @return boolean
     */
    protected boolean isLeaveMaxMessageSizeExceeded()
    {
        return getConfiguration().isLeaveMaxMessageSizeExceeded();
    }    
    
    /**
     * Returns the leaveUndeliverable.
     * @return boolean
     */
    protected boolean isLeaveUndeliverable()
    {
        return getConfiguration().isLeaveUndeliverable();
    }       

    /**
     * Returns the RejectUserUndefinded.
     * @return boolean
     */
    protected boolean isRejectUserUndefined()
    {
        return getConfiguration().isRejectUserUndefined();
    }
    
    /**
     * Returns the RejectRemoteReceivedHeaderInvalid.
     * @return boolean
     */
    protected boolean isRejectRemoteReceivedHeaderInvalid()
    {
        return getConfiguration().isRejectRemoteReceivedHeaderInvalid();
    }    
    
    /**
     * Returns the RejectMaxMessageSizeExceeded.
     * @return boolean
     */
    protected boolean isRejectMaxMessageSizeExceeded()
    {
        return getConfiguration().isRejectMaxMessageSizeExceeded();
    }    
    
    /**
     * Returns the RejectUserBlacklisted.
     * @return boolean
     */
    protected boolean isRejectBlacklisted()
    {
        return getConfiguration().isRejectBlacklisted();
    }   
    
    /**
     * Returns the RejectRemoteRecipient.
     * @return boolean
     */
    protected boolean isRejectRemoteRecipient()
    {
        return getConfiguration().isRejectRemoteRecipient();
    }
    
    /**
     * Returns the markBlacklistedSeen.
     * @return boolean
     */
    protected boolean isMarkBlacklistedSeen()
    {
        return getConfiguration().isMarkBlacklistedSeen();
    }
    
    /**
     * Returns the markRecipientNotFoundSeen.
     * @return boolean
     */
    protected boolean isMarkRecipientNotFoundSeen()
    {
        return getConfiguration().isMarkRecipientNotFoundSeen();
    }
    
    /**
     * Returns the leaveRecipientNotFound.
     * @return boolean
     */
    protected boolean isLeaveRecipientNotFound()
    {
        return getConfiguration().isLeaveRecipientNotFound();
    }
    
    /**
     * Returns the rejectRecipientNotFound.
     * @return boolean
     */
    protected boolean isRejectRecipientNotFound()
    {
        return getConfiguration().isRejectRecipientNotFound();
    }       

    /**
     * Returns the markRemoteRecipientSeen.
     * @return boolean
     */
    protected boolean isMarkRemoteRecipientSeen()
    {
        return getConfiguration().isMarkRemoteRecipientSeen();
    }   
    
    /**
     * Returns the markUserUndefindedSeen.
     * @return boolean
     */
    protected boolean isMarkUserUndefinedSeen()
    {
        return getConfiguration().isMarkUserUndefinedSeen();
    }
    
    /**
     * Returns the markRemoteReceivedHeaderInvalidSeen.
     * @return boolean
     */
    protected boolean isMarkRemoteReceivedHeaderInvalidSeen()
    {
        return getConfiguration().isMarkRemoteReceivedHeaderInvalidSeen();
    }    
    
    /**
     * Returns the MarkMaxMessageSizeExceededSeen.
     * @return boolean
     */
    protected boolean isMarkMaxMessageSizeExceededSeen()
    {
        return getConfiguration().isMarkMaxMessageSizeExceededSeen();
    }    
    
    /**
     * Returns the markUndeliverableSeen.
     * @return boolean
     */
    protected boolean isMarkUndeliverableSeen()
    {
        return getConfiguration().isMarkUndeliverableSeen();
    }
    
    /**
     * Answers true if the folder should be opened read only.
     * For this to be true... 
     * - isKeep() must be true
     * - isMarkSeen() must be false
     * @return boolean
     */
    protected boolean isOpenReadOnly()
    {
        return getConfiguration().isOpenReadOnly();
    }   


    /**
     * Returns the recurse.
     * @return boolean
     */
    protected boolean isRecurse()
    {
        return getConfiguration().isRecurse();
    }


    
    
    /**
     * Process the mail elements of the receiver
     */
    abstract public void process() throws MessagingException;
    
    /**
     * Returns the blacklist.
     * @return Set
     */
    protected Set getBlacklist()
    {
        return getConfiguration().getBlacklist();
    }
    
    /**
     * Returns the <code>ParsedConfiguration</code> from the <code>Account</code>.
     * @return ParsedConfiguration
     */
    protected ParsedConfiguration getConfiguration()
    {
        return getAccount().getParsedConfiguration();
    }    

    /**
     * Returns a lazy initialised attributePrefix.
     * @return String
     */
    protected String getAttributePrefix()
    {
        String value = null;
        if (null == (value = getAttributePrefixBasic()))
        {
            updateAttributePrefix();
            return getAttributePrefix();
        }    
        return value;
    }
    
    /**
     * Returns the attributePrefix.
     * @return String
     */
    private String getAttributePrefixBasic()
    {
        return fieldAttributePrefix;
    }
    
    /**
     * Returns the computed attributePrefix.
     * @return String
     */
    protected String computeAttributePrefix()
    {
        return getClass().getPackage().getName() + ".";
    }       

    /**
     * Sets the attributePrefix.
     * @param attributePrefix The attributePrefix to set
     */
    protected void setAttributePrefix(String attributePrefix)
    {
        fieldAttributePrefix = attributePrefix;
    }
    
    /**
     * Updates the attributePrefix.
     */
    protected void updateAttributePrefix()
    {
        setAttributePrefix(computeAttributePrefix());
    }    

    /**
     * Returns the account.
     * @return Account
     */
    public Account getAccount()
    {
        return fieldAccount;
    }

    /**
     * Sets the account.
     * @param account The account to set
     */
    protected void setAccount(Account account)
    {
        fieldAccount = account;
    }
    
    /**
     * Returns the getMaxMessageSizeLimit.
     * @return int
     */
    protected int getMaxMessageSizeLimit()
    {
        return getConfiguration().getMaxMessageSizeLimit();
    }    

}
