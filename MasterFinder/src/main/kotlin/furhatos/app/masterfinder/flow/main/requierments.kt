import furhatos.app.masterfinder.flow.Parent
import furhatos.app.masterfinder.flow.main.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.records.User

val Requierements: State = state(Parent) {

    onEntry {
        furhat.ask("To apply for the master's program, you need a relevant bachelor's degree, a sufficient GPA, and English proficiency. Do you believe you meet these requirements?")
    }

    onResponse<Yes> {
        goto(Nationality)
    }

    onResponse<No> {
        goto(NotSuited)
    }

}

val NotSuited: State = state(Parent) {
    onEntry {
        furhat.say("No problem! often you can enter a masters program by doing a pre-master")
        furhat.ask("Do you want more information about a pre-master for" + UserData.userOptionalMaster +"?")
    }

    onResponse<Yes> {
        goto(WhatKindOfInfo)
    }

    onResponse<No> {
        goto(General)
    }

}

val WhatKindOfInfo: State = state(Parent) {
    onEntry {
        furhat.ask("Do you want to know general information about a pre-master, the duration of the pre-master for" + UserData.userOptionalMaster + ", something about a required background or about the purpose?")
    }

    onResponse<GeneralPreMaster> {
        furhat.say("Pre-master's programs are preparatory courses designed to bridge the gap between a student's prior education and the requirements of a master's program. They are intended for students with a bachelor's degree from a university of applied sciences or those whose degree doesn't directly align with the master's program. These programs help students gain the necessary knowledge, skills, and academic competencies to succeed in their chosen master's studies, with a focus on domain-specific knowledge, mathematical skills, and academic skills, especially for HBO graduates transitioning to research universities.")
        goto(WantToKnowMore)
    }

    onResponse<DurationPreMaster> {
        furhat.say("A pre-master takes half a year. You can take up to one year to finish it.")
        goto(WantToKnowMore)
    }

    onResponse<BackgroundPreMaster> {
        furhat.say("You need a" + findPreMaster(UserData.userOptionalMaster)!!.requiredBackground + ". Please visit the UT website if you need any more information")
        goto(WantToKnowMore)
    }

    onResponse<PurposePreMaster> {
        furhat.say("The purpose of this pre-master is to" + findPreMaster(UserData.userOptionalMaster)!!.purpose + ". Please visit the UT website if you need any more information")
        goto(WantToKnowMore)
    }

}

val WantToKnowMore: State = state(Parent) {
    onEntry {
        furhat.ask("Do you want to know other information about the pre-master?")
    }

    onResponse<Yes> {
        goto(WhatKindOfInfo)
    }

    onResponse<No> {
        furhat.say("Okay")
        goto(Nationality)
    }

}

val Nationality: State = state(Parent) {
    onEntry {
        furhat.say("I have to know your nationality to give information about the application process")
        furhat.ask("Can I ask? Are you a Dutch citizen, a citizen of another European country, or a non-European citizen?")
    }

    onResponse<Dutch> {
        furhat.say("As a Dutch citizen, for a September intake, you can apply from 1 October with a deadline before 1 August. For a February intake, applications open on 1 March with a deadline before 1 January.")
        goto(General)
    }

    onResponse<European> {
        furhat.say("As a citizen from Europe country, for a September intake, you can apply from 1 October with a deadline before 1 July. For a February intake, applications open on 1 March with a deadline before 1 December.")
        goto(General)
    }

    onResponse<OutsideEurope> {
        furhat.say("As a non-EEA citizen, for a September intake, you can apply from 1 October with a deadline before 1 May. For a February intake, applications open on 1 March with a deadline before 1 October.")
        goto(General)
    }

}

fun findPreMaster(name: String?): PreMasterProgram? {
    return preMasters.find { it.name.equals(name, ignoreCase = true) }
}

data class PreMasterProgram(
        val name: String,
        val durationMonths: Int,
        val requiredBackground: String,
        val purpose: String
)

