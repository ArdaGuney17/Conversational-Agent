package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Greeting: State = state(Parent) {
    onEntry {
        furhat.say("Hello! My name is Matty, I will help you today finding your masters program")
        furhat.ask("Do you want help choosing your masters?")
    }

    onResponse<Yes> {
        furhat.say("Great!")
        furhat.ask("Do you already have something in mind?")

        onResponse<Yes> {
            furhat.say("Good to hear!")
            goto(MasterSelection) // User has something in mind
        }

        onResponse<No> {
            furhat.say("That is okay")
            goto(MasterSelection) // Change to bachelor
        }
    }

    onResponse<No> {
        furhat.say("No worries! Thank you for this short conversation")
        goto(Idle)
    }

}

