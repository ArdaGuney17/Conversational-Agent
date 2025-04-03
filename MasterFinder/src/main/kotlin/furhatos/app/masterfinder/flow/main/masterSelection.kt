package furhatos.app.masterfinder.flow.main

import Requierements
import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.*
import furhatos.nlu.common.*
import furhatos.util.Language

// Define custom intents



var masterNames: kotlin.collections.List<String?>? = null

class ProposedMasters : Intent(){
    override fun getExamples(lang: Language): kotlin.collections.List<String?>? {
        println(masterNames)
        return masterNames
    }
}

val MasterSelection: State = state(Parent) {

    onEntry {
        furhat.ask("Which master's program did you have in mind?")
    }

    onResponse<MasterCourses> {

        val userInput = it.text
        val mastersIntent = MasterCourses()

        mastersIntent.processUserInput(userInput)

        // Find the corresponding program from the list
        val master = UserData.userOptionalMaster

        if (master != null) {
            furhat.say("So you are interested in $master, that is nice to hear")
            goto(MasterInformation)
        } else {
            furhat.say("I’m sorry, I couldn’t find that master’s program. Could you try again?")
            reentry()
        }
    }

    onResponse<NoAnswer> {
        furhat.say("Alright, let me know if you need any other assistance.")
        goto(Parent)
    }
}


fun findMatchingMasters(bachelor: String): kotlin.collections.List<MasterProgram> {
    return masterPrograms.filter { master ->
        master.admission.contains(bachelor, ignoreCase = true)
    }
}

val MasterFromBachelor: State = state {
    onEntry {
        val bachelorStudy = UserData.userStudyProgram
        println("User selected Bachelor: $bachelorStudy")

        val matchingMasters = bachelorStudy?.let { findMatchingMasters(it) }

        if (matchingMasters != null) {
            if (matchingMasters.isNotEmpty()) {
                //masterNames = matchingMasters.joinToString(", ") { it.name }
                masterNames = matchingMasters.map { it.name }
                println(masterNames)
                println(UserData.userOptionalMaster)
                furhat.say("Based on your bachelor's degree in $bachelorStudy, you can apply for the following Master's programs: $masterNames.")
                goto(KnowMoreAboutMaster)
            } else {
                furhat.say("I'm sorry, but I couldn't find a Master's program directly related to $bachelorStudy. You may need to check the admission requirements on the UT website.")
            }
        }

        goto(Idle) // Move to the next state in your flow
        }
}

val KnowMoreAboutMaster: State = state {
    onEntry {
        furhat.ask("Would you like more information about any of these masters?")
    }

    onResponse<YesAnswer> {
        goto(ChoiceMastersFromBachelor)
    }

    onResponse<NoAnswer> {
        goto(General)
    }
}

val ChoiceMastersFromBachelor: State = state {
    onEntry {
        furhat.ask("Which masters?")
    }

    onResponse<ProposedMasters> {
        val chosenMaster = it.text
        UserData.userOptionalMaster = chosenMaster
        goto(MasterInformation)
    }

    onResponse {
        furhat.say("You did not answer any of the proposed masters")
        goto(General)
    }
}

fun findMatchingMastersByInterest(userInterests: kotlin.collections.List<String?>): kotlin.collections.List<MasterProgram> {
    return masterPrograms.filter { master ->
        userInterests.any { interest ->
            interest?.let { master.name.contains(it, ignoreCase = true) } == true ||
                    interest?.let { master.faculty.contains(it, ignoreCase = true) } == true||
                    interest?.let { master.admission.contains(it, ignoreCase = true) } == true||
                    interest?.let { master.careerProspects.contains(it, ignoreCase = true) } == true||
                    interest?.let { master.structure.contains(it, ignoreCase = true) } == true
        }
    }
}

val InterestBasedMasterSelection: State = state {
    onEntry {
        val userInterest = UserData.userInterests // This should be a single interest or list of interests
        println("User selected interests: $userInterest")

        val matchingMasters = findMatchingMastersByInterest(listOf(userInterest)) // Convert to list if single value

        if (matchingMasters.isNotEmpty()) {
            masterNames = matchingMasters.map { it.name }
            furhat.say("Based on your interests in $userInterest, you might be interested in these Master's programs: $masterNames.")
            goto(KnowMoreAboutMaster)
        } else {
            furhat.say("I couldn't find a direct match for your interests in our Master's programs. You may want to explore options on the UT website.")
            goto(General)
        }
    }
}

