# File Upload & Processing Service (Plain Java, No Framework)

This project is a small, framework-agnostic Java 21 module that:

- Accepts a file upload (simulated via `InputStream` or from real files)
- Validates allowed file types (`.txt` and `.csv`)
- Processes the file to count lines and words
- Saves the processed data to an in-memory "database"
- Logs key actions and handles exceptions gracefully

The framework-agnostic side is intended, the code is organized in a MVC-ish way and the service takes InputStream so it can be plugged into a 
real HTTP multipart upload later without changing core logic and can easily integrate framework such as Springboot.

## Requirements

- Java 21
- Maven 3.x

## Build

Run (windows/powershell friendly) from the project root ("path/to/project/fileuploaddemo"):

mvn clean compile

## Run

### Demo mode (no arguments)
Runs a built-in demo (valid.txt + invalid.exe):

  mvn exec:java

### File mode (real files)
Pass one or more file paths as program arguments.

Recommended (Windows/PowerShell friendly, supports spaces):

  mvn clean package -DskipTests
  java -cp target\classes com.fileuploadex.App "path/to/file1.txt" "path/to/file2.csv"

Optional (works well when paths have no spaces):

  mvn exec:java "-Dexec.args=path/to/file1.txt path/to/file2.csv"

Notes:
- Only .txt and .csv are accepted by design.
- If a path contains spaces, prefer the direct java command above

Alternatively, you can configure your IDE (e.g. VS Code) to run `App` with program arguments corresponding to file paths.

## Tests
Run all unit tests (including AppTest):

  mvn test
