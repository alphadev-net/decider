project.modules.each {
    try {
        def manifestFile = new File("${project.basedir}/AndroidManifest.xml")
        def ns = new groovy.xml.Namespace(
                                        "http://schemas.android.com/apk/res/android", "ns")
        def parser = new groovy.util.XmlParser(false, true)
        def rootNode = parser.parse(manifestFile)
        def attributes = rootNode.attributes()
        attributes[ns.versionName] = "${project.version}"
        def writer = new groovy.io.GroovyPrintWriter(manifestFile)
        writer.println('&lt;?xml version="1.0" encoding="UTF-8"?&gt;')
        def xmlWriter = new groovy.util.XmlNodePrinter(writer)
        xmlWriter.setPreserveWhitespace(false)
        xmlWriter.setNamespaceAware(true)
        xmlWriter.print(rootNode)
        println('Replaced Version in AndroidManifest.xml.')
    } catch (FileNotFoundException e) {
        println('No AndroidManifest.xml file found. Skipping version update.')
        println('Probably not an Android project, but a library.')
        println('Skipping version update.')
    }
}