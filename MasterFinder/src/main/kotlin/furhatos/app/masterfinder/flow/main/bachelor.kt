package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.

val Bachelor: State = state(Parent) {
    onEntry {
        furhat.say("Hello! I am the Bachelor guide. I can help you understand Bachelor's programs.")
        furhat.ask("Are you interested in pursuing a Bachelor's degree?")
    }

    onResponse<Yes> {
        furhat.say("That's wonderful! Choosing a Bachelor’s program is an exciting step.")
        furhat.ask("Do you already have a field of study in mind?")

        onResponse<Yes> {
            furhat.say("Great! I can help you refine your choice.")
            goto(MasterSelection) // Adjust this if there's a separate Bachelor selection
        }

        onResponse<No> {
            furhat.say("No problem! I can give you some recommendations.")
            goto(MasterSelection) // Adjust if there's a separate Bachelor-related state
        }
    }

    onResponse<No> {
        furhat.say("That's okay! If you ever need help, feel free to ask.")
    }
}
