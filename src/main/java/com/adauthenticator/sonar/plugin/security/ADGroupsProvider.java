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
import com.adauthenticator.model.ADGroup;
import com.adauthenticator.model.ADUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.security.ExternalGroupsProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Retrieve the groups for a given user.
 * 
 * @author Jiji_Sasidharan
 */
public class ADGroupsProvider extends ExternalGroupsProvider {

	private static final Logger LOG = LoggerFactory.getLogger(ADGroupsProvider.class);
    private ADUserRetriever adUserRetriever;

    public ADGroupsProvider(ADUserRetriever adUserRetriever) {
        this.adUserRetriever = adUserRetriever;
    }

    /**
     * Returns the groups associated with the user.
     * 
     * @username The user name.
     */
    @Override
    public Collection<String> doGetGroups(String username) {

        ADUser user = adUserRetriever.retrieveLoggedInUser();

        if (user != null) {
    		LOG.debug("{} belongs to groups: {}", username, user.getGroups());

            Collection<ADGroup> adGroups = user.getGroups();
            List<String> groups = new ArrayList<String>(adGroups.size());
            for (ADGroup adGroup: adGroups) {
                groups.add(adGroup.getCommonName());
            }

    		return groups;
    	}
        return null;
    }
}
