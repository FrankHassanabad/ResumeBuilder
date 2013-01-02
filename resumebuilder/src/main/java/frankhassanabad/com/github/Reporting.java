package frankhassanabad.com.github;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRXmlUtils;
import org.w3c.dom.Document;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Adapted from the Jasper Examples, these are static helper functions
 * for compiling, filling, and outputting reports.
 */
public class Reporting {

    /**
     * Compiles a report given the input jrxml and the output compiled jasper file.
     * <br/>
     * Example:
     * <pre>
     * compile("data/jasperTemplates/example.jrxml" "data/jasperOutput/example.jasper")
     * </pre>
     * @param jrxml The input jrxml file
     * @param jasperFile The compiled jasper file to output
     */
    public static void compile(String jrxml, String jasperFile) throws JRException {
        JasperCompileManager.compileReportToFile(jrxml, jasperFile);
    }

    /**
     * Fills a jasper file and outputs the fill file.
     * <br/>
     * Example:
     * <pre>
     * fill("data/jasperOutput/example.jasper")
     * </pre>
     * @param jasperFile The jasper file to fill and create the jrprint file
     * @param useCoverLetter Set to true if you want to output a cover letter, otherwise false.
     */
    @SuppressWarnings("unchecked")
    public static void fill(String jasperFile, boolean useCoverLetter) throws JRException {

        //Get the xml data from the file system
        Map params = new HashMap(7);
        Document personXML = JRXmlUtils.parse(JRLoader.getLocationInputStream("data/linkedInResumes/linkedInProfileData.xml"));
        Document coverLetterXML = JRXmlUtils.parse(JRLoader.getLocationInputStream("data/linkedInResumes/coverletter.xml"));
        params.put("COVERLETTER_XML_DATA_DOCUMENT", coverLetterXML);
        params.put("USE_COVER_LETTER", useCoverLetter);
        params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, personXML);
        params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
        params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
        params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
        params.put(JRParameter.REPORT_LOCALE, Locale.US);

