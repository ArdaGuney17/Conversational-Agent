package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.Intent
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.util.Language

class NoBachelor: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I do not have a bachelor degree", "I am not a student")
    }
}

class Bachelors: Intent() {
    override fun getExamples(lang: Language): List<String> {
        // Static list of Bachelor programs at UT
        val bachelorStudies = listOf( //needs to be updated
            "Applied Mathematics",
            "Biomedical Engineering",
            "Civil Engineering",
            "Electrical Engineering",
            "Industrial Engineering and Management",
            "Mechanical Engineering",
            "Philosophy of Science, Technology, and Society",
            "Psychology",
            "Sustainable Energy Technology",
            "Business Information Technology",
            "International Business Administration",
            "Technical Computer Science",
            "Creative Technology",
            "Advanced Technology",
            "Health Sciences",
            "Public Administration",
            "Design, Production and Management"
        )
        return bachelorStudies
    }

    // This method processes the user's input and stores the study program
    fun processUserInput(userInput: String) {
        val bachelorStudies = getExamples(Language.ENGLISH_GB)

        for (study in bachelorStudies) {
            if (userInput.contains(study, ignoreCase = true)) {
                // Save the study program to the UserData singleton
                UserData.userStudyProgram = study
                break
            }
        }

        // Log the study program (just for demo)
        println("User is studying: ${UserData.userStudyProgram}")
    }
}

class Interests: Intent() {
    override fun getExamples(lang: Language): List<String> {
        // Static list of interests
        val interests = listOf(
                "mathematics",
                "physics",
                "biomedical engineering",
                "business and management",
                "information technology",
                "chemical engineering",
                "civil engineering",
                "communication science",
                "computer science",
                "construction management",
                "education and learning",
                "electrical engineering",
                "embedded systems",
                "environmental science",
                "european studies",
                "health and medicine",
                "humanitarian engineering",
                "industrial design",
                "industrial engineering",
                "human-computer interaction",
                "mechanical engineering",
                "nanotechnology",
                "philosophy and ethics",
                "psychology",
                "public administration",
                "robotics",
                "science and technology studies",
                "sustainable energy",
                "technical medicine",
                "water technology",
                "data science",
                "research",
                "engineering",
                "finance",
                "nanomaterials",
                "quantum physics",
                "fluid dynamics",
                "healthcare technology",
                "medical devices",
                "management",
                "consulting",
                "entrepreneurship",
                "software development",
                "AI",
                "data analytics",
                "IT architecture",
                "chemical processes",
                "urban planning",
                "project management",
                "public relations",
                "media",
                "digital communication",
                "telecommunications",
                "power systems",
                "IoT",
                "automation",
                "sustainability",
                "policy development",
                "policy analysis",
                "diplomacy",
                "governance",
                "UX design",
                "innovation management",
                "operations management",
                "logistics",
                "supply chain",
                "decision support systems",
                "robotics",
                "autonomous systems",
                "AI-driven automation",
                "control systems",
                "science communication",
                "ethics consulting",
                "cognitive psychology",
                "human factors",
                "mental health",
                "government",
                "public sector management"
        )
        println("DEBUG: Interests intent loaded with examples: $interests") // NEW LOG
        return interests
    }

    // This method processes the user's input and stores the study program
    fun processUserInterest(userInput: String) {
        val interests = getExamples(Language.ENGLISH_GB)
        val matchedInterest = interests.firstOrNull { interest ->
            userInput.trim().equals(interest, ignoreCase = true) || userInput.contains(Regex("\\b$interest\\b", RegexOption.IGNORE_CASE))
        }

        if (matchedInterest != null) {
            UserData.userInterests = matchedInterest
            println("Matched interest: $matchedInterest")
        } else {
            println("No matching interest found for: $userInput")
        }

        /*for (interest in userInterests) {
            if (userInput.contains(interest, ignoreCase = true)) {
                // Save the study program to the UserData singleton
                UserData.userInterests = interest
                break
            }
        }

        // Log the study program (just for demo)
        println("User has interest in: ${UserData.userInterests}")*/
    }
}


val BachelorSelection: State = state(Parent) {
    onEntry {
        furhat.ask("What is your bachelor degree?")
    }

    onResponse<NoBachelor> {
        goto(EnrolledAsBachelor) // Transition to the next state
    }

    onResponse<Bachelors> {
        val userInput = it.text // Get the user's response as a String
        val bachelorsIntent = Bachelors()
        bachelorsIntent.processUserInput(userInput) // Call the method to process and save the study program

        goto(MasterDirection)
    }
}

val EnrolledAsBachelor: State = state {

    onEntry {
        furhat.ask("Are you enrolled as a bachelor student?")
    }

    onResponse<Yes> {
        goto(TellsAboutBachelor)
    }
    onResponse<No> {
        furhat.say("I can only help you to find a master study. Information about bachelor studies are available on the UT website.")
        goto(General)
    }
}

val WhatIsBachelor: State = state {
    onResponse<Yes> {
        goto(TellsAboutBachelor)
    }
    onResponse<No> {
        furhat.say("I can only help you to find a master study. Information about bachelor studies are available on the UT website.")
        goto(General)
    }
}

val TellsAboutBachelor: State = state {
    onEntry {
        furhat.ask("What are you studying?")
    }
    onResponse<Bachelors> {
        val userInput = it.text // Get the user's response as a String
        val bachelorsIntent = Bachelors()
        bachelorsIntent.processUserInput(userInput) // Call the method to process and save the study program

        goto(MasterDirection)
    }
}

val MasterDirection: State = state {
    onEntry {
        furhat.ask("Do you want to go in the same direction in your masters?")
    }
    onResponse<Yes> {
        furhat.say("Great!")
        goto(MasterFromBachelor)
    }
    onResponse<No> {
        furhat.say("That is okay.")
        println("Going to TellsAboutInterests") // Debugging
        goto(TellsAboutInterests)
    }
}

val TellsAboutInterests: State = state {
    onEntry {
        println("In TellsAboutInterests") // Debug log
        furhat.ask("Can you tell me more about your interests?")
    }
    onResponse<Interests> {
        println("User input classified as: ${it.intent}")
        val userInterest = it.text // Get the user's response as a String
        val interests = Interests()
        interests.processUserInterest(userInterest) // Call the method to process and save the study program
        println("User has interest in: ${UserData.userInterests}")
        goto(GroundingInterest)
    }
}

val GroundingInterest: State = state {
    onEntry {
        val interest = UserData.userInterests
        furhat.say("Okay, I can tell you more about $interest")
        goto(InterestBasedMasterSelection)
    }
}