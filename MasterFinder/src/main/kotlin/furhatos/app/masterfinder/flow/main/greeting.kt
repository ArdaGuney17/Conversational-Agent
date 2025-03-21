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
        furhat.ask("Great!")
    }

    onResponse<No> {
        furhat.say("No worries! Thank you for this short conversation")
        goto(MasterSelection) // Now transitions to the MasterSelection state
    }

    onResponse<Yes> {
        furhat.ask("Do you already have something in mind?")
    }

    onResponse<Yes> {
        goto(MasterSelection) // Now transitions to the MasterSelection state
    }

    onResponse<No> {
        goto(MasterSelection) // Change for Bachelor class
    }



}

