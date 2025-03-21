package furhatos.app.masterfinder.flow.main

import sun.java2d.loops.FillRect.General

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val General: State = state {
    onEntry {
        furhat.ask("Is there anything else I can help you with?")
    }

    

}