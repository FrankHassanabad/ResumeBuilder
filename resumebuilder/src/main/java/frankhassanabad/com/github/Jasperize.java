package frankhassanabad.com.github;

import net.sf.jasperreports.engine.JRException;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Main program to call through Java in order to take a LinkedInProfile on disk and load it.  This will by default
 * look at "data/LinkedInResumes/linkedInProfileData.xml" for the XML of the profile from LinkedIn.  You have
 * the following options:
 *
 * <pre>
 * usage: Jasperize [OPTIONS] [InputJrxmlFile] [OutputExportFile]
 * -cl,--coverLetter   Utilizes a cover letter defined in coverletter.xml
 * -h,--help           Shows the help documentation
 * -v,--version        Shows the help documentation
 * </pre>
 *
 * The defaults if no options are given:
 * <pre>
 *     [OPTIONS] = (none)
 *     [InputJrxmlFile] data/jasperTemplates/resume1.jrxml
 *     [OutputExportFile] data/jasperOutput/linkedInResume.pdf
 * </pre>
 *
 * Example calls are:
 * Get help
 * <pre>
 * Jasperize -h
 * </pre>
 *
 * Show version
 * <pre>
 * Jasperize -v
 * </pre>
 *
 * Get a PDF output of a template
 * <pre>
 * Jasperize data/jasperTemplates/resume1.jrxml data/jasperOutput/linkedInResume.pdf
 * </pre>
 *
 * Get a PDF output of a template with attached cover letter
 * <pre>
 * Jasperize -cl data/jasperTemplates/resume1.jrxml data/jasperOutput/linkedInResume.pdf
 * </pre>
 */
public class Jasperize {

    /** Version of Jasperize */
    private static final String version = "1.0.0";

    /**
     * Main method to call through Java in order to take a LinkedInProfile on disk and load it.
     *
     * @param args command line arguments where the options are stored
     * @throws FileNotFoundException Thrown if any file its expecting cannot be found.
     * @throws JRException  If there's generic overall Jasper issues.
     * @throws ParseException If there's command line parsing issues.
     */
    public static void main(String[] args) throws FileNotFoundException, JRException, ParseException {

        Options options = new Options();
        options.addOption("h", "help", false, "Shows the help documentation");
        options.addOption("v", "version", false, "Shows the help documentation");
        options.addOption("cl", "coverLetter", false, "Utilizes a cover letter defined in coverletter.xml");
        CommandLineParser parser = new GnuParser();
        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Jasperize [OPTIONS] [InputJrxmlFile] [OutputExportFile]", options);
            System.exit(0);
        }
        if (cmd.hasOption("v")) {
            System.out.println("Version:" + version);
            System.exit(0);
        }
        boolean useCoverLetter = cmd.hasOption("cl");

        @SuppressWarnings("unchecked")
        List<String> arguments = cmd.getArgList();

        final String jrxmlFile;
        final String jasperOutput;
        if (arguments.size() == 3) {
            jrxmlFile = arguments.get(1);
            jasperOutput = arguments.get(2);
            System.out.println("*Detected* the command line arguments of:");
            System.out.println("    [InputjrxmlFile] " + jrxmlFile);
            System.out.println("    [OutputExportFile] " + jasperOutput);
        } else {
            System.out.println("Using the default arguments of:");
            jrxmlFile = "data/jasperTemplates/resume1.jrxml";
            jasperOutput = "data/jasperOutput/linkedInResume.pdf";
            System.out.println("    [InputjrxmlFile] " + jrxmlFile);
            System.out.println("    [OutputExportFile] " + jasperOutput);
        }

        final String compiledMasterFile;
        final String outputType;
        final String jrPrintFile;
        //Split the inputFile
        final String[] inputSplit = jrxmlFile.split("\\.");
        if(inputSplit.length != 2 || !(inputSplit[1].equalsIgnoreCase("jrxml"))) {
            //Error
            System.out.println("Your [InputjrxmlFile] (1st argument) should have a jrxml extension like such:");
            System.out.println("    data/jasperTemplates/resume1.jrxml");
            System.exit(1);
        }
        //Split the outputFile
        final String[] outputSplit = jasperOutput.split("\\.");
        if(outputSplit.length != 2) {
            //Error
            System.out.println("Your [OutputExportFile] (2nd argument) should have a file extension like such:");
            System.out.println("    data/jasperOutput/linkedInResume.pdf");
            System.exit(1);
        }

        File inputFile = new File(inputSplit[0]);
        String inputFileName = inputFile.getName();
        String inputFileParentPath = inputFile.getParent();

        File outputFile = new File(outputSplit[0]);
        String outputFileParentPath = outputFile.getParent();

        System.out.println("Compiling report(s)");
        compileAllJrxmlTemplateFiles(inputFileParentPath, outputFileParentPath);
        System.out.println("Done compiling report(s)");

        compiledMasterFile = outputFileParentPath + File.separator + inputFileName + ".jasper";
        jrPrintFile = outputFileParentPath + File.separator + inputFileName + ".jrprint";

        System.out.println("Filling report: " + compiledMasterFile);
        Reporting.fill(compiledMasterFile, useCoverLetter);
        System.out.println("Done filling reports");
        outputType = outputSplit[1];
        System.out.println("Creating output export file of: " + jasperOutput);
        if(outputType.equalsIgnoreCase("pdf")) {
            Reporting.pdf(jrPrintFile, jasperOutput);
        }
        if(outputType.equalsIgnoreCase("pptx")) {
            Reporting.pptx(jrPrintFile, jasperOutput);
        }
        if(outputType.equalsIgnoreCase("docx")) {
            Reporting.docx(jrPrintFile, jasperOutput);
        }
        if(outputType.equalsIgnoreCase("odt")) {
            Reporting.odt(jrPrintFile, jasperOutput);
        }
        if(outputType.equalsIgnoreCase("xhtml")) {
            Reporting.xhtml(jrPrintFile, jasperOutput);
        }
        System.out.println("Done creating output export file of: " + jasperOutput);
    }

    /**
     * Compiles all the jrxml template files into an output directory.  This does just a one directory
     * deep scan for files.
     * @param inputDirectory The input directory containing all the jrxml's.
     * @param outputDirectory The output directory to put all the .jasper files.
     * @throws JRException If there's any problems compiling a jrxml, this will get thrown.
     */
    private static void compileAllJrxmlTemplateFiles(String inputDirectory, String outputDirectory) throws JRException {
        //compile all .jrxml's from the JasperTemplates directory into the jasperOutput directory.
        File jrxmlInputFolder = new File(inputDirectory);
        File[] jrxmlFiles = jrxmlInputFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("jrxml");
            }
        });
        for (File jrxmlFileFile : jrxmlFiles) {
            String inputFile = jrxmlFileFile.getPath();
            String nameWithNoExtension = jrxmlFileFile.getName().split("\\.")[0];
            String outputFile = outputDirectory + File.separator + nameWithNoExtension + ".jasper";
            System.out.println("Compiling: " + jrxmlFileFile);
            Reporting.compile(inputFile, outputFile);
        }
    }
}
