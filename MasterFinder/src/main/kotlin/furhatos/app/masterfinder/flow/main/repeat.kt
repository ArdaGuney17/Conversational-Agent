package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.*

val Repeat: State = state(Parent) {

    onEntry {
        furhat.say("Sorry, I did not understand you. Could you repeat that please?")
        reentry()
    }
}