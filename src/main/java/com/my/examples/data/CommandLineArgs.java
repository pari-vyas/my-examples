package com.my.examples.data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommandLineArgs {
    @Value("${action:none}")
    private String action;
    @Value("${name:none}")
    private String name;
    @Value("${inputFile:#{null}}")
    private String inputFile;
    @Value("${outputDirectory:#{null}}")
    private String outputDirectory;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputDirectory() { return outputDirectory; }

    public void setOutputDirectory(String outputDirectory) { this.outputDirectory = outputDirectory; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Args{");
        sb.append("\n action='").append(action).append('\'');
        sb.append(",\n name='").append(name).append('\'');
        sb.append(",\n inputFile='").append(inputFile).append('\'');
        sb.append(",\n outputDirectory='").append(outputDirectory).append('\'');
        sb.append('}');
        return sb.toString();
    }
}