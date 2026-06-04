Skill Based Job Recommendation System

Project Overview

The Skill Based Job Recommendation System is a Java Swing desktop application that recommends suitable job roles based on a user's skills. The system compares user-selected skills with predefined job requirements and calculates a matching score to identify the most suitable career options.

Problem Statement

Students often possess multiple technical skills but may not know which job roles best match their skill set. This project helps users identify relevant career opportunities by evaluating their skills against different job profiles.

Features

- Skill-based job recommendations
- Interactive Java Swing GUI
- Weighted skill matching mechanism
- Job-role ranking based on matching score
- Accuracy comparison graph
- CSV dataset support
- User-friendly desktop application

Technologies Used

- Java
- Java Swing
- IntelliJ IDEA
- CSV Files
- Git
- GitHub

Working of the System

1. The user selects or enters skills through the graphical interface.
2. Each job role in the dataset contains a list of required skills.
3. Every skill is assigned a weight from 1 to 5.
4. Higher weights indicate more important skills for that job role.
5. The system calculates a total score by matching user skills with job-role skills.
6. Job roles with higher scores are recommended to the user.

Skill Weighting Logic

The recommendation system uses weighted skill matching.

Example:

Java Developer

- Java = 5
- OOP = 5
- SQL = 4
- Git = 3
- HTML = 2

If a user possesses Java, OOP, SQL, and Git skills, the total matching score is calculated based on the corresponding weights.

The job roles with the highest matching scores are displayed as recommendations.

Project Modules

User Interface Module

Provides screens for skill selection and recommendation display.

Skill Matching Module

Compares user skills with job-role requirements.

Recommendation Module

Calculates weighted scores and ranks job roles.

Result Display Module

Displays recommended job roles and matching scores.

Accuracy Analysis Module

Shows comparison graphs and performance results.

Advantages

- Easy to use
- Fast recommendation generation
- Helps students identify suitable career paths
- Demonstrates practical use of weighted scoring techniques
- Supports multiple job profiles

Future Enhancements

- Web-based version
- Larger job database
- Resume-based skill extraction
- Personalized career guidance
- Real-time job market integration

Author

Sushmitha Pittala
Final-Year Computer Science and Engineering Student
