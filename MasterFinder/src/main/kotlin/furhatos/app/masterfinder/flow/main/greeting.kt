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
        furhat.say("Hello! My name is Matty, I will help you today finding your mas ters program?")
        furhat.ask("Do you already have something in mind?")
    }

    onResponse<Yes> {
        furhat.say("Hello World! ")
    }

    onResponse<No> {
        furhat.say("Ok.")
    }

}

