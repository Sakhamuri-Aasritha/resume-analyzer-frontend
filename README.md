# 🎯 Resume Analyzer - Frontend (React)

This is the React frontend for the Resume Analyzer application. It allows users to upload a resume and a job description, and view a match score along with the extracted skills.

## 🚀 Features

- Upload resume & job description files (PDF, DOCX, TXT)
- Displays:
  - Match Score (%)
  - ✅ Matched Skills
  - 🧠 JD Skills
  - 📄 Resume Skills
- Download analysis result as `.json` file
- Beautiful, responsive UI built with plain CSS

## 📸 Screenshot

<img src="https://github.com/Sakhamuri-Aasritha/ScreenShot/blob/main/Screenshot%20(5).png" alt="Frontend ScreenShot">

## 🛠️ Tech Stack

- React
- JavaScript (ES6+)
- CSS

## 📦 Installation

1. Navigate to the frontend directory:

   ```bash
   cd resume-analyzer-frontend
# 🧠 Resume Analyzer - Backend (Spring Boot)

This is the backend API for the Resume Analyzer application. It accepts a resume and a job description file, extracts skills, and returns a match score based on keyword similarity. It also saves the analysis result to a `.json` file for reference.

---

## 🚀 Features

- Upload a **resume** and a **job description** via REST API
- Extracts relevant skills using Apache Tika
- Computes:
  - 📊 Match Score (%)
  - ✅ Matched Skills
  - 🧠 JD (Job Description) Skills
  - 📄 Resume Skills
- Saves results as JSON file locally
- Designed to integrate with React frontend

---

## 🛠 Tech Stack

- Java 21
- Spring Boot 3.4+
- Apache Tika (for text extraction)
- Jackson (for JSON serialization)
- Maven

---

## 📦 How to Run

### 1️⃣ Clone the Repo

```bash
git clone https://github.com/YOUR_USERNAME/resume-analyzer-backend.git
cd resume-analyzer-backend
