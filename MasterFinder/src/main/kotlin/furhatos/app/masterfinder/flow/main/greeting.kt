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
        furhat.say("Hello! My name is Matty, I will help you today finding your master's program")
        furhat.ask("Do you want help choosing your master's?")
    }

    onResponse<Yes> {
        furhat.say("Great!")
        goto(AskIfSomethingInMind) // Transition to the next state
    }

    onResponse<No> {
        furhat.say("No worries! Thank you for this short conversation")
        goto(Idle)
    }
}

// New state to ask about preferences
val AskIfSomethingInMind: State = state(Parent) {
    onEntry {
        furhat.ask("Do you already have something in mind?")
    }

    onResponse<Yes> {
        furhat.say("Good to hear!")
        goto(MasterSelection) // Transition to MasterSelection
    }

    onResponse<No> {
        furhat.say("That is okay")
        goto(MasterSelection) // Transition to MasterSelection (or another state if needed)
    }
}



