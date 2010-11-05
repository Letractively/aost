<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <!-- imports the original docbook stylesheet -->
  <xsl:import href="urn:docbkx:stylesheet"/>

  <!-- set bellow all your custom xsl configuration -->



  <!--
    Important links:
    - http://www.sagehill.net/docbookxsl/
    - http://docbkx-tools.sourceforge.net/
  -->
    <xsl:attribute-set name="monospace.verbatim.properties">
      <xsl:attribute name="font-size">
        <xsl:choose>
          <xsl:when test="processing-instruction('db-font-size')"><xsl:value-of
               select="processing-instruction('db-font-size')"/></xsl:when>
          <xsl:otherwise>inherit</xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>
    </xsl:attribute-set>
 
</xsl:stylesheet>