AD-Authenticator-Sonar-Plugin
================

ad-authenticator-sonar-plugin is a plugin for the Sonar server to use Active Directory for authenticating and retrieval of user's details.

Usage
=====
Download ad-authenticator-sonar-plugin-x.x.x.jar and copy to the ${SONAR_HOME}/extensions/plugins directory.

In the ${SONAR_HOME}/conf/sonar.properties configuration file add the following properties:

    sonar.security.realm: AD',
    sonar.authenticator.createUsers: true',
    sonar.security.updateUserAttributes: true',

### Faster Searches

Sometime Active Directory searches can be made faster by adding additional search objects. Up to 5 additional search
objects can be added by setting additional properties in ${SONAR_HOME}/conf/sonar.properties configuration file. For example:

    sonar.ad.search.1: OU=User Accounts

Note: This will be specific to your Active Directory setup.

### Groups

If you want to use any of a user's Active Directory groups in Sonar, first create the group in Sonar with the same name
and when a user authenticates and is already assigned to an Active Directory group with the same name, the user will be
automatically added to the matching Sonar group.

Testing
=======
TODO

Bugs & Issues
=============
Please use GitHub issues to report bugs, features, or other problems.

License & Authors
=================
The project was adapted from sonar-ad-plugin developed by Jiji Sasidharan (https://github.com/programmingforliving/sonar-ad-plugin) to extract the 
core AD authentication logic. Thanks Jiji!

- Copyright (C) 2015 Keith Lyall
- Copyright (C) 2012-2014 Jiji Sasidharan (http://programmingforliving.com/)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
