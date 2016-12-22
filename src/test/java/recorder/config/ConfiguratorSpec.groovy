package recorder.config

import base.BaseSpecification

import static org.hamcrest.CoreMatchers.notNullValue
import static spock.util.matcher.HamcrestSupport.that

/**
 * Specification for Configurator class
 *
 * Created by Mykhailo on 014 14.11.16.
 */
class ConfiguratorSpec extends BaseSpecification {

    def "GetOutputFile"() {
        given: "an output folder"
            def outputFolder = Configurator.getOutputFolderUnchecked()
        expect: "the folder is not exist on startup"
            !outputFolder.exists()
        when: "an output file is queried"
            def file = Configurator.getNextOutputFile()
        then: "the output folder is created on the first query to the output file"
            outputFolder.exists()
        expect: "file is not null and file name matches to the pattern"
            that file, notNullValue()
            file.name ==~ /\w*_\d{4,8}_\d{3}\.\w{2,4}/
        cleanup: "delete the output folder after test"
            outputFolder.delete()
    }

    def "GetFileType"() {
        expect: "a file type is not null"
            that Configurator.getFileType(), notNullValue()
    }

    def "GetAudioFormat"() {
        expect: "an audio format is not null"
            that Configurator.getAudioFormat(), notNullValue()
    }
}
