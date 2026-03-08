# Relikd

A vintage computer catalog application built for practicing OOP & Database concepts. Browse classic hardware (Relics haha) like the Apple II, IBM PC, Commodore 64, and more.

Built with **Java 21**, **JavaFX**, and **SQLite** (via JDBC).

<p align="center">
  <img src="https://files.tecnoblog.net/wp-content/uploads/2023/01/jobs-couch-lisa.jpg" alt="Steve Jobs and Apple Lisa" width="400"/>
</p>

## Features
- **Fast Search & Filters**: Sort and find machines instantly.
- **Async Images**: High-quality Wiki images fetch and cache in the background via Virtual Threads.
- **Embedded DB**: Everything runs off a single, auto-seeding SQLite file.

## Setup & Run

To run Relikd locally, ensure you have **Java JDK 21+** installed. The project uses the Maven Wrapper (`mvnw`), so you don't need to install Maven manually on your machine.

**1. Clone the repository and navigate to the project directory:**
```bash
git clone https://github.com/yourusername/relikd.git
cd relikd
```

**2. Compile the project:**
```bash
./mvnw clean compile
```

**3. Launch the application:**
```bash
./mvnw javafx:run
```

*Note: On your very first run, Relikd will automatically instantiate the SQLite database and seed it with all vintage computer listings. This takes less than a second!*

Enjoy the retro computing history!
