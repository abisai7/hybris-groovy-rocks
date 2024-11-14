// List all files in the hybris server
new File('/etc/passwd').eachLine {
    println it
}