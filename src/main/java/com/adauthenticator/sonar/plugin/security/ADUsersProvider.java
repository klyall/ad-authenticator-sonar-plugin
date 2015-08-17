/*
 * Sonar AD Plugin
 * Copyright (C) 2012-2014 Jiji Sasidharan
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

import com.adauthenticator.ADUserRetriever;
import com.adauthenticator.model.ADUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.security.ExternalUsersProvider;
import org.sonar.api.security.UserDetails;

/**
 * Retrieves the user details from Active Directory and updates the User Details object.
 * The User Details object is injected so that it can be updated after the authentication has been done.
 *
 * @author  Keith Lyall
 * @author Jiji_Sasidharan
 */
public class ADUsersProvider extends ExternalUsersProvider {
    
    private static final Logger LOG = LoggerFactory.getLogger(ADUsersProvider.class);
    private ADUserRetriever adUserRetriever;
    private UserDetails userDetails;

    public ADUsersProvider(ADUserRetriever adUserRetriever, UserDetails userDetails) {
        this.adUserRetriever = adUserRetriever;
        this.userDetails = userDetails;
    }

    /**
     * Returns the UserDetails.
     */
    public UserDetails doGetUserDetails(Context context) {
        String username = context.getUsername();
        LOG.debug("Retrieving the user details for '{}'", username);

        // Bypass AD authentication for default user 'admin'
        if ("admin".equals(username)) {
            LOG.info("Bypassing AD to allow Sonar default user 'admin' "
                    + "to authenticate against Sonar internal database. ");
        } else {
            ADUser adUser = adUserRetriever.retrieveLoggedInUser();
            if (adUser != null) {
                userDetails = mapFrom(adUser);
            } else {
                userDetails.setName(context.getUsername());
                userDetails.setEmail("");
            }
            LOG.debug("Updated userDetails: {}", userDetails);
        }
        return userDetails;
    }

    private UserDetails mapFrom(ADUser adUser) {
        userDetails.setName(adUser.getDisplayName());
        userDetails.setEmail(adUser.getEmail());
        return userDetails;
    }
}