val MasterInformation: State = state(Parent){

    fun getMasterByName(name: String?): MasterProgram? {
        return masterPrograms.find { it.name.equals(name, ignoreCase = true) }
    }


    var master = getMasterByName(UserData.userOptionalMaster)



    onEntry {
        if (master != null) {
            furhat.ask("Would you like to know about the admission requirements, career opportunities, faculty, duration or the study structure for${master.name}?")
        }
    }

    onResponse<AskAboutRequirements> {
        if (master != null) {
            furhat.say("For ${master.name}, the admission requirements include: ${master.admission}.")
        }
        goto(Requierements)
    }

    onResponse<CareerRequest> {
        if (master != null) {
            furhat.say("Graduates of ${master.name} often pursue careers in ${master.careerProspects}.")
        }
        goto(OtherInformation)
    }

    onResponse<FacultyRequest> {
        if (master != null) {
            furhat.say("The faculty for ${master.name} is ${master.faculty}.")
        }
        goto(OtherInformation)
    }

    onResponse<DurationRequest> {
        if (master != null) {
            furhat.say("The duration of ${master.name} is ${master.duration}.")
        }
        goto(OtherInformation)
    }

    onResponse<StructureRequest> {
        if (master != null) {
            furhat.say("${master.name} is structured as follows: ${master.structure}.")
        }
        goto(OtherInformation)
    }

    onResponse<NoAnswer> {
        goto(General)
    }
}

val OtherInformation: State = state {
    onEntry {
        furhat.ask("Would you like other information about this masters?")
    }

    onResponse<YesAnswer> {
        goto(MasterInformation)
    }

    onResponse<NoAnswer> {
        goto(ContinueOrEnd)
    }
}

val ContinueOrEnd: State = state {
    onEntry {
        furhat.ask("Would you like information about an other masters program?")
    }

    onResponse<YesAnswer> {
        goto(MasterSelection)
    }

    onResponse<NoAnswer> {
        goto(General)
    }
}


data class MasterProgram(
        val name: String,
        val duration: String,
        val language: String,
        val faculty: String,
        val admission: String,
        val careerProspects: String,
        val structure: String
)

