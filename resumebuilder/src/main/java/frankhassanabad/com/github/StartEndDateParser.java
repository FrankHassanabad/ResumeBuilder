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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper utility class that is typically used within Jasper's IReport for use
 * with the LinkedInAPI data.  What this parser does is take a start month,
 * start year, end month, end year, boolean of if it's the current date, a
 * date pattern, and then creates a date interval string.
 *
 * You can call this from within your jrxml with field variables like so:
 * <pre>
 * frankhassanabad.com.github.StartEndDateParser.parse($F{start-date-month}, $F{start-date-year},
 * $F{end-date-month}, $F{end-date-year}, $F{is-current}, "MMMMM yyyy");
 * </pre>
 * For example, if you passed into the fields the values of
 * <pre>
 *     start-date-month = 1
 *     start-date-year = 2011
 *     end-date-month = 2
 *     end-date-year = 2012
 *     is-current = false
 * </pre>
 * Your returned value would then be something like
 * <pre>
 * Janurary 1 2011 - Feburary 2012
 * </pre>
 */
public class StartEndDateParser {

    /**
     * This is the mechanism which parses a start month, start year, end month,
     * end year, boolean of if it's the current date, a date pattern, and then creates
     * a date interval string.
     * For example, if you passed into the variables the values of
     * <pre>
     *     startMonth = 1
     *     startYear = 2011
     *     endMonth = 2
     *     endYear = 2012
     *     is-current = false
     *     datePattern = MMMMM yyyy
     * </pre>
     * Your returned value would then be something like
     * <pre>
     * Janurary 1 2011 - Feburary 2012
     * </pre>
     * @param startMonth The start month between 1 and 12.
     * @param startYear The start year which is 4 digits such as 2011.
     * @param endMonth The end month between 1 and 12.
     * @param endYear The end year which is 4 digits such as 2012
     * @param isCurrent If this is true it will return the word "Present" instead of the end date.
     * @param datePattern The date pattern that should be in {@link SimpleDateFormat} format.
     * @return The string interval such as Janurary 1 2011 - Feburary 2012
     * @throws ParseException Throws this if one of the arguments has problems or the date pattern has issues.
     */
    public static String parse(String startMonth, String startYear, String endMonth, String endYear, String isCurrent, String datePattern) throws ParseException {
        boolean current = Boolean.parseBoolean(isCurrent);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        String startDateString;
        String endDateString;
        if (startMonth != null && startYear != null) {
            Date startDate = sdf.parse(startMonth + "/" + startYear);
            startDateString = new SimpleDateFormat(datePattern).format(startDate);
        } else {
            startDateString = "Unknown";
        }
        if (current) {
            endDateString = "Present";
        }
        else {
            if (endMonth != null && endYear != null) {
                Date endDate = sdf.parse(endMonth + "/" + endYear);
                endDateString = new SimpleDateFormat(datePattern).format(endDate);
            } else {
                endDateString = "Unknown";
            }
        }
        return startDateString + "-" + endDateString;
    }
}
