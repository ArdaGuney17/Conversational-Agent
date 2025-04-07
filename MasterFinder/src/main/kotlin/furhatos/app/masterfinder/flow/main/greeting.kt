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
        furhat.say("Hello! My name is Matty, I can help you find your master's program")
        furhat.ask("What is your name?")
    }

    onResponse<Name> {
        val userInput = it.text
        val userInputSplit = userInput.split(" ")
        val name = userInputSplit[userInputSplit.size-1]
        UserData.userName = name

        furhat.say("Hello" + UserData.userName + ", nice to meet you!")
        goto(Greeting2)
    }

    onResponse {
        val userInput = it.text
        val name = userInput
        UserData.userName = name

        furhat.say("Hello" + UserData.userName + ", nice to meet you!")
        goto(Greeting2)
    }
}

val Greeting2: State = state(Parent) {
    onEntry {
        furhat.ask("Do you want help choosing your master's?")
    }

    onResponse<YesAnswer> {
        furhat.say("Great!")
        goto(AskIfSomethingInMind) // Transition to the next state
    }

    onResponse<NoAnswer> {
        furhat.say("No worries! Thank you for this short conversation")
        goto(Idle)
    }
}


// New state to ask about preferences
val AskIfSomethingInMind: State = state(Parent) {
    onEntry {
        furhat.ask("Do you already have something in mind " + UserData.userName + "?")
    }

    onResponse<YesAnswer> {
        furhat.say("Good to hear!")
        goto(MasterSelection) // Transition to MasterSelection
    }

    onResponse<NoAnswer> {
        furhat.say("That is okay.")
        goto(BachelorSelection) // Transition to BachelorSelection (or another state if needed)
    }
}



