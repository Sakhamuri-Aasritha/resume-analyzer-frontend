import React, { useState } from "react";
import "./App.css";

function App() {
  const [resumeFile, setResumeFile] = useState(null);
  const [jobFile, setJobFile] = useState(null);
  const [result, setResult] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!resumeFile || !jobFile) {
      alert("Please upload both files!");
      return;
    }

    const formData = new FormData();
    formData.append("resume", resumeFile);
    formData.append("job", jobFile);

    try {
      const response = await fetch("http://localhost:8080/api/resume/match", {
        method: "POST",
        body: formData,
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Server responded with status ${response.status}: ${errorText}`);
      }

      const data = await response.json();
      setResult(data);
    } catch (error) {
      console.error("FETCH ERROR:", error);
      alert("Something went wrong! Check the console for details.");
    }
  };

  // ✅ Download JSON file
  const downloadJSON = (data) => {
    const blob = new Blob([JSON.stringify(data, null, 2)], { type: "application/json" });
    const url = URL.createObjectURL(blob);

    const link = document.createElement("a");
    link.href = url;
    link.download = `match-result-${Date.now()}.json`;
    link.click();

    URL.revokeObjectURL(url);
  };

  return (
      <div className="App">
        <h1>📄 Resume Analyzer</h1>
        <form onSubmit={handleSubmit}>
          <div>
            <label>Resume:</label>
            <input type="file" onChange={(e) => setResumeFile(e.target.files[0])} />
          </div>
          <div>
            <label>Job Description:</label>
            <input type="file" onChange={(e) => setJobFile(e.target.files[0])} />
          </div>
          <button type="submit">Analyze</button>
        </form>

        {result && (
            <div className="result">
              <h2>📊 Match Score: {result.matchScore.toFixed(2)}%</h2>
              <p><strong>✅ Matched Skills:</strong> {result.matchedSkills.join(", ")}</p>
              <p><strong>🧠 JD Skills:</strong> {result.jdSkills.join(", ")}</p>
              <p><strong>📄 Resume Skills:</strong> {result.resumeSkills.join(", ")}</p>

              {/* 🔽 Download Button */}
              <button onClick={() => downloadJSON(result)} style={{ marginTop: "1rem" }}>
                ⬇️ Download Match Result
              </button>
            </div>
        )}
      </div>
  );
}

export default App;