        //File the file
        JasperFillManager.fillReportToFile(jasperFile, params);
    }

    /**
     * Outputs a PDF file given a jprintFile.
     * <br/>
     * Example:
     * <pre>pdf("data/jasperOutput/example.jrprint", "data/jasperOutput/example.pdf")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The PDF output file.
     */
    public static void pdf(String jprintFile, String destinationFile) throws JRException {
        JasperExportManager.exportReportToPdfFile(jprintFile, destinationFile);
    }

    /**
     * Outputs a PDF file given a jprintFile.
     * <br/>
     * Example:
     * <pre>pdf("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void pdf(String jprintFile) throws JRException {
        JasperExportManager.exportReportToPdfFile(jprintFile);
    }

    /**
     * Outputs a rtf file given a jprintFile.
     * <br/>
     * Example:
     * <pre>rtf("data/jasperOutput/example.jrprint", "data/jasperOutput/example.rtf")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The rtf output file.
     */
    public static void rtf(String jprintFile, String destinationFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);

        JRRtfExporter exporter = new JRRtfExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationFile);

        exporter.exportReport();

    }

    /**
     * Outputs a rtf file given a jprintFile.
     * <br/>
     * Example:
     * <pre>rtf("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void rtf(String jprintFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);

        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".rtf");

        JRRtfExporter exporter = new JRRtfExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

        exporter.exportReport();

    }

    /**
     * Outputs a rtf file given a jprintFile.
     * <br/>
     * Example:
     * <pre>xml("data/jasperOutput/example.jrprint", "data/jasperOutput/example.xml")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The xml output file.
     */
    public static void xml(String jprintFile, String destinationFile) throws JRException {
        JasperExportManager.exportReportToXmlFile(jprintFile, destinationFile, false);
    }

    /**
     * Outputs a xml file given a jprintFile.
     * <br/>
     * Example:
     * <pre>xml("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void xml(String jprintFile) throws JRException {
        JasperExportManager.exportReportToXmlFile(jprintFile, false);
    }

    /**
     * Outputs a xml embed given a jprintFile.
     * <br/>
     * Example:
     * <pre>xmlEmbed("data/jasperOutput/example.jrprint", "data/jasperOutput/example.xml")</pre>
     * @param jprintFile The jprint file.
     * @param destinationfile The xml output file.
     */
    public static void xmlEmbed(String jprintFile, String destinationfile) throws JRException {
        JasperExportManager.exportReportToXmlFile(jprintFile, destinationfile, true);
    }

    /**
     * Outputs a xml file given a jprintFile.
     * <br/>
     * Example:
     * <pre>xmlEmbed("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void xmlEmbed(String jprintFile) throws JRException {
        JasperExportManager.exportReportToXmlFile(jprintFile, true);
    }

    /**
     * Outputs a html file given a jprintFile.
     * <br/>
     * Example:
     * <pre>html("data/jasperOutput/example.jrprint", "data/jasperOutput/example.html")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The html output file.
     */
    public static void html(String jprintFile, String destinationFile) throws JRException {
        JasperExportManager.exportReportToHtmlFile(jprintFile, destinationFile);
    }

    /**
     * Outputs a html file given a jprintFile.
     * <br/>
     * Example:
     * <pre>html("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void html(String jprintFile) throws JRException {
        JasperExportManager.exportReportToHtmlFile(jprintFile);
    }

    /**
     * Outputs a xls file given a jprintFile.
     * <br/>
     * Example:
     * <pre>xls("data/jasperOutput/example.jrprint", "data/jasperOutput/example.xls")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The xls output file.
     */
    public static void xls(String jprintFile, String destinationFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        JRXlsExporter exporter = new JRXlsExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationFile);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

        exporter.exportReport();
    }

    /**
     * Outputs a xls file given a jprintFile.
     * <br/>
     * Example:
     * <pre>xls("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void xls(String jprintFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".xls");

        JRXlsExporter exporter = new JRXlsExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

        exporter.exportReport();

    }

    /**
     * Outputs a jxl file given a jprintFile.
     * <br/>
     * Example:
     * <pre>jxl("data/jasperOutput/example.jrprint", "data/jasperOutput/example.jxl")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The jxl output file.
     */
    public static void jxl(String jprintFile, String destinationFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        JExcelApiExporter exporter = new JExcelApiExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationFile);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

        exporter.exportReport();

    }

    /**
     * Outputs a jxl file given a jprintFile.
     * <br/>
     * Example:
     * <pre>xls("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void jxl(String jprintFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".jxl.xls");

        JExcelApiExporter exporter = new JExcelApiExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

        exporter.exportReport();

    }

    /**
     * Outputs a csv file given a jprintFile.
     * <br/>
     * Example:
     * <pre>csv("data/jasperOutput/example.jrprint", "data/jasperOutput/example.csv")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The csv output file.
     */
    public static void csv(String jprintFile, String destinationFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        JRCsvExporter exporter = new JRCsvExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationFile);

        exporter.exportReport();

    }

    /**
     * Outputs a csv file given a jprintFile.
     * <br/>
     * Example:
     * <pre>csv("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void csv(String jprintFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".csv");

        JRCsvExporter exporter = new JRCsvExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

        exporter.exportReport();

    }

    /**
     * Outputs a odt file given a jprintFile.
     * <br/>
     * Example:
     * <pre>odt("data/jasperOutput/example.jrprint", "data/jasperOutput/example.odt")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The odt output file.
     */
    public static void odt(String jprintFile, String destinationFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        JROdtExporter exporter = new JROdtExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationFile);

        exporter.exportReport();

    }

    /**
     * Outputs a odt file given a jprintFile.
     * <br/>
     * Example:
     * <pre>odt("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void odt(String jprintFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".odt");

        JROdtExporter exporter = new JROdtExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

        exporter.exportReport();

    }

    /**
     * Outputs a ods file given a jprintFile.
     * <br/>
     * Example:
     * <pre>ods("data/jasperOutput/example.jrprint", "data/jasperOutput/example.ods")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The ods output file.
     */
    public static void ods(String jprintFile, String destinationFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        JROdsExporter exporter = new JROdsExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationFile);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

        exporter.exportReport();

    }

    /**
     * Outputs a ods file given a jprintFile.
     * <br/>
     * Example:
     * <pre>ods("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void ods(String jprintFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".ods");

        JROdsExporter exporter = new JROdsExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

        exporter.exportReport();

    }

    /**
     * Outputs a docx file given a jprintFile.
     * <br/>
     * Example:
     * <pre>docx("data/jasperOutput/example.jrprint", "data/jasperOutput/example.docx")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The docx output file.
     */
    public static void docx(String jprintFile, String destinationFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        JRDocxExporter exporter = new JRDocxExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationFile);

        exporter.exportReport();

    }

    /**
     * Outputs a docx file given a jprintFile.
     * <br/>
     * Example:
     * <pre>docx("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void docx(String jprintFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".docx");

        JRDocxExporter exporter = new JRDocxExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

        exporter.exportReport();

    }

    /**
     * Outputs a xlsx file given a jprintFile.
     * <br/>
     * Example:
     * <pre>xlsx("data/jasperOutput/example.jrprint", "data/jasperOutput/example.xlsx")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The xlsx output file.
     */
    public static void xlsx(String jprintFile, String destinationFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        JRXlsxExporter exporter = new JRXlsxExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationFile);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

        exporter.exportReport();

    }

    /**
     * Outputs a xlsx file given a jprintFile.
     * <br/>
     * Example:
     * <pre>xlsx("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void xlsx(String jprintFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".xlsx");

        JRXlsxExporter exporter = new JRXlsxExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

        exporter.exportReport();

    }

    /**
     * Outputs a pptx file given a jprintFile.
     * <br/>
     * Example:
     * <pre>pptx("data/jasperOutput/example.jrprint", "data/jasperOutput/example.pptx")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The pptx output file.
     */
    public static void pptx(String jprintFile, String destinationFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        JRPptxExporter exporter = new JRPptxExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationFile);

        exporter.exportReport();

    }

    /**
     * Outputs a pptx file given a jprintFile.
     * <br/>
     * Example:
     * <pre>pptx("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void pptx(String jprintFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".pptx");

        JRPptxExporter exporter = new JRPptxExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

        exporter.exportReport();

    }

    /**
     * Outputs a xhtml file given a jprintFile.
     * <br/>
     * Example:
     * <pre>pptx("data/jasperOutput/example.jrprint", "data/jasperOutput/example.xhtml")</pre>
     * @param jprintFile The jprint file.
     * @param destinationFile The xhtml output file.
     */
    public static void xhtml(String jprintFile, String destinationFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        JRXhtmlExporter exporter = new JRXhtmlExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationFile);

        exporter.exportReport();

    }

    /**
     * Outputs a xhtml file given a jprintFile.
     * <br/>
     * Example:
     * <pre>xhtml("data/jasperOutput/example.jrprint")</pre>
     * @param jprintFile The jprint file.
     */
    public static void xhtml(String jprintFile) throws JRException {
        File sourceFile = new File(jprintFile);

        JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".x.html");

        JRXhtmlExporter exporter = new JRXhtmlExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

        exporter.exportReport();
    }
}
