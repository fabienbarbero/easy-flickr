/*
 * (C) Copyright 2014 Fabien Barbero.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package com.github.fabienbarbero.flickr.api;

import com.github.fabienbarbero.flickr.api.entities.UserInfo;
import javax.swing.JOptionPane;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Fabien Barbero
 */
public class InitializationTest
        extends AbstractTest
{

    @Test
    public void testInitialization()
            throws Exception
    {
        if ( flickr.isFirstStart() ) {
            String url = flickr.getAuthorizationUrl();
            System.out.println( url );
            assertNull( flickr.getUser() );

            String verifier = JOptionPane.showInputDialog( "Verifier" );
            String token = JOptionPane.showInputDialog( "Token" );
            flickr.verifyToken( verifier, token );
        }

        // Auth
        UserInfo caller = flickr.getUser();
        assertNotNull( caller );
        assertNotNull( caller.getPhotosInfo().getFirstDate() );
        assertTrue( caller.getPhotosInfo().getCount() > 0 );
    }

}
