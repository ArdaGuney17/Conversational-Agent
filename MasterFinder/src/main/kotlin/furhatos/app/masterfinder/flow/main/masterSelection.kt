package furhatos.app.masterfinder.flow.main

class masterSelection {
    val mastersSelection = listOf(
        MasterProgram(
            "Shaping Responsible Futures",
            "Engineering Technology",
            "September",
            true,
            "English",
            false,
            "Transdisciplinary approach with real-world projects",
            "Open for students with various backgrounds, motivation letter required",
            "Focuses on leadership and innovation in tackling global challenges"
        ),
        MasterProgram(
            "Applied Mathematics",
            "Electrical Engineering, Mathematics and Computer Science",
            "September, February",
            true,
            "English",
            false,
            "Two-year program specializing in mathematical modeling, data science, and optimization",
            "Requires a strong background in mathematics and/or computer science",
            "Opportunities in academia, finance, healthcare, logistics, and engineering"
        ),
        MasterProgram(
            "Applied Physics",
            "Technical Sciences",
            "September, February",
            true,
            "English",
            false,
            "Two-year research-based program exploring physics applications in various industries",
            "Requires a Bachelor in Physics or related field",
            "Careers in research, high-tech industry, and academia"
        )
    )

    // Furhat Skill
    class MasterFinderSkill : Skill() {
        override fun start() {
            Flow().run(Init)
        }
    }

    // Flow for starting the conversation
    val Init: State = state {
        onEntry {
            furhat.say("Hello! I can help you find information about master's programs at the University of Twente. Which master's are you interested in?")
            goto(SelectMaster)
        }
    }

    // Flow for selecting a master's program
    val SelectMaster: State = state {
        onResponse<MasterName> {
            val master = masters.find { it.name.equals(it.text, ignoreCase = true) }
            if (master != null) {
                furhat.say("The ${master.name} program is part of the ${master.faculty} faculty. Would you like to know about admission requirements, career prospects, or structure?")
                goto(MasterDetails(master))
            } else {
                furhat.say("I couldn't find that master's program. Please try again.")
                reentry()
            }
        }
    }

    // Flow for retrieving specific details
    fun MasterDetails(master: MasterProgram) = state {
        onResponse<AdmissionQuery> {
            furhat.say("The admission requirements for ${master.name} are: ${master.admissionRequirements}.")
            goto(ContinueOrEnd)
        }
        onResponse<CareerQuery> {
            furhat.say("Graduates of ${master.name} can pursue careers in: ${master.careerProspects}.")
            goto(ContinueOrEnd)
        }
        onResponse<StructureQuery> {
            furhat.say("The structure of ${master.name} is: ${master.structure}.")
            goto(ContinueOrEnd)
        }
    }

    // Flow to continue or end the conversation
    val ContinueOrEnd: State = state {
        onEntry {
            furhat.ask("Would you like to ask about another master's program?")
        }
        onResponse<Yes> {
            goto(SelectMaster)
        }
        onResponse<No> {
            furhat.say("Alright! Good luck with your decision!")
            goto(Idle)
        }
    }

}