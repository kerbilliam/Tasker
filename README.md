# Tasker

## How to Run

### GNU/Linux and MacOS

```bash
cd path/to/Tasker
chmod +x install
./install
source ~/.bashrc
```

Then, simply run:

```bash
tasker
```
At this time, the program only looks for and create new files in the current directory that tasker is ran in.

### Microsoft Windows

Add the path to Tasker to your environment variables. Then, `cd` to where Tasker is located in your system. Then run:

```powershell
./compile.bat
```

After that, run:

```powershell
tasker
```

Currently for Windows, Tasker will only work when you run `tasker` within the directory Tasker is installed.

## References

This project uses the SQLite JDBC driver (version 3.49.1.0): https://github.com/xerial/sqlite-jdbc

### Resources Used for Learning and Development

SQL commands and usage: 
- https://youtu.be/8Xyn8R9eKB8?si=jv9aQlf7iY3a3Kip

Working with SQLite JDBC driver: 
- https://youtu.be/0beocykXUag?si=ZLpKAcufIwLPOyWM
- https://youtu.be/03rDqI6lxdI?si=omc_1UFMRM6SfsbB
- https://www.geeksforgeeks.org/establishing-jdbc-connection-in-java/

Cipher:
- https://www.baeldung.com/java-aes-encryption-decryption

