/*
 * Copyright 2010 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.fusesource.fabric.webui.agents

import activemq.ActiveMQAgentResource
import camel.CamelAgentResource
import jvm.JVMAgentResource
//import monitor.MonitorAgentResource
import org.fusesource.fabric.api.Container
import osgi.OsgiAgentResource

/**
 * <p>
 * </p>
 *
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
trait ManagementExtension {
  def id: String
}

trait ManagementExtensionFactory {
  def create(a: Container, jmx_username: String, jmx_password:String ): Option[ManagementExtension]
}

object ManagementExtensionFactory {
  // We could dynamically look the extensions up to support plugin extensions.
  val factories = List[ManagementExtensionFactory](
    JVMAgentResource,
    ActiveMQAgentResource,
    CamelAgentResource,
    OsgiAgentResource
/*    ,
    MonitorAgentResource
*/
  )

  def extensions(a: Container, jmx_username:String, jmx_password:String): Seq[ManagementExtension] = factories.flatMap(_.create(a, jmx_username, jmx_password))
}
