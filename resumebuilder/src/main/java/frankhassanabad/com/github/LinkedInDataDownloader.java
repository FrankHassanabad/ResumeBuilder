package frankhassanabad.com.github;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.scribe.builder.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

/**
 * Main program to call through Java in order to download a LinkedIn profile to disk.  This will by default
 * download the current user's profile data from LinkedIn and save it out as an XML file at
 * <pre>
 * data/linkedInResumes/linkedInProfileData.xml.
 * </pre>
 *
 * When the program is launched it uses an interactive prompt to gather the LinkedIn API key, secret API, and
 * then finally the verifier code
 *
 * The interactive prompt looks like
 * <pre>
 * Enter your LinkedIn API Key:
 * >> (enter your LinkedIn API key)
 *
 * Enter your LinkedIn API Secret Key:
 * >> (enter your LinkedIn API secret key)
 *
 * You need to authorize LinkedInDataDownloader by copying the LinkedIn URL into a browser:
 * https://api.linkedin.com/uas/oauth/authorize?oauth_token=c445a33c-1150-41ea-8451-b844b90ffa41
 * And paste the verifier here
 * >> (visit the web site and then enter the verifier code here)
 * Writing the output of your profile into data/linkedInResumes/linkedInProfileData.xml
 * Wrote the output of your profile into data/linkedInResumes/linkedInProfileData.xml
 * </pre>
 */
public class LinkedInDataDownloader
{

    /**
     * These are all the LinkedIn fields that you have to have in order to get the full profile
     * data for constructing a resume.
     */
    private static final String resumeFields =
            "(id,first-name,last-name,positions,skills,picture-url,site-standard-profile-request," +
                    "educations,phone-numbers,main-address,email-address,headline,summary,interests," +
                    "public-profile-url)";

    /** The REST API for getting the profile data */
    private static final String PROFILE_RESOURCE_URL = "http://api.linkedin.com/v1/people/~:" + resumeFields;


    /**
     * Main program to call through Java in order to download a LinkedIn profile to disk.
     *
     * @param args Program arguments that are currently used.
     * @throws FileNotFoundException Thrown if any file its expecting cannot be found.
     */
    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(System.in);

        //Enter your LinkedIn API Key
        System.out.println("Enter your LinkedIn API Key:");
        System.out.print(">>");
        String apiKey = in.nextLine();

        //Enter your LinkedIn Secret Key
        System.out.println("Enter your LinkedIn API Secret Key:");
        System.out.print(">>");
        String apiSecretKey = in.nextLine();

        //You have to past in your API key and your API Secret
        //key below that you obtain for signing up with LinkedIN
        //API service.
        OAuthService service = new ServiceBuilder()
                .provider(LinkedInFullProfile.class)
                .apiKey(apiKey)
                .apiSecret(apiSecretKey)
                .build();

        // Obtain the Request Token
        Token requestToken = service.getRequestToken();
        System.out.println();
        System.out.println("You need to authorize LinkedInDataDownloader by copying the LinkedIn URL into a browser:");
        System.out.println(service.getAuthorizationUrl(requestToken));
        System.out.println("And paste the verifier here");
        System.out.print(">>");
        Verifier verifier = new Verifier(in.nextLine());
        System.out.println();

        // Trade the Request Token and Verifier for the Access Token
        Token accessToken = service.getAccessToken(requestToken, verifier);

        // Get our full LinkedInProfile and save it to a file
        OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_RESOURCE_URL);
        service.signRequest(accessToken, request);
        Response response = request.send();
        String xmlString = response.getBody();
        System.out.println("Writing the output of your profile into data/linkedInResumes/linkedInProfileData.xml");
        PrintWriter out = new PrintWriter("data/linkedInResumes/linkedInProfileData.xml");
        out.println(xmlString);
        out.close();
        System.out.println("Wrote the output of your profile into data/linkedInResumes/linkedInProfileData.xml");

    }
}
