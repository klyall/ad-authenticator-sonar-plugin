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

import com.adauthenticator.ADAuthenticator;
import com.adauthenticator.ADAuthenticatorFactory;
import com.adauthenticator.sonar.plugin.ADSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.security.*;

/**
 * ADSecurityRealm - SecurityRealm implementation for Sonar AD Plugin.
 * 
 * @author Jiji_Sasidharan
 */
public class ADSecurityRealm extends SecurityRealm {

    public String SECURITY_REALM_NAME = "AD";

    private static final Logger LOG = LoggerFactory.getLogger(ADSecurityRealm.class);
    
    private ADSettings settings;
    private Authenticator authenticator;
    private ExternalUsersProvider externalUserProvider;
    private ExternalGroupsProvider externalGroupsProvider;

    public ADSecurityRealm(ADSettings settings) {
        this.settings = settings;
    }
    
    /**
     * Returns SecurityRealm name for Sonar AD Plugin.
     * 
     * The Sonar AD Plugin should be configured in <code>sonar.properties</code>
     * with <code>sonar.security.realm: AD </code>
     * 
     * @return SecurityRealm name
     */
    @Override
    public String getName() {
        return SECURITY_REALM_NAME;
    }

    /**
     * Initialize the AD Security Realm.
     */
    @Override
    public void init() {
        ADAuthenticator adAuthenticator = createADAuthenticator();
        UserDetails userDetails         = new UserDetails();
        externalUserProvider            = new ADUsersProvider(adAuthenticator, userDetails);
        authenticator                   = new SonarADAuthenticator(adAuthenticator, externalUserProvider);
        externalGroupsProvider          = new ADGroupsProvider(adAuthenticator);

        LOG.info("ADSecurityRealm initialized successfully.");
    }

    @Override
    public Authenticator doGetAuthenticator() {
        return authenticator;
    }

    @Override
    public ExternalUsersProvider getUsersProvider() {
        return externalUserProvider;
    }

    @Override
    public ExternalGroupsProvider getGroupsProvider() {
        return externalGroupsProvider;
    }

    private ADAuthenticator createADAuthenticator() {
        ADAuthenticator authenticator;

        if (settings.getDnsDomain() != null) {
            authenticator = ADAuthenticatorFactory.newInstance(settings.getDnsDomain());
        } else {
            authenticator = ADAuthenticatorFactory.newInstance();
        }

        if (settings.getSearchObject1() != null && !settings.getSearchObject1().equals("")) {
            authenticator.addSearchObject(settings.getSearchObject1());
        }

        if (settings.getSearchObject2() != null && !settings.getSearchObject2().equals("")) {
            authenticator.addSearchObject(settings.getSearchObject2());
        }

        if (settings.getSearchObject3() != null && !settings.getSearchObject3().equals("")) {
            authenticator.addSearchObject(settings.getSearchObject3());
        }

        if (settings.getSearchObject4() != null && !settings.getSearchObject4().equals("")) {
            authenticator.addSearchObject(settings.getSearchObject4());
        }

        if (settings.getSearchObject5() != null && !settings.getSearchObject5().equals("")) {
            authenticator.addSearchObject(settings.getSearchObject5());
        }

        return authenticator;
    }
}
