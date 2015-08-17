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
package com.adauthenticator.sonar.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.ServerExtension;
import org.sonar.api.config.Settings;

/**
 * ADSettings - Setting for Active Directory.
 * <p>
 * This class holds
 * <ul>
 * <li>Domain Name for Active DirectoryØ Authentication</li>
 * <li>List of AD Authentication Provider configurations</li>
 * </ul>
 * The above configurations are automatically detected based on the FQN of the host where
 * the Sonar is running. The Domain name can also be configured in sonar.properties in case
 * the FQN of the host doesn't have the authentication domain name in it.
 *
 * @author Jiji_Sasidharan
 */
public class ADSettings implements ServerExtension {

    public static final String CONFIG_OVERRIDE_AD_DOMAIN = "sonar.ad.domain";
    public static final String CONFIG_SEARCH_OBJECT_1 = "sonar.ad.search.1";
    public static final String CONFIG_SEARCH_OBJECT_2 = "sonar.ad.search.2";
    public static final String CONFIG_SEARCH_OBJECT_3 = "sonar.ad.search.3";
    public static final String CONFIG_SEARCH_OBJECT_4 = "sonar.ad.search.4";
    public static final String CONFIG_SEARCH_OBJECT_5 = "sonar.ad.search.5";

    private static final Logger LOG = LoggerFactory.getLogger(ADSettings.class);

    private final String searchObject1;
    private final String searchObject2;
    private final String searchObject3;
    private final String searchObject4;
    private final String searchObject5;
    private final String dnsDomain;

    /**
     * Constructor
     *
     * @param settings The Sonar setting from the sonar.properties file.
     */
    @Properties({
            @Property(key = CONFIG_OVERRIDE_AD_DOMAIN, name = "AD domain to search for", defaultValue = ""),
            @Property(key = CONFIG_SEARCH_OBJECT_1, name = "AD object to speed up searches e.g. 'OU=User Accounts'", defaultValue = ""),
            @Property(key = CONFIG_SEARCH_OBJECT_2, name = "AD object to speed up searches e.g. 'OU=User Accounts'", defaultValue = ""),
            @Property(key = CONFIG_SEARCH_OBJECT_3, name = "AD object to speed up searches e.g. 'OU=User Accounts'", defaultValue = ""),
            @Property(key = CONFIG_SEARCH_OBJECT_4, name = "AD object to speed up searches e.g. 'OU=User Accounts'", defaultValue = ""),
            @Property(key = CONFIG_SEARCH_OBJECT_5, name = "AD object to speed up searches e.g. 'OU=User Accounts'", defaultValue = "")
    })
    public ADSettings(Settings settings) {
        dnsDomain = settings.getString(CONFIG_OVERRIDE_AD_DOMAIN);
        searchObject1 = settings.getString(CONFIG_SEARCH_OBJECT_1);
        searchObject2 = settings.getString(CONFIG_SEARCH_OBJECT_2);
        searchObject3 = settings.getString(CONFIG_SEARCH_OBJECT_3);
        searchObject4 = settings.getString(CONFIG_SEARCH_OBJECT_4);
        searchObject5 = settings.getString(CONFIG_SEARCH_OBJECT_5);

        LOG.debug("ADSettings.dnsDomain    : {}", dnsDomain);
        LOG.debug("ADSettings.searchObject1: {}", searchObject1);
        LOG.debug("ADSettings.searchObject2: {}", searchObject2);
        LOG.debug("ADSettings.searchObject3: {}", searchObject3);
        LOG.debug("ADSettings.searchObject4: {}", searchObject4);
        LOG.debug("ADSettings.searchObject5: {}", searchObject5);
    }

    /**
     * Return dnsDomain.
     *
     * @return the dnsDomain
     */
    public String getDnsDomain() {
        return dnsDomain;
    }

    /**
     * Returns the first optionally configured search object that can be used to speed up AD searches.
     *
     * @return the AD search object
     */
    public String getSearchObject1() {
        return searchObject1;
    }

    /**
     * Returns the second optionally configured search object that can be used to speed up AD searches.
     *
     * @return the AD search object
     */
    public String getSearchObject2() {
        return searchObject2;
    }

    /**
     * Returns the third optionally configured search object that can be used to speed up AD searches.
     *
     * @return the AD search object
     */
    public String getSearchObject3() {
        return searchObject3;
    }

    /**
     * Returns the fourth optionally configured search object that can be used to speed up AD searches.
     *
     * @return the AD search object
     */
    public String getSearchObject4() {
        return searchObject4;
    }

    /**
     * Returns the fifth optionally configured search object that can be used to speed up AD searches.
     *
     * @return the AD search object
     */
    public String getSearchObject5() {
        return searchObject5;
    }
}
