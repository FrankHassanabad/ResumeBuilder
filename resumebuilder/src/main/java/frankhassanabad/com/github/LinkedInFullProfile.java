/*
 * ResumeBuilder.  Builds a resume from your LinkedIn profile using JasperSoft's tools.
 * Copyright (C) 2013, Frank Hassanabad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package frankhassanabad.com.github;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

/**
 * I implement my own DefaultApi10a so that I can add
 * more security requests through the {@link #getRequestTokenEndpoint()}.
 */
public class LinkedInFullProfile extends DefaultApi10a {

    /** The authorize url */
    private static final String AUTHORIZE_URL = "https://api.linkedin.com/uas/oauth/authorize?oauth_token=%s";

    /**
     * Returns the REST URL for access token endpoint.
     * @return The REST URL for access token endpoint.
     */
    @Override
    public String getAccessTokenEndpoint()
    {
        return "https://api.linkedin.com/uas/oauth/accessToken";
    }

    /**
     * Returns the REST URL for request token endpoint.  This is where I added
     * my own additional security requests for full profile, email address, and
     * contact information.
     * @return The REST URL for request token endpoint.
     */
    @Override
    public String getRequestTokenEndpoint()
    {
        return "https://api.linkedin.com/uas/oauth/requestToken?scope=r_fullprofile,r_emailaddress,r_contactinfo";
    }

    /**
     * Return the authorization url
     * @param requestToken The request token
     * @return The authorization url
     */
    @Override
    public String getAuthorizationUrl(Token requestToken)
    {
        return String.format(AUTHORIZE_URL, requestToken.getToken());
    }
}

