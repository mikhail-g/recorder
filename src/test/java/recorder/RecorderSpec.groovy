package recorder

import base.BaseSpecification
import recorder.sound.RecordTask

import java.lang.reflect.Field

/**
 * Recorder Specification
 * Created by Mykhailo on 014 14.11.16.
 */
class RecorderSpec extends BaseSpecification {

    def recorder = RecorderImpl.instance
    def clientMock = Mock(RecordTask)

    void setup() {
        Field clientField = RecorderImpl.getDeclaredField("recordTask")
        clientField.setAccessible(true)
        clientField.set(recorder, clientMock)
    }

    void cleanup() {
    }

    def "Record"() {
        when:
            recorder.record()
        then:
            1 * clientMock.lineAvailable
            1 * clientMock.run()
    }

    def "Stop"() {

    }

    def "Quit"() {

    }

    def "GetStatus"() {

    }
}
