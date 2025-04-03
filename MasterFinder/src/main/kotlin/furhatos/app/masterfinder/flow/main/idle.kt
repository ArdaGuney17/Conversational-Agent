package furhatos.app.masterfinder.flow.main

import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onUserEnter
import furhatos.flow.kotlin.state

val Idle: State = state {
    onEntry {
        furhat.say{
            random {
                +"Okay, happy to have been of assistance"
                +"Good luck with your studies ${UserData.userName}"
                +"I really liked getting to know you, hope to see you again ${UserData.userName}"
                +"I really enjoyed our conversation, thank you for talking to me"
            }
        }
        furhat.say("Goodbye")
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Greeting)
    }

}
