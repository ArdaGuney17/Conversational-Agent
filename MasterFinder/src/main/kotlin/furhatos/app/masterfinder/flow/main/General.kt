package furhatos.app.masterfinder.flow.main

import sun.java2d.loops.FillRect.General

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.Intent
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.util.Language

class AskAboutMasters: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Tell me about master's programs", "I want to know about the Master programs", "What Master programs are available?")
    }
}

val General: State = state {
    onEntry {
        furhat.ask{
            random {
                +"Is there anything else I can help you with?"
                +"Do you have any other questions?"
                +"Anything else I can do for you?"
                +"Before we wrap up, is there anything else I can assist you with?"
            }
        }
    }

    onResponse<Yes> {
        goto(FollowUpQuestion) // Transition to MasterSelection
    }

    onResponse<No> {
        furhat.say("Okay, happy to have been of assistance")
        goto(Idle)
    }
}

val FollowUpQuestion: State = state {
    onEntry {
        furhat.ask{
            random {
                +"What is your question?"
                +"What else can I help you with?"
            }
        }
    }

    onResponse<AskAboutMasters> {
        goto(MasterSelection)
    }

}