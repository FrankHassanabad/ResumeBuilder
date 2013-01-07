ResumeBuilder
=============

Builds a resume from your LinkedIn profile using [LinkedIn's API](https://developer.linkedin.com/apis) and 
[JasperSoft's Business Intelligence tools](http://www.jaspersoft.com/).  This is accomplished through two tools
developed here.  The first tool being LinkedInDownloader and the second being Jasperize.  As the names imply, 
LinkedInDownloader downloads your profile data and Jasperize builds your resume for you.  This seperate two stage 
process allows you to fiddle and customize your LinkedIn data before automatically generating your resume.

Features are

* Downloads your LinkedIn profile data
* Generates your resume using a JasperSoft template
* Optionally includes a cover letter with your resume
* Supports several formats which are PDF, DOCX, PPTX, ODT, XHTML

Examples
=============

Example resumes in different output formats

[Adobe Acrobat (pdf)] (https://github.com/FrankHassanabad/ResumeBuilder/blob/master/resumebuilder/data/jasperOutput/linkedInResume.pdf?raw=true)  
[Microsoft word (docx)] (https://github.com/FrankHassanabad/ResumeBuilder/blob/master/resumebuilder/data/jasperOutput/linkedInResume.docx?raw=true)  
[Microsoft power point (pptx)] (https://github.com/FrankHassanabad/ResumeBuilder/blob/master/resumebuilder/data/jasperOutput/linkedInResume.pptx?raw=true)  

Installing
=============

You can install the source through master which is typically fairly stable or through a tag.  
The benfits of pulling from a tag is that you have a version number you can associate with any problems you might 
encounter.  Also, you know when there's a new version as I will always push new versions out through the tags.

Clone the repository ([Install git if you haven't already](https://help.github.com/articles/set-up-git))

```
git clone https://github.com/FrankHassanabad/ResumeBuilder.git
```

Either build from master or switch to a tag and build with that.  I recommend using the latest tag and building from 
that so that if you find an issue you can report against that tagged version.  I will use a version as an example 
but it might not be the latest version.  For the latest tag see 
[tags](https://github.com/FrankHassanabad/ResumeBuilder/tags).

```
git checkout resumebuilder-parent-1.0.0
```

Build using maven ([Install maven if you haven't already](http://maven.apache.org/download.cgi))

```
mvn package
```

Running LinkedInDataDownloader
=============

Open a DOS command line window to your main ResumeBuilder directory.  From there, switch to the distribution directory.  
Note that the directory versions _are going to be different_ depending on if you're using a tag version or master 
version.  Below I assume a tag version of 1.0.1.

```
cd distribution/target/ResumeBuilder-1.0.1/ResumeBuilder-1.0.1
```

From within the above distribution directory you can run LinkedInDownloader to download your profile XML 
information into the diretory of data/linkedInResumes.  However first, you have to request a set of
[LinkedIn API keys] (https://www.linkedin.com/secure/developer) from you account.

```
LinkedInDataDownloader.bat
```

When prompted enter your API key

```
Enter your LinkedIn API Key:
>> (Enter your api key here)
```

When the next prompt occurs, enter your API secert key

```
Enter your LinkedIn API Secret Key:
>> (Enter your secret api key)
```

When the final prompt comes up to get the verification token, visit the website, enter your LinkedIn credentials 
and then put in the verification key

```
You need to authorize LinkedInDataDownloader by copying the LinkedIn URL into a browser:
https://api.linkedin.com/uas/oauth/authorize?oauth_token=xxxxxx-xxxx-xxxx-xxxx-xxxxxx
And paste the verifier here
>> (Enter your verification code)
```

And then you should see the program output of download your LinkedInProfile like so 
```
Writing the output of your profile into data/linkedInResumes/linkedInProfileData.xml
Wrote the output of your profile into data/linkedInResumes/linkedInProfileData.xml
```

Running Jasperize
=============



