# ProdPartsPDFBundler

User imports a csv with a list of string. Scanner assume list in the third column and has a header row. The program then searches through
the directory in which it was executed from for pdf files with a substring in their name that matches any of the items in the list and displays
whether they are found or note once complete. User can then export to a combined pdf.

Created using Java 10 and JavaFX. Known supported JRE 10.0.1. Has not be tested on java 11 with openJDK or higher.