/** List of Available Masters */
val masterPrograms = listOf(
        MasterProgram("Applied Mathematics", "2", "English", "Electrical Engineering, Mathematics and Computer Science", "A relevant bachelor's degree, sufficient English proficiency.", "Data Science, Research, Engineering, Finance.", "Includes mathematical modeling, computational science, and data analytics."),
        MasterProgram("Applied Physics", "2", "English", "Technical Physics", "Physics or engineering background, sufficient mathematics knowledge.", "R&D, Engineering, Academia, High-tech industry.", "Specializations in nanotechnology, quantum physics, and fluid dynamics."),
        MasterProgram("Biomedical Engineering", "2", "English", "Biomedical Engineering", "Bachelor’s degree in Biomedical Engineering, Mechanical Engineering, or a related field.", "Healthcare technology, Medical Devices, Research.", "Focus on medical technology, bioinstrumentation, and biomaterials."),
        MasterProgram("Business Administration", "1", "English", "Behavioural, Management and Social Sciences", "Bachelor's degree in Business Administration or related fields.", "Management, Consulting, Entrepreneurship.", "Management theory, business strategy, and leadership."),
        MasterProgram("Business Information Technology", "2", "English", "Electrical Engineering, Mathematics and Computer Science", "Bachelor's degree in Information Technology, Business, or related fields.", "IT Management, Business Analysis, Software Development.", "Business processes, IT architecture, and data management."),
        MasterProgram("Chemical Science and Engineering", "2", "English", "Engineering Technology", "Bachelor's degree in Chemical Engineering or related fields.", "Chemical Engineering, Process Industries, R&D.", "Focus on process technology, chemical engineering, and materials science."),
        MasterProgram("Civil Engineering and Management", "2", "English", "Engineering Technology", "Bachelor’s degree in Civil Engineering or related fields.", "Construction, Urban Planning, Project Management.", "Urban design, project management, and sustainable infrastructure."),
        MasterProgram("Communication Science", "1", "English", "Behavioural, Management and Social Sciences", "Bachelor's degree in Communication, Media, or related fields.", "Public Relations, Media, Communication Strategy.", "Focus on communication theory, media studies, and digital communication."),
        MasterProgram("Computer Science", "2", "English", "Electrical Engineering, Mathematics and Computer Science", "Bachelor's degree in Computer Science, Software Engineering, or related fields.", "Software Development, AI, Data Science.", "Programming, algorithms, artificial intelligence, and software engineering."),
        MasterProgram("Construction Management and Engineering", "2", "English", "Engineering Technology", "Bachelor’s degree in Civil Engineering or related fields.", "Construction, Infrastructure, Project Management.", "Construction process optimization, risk management, and digital construction."),
        MasterProgram("Educational Science and Technology", "1", "English", "Behavioural, Management and Social Sciences", "Bachelor’s degree in Education, Psychology, or related fields.", "Educational Technology, Instructional Design, Research.", "Curriculum development, instructional design, learning analytics."),
        MasterProgram("Electrical Engineering", "2", "English", "Electrical Engineering, Mathematics and Computer Science", "Bachelor's degree in Electrical Engineering or related fields.", "Telecommunications, Power Systems, Embedded Systems.", "Electronics, circuits, systems design, and signal processing."),
        MasterProgram("Embedded Systems", "2", "English", "Electrical Engineering, Mathematics and Computer Science", "Bachelor's degree in Electrical Engineering, Computer Science, or related fields.", "Embedded Systems Development, IoT, Automation.", "Focus on hardware, software, and system integration."),
        MasterProgram("Environmental and Energy Management", "1", "English", "Engineering Technology", "Bachelor's degree in Environmental Science, Engineering, or related fields.", "Sustainability, Energy Management, Policy Development.", "Energy systems, sustainability strategies, and environmental impact."),
        MasterProgram("European Studies", "1", "English", "Behavioural, Management and Social Sciences", "Bachelor's degree in Social Sciences, Political Science, or related fields.", "Policy Analysis, Diplomacy, Governance.", "European governance, policy evaluation, international relations."),
        MasterProgram("Health Sciences", "1", "English", "Behavioural, Management and Social Sciences", "Bachelor’s degree in Health Sciences or related fields.", "Healthcare Management, Policy Development, Research.", "Healthcare systems, patient-centered care, and policy analysis."),
        MasterProgram("Industrial Design Engineering", "2", "English", "Engineering Technology", "Bachelor’s degree in Industrial Design, Engineering, or related fields.", "Product Design, UX Design, Innovation Management.", "User-centered design, prototyping, design methodology."),
        MasterProgram("Industrial Engineering and Management", "2", "English", "Behavioural, Management and Social Sciences", "Bachelor's degree in Industrial Engineering, Business, or related fields.", "Operations Management, Logistics, Consultancy.", "Process optimization, supply chain management, and decision support systems."),
        MasterProgram("Interaction Technology", "2", "English", "Electrical Engineering, Mathematics and Computer Science", "Bachelor’s degree in Computer Science, Psychology, or related fields.", "Human-Computer Interaction, AI, UX Design.", "Focus on user experience, artificial intelligence, and robotics."),
        MasterProgram("Mechanical Engineering", "2", "English", "Engineering Technology", "Bachelor’s degree in Mechanical Engineering or related fields.", "Engineering, Design, Manufacturing.", "Design and analysis, thermodynamics, materials science."),
        MasterProgram("Nanotechnology", "2", "English", "Engineering Technology", "Bachelor's degree in Physics, Chemistry, or related fields.", "Nanomaterials, Research, High-tech Industry.", "Nanomaterials, nanoelectronics, and nanomedicine."),
        MasterProgram("Philosophy of Science, Technology and Society", "1", "English", "Behavioural, Management and Social Sciences", "Bachelor's degree in Social Sciences, Humanities, or related fields.", "Policy Making, Ethics Consulting, Research.", "Ethics of technology, science communication, and future studies."),
        MasterProgram("Psychology", "1", "English", "Behavioural, Management and Social Sciences", "Bachelor's degree in Psychology or related fields.", "Clinical Psychology, Research, Human Factors.", "Cognitive psychology, human factors, and mental health."),
        MasterProgram("Public Administration", "1", "English", "Behavioural, Management and Social Sciences", "Bachelor's degree in Political Science, Public Administration, or related fields.", "Government, Policy Making, Management.", "Public sector management, governance, and policy analysis."),
        MasterProgram("Robotics", "2", "English", "Electrical Engineering, Mathematics and Computer Science", "Bachelor's degree in Engineering, Computer Science, or related fields.", "Autonomous Systems, AI, Industrial Robotics.", "Robot design, AI-driven automation, control systems.")
)
