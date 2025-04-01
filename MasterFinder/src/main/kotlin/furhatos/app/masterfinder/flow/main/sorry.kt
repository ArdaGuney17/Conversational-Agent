package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.*

val Sorry: State = state(Parent) {

    onEntry {
        furhat.say("Sorry, I do not know this. For more information take a look at the website of University of Twente or ask a person around me.")
        goto(Idle)
    }
}
