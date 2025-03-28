package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.*

val End_Error: State = state(Parent) {

    onEntry {
        furhat.say("Unfortunately, the university does not have a suitable master's programme!")
        goto(Idle)
    }
}
