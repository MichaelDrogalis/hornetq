/*
 * Copyright 2005-2014 Red Hat, Inc.
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.hornetq.core.protocol.core.impl.wireformat;

import javax.transaction.xa.Xid;

import org.hornetq.api.core.HornetQBuffer;
import org.hornetq.core.protocol.core.impl.PacketImpl;
import org.hornetq.utils.XidCodecSupport;

/**
 * @author <a href="mailto:tim.fox@jboss.com">Tim Fox</a>
 *
 */
public class SessionXACommitMessage extends PacketImpl
{
   private boolean onePhase;

   private Xid xid;

   public SessionXACommitMessage(final Xid xid, final boolean onePhase)
   {
      super(SESS_XA_COMMIT);

      this.xid = xid;
      this.onePhase = onePhase;
   }

   public SessionXACommitMessage()
   {
      super(SESS_XA_COMMIT);
   }

   public Xid getXid()
   {
      return xid;
   }

   public boolean isOnePhase()
   {
      return onePhase;
   }

   @Override
   public boolean isAsyncExec()
   {
      return true;
   }

   @Override
   public void encodeRest(final HornetQBuffer buffer)
   {
      XidCodecSupport.encodeXid(xid, buffer);
      buffer.writeBoolean(onePhase);
   }

   @Override
   public void decodeRest(final HornetQBuffer buffer)
   {
      xid = XidCodecSupport.decodeXid(buffer);
      onePhase = buffer.readBoolean();
   }

   @Override
   public String toString()
   {
      return getParentString() + ", xid=" + xid + ", onePhase=" + onePhase + "]";
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + (onePhase ? 1231 : 1237);
      result = prime * result + ((xid == null) ? 0 : xid.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (!super.equals(obj))
         return false;
      if (!(obj instanceof SessionXACommitMessage))
         return false;
      SessionXACommitMessage other = (SessionXACommitMessage)obj;
      if (onePhase != other.onePhase)
         return false;
      if (xid == null)
      {
         if (other.xid != null)
            return false;
      }
      else if (!xid.equals(other.xid))
         return false;
      return true;
   }
}
