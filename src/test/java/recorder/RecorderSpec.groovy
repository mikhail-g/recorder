package recorder

import base.BaseSpecification
import recorder.sound.SoundRecorder

/**
 * Recorder Specification
 * Created by Mykhailo on 014 14.11.16.
 */
class RecorderSpec extends BaseSpecification {

    void setup() {

    }

    void cleanup() {

    }

    def "Init"() {
        expect:
//            RecorderImpl.instance.init()
            def soundMock = Mock(SoundRecorder)
            soundMock
    }

    def "Record"() {

    }

    def "Stop"() {

    }

    def "Quit"() {

    }

    def "GetStatus"() {

    }
}
