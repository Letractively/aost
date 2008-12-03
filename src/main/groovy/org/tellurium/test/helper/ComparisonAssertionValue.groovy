package org.tellurium.test.helper
/**
 * hold two parameters, expected and actual
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 20, 2008
 * 
 */
class ComparisonAssertionValue extends AssertionValue{

    private static final String EXPECTED = "Expected"
    private def expected

    private static final String ACTUAL = "Actual"
    private def actual

    public String toString() {
        final int typicalLength = 64
        final String avpSeparator = ": "
        final String fieldSeparator = ","
        final String fieldStart = " "

        StringBuilder sb = new StringBuilder(typicalLength)
        sb.append(EXPECTED).append(avpSeparator).append("\"" + expected + "\"").append(fieldSeparator)
        sb.append(fieldStart).append(ACTUAL).append(avpSeparator).append("\"" + actual + "\"").append(fieldSeparator)

        return sb.toString()
    }

}