val preMasters = listOf(
        PreMasterProgram(
                "Applied Mathematics",
                6,
                "Bachelor's in Mathematics, Engineering, or related fields",
                "Cover mathematical modeling, computational science, and data analytics."
        ),
        PreMasterProgram(
                "Applied Physics",
                6,
                "Bachelor's in Physics, Engineering, or related fields",
                "Prepare students for research in physics, nanotechnology, and quantum mechanics."
        ),
        PreMasterProgram(
                "Biomedical Engineering",
                6,
                "Bachelor's in Biomedical Engineering, Mechanical Engineering, or related fields",
                "Bridge the gap between engineering and medical sciences for healthcare innovations."
        ),
        PreMasterProgram(
                "Business Administration",
                6,
                "Bachelor's in Business, Economics, or related fields",
                "Cover management theory, business strategy, and leadership skills."
        ),
        PreMasterProgram(
                "Business Information Technology",
                6,
                "Bachelor's in Business, IT, or related fields",
                "Focus on IT management, software development, and business analytics."
        ),
        PreMasterProgram(
                "Chemical Science and Engineering",
                6,
                "Bachelor's in Chemical Engineering, Chemistry, or related fields",
                "Prepare students for process technology, materials science, and sustainable chemistry."
        ),
        PreMasterProgram(
                "Civil Engineering and Management",
                6,
                "Bachelor's in Civil Engineering or related fields",
                "Cover project management, urban development, and sustainable infrastructure."
        ),
        PreMasterProgram(
                "Communication Science",
                6,
                "Bachelor's in Media, Communication, or related fields",
                "Focus on media studies, digital communication, and public relations."
        ),
        PreMasterProgram(
                "Computer Science",
                6,
                "Bachelor's in Computer Science, IT, or related fields",
                "Cover programming, AI, software engineering, and cybersecurity."
        ),
        PreMasterProgram(
                "Construction Management and Engineering",
                6,
                "Bachelor's in Civil Engineering, Architecture, or related fields",
                "Focus on construction project management, risk assessment, and digital construction."
        ),
        PreMasterProgram(
                "Educational Science and Technology",
                6,
                "Bachelor's in Education, Psychology, or related fields",
                "Cover instructional design, digital learning, and curriculum development."
        ),
        PreMasterProgram(
                "Electrical Engineering",
                6,
                "Bachelor's in Electrical Engineering or related fields",
                "Prepare students for embedded systems, telecommunications, and power systems."
        ),
        PreMasterProgram(
                "Embedded Systems",
                6,
                "Bachelor's in Computer Science, Electrical Engineering, or related fields",
                "Focus on system integration, automation, and IoT."
        ),
        PreMasterProgram(
                "Environmental and Energy Management",
                6,
                "Bachelor's in Environmental Science, Engineering, or related fields",
                "Cover sustainability, energy policy, and environmental management."
        ),
        PreMasterProgram(
                "European Studies",
                6,
                "Bachelor's in Social Sciences, Political Science, or related fields",
                "Focus on European governance, policy analysis, and international relations."
        ),
        PreMasterProgram(
                "Health Sciences",
                6,
                "Bachelor's in Health Sciences, Public Health, or related fields",
                "Prepare students for healthcare management, policy making, and research."
        ),
        PreMasterProgram(
                "Industrial Design Engineering",
                6,
                "Bachelor's in Industrial Design, Engineering, or related fields",
                "Focus on user-centered design, prototyping, and product development."
        ),
        PreMasterProgram(
                "Industrial Engineering and Management",
                6,
                "Bachelor's in Industrial Engineering, Business, or related fields",
                "Cover logistics, operations management, and decision support systems."
        ),
        PreMasterProgram(
                "Interaction Technology",
                6,
                "Bachelor's in Computer Science, Psychology, or related fields",
                "Focus on human-computer interaction, AI, and UX design."
        ),
        PreMasterProgram(
                "Mechanical Engineering",
                6,
                "Bachelor's in Mechanical Engineering or related fields",
                "Cover thermodynamics, materials science, and manufacturing processes."
        ),
        PreMasterProgram(
                "Nanotechnology",
                6,
                "Bachelor's in Physics, Chemistry, or related fields",
                "Focus on nanoelectronics, nanomaterials, and nanomedicine."
        ),
        PreMasterProgram(
                "Philosophy of Science, Technology and Society",
                6,
                "Bachelor's in Social Sciences, Humanities, or related fields",
                "Cover ethics of technology, science communication, and policy making."
        ),
        PreMasterProgram(
                "Psychology",
                6,
                "Bachelor's in Psychology or related fields",
                "Focus on cognitive psychology, human factors, and mental health."
        ),
        PreMasterProgram(
                "Public Administration",
                6,
                "Bachelor's in Political Science, Public Administration, or related fields",
                "Cover governance, public sector management, and policy analysis."
        ),
        PreMasterProgram(
                "Robotics",
                6,
                "Bachelor's in Engineering, Computer Science, or related fields",
                "Focus on AI-driven automation, control systems, and robot design."
                )
)

