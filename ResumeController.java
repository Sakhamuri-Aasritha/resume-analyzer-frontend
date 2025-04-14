package com.resume.analyzer.contoller;

import org.apache.tika.Tika;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resume.analyzer.contoller.MatchResult;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private static final List<String> skillSet = Arrays.asList(
            "Java", "Spring Boot", "Python", "AWS", "Azure", "Docker", "Kubernetes",
            "SQL", "MySQL", "PostgreSQL", "JavaScript", "HTML", "CSS", "React", "Angular"
    );

    private static final List<String> educationKeywords = Arrays.asList(
            "b.tech", "b.e", "bachelor", "m.tech", "m.s", "master", "mba", "phd",
            "university", "college", "graduated", "gpa", "cgpa"
    );

    private static final List<String> experienceKeywords = Arrays.asList(
            "software engineer", "developer", "intern", "analyst", "company", "experience",
            "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec", "-", "present"
    );

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded.");
        }

        try {
            Tika tika = new Tika();
            String text = tika.parseToString(file.getInputStream()).toLowerCase();

            // Skill Extraction
            List<String> matchedSkills = skillSet.stream()
                    .filter(skill -> text.contains(skill.toLowerCase()))
                    .collect(Collectors.toList());

            // Split resume into lines
            List<String> lines = Arrays.asList(text.split("\\r?\\n"));

            // Education Extraction
            List<String> education = lines.stream()
                    .filter(line -> educationKeywords.stream().anyMatch(line::contains))
                    .collect(Collectors.toList());

            // Experience Extraction
            List<String> experience = lines.stream()
                    .filter(line -> experienceKeywords.stream().anyMatch(line::contains))
                    .collect(Collectors.toList());

            // Build response
            StringBuilder response = new StringBuilder();
            response.append("✅ Skills Found: ").append(matchedSkills).append("\n\n");
            response.append("🎓 Education:\n");
            education.forEach(e -> response.append("- ").append(e).append("\n"));
            response.append("\n💼 Experience:\n");
            experience.forEach(e -> response.append("- ").append(e).append("\n"));

            return ResponseEntity.ok(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing resume: " + e.getMessage());
        }
    }


    @PostMapping("/match")
    public ResponseEntity<MatchResult> matchResumeWithJD(
            @RequestParam("resume") MultipartFile resumeFile,
            @RequestParam("job") MultipartFile jobDescFile) {

        if (resumeFile.isEmpty() || jobDescFile.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Tika tika = new Tika();
            String resumeText = tika.parseToString(resumeFile.getInputStream()).toLowerCase();
            String jobDescText = tika.parseToString(jobDescFile.getInputStream()).toLowerCase();

            List<String> jdSkills = skillSet.stream()
                    .filter(skill -> jobDescText.contains(skill.toLowerCase()))
                    .collect(Collectors.toList());

            List<String> resumeSkills = skillSet.stream()
                    .filter(skill -> resumeText.contains(skill.toLowerCase()))
                    .collect(Collectors.toList());

            List<String> matched = jdSkills.stream()
                    .filter(resumeSkills::contains)
                    .collect(Collectors.toList());

            double matchScore = (jdSkills.size() == 0) ? 0 : ((double) matched.size() / jdSkills.size()) * 100;

            MatchResult result = new MatchResult(matchScore, matched, jdSkills, resumeSkills);


// ✅ Save to JSON file before returning
            ObjectMapper mapper = new ObjectMapper();
            String outputDir = "output";
            new File(outputDir).mkdirs();

            String fileName = "match-result_" + System.currentTimeMillis() + ".json";
            File outputFile = new File(outputDir + File.separator + fileName);

            mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, result);

// ✅ Now return the response
            return ResponseEntity.ok(result);



        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}

