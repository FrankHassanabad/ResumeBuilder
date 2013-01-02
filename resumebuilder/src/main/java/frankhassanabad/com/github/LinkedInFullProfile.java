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

