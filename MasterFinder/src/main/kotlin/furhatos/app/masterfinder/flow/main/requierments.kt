package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Requierements: State = state(Parent) {

    onEntry {
        //furhat.say("The requirements are ...") // Adjust this with actual requirements
        furhat.ask("Do you think you fulfill all the requirements?")
    }

    onResponse<Yes> {
        furhat.say("Great! You can start to apply. The application period is from ... to ...") // Adjust with actual dates
        goto(General)
    }

    onResponse<No> {
        furhat.ask("Do you want more information about the pre-master?")

        onResponse<Yes> {
            furhat.say("The pre-master program ...") // Provide details about the pre-master
            goto(General)
        }

        onResponse<No> {
            goto(General)
        }
    }
}
