/*
 * Sonar AD Plugin
 * Copyright (C) Jiji Sasidharan
 * http://programmingforliving.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.adauthenticator.sonar.plugin.security;

import com.adauthenticator.ADUserAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.security.Authenticator;
import org.sonar.api.security.ExternalUsersProvider;

/**
 * Authenticator for Sonar AD Plugin.
 * 
 * @author Jiji_Sasidharan
 */
public class SonarADAuthenticator extends Authenticator {

    private static final Logger LOG = LoggerFactory.getLogger(SonarADAuthenticator.class);

    private ADUserAuthenticator adAuthenticator;
    private ExternalUsersProvider usersProvider;

    public SonarADAuthenticator(ADUserAuthenticator adAuthenticator, ExternalUsersProvider usersProvider) {
        this.adAuthenticator = adAuthenticator;
        this.usersProvider = usersProvider;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.sonar.api.security.Authenticator#doAuthenticate(org.sonar.api.security.Authenticator.Context)
     */
    @Override
    public boolean doAuthenticate(Context context) {
        LOG.debug("Authenticating: {}", context.getUsername());

        boolean result = adAuthenticator.authenticate(context.getUsername(), context.getPassword());

        if (result) {
            LOG.debug("Authentication passed: {}", context.getUsername());
            updateUserDetailsFromActiveDirectory(context.getUsername());
        } else {
            LOG.debug("Authentication failed: {}", context.getUsername());
        }

        return result;
    }

    private void updateUserDetailsFromActiveDirectory(String username) {
        ExternalUsersProvider.Context ctx = new ExternalUsersProvider.Context(username, null);
        usersProvider.doGetUserDetails(ctx);
    }
}
