package org.apache.maven.wagon.providers.webdav.jackrabbit;

/*
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

import java.io.File;
import java.io.IOException;

import org.apache.maven.wagon.FileTestUtils;
import org.apache.maven.wagon.WagonTestCase;
import org.apache.maven.wagon.repository.Repository;
import org.apache.maven.wagon.resource.Resource;

/**
 * WebDAV Wagon Test
 *
 * @author <a href="mailto:joakim@erdfelt.com">Joakim Erdfelt</a>
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 */
public class WebDavWagonTest
    extends WagonTestCase
{
    private ServletServer server;

    protected String getTestRepositoryUrl()
        throws IOException
    {
        return "dav:http://localhost:10007/dav/newfolder/folder2";
    }

    protected String getProtocol()
    {
        return "dav";
    }

    protected void setupWagonTestingFixtures()
        throws Exception
    {
        if ( System.getProperty( "basedir" ) == null )
        {
            System.setProperty( "basedir", System.getProperty( "user.dir" ) );
        }

        File file = FileTestUtils.createUniqueFile( "dav-repository", "test-resource" );

        file.delete();

        File davDir = file.getParentFile();
        davDir.mkdirs();

        server = (ServletServer) lookup( ServletServer.ROLE );
    }

    protected void tearDownWagonTestingFixtures()
        throws Exception
    {
        release( server );
    }

    protected long getExpectedLastModifiedOnGet( Repository repository, Resource resource )
    {
        File file = getTestFile( "target/test-output/dav-repository/newfolder/folder2", resource.getName() );
        return file.lastModified();
    }
}