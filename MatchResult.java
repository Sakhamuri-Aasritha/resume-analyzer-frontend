package com.resume.analyzer.contoller;

import java.util.List;

public class MatchResult {
    private double matchScore;
    private List<String> matchedSkills;
    private List<String> jdSkills;
    private List<String> resumeSkills;

    public MatchResult(double matchScore, List<String> matchedSkills, List<String> jdSkills, List<String> resumeSkills) {
        this.matchScore = matchScore;
        this.matchedSkills = matchedSkills;
        this.jdSkills = jdSkills;
        this.resumeSkills = resumeSkills;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public List<String> getJdSkills() {
        return jdSkills;
    }

    public List<String> getResumeSkills() {
        return resumeSkills;
    }
